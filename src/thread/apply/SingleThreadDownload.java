package thread.apply;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SingleThreadDownload implements Runnable {
	private String downloadPath;

	public SingleThreadDownload(String downloadPath) {
		this.downloadPath = downloadPath;
	}

	private String getFileNamefromPath(String downloadPath) {
		return downloadPath.substring(downloadPath.lastIndexOf('/') + 1);
	}

	@Override
	public void run() {
		try {
			URL url = new URL(downloadPath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setReadTimeout(1000 * 5);

			InputStream inputStream = conn.getInputStream();
			inputStreamToFile(getFileNamefromPath(downloadPath), inputStream);

			System.out.println("download finished!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void inputStreamToFile(String fileName, InputStream inputStream)
			throws Exception {
		OutputStream outStream = new FileOutputStream(new File(fileName));
		int len = 0;
		byte[] buffer = new byte[1024 * 4];
		while ((len = inputStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		outStream.close();
		inputStream.close();
	}

	public static void main(String[] args) {
		new Thread(new SingleThreadDownload(
				"http://192.168.63.66:8080/NetForAndroid/AdobeReader.exe"))
				.start();
	}

}
