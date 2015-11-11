package net.tcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 第一个TCP程序。在Java中使用Socket(即套接字)完成TCP程序的开发，使用此类可以方便地建立可靠的、双向的、持续的、点对点的通信连接。
 * 在TCP程序的开发中，服务器端使用ServerSocket等待客户端的连接，每一个客户端使用Socket对象表示。
 */
public class TCPClient {

	public static void main(String[] args) {
		try {
			// 在开发中，经常使用127.0.0.1表示本机的IP地址或使用localhost表示本机
			Socket client = new Socket("localhost", 7979);
			// 在客户端可以使用可以使用Socket的getInputStream方法取得服务器的输出信息
			BufferedReader buf = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
			String info = buf.readLine();

			System.out.println("服务器端输出的内容：" + info);

			buf.close();
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
// 以上的程序需要编写两套代码(TCPServer和TCPClient)才可以完成网络程序的开发，实际上这就表示C/S(客户端/服务器)架构，一般这种模式需要同时维护两套代码，而B/S(浏览器/服务器)架构只需要维护一套代码。