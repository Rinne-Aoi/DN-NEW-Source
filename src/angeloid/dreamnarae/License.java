package angeloid.dreamnarae;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class License extends Activity {

	private String assetTxt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.license);
		TextView text = (TextView) findViewById(R.id.license);

		try {
			assetTxt = readText("license/license.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}

		text.setText(assetTxt);
	}

	private String readText(String file) throws IOException {
		InputStream is = getAssets().open(file);

		int size = is.available();
		byte[] buffer = new byte[size];
		is.read(buffer);
		is.close();

		String text = new String(buffer);

		return text;
	}
}