package net.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class BaseDemo {

	private static final String NET_BAI_DU = "www.hao123.com";// 好123网站

	public static void main(String[] args) {
		try {
			InetAddress local = InetAddress.getLocalHost();
			InetAddress remote = InetAddress.getByName(NET_BAI_DU);
			System.out.println("本机IP地址：" + local.getHostAddress());
			System.out.println("主机名/域名：" + local.getHostName());
			System.out.println("本机是否可达：" + local.isReachable(2000));
			System.out.println();
			System.out.println("好123网站IP地址：" + remote.getHostAddress());
			System.out.println("主机名/域名：" + remote.getHostName());

			// 千万要记得加上传输协议和"//"，更要注意是http协议还是https协议，因为协议的不同，可能导致以下的输出内容不同
			URL url = new URL("http://" + NET_BAI_DU);
			URLConnection openConnection = url.openConnection();
			System.out.println("内容类型：" + openConnection.getContentType());
			System.out.println("内容大小：" + openConnection.getContentLength());
			InputStream inputStream = openConnection.getInputStream();// 获取的大小(对我们的程序而言是输入流，而对服务器而言是输出流)
			System.out.println("流的大小：" + inputStream.available());// 获取流的大小

			String keyWork = "Ssywbj苏韦 杰";
			String enc = "UTF-8";
			String encodeString = URLEncoder.encode(keyWork, enc);// 用UTF-8编码
			System.out.println("编码：" + encodeString);
			// 若用UTF-8编码，而解码时用非UTF-8，则就会出现乱码的情况，即用哪种编码格式编码，解码时就一定要用那种格式解码
			System.out.println("解码：" + URLDecoder.decode(encodeString, enc));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 解压,处理下载的zip工具包文件
	 * 
	 * @param directory
	 *            要解压到的目录
	 * @param zip
	 *            工具包文件
	 * @throws Exception
	 *             操作失败时抛出异常
	 */
	@SuppressWarnings("resource")
	public static void unzipFile(String directory, File zip) throws Exception {
		try {
			ZipInputStream zis = new ZipInputStream(new FileInputStream(zip));
			ZipEntry ze = zis.getNextEntry();
			File parent = new File(directory);
			if (!parent.exists() && !parent.mkdirs()) {
				throw new Exception("创建解压目录 \"" + parent.getAbsolutePath()
						+ "\" 失败");
			}
			while (ze != null) {
				String name = ze.getName();
				File child = new File(parent, name);
				FileOutputStream output = new FileOutputStream(child);
				byte[] buffer = new byte[10240];
				int bytesRead = 0;
				while ((bytesRead = zis.read(buffer)) > 0) {
					output.write(buffer, 0, bytesRead);
				}
				output.flush();
				output.close();
				ze = zis.getNextEntry();
			}
			zis.close();
		} catch (IOException e) {
		}
	}

}
