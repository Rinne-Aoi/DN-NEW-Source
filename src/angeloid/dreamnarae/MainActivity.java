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

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import angeloid.dreamnarae.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

public class MainActivity extends SlidingActivity implements
		SensorEventListener {

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

	// Kakao Link
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

	// MediaPlayer
	MediaPlayer mplayer;
	
	// Drawable
	Drawable icon_brand;
	Drawable icon_delete;
	Drawable icon_developerinfo;
	Drawable icon_donate;
	Drawable icon_home;
	Drawable icon_miracle;
	Drawable icon_prev;
	Drawable icon_promoting;
	Drawable icon_pure;
	Drawable icon_save;
	Drawable icon_settings;
	Drawable icon_spica;
	Drawable icon_spisave;
	Drawable icon_updatelog;
	TypedArray Menu;
	TypedArray Tweak;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setBehindContentView(R.layout.slidingmenumain);
		
		// MenuIcon
		Resources res = getResources();
		Menu = res.obtainTypedArray(R.array.menu);
		Tweak = res.obtainTypedArray(R.array.tweak);
		icon_brand = res.getDrawable(R.drawable.icon_brand);
		icon_delete = res.getDrawable(R.drawable.icon_delete);
		icon_developerinfo = res.getDrawable(R.drawable.icon_developerinfo);
		icon_donate = res.getDrawable(R.drawable.icon_donate);
		icon_home = res.getDrawable(R.drawable.icon_home);
		icon_miracle = res.getDrawable(R.drawable.icon_miracle);
		icon_prev = res.getDrawable(R.drawable.icon_prev);
		icon_promoting = res.getDrawable(R.drawable.icon_promoting);
		icon_pure = res.getDrawable(R.drawable.icon_pure);
		icon_save = res.getDrawable(R.drawable.icon_save);
		icon_settings = res.getDrawable(R.drawable.icon_settings);
		icon_spica = res.getDrawable(R.drawable.icon_spica);
		icon_spisave = res.getDrawable(R.drawable.icon_spisave);
		icon_updatelog = res.getDrawable(R.drawable.icon_updatelog);
	

		// SlideingMenu
		View header = getLayoutInflater().inflate(R.layout.header, null, false);
		header.setClickable(false);
		ListView list = (ListView) findViewById(R.id.list);
		ListView list2 = (ListView) findViewById(R.id.list2);
		ArrayAdapter<CharSequence> menu_array = ArrayAdapter
				.createFromResource(MainActivity.this, R.array.menu,
						R.layout.listviewlayout);
		ArrayAdapter<CharSequence> menu_array2 = ArrayAdapter
				.createFromResource(MainActivity.this, R.array.tweak,
						R.layout.listviewlayout);
		list2.addHeaderView(header);
		list.setAdapter(menu_array);
		list2.setAdapter(menu_array2);
		
		list.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Class<?> cls = null;
				if (position == 0) {
					cls = MainActivity.class;
				} else if (position == 1) {
					cls = Update_Main.class;
				} else if (position == 2) {
					try {
						sendAppData(view);
					} catch (NameNotFoundException e) {
						e.printStackTrace();
					}
				} else if (position == 3) {
					cls = Setting.class;
				} else if (position == 4) {
					cls = Developer_Info.class;
				} else if (position == 5) {
					cls = Donate.class;
				}
				Intent intent = new Intent(MainActivity.this, cls);
				startActivity(intent);
			}

		});
		list2.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Class<?> cls = null;
				if (position == 1) {
					cls = SPiCa.class;
				} else if (position == 2) {
					cls = Pure.class;
				} else if (position == 3) {
					cls = Save.class;
				} else if (position == 4) {
					cls = Prev.class;
				} else if (position == 5) {
					cls = Miracle.class;
				} else if (position == 6) {
					cls = Brand.class;
				} else if (position == 7) {
					cls = SPiSave.class;
				} else if (position == 8) {
					cls = Delete.class;
				}
				Intent intent = new Intent(MainActivity.this, cls);
				startActivity(intent);
			}

		});

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
			TextView message = (TextView) view.findViewById(R.id.message);
			message.setText(R.string.quitmessage);
			message.setTextColor(Color.WHITE);
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

				float speed = Math.abs(x + y - lastX - lastY) / gabOfTime
						* 10000;

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

	  public class MenuAdapter extends BaseAdapter {
		private ArrayList<MenuItem> object;

		public MenuAdapter(ArrayList<MenuItem> object) {
			super();
			this.object = object;
		}

		@Override
		public int getCount() {
			return object.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}


		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater
						.from(MainActivity.this);
				convertView = inflater.inflate(R.layout.dnmenulayout, parent,
						false);
				holder = new ViewHolder();

				// Find View
				holder.icon = (ImageView) convertView.findViewById(R.id.icon);
				holder.TweakName = (TextView) convertView
						.findViewById(R.id.TweakName);
				convertView.setTag(holder);
			} else
				holder = (ViewHolder) convertView.getTag();

			Drawable icon = object.get(position).getIcon();
			String Tname = object.get(position).getName();

			// Set Resources
			holder.icon.setImageDrawable(icon);
			holder.TweakName.setText(Tname);
			return convertView;
		}

		class ViewHolder {
			ImageView icon;
			TextView TweakName;
		}

 	} 

}