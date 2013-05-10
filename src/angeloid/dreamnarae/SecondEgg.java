/*
 * DreamNarae, Emotional Android Tools.
    Copyright (C) 2013 Seo, Dong-Gil in Angeloid Team. 

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


    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
	
	Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
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