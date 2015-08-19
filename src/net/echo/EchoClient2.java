package net.echo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class EchoClient2 {

	public static void main(String[] args) {

		try {
			Socket client = new Socket("localhost", EchoServer2.PORT);
			BufferedReader inputInfo = new BufferedReader(
					new InputStreamReader(System.in));// 用于接收从键盘输入的信息
			BufferedReader serverInfo = new BufferedReader(
					new InputStreamReader(client.getInputStream()));// 用于接收从服务器返回的信息
			PrintStream out = new PrintStream(client.getOutputStream());

			boolean flag = true;
			while (flag) {
				System.out.print("输入信息：");
				// 注意调用的是println方法，如果是print方法，在键盘输入信息按回车键后不会输出回应信息
				String info = inputInfo.readLine();
				out.println(info);

				if ("bye".equals(info)) {
					flag = false;
				} else {
					System.out.println(serverInfo.readLine());
				}
			}

			serverInfo.close();
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
