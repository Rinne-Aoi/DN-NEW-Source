/* DreamNarae Boot Script
 * 
 * Copyright 2013 Sopiane(www.sirospace.com) in Angeloid Team
 */

/*
 * DreamNarae, Emotional Android Tools.
    Copyright (C) 2013 Seo, Dong-Gil in Angeloid Team. 

 This code is dual-licensed under the terms of the Apache License Version 2.0 and
    the terms of the General Public License (GPL) Version 2.
    You may use this code according to either of these licenses as is most appropriate
    for your project on a case-by-case basis.

    The terms of each license can be found in the root directory of this project's repository as well as at:

    * http://www.apache.org/licenses/LICENSE-2.0
    * http://www.gnu.org/licenses/gpl-2.0.txt
 
    Unless required by applicable law or agreed to in writing, software
    distributed under these Licenses is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See each License for the specific language governing permissions and
    limitations under that License.


    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
	
	Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package angeloid.dreamnarae;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.exceptions.RootDeniedException;
import com.stericson.RootTools.execution.CommandCapture;

public class Boot_Script extends BroadcastReceiver {

	@Override
	public void onReceive(Context c, Intent i) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(c);
		String statboolean = prefs.getString("bootcheck", "false");
		if (!(RootTools.isAccessGiven())) {
			Toast.makeText(c, R.string.noroottoast, Toast.LENGTH_LONG).show();
		} else {
			if (statboolean.equals("false")) {
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