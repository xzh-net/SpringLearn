package net.xzh.client.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import net.xzh.api.entity.Invocation;
import net.xzh.api.entity.URL;
import net.xzh.register.RegisterType;
import net.xzh.register.RemoteRegister;
import net.xzh.register.factory.RemoteRegisterFactory;
import net.xzh.server.protocl.Protocl;
import net.xzh.server.protocl.ProtoclFactory;
import net.xzh.server.protocl.ProtoclType;

public class ProxyFactory {

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T getProxy(final ProtoclType protoclType ,final RegisterType registerType, final Class interfaceClass){
       return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
           public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
               Protocl protocl = ProtoclFactory.getProtocl(protoclType);
               Invocation invocation = new Invocation(interfaceClass.getName(),method.getName(),method.getParameterTypes(),args);
               RemoteRegister remoteRegister = RemoteRegisterFactory.getRemoteRegister(registerType);
               URL radomURL = remoteRegister.getRadomURL(interfaceClass.getName());
               System.out.println("调用地址host:"+ radomURL.getHost()+ ",port:"+radomURL.getPort());
               return protocl.invokeProtocl(radomURL,invocation);
           }
       });
    }
}
