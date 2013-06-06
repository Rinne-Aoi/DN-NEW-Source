package angeloid.dreamnarae;

import java.util.ArrayList;
import java.util.Random;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import angeloid.dreamnarae.cleaner.Cleaner;

import com.bugsense.trace.BugSenseHandler;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

public class BaseSlidingActivity extends SlidingActivity {

	// ListView
	private ArrayList<DNMenu> Array_Data;
	private DNMenu data;
	private ListAdapter adapter;
	ListView list;

	// Easter Egg
	private static Random m_rand = new Random();
	EditText hiddenedit;
	TextView hidden1;
	String random2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBehindContentView(R.layout.slidingmenumain);
		BugSenseHandler.initAndStartSession(BaseSlidingActivity.this,
				"431c24dd");

		// Easter Egg
		random2 = String.valueOf(m_rand.nextInt(30 + 1));

		// ListView
		list = (ListView) findViewById(R.id.list);
		Array_Data = new ArrayList<DNMenu>();
		data = new DNMenu(R.drawable.icon_home, getString(R.string.main),
				getString(R.string.main_sub));
		Array_Data.add(data);
		data = new DNMenu(R.drawable.icon_updatelog,
				getString(R.string.update), getString(R.string.update_sub));
		Array_Data.add(data);
		data = new DNMenu(R.drawable.icon_promoting,
				getString(R.string.promoting),
				getString(R.string.promoting_sub));
		Array_Data.add(data);
		data = new DNMenu(R.drawable.icon_settings,
				getString(R.string.setting), getString(R.string.setting_sub));
		Array_Data.add(data);
		data = new DNMenu(R.drawable.icon_developerinfo,
				getString(R.string.developerinfo),
				getString(R.string.developerinfo_sub));
		Array_Data.add(data);
		data = new DNMenu(R.drawable.icon_guide, getString(R.string.guide),
				getString(R.string.guide_sub));
		Array_Data.add(data);
		data = new DNMenu(R.drawable.icon_cleaner, getString(R.string.cleaner),
				getString(R.string.cleaner_sub));
		Array_Data.add(data);
		data = new DNMenu(R.drawable.icon_ttsstore,
				getString(R.string.ttsstore), getString(R.string.ttsstore_sub));
		Array_Data.add(data);
		data = new DNMenu(R.drawable.icon_spica, getString(R.string.spica),
				getString(R.string.spica_sub));
		Array_Data.add(data);
		data = new DNMenu(R.drawable.icon_pure, getString(R.string.pure),
				getString(R.string.pure_sub));
		Array_Data.add(data);
		data = new DNMenu(R.drawable.icon_save, getString(R.string.save),
				getString(R.string.save_sub));
		Array_Data.add(data);
		data = new DNMenu(R.drawable.icon_prev, getString(R.string.prev),
				getString(R.string.prev_sub));
		Array_Data.add(data);
		data = new DNMenu(R.drawable.icon_brand, getString(R.string.brand),
				getString(R.string.brand_sub));
		Array_Data.add(data);
		data = new DNMenu(R.drawable.icon_miracle, getString(R.string.miracle),
				getString(R.string.miracle_sub));
		Array_Data.add(data);
		data = new DNMenu(R.drawable.icon_spisave, getString(R.string.spisave),
				getString(R.string.spisave_sub));
		Array_Data.add(data);
		adapter = new ListAdapter(this, R.layout.listviewlayout, Array_Data);
		list.setAdapter(adapter);
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
					cls = Promoting.class;
				} else if (position == 3) {
					cls = Setting.class;
				} else if (position == 4) {
					cls = Developer_Info.class;
				} else if (position == 5) {
					cls = GuideActivity.class;
				} else if (position == 6) {
					cls = Cleaner.class;
				} else if (position == 7) {
					cls = TTS_Store.class;
				} else if (position == 8) {
					cls = SPiCa.class;
				} else if (position == 9) {
					cls = Pure.class;
				} else if (position == 10) {
					cls = Save.class;
				} else if (position == 11) {
					cls = Prev.class;
				} else if (position == 12) {
					cls = Brand.class;
				} else if (position == 13) {
					cls = Miracle.class;
				} else if (position == 14) {
					cls = SPiSave.class;
				} 
				Intent intent = new Intent(BaseSlidingActivity.this, cls);
				startActivity(intent);
				finish();
			}
		});

	}

	public void hiddengo(View v) {
		hiddenedit = (EditText) findViewById(R.id.hiddenedit);
		hidden1 = (TextView) findViewById(R.id.hidden1);
		if (random2.equals(hiddenedit.getText().toString())) {
			startActivity(new Intent(BaseSlidingActivity.this, Hidden.class));
		} else {
			hidden1.setText(R.string.wrongpw);
			hidden1.setTextColor(getResources().getColor(R.color.Red));
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
}
