package angeloid.dreamnarae;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import com.stericson.RootTools.RootTools;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Brand extends Activity {

	Button apply;
	Button info;
	static MediaPlayer mplayer;
	ImageView imageview;
	// Layout
	TextView LayoutTitle;
	TextView LayoutTitle2;

	// Slide Menu
	Button main;
	Button update;
	Button spica;
	Button pure;
	Button save;
	Button prev;
	Button miracle;
	Button brand;
	Button spisave;
	Button delete;
	Button promoting;
	Button setting;
	Button developerinfo;
	Button donate;

	// Kakao Link / Story Link
	private String encoding = "UTF-8";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.brand);
		// Layout
		LayoutTitle = (TextView) findViewById(R.id.tabtextview);
		LayoutTitle.setTypeface(MainActivity.Font);
		LayoutTitle2 = (TextView) findViewById(R.id.tabtextview2);
		LayoutTitle2.setTypeface(MainActivity.Font);

		// Slide Menu
		main = (Button) findViewById(R.id.mainscreen);
		update = (Button) findViewById(R.id.updatelog);
		spica = (Button) findViewById(R.id.spica);
		pure = (Button) findViewById(R.id.pure);
		save = (Button) findViewById(R.id.save);
		prev = (Button) findViewById(R.id.prev);
		miracle = (Button) findViewById(R.id.miracle);
		brand = (Button) findViewById(R.id.brand);
		spisave = (Button) findViewById(R.id.spisave);
		delete = (Button) findViewById(R.id.delete);
		promoting = (Button) findViewById(R.id.promoting);
		setting = (Button) findViewById(R.id.setting);
		developerinfo = (Button) findViewById(R.id.developerinfo);
		donate = (Button) findViewById(R.id.donate);

		// Slide Menu Fonts
		main.setTypeface(MainActivity.Font);
		update.setTypeface(MainActivity.Font);
		spica.setTypeface(MainActivity.Font);
		pure.setTypeface(MainActivity.Font);
		save.setTypeface(MainActivity.Font);
		prev.setTypeface(MainActivity.Font);
		miracle.setTypeface(MainActivity.Font);
		brand.setTypeface(MainActivity.Font);
		spisave.setTypeface(MainActivity.Font);
		delete.setTypeface(MainActivity.Font);
		promoting.setTypeface(MainActivity.Font);
		setting.setTypeface(MainActivity.Font);
		developerinfo.setTypeface(MainActivity.Font);
		donate.setTypeface(MainActivity.Font);
		apply = (Button) findViewById(R.id.apply);
		info = (Button) findViewById(R.id.info);
		apply.setTypeface(MainActivity.Font);
		info.setTypeface(MainActivity.Font);
		imageview = (ImageView) findViewById(R.id.imageview);
		mplayer = MediaPlayer.create(Brand.this, R.raw.brand);
		apply.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (RootTools.isAccessGiven()) {
					try {
						DownloadProcess.URLCheck();
						Intent();
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
				} else {
					Toast.makeText(Brand.this, R.string.noroottoast, Toast.LENGTH_LONG)
							.show();
				}

			}
		});
		if (new File("/system/98banner_dreamnarae_brand").exists()) {
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

	public void Intent() {
		Intent intent = new Intent(this, RootToolsInstallProcess.class);
		intent.putExtra("Version", "Brand");
		startActivity(intent);
	}

	public void dialog() {
		View view = this.getLayoutInflater().inflate(R.layout.customdialog,
				null);
		TextView txtTitle = (TextView) view.findViewById(R.id.title);
		txtTitle.setText(R.string.brand_title);
		txtTitle.setTextColor(Color.WHITE);
		txtTitle.setTextSize(20);
		TextView message = (TextView) view.findViewById(R.id.message);
		message.setText(R.string.brand_info);
		message.setTextColor(Color.WHITE);
		AlertDialog.Builder builder = new Builder(Brand.this);
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

	/**
	 * Send App data
	 */
	public void sendAppData(View v) throws NameNotFoundException {
		ArrayList<Map<String, String>> metaInfoArray = new ArrayList<Map<String, String>>();

		// If application is support Android platform.
		Map<String, String> metaInfoAndroid = new Hashtable<String, String>(1);
		metaInfoAndroid.put("os", "android");
		metaInfoAndroid.put("devicetype", "phone");
		metaInfoAndroid.put("installurl",
				"market://details?id=angeloid.dreamnarae");
		metaInfoAndroid.put("executeurl", "kakaoLinkTest://starActivity");

		// add to array
		metaInfoArray.add(metaInfoAndroid);

		// Recommended: Use application context for parameter.
		KakaoLink kakaoLink = KakaoLink.getLink(getApplicationContext());

		// check, intent is available.
		if (!kakaoLink.isAvailableIntent()) {
			alert("Not installed KakaoTalk.");
			return;
		}

		/**
		 * @param activity
		 * @param url
		 * @param message
		 * @param appId
		 * @param appVer
		 * @param appName
		 * @param encoding
		 * @param metaInfoArray
		 */
		String app_name = getString(R.string.app_name);
		String message = getString(R.string.kakaotalkmessage);
		kakaoLink
				.openKakaoAppLink(
						this,
						"market://details?id=angeloid.dreamnarae",
						message,
						getPackageName(),
						getPackageManager().getPackageInfo(getPackageName(), 0).versionName,
						app_name, encoding, metaInfoArray);
	}

	private void alert(String message) {
		View view = this.getLayoutInflater().inflate(R.layout.customdialog,
				null);
		TextView txtTitle = (TextView) view.findViewById(R.id.title);
		txtTitle.setText(R.string.app_name);
		txtTitle.setTextColor(Color.WHITE);
		txtTitle.setTextSize(20);
		TextView message1 = (TextView) view.findViewById(R.id.message);
		message1.setText(message);
		message1.setTextColor(Color.WHITE);
		AlertDialog.Builder builder = new Builder(Brand.this);
		builder.setView(view);
		builder.setPositiveButton(android.R.string.ok, null).create().show();
	}

	public void mainscreen(View v) {
		startActivity(new Intent(this, MainActivity.class));
		finish();
	}

	public void updatelog(View v) {
		startActivity(new Intent(this, Update_Main.class));
		finish();
	}

	public void spicascreen(View v) {
		startActivity(new Intent(this, SPiCa.class));
		finish();
	}

	public void purescreen(View v) {
		startActivity(new Intent(this, Pure.class));
		finish();
	}

	public void savescreen(View v) {
		startActivity(new Intent(this, Save.class));
		finish();
	}

	public void prevscreen(View v) {
		startActivity(new Intent(this, Prev.class));
		finish();
	}

	public void miraclescreen(View v) {
		startActivity(new Intent(this, Miracle.class));
		finish();
	}

	public void brandscreen(View v) {
		// startActivity(new Intent(this, Brand.class));
	}

	public void spisavescreen(View v) {
		startActivity(new Intent(this, SPiSave.class));
		finish();
	}

	public void deletescreen(View v) {
		startActivity(new Intent(this, Delete.class));
		finish();
	}

	public void promotingscreen(View v) {
		try {
			sendAppData(v);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void settingscreen(View v) {
		startActivity(new Intent(this, Settings.class));
		finish();
	}

	public void developerinfoscreen(View v) {
		startActivity(new Intent(this, Developer_Info.class));
		finish();
	}

	public void donatescreen(View v) {
		startActivity(new Intent(this, Donate.class));
		finish();
	}
}