package thread.apply;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

//调用IO的写方法，其实是把数据先缓存到内存中，在某一时刻才会写到存储设备中。
/**
 * 多线程下载器
 */
public class MulThreadDownload {
	private String downloadPath;
	private int threads;

	public MulThreadDownload(String path, int threadSize) {
		this.downloadPath = path;
		this.threads = threadSize;
	}

	private String getFileNamefromPath(String path) {
		return path.substring(path.lastIndexOf('/') + 1);
	}

	public void download() throws Exception {
		URL url = new URL(downloadPath);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5 * 1000);

		int fileLen = conn.getContentLength();// 获取下载文件的长度
		File file = new File(getFileNamefromPath(downloadPath));// 根据路径中的文件名在本地创建新的文件用于保存数据
		RandomAccessFile raf = new RandomAccessFile(file, "rwd");// rwd：rw表示可读写，d表示数据立刻写到存储设备中
		raf.setLength(fileLen);// 设置新创建的文件的大小和要下载的文件一样
		raf.close();

		int block = fileLen % threads == 0 ? fileLen / threads : fileLen
				/ threads + 1;// 计算每条线程下载应下载的数据长度

		// 开启线程下载数据
		for (int threadid = 0; threadid < threads; threadid++) {
			new Thread(new MulThreadDownload.DownloadThread(url, file, block,
					threadid)).start();
		}

	}

	public static void main(String[] args) {
		try {
			new MulThreadDownload(
					"http://192.168.63.66:8080/NetForAndroid/AdobeReader.exe",
					3).download();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final class DownloadThread implements Runnable {
		private URL url;
		private File file;
		private int block;
		private int threadid;

		public DownloadThread(URL url, File file, int block, int threadid) {
			this.url = url;
			this.file = file;
			this.block = block;
			this.threadid = threadid;
		}

		@Override
		public void run() {
			try {
				int startposition = threadid * block;// 线程下载的开始位置
				int endposition = (threadid + 1) * block - 1;// 线程下载的结束位置

				RandomAccessFile raf = new RandomAccessFile(file, "rwd");
				raf.seek(startposition);// 设置从文件的哪个位置写入数据

				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(5 * 1000);
				conn.setRequestProperty("Range", "bytes=" + startposition + "-"
						+ endposition);// Range:请求头字段，用于指定从哪个位置开始到哪个位置结束

				InputStream inputStream = conn.getInputStream();
				byte[] buffer = new byte[1024 * 8];
				int len = 0;
				while ((len = inputStream.read(buffer)) != -1) {
					raf.write(buffer, 0, len);
				}

				inputStream.close();
				raf.close();

				System.out.println("Thread--" + threadid
						+ ", download finish!!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
