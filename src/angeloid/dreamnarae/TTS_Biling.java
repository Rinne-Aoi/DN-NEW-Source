package angeloid.dreamnarae;

import java.util.ArrayList;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;

import com.android.vending.billing.IInAppBillingService;

public class TTS_Biling extends FragmentActivity {
	IInAppBillingService mService;
	final static int TRUE = 1;
	final static int FALSE = 0;
	boolean isPro = false;
	String sku = "";

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
		setContentView(R.layout.biling);
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(TTS_Biling.this);
		Biling_Friends();
		int temp_isPro = sharedPrefs.getInt("License", 0);
		if (temp_isPro == TRUE)
			isPro = true;
		Intent intent = getIntent();
		String Type = intent.getStringExtra("Type");
		if (Type.equals("Narae")) {
			sku = "dntts_narae";
			Biling_Narae();
		} else if (Type.equals("Friends")) {
			sku = "dntts_friends";
			Biling_Friends();
		}
		bindService(new Intent("com.android.vending.billing.InAppBillingService.BIND"),
                mServiceConn, Context.BIND_AUTO_CREATE);
	}
	
	@Override
    public void onDestroy() {
        super.onDestroy();
        if (mServiceConn != null) {
            unbindService(mServiceConn);
        }   
    }

	void Biling_Friends() {
		try {
			// Query Already Purchased
			Bundle ownedItems = mService.getPurchases(3, getPackageName(),
					"inapp", null);
			int response = ownedItems.getInt("RESPONSE_CODE");
			ArrayList<String> ownedSkus = null;

			if (response == 0)
				ownedSkus = ownedItems
						.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");

			if (response == 0 && ownedSkus.size() > 0
					&& ownedSkus.get(0).equals(sku)) {
				SharedPreferences sharedPref = PreferenceManager
						.getDefaultSharedPreferences(this);
				SharedPreferences.Editor editor = sharedPref.edit();
				editor.putInt("License", TRUE);
				editor.commit();
				isPro = true;
			}

			// Purchase it
			
			Bundle buyIntentBundle = mService.getBuyIntent(3, getPackageName(),
					sku, "inapp", "");
			PendingIntent pendingIntent = buyIntentBundle
					.getParcelable("BUY_INTENT");
			startIntentSenderForResult(pendingIntent.getIntentSender(), 1001,
					new Intent(), Integer.valueOf(0), Integer.valueOf(0),
					Integer.valueOf(0));

			// Check
			ownedItems = mService.getPurchases(3, getPackageName(), "inapp",
					null);
			response = ownedItems.getInt("RESPONSE_CODE");
			if (response == 0)
				ownedSkus = ownedItems
						.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");

			if (response == 0 && ownedSkus.size() > 0
					&& ownedSkus.get(0).equals(sku)) {
				SharedPreferences sharedPref = PreferenceManager
						.getDefaultSharedPreferences(this);
				SharedPreferences.Editor editor = sharedPref.edit();
				editor.putInt("License", TRUE);
				editor.commit();
				isPro = true;
			}

		} catch (Exception e) {
		}
	}
	void Biling_Narae() {
		try {
			// Query Already Purchased
			Bundle ownedItems = mService.getPurchases(3, getPackageName(),
					"inapp", null);
			int response = ownedItems.getInt("RESPONSE_CODE");
			ArrayList<String> ownedSkus = null;

			if (response == 0)
				ownedSkus = ownedItems
						.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");

			if (response == 0 && ownedSkus.size() > 0
					&& ownedSkus.get(0).equals(sku)) {
				SharedPreferences sharedPref = PreferenceManager
						.getDefaultSharedPreferences(this);
				SharedPreferences.Editor editor = sharedPref.edit();
				editor.putInt("License", TRUE);
				editor.commit();
				isPro = true;
			}

			// Purchase it
			
			Bundle buyIntentBundle = mService.getBuyIntent(3, getPackageName(),
					sku, "inapp", "");
			PendingIntent pendingIntent = buyIntentBundle
					.getParcelable("BUY_INTENT");
			startIntentSenderForResult(pendingIntent.getIntentSender(), 1001,
					new Intent(), Integer.valueOf(0), Integer.valueOf(0),
					Integer.valueOf(0));

			// Check
			ownedItems = mService.getPurchases(3, getPackageName(), "inapp",
					null);
			response = ownedItems.getInt("RESPONSE_CODE");
			if (response == 0)
				ownedSkus = ownedItems
						.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");

			if (response == 0 && ownedSkus.size() > 0
					&& ownedSkus.get(0).equals(sku)) {
				SharedPreferences sharedPref = PreferenceManager
						.getDefaultSharedPreferences(this);
				SharedPreferences.Editor editor = sharedPref.edit();
				editor.putInt("License", TRUE);
				editor.commit();
				isPro = true;
			}

		} catch (Exception e) {
		}
	}
}