package net.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Test implements Runnable {
	int max = 10; // 最大开启线程数
	int i = 0; // 回复数字
	int temp;
	ServerSocket serverSocket;
	Socket socket[];

	public Test() {
		try {
			serverSocket = new ServerSocket(5648); // 在5648端口进行侦听
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("can't initate ServerSocket!");
			return;
		}

		socket = new Socket[max];

		System.out.println("waiting for connect");
		try {
			while ((socket[i] = serverSocket.accept()) != null) {
				temp = i;
				i++;
				new Thread(this).start(); // 每侦听到一个客户端的连接，就会开启一个工作线程
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Test();
	}

	@Override
	public void run() {
		Socket sk = socket[temp];
		System.out.println("accept:" + sk.getInetAddress().getHostAddress());
		InputStream is = null;
		OutputStream os = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		try {
			is = sk.getInputStream();
			os = sk.getOutputStream();
			br = new BufferedReader(new InputStreamReader(is));
			pw = new PrintWriter(os);
		} catch (IOException e) {
			e.printStackTrace();
			try {
				sk.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return;
		}
		String str;
		try {
			int m = 0;
			while ((str = br.readLine()) != null) {
				System.out.println(str);
				pw.println(m);
				pw.flush();
				m++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}