package angeloid.dreamnarae;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Promoting extends Activity {

	// Kakao Link
	private String encoding = "UTF-8";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.promoting);

	}

	public void kakaobtn(View v) {
		try {
			sendAppData(v);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void emailbtn(View v) {
		Intent email = new Intent(Intent.ACTION_SEND);
		PackageManager packageManager = getPackageManager();
		List<ResolveInfo> activities = packageManager.queryIntentActivities(
				email, 0);
		boolean isIntentSafe = activities.size() > 0;
		if (isIntentSafe) {
		    startActivity(email);
		} else {
			alert(getString(R.string.isemailapp));
		}
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
		AlertDialog.Builder builder = new Builder(Promoting.this);
		builder.setView(view);
		builder.setPositiveButton(android.R.string.ok, null).create().show();
	}
}