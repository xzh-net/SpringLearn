package net.xzh.client.comsummer;

import net.xzh.api.HelloService;
import net.xzh.register.RegisterType;
import net.xzh.server.protocl.ProtoclType;

import net.xzh.client.proxy.ProxyFactory;

public class Consumer {
    public static void main(String[] args) {
        HelloService helloService = ProxyFactory.getProxy(ProtoclType.HTTP, RegisterType.ZOOKEEPER,HelloService.class);
        String result = helloService.sayHello("xuzhihao");
        System.out.println(result);
    }
}
