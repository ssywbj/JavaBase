package net.echo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 为了解决单线程机制带来的问题，我们引入多线程处理机制，即每一个客户端连接之后都启动一个线程，这样就可以保证一个服务器可以同时与多个客户端同时通信。
 */
public class EchoServer2 implements Runnable {

	public static final int PORT = 7157;
	private Socket client;

	public EchoServer2(Socket client) {
		this.client = client;
	}

	// 在服务器端的代码中把对客户端的操作放在线程类中
	@Override
	public void run() {
		BufferedReader buf = null;
		PrintStream out = null;

		try {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(PORT);
			Socket client = null;

			boolean flag = true;
			while (flag) {// 无限制地接收客户端连接；注意while(flag)和直接写成while(true)两种写法的区别
				System.out.println("服务器正在运行，等待客户端连接......");
				client = server.accept();
				new Thread(new EchoServer2(client)).start();// 开启子线程与客户端交互信息
			}

			/*
			 * 若直接写成while(true)，则以下代码的出现将不符合语法，程序不能通过编译，因为程序进入永远进入了死循环状态，不会往下执行；
			 * 而通过变量的形式加入判断，因为程序有可能在其他地方改写变量的值，使循环结束，从而执行下面的代码。
			 */
			server.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}