package net.xzh.register.zookeeper;

import org.junit.Before;
import org.junit.Test;

import net.xzh.api.HelloService;
import net.xzh.api.entity.URL;

import net.xzh.register.zookeeper.ZookeepRemoteRegister;
import net.xzh.register.zookeeper.ZookeeperClient;

public class ZookeepRemoteRegisterTest {
    ZookeepRemoteRegister remoteRegister;
    @Before
    public void setUp() throws Exception {
        remoteRegister = new ZookeepRemoteRegister(new ZookeeperClient());
    }

    @Test
    public void register() {
        remoteRegister.register(HelloService.class.getName(),new URL("172.17.17.200",2181));
    }

    @Test
    public void getRadomURL() {
        register();
        remoteRegister.getRadomURL(HelloService.class.getName());
    }
}