package com.rpc.demo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @author jianjun
 * @version 1.0
 * @date 2020/10/22
 */
public class ProcessorHandler implements Runnable {

    private Object service;
    private Socket socket;

    public ProcessorHandler(Object service, Socket socket) {
        this.service = service;
        this.socket = socket;
    }

    @Override
    public void run() {
        ObjectInputStream objectInputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
            Object result = invoke(rpcRequest);

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(result);
            objectOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Object invoke(RpcRequest rpcRequest) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        //反射实现
        //获取类
        Object[] args = rpcRequest.getParameters(); //拿到客户端请求的参数
        Class<?>[] types = new Class[args.length];//获得每个参数的类型
        for (int i = 0; i < args.length; i++) {
            types[i] = args[i].getClass();
        }

        Class classz = Class.forName(rpcRequest.getClassName());//跟去请求的类进行加载
        Method method = classz.getMethod(rpcRequest.getMethodName(), types); //sayHello, saveUser找到这个类中的方法
        Object result = method.invoke(service,args);//HelloServiceImpl 进行反射调用

        return result;
    }
}
