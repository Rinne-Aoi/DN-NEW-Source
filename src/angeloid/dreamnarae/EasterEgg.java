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
	static String easteregg = "15000";
	
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