package angeloid.dreamnarae;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.exceptions.RootDeniedException;
import com.stericson.RootTools.execution.CommandCapture;

public class RootToolsInstallProcess extends Activity {

	// Version Name
	String VersionName;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.installprocess);

		// Intent Data
		Intent intent = getIntent();
		VersionName = intent.getStringExtra("Version");

		if (VersionName.equals("SPiCa")) {
			try {
				Install_SPiCa();
				recovery();
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
		if (VersionName.equals("Miracle")) {
			try {
				Install_Miracle();
				recovery();
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
		if (VersionName.equals("Save")) {
			try {
				Install_Save();
				recovery();
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
		if (VersionName.equals("Prev")) {
			try {
				Install_Prev();
				recovery();
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
		if (VersionName.equals("SPiSave")) {
			try {
				Install_SPiSave();
				recovery();
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
		if (VersionName.equals("Brand")) {
			try {
				Install_Brand();
				recovery();
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
		if (VersionName.equals("Pure")) {
			try {
				Install_Pure();
				recovery();
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
		if (VersionName.equals("Delete")) {
			try {
				Delete_File();
				DeleteComplete();
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

	public void Install_SPiCa() throws InterruptedException, IOException,
			TimeoutException, RootDeniedException {
		Delete_File();
		RootTools.log("Test Log // Excute Test Command_spica");
		RootTools.copyFile("/data/data/angeloid.dreamnarae/files/spica/00prop",
				"/system/etc/init.d/", false, true);
		RootTools.copyFile("/data/data/angeloid.dreamnarae/files/spica/01io",
				"/system/etc/init.d", false, true);
		RootTools.copyFile("/data/data/angeloid.dreamnarae/files/spica/02freq",
				"/system/etc/init.d", false, true);
		RootTools.copyFile(
				"/data/data/angeloid.dreamnarae/files/spica/03zipalign",
				"/system/etc/init.d", false, true);
		RootTools
				.copyFile(
						"/data/data/angeloid.dreamnarae/files/spica/98banne_dreamnarae_spica",
						"/system/", false, true);
		RootTools.copyFile("/data/data/angeloid.dreamnarae/files/allflag",
				"/system/", false, true);
		CommandCapture command = new CommandCapture(0,
				"chmod 755 /system/etc/init.d/00prop",
				"chmod 755 /system/etc/init.d/01io",
				"chmod 755 /system/etc/init.d/02freq",
				"chmod 755 /system/etc/init.d/03zipalign",
				"chmod 755 /system/98banner_dreamnarae_spica",
				"chmod 755 /system/allflag");
		RootTools.getShell(true).add(command).waitForFinish();
		installcomplete();
	}

	public void Install_Pure() throws InterruptedException, IOException,
			TimeoutException, RootDeniedException {
		Delete_File();
		RootTools.remount("/system/", "rw");
		RootTools.log("Test Log // Excute Test Command_pure");
		RootTools.copyFile("/data/data/angeloid.dreamnarae/files/pure/00cpu",
				"/system/etc/init.d/00cpu", false, false);
		RootTools.copyFile(
				"/data/data/angeloid.dreamnarae/files/pure/01memory",
				"/system/etc/init.d/01memory", false, false);
		RootTools.copyFile("/data/data/angeloid.dreamnarae/files/pure/02prop",
				"/system/etc/init.d/02prop", false, false);
		RootTools.copyFile(
				"/data/data/angeloid.dreamnarae/files/pure/03cleaning",
				"/system/etc/init.d/03cleaning", false, false);
		RootTools.copyFile(
				"/data/data/angeloid.dreamnarae/files/pure/04izpalign",
				"/system/etc/init.d/04zipalign", false, false);
		RootTools
				.copyFile(
						"/data/data/angeloid.dreamnarae/files/pure/98banne_dreamnarae_pure",
						"/system/98banner_dreamnarae_pure", false, false);
		RootTools.copyFile("/data/data/angeloid.dreamnarae/files/allflag",
				"/system/allflag", false, false);
		CommandCapture command = new CommandCapture(0,
				"chmod 755 /system/etc/init.d/00cpu",
				"chmod 755 /system/etc/init.d/01memory",
				"chmod 755 /system/etc/init.d/02prop",
				"chmod 755 /system/etc/init.d/03cleaning",
				"chmod 755 /system/etc/init.d/04zipalign",
				"chmod 755 /system/98banner_dreamnarae_pure",
				"chmod 755 /system/allflag");
		RootTools.getShell(true).add(command).waitForFinish();
		installcomplete();
	}

	public void Install_Miracle() throws InterruptedException, IOException,
			TimeoutException, RootDeniedException {
		Delete_File();
		RootTools.remount("/system/", "rw");
		RootTools.log("Test Log // Excute Test Command_miracle");
		RootTools.copyFile(
				"/data/data/angeloid.dreamnarae/files/miracle/00set",
				"/system/etc/init.d/00set", false, false);
		RootTools.copyFile(
				"/data/data/angeloid.dreamnarae/files/miracle/01vsls",
				"/system/etc/init.d/01vsls", false, false);
		RootTools.copyFile(
				"/data/data/angeloid.dreamnarae/files/miracle/02dch",
				"/system/etc/init.d/02dch", false, false);
		RootTools.copyFile(
				"/data/data/angeloid.dreamnarae/files/pure/03zipalign",
				"/system/etc/init.d/03zipalign", false, false);
		RootTools
				.copyFile(
						"/data/data/angeloid.dreamnarae/files/miracle/98banne_dreamnarae_miracle",
						"/system/98banner_dreamnarae_miracle", false, false);
		RootTools.copyFile("/data/data/angeloid.dreamnarae/files/allflag",
				"/system/allflag", false, false);
		CommandCapture command = new CommandCapture(0,
				"chmod 755 /system/etc/init.d/00set",
				"chmod 755 /system/etc/init.d/01vsls",
				"chmod 755 /system/etc/init.d/02dch",
				"chmod 755 /system/etc/init.d/03zipalign",
				"chmod 755 /system/98banner_dreamnarae_miracle",
				"chmod 755 /system/allflag");
		RootTools.getShell(true).add(command).waitForFinish();
		installcomplete();
	}

	public void Install_Save() throws InterruptedException, IOException,
			TimeoutException, RootDeniedException {
		Delete_File();
		RootTools.log("Test Log // Excute Test Command_save");
		RootTools.remount("/system/", "rw");
		RootTools.copyFile("/data/data/angeloid.dreamnarae/files/save/00sp",
				"/system/etc/init.d/00sp", false, false);
		RootTools.copyFile("/data/data/angeloid.dreamnarae/files/save/01v",
				"/system/etc/init.d/01v", false, false);
		RootTools.copyFile("/data/data/angeloid.dreamnarae/files/save/02deep",
				"/system/etc/init.d/02deep", false, false);
		RootTools
				.copyFile(
						"/data/data/angeloid.dreamnarae/files/save/98banne_dreamnarae_save",
						"/system/98banner_dreamnarae_save", false, false);
		RootTools.copyFile("/data/data/angeloid.dreamnarae/files/allflag",
				"/system/allflag", false, false);
		CommandCapture command = new CommandCapture(0,
				"chmod 755 /system/etc/init.d/00sp",
				"chmod 755 /system/etc/init.d/01v",
				"chmod 755 /system/etc/init.d/02deep",
				"chmod 755 /system/98banner_dreamnarae_save",
				"chmod 755 /system/allflag");
		RootTools.getShell(true).add(command).waitForFinish();
		installcomplete();
	}

	public void Install_Prev() throws InterruptedException, IOException,
			TimeoutException, RootDeniedException {
		Delete_File();
		RootTools.remount("/system/", "rw");
		RootTools.log("Test Log // Excute Test Command_prev");
		RootTools.copyFile(
				"/data/data/angeloid.dreamnarae/files/prev/00proppv",
				"/system/etc/init.d/00proppv", false, false);
		RootTools.copyFile("/data/data/angeloid.dreamnarae/files/prev/01iopv",
				"/system/etc/init.d/01iopv", false, false);
		RootTools.copyFile(
				"/data/data/angeloid.dreamnarae/files/prev/02freqpv",
				"/system/etc/init.d/02freqpv", false, false);
		RootTools
				.copyFile(
						"/data/data/angeloid.dreamnarae/files/prev/98banne_dreamnarae_prev",
						"/system/98banner_dreamnarae_prev", false, false);
		RootTools.copyFile("/data/data/angeloid.dreamnarae/files/allflag",
				"/system/allflag", false, false);
		CommandCapture command = new CommandCapture(0,
				"chmod 755 /system/etc/init.d/00proppv",
				"chmod 755 /system/etc/init.d/01iopv",
				"chmod 755 /system/etc/init.d/02freqpv",
				"chmod 755 /system/98banner_dreamnarae_prev",
				"chmod 755 /system/allflag");
		RootTools.getShell(true).add(command).waitForFinish();
		installcomplete();
	}

	public void Install_SPiSave() throws InterruptedException, IOException,
			TimeoutException, RootDeniedException {
		Delete_File();
		RootTools.log("Test Log // Excute Test Command_spisave");
		RootTools.remount("/system/", "rw");
		RootTools.copyFile(
				"/data/data/angeloid.dreamnarae/files/spisave/00prop",
				"/system/etc/init.d/00prop", false, false);
		RootTools.copyFile("/data/data/angeloid.dreamnarae/files/spisave/01io",
				"/system/etc/init.d/01io", false, false);
		RootTools.copyFile(
				"/data/data/angeloid.dreamnarae/files/spisave/02freq",
				"/system/etc/init.d/02freq", false, false);
		RootTools
				.copyFile(
						"/data/data/angeloid.dreamnarae/files/spisave/98banne_dreamnarae_spisave",
						"/system/98banner_dreamnarae_pure", false, false);
		RootTools.copyFile("/data/data/angeloid.dreamnarae/files/allflag",
				"/system/allflag", false, false);
		CommandCapture command = new CommandCapture(0,
				"chmod 755 /system/etc/init.d/00prop",
				"chmod 755 /system/etc/init.d/01io",
				"chmod 755 /system/etc/init.d/02freq",
				"chmod 755 /system/98banner_dreamnarae_spisave",
				"chmod 755 /system/allflag");
		RootTools.getShell(true).add(command).waitForFinish();
		installcomplete();
	}

	public void Install_Brand() throws InterruptedException, IOException,
			TimeoutException, RootDeniedException {
		Delete_File();
		RootTools.log("Test Log // Excute Test Command_brand");
		RootTools.remount("/system/", "rw");
		RootTools.copyFile("/data/data/angeloid.dreamnarae/files/brand/00b",
				"/system/etc/init.d/00b", false, false);
		RootTools.copyFile("/data/data/angeloid.dreamnarae/files/brand/01r",
				"/system/etc/init.d/01r", false, false);
		RootTools.copyFile(
				"/data/data/angeloid.dreamnarae/files/spisave/02and",
				"/system/etc/init.d/02and", false, false);
		RootTools
				.copyFile(
						"/data/data/angeloid.dreamnarae/files/brand/98banne_dreamnarae_brand",
						"/system/98banner_dreamnarae_brand", false, false);
		RootTools.copyFile("/data/data/angeloid.dreamnarae/files/allflag",
				"/system/allflag", false, false);
		CommandCapture command = new CommandCapture(0,
				"chmod 755 /system/etc/init.d/00b",
				"chmod 755 /system/etc/init.d/01r",
				"chmod 755 /system/etc/init.d/02and",
				"chmod 755 /system/98banner_dreamnarae_brand",
				"chmod 755 /system/allflag");
		RootTools.getShell(true).add(command).waitForFinish();
		installcomplete();
	}

	public static void Delete_File() throws InterruptedException, IOException,
			TimeoutException, RootDeniedException {
		RootTools.remount("/system/", "rw");
		CommandCapture command = new CommandCapture(0,
				"rm /system/98banner_dreamnarae_spica",
				"rm /system/98banner_dreamnarae_miracle",
				"rm /system/98banner_dreamnarae_save",
				"rm /system/98banner_dreamnarae_prev",
				"rm /system/98banner_dreamnarae_pure",
				"rm /system/98banner_dreamnarae_brand",
				"rm /system/98banner_dreamnarae_spisave", "rm /system/allflag",
				"rm /system/etc/init.d/00prop", "rm /system/etc/init.d/01io",
				"rm /system/etc/init.d/02freq",
				"rm /system/etc/init.d/03zipalign",
				"rm /system/etc/init.d/01kswapd0",
				"rm /system/etc/init.d/02io", "rm /system/etc/init.d/03freq",
				"rm /system/etc/init.d/04zipalign",
				"rm /system/etc/init.d/00set",
				"rm /system/etc/init.d/01property",
				"rm /system/etc/init.d/02vsls", "rm /system/etc/init.d/03dch",
				"rm /system/etc/init.d/04zip", "rm /system/etc/init.d/01vsls",
				"rm /system/etc/init.d/02dch", "rm /system/etc/init.d/00sp",
				"rm /system/etc/init.d/01v", "rm /system/etc/init.d/02deep",
				"rm /system/etc/init.d/03zip",
				"rm /system/etc/init.d/00proppv",
				"rm /system/etc/init.d/01kswapd0pv",
				"rm /system/etc/init.d/02iopv",
				"rm /system/etc/init.d/03freqpv",
				"rm /system/etc/init.d/04zippv",
				"rm /system/etc/init.d/01iopv",
				"rm /system/etc/init.d/02freqpv",
				"rm /system/etc/init.d/00cpu", "rm /system/etc/init.d/01loosy",
				"rm /system/etc/init.d/02memory",
				"rm /system/etc/init.d/03prop",
				"rm /system/etc/init.d/04cleaning",
				"rm /system/etc/init.d/00b", "rm /system/etc/init.d/01r",
				"rm /system/etc/init.d/02and",
				"rm /system/etc/init.d/00cleaning",
				"rm /system/etc/init.d/01cpu",
				"rm /system/etc/init.d/02sysctl",
				"rm /system/etc/init.d/03memory",
				"rm /system/etc/init.d/04prop",
				"rm /system/etc/init.d/05zipalign",
				"rm /system/etc/init.d/06sysctl",
				"rm /system/etc/init.d/00cpu",
				"rm /system/etc/init.d/01memory",
				"rm /system/etc/init.d/02prop",
				"rm /system/etc/init.d/03cleaning",
				"rm /system/etc/init.d/04zipalign");
		RootTools.getShell(true).add(command).waitForFinish();
	}

	public void installcomplete() throws IOException {
		File file = new File("/system/allflag");
		if (file.length() > 0) {
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
			AlertDialog.Builder builder = new Builder(
					RootToolsInstallProcess.this);
			builder.setView(view);
			builder.setPositiveButton(R.string.yes,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							RootTools.restartAndroid();
						}
					})
					.setNegativeButton(R.string.no,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
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
			AlertDialog.Builder builder = new Builder(
					RootToolsInstallProcess.this);
			builder.setView(view1);
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

	public void DeleteComplete() throws IOException {
		File file = new File("/system/allflag");
		if (file.length() > 0) {
			View view1 = this.getLayoutInflater().inflate(
					R.layout.customdialog, null);
			Log.d("Install", "Delete Failed");
			TextView txtTitle = (TextView) view1.findViewById(R.id.title);
			txtTitle.setText(R.string.errortitle);
			txtTitle.setTextColor(Color.WHITE);
			txtTitle.setTextSize(20);
			TextView message = (TextView) view1.findViewById(R.id.message);
			message.setText(R.string.error2message);
			message.setTextColor(Color.WHITE);
			AlertDialog.Builder builder = new Builder(
					RootToolsInstallProcess.this);
			builder.setView(view1);
			builder.setPositiveButton(R.string.infoclose,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							finish();
						}
					}

			).show();
		} else {
			View view = this.getLayoutInflater().inflate(R.layout.customdialog,
					null);
			TextView txtTitle = (TextView) view.findViewById(R.id.title);
			txtTitle.setText(R.string.reboottitle);
			txtTitle.setTextColor(Color.WHITE);
			txtTitle.setTextSize(20);
			TextView message = (TextView) view.findViewById(R.id.message);
			message.setText(R.string.rebootmessage);
			message.setTextColor(Color.WHITE);
			AlertDialog.Builder builder = new Builder(
					RootToolsInstallProcess.this);
			builder.setView(view);
			builder.setPositiveButton(R.string.yes,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							RootTools.restartAndroid();
						}
					})
					.setNegativeButton(R.string.no,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();

								}
							}).show();

		}
	}

	public void recovery() throws IOException, InterruptedException {
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
}