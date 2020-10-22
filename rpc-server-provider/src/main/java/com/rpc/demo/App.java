package com.rpc.demo;

/**
 * @author jianjun
 * @version 1.0
 * @date 2020/10/22
 */
public class App {
    public static void main(String[] args){

        IHelloService helloService=new HelloServiceImpl();

        RpcProxyServer proxyServer=new RpcProxyServer();
        proxyServer.publisher(helloService,8082);
    }
}
