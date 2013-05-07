/* DreamNarae MainActivity
 * based on KakaoLink at kakao
 * based on SlideHolder at Dmitry Zaitsev
 * 
 * Edit by Sopiane(www.sirospace.com) in Angeloid Team
 */
package angeloid.dreamnarae;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class MainActivity extends Activity implements SensorEventListener {

	// Layout
	TextView LayoutTitle;
	TextView LayoutTitle2;

	// Easter Egg
	private static Random m_rand = new Random();
	String random = "";
	String random1 = "";
	String easteregg1 = "O";
	String man = "10000";
	static String easteregg = "";

	// Sensor
	private long lastTime;
	private float speed;
	private float lastX;
	private float lastY;
	private float x, y;
	private static final int SHAKE_THRESHOLD = 800;
	private static final int DATA_X = SensorManager.AXIS_X;
	private static final int DATA_Y = SensorManager.AXIS_Y;
	private SensorManager sensorManager;
	private Sensor accelerormeterSensor;

	// Kakao Link / Story Link
	private String encoding = "UTF-8";

	// Slide Holder
	private SlideMenu mSlideHolder;

	// Setting
	boolean mainvoice = false;

	// MediaPlayer
	MediaPlayer mplayer;

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

	// MainFillper
	ImageView iv1;
	ImageView iv2;
	ImageView iv3;
	ImageView iv4;
	ImageView iv5;
	ImageView iv6;
	ImageView iv7;
	ImageView iv8;
	ImageView iv9;
	ImageView iv10;
	ImageView iv11;
	ViewFlipper vf;

	// Fonts
	public static Typeface Font;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab1);

		// Disable StrickMode Policy
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		// Layout
		LayoutTitle = (TextView) findViewById(R.id.tabtextview);
		LayoutTitle.setTypeface(MainActivity.Font);
		LayoutTitle2 = (TextView) findViewById(R.id.tabtextview2);
		LayoutTitle2.setTypeface(MainActivity.Font);

		// Font
		initializeTypefaces();

		// Settings
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(MainActivity.this);
		boolean statvoice = prefs.getBoolean("mainvoice", false);
		mainvoice = statvoice;

		if (mainvoice == true) {
			mplayer = MediaPlayer.create(MainActivity.this, R.raw.welcome);
			mplayer.start();
		}

		// Sensor
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		accelerormeterSensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		// Easter Egg
		random = String.valueOf(m_rand.nextInt(1000 + 1));
		MainActivity.easteregg = random;

		// SlideHolder
		mSlideHolder = (SlideMenu) findViewById(R.id.slideHolder);
		mSlideHolder.toggle();

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

		// MainFippler
		vf = (ViewFlipper) findViewById(R.id.viewFlipper1);
		iv1 = (ImageView) findViewById(R.id.imageView1);
		iv2 = (ImageView) findViewById(R.id.imageView2);
		iv3 = (ImageView) findViewById(R.id.imageView3);
		iv4 = (ImageView) findViewById(R.id.imageView4);
		iv5 = (ImageView) findViewById(R.id.imageView5);
		iv6 = (ImageView) findViewById(R.id.imageView6);
		iv7 = (ImageView) findViewById(R.id.imageView7);
		iv8 = (ImageView) findViewById(R.id.imageView8);
		iv9 = (ImageView) findViewById(R.id.imageView9);
		iv10 = (ImageView) findViewById(R.id.imageView10);
		iv11 = (ImageView) findViewById(R.id.imageView11);
		vf.setFlipInterval(6500);
		vf.setInAnimation(AnimationUtils.loadAnimation(MainActivity.this,
				R.anim.left_in));
		vf.setOutAnimation(AnimationUtils.loadAnimation(MainActivity.this,
				R.anim.left_out));
		vf.startFlipping();

		// SDCard Check
		StatFs stat = new StatFs(Environment.getExternalStorageDirectory()
				.getPath());
		double sdAvailSize = (double) stat.getAvailableBlocks()
				* (double) stat.getBlockSize();
		double megaAvailable = sdAvailSize / 1048576;
		if (megaAvailable < 10.0) {
			View view = this.getLayoutInflater().inflate(R.layout.customdialog,
					null);
			TextView txtTitle = (TextView) view.findViewById(R.id.title);
			txtTitle.setText(R.string.sdcarderrortitle);
			txtTitle.setTextColor(Color.WHITE);
			txtTitle.setTextSize(20);
			txtTitle.setTypeface(MainActivity.Font);
			TextView message = (TextView) view.findViewById(R.id.message);
			message.setText(R.string.sdcarderrormessage);
			message.setTextColor(Color.WHITE);
			message.setTypeface(MainActivity.Font);
			AlertDialog.Builder builder1 = new Builder(MainActivity.this);
			builder1.setView(view);
			builder1.setCancelable(false);
			builder1.setPositiveButton(R.string.infoclose,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							finish();
						}
					}).show();
		}

		// SU Check
		if (!(new File("/system/bin/su").exists())
				&& !(new File("/system/xbin/su").exists())
				&& !(new File("/system/bin/busybox").exists())) {
			norootmode();
		}
	}

	private void initializeTypefaces() {
		MainActivity.Font = Typeface.createFromAsset(getAssets(),
				"fonts/Arita.otf");
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
		AlertDialog.Builder builder = new Builder(MainActivity.this);
		builder.setView(view);
		builder.setPositiveButton(android.R.string.ok, null).create().show();
	}

	// Non Root Mode
	public void norootmode() {
		SharedPreferences prefs = getSharedPreferences("norootmode",
				MODE_PRIVATE);
		SharedPreferences.Editor ed = prefs.edit();
		ed.putString("norootmode", "true");
		ed.commit();
		Toast.makeText(this, R.string.noroottoast, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onStart() {
		super.onStart();

		if (accelerormeterSensor != null)
			sensorManager.registerListener(this, accelerormeterSensor,
					SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	public void onStop() {
		super.onStop();

		if (sensorManager != null)
			sensorManager.unregisterListener(this);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			long currentTime = System.currentTimeMillis();
			long gabOfTime = (currentTime - lastTime);

			if (gabOfTime > 100) {
				lastTime = currentTime;

				x = event.values[SensorManager.AXIS_X];
				y = event.values[SensorManager.AXIS_Y];

				speed = Math.abs(x + y - lastX - lastY) / gabOfTime * 10000;

				if (speed > SHAKE_THRESHOLD) {
					handleShakeEvent();
				}
				lastX = event.values[DATA_X];
				lastY = event.values[DATA_Y];
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			View view = this.getLayoutInflater().inflate(R.layout.customdialog,
					null);
			TextView txtTitle = (TextView) view.findViewById(R.id.title);
			txtTitle.setText(R.string.quittitle);
			txtTitle.setTextColor(Color.WHITE);
			txtTitle.setTextSize(20);
			txtTitle.setTypeface(MainActivity.Font);
			TextView message = (TextView) view.findViewById(R.id.message);
			message.setText(R.string.quitmessage);
			message.setTextColor(Color.WHITE);
			message.setTypeface(MainActivity.Font);
			AlertDialog.Builder builer = new AlertDialog.Builder(this);
			builer.setView(view);
			builer.setPositiveButton(android.R.string.yes,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							android.os.Process.killProcess(android.os.Process
									.myPid());
						}
					});
			builer.setNegativeButton(android.R.string.no, null).show();

			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	public void handleShakeEvent() {
		int i = Integer.parseInt(man) - 1;
		man = String.valueOf(i);
		String secondegg1 = getString(R.string.secondegg1);
		String secondegg2 = getString(R.string.easteregg2);
		String secondegg3 = getString(R.string.secondegg2);
		CharSequence toasttrick = secondegg1 + " " + man + " " + secondegg2;
		Toast.makeText(this, toasttrick, Toast.LENGTH_SHORT).show();
		Log.d("Tab_MainActivity", man);
		if (man.equals("0")) {
			startActivity(new Intent(MainActivity.this, SecondEgg.class));
			man = "10000";
			Toast.makeText(this, secondegg3, Toast.LENGTH_SHORT).show();
		}

	}

	public void eastereggevent(View v) {
		mSlideHolder.toggle();
		int i = Integer.parseInt(easteregg) - 1;
		MainActivity.easteregg = String.valueOf(i);
		String easteregg2 = getString(R.string.easteregg1);
		String easteregg3 = getString(R.string.easteregg2);
		String easteregg4 = getString(R.string.easteregg3);
		CharSequence toasttrick = easteregg2 + " " + MainActivity.easteregg
				+ " " + easteregg3;
		Toast.makeText(this, toasttrick, Toast.LENGTH_SHORT).show();
		Log.d("Tab_MainActivity", MainActivity.easteregg);
		if (MainActivity.easteregg.equals("0")) {
			startActivity(new Intent(MainActivity.this, EasterEgg.class));
			random = String.valueOf(m_rand.nextInt(1000 + 1));
			MainActivity.easteregg = random;
			Toast.makeText(this, easteregg4, Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {

	}

	public void mainscreen(View v) {
		// startActivity(new Intent(this, MainActivity.class));
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
		startActivity(new Intent(this, Brand.class));
		finish();
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