package framework.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HelloServer {
    public static final int PORT_NUMBER = 7978;

    public static void main(String args[]) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup);
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new HelloServerInitializer());

            ChannelFuture f = b.bind(PORT_NUMBER).sync();// 服务器绑定端口监听
            f.channel().closeFuture().sync();// 监听服务器关闭监听
            /* b.bind(portNumber).sync().channel().closeFuture().sync(); */
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
