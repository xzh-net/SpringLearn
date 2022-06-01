package net.xzh.register.factory;


import net.xzh.register.RegisterType;
import net.xzh.register.RemoteRegister;
import net.xzh.register.local.RemoterMapRegister;
import net.xzh.register.zookeeper.ZookeepRemoteRegister;
import net.xzh.register.zookeeper.ZookeeperClient;

public class RemoteRegisterFactory  {
    private static RemoterMapRegister remoterMapRegister = new RemoterMapRegister();
    private static ZookeepRemoteRegister zookeepRemoteRegister = new ZookeepRemoteRegister(new ZookeeperClient());
    public static RemoteRegister getRemoteRegister(RegisterType registerType){
        switch (registerType){
            case LOCAL:return remoterMapRegister;
            case ZOOKEEPER: return zookeepRemoteRegister;
            default:return null;
        }
    }
}
