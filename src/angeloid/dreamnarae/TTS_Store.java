package angeloid.dreamnarae;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TTS_Store extends FragmentActivity {
	private SwipeyTabs mTabs;
	private ViewPager mViewPager;
	LinearLayout f;
	TextView tabtextview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ttsstore_main);
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		mTabs = (SwipeyTabs) findViewById(R.id.swipeytabs);
		tabtextview = (TextView) findViewById(R.id.tabtextview);
		f = (LinearLayout) findViewById(R.id.main_layout2);
		SwipeyTabsPagerAdapter adapter = new SwipeyTabsPagerAdapter(this,
				getSupportFragmentManager());
		mViewPager.setAdapter(adapter);
		mTabs.setAdapter(adapter);
		mViewPager.setOnPageChangeListener(mTabs);
		mViewPager.setCurrentItem(0);
	}

	private class SwipeyTabsPagerAdapter extends FragmentPagerAdapter implements
			SwipeyTabsAdapter {

		private final Context mContext;

		private String[] TITLES = { getString(R.string.maid),
				getString(R.string.friend), getString(R.string.narae) };

		private Fragment[] FRAGMENTS = {new TTSStore_Maid(), new TTSStore_Friends(), new TTSStore_Narae()};

		public SwipeyTabsPagerAdapter(Context context, FragmentManager fm) {
			super(fm);
			this.mContext = context;
		}

		@Override
		public Fragment getItem(int position) {
			return FRAGMENTS[position];
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}

		public TextView getTab(final int position, SwipeyTabs root) {
			TextView view = (TextView) LayoutInflater.from(mContext).inflate(
					R.layout.swipey_tab_indicator, root, false);
			view.setText(TITLES[position]);
			view.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					mViewPager.setCurrentItem(position);
				}
			});

			return view;
		}
	}
}
