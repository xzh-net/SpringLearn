package net.xzh.spdy;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import static io.netty.handler.codec.http.HttpUtil.is100ContinueExpected;

/**
 *
 * 2018/11/22.
 */
@ChannelHandler.Sharable
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
        // 当  FullHttpRequest  到达的时候，channelRead0方法会被调用。

        // 如果需要的话，就发送一个 100 continue
        if(is100ContinueExpected(req)){
            ctx.write(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE));
        }

        // 200 OK
        FullHttpResponse response = new DefaultFullHttpResponse(req.protocolVersion(),HttpResponseStatus.OK);
        response.content().writeBytes(getContent().getBytes(CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain; charset=UTF-8");

        // keepAlive情况的处理
        boolean keepAlive = HttpUtil.isKeepAlive(req);
        if(keepAlive) {
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,response.content().readableBytes());
            response.headers().set(HttpHeaderNames.CONNECTION,HttpHeaderNames.KEEP_ALIVE);
        }

        ChannelFuture future = ctx.writeAndFlush(response);
        if(!keepAlive) {
            future.addListener(ChannelFutureListener.CLOSE);
        }

    }

    protected String getContent(){
        return " This content is transmitted via HTTP \r\n";
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)throws Exception{
        cause.printStackTrace();
        ctx.close();
    }
}
