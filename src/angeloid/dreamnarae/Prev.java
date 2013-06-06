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

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.exceptions.RootDeniedException;
import com.stericson.RootTools.execution.CommandCapture;

public class Prev extends BaseTweakSlidingActivity {
	Button apply;
	ImageView imageview;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prev);
		apply = (Button) findViewById(R.id.apply);
		imageview = (ImageView) findViewById(R.id.imageview);
		apply.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				apply.setEnabled(false);
				apply.setFocusable(false);
				startDownload(v);
				Handler mHandler = new Handler();
				mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						if (RootTools.isAccessGiven()) {
							try {
								Install_Prev();
							} catch (InterruptedException e) {
							} catch (IOException e) {
							} catch (TimeoutException e) {

							} catch (RootDeniedException e) {
							}
						} else {
							Toast.makeText(Prev.this, R.string.noroottoast,
									Toast.LENGTH_LONG).show();
						}
					}
				}, 2000);

			}
		});
		if (new File("/system/98banner_dreamnarae_prev").exists()) {
			apply.setEnabled(false);
			apply.setFocusable(false);
			imageview.setImageResource(R.drawable.apply);
		} else {
		}

	}

	public void Install_Prev() throws InterruptedException, IOException,
			TimeoutException, RootDeniedException {
		Delete_File();
		RootTools.remount("/system/", "rw");
		RootTools.copyFile(this.getExternalFilesDir(null) + "/prev_set.sh",
				"/system/etc/dreamnarae.sh", true, false);
		CommandCapture command = new CommandCapture(0,
				"chmod 755 /system/etc/dreamnarae.sh");
		RootTools.getShell(true).add(command).waitForFinish();
		installcomplete();
	}

	public void installcomplete() throws IOException, InterruptedException,
			TimeoutException, RootDeniedException {
		recovery();
		File file = new File("/system/etc/dreamnarae.sh");
		if (file.length() > 0) {
			RootTools.remount("/system/", "RW");
			CommandCapture command = new CommandCapture(0,
					"busybox touch /system/98banner_dreamnarae_prev",
					"echo check > /system/98banner_dreamnarae_prev",
					"chmod 755 /system/98banner_dreamnarae_prev");
			RootTools.getShell(true).add(command).waitForFinish();
			Log.d("Install", "Install Success!");
			success_dn();
			if (new File("/system/98banner_dreamnarae_prev").exists()) {
				apply.setEnabled(false);
				apply.setFocusable(false);
				imageview.setImageResource(R.drawable.apply);
			} else {
			}

		} else {
			fail_dn();
		}
	}
}
