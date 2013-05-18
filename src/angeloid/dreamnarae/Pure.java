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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jotabout.zipdownloader.util.DecompressZip;
import com.jotabout.zipdownloader.util.DownloadFile;
import com.jotabout.zipdownloader.util.ExternalStorage;
import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.exceptions.RootDeniedException;
import com.stericson.RootTools.execution.CommandCapture;

public class Pure extends Activity {
	Button apply;
	Button info;
	static MediaPlayer mplayer;
	ImageView imageview;
	protected ProgressDialog mProgressDialog;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pure);
		apply = (Button) findViewById(R.id.apply);
		info = (Button) findViewById(R.id.info);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		imageview = (ImageView) findViewById(R.id.imageview);
		mplayer = MediaPlayer.create(Pure.this, R.raw.spica);
		apply.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (RootTools.isAccessGiven()) {
					startDownload(v);
                    try {
                        Install_Pure();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    } catch (RootDeniedException e) {
                        e.printStackTrace();
                    }
                } else {
					Toast.makeText(Pure.this, R.string.noroottoast,
							Toast.LENGTH_LONG).show();
				}
			}
		});
		if (new File("/system/98banner_dreamnarae_pure").exists()) {
			apply.setEnabled(false);
			apply.setFocusable(false);
			imageview.setImageResource(R.drawable.apply);
		} else {
		}

		info.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog();

			}
		});
	}

	public void dialog() {
		View view = this.getLayoutInflater().inflate(R.layout.customdialog,
				null);
		TextView txtTitle = (TextView) view.findViewById(R.id.title);
		txtTitle.setText(R.string.pure_title);
		txtTitle.setTextColor(Color.WHITE);
		txtTitle.setTextSize(20);
		TextView message = (TextView) view.findViewById(R.id.message);
		message.setText(R.string.pure_info);
		message.setTextColor(Color.WHITE);
		AlertDialog.Builder builder = new Builder(Pure.this);
		builder.setView(view);
		builder.setCancelable(false);
		builder.setPositiveButton(R.string.infoclose,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}

		)
				.setNegativeButton(R.string.voice,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								mplayer.start();
							}
						}).show();
	}

	public void Install_Pure() throws InterruptedException, IOException,
			TimeoutException, RootDeniedException {
		Delete_File();
		RootTools.remount("/system/", "rw");
		RootTools.copyFile(this.getExternalFilesDir(null) + "/pure_set.sh",
				"/system/etc/dreamnarae", true, false);
		RootTools.remount("/system/", "rw");
		CommandCapture command = new CommandCapture(0,
				"chmod 755 /system/etc/dreamnarae.sh");
		RootTools.getShell(true).add(command).waitForFinish();
		installcomplete();
	}

	public static void Delete_File() throws InterruptedException, IOException,
			TimeoutException, RootDeniedException {
		RootTools.remount("/system/", "RW");
		CommandCapture command = new CommandCapture(0,
				"rm /system/98banner_dreamnarae_spica",
				"rm /system/98banner_dreamnarae_miracle",
				"rm /system/98banner_dreamnarae_save",
				"rm /system/98banner_dreamnarae_prev",
				"rm /system/98banner_dreamnarae_pure",
				"rm /system/98banner_dreamnarae_brand",
				"rm /system/98banner_dreamnarae_spisave",
				"rm /system/etc/dreamnarae.sh");
		RootTools.getShell(true).add(command).waitForFinish();
	}

	public void installcomplete() throws IOException, InterruptedException,
			TimeoutException, RootDeniedException {
		recovery();
		File file = new File("/system/etc/dreamnarae.sh");
		if (file.length() > 0) {
			CommandCapture command = new CommandCapture(0,
					"busybox touch /system/98banner_dreamnarae_pure",
					"echo check > /system/98banner_dreamnarae_pure");
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
			AlertDialog.Builder builder = new Builder(Pure.this);
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
									e.printStackTrace();
								} catch (TimeoutException e) {
									e.printStackTrace();
								} catch (RootDeniedException e) {
									e.printStackTrace();
								}
							} catch (IOException e) {
								e.printStackTrace();
							}

						}
					})
					.setNegativeButton(R.string.no,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
                                    if (new File("/system/98banner_dreamnarae_pure").exists()) {
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
			AlertDialog.Builder builder = new Builder(Pure.this);
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

	public void recovery() throws IOException, InterruptedException,
			TimeoutException, RootDeniedException {
		RootTools.copyFile(this.getExternalFilesDir(null)
				+ "/dn_delete_signed.zip", Environment
				.getExternalStorageDirectory().getAbsolutePath()
				+ "/dn_delete_signed.zip", true, false);
	}

	public void startDownload(View v) {
		String url = "http://gecp.kr/dn/dn-public.zip";
		new DownloadTask().execute(url);
	}

	private class DownloadTask extends AsyncTask<String, Void, Exception> {

		@Override
		protected void onPreExecute() {
			showProgress();
		}

		@Override
		protected Exception doInBackground(String... params) {
			String url = (String) params[0];

			try {
				downloadAllAssets(url);
			} catch (Exception e) {
				return e;
			}

			return null;
		}

		@Override
		protected void onPostExecute(Exception result) {
			dismissProgress();
			if (result == null) {
				return;
			}
			Toast.makeText(Pure.this, result.getLocalizedMessage(),
					Toast.LENGTH_LONG).show();
		}
	}

	protected void showProgress() {
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setTitle(R.string.progress_title);
		mProgressDialog.setMessage(getString(R.string.progress_detail));
		mProgressDialog.setIndeterminate(true);
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
	}

	protected void dismissProgress() {
		if (mProgressDialog != null && mProgressDialog.isShowing()
				&& mProgressDialog.getWindow() != null) {
			try {
				mProgressDialog.dismiss();
			} catch (IllegalArgumentException ignore) {
				;
			}
		}
		mProgressDialog = null;
	}

	private void downloadAllAssets(String url) {
		File zipDir = ExternalStorage.getSDCacheDir(this, "tmp");
		File zipFile = new File(zipDir.getPath() + "/newdn.zip");
		File outputDir = ExternalStorage.getSDCacheDir(this, "");

		try {
			DownloadFile.download(url, zipFile, zipDir);
			unzipFile(zipFile, outputDir);
		} finally {
			zipFile.delete();
		}
	}

	protected void unzipFile(File zipFile, File destination) {
		DecompressZip decomp = new DecompressZip(zipFile.getPath(),
				destination.getPath() + File.separator);
		decomp.unzip();
	}
}
