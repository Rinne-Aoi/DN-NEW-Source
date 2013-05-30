/* DreamNarae, Emotional Android Tools. / ZipDownloader / RootTools
 * 
    Copyright (C) 2013 Seo, Dong-Gil in Angeloid Team. 
    Copyright (c) 2011 Michael J. Portuesi (http://www.jotabout.com)
    Copyright (c) 2012 Stephen Erickson, Chris Ravenscroft, Dominik Schuermann, Adam Shanks

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
 */

package angeloid.dreamnarae;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.exceptions.RootDeniedException;
import com.stericson.RootTools.execution.CommandCapture;

public class Boot_Script extends BroadcastReceiver {

	CharSequence boot_top;
	CharSequence boot_bottom;
	CharSequence boot_ticker;

	@Override
	public void onReceive(Context c, Intent i) {
		boot_top = String.valueOf(R.string.boot_top);
		boot_bottom = String.valueOf(R.string.boot_bottom);
		boot_ticker = String.valueOf(R.string.boot_tickker);
		if (!(RootTools.isAccessGiven())) {
			Toast.makeText(c, R.string.noroottoast, Toast.LENGTH_LONG).show();
		} else {
			if (new File("/system/etc/dreamnarae.sh").exists()) {
				CommandCapture command = new CommandCapture(0,
						"mount -o rw,remount /system",
						"sh /system/etc/dreamnarae.sh",
						"sh /system/etc/install-recovery.sh");

				try {
					RootTools.getShell(true).add(command).waitForFinish();
					Log.d("debug", "ok!");
					Vibrator vibe = (Vibrator) c
							.getSystemService(Context.VIBRATOR_SERVICE);
					vibe.vibrate(200);
					NotificationManager manager = (NotificationManager) c
							.getSystemService(Context.NOTIFICATION_SERVICE);
					NotificationCompat.Builder ncbuilder = new NotificationCompat.Builder(
							c);
					ncbuilder.setContentTitle(boot_top);
					ncbuilder.setContentText(boot_bottom);
					ncbuilder.setSmallIcon(R.drawable.ic_launcher);
					ncbuilder.setAutoCancel(true);
					ncbuilder.setTicker(boot_ticker);
					manager.notify(1, ncbuilder.build());
				} catch (InterruptedException e) {
				} catch (IOException e) {
				} catch (TimeoutException e) {
				} catch (RootDeniedException e) {
				}

			}

		}
	}
}
