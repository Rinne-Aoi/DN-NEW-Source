/* DreamNarae Boot Script
 * 
 * Copyright 2013 Sopiane(www.sirospace.com) in Angeloid Team
 */
package angeloid.dreamnarae;

import java.io.File;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class Boot_Script extends BroadcastReceiver {
	static boolean bootcheck;

	@Override
	public void onReceive(Context c, Intent i) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(c);
		boolean statboolean = prefs.getBoolean("bootcheck", false);
		Log.d("Tab_MainActivity", String.valueOf(statboolean));
		Boot_Script.bootcheck = statboolean;
		if (!(new File("/system/bin/su").exists())
				&& !(new File("/system/xbin/su").exists())
				&& !(new File("/system/bin/busybox").exists())) {
			Toast.makeText(c, R.string.noroottoast, Toast.LENGTH_LONG).show();
		} else {
			if (bootcheck == false) {
				if (new File("/system/allflag").exists()) {
					StringBuilder boot = new StringBuilder();
					boot.append("busybox mount -o rw,remount /system;");
					boot.append("sh /system/etc/install-recovery.sh;");
					boot.append("busybox run-parts /system/etc/init.d;");
					Helper.instantExec(c, boot.toString());
					try {
						Runtime.getRuntime().exec("su");
						String[] rmcommand = { "su", "-c",
								"rm /data/data/angeloid.dreamnarae/files/scriptrunner.sh" };
						Runtime.getRuntime().exec(rmcommand); 
						Toast.makeText(c, R.string.bootcomplete,
								Toast.LENGTH_SHORT).show();
					} catch (Exception e) {
						Log.e("LOGTAG", "Couldn't Execute" + e.getMessage());
					}
				}
			}
		}

	}
}