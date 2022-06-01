package net.xzh.server.protocl.http;

import net.xzh.api.entity.Invocation;
import net.xzh.api.entity.URL;

import net.xzh.server.protocl.Protocl;
import net.xzh.server.protocl.http.client.HttpClient;

public class HttpProtocl implements Protocl {

    public Object invokeProtocl(URL url, Invocation invocation) {
        HttpClient httpClient = new HttpClient();
        return httpClient.post(url.getHost(),url.getPort(),invocation);
    }

    public void start(URL url) {
        HttpServer httpServer = new HttpServer();
        httpServer.start(url.getHost(),url.getPort());
    }
}
