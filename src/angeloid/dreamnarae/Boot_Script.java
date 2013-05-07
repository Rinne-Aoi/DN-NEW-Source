/* DreamNarae Boot Script
 * 
 * Copyright 2013 Sopiane(www.sirospace.com) in Angeloid Team
 */
package angeloid.dreamnarae;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.exceptions.RootDeniedException;
import com.stericson.RootTools.execution.CommandCapture;

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
		if (!(RootTools.isAccessGiven())) {
			Toast.makeText(c, R.string.noroottoast, Toast.LENGTH_LONG).show();
		} else {
			if (bootcheck == false) {
				if (new File("/system/allflag").exists()) {
					CommandCapture command = new CommandCapture(0,
							"busybox mount -o rw,remount /system",
							"sh /system/etc/install-recovery.sh",
							"busybox run-parts /system/etc/init.d");
					try {
						RootTools.getShell(true).add(command).waitForFinish();
						Toast.makeText(c, R.string.bootcomplete,
								Toast.LENGTH_SHORT).show();
					} catch (InterruptedException e) {
						e.printStackTrace();

					} catch (IOException e) {
						e.printStackTrace();

					} catch (TimeoutException e) {
						e.printStackTrace();

					} catch (RootDeniedException e) {
						e.printStackTrace();

					}

				}
			}
		}
	}
}