package angeloid.dreamnarae;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

public class SecondEgg extends Activity {
	MediaPlayer mplayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.secondegg);
		mplayer = MediaPlayer.create(SecondEgg.this, R.raw.trapcard);
		mplayer.setLooping(true);
		mplayer.start();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			new AlertDialog.Builder(this)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle(R.string.noquittitle)
					.setMessage(R.string.noquitmessage)
					.setPositiveButton(R.string.infoclose,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {

								}
							}).show();

			return true;
		}

		return super.onKeyDown(keyCode, event);
	}
	protected void onPause() {
		super.onPause();
		Toast.makeText(this, R.string.isleaving, Toast.LENGTH_SHORT).show();
		mplayer.stop();
		finish();
		
	}

	protected void onStop() {
		super.onStop();
		Toast.makeText(this, R.string.isleaving, Toast.LENGTH_SHORT).show();
		mplayer.stop();
		finish();
	}
}