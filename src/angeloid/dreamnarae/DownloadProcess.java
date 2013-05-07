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

import android.util.Log;

public class DownloadProcess {

	static URL finalURL;
	static HttpURLConnection checklogic;
	public static boolean isDownloadInProgress;
	public static boolean isLowOnMemory;
	public static boolean networkstate = false;
<<<<<<< HEAD
	public static String BASE_FOLDER;

	// Server Link
	static URL url1;
	static URL url2;

	public static void URLCheck(Context c) throws MalformedURLException {
=======
	public static String BASE_FOLDER = "/data/data/angeloid.dreamnarae/";
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
	
	// TODO ´ÙÁß¼­¹ö(ÃÖ´ëÇÑ ¸¹Àº Á÷ ¸µÅ© ¼­¹ö¸¦ ³Ö´Â´Ù. ÃÖ´ëÇÑ ¸¹ÀÌ-!)
	public static void URLCheck() throws MalformedURLException {
>>>>>>> Last One ISSUE! but Test Device is broken...
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
<<<<<<< HEAD
				startdownload(c);
=======
				checklogic.disconnect();
				startdownload();
>>>>>>> Last One ISSUE! but Test Device is broken...
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
<<<<<<< HEAD
			checklogic.connect();
			if (!(checklogic.getResponseCode() == 200)) {
				Log.d("Download Process", "Server Failed!");
				// ì„¸ë²ˆì§¸ ì„œë²„
			} else {
				Log.d("Download Process", "Server Clear!");
				finalURL = url2;
				startdownload(c);
=======
			checklogic.connect();
			if (!(checklogic.getResponseCode() == 200)) {
				Log.d("Download Process", "Server Failed!");
				URLCheck3();
			} else {
				Log.d("Download Process", "Server Clear!");
				finalURL = url2;
				checklogic.disconnect();
				startdownload();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void URLCheck3() throws MalformedURLException {
		try {
			url3 = new URL("");
			checklogic = (HttpURLConnection) url3.openConnection();
			checklogic.setConnectTimeout(3000);
			checklogic.setReadTimeout(3000);
			checklogic.connect();
			if (!(checklogic.getResponseCode() == 200)) {
				Log.d("Download Process", "Server Failed!");
				URLCheck4();
			} else {
				Log.d("Download Process", "Server Clear!");
				finalURL = url3;
				startdownload();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void URLCheck4() throws MalformedURLException {
		try {
			url4 = new URL("");
			checklogic = (HttpURLConnection) url4.openConnection();
			checklogic.setConnectTimeout(3000);
			checklogic.setReadTimeout(3000);
			checklogic.connect();
			if (!(checklogic.getResponseCode() == 200)) {
				Log.d("Download Process", "Server Failed!");
				URLCheck5();
			} else {
				Log.d("Download Process", "Server Clear!");
				finalURL = url4;
				startdownload();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void URLCheck5() throws MalformedURLException {
		try {
			url5 = new URL("");
			checklogic = (HttpURLConnection) url5.openConnection();
			checklogic.setConnectTimeout(3000);
			checklogic.setReadTimeout(3000);
			checklogic.connect();
			if (!(checklogic.getResponseCode() == 200)) {
				Log.d("Download Process", "Server Failed!");
				URLCheck6();
			} else {
				Log.d("Download Process", "Server Clear!");
				finalURL = url5;
				startdownload();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void URLCheck6() throws MalformedURLException {
		try {
			url6 = new URL("");
			checklogic = (HttpURLConnection) url6.openConnection();
			checklogic.setConnectTimeout(3000);
			checklogic.setReadTimeout(3000);
			checklogic.connect();
			if (!(checklogic.getResponseCode() == 200)) {
				Log.d("Download Process", "Server Failed!");
				URLCheck7();
			} else {
				Log.d("Download Process", "Server Clear!");
				finalURL = url6;
				startdownload();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void URLCheck7() throws MalformedURLException {
		try {
			url7 = new URL("");
			checklogic = (HttpURLConnection) url7.openConnection();
			checklogic.setConnectTimeout(3000);
			checklogic.setReadTimeout(3000);
			checklogic.connect();
			if (!(checklogic.getResponseCode() == 200)) {
				Log.d("Download Process", "Server Failed!");
				URLCheck8();
			} else {
				Log.d("Download Process", "Server Clear!");
				finalURL = url7;
				startdownload();
>>>>>>> Last One ISSUE! but Test Device is broken...
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

<<<<<<< HEAD
	public static void startdownload(Context cxnt) {
		Context = cxnt;
		BASE_FOLDER = Context.getFilesDir().getPath();
=======
	public static void URLCheck8() throws MalformedURLException {
		try {
			url8 = new URL("");
			checklogic = (HttpURLConnection) url8.openConnection();
			checklogic.setConnectTimeout(3000);
			checklogic.setReadTimeout(3000);
			checklogic.connect();
			if (!(checklogic.getResponseCode() == 200)) {
				Log.d("Download Process", "Server Failed!");
				URLCheck9();
			} else {
				Log.d("Download Process", "Server Clear!");
				finalURL = url8;
				startdownload();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void URLCheck9() throws MalformedURLException {
		try {
			url9 = new URL("");
			checklogic = (HttpURLConnection) url9.openConnection();
			checklogic.setConnectTimeout(3000);
			checklogic.setReadTimeout(3000);
			checklogic.connect();
			if (!(checklogic.getResponseCode() == 200)) {
				Log.d("Download Process", "Server Failed!");
				URLCheck10();
			} else {
				Log.d("Download Process", "Server Clear!");
				finalURL = url9;
				startdownload();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void URLCheck10() throws MalformedURLException {
		try {
			url10 = new URL("");
			checklogic = (HttpURLConnection) url10.openConnection();
			checklogic.setConnectTimeout(3000);
			checklogic.setReadTimeout(3000);
			checklogic.connect();
			if (!(checklogic.getResponseCode() == 200)) {
				Log.d("Download Process", "Server Failed!");
				URLCheck();
			} else {
				Log.d("Download Process", "Server Clear!");
				finalURL = url10;
				startdownload();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void startdownload() {
		
>>>>>>> Last One ISSUE! but Test Device is broken...
		isLowOnMemory = false;
		new RunUnZipThread().start();
	}

	private static class RunUnZipThread extends Thread {
		@Override
		public void run() {
			DownloadProcess.isDownloadInProgress = true;
			URLConnection urlConnection;
			try {
				checklogic.disconnect();
				Log.d("sa", String.valueOf(finalURL));
				urlConnection = finalURL.openConnection();
				ZipInputStream ZIStream = new ZipInputStream(
						urlConnection.getInputStream());

				for (ZipEntry zipEntry = ZIStream.getNextEntry(); zipEntry != null; zipEntry = ZIStream
						.getNextEntry()) {

					String innerFileName = BASE_FOLDER + File.separator
							+ zipEntry.getName();
					Log.d("sa", innerFileName);
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

<<<<<<< HEAD
}
=======
}
>>>>>>> Last One ISSUE! but Test Device is broken...
