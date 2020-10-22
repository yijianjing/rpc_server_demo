package com.rpc.demo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author jianjun
 * @version 1.0
 * @date 2020/10/22
 */
public class RpcProxyServer {

    ExecutorService executorService= Executors.newCachedThreadPool();

    public void publisher(Object service, int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                executorService.execute(new ProcessorHandler(service, socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
