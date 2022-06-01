package net.xzh.spdy;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.ApplicationProtocolConfig;
import io.netty.handler.ssl.ApplicationProtocolNames;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import java.net.InetSocketAddress;

/**
 * 2018/11/22.
 */
public class SpdyServer {

    private final NioEventLoopGroup group = new NioEventLoopGroup();
    private final SslContext context;
    private Channel channel ;

    public SpdyServer(SslContext context){
        this.context = context;
    }


    public ChannelFuture start(InetSocketAddress address){
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(group)
                .channel(NioServerSocketChannel.class)
                .childHandler(new SpdyChannelInitializer(context));
        ChannelFuture future = bootstrap.bind(address);
        future.syncUninterruptibly();
        channel = future.channel();
        return future;
    }

    public void destory(){
        if(channel != null) {
            channel.close();
        }
        group.shutdownGracefully();
    }

    public static void main(String[] args) throws Exception {
        int port = 8081;
        // Configure SSL.
        SelfSignedCertificate ssc = new SelfSignedCertificate();
        SslContext sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey())
                .applicationProtocolConfig(new ApplicationProtocolConfig(
                        ApplicationProtocolConfig.Protocol.NPN,
                        // NO_ADVERTISE is currently the only mode supported by both OpenSsl and JDK providers.
                        ApplicationProtocolConfig.SelectorFailureBehavior.NO_ADVERTISE,
                        // ACCEPT is currently the only mode supported by both OpenSsl and JDK providers.
                        ApplicationProtocolConfig.SelectedListenerFailureBehavior.ACCEPT,
                        ApplicationProtocolNames.SPDY_3_1,
                        ApplicationProtocolNames.HTTP_1_1))
                .build();

        final SpdyServer server = new SpdyServer(sslCtx);

        ChannelFuture future = server.start(new InetSocketAddress(port));

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run(){
                server.destory();
            }
        });
        future.channel().closeFuture().syncUninterruptibly();
    }

}
