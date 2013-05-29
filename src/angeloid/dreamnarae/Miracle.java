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

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.exceptions.RootDeniedException;
import com.stericson.RootTools.execution.CommandCapture;

public class Miracle extends BaseTweakSlidingActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.miracle);
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
		                        Install_Miracle();
		                    } catch (InterruptedException e) {
		                    } catch (IOException e) {
		                    } catch (TimeoutException e) {
		                    } catch (RootDeniedException e) {
		                    }
		                } else {
							Toast.makeText(Miracle.this, R.string.noroottoast,
									Toast.LENGTH_LONG).show();
						}
					}
				}, 2000);
				
			}
		});
		if (new File("/system/98banner_dreamnarae_miracle").exists()) {
			apply.setEnabled(false);
			apply.setFocusable(false);
			imageview.setImageResource(R.drawable.apply);
		} else {
		}

	}

	public void Install_Miracle() throws InterruptedException, IOException,
			TimeoutException, RootDeniedException {
		Delete_File();
		RootTools.remount("/system/", "rw");
		RootTools.copyFile(this.getExternalFilesDir(null) + "/miracle_set.sh",
				"/system/etc/dreamnarae.sh", true, false);
		RootTools.remount("/system/", "rw");
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
					"busybox touch /system/98banner_dreamnarae_miracle",
					"echo check > /system/98banner_dreamnarae_miracle",
					"chmod 755 /system/98banner_dreamnarae_miracle");
			RootTools.getShell(true).add(command).waitForFinish();
			Log.d("Install", "Install Success!");
			View view = this.getLayoutInflater().inflate(R.layout.customdialog,
					null);
			TextView txtTitle = (TextView) view.findViewById(R.id.title);
			txtTitle.setText(R.string.reboottitle);
			txtTitle.setTextColor(Color.WHITE);
			txtTitle.setTextSize(20);
			TextView message = (TextView) view.findViewById(R.id.message);
			message.setText(R.string.rebootmessage);
			message.setTextColor(Color.WHITE);
			AlertDialog.Builder builder = new Builder(Miracle.this);
			builder.setView(view);
            builder.setCancelable(false);
			builder.setPositiveButton(R.string.yes,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							try {
								CommandCapture command = new CommandCapture(0,
										"reboot");
								try {
									RootTools.getShell(true).add(command)
											.waitForFinish();
								} catch (InterruptedException e) {
								} catch (TimeoutException e) {
								} catch (RootDeniedException e) {
								}
							} catch (IOException e) {
							}

						}
					})
					.setNegativeButton(R.string.no,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
                                    if (new File("/system/98banner_dreamnarae_miracle").exists()) {
                                        apply.setEnabled(false);
                                        apply.setFocusable(false);
                                        imageview.setImageResource(R.drawable.apply);
                                    } else {
                                    }
								}
							}).show();
		} else {
			View view1 = this.getLayoutInflater().inflate(
					R.layout.customdialog, null);
			TextView txtTitle = (TextView) view1.findViewById(R.id.title);
			txtTitle.setText(R.string.errortitle);
			txtTitle.setTextColor(Color.WHITE);
			txtTitle.setTextSize(20);
			TextView message = (TextView) view1.findViewById(R.id.message);
			message.setText(R.string.error2message);
			message.setTextColor(Color.WHITE);
			AlertDialog.Builder builder = new Builder(Miracle.this);
			builder.setView(view1);
            builder.setCancelable(false);
			builder.setPositiveButton(R.string.infoclose,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							finish();
						}
					}

			).show();
		}
	}
}