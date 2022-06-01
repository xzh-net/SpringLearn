package net.xzh.server.provide;

import net.xzh.api.HelloService;
import net.xzh.api.entity.URL;
import net.xzh.register.LocalRegister;
import net.xzh.register.RegisterType;
import net.xzh.register.RemoteRegister;
import net.xzh.register.factory.LocalRegisterFactory;
import net.xzh.register.factory.RemoteRegisterFactory;

import net.xzh.server.protocl.Protocl;
import net.xzh.server.protocl.ProtoclFactory;
import net.xzh.server.protocl.ProtoclType;

public class Provider2 {
	public static void main(String[] args) {
		URL url = new URL("localhost", 8022);
		// 远程服务注册地址
		RemoteRegister register = RemoteRegisterFactory.getRemoteRegister(RegisterType.ZOOKEEPER);
		register.register(HelloService.class.getName(), url);

		// 本地注册服务的实现类
		LocalRegister localRegister = LocalRegisterFactory.getLocalRegister(RegisterType.LOCAL);
		localRegister.register(HelloService.class.getName(), HelloServiceImpl.class);

		Protocl protocl = ProtoclFactory.getProtocl(ProtoclType.NETTY);
		protocl.start(url);
	}
}
