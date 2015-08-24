package thread.apply;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.io.RandomAccessFile;
import java.net.Socket;

public class UploadFileClient {

	private static String readLine(PushbackInputStream in) throws IOException {
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

	public static void main(String[] args) {
		try {
			Socket socket = new Socket("127.0.0.1", 7879);

			OutputStream outStream = socket.getOutputStream();
			String fileName = "AdobeReader.exe";
			File file = new File(fileName);
			String head = "Content-length=" + file.length() + ";filename="
					+ fileName + ";sourceid=\r\n";
			outStream.write(head.getBytes());

			PushbackInputStream inStream = new PushbackInputStream(
					socket.getInputStream());
			String response = readLine(inStream);
			System.out.println("reponse = " + response);

			String[] items = response.split(";");
			String position = items[1].substring(items[1].indexOf("=") + 1);
			RandomAccessFile fileOutStream = new RandomAccessFile(file, "r");
			fileOutStream.seek(Integer.valueOf(position));
			byte[] buffer = new byte[1024 * 2];
			int len = -1;
			int i = 0;
			while ((len = fileOutStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
				i++;
			}
			outStream.close();
			fileOutStream.close();
			inStream.close();
			socket.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
