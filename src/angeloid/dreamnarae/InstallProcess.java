package angeloid.dreamnarae;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class InstallProcess extends Activity {

	// Dialog View
	static View view;
	static View view1;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.installprocess);

		// Dialog View
		view = this.getLayoutInflater().inflate(R.layout.customdialog, null);
		view1 = this.getLayoutInflater().inflate(R.layout.customdialog, null);
		// Intent Data
		Intent intent = getIntent();
		String VersionName = intent.getStringExtra("Version");

		if (VersionName.equals("Delete")) {
			try {
				Delete(InstallProcess.this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (VersionName.equals("SPiCa")) {
			try {
				InstallSPiCa(InstallProcess.this);
				Log.d("InstallProcess", "SPiCa");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (VersionName.equals("Miracle")) {
			try {
				InstallMiracle(InstallProcess.this);
				Log.d("InstallProcess", "Miracle");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (VersionName.equals("Save")) {
			try {
				InstallSave(InstallProcess.this);
				Log.d("InstallProcess", "Save");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (VersionName.equals("Prev")) {
			try {
				InstallPrev(InstallProcess.this);
				Log.d("InstallProcess", "Prev");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (VersionName.equals("SPiSave")) {
			try {
				InstallSPiSave(InstallProcess.this);
				Log.d("InstallProcess", "SPiSave");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (VersionName.equals("Brand")) {
			try {
				InstallBrand(InstallProcess.this);
				Log.d("InstallProcess", "Brand");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (VersionName.equals("Pure")) {
			try {
				InstallPure(InstallProcess.this);
				Log.d("InstallProcess", "Pure");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void Delete(Context c) throws IOException {
		DeleteVersion(c);
		DeleteComplete(c);
	}

	public static void InstallSPiCa(Context c) throws Exception {
		DownloadProcess.URLCheck();
		File file = new File("/data/data/angeloid.dreamnarae/files/allflag");
		if (file.length() > 0) {
			SPiCaVersion(c);
			Helper.recovery();
		}
	}

	public static void InstallPure(Context c) throws Exception {
		DownloadProcess.URLCheck();
		File file = new File("/data/data/angeloid.dreamnarae/files/allflag");
		if (file.length() > 0) {
			PureVersion(c);
			Helper.recovery();
		}
	}

	public static void InstallSave(Context c) throws Exception {
		DownloadProcess.URLCheck();
		File file = new File("/data/data/angeloid.dreamnarae/files/allflag");
		if (file.length() > 0) {
			SaveVersion(c);
			Helper.recovery();
		}
	}

	public static void InstallPrev(Context c) throws Exception {
		DownloadProcess.URLCheck();
		File file = new File("/data/data/angeloid.dreamnarae/files/allflag");
		if (file.length() > 0) {
			PrevVersion(c);
			Helper.recovery();
		}
	}

	public static void InstallMiracle(Context c) throws Exception {
		DownloadProcess.URLCheck();
		File file = new File("/data/data/angeloid.dreamnarae/files/allflag");
		if (file.length() > 0) {
			MiracleVersion(c);
			Helper.recovery();
		}
	}

	public static void InstallBrand(Context c) throws Exception {
		DownloadProcess.URLCheck();
		File file = new File("/data/data/angeloid.dreamnarae/files/allflag");
		if (file.length() > 0) {
			BrandVersion(c);
			Helper.recovery();
		}
	}

	public static void InstallSPiSave(Context c) throws Exception {
		DownloadProcess.URLCheck();
		File file = new File("/data/data/angeloid.dreamnarae/files/allflag");
		if (file.length() > 0) {
			SPiSaveVersion(c);
			Helper.recovery();
		}
	}

	public static void SPiCaVersion(Context c) throws IOException {
		DeleteVersion(c);
		Log.d("debug", "Install SPiCa");
		StringBuilder spica = new StringBuilder();
		spica.append("mount -o rw,remount /system;");
		spica.append("mkdir /system/etc/init.d;");
		spica.append("cat /data/data/angeloid.dreamnarae/files/spica/00prop > /system/etc/init.d/00prop;");
		spica.append("cat /data/data/angeloid.dreamnarae/files/spica/01io > /system/etc/init.d/01io;");
		spica.append("cat /data/data/angeloid.dreamnarae/files/spica/02freq > /system/etc/init.d/02freq;");
		spica.append("cat /data/data/angeloid.dreamnarae/files/spica/03zipalign > /system/etc/init.d/03zipalign;");
		spica.append("cat /data/data/angeloid.dreamnarae/files/spica/98banner_dreamnarae_spica > /system/98banner_dreamnarae_spica;");
		spica.append("cat /data/data/angeloid.dreamnarae/files/allflag > /system/allflag;");
		spica.append("chmod 755 /system/etc/init.d/00prop;");
		spica.append("chmod 755 /system/etc/init.d/01io;");
		spica.append("chmod 755 /system/etc/init.d/02freq;");
		spica.append("chmod 755 /system/etc/init.d/03zipalign;");
		spica.append("chmod 755 /system/98banner_dreamnarae_spica;");
		spica.append("chmod 755 /system/allflag;");
		spica.append("busybox mount -o ro,remount /system;");
		Helper.instantExec(c, spica.toString());
		Runtime.getRuntime().exec("su");
		String[] rmcommand = { "su", "-c",
				"rm /data/data/angeloid.dreamnarae/files/scriptrunner.sh" };
		Runtime.getRuntime().exec(rmcommand);
		InstallProcess.installcomplete(c);
	}

	public static void MiracleVersion(Context c) throws IOException {
		DeleteVersion(c);
		StringBuilder miracle = new StringBuilder();
		miracle.append("mount -o rw,remount /system;");
		miracle.append("mkdir /system/etc/init.d;");
		miracle.append("cat /data/data/angeloid.dreamnarae/files/miracle/00set > /system/etc/init.d/00set;");
		miracle.append("cat /data/data/angeloid.dreamnarae/files/miracle/01vsls > /system/etc/init.d/01vsls;");
		miracle.append("cat /data/data/angeloid.dreamnarae/files/miracle/02dch > /system/etc/init.d/02dch;");
		miracle.append("cat /data/data/angeloid.dreamnarae/files/miracle/03zipalign > /system/etc/init.d/03zipalign;");
		miracle.append("cat /data/data/angeloid.dreamnarae/files/miracle/98banner_dreamnarae_miracle > /system/98banner_dreamnarae_miracle;");
		miracle.append("cat /data/data/angeloid.dreamnarae/files/allflag > /system/allflag;");
		miracle.append("chmod 755 /system/etc/init.d/00set;");
		miracle.append("chmod 755 /system/etc/init.d/01vsls;");
		miracle.append("chmod 755 /system/etc/init.d/02dch;");
		miracle.append("chmod 755 /system/etc/init.d/03zipalign;");
		miracle.append("chmod 755 /system/98banner_dreamnarae_miracle;");
		miracle.append("chmod 755 /system/allflag;");
		miracle.append("busybox mount -o ro,remount /system;");
		Helper.instantExec(c, miracle.toString());
		Runtime.getRuntime().exec("su");
		String[] rmcommand = { "su", "-c",
				"rm /data/data/angeloid.dreamnarae/files/scriptrunner.sh" };
		Runtime.getRuntime().exec(rmcommand);
		InstallProcess.installcomplete(c);
	}

	public static void SaveVersion(Context c) throws IOException {
		DeleteVersion(c);
		StringBuilder save = new StringBuilder();
		save.append("mount -o rw,remount /system;");
		save.append("mkdir /system/etc/init.d;");
		save.append("cat /data/data/angeloid.dreamnarae/files/save/00sp > /system/etc/init.d/00sp;");
		save.append("cat /data/data/angeloid.dreamnarae/files/save/01v > /system/etc/init.d/01v;");
		save.append("cat /data/data/angeloid.dreamnarae/files/save/02deep > /system/etc/init.d/02deep;");
		save.append("cat /data/data/angeloid.dreamnarae/files/save/98banner_dreamnarae_save > /system/98banner_dreamnarae_save;");
		save.append("cat /data/data/angeloid.dreamnarae/files/allflag > /system/allflag;");
		save.append("chmod 755 /system/etc/init.d/00sp;");
		save.append("chmod 755 /system/etc/init.d/01v;");
		save.append("chmod 755 /system/etc/init.d/02deep;");
		save.append("chmod 755 /system/98banner_dreamnarae_save;");
		save.append("chmod 755 /system/allflag;");
		save.append("busybox mount -o ro,remount /system;");
		Helper.instantExec(c, save.toString());
		Runtime.getRuntime().exec("su");
		String[] rmcommand = { "su", "-c",
				"rm /data/data/angeloid.dreamnarae/files/scriptrunner.sh" };
		Runtime.getRuntime().exec(rmcommand);
		InstallProcess.installcomplete(c);
	}

	public static void PrevVersion(Context c) throws IOException {
		DeleteVersion(c);
		StringBuilder prev = new StringBuilder();
		prev.append("mount -o rw,remount /system;");
		prev.append("mkdir /system/etc/init.d;");
		prev.append("cat /data/data/angeloid.dreamnarae/files/prev/00proppv > /system/etc/init.d/00proppv;");
		prev.append("cat /data/data/angeloid.dreamnarae/files/prev/01iopv > /system/etc/init.d/01iopv;");
		prev.append("cat /data/data/angeloid.dreamnarae/files/prev/02freqpv > /system/etc/init.d/02freqpv;");
		prev.append("cat /data/data/angeloid.dreamnarae/files/prev/98banner_dreamnarae_prev > /system/98banner_dreamnarae_prev;");
		prev.append("cat /data/data/angeloid.dreamnarae/files/allflag > /system/allflag;");
		prev.append("chmod 755 /system/etc/init.d/00proppv;");
		prev.append("chmod 755 /system/etc/init.d/01iopv;");
		prev.append("chmod 755 /system/etc/init.d/02freqpv;");
		prev.append("chmod 755 /system/98banner_dreamnarae_prev;");
		prev.append("chmod 755 /system/allflag;");
		prev.append("busybox mount -o ro,remount /system;");
		Helper.instantExec(c, prev.toString());
		Runtime.getRuntime().exec("su");
		String[] rmcommand = { "su", "-c",
				"rm /data/data/angeloid.dreamnarae/files/scriptrunner.sh" };
		Runtime.getRuntime().exec(rmcommand);
		InstallProcess.installcomplete(c);
	}

	public static void SPiSaveVersion(Context c) throws IOException {
		DeleteVersion(c);
		StringBuilder spisave = new StringBuilder();
		spisave.append("mount -o rw,remount /system;");
		spisave.append("mkdir /system/etc/init.d;");
		spisave.append("cat /data/data/angeloid.dreamnarae/files/spisave/00prop > /system/etc/init.d/00prop;");
		spisave.append("cat /data/data/angeloid.dreamnarae/files/spisave/01io > /system/etc/init.d/01io;");
		spisave.append("cat /data/data/angeloid.dreamnarae/files/spisave/02freq > /system/etc/init.d/02freq;");
		spisave.append("cat /data/data/angeloid.dreamnarae/files/spisave/98banner_dreamnarae_spisave > /system/98banner_dreamnarae_spisave;");
		spisave.append("cat /data/data/angeloid.dreamnarae/files/allflag > /system/allflag;");
		spisave.append("chmod 755 /system/etc/init.d/00prop;");
		spisave.append("chmod 755 /system/etc/init.d/01io;");
		spisave.append("chmod 755 /system/etc/init.d/02freq;");
		spisave.append("chmod 755 /system/98banner_dreamnarae_spisave;");
		spisave.append("chmod 755 /system/allflag;");
		spisave.append("busybox mount -o ro,remount /system;");
		Helper.instantExec(c, spisave.toString());
		Runtime.getRuntime().exec("su");
		String[] rmcommand = { "su", "-c",
				"rm /data/data/angeloid.dreamnarae/files/scriptrunner.sh" };
		Runtime.getRuntime().exec(rmcommand);
		InstallProcess.installcomplete(c);
	}

	public static void BrandVersion(Context c) throws IOException {
		DeleteVersion(c);
		StringBuilder brand = new StringBuilder();
		brand.append("mount -o rw,remount /system;");
		brand.append("mkdir /system/etc/init.d;");
		brand.append("mkdir /system/etc/init.d;");
		brand.append("cat /data/data/angeloid.dreamnarae/files/brand/00b > /system/etc/init.d/00b;");
		brand.append("cat /data/data/angeloid.dreamnarae/files/brand/01r > /system/etc/init.d/01r;");
		brand.append("cat /data/data/angeloid.dreamnarae/files/brand/02and > /system/etc/init.d/02and;");
		brand.append("cat /data/data/angeloid.dreamnarae/files/brand/98banner_dreamnarae_brand > /system/98banner_dreamnarae_brand;");
		brand.append("cat /data/data/angeloid.dreamnarae/files/allflag > /system/allflag;");
		brand.append("chmod 755 /system/etc/init.d/00b;");
		brand.append("chmod 755 /system/etc/init.d/01r;");
		brand.append("chmod 755 /system/etc/init.d/02and;");
		brand.append("chmod 755 /system/98banner_dreamnarae_brand;");
		brand.append("chmod 755 /system/allflag;");
		brand.append("busybox mount -o ro,remount /system;");
		Helper.instantExec(c, brand.toString());
		Runtime.getRuntime().exec("su");
		String[] rmcommand = { "su", "-c",
				"rm /data/data/angeloid.dreamnarae/files/scriptrunner.sh" };
		Runtime.getRuntime().exec(rmcommand);
		InstallProcess.installcomplete(c);
	}

	public static void PureVersion(Context c) throws IOException {
		DeleteVersion(c);
		StringBuilder pure = new StringBuilder();
		pure.append("mount -o rw,remount /system;");
		pure.append("mkdir /system/etc/init.d;");
		pure.append("cat /data/data/angeloid.dreamnarae/files/pure/00cpu > /system/etc/init.d/00cpu;");
		pure.append("cat /data/data/angeloid.dreamnarae/files/pure/01memory > /system/etc/init.d/01memory;");
		pure.append("cat /data/data/angeloid.dreamnarae/files/pure/02prop > /system/etc/init.d/02prop;");
		pure.append("cat /data/data/angeloid.dreamnarae/files/pure/03cleaning > /system/etc/init.d/03cleaning;");
		pure.append("cat /data/data/angeloid.dreamnarae/files/pure/04zipalign > /system/etc/init.d/04zipalign;");
		pure.append("cat /data/data/angeloid.dreamnarae/files/pure/98banner_dreamnarae_pure > /system/98banner_dreamnarae_pure;");
		pure.append("cat /data/data/angeloid.dreamnarae/files/allflag > /system/allflag;");
		pure.append("chmod 755 /system/etc/init.d/00cpu;");
		pure.append("chmod 755 /system/etc/init.d/01memory;");
		pure.append("chmod 755 /system/etc/init.d/02prop;");
		pure.append("chmod 755 /system/etc/init.d/03cleaning;");
		pure.append("chmod 755 /system/etc/init.d/04zipalign;");
		pure.append("chmod 755 /system/98banner_dreamnarae_pure;");
		pure.append("chmod 755 /system/allflag;");
		pure.append("busybox mount -o ro,remount /system;");
		Helper.instantExec(c, pure.toString());
		Runtime.getRuntime().exec("su");
		String[] rmcommand = { "su", "-c",
				"rm /data/data/angeloid.dreamnarae/files/scriptrunner.sh" };
		Runtime.getRuntime().exec(rmcommand);
		InstallProcess.installcomplete(c);
	}

	public static void DeleteVersion(Context c) throws IOException {
		StringBuilder delete = new StringBuilder();
		delete.append("mount -o rw,remount /system;");
		delete.append("rm /system/98banner_dreamnarae_spica;");
		delete.append("rm /system/98banner_dreamnarae_miracle;");
		delete.append("rm /system/98banner_dreamnarae_save;");
		delete.append("rm /system/98banner_dreamnarae_prev;");
		delete.append("rm /system/98banner_dreamnarae_pure;");
		delete.append("rm /system/98banner_dreamnarae_brand;");
		delete.append("rm /system/98banner_dreamnarae_spisave;");
		delete.append("rm /system/97banner_dreamnarae_pe;");
		delete.append("rm /system/allflag;");
		delete.append("rm /system/etc/sysctl.sh;");
		delete.append("rm /system/etc/init.d/00prop;");
		delete.append("rm /system/etc/init.d/01io;");
		delete.append("rm /system/etc/init.d/02freq;");
		delete.append("rm /system/etc/init.d/03zipalign;");
		delete.append("rm /system/etc/init.d/01kswapd0;");
		delete.append("rm /system/etc/init.d/02io;");
		delete.append("rm /system/etc/init.d/03freq;");
		delete.append("rm /system/etc/init.d/04zipalign;");
		delete.append("rm /system/etc/init.d/00set;");
		delete.append("rm /system/etc/init.d/01property;");
		delete.append("rm /system/etc/init.d/02vsls;");
		delete.append("rm /system/etc/init.d/03dch;");
		delete.append("rm /system/etc/init.d/04zip;");
		delete.append("rm /system/etc/init.d/01vsls;");
		delete.append("rm /system/etc/init.d/02dch;");
		delete.append("rm /system/etc/init.d/00sp;");
		delete.append("rm /system/etc/init.d/01v;");
		delete.append("rm /system/etc/init.d/02deep;");
		delete.append("rm /system/etc/init.d/03zip;");
		delete.append("rm /system/etc/init.d/00proppv;");
		delete.append("rm /system/etc/init.d/01kswapd0pv;");
		delete.append("rm /system/etc/init.d/02iopv;");
		delete.append("rm /system/etc/init.d/03freqpv;");
		delete.append("rm /system/etc/init.d/04zippv;");
		delete.append("rm /system/etc/init.d/01iopv;");
		delete.append("rm /system/etc/init.d/02freqpv;");
		delete.append("rm /system/etc/init.d/00cpu;");
		delete.append("rm /system/etc/init.d/01loosy;");
		delete.append("rm /system/etc/init.d/02memory;");
		delete.append("rm /system/etc/init.d/03prop;");
		delete.append("rm /system/etc/init.d/04cleaning;");
		delete.append("rm /system/etc/init.d/00b;");
		delete.append("rm /system/etc/init.d/01r;");
		delete.append("rm /system/etc/init.d/02and;");
		delete.append("rm /system/etc/init.d/00cleaning;");
		delete.append("rm /system/etc/init.d/01cpu;");
		delete.append("rm /system/etc/init.d/02sysctl;");
		delete.append("rm /system/etc/init.d/03memory;");
		delete.append("rm /system/etc/init.d/04prop;");
		delete.append("rm /system/etc/init.d/05zipalign;");
		delete.append("rm /system/etc/init.d/06sysctl;");
		delete.append("rm /system/etc/init.d/00cpu;");
		delete.append("rm /system/etc/init.d/01memory;");
		delete.append("rm /system/etc/init.d/02prop;");
		delete.append("rm /system/etc/init.d/03cleaning;");
		delete.append("rm /system/etc/init.d/04zipalign;");
		delete.append("busybox mount -o ro,remount /system;");
		Helper.instantExec(c, delete.toString());
		Runtime.getRuntime().exec("su");
		String[] rmcommand = { "su", "-c",
				"rm /data/data/angeloid.dreamnarae/files/scriptrunner.sh" };
		Runtime.getRuntime().exec(rmcommand);
	}

	public static void installcomplete(Context c) throws IOException {
		File file = new File("/system/allflag");
		if (file.length() > 0) {
			Log.d("Install", "Install Success!");
			TextView txtTitle = (TextView) view.findViewById(R.id.title);
			txtTitle.setText(R.string.reboottitle);
			txtTitle.setTextColor(Color.WHITE);
			txtTitle.setTextSize(20);
			TextView message = (TextView) view.findViewById(R.id.message);
			message.setText(R.string.rebootmessage);
			message.setTextColor(Color.WHITE);
			AlertDialog.Builder builder = new Builder(c);
			builder.setView(view);
			builder.setPositiveButton(R.string.yes,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();

							try {
								Runtime.getRuntime().exec("su");
								String[] bootcommand = { "su", "-c", "reboot" };
								Runtime.getRuntime().exec(bootcommand);
							} catch (Exception e) {

							}
							;
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
			TextView txtTitle = (TextView) view1.findViewById(R.id.title);
			txtTitle.setText(R.string.errortitle);
			txtTitle.setTextColor(Color.WHITE);
			txtTitle.setTextSize(20);
			TextView message = (TextView) view1.findViewById(R.id.message);
			message.setText(R.string.error2message);
			message.setTextColor(Color.WHITE);
			AlertDialog.Builder builder = new Builder(c);
			builder.setView(view1);
			builder.setPositiveButton(R.string.infoclose,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();

						}
					}

			).show();
			DeleteVersion(c);
		}
	}

	public static void DeleteComplete(Context c) throws IOException {
		File file = new File("/system/allflag");
		if (file.length() > 0) {
			Log.d("Install", "Delete Failed");
			TextView txtTitle = (TextView) view1.findViewById(R.id.title);
			txtTitle.setText(R.string.errortitle);
			txtTitle.setTextColor(Color.WHITE);
			txtTitle.setTextSize(20);
			TextView message = (TextView) view1.findViewById(R.id.message);
			message.setText(R.string.error2message);
			message.setTextColor(Color.WHITE);
			AlertDialog.Builder builder = new Builder(c);
			builder.setView(view1);
			builder.setPositiveButton(R.string.infoclose,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					}

			).show();
			DeleteVersion(c);
		} else {
			TextView txtTitle = (TextView) view.findViewById(R.id.title);
			txtTitle.setText(R.string.reboottitle);
			txtTitle.setTextColor(Color.WHITE);
			txtTitle.setTextSize(20);
			TextView message = (TextView) view.findViewById(R.id.message);
			message.setText(R.string.rebootmessage);
			message.setTextColor(Color.WHITE);
			AlertDialog.Builder builder = new Builder(c);
			builder.setView(view);
			builder.setPositiveButton(R.string.yes,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();

							try {
								Runtime.getRuntime().exec("su");
								String[] bootcommand = { "su", "-c", "reboot" };
								Runtime.getRuntime().exec(bootcommand);
							} catch (Exception e) {

							}
							;
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
}