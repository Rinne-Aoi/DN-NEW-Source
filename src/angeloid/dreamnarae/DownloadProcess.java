package angeloid.dreamnarae;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.content.Context;

public class DownloadProcess {
	private static String BASE_FOLDER;
	public static Context Context;
	public static boolean isDownloadInProgress;
	public static boolean isLowOnMemory;
	public static boolean networkstate = false;
	

	public static void startdownload(Context c) {
		Context = c;
		DownloadProcess.BASE_FOLDER = DownloadProcess.Context.getFilesDir()
				.getPath();
		DownloadProcess.isLowOnMemory = false;
		new RunUnZipThread().start();
		 
	}
	private static class RunUnZipThread extends Thread {
		@Override
		public void run() {
			DownloadProcess.isDownloadInProgress = true;
			URLConnection urlConnection;
			try {
                // 파일을 다운로드 할 주소 입력, Direct Link만 사용 가능
				URL finalUrl = new URL("http://sirospace.info/dn2f.zip");
				urlConnection = finalUrl.openConnection();

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