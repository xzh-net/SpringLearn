package net.xzh.server.provide;

import net.xzh.api.HelloService;

public class HelloServiceImpl implements HelloService {

	public String sayHello(String name) {
		System.out.println("hello," + name);
		return "hello " + name;
	}
}
