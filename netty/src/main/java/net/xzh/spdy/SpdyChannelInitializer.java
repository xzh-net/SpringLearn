package net.xzh.spdy;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;

/**
 * Created by dimi on 2018/11/22.
 */
public class SpdyChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final SslContext context;

    public SpdyChannelInitializer(SslContext context){
        this.context = context;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        /*SSLEngine engine = context.createSSLEngine();
        engine.setUseClientMode(false);
        NextProtoNego.put(engine, new DefaultServerProvider());
        NextProtoNego.debug = true;*/

        pipeline.addLast("sslHandler", context.newHandler(ch.alloc()));
        pipeline.addLast("chooser",new DefaultSpdyOrHttpChooser(1024 * 1024));
    }
}
