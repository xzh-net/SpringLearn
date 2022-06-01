package net.xzh.register.factory;


import net.xzh.register.LocalRegister;
import net.xzh.register.RegisterType;
import net.xzh.register.local.LocalMapRegister;

public class LocalRegisterFactory {

    private static LocalMapRegister localMapRegister = new LocalMapRegister();
    public static LocalRegister getLocalRegister(RegisterType registerType){
        switch (registerType){
            case LOCAL: return localMapRegister;
            default:return null;
        }
    }
}
