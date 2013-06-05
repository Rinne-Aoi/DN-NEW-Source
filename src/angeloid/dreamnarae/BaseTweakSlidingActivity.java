package angeloid.dreamnarae;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
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

public class BaseTweakSlidingActivity extends BaseSlidingActivity {

	// Tweak
	protected ProgressDialog mProgressDialog;
	Button apply;
	ImageView imageview;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Tweak
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		apply = (Button) findViewById(R.id.apply);
		imageview = (ImageView) findViewById(R.id.imageview);

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
			// TODO
			Toast.makeText(BaseTweakSlidingActivity.this,
					result.getLocalizedMessage(), Toast.LENGTH_LONG).show();
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

	public void success_dn() {
		View view = this.getLayoutInflater().inflate(R.layout.customdialog,
				null);
		TextView txtTitle = (TextView) view.findViewById(R.id.title);
		txtTitle.setText(R.string.reboottitle);
		txtTitle.setTextColor(Color.WHITE);
		txtTitle.setTextSize(20);
		TextView message = (TextView) view.findViewById(R.id.message);
		message.setText(R.string.rebootmessage);
		message.setTextColor(Color.WHITE);
		AlertDialog.Builder builder = new Builder(BaseTweakSlidingActivity.this);
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
								if (new File(
										"/system/98banner_dreamnarae_miracle")
										.exists()) {
									apply.setEnabled(false);
									apply.setFocusable(false);
									imageview
											.setImageResource(R.drawable.apply);
								} else {
								}
							}
						}).show();
	}

	public void fail_dn() {
		View view1 = this.getLayoutInflater().inflate(R.layout.customdialog,
				null);
		TextView txtTitle = (TextView) view1.findViewById(R.id.title);
		txtTitle.setText(R.string.errortitle);
		txtTitle.setTextColor(Color.WHITE);
		txtTitle.setTextSize(20);
		TextView message = (TextView) view1.findViewById(R.id.message);
		message.setText(R.string.error2message);
		message.setTextColor(Color.WHITE);
		AlertDialog.Builder builder = new Builder(BaseTweakSlidingActivity.this);
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
