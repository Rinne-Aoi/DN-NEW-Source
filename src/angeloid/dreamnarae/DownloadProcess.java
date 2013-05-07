/* Download Files DreamNarae Server
 * on the 5~10 Servers!
 * 
 * Edit : So/piane(www.sirospace.com) in Angeloid Team 
 */

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
import android.widget.Toast;

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
	static URL url3;
	static URL url4;
	static URL url5;
	static URL url6;
	static URL url7;
	static URL url8;
	static URL url9;
	static URL url10;
	
	// TODO 다중서버(최대한 많은 직 링크 서버를 넣는다. 최대한 많이-!)
	public static void URLCheck(Context c) throws MalformedURLException {
		try {
			url1 = new URL("http://gecp.kr/dn/dn2.1f.zip");
			checklogic = (HttpURLConnection) url1.openConnection();
			checklogic.setConnectTimeout(3000);
			checklogic.setReadTimeout(3000);
			checklogic.connect();
			if (!(checklogic.getResponseCode() == 200)) {
				Toast.makeText(c, R.string.downloadfailed, Toast.LENGTH_SHORT)
						.show();
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
				Toast.makeText(c, R.string.downloadfailed, Toast.LENGTH_SHORT)
						.show();
				Log.d("Download Process", "Server Failed!");
				URLCheck3(c);
			} else {
				Log.d("Download Process", "Server Clear!");
				finalURL = url2;
				startdownload(c);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void URLCheck3(Context c) throws MalformedURLException {
		try {
			url3 = new URL("");
			checklogic = (HttpURLConnection) url3.openConnection();
			checklogic.setConnectTimeout(3000);
			checklogic.setReadTimeout(3000);
			checklogic.connect();
			if (!(checklogic.getResponseCode() == 200)) {
				Toast.makeText(c, R.string.downloadfailed, Toast.LENGTH_SHORT)
						.show();
				Log.d("Download Process", "Server Failed!");
				URLCheck4(c);
			} else {
				Log.d("Download Process", "Server Clear!");
				finalURL = url3;
				startdownload(c);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void URLCheck4(Context c) throws MalformedURLException {
		try {
			url4 = new URL("");
			checklogic = (HttpURLConnection) url4.openConnection();
			checklogic.setConnectTimeout(3000);
			checklogic.setReadTimeout(3000);
			checklogic.connect();
			if (!(checklogic.getResponseCode() == 200)) {
				Toast.makeText(c, R.string.downloadfailed, Toast.LENGTH_SHORT)
						.show();
				Log.d("Download Process", "Server Failed!");
				URLCheck5(c);
			} else {
				Log.d("Download Process", "Server Clear!");
				finalURL = url4;
				startdownload(c);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void URLCheck5(Context c) throws MalformedURLException {
		try {
			url5 = new URL("");
			checklogic = (HttpURLConnection) url5.openConnection();
			checklogic.setConnectTimeout(3000);
			checklogic.setReadTimeout(3000);
			checklogic.connect();
			if (!(checklogic.getResponseCode() == 200)) {
				Toast.makeText(c, R.string.downloadfailed, Toast.LENGTH_SHORT)
						.show();
				Log.d("Download Process", "Server Failed!");
				URLCheck6(c);
			} else {
				Log.d("Download Process", "Server Clear!");
				finalURL = url5;
				startdownload(c);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void URLCheck6(Context c) throws MalformedURLException {
		try {
			url6 = new URL("");
			checklogic = (HttpURLConnection) url6.openConnection();
			checklogic.setConnectTimeout(3000);
			checklogic.setReadTimeout(3000);
			checklogic.connect();
			if (!(checklogic.getResponseCode() == 200)) {
				Toast.makeText(c, R.string.downloadfailed, Toast.LENGTH_SHORT)
						.show();
				Log.d("Download Process", "Server Failed!");
				URLCheck7(c);
			} else {
				Log.d("Download Process", "Server Clear!");
				finalURL = url6;
				startdownload(c);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void URLCheck7(Context c) throws MalformedURLException {
		try {
			url7 = new URL("");
			checklogic = (HttpURLConnection) url7.openConnection();
			checklogic.setConnectTimeout(3000);
			checklogic.setReadTimeout(3000);
			checklogic.connect();
			if (!(checklogic.getResponseCode() == 200)) {
				Toast.makeText(c, R.string.downloadfailed, Toast.LENGTH_SHORT)
						.show();
				Log.d("Download Process", "Server Failed!");
				URLCheck8(c);
			} else {
				Log.d("Download Process", "Server Clear!");
				finalURL = url7;
				startdownload(c);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void URLCheck8(Context c) throws MalformedURLException {
		try {
			url8 = new URL("");
			checklogic = (HttpURLConnection) url8.openConnection();
			checklogic.setConnectTimeout(3000);
			checklogic.setReadTimeout(3000);
			checklogic.connect();
			if (!(checklogic.getResponseCode() == 200)) {
				Toast.makeText(c, R.string.downloadfailed, Toast.LENGTH_SHORT)
						.show();
				Log.d("Download Process", "Server Failed!");
				URLCheck9(c);
			} else {
				Log.d("Download Process", "Server Clear!");
				finalURL = url8;
				startdownload(c);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void URLCheck9(Context c) throws MalformedURLException {
		try {
			url9 = new URL("");
			checklogic = (HttpURLConnection) url9.openConnection();
			checklogic.setConnectTimeout(3000);
			checklogic.setReadTimeout(3000);
			checklogic.connect();
			if (!(checklogic.getResponseCode() == 200)) {
				Toast.makeText(c, R.string.downloadfailed, Toast.LENGTH_SHORT)
						.show();
				Log.d("Download Process", "Server Failed!");
				URLCheck10(c);
			} else {
				Log.d("Download Process", "Server Clear!");
				finalURL = url9;
				startdownload(c);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void URLCheck10(Context c) throws MalformedURLException {
		try {
			url10 = new URL("");
			checklogic = (HttpURLConnection) url10.openConnection();
			checklogic.setConnectTimeout(3000);
			checklogic.setReadTimeout(3000);
			checklogic.connect();
			if (!(checklogic.getResponseCode() == 200)) {
				Toast.makeText(c, R.string.downloadfailed, Toast.LENGTH_SHORT)
						.show();
				Log.d("Download Process", "Server Failed!");
				URLCheck(c);
			} else {
				Log.d("Download Process", "Server Clear!");
				finalURL = url10;
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
