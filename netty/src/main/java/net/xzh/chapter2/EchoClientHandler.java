package net.xzh.chapter2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * 代码清单 2-3 客户端的 ChannelHandler
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
@Sharable
//标记该类的实例可以被多个 Channel 共享
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
	// 这里使用了SimpleChannelInboundHandler 而不是 ChannelInboundHandlerAdapter
	// 区别在于 SimpleChannelInboundHandler会主动释放资源（本例中的ByteBuf
	// in），而ChannelInboundHandlerAdapter不会。
	// 其实前者是继承自后者，然后复写了channelRead方法，并在channelRead方法中调用了channelRead0方法，然后释放资源（这些资源实现了ReferenceCounted接口）。
	// channelRead0方法其实是使用了模板方法设计模式。
	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		// 当被通知 Channel是活跃的时候，发送一条消息
		ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, ByteBuf in) {
		// 记录已接收消息的转储
		System.out.println(" Client received : " + ByteBufUtil.hexDump(in.readBytes(in.readableBytes())));
	}

	@Override
	// 在发生异常时，记录错误并关闭Channel
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
