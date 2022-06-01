package net.xzh.register.zookeeper;

import java.util.List;
import java.util.Random;

import com.alibaba.fastjson.JSONObject;
import net.xzh.api.entity.URL;

import net.xzh.register.RemoteRegister;

public class ZookeepRemoteRegister implements RemoteRegister {

    private ZookeeperClient client;

    public ZookeepRemoteRegister(ZookeeperClient zookeeperClient) {
        this.client = zookeeperClient;
    }

    public void register(String interfaceName, URL host) {
        try{
            StringBuilder nodePath = new StringBuilder("/");
            nodePath.append(interfaceName).append("/").append(JSONObject.toJSONString(host));
            if (client.started()){
                client.createNodePath(nodePath.toString(),System.currentTimeMillis()+"");
            }
        }catch (Exception e){

        }
    }

    public URL getRadomURL(String interfaceName) {
        try {
            StringBuilder nodepath = new StringBuilder("/");
            nodepath.append(interfaceName);
            List<URL> urls = client.getChildNodes(nodepath.toString());
            Random random = new Random();
            int i = random.nextInt(urls.size());
            return urls.get(i);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
