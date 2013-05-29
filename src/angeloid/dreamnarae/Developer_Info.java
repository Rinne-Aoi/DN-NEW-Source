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

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Developer_Info extends BaseSlidingActivity {

	// MediaPlayer
	MediaPlayer mplayer;
	String man = "10";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.developer);
		// ListView
		ListView list = (ListView) findViewById(R.id.list);
		ArrayAdapter<CharSequence> developerinfo = ArrayAdapter
				.createFromResource(Developer_Info.this, R.array.developinfo,
						R.layout.listviewlayout);
		list.setAdapter(developerinfo);
		list.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 0) {
					mplayer();
				}
			}

		});

	}

	public void mplayer() {

		int i = Integer.parseInt(man) - 1;
		man = String.valueOf(i);
		if (mplayer.isPlaying() == false) {
			Log.d("Developer_Info", man);
		}
		if (man.equals("0")) {
			man = "10";
			mplayer.start();
			if (mplayer.isPlaying() == true) {
				Toast.makeText(this, R.string.introduce2, Toast.LENGTH_SHORT)
						.show();
			}
		}
	}
}