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
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Setting extends Activity {

    TextView setting3_title;
    TextView setting4_title;
    TextView setting3_summary;
    TextView setting4_summary;
    Button go1;
    Button go2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        setting3_title = (TextView) findViewById(R.id.setting3_title);
        setting4_title = (TextView) findViewById(R.id.setting4_title);
        setting3_summary = (TextView) findViewById(R.id.setting3_summary);
        setting4_summary = (TextView) findViewById(R.id.setting4_summary);
        go1 = (Button) findViewById(R.id.go1);
        go2 = (Button) findViewById(R.id.go2);
        go1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("mailto:sirospace@sirospace.com");
                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                startActivity(it);
            }
        });
        go2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                licenseintent();
            }
        });
    }
    public void licenseintent() {
        startActivity(new Intent(this, License.class));
    }

}
