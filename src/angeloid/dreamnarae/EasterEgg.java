package angeloid.dreamnarae;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class EasterEgg extends Activity {
	TextView tab;
	ImageView present;
	TextView title;
	TextView remain;
	TextView obtain;
	static String easteregg = "1500";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.easteregg);
		tab = (TextView) findViewById(R.id.tabtextview);
		title = (TextView) findViewById(R.id.receivepresent);
		remain = (TextView) findViewById(R.id.remain);
		obtain = (TextView) findViewById(R.id.obtain);
		present = (ImageView) findViewById(R.id.presentview);
		tab.setTypeface(MainActivity.Font);
		title.setTypeface(MainActivity.Font);
		remain.setTypeface(MainActivity.Font);
		obtain.setTypeface(MainActivity.Font);
		remain.setText(easteregg);
	}
	
	public void Click(View v) {
		int i = Integer.parseInt(easteregg) - 1;
		EasterEgg.easteregg = String.valueOf(i);
		remain.setText(easteregg);
		if (EasterEgg.easteregg.equals("0")) {
			startActivity(new Intent(this, SecondEgg.class));
		}
	}


}
