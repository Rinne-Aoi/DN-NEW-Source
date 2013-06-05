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

import java.util.ArrayList;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.vending.billing.IInAppBillingService;

public class Donate extends BaseSlidingActivity {

	IInAppBillingService mService;
	boolean isPro = false;
	final static int TRUE = 1;
	final static int FALSE = 0;
	Button button1;

	ServiceConnection mServiceConn = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName name) {
			mService = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mService = IInAppBillingService.Stub.asInterface(service);
		}
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		button1 = (Button) findViewById(R.id.button1);
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		int temp_isPro = sharedPrefs.getInt("License", 0);
		if (temp_isPro == TRUE)
			isPro = true;
		setContentView(R.layout.donate);
		button1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				donate(v);
				
			}
		});
	}

	void donate(View v) {
		try {
			// Query Already Purchased
			Bundle ownedItems = mService.getPurchases(3, getPackageName(),
					"inapp", null);
			int response = ownedItems.getInt("RESPONSE_CODE");
			ArrayList<String> ownedSkus = null;
			if(response == 0) 
				ownedSkus = ownedItems.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
			if(response == 0 && ownedSkus.size() > 0 && ownedSkus.get(0).equals("dndonate_1"))
			{
				showToast(getString(R.string.AlreadyBuy));
				SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
				SharedPreferences.Editor editor = sharedPref.edit();
				editor.putInt("License", TRUE);
				editor.commit();
				isPro = true;
				return;
			}
			// Purchase it
			String sku = "dndonate_1";
			Bundle buyIntentBundle = mService.getBuyIntent(3, getPackageName(), sku, "inapp", "");
			PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");
			startIntentSenderForResult(pendingIntent.getIntentSender(),
					   1001, new Intent(), Integer.valueOf(0), Integer.valueOf(0),
					   Integer.valueOf(0));
			// Check
			ownedItems = mService.getPurchases(3, getPackageName(), "inapp", null);
			response = ownedItems.getInt("RESPONSE_CODE");
			if(response == 0) 
				ownedSkus = ownedItems.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
			
			if(response == 0 && ownedSkus.size() > 0 && ownedSkus.get(0).equals("dndonate_1"))
			{
				SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
				SharedPreferences.Editor editor = sharedPref.edit();
				editor.putInt("License", TRUE);
				editor.commit();
				isPro = true;
				showToast(getString(R.string.RestartApp));
				return;
			}
		} catch (Exception e) {
			
		}
	}
	
	public void showToast(final String Msg) {
		this.runOnUiThread(new Runnable() {
			  public void run() {
			    Toast.makeText(getApplicationContext(), Msg, Toast.LENGTH_SHORT).show();
			  }
		});
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mServiceConn != null) {
			unbindService(mServiceConn);
		}
	}
}
