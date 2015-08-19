package net.tcp;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 第一个TCP程序。在Java中使用Socket(即套接字)完成TCP程序的开发，使用此类可以方便地建立可靠的、双向的、持续的、点对点的通信连接。
 * 在TCP程序的开发中，服务器端使用ServerSocket等待客户端的连接，每一个客户端使用Socket对象表示。
 */
public class TCPServer {

	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(7979);
			System.out.println("服务器正在运行，等待客户端连接......");
			// 服务器使用accept方法等待客户端的连接，此方法执行之后服务器端将进入阻塞状态，直到客户端连接之后才能向下执行。
			Socket client = server.accept();
			// 在服务器端可以通过Socket类的getOutputStream方法取得客户端的输出信息
			PrintStream printStream = new PrintStream(client.getOutputStream());// 通过客户端的OutputStream实例化PrintStream，因为使PrintStream具有向客户端输出信息的功能。
			printStream.print("Hello TCP!!");

			printStream.close();
			client.close();
			server.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}