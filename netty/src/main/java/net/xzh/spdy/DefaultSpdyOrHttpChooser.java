package net.xzh.spdy;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.spdy.*;
import io.netty.handler.ssl.ApplicationProtocolNames;
import io.netty.handler.ssl.ApplicationProtocolNegotiationHandler;

/**
 * netty in action 一书中 使用的是 SpdyOrHttpChooser 。然后netty 4 中没有此类。
 * 经过查询，github上有如下备注：
 * SpdyOrHttpChooser and Http2OrHttpChooser duplicate fair amount code with each other
 *  - Replace SpdyOrHttpChooser and Http2OrHttpChooser with ApplicationProtocolNegotiationHandler
 *  - Add ApplicationProtocolNames to define the known application-level protocol names
 * 所以使用  ApplicationProtocolNegotiationHandler
 * 2018/11/22.
 */
public class DefaultSpdyOrHttpChooser extends ApplicationProtocolNegotiationHandler {

    int MAX_CONTENT_LENGTH;

    public DefaultSpdyOrHttpChooser(int maxContentLength){
        super(ApplicationProtocolNames.HTTP_1_1);
        this.MAX_CONTENT_LENGTH = maxContentLength;
    }

    @Override
    protected void configurePipeline(ChannelHandlerContext ctx, String protocol) throws Exception {
        if(ApplicationProtocolNames.HTTP_1_1.equals(protocol)) {
            configureHttp1(ctx);
            return;
        }

        if(ApplicationProtocolNames.SPDY_3_1.equals(protocol)) {
            configureSpdy(ctx, SpdyVersion.SPDY_3_1);
        }
    }

    private  void configureHttp1(ChannelHandlerContext ctx) throws Exception {
        ChannelPipeline p = ctx.pipeline();
        p.addLast(new HttpServerCodec());
        p.addLast(new HttpObjectAggregator(MAX_CONTENT_LENGTH));
        p.addLast(new HttpRequestHandler());
    }

    private  void configureSpdy(ChannelHandlerContext ctx, SpdyVersion version) throws Exception {
        ChannelPipeline p = ctx.pipeline();
        p.addLast(new SpdyFrameCodec(version));
        p.addLast(new SpdySessionHandler(version, true));
        p.addLast(new SpdyHttpEncoder(version));
        p.addLast(new SpdyHttpDecoder(version, MAX_CONTENT_LENGTH));
        p.addLast(new SpdyHttpResponseStreamIdHandler());
        p.addLast(new SpdyRequestHandler());
    }

}
