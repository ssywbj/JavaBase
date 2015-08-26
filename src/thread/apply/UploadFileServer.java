package thread.apply;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UploadFileServer {
	public static final int PORT = 7880;// 监听端口
	public static final int BUFFER_TIME = 4;
	private ExecutorService executorService;// 线程池
	private boolean quit;
	private ServerSocket server;
	private Map<Long, FileLog> datas = new HashMap<Long, FileLog>();// 存放断点数据

	public UploadFileServer() {
		executorService = Executors.newFixedThreadPool(Runtime.getRuntime()
				.availableProcessors() * 50);// 创建线程池，池中共有(CPU个数*50)条线程
	}

	/**
	 * 关闭服务器
	 */
	public void quit() throws Exception {
		this.quit = true;
		server.close();
	}

	/**
	 * 启动服务器
	 */
	public void start() throws Exception {
		server = new ServerSocket(PORT);// 服务器在某个端口进行监听
		while (!quit) {
			Socket socket = server.accept();
			executorService.execute(new SocketTask(socket));
		}
	}

	private final class SocketTask implements Runnable {
		private Socket socket;

		public SocketTask(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				System.out.println("accepted connection-->"
						+ socket.getInetAddress() + ":" + socket.getPort());

				// 获取客户端发送过来的协议数据：Content-Length=xxx;filename=xxx.3gp;sourceid=xxx\r\n......
				PushbackInputStream inStream = new PushbackInputStream(
						socket.getInputStream());
				String head = StreamTool.readLine(inStream);// 提取第一行数据，即数据头部分数据
				System.out.println("head = " + head);

				if (head != null && !"".equals(head)) {// 获取数据头部分的各个数据
					String[] items = head.split(";");
					String filelength = items[0].substring(items[0]
							.indexOf("=") + 1);
					String filename = items[1]
							.substring(items[1].indexOf("=") + 1);
					String sourceid = items[2]
							.substring(items[2].indexOf("=") + 1);

					long id = System.currentTimeMillis();
					FileLog log = null;
					if (sourceid != null && !"".equals(sourceid)) {// 如果是初次上传，sourceid为空
						id = Long.valueOf(sourceid);
						log = find(id);// 查找要上传的文件是否存在上传记录
					}

					File file = null;// 生成本地文件文件，保存客户端上传的文件
					int position = 0;
					if (log == null) {// 如果是初次上传
						String path = new SimpleDateFormat("yyyy-MM-dd'T'HH_mm_ss")
								.format(new Date());
						File dir = new File("file/" + path);
						if (!dir.exists()) {
							dir.mkdirs();// 依次建立多个目录
						}
						file = new File(dir, filename);

						if (file.exists()) {// 如果上传的文件发生重名， 则对文件进行更名操作
							filename = filename.substring(0,
									filename.indexOf(".") - 1)
									+ dir.listFiles().length
									+ filename.substring(filename.indexOf("."));
							file = new File(dir, filename);
						}
						save(id, file);
					} else {// 如果不是初次上传，就读取上次的断点位置
						file = new File(log.getPath());
						if (file.exists()) {
							File logFile = new File(file.getParentFile(),
									file.getName() + ".log");
							if (logFile.exists()) {
								Properties properties = new Properties();
								properties.load(new FileInputStream(logFile));
								position = Integer.valueOf(properties
										.getProperty("length".trim()));// 读取断点位置
							}
						}
					}

					// 向客户端返回响应信息
					OutputStream outStream = socket.getOutputStream();
					String response = "sourceid=" + id + ";position="
							+ position + "\r\n";
					outStream.write(response.getBytes());

					RandomAccessFile raf = new RandomAccessFile(file, "rwd");
					if (position == 0) {// 初次上传要先设置本地文件的大小
						raf.setLength(Integer.valueOf(filelength));
					}
					raf.seek(position);// 从指定位置写入数据

					// 开始保存客户端上传的数据
					byte[] buffer = new byte[1024 * BUFFER_TIME];
					int len = -1;
					int length = position;
					while ((len = inStream.read(buffer)) != -1) {
						raf.write(buffer, 0, len);
						length += len;
						Properties properties = new Properties();
						properties.put("length", String.valueOf(length));
						FileOutputStream logFile = new FileOutputStream(
								new File(file.getParentFile(), file.getName()
										+ ".log"));
						properties.store(logFile, null);// 实时记录文件的最后保存位置
						logFile.close();
					}

					if (length == raf.length()) {
						delete(id);
					}

					raf.close();
					outStream.close();
					inStream.close();
					if (socket != null && !socket.isClosed()) {
						socket.close();
					}
					file = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 当文件上传完毕，删除记录
	 */
	private void delete(Long sourceid) {
		if (datas.containsKey(sourceid)) {
			datas.remove(sourceid);
		}
	}

	/**
	 * 查找是否存在上传记录
	 */
	private FileLog find(Long sourceid) {
		return datas.get(sourceid);
	}

	/**
	 * 保存上传记录
	 */
	public void save(Long id, File saveFile) {
		datas.put(id, new FileLog(id, saveFile.getAbsolutePath()));
	}

	private final class FileLog {
		private Long id;
		private String path;

		public FileLog(Long id, String path) {
			this.id = id;
			this.path = path;
		}

		public String getPath() {
			return path;
		}

		@Override
		public String toString() {
			return "FileLog [id=" + id + ", path=" + path + "]";
		}
	}

	public static void main(String[] args) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					new UploadFileServer().start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

}
