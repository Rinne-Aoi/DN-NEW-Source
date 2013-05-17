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

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

public class MainActivity extends SlidingActivity implements SensorEventListener {
	// Layout
	TextView LayoutTitle;
	TextView LayoutTitle2;

	// Easter Egg
	private static Random m_rand = new Random();
	String random = "";
	String random1 = "";
	String easteregg1 = "O";
	String man = "20000";
	static String easteregg = "";
	String intro = "5";

	// Sensor
	private long lastTime;
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

	// MediaPlayer
	MediaPlayer mplayer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        setBehindContentView(R.layout.menu);
        // SlideingMenu
        ListView list =(ListView) findViewById(R.id.list);
        ArrayAdapter<CharSequence> menu_array = ArrayAdapter
                .createFromResource(MainActivity.this, R.array.menu,
                        R.layout.listviewlayout);
        list.setAdapter(menu_array);
        SlidingMenu menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.menu);

		// Font
		initializeTypefaces();

		// Layout
		LayoutTitle = (TextView) findViewById(R.id.tabtextview);
		LayoutTitle.setTypeface(MainActivity.Font);

		// Sensor
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		accelerormeterSensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		// Easter Egg
		random = String.valueOf(m_rand.nextInt(1000 + 1));
		MainActivity.easteregg = random;

		// MediaPlayer
		mplayer = MediaPlayer.create(MainActivity.this, R.raw.fullintro);

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

	}

	private void initializeTypefaces() {
		MainActivity.Font = Typeface.createFromAsset(getAssets(),
				"fonts/Arita.otf");
	}

	public void sendAppData(View v) throws NameNotFoundException {
		ArrayList<Map<String, String>> metaInfoArray = new ArrayList<Map<String, String>>();
		Map<String, String> metaInfoAndroid = new Hashtable<String, String>(1);
		metaInfoAndroid.put("os", "android");
		metaInfoAndroid.put("devicetype", "phone");
		metaInfoAndroid.put("installurl",
				"market://details?id=angeloid.dreamnarae");
		metaInfoAndroid.put("executeurl", "kakaoLinkTest://starActivity");

		metaInfoArray.add(metaInfoAndroid);

		KakaoLink kakaoLink = KakaoLink.getLink(getApplicationContext());
		if (!kakaoLink.isAvailableIntent()) {
			alert(getString(R.string.ishavekatok));
			return;
		}

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

	public void Introduce(View v) {
		int i = Integer.parseInt(intro) - 1;
		intro = String.valueOf(i);
		String secondegg1 = getString(R.string.introduce1);
		String secondegg2 = getString(R.string.easteregg2);
		CharSequence toasttrick = secondegg1 + " " + intro + " " + secondegg2;
		if (mplayer.isPlaying() == false) {
			Toast.makeText(this, toasttrick, Toast.LENGTH_SHORT).show();
			Log.d("Tab_MainActivity", intro);
		}
		if (intro.equals("0")) {
			intro = "5";
			mplayer.start();
			if (mplayer.isPlaying() == true) {
				Toast.makeText(this, R.string.introduce2, Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	public void eastereggevent(View v) {
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
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
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

                float speed = Math.abs(x + y - lastX - lastY) / gabOfTime * 10000;

				if (speed > SHAKE_THRESHOLD) {
                    handleShakeEvent();
                }
				lastX = event.values[DATA_X];
				lastY = event.values[DATA_Y];
			}
		}
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

}