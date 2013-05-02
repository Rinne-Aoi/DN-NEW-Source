/*
 * Root Permission Helper(RPH) Library
 * developed by 2011 Vista(NexusRoi).
 * based on tenfar's defy bootstrap source
 * edit by 2012 - 2013 Sopiane(www.sirospace.com) in Angeloid Team.
 * for DreamNarae
 */

package angeloid.dreamnarae;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class Helper {
	public final static String SCRIPT_NAME = "scriptrunner.sh";

	public static void recovery() throws IOException, InterruptedException {
		Log.d("Helper", Environment.getExternalStorageDirectory()
				.getAbsolutePath());
		String[] recovery = {
				"/system/bin/sh",
				"-c",
				"cat /data/data/angeloid.dreamnarae/files/dn_delete_signed.zip > "
						+ Environment.getExternalStorageDirectory()
								.getAbsolutePath() + "/dn_delete_signed.zip" };
		Log.d("Helper", "Recovery Tool Exist : " + recovery);
		Process p = Runtime.getRuntime().exec(recovery);
		p.waitFor();
	}

	public static boolean instantExec(Context context, String command) {
		try {
			runSuCommand(context, command.toString());
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static String dumpFile(String filename) {
		String line = "";
		try {
			Process ifc = Runtime.getRuntime().exec("cat " + filename);
			BufferedReader bis = new BufferedReader(new InputStreamReader(
					ifc.getInputStream()));
			line = bis.readLine();
			ifc.destroy();

		} catch (java.io.IOException e) {
			return new String("");
		}

		return line;
	}

	public static Process runSuCommandAsync(Context context, String command)
			throws IOException {
		DataOutputStream fout = new DataOutputStream(context.openFileOutput(
				SCRIPT_NAME, 0));
		fout.writeBytes(command);
		fout.close();

		String[] args = new String[] {
				"su",
				"-c",
				". " + context.getFilesDir().getAbsolutePath() + "/"
						+ SCRIPT_NAME };
		Process proc = Runtime.getRuntime().exec(args);
		return proc;
	}

	public static int runSuCommand(Context context, String command)
			throws IOException, InterruptedException {
		return runSuCommandAsync(context, command).waitFor();
	}
}