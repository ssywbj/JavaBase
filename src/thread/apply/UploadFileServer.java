package thread.apply;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
	private ExecutorService executorService;// 线程池
	private int port;
	private boolean quit;
	private ServerSocket server;
	private Map<Long, FileLog> datas = new HashMap<Long, FileLog>();// 存放断点数据

	public UploadFileServer(int port) {
		this.port = port;
		executorService = Executors.newFixedThreadPool(Runtime.getRuntime()
				.availableProcessors() * 50);
	}

	public void quit() {
		this.quit = true;
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void start() throws Exception {
		server = new ServerSocket(port);// 在某个端口进行监听
		while (!quit) {
			Socket socker = server.accept();
			executorService.execute(new SocketTask(socker));
		}
	}

	final class SocketTask implements Runnable {
		private Socket socket;

		public SocketTask(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				System.out.println("accepted connection "
						+ socket.getInetAddress() + ":" + socket.getPort());
				PushbackInputStream inStream = new PushbackInputStream(
						socket.getInputStream());
				String head = readLine(inStream);
				System.out.println("head = " + head);
				if (head != null && !"".equals(head)) {
					String[] items = head.split(";");
					String filelength = items[0].substring(items[0]
							.indexOf("=") + 1);
					String filename = items[1]
							.substring(items[1].indexOf("=") + 1);
					String sourceid = items[2]
							.substring(items[2].indexOf("=") + 1);
					long id = System.currentTimeMillis();
					FileLog log = null;
					if (sourceid != null && !"".equals(sourceid)) {
						id = Long.valueOf(sourceid);
						log = find(id);
					}
					File file = null;
					int position = 0;
					if (log == null) {// 如果上传的文件不存在上传记录，就为文件添加跟踪记录
						String path = new SimpleDateFormat(
								"yyyy/MM/dd/HH/mm").format(new Date());
						File dir = new File("file/" + path);
						if (!dir.exists()) {
							dir.mkdir();
						}
						
						file = new File(dir, filename);
						if (file.exists()) {
							filename = filename.substring(0,
									filename.indexOf(".") - 1)
									+ dir.listFiles().length + filename;
							file = new File(dir, filename);
						}
						save(id, file);
					} else {// 如果上传的文件存在上传记录，就读取上次的断点位置
						file = new File(log.getPath());
						if (file.exists()) {
							File logFile = new File(file.getParentFile(),
									file.getName() + ".log");
							if (logFile.exists()) {
								Properties properties = new Properties();
								properties.load(new FileInputStream(logFile));
								position = Integer.valueOf(properties
										.getProperty("length".trim()));
							}
						}
					}

					// 服务器收到客户端的请求信息后，向客户客户端返回响应信息。
					OutputStream outStream = socket.getOutputStream();
					String response = "sourceid=" + id + ";position="
							+ position + "\r\n".trim();
					outStream.write(response.getBytes());

					RandomAccessFile fileOutStream = new RandomAccessFile(file,
							"rwd");
					if (position == 0) {
						fileOutStream.setLength(Integer.valueOf(filelength));
					}
					fileOutStream.seek(position);

					byte[] buffer = new byte[1024 * 2];
					int len = -1;
					int length = position;
					while ((len = inStream.read(buffer)) != -1) {
						fileOutStream.write(buffer, 0, len);
						length += len;
						Properties properties = new Properties();
						properties.put("length", String.valueOf(length));
						FileOutputStream logFile = new FileOutputStream(
								new File(file.getParentFile(), file.getName()));
						properties.store(logFile, null);// 实时记录文件的最后保存位置
						logFile.close();
					}

					if (length == fileOutStream.length()) {
						delete(id);
					}

					fileOutStream.close();
					inStream.close();
					outStream.close();
					file = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private void delete(Long sourceid) {
			if (datas.containsKey(sourceid)) {
				datas.remove(sourceid);
			}
		}

		private FileLog find(Long sourceid) {
			return datas.get(sourceid);
		}

		public void save(Long id, File saveFile) {
			datas.put(id, new FileLog(id, saveFile.getAbsolutePath()));
		}

		/**
		 * 从输入流中读取第一行数据
		 */
		private String readLine(PushbackInputStream in) throws IOException {
			char buf[] = new char[128];
			int room = buf.length;
			int offset = 0;
			int c;
			loop: while (true) {
				switch (c = in.read()) {
				case -1:
				case '\n':
					break loop;
				case '\r':
					int c2 = in.read();
					if ((c2 != '\n') && (c2 != -1))
						in.unread(c2);
					break loop;
				default:
					if (--room < 0) {
						char[] lineBuffer = buf;
						buf = new char[offset + 128];
						room = buf.length - offset - 1;
						System.arraycopy(lineBuffer, 0, buf, 0, offset);

					}
					buf[offset++] = (char) c;
					break;
				}
			}
			if ((c == -1) && (offset == 0))
				return null;
			return String.copyValueOf(buf, 0, offset);
		}

	}

	private class FileLog {
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
		try {
			new UploadFileServer(7879).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
