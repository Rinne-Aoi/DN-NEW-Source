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
   /**
 * ZipDownloader
 * 
 * A simple app to demonstrate downloading and unpacking a .zip file
 * as a background task.
 * 
 * Copyright (c) 2011 Michael J. Portuesi (http://www.jotabout.com)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package angeloid.dreamnarae;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jotabout.zipdownloader.util.DecompressZip;
import com.jotabout.zipdownloader.util.DownloadFile;
import com.jotabout.zipdownloader.util.ExternalStorage;
import com.stericson.RootTools.RootTools;

public class Pure extends Activity {
	Button apply;
	Button info;
	static MediaPlayer mplayer;
	ImageView imageview;
	TextView LayoutTitle;
	protected ProgressDialog mProgressDialog;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pure);
		LayoutTitle = (TextView) findViewById(R.id.tabtextview);
		LayoutTitle.setTypeface(MainActivity.Font);
		apply = (Button) findViewById(R.id.apply);
		info = (Button) findViewById(R.id.info);
		apply.setTypeface(MainActivity.Font);
		info.setTypeface(MainActivity.Font);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		imageview = (ImageView) findViewById(R.id.imageview);
		mplayer = MediaPlayer.create(Pure.this, R.raw.spica);
		apply.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (RootTools.isAccessGiven()) {
					startDownload(v);
				} else {
					Toast.makeText(Pure.this, R.string.noroottoast,
							Toast.LENGTH_LONG).show();
				}
			}
		});
		if (new File("/system/98banner_dreamnarae_pure").exists()) {
			apply.setEnabled(false);
			apply.setFocusable(false);
			imageview.setImageResource(R.drawable.apply);
		} else {
		}

		info.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog();

			}
		});
	}

	public void dialog() {
		View view = this.getLayoutInflater().inflate(R.layout.customdialog,
				null);
		TextView txtTitle = (TextView) view.findViewById(R.id.title);
		txtTitle.setText(R.string.pure_title);
		txtTitle.setTextColor(Color.WHITE);
		txtTitle.setTextSize(20);
		TextView message = (TextView) view.findViewById(R.id.message);
		message.setText(R.string.pure_info);
		message.setTextColor(Color.WHITE);
		AlertDialog.Builder builder = new Builder(Pure.this);
		builder.setView(view);
		builder.setCancelable(false);
		builder.setPositiveButton(R.string.infoclose,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}

		)
				.setNegativeButton(R.string.voice,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								mplayer.start();
							}
						}).show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			View view = this.getLayoutInflater().inflate(R.layout.customdialog,
					null);
			TextView txtTitle = (TextView) view.findViewById(R.id.title);
			txtTitle.setText(R.string.quittitle);
			txtTitle.setTextColor(Color.WHITE);
			txtTitle.setTextSize(20);
			txtTitle.setTypeface(MainActivity.Font);
			TextView message = (TextView) view.findViewById(R.id.message);
			message.setText(R.string.quitmessage);
			message.setTextColor(Color.WHITE);
			message.setTypeface(MainActivity.Font);
			AlertDialog.Builder builer = new AlertDialog.Builder(this);
			builer.setView(view);
			builer.setPositiveButton(android.R.string.yes,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							android.os.Process.killProcess(android.os.Process
									.myPid());
						}
					});
			builer.setNegativeButton(android.R.string.no, null).show();

			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (new File("/system/98banner_dreamnarae_pure").exists()) {
			apply.setEnabled(false);
			apply.setFocusable(false);
			imageview.setImageResource(R.drawable.apply);
		} else {
		}
	}

	public void startDownload(View v) {
		String url = "http://gecp.kr/dn/newdn.zip";
		new DownloadTask().execute(url);
	}

	private class DownloadTask extends AsyncTask<String, Void, Exception> {

		@Override
		protected void onPreExecute() {
			showProgress();
		}

		@Override
		protected Exception doInBackground(String... params) {
			String url = (String) params[0];

			try {
				downloadAllAssets(url);
			} catch (Exception e) {
				return e;
			}

			return null;
		}

		@Override
		protected void onPostExecute(Exception result) {
			dismissProgress();
			if (result == null) {
				return;
			}
			Toast.makeText(Pure.this, result.getLocalizedMessage(),
					Toast.LENGTH_LONG).show();
		}
	}

	protected void showProgress() {
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setTitle(R.string.progress_title);
		mProgressDialog.setMessage(getString(R.string.progress_detail));
		mProgressDialog.setIndeterminate(true);
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
	}

	protected void dismissProgress() {
		if (mProgressDialog != null && mProgressDialog.isShowing()
				&& mProgressDialog.getWindow() != null) {
			try {
				mProgressDialog.dismiss();
			} catch (IllegalArgumentException ignore) {
				;
			}
		}
		mProgressDialog = null;
	}

	private void downloadAllAssets(String url) {
		File zipDir = ExternalStorage.getSDCacheDir(this, "tmp");
		File zipFile = new File(zipDir.getPath() + "/newdn.zip");
		File outputDir = ExternalStorage.getSDCacheDir(this, "");

		try {
			DownloadFile.download(url, zipFile, zipDir);
			unzipFile(zipFile, outputDir);
		} finally {
			zipFile.delete();
		}
	}

	protected void unzipFile(File zipFile, File destination) {
		DecompressZip decomp = new DecompressZip(zipFile.getPath(),
				destination.getPath() + File.separator);
		decomp.unzip();
	}
}
