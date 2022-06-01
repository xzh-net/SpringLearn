package net.xzh.server.protocl;

import net.xzh.api.entity.Invocation;
import net.xzh.api.entity.URL;

public interface Protocl {
    /**
     * 远程调用
     * @param url
     * @param invocation
     */
    Object invokeProtocl(URL url, Invocation invocation);

    /**
     * 服务开启
     * @param url
     */
    void start(URL url);
}
