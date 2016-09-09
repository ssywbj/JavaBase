package framework.netty;

import java.net.InetAddress;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class HelloServerHandler extends SimpleChannelInboundHandler<String> {

	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext,
			String s) throws Exception {
		System.out.println("客户端："
				+ channelHandlerContext.channel().remoteAddress() + " -->" + s);// 收到客户端的消息
		channelHandlerContext.writeAndFlush(s + "(ECHO)" + "\n"); // 向客户端返回消息
	}

	/**
	 * 建立连接时调用该方法
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		System.out.println("连接-->" + ctx.channel().remoteAddress());
		ctx.writeAndFlush("Welcome To "+ InetAddress.getLocalHost().getHostName() + "\n");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		System.out.println("断开-->" + ctx.channel().remoteAddress());
	}

}
