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

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.github.espiandev.showcaseview.ShowcaseView;

public class MainActivity extends BaseSlidingActivity implements
		ShowcaseView.OnShowcaseEventListener {

	// MainFillper
	ImageView iv1;
	ImageView iv2;
	ImageView iv3;
	ImageView iv4;
	ImageView iv5;
	ImageView iv6;
	ImageView iv7;
	ImageView iv8;
	ImageView iv9;
	ImageView iv10;
	ViewFlipper vf;

	// ShowCase
	ShowcaseView sv;
	ImageButton button;
	String scview;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// MainFippler
		vf = (ViewFlipper) findViewById(R.id.viewFlipper1);
		iv1 = (ImageView) findViewById(R.id.imageView1);
		iv2 = (ImageView) findViewById(R.id.imageView2);
		iv3 = (ImageView) findViewById(R.id.imageView3);
		iv4 = (ImageView) findViewById(R.id.imageView4);
		iv5 = (ImageView) findViewById(R.id.imageView5);
		iv6 = (ImageView) findViewById(R.id.imageView6);
		iv7 = (ImageView) findViewById(R.id.imageView7);
		iv8 = (ImageView) findViewById(R.id.imageView8);
		iv9 = (ImageView) findViewById(R.id.imageView9);
		iv10 = (ImageView) findViewById(R.id.imageView11);
		vf.setFlipInterval(6500);
		vf.setInAnimation(AnimationUtils.loadAnimation(MainActivity.this,
				R.anim.left_in));
		vf.setOutAnimation(AnimationUtils.loadAnimation(MainActivity.this,
				R.anim.left_out));
		vf.startFlipping();
		Handler mHandler = new Handler();
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// ShowCase
				button = (ImageButton) findViewById(R.id.dummy);
				ShowcaseView.ConfigOptions co = new ShowcaseView.ConfigOptions();
				co.hideOnClickOutside = true;
				sv = ShowcaseView.insertShowcaseView(R.id.dummy,
						MainActivity.this, getString(R.string.tostart),
						getString(R.string.tostartmessage), co);
				sv.animateGesture(0, 0, 200, 0);
			}
		}, 500);

	}

	@Override
	public void onShowcaseViewHide(ShowcaseView showcaseView) {

	}

	@Override
	public void onShowcaseViewShow(ShowcaseView showcaseView) {

	}

}