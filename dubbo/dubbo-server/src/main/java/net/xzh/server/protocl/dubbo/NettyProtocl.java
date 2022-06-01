package net.xzh.server.protocl.dubbo;

import net.xzh.api.entity.Invocation;
import net.xzh.api.entity.URL;

import net.xzh.server.protocl.Protocl;
import net.xzh.server.protocl.dubbo.client.NettyClient;

public class NettyProtocl implements Protocl {
    public Object invokeProtocl(URL url, Invocation invocation) {
        NettyClient nettyClient = new NettyClient();
        return nettyClient.send(url.getHost(),url.getPort(),invocation);
    }

    public void start(URL url) {
        NettyServer nettyServer = new NettyServer();
        nettyServer.start(url.getHost(),url.getPort());
    }
}
