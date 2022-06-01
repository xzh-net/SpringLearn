package net.xzh.server.protocl.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import net.xzh.api.entity.Invocation;
import net.xzh.register.RegisterType;
import net.xzh.register.factory.LocalRegisterFactory;

public class HttpHandler {

    public void handler(HttpServletRequest req, HttpServletResponse resp){
        // 获取对象
        try {
            InputStream is = req.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(is);
            Invocation invocation = (Invocation) objectInputStream.readObject();
            String interfaceName = invocation.getInterfaceName();
            Class interfaceImplClass = LocalRegisterFactory.getLocalRegister(RegisterType.LOCAL).get(interfaceName);
            Method method = interfaceImplClass.getMethod(invocation.getMethodName(),invocation.getParamtypes());
            String result = (String) method.invoke(interfaceImplClass.newInstance(),invocation.getObjects());
            IOUtils.write(result,resp.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
