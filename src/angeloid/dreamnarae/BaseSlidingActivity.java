package angeloid.dreamnarae;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

public class BaseSlidingActivity extends SlidingActivity {

	// ListView
	private ArrayList<DNMenu> Array_Data;
	private DNMenu data;
	private ListAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBehindContentView(R.layout.slidingmenumain);

		// ListView
		ListView list = (ListView) findViewById(R.id.list);
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
		data = new DNMenu(R.drawable.icon_donate, getString(R.string.donate),
				getString(R.string.donate_sub));
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
					cls = Donate.class;
				} else if (position == 6) {
					cls = SPiCa.class;
				} else if (position == 7) {
					cls = Pure.class;
				} else if (position == 8) {
					cls = Save.class;
				} else if (position == 9) {
					cls = Prev.class;
				} else if (position == 10) {
					cls = Brand.class;
				} else if (position == 11) {
					cls = Miracle.class;
				} else if (position == 12) {
					cls = SPiSave.class;
				}
				Intent intent = new Intent(BaseSlidingActivity.this, cls);
				startActivity(intent);
				finish();
			}
		});

	}
}
