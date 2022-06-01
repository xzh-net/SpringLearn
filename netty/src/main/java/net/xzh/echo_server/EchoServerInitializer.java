package net.xzh.echo_server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * 2018/11/14.
 */
public class EchoServerInitializer extends ChannelInitializer<Channel>{
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new LineBasedFrameDecoder(1024));// 基于分隔符 \r\n 的数据帧解码。
        pipeline.addLast(new EchoServerHandler());
    }
}
