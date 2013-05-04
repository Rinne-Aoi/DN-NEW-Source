package angeloid.dreamnarae;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.content.Context;
import android.util.Log;

public class DownloadProcess {

	static URL finalURL;
	static HttpURLConnection checklogic;
	public static Context Context;
	public static boolean isDownloadInProgress;
	public static boolean isLowOnMemory;
	public static boolean networkstate = false;
	public static String BASE_FOLDER;

	// Server Link
	static URL url1;
	static URL url2;

	public static void URLCheck(Context c) throws MalformedURLException {
		try {
			url1 = new URL("http://gecp.kr/dn/dn2.1f.zip");
			checklogic = (HttpURLConnection) url1.openConnection();
			checklogic.setConnectTimeout(3000);
			checklogic.setReadTimeout(3000);
			checklogic.connect();
			if (!(checklogic.getResponseCode() == 200)) {
				Log.d("Download Process", "Server Failed!");
				URLCheck2(c);
			} else {
				Log.d("Download Process", "Server Clear!");
				finalURL = url1;
				startdownload(c);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void URLCheck2(Context c) throws MalformedURLException {
		try {
			url2 = new URL("http://sirospace.info/dn2f.zip");
			checklogic = (HttpURLConnection) url2.openConnection();
			checklogic.setConnectTimeout(3000);
			checklogic.setReadTimeout(3000);
			checklogic.connect();
			if (!(checklogic.getResponseCode() == 200)) {
				Log.d("Download Process", "Server Failed!");
				// 세번째 서버
			} else {
				Log.d("Download Process", "Server Clear!");
				finalURL = url2;
				startdownload(c);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void startdownload(Context cxnt) {
		Context = cxnt;
		BASE_FOLDER = Context.getFilesDir().getPath();
		isLowOnMemory = false;
		new RunUnZipThread().start();
	}

	private static class RunUnZipThread extends Thread {
		@Override
		public void run() {
			DownloadProcess.isDownloadInProgress = true;
			URLConnection urlConnection;
			try {
				urlConnection = finalURL.openConnection();
				ZipInputStream ZIStream = new ZipInputStream(
						urlConnection.getInputStream());

				for (ZipEntry zipEntry = ZIStream.getNextEntry(); zipEntry != null; zipEntry = ZIStream
						.getNextEntry()) {

					String innerFileName = BASE_FOLDER + File.separator
							+ zipEntry.getName();
					File innerFile = new File(innerFileName);

					if (innerFile.exists()) {
						innerFile.delete();
					}

					if (zipEntry.isDirectory()) {
						innerFile.mkdirs();
					} else {
						FileOutputStream outputStream = new FileOutputStream(
								innerFileName);
						final int BUFFER_SIZE = 2048;

						BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
								outputStream, BUFFER_SIZE);

						int count = 0;
						byte[] buffer = new byte[BUFFER_SIZE];
						while ((count = ZIStream.read(buffer, 0, BUFFER_SIZE)) != -1) {
							bufferedOutputStream.write(buffer, 0, count);
						}

						bufferedOutputStream.flush();
						bufferedOutputStream.close();
					}

					ZIStream.closeEntry();
				}

				ZIStream.close();
			} catch (IOException e) {
				if (e.getMessage().equalsIgnoreCase("No space left on device")) {
					DownloadProcess.isLowOnMemory = true;
				}
				e.printStackTrace();
			}
			DownloadProcess.isDownloadInProgress = false;
		}
	};

}
