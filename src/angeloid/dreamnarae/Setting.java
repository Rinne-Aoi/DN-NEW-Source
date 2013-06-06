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

import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class Setting extends BaseSlidingActivity {
	CheckBox ttscheck;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		ttscheck = (CheckBox) findViewById(R.id.checkBox1);
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(Setting.this);
		String check = prefs.getString("tts", "true");
		if (check.equals("true")) {
			ttscheck.setChecked(true);
		}
		ttscheck.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (ttscheck.isChecked() == true) {
					SharedPreferences prefs = PreferenceManager
							.getDefaultSharedPreferences(Setting.this);
					SharedPreferences.Editor editor = prefs.edit();
					editor.putString("tts", "true");
					editor.commit();
				} else if (ttscheck.isChecked() == false) {
					SharedPreferences prefs = PreferenceManager
							.getDefaultSharedPreferences(Setting.this);
					SharedPreferences.Editor editor = prefs.edit();
					editor.putString("tts", "false");
					editor.commit();
				}

			}
		});
	}

	public void mail(View v) {
		Uri uri = Uri.parse("mailto:sirospace@sirospace.com");
		Intent it = new Intent(Intent.ACTION_SENDTO, uri);
		PackageManager packageManager = getPackageManager();
		List<ResolveInfo> activities = packageManager.queryIntentActivities(it,
				0);
		boolean isIntentSafe = activities.size() > 0;
		if (isIntentSafe) {
			startActivity(it);
		} else {
			alert(getString(R.string.isemailapp));
		}
	}

	public void licenseintent(View v) {
		startActivity(new Intent(this, License.class));
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
		AlertDialog.Builder builder = new Builder(Setting.this);
		builder.setView(view);
		builder.setPositiveButton(android.R.string.ok, null).create().show();
	}

}
