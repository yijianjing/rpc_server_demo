package com.rpc.demo;

/**
 * @author jianjun
 * @version 1.0
 * @date 2020/10/22
 */
public class HelloServiceImpl implements IHelloService{


    @Override
    public String sayHello(String content) {
        System.out.println("hello sayHello "+content);
        return "Say Hello:"+content;
    }

    @Override
    public String saveUser(User user) {
        System.out.println("hello saveUser "+user.toString());
        return "SUCCESS";
    }
}
