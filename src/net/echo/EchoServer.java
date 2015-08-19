package net.echo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Echo程序是网络编程通信交互的一个经典案例，称为回应程序，即客户端输入哪些内容，服务器端会在这些内容前加上“ECHO:”并将信息回送给客户端。
 */
public class EchoServer {

	public static final int PORT = 7150;

	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(PORT);
			Socket client = null;
			BufferedReader buf = null;
			PrintStream out = null;

			boolean flag = true;
			while (flag) {// 无限制地接收客户端连接；注意while(flag)和直接写成while(true)两种写法的区别
				System.out.println("服务器正在运行，等待客户端连接......");
				client = server.accept();
				buf = new BufferedReader(new InputStreamReader(
						client.getInputStream()));// 获取客户端的输入信息
				out = new PrintStream(client.getOutputStream());// 向客户端输出信息

				boolean f = true;
				while (f) {// 注意加此处加与不加while的区别
					String source = buf.readLine();
					if (source == null || "".equals(source)) {
						f = false;
					} else {
						if ("bye".equals(source)) {
							f = false;
						} else {
							// 注意调用的是println方法，如果是print方法，在键盘输入信息按回车键后不会自动换行也不会输出回应信息，达不程序运行的目的
							out.println("ECHO:" + source);
						}
					}
				}

				// 先在此处关闭这两个流
				out.close();
				client.close();
			}

			/*
			 * 若直接写成while(true)，则以下代码的出现将不符合语法，程序不能通过编译，因为程序进入永远进入了死循环状态，不会往下执行；
			 * 而通过变量的形式加入判断，因为程序有可能在其他地方改写变量的值，使循环结束，从而执行下面的代码。
			 */
			// 最后在此处关闭这两个流
			server.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
//存在的问题：服务器端每次只能有一个用户连接，其他客户端无法同时连接服务器，要等待服务器出现空闲才可以连接，这实际止就属于单线程处理机制。
