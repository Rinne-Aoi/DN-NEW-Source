/* Power File Manager / RootTools
 * 
    Copyright (C) 2013 Mu Hwan, Kim
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

package angeloid.dreamnarae.file.manager;

import android.annotation.SuppressLint;
import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.*;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.*;
import android.net.Uri;
import android.os.*;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.*;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemLongClickListener;

import java.io.*;
import java.util.*;

import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.execution.Command;
import angeloid.dreamnarae.R;

@SuppressLint("HandlerLeak")
public class RootActivity extends ListActivity {

	ArrayList<RootFileProperty> item = new ArrayList<RootFileProperty>();
	ArrayList<String> path = new ArrayList<String>();
	Drawable[] icon;
	final int MAX_LIST_ITEMS = 1000;
	String[] clipboard = new String[MAX_LIST_ITEMS + 1];
	int NumberofClipboardItems = 0;
	ListView list;
	String root;
	TextView myPath;
	int nowlevel = -1;
	Parcelable list_state[] = new Parcelable[MAX_LIST_ITEMS + 1];
	int isSelected[] = new int[MAX_LIST_ITEMS + 1];
	int NumberofSelectedItems = 0;
	long backPressedTime = 0;
	String nowPath = "";
	boolean showMultiSelectToast = true;
	boolean Renaming = false;
	int NumberOfImageFiles = 0;
	String sfilename;
	String appTheme;
	Drawable nowIcon;
	FileAdapter adapter;
	Resources res;
	Drawable Folder;
	Drawable Others;
	Drawable Image;
	Drawable Audio;
	Drawable Compressed;

	@SuppressLint("InlinedApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		appTheme = sharedPrefs.getString("AppTheme", null);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB
				&& appTheme != null) {
			if (appTheme.equals("Light"))
				setTheme(android.R.style.Theme_Holo_Light_DarkActionBar);
			if (appTheme.equals("Dark"))
				setTheme(android.R.style.Theme_Holo);
		}

		if (appTheme == null)
			appTheme = "Light";

		setContentView(R.layout.rootmain);

		myPath = (TextView) findViewById(R.id.rpath);
		root = "/";
		findViewById(R.id.rCopyBtn).setVisibility(View.INVISIBLE);
		findViewById(R.id.rPasteBtn).setVisibility(View.INVISIBLE);
		findViewById(R.id.rMoveBtn).setVisibility(View.INVISIBLE);
		findViewById(R.id.rDeleteBtn).setVisibility(View.INVISIBLE);
		findViewById(R.id.rPermBtn).setVisibility(View.INVISIBLE);

		list = (ListView) findViewById(android.R.id.list);

		list.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				SelectionItems(parent, position);
				if (showMultiSelectToast) {
					showToast(getString(R.string.NowStartMultiSelectMode));
					showMultiSelectToast = false;
				}
				return true;
			}
		});

		list.setOnScrollListener(new OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState != 0)
					((FileAdapter) list.getAdapter()).isScrolling = true;
				else {
					((FileAdapter) list.getAdapter()).isScrolling = false;
					((FileAdapter) list.getAdapter()).notifyDataSetChanged();
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
			}
		});

		list.setScrollingCacheEnabled(true);

		res = getResources();
		Folder = res.getDrawable(R.drawable.folder);
		Others = res.getDrawable(R.drawable.others);
		Image = res.getDrawable(R.drawable.image);
		Audio = res.getDrawable(R.drawable.audio);
		Compressed = res.getDrawable(R.drawable.compressed);

		getDir(root);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.root, menu);
		return true;
	}

	private void getDir(String dirPath) {
		final int path_len = myPath.getText().toString().length(); // Get Path's
																	// Length
		final int tag_len = 10; // Length of "Location: "
		if (path_len - tag_len < dirPath.length())
			nowlevel++; // Go into
		else if (path_len - tag_len > dirPath.length())
			nowlevel--; // Go back
		nowPath = dirPath;
		item.clear();
		path.clear();

		RootFile f = new RootFile(dirPath);
		RootFile files[] = f.listFiles();

		for (int i = 0; i <= MAX_LIST_ITEMS; i++)
			isSelected[i] = View.INVISIBLE; // initialize isSelected with
											// View.INVISIBLE [No Selected
											// Items]
		NumberofSelectedItems = 0;

		if (!dirPath.equals(root)) {
			item.add(new RootFileProperty("FOLDER", "../",
					getString(R.string.ParentFolder), "", ""));
			path.add(f.getParent());
		}

		int filelen = files.length;
		for (int i = 0; i < filelen; i++) {
			RootFile file = files[i];

			if (!file.isHidden() && file.canRead()) {
				path.add(file.getPath());

				String icontype = file.isDirectory() ? "FOLDER"
						: getExtension(file);
				String filesize = file.isDirectory() ? "" : formatFileSize(file
						.length());
				if (file.getPerms().equals(""))
					filesize = "";
				item.add(new RootFileProperty(icontype, file.getName(),
						DateFormat.format("yyyy.MM.dd kk:mm",
								file.lastModified()).toString(), filesize, file
								.getPerms()));
			}
		}

		final int psize = path.size();
		icon = new Drawable[psize];

		Thread getIcons = new Thread() {
			public void run() {
				String file, mimeType;
				for (int i = 0; i < psize; i++) {
					if (item.get(i).getIcon().equals("FOLDER"))
						icon[i] = Folder;
					else {
						file = item.get(i).getIcon();
						mimeType = getMIME(file);
						if (file.equals("zip") || file.equals("7z")
								|| file.equals("rar") || file.equals("tar"))
							icon[i] = Compressed;

						else if (mimeType == null)
							icon[i] = Others;

						else if (mimeType.startsWith("image"))
							icon[i] = Image;

						else if (mimeType.startsWith("audio"))
							icon[i] = Audio;

						else if (!file.equals("apk"))
							icon[i] = Others;

						else if (file.equals("apk"))
							icon[i] = null; // set NULL when apk file
					}
				}
			}
		};

		runOnUiThread(getIcons);

		adapter = new FileAdapter(item);
		if (path_len - tag_len <= dirPath.length()) // Go Into
		{
			adapter.loader.clearCache();
			list_state[nowlevel] = list.onSaveInstanceState();
			list.setAdapter(adapter);
		} else if (path_len - tag_len > dirPath.length()) // Go Back
		{
			adapter.loader.clearCache();
			list.setAdapter(adapter);
			list.onRestoreInstanceState(list_state[nowlevel + 1]);
		}

		myPath.setText(getString(R.string.Path) + " " + dirPath);
	}

	@Override
	protected void onListItemClick(ListView parent, View view, int position,
			long id) {
		if (Renaming) {
			FileRename(path.get(position));
			Renaming = false;
			return;
		}

		if (NumberofSelectedItems > 0) // If now Selecting File/Dirs
		{
			SelectionItems(parent, position);
			return;
		}

		RootFile file = new RootFile(path.get(position));
		if (file.isDirectory())
			getDir(path.get(position));
		else {
			String name = file.getName();
			int length = name.length() - 1;
			StringBuffer ext = new StringBuffer();
			while (true) {
				if (name.charAt(length) != 46)
					ext.append(name.charAt(length--));
				else
					break;
				if (length <= 0) {
					ext = null;
					break;
				}
			}

			if (ext == null)
				ext = new StringBuffer().append("");
			StringBuffer temp = new StringBuffer();
			if (ext != null)
				temp = ext.reverse();
			String extension = temp.toString();
			String mimeType = MimeTypeMap.getSingleton()
					.getMimeTypeFromExtension(extension.toLowerCase());
			if (mimeType == null) {
				showToast(getString(R.string.WillOpenTextEditor));
				Intent intent = new Intent(RootActivity.this, TextEditor.class);
				intent.putExtra("filepath", file.getPath());
				startActivity(intent);
				return;
			}
			runFile(file, mimeType);
		}

		if (NumberofClipboardItems == 0) {
			findViewById(R.id.rCopyBtn).setVisibility(View.INVISIBLE);
			findViewById(R.id.rPasteBtn).setVisibility(View.INVISIBLE);
			findViewById(R.id.rMoveBtn).setVisibility(View.INVISIBLE);
			findViewById(R.id.rDeleteBtn).setVisibility(View.INVISIBLE);
			findViewById(R.id.rPermBtn).setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void onBackPressed() {
		if (nowlevel > 0 && NumberofSelectedItems == 0)
			getDir(path.get(0));
		else if (nowlevel >= 0 && NumberofSelectedItems > 0) {
			for (int i = 0; i <= MAX_LIST_ITEMS; i++)
				isSelected[i] = View.INVISIBLE; // initialize isSelected with
												// View.INVISIBLE [No Selected
												// Items]
			NumberofSelectedItems = 0;
			if (showMultiSelectToast)
				showToast(getString(R.string.EndofMultiSelectMode));
			getDir(nowPath);
			findViewById(R.id.rCopyBtn).setVisibility(View.INVISIBLE);
			findViewById(R.id.rPasteBtn).setVisibility(View.INVISIBLE);
			findViewById(R.id.rMoveBtn).setVisibility(View.INVISIBLE);
			findViewById(R.id.rDeleteBtn).setVisibility(View.INVISIBLE);
			findViewById(R.id.rPermBtn).setVisibility(View.INVISIBLE);
		} else {
			long tempTime = System.currentTimeMillis();
			long intervalTime = tempTime - backPressedTime;

			if (0 <= intervalTime && 2000 >= intervalTime)
				super.onBackPressed();
			else {
				backPressedTime = tempTime;
				showToast(getString(R.string.PressAgainToExit));
			}
		}
	}

	public void onrCopyBtnPress(View v) {
		clipboard = new String[NumberofSelectedItems];
		int n = 0, i = 0;
		while (true) {
			if (isSelected[i] == View.VISIBLE) {
				clipboard[n] = path.get(i);
				if (n < NumberofSelectedItems)
					n++;
				else
					break;
			}
			if (i < MAX_LIST_ITEMS)
				i++;
			else
				break;
		}

		NumberofClipboardItems = NumberofSelectedItems;
		for (i = 0; i <= MAX_LIST_ITEMS; i++)
			isSelected[i] = View.INVISIBLE;
		NumberofSelectedItems = 0;
		getDir(nowPath);
		findViewById(R.id.rCopyBtn).setVisibility(View.INVISIBLE);
		findViewById(R.id.rPermBtn).setVisibility(View.INVISIBLE);
		findViewById(R.id.rDeleteBtn).setVisibility(View.INVISIBLE);
		findViewById(R.id.rPasteBtn).setVisibility(View.VISIBLE);
		findViewById(R.id.rMoveBtn).setVisibility(View.VISIBLE);
	}

	public void onrPasteBtnPress(View v) {
		final ProgressDialog dialog = ProgressDialog.show(this,
				getString(R.string.Copying), getString(R.string.Wait), true);

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				getDir(nowPath);
			}
		};

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					for (int i = 0; i < NumberofClipboardItems; i++) {
						FileCopy(new RootFile(clipboard[i]), new RootFile(
								nowPath));
					}
				} catch (IOException e) {
					showToast(e.getMessage());
				}
				handler.sendEmptyMessage(0);
				dialog.dismiss();
			}
		}).start();

		findViewById(R.id.rCopyBtn).setVisibility(View.INVISIBLE);
		findViewById(R.id.rPasteBtn).setVisibility(View.INVISIBLE);
		findViewById(R.id.rMoveBtn).setVisibility(View.INVISIBLE);
		findViewById(R.id.rDeleteBtn).setVisibility(View.INVISIBLE);
		findViewById(R.id.rPermBtn).setVisibility(View.INVISIBLE);
	}

	public void onrDeleteBtnPress(View v) {
		final ProgressDialog dialog = ProgressDialog.show(this,
				getString(R.string.Deleting), getString(R.string.Wait), true);

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				getDir(nowPath);
			}
		};

		new Thread(new Runnable() {
			@Override
			public void run() {
				int i = 0;
				while (true) {
					if (isSelected[i] == View.VISIBLE) {
						new RootFile(path.get(i)).delete();
					}
					if (i < MAX_LIST_ITEMS)
						i++;
					else
						break;
				}

				handler.sendEmptyMessage(0);
				dialog.dismiss();
			}
		}).start();

		findViewById(R.id.rCopyBtn).setVisibility(View.INVISIBLE);
		findViewById(R.id.rPasteBtn).setVisibility(View.INVISIBLE);
		findViewById(R.id.rMoveBtn).setVisibility(View.INVISIBLE);
		findViewById(R.id.rDeleteBtn).setVisibility(View.INVISIBLE);
		findViewById(R.id.rPermBtn).setVisibility(View.INVISIBLE);
	}

	public void onrMoveBtnPress(View v) {
		final ProgressDialog dialog = ProgressDialog.show(this,
				getString(R.string.Moving), getString(R.string.Wait), true);
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				getDir(nowPath);
			}
		};

		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < NumberofClipboardItems; i++) {
					RootTools.remount(nowPath, "rw");
					final String w = "busybox mv " + clipboard[i] + " "
							+ nowPath;
					Command cmd = new Command(0, w) {
						@Override
						public void output(int id, String line) {
						}
					};

					try {
						RootTools.getShell(true).add(cmd).waitForFinish();
					} catch (Exception e) {
						showToast(e.getMessage());
					}
				}

				handler.sendEmptyMessage(0);
				dialog.dismiss();
			}
		}).start();

		findViewById(R.id.rCopyBtn).setVisibility(View.INVISIBLE);
		findViewById(R.id.rPasteBtn).setVisibility(View.INVISIBLE);
		findViewById(R.id.rMoveBtn).setVisibility(View.INVISIBLE);
		findViewById(R.id.rDeleteBtn).setVisibility(View.INVISIBLE);
		findViewById(R.id.rPermBtn).setVisibility(View.INVISIBLE);
	}

	public void onrPermBtnPress(View v) {
		Context mContext = getApplicationContext();
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.permset, null);
		final EditText NumPerm = (EditText) layout
				.findViewById(R.id.NumericPerm);
		if (appTheme.equals("Light"))
			NumPerm.setTextColor(Color.BLACK);
		if (appTheme.equals("Dark"))
			NumPerm.setTextColor(Color.WHITE);
		AlertDialog.Builder aDialog = new AlertDialog.Builder(RootActivity.this);
		aDialog.setTitle(getString(R.string.Perm));
		aDialog.setView(layout);

		aDialog.setPositiveButton(getString(R.string.Finish),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						String newPerm = NumPerm.getText().toString();
						RootTools.remount(nowPath, "rw");
						int i = 0;
						while (true) {
							String aPath = new String();
							if (isSelected[i] == View.VISIBLE)
								aPath = path.get(i);
							{
								final String w = "busybox chmod " + newPerm
										+ " " + aPath;
								Command cmd = new Command(0, w) {
									@Override
									public void output(int id, String line) {
									}
								};

								try {
									RootTools.getShell(true).add(cmd)
											.waitForFinish();
								} catch (Exception e) {
									showToast(e.getMessage());
								}
							}

							if (i < path.size())
								i++;
							else
								break;
						}

						getDir(nowPath);
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.toggleSoftInput(
								InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
					}
				});

		AlertDialog ad = aDialog.create();
		ad.show();
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
		findViewById(R.id.rCopyBtn).setVisibility(View.INVISIBLE);
		findViewById(R.id.rPasteBtn).setVisibility(View.INVISIBLE);
		findViewById(R.id.rMoveBtn).setVisibility(View.INVISIBLE);
		findViewById(R.id.rDeleteBtn).setVisibility(View.INVISIBLE);
		findViewById(R.id.rPermBtn).setVisibility(View.INVISIBLE);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.NewFolder) {
			MakeNewFolder();
		} else if (id == R.id.Rename) {
			Renaming = true;
			showToast(getString(R.string.StartRenaming));
		} else if (id == R.id.Search) {
			InitializeSearch();
		}
		return false;
	}

	public void SelectionItems(AdapterView<?> parent, int position) {
		Button rCopyBtn = (Button) findViewById(R.id.rCopyBtn);
		Button rDeleteBtn = (Button) findViewById(R.id.rDeleteBtn);
		Button rPermBtn = (Button) findViewById(R.id.rPermBtn);

		if (isSelected[position] == View.VISIBLE) // Already Selected
		{
			// When NumberofSelectedItems == 1, After this process it will be 0,
			// then set copy/delete button invisible
			if (NumberofSelectedItems == 1) {
				rCopyBtn.setVisibility(View.INVISIBLE);
				rDeleteBtn.setVisibility(View.INVISIBLE);
			}

			isSelected[position] = View.INVISIBLE; // Deselect
			NumberofSelectedItems--;
			int firstPos = parent.getFirstVisiblePosition(); // Now 'Showing'
																// First
																// Position
			int wantedPos = position - firstPos; // Position : Now 'Showing'
													// Selected Position,
													// wantedPos : Now absolute
													// position
			View v = parent.getChildAt(wantedPos); // getChildAt() 's Parameter
													// must an absolute position
													// number.
			ImageView check = (ImageView) v.findViewById(R.id.check);
			check.setVisibility(View.INVISIBLE); // Uncheck it
			check.refreshDrawableState(); // Refresh Button
			return;
		}

		rCopyBtn.setVisibility(View.VISIBLE); // Set Visible
		rDeleteBtn.setVisibility(View.VISIBLE); // Set Visible
		rPermBtn.setVisibility(View.VISIBLE); // Set Visible
		isSelected[position] = View.VISIBLE; // Set Visible
		NumberofSelectedItems++;
		int firstPos = parent.getFirstVisiblePosition();
		int wantedPos = position - firstPos;
		View v = parent.getChildAt(wantedPos);
		ImageView check = (ImageView) v.findViewById(R.id.check);
		check.setVisibility(View.VISIBLE);
		check.refreshDrawableState(); // Refresh
		return;
	}

	public String formatFileSize(long size) {
		if (size < 1024)
			return new Long(size).toString() + " bytes";
		else if (size < 1024 * 1024) {
			String t = String.format("%.2f KB", (double) size / 1024);
			return t;
		} else if (size < 1024 * 1024 * 1024) {
			String t = String.format("%.2f MB", (double) size / 1024 / 1024);
			return t;
		} else {
			String t = String.format("%.2f GB",
					(double) size / 1024 / 1024 / 1024);
			return t;
		}
	}

	public void runFile(RootFile file, String MimeType) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		Uri uri = Uri.fromFile(file);
		intent.setDataAndType(uri, MimeType);
		PackageManager packageManager = getPackageManager();
		List<ResolveInfo> activities = packageManager.queryIntentActivities(
				intent, 0);
		boolean isIntentSafe = activities.size() > 0;
		if (isIntentSafe)
			startActivity(intent);
		else
			showToast(getString(R.string.NoAppsToOpen));
	}

	public void FileRename(final String filepath) {
		Context mContext = getApplicationContext();
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.rename, null);
		EditText name = (EditText) layout.findViewById(R.id.NewName);
		if (appTheme.equals("Light"))
			name.setTextColor(Color.BLACK);
		if (appTheme.equals("Dark"))
			name.setTextColor(Color.WHITE);
		AlertDialog.Builder aDialog = new AlertDialog.Builder(RootActivity.this);
		aDialog.setTitle(getString(R.string.Renaming));
		aDialog.setView(layout);

		aDialog.setPositiveButton(getString(R.string.Finish),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						EditText NewName = (EditText) layout
								.findViewById(R.id.NewName);
						String newNameStr = NewName.getText().toString();

						RootFile now = new RootFile(filepath);
						now.renameTo(new File(nowPath + "/" + newNameStr));
						getDir(nowPath);
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.toggleSoftInput(
								InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
					}
				});
		aDialog.setNegativeButton(getString(R.string.Cancel),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.toggleSoftInput(
								InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
					}
				});

		AlertDialog ad = aDialog.create();
		ad.show();
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
	}

	public void MakeNewFolder() {
		Context mContext = getApplicationContext();
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.getname, null);
		final EditText FolderName = (EditText) layout
				.findViewById(R.id.gettingName);
		if (appTheme.equals("Light"))
			FolderName.setTextColor(Color.BLACK);
		if (appTheme.equals("Dark"))
			FolderName.setTextColor(Color.WHITE);
		FolderName.setHint(R.string.newFolder_Hint);
		AlertDialog.Builder aDialog = new AlertDialog.Builder(RootActivity.this);
		aDialog.setTitle(getString(R.string.newFolder));
		aDialog.setView(layout);

		aDialog.setPositiveButton(getString(R.string.Finish),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						String newFolderName = FolderName.getText().toString();

						RootFile file = new RootFile(nowPath + "/"
								+ newFolderName);
						file.mkdir();
						getDir(nowPath);
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.toggleSoftInput(
								InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
					}
				});
		aDialog.setNegativeButton(getString(R.string.Cancel),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.toggleSoftInput(
								InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
					}
				});

		AlertDialog ad = aDialog.create();
		ad.show();
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
	}

	public void InitializeSearch() {
		Context mContext = getApplicationContext();
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.getname, null);
		final EditText SearchingFileName = (EditText) layout
				.findViewById(R.id.gettingName);
		if (appTheme.equals("Light"))
			SearchingFileName.setTextColor(Color.BLACK);
		if (appTheme.equals("Dark"))
			SearchingFileName.setTextColor(Color.WHITE);
		SearchingFileName.setHint(R.string.Search_Hint);
		AlertDialog.Builder aDialog = new AlertDialog.Builder(RootActivity.this);
		aDialog.setTitle(getString(R.string.Search));
		aDialog.setView(layout);

		aDialog.setPositiveButton(getString(R.string.Finish),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						sfilename = SearchingFileName.getText().toString();
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.toggleSoftInput(
								InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

						final ProgressDialog pdialog = ProgressDialog.show(
								RootActivity.this,
								getString(R.string.Searching),
								getString(R.string.Wait), true);
						new Thread(new Runnable() {
							@Override
							public void run() {
								ArrayList<SearchedRootFileProperty> arr = Search(
										nowPath,
										sfilename,
										new ArrayList<SearchedRootFileProperty>());

								Intent searchActivity = new Intent(
										RootActivity.this,
										RootSearchActivity.class);
								searchActivity.putParcelableArrayListExtra(
										"filelist", arr);
								startActivity(searchActivity);
								pdialog.dismiss();
							}
						}).start();
					}
				});
		aDialog.setNegativeButton(getString(R.string.Cancel),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.toggleSoftInput(
								InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
					}
				});

		AlertDialog ad = aDialog.create();
		ad.show();
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

	}

	public ArrayList<SearchedRootFileProperty> Search(String Path, String Name,
			ArrayList<SearchedRootFileProperty> arr) {
		RootFile file = new RootFile(Path);
		if (file.equals(null))
			return null;
		RootFile[] list = file.listFiles();

		int len = list.length;
		for (int i = 0; i < len; i++) {
			if (list[i].isDirectory() && !list[i].isHidden())
				Search(list[i].getAbsolutePath(), Name, arr);
			else {
				if (list[i].getName().contains(Name))
					arr.add(new SearchedRootFileProperty(getExtension(list[i]),
							list[i].getName(), DateFormat.format(
									"yyyy.MM.dd kk:mm", list[i].lastModified())
									.toString(), formatFileSize(list[i]
									.length()), list[i].getPerms(), list[i]
									.getAbsolutePath()));
			}
		}

		return arr;
	}

	public Drawable getApkIcon(String filePath) {
		PackageInfo packageInfo = getPackageManager().getPackageArchiveInfo(
				filePath, PackageManager.GET_ACTIVITIES);
		if (packageInfo == null)
			return null;
		ApplicationInfo appInfo = packageInfo.applicationInfo;
		appInfo.sourceDir = filePath;
		appInfo.publicSourceDir = filePath;
		return appInfo.loadIcon(getPackageManager());
	}

	public String getExtension(RootFile file) {
		String name = file.getName();
		int length = name.length() - 1;
		if (length < 0)
			return "";
		StringBuffer ext = new StringBuffer();
		while (true) {
			if (name.charAt(length) != '.')
				ext.append(name.charAt(length--));
			else
				break;
			if (length <= 0)
				return "";
		}

		StringBuffer temp = new StringBuffer();
		if (ext != null)
			temp = ext.reverse();
		return temp.toString();
	}

	public String getMIME(String ext) {
		return MimeTypeMap.getSingleton().getMimeTypeFromExtension(
				ext.toLowerCase());
	}

	public void FileCopy(RootFile from, RootFile to) throws IOException {
		RootTools.remount(to.getAbsolutePath(), "rw");
		String from_path = from.getAbsolutePath();
		String to_path = to.getAbsolutePath();
		if (from.isFile())
			RootTools.copyFile(from_path, to_path, false, true);
		else if (from.isDirectory()) {
			RootFile files[] = from.listFiles();
			String dir = to_path
					+ from_path.substring(
							from.getAbsolutePath().lastIndexOf("/"),
							from_path.length());
			int len = files.length;

			new RootFile(dir).mkdir();

			for (int i = 0; i < len; i++) {
				if ((new RootFile(from_path + "/" + files[i].getName()))
						.isDirectory())
					FileCopy(
							new RootFile(from_path + "/" + files[i].getName()),
							new RootFile(dir));

				else
					RootTools.copyFile(from_path + "/" + files[i].getName(),
							dir, false, true);
			}
		}
	}

	public String calcPerm(String perm) {
		int ret = 0;
		if (perm.charAt(0) == 'r')
			ret += 400;
		if (perm.charAt(1) == 'w')
			ret += 200;
		if (perm.charAt(2) == 'x')
			ret += 100;
		if (perm.charAt(3) == 'r')
			ret += 40;
		if (perm.charAt(4) == 'w')
			ret += 20;
		if (perm.charAt(5) == 'x')
			ret += 10;
		if (perm.charAt(6) == 'r')
			ret += 4;
		if (perm.charAt(7) == 'w')
			ret += 2;
		if (perm.charAt(8) == 'x')
			ret += 1;

		return String.format("%03d", ret);
	}

	public void showToast(final String Msg) {
		this.runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(getApplicationContext(), Msg, Toast.LENGTH_SHORT)
						.show();
			}
		});
	}

	public class FileAdapter extends BaseAdapter {
		private ArrayList<RootFileProperty> object;
		ImageLoader loader = new ImageLoader(getApplicationContext());
		boolean isScrolling = false;
		String filename, filedate, fileperm, filesize, txtPerm;
		String file, mimeType;
		StringBuilder sb = new StringBuilder(100);
		ViewHolder holder;

		public FileAdapter(ArrayList<RootFileProperty> object) {
			super();
			this.object = object;
		}

		@Override
		public int getCount() {
			return object.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			if (row == null) {
				LayoutInflater inflater = LayoutInflater
						.from(RootActivity.this);
				row = inflater.inflate(R.layout.rootrow, parent, false);
				holder = new ViewHolder();

				// Find View
				holder.fileicon = (ImageView) row.findViewById(R.id.icon);
				holder.filename = (TextView) row.findViewById(R.id.filename);
				holder.filedate = (TextView) row.findViewById(R.id.filedate);
				holder.fileperm = (TextView) row.findViewById(R.id.fileperm);
				holder.filesize = (TextView) row.findViewById(R.id.filesize);
				holder.check = (ImageView) row.findViewById(R.id.check);
				row.setTag(holder);
			} else
				holder = (ViewHolder) row.getTag();

			filename = object.get(position).getName();
			filedate = object.get(position).getDate();
			fileperm = object.get(position).getPerm();
			filesize = object.get(position).getSize();

			sb.setLength(0);
			if (fileperm.equals(""))
				txtPerm = "";
			else
				txtPerm = sb.append(fileperm).append(" [")
						.append(calcPerm(fileperm)).append("]").toString();

			String dir = nowPath.equals(root) ? nowPath
					+ object.get(position).getName() : nowPath + "/"
					+ object.get(position).getName();

			if (icon[position] != null)
				holder.fileicon.setImageDrawable(icon[position]);
			else {
				if (isScrolling)
					holder.fileicon.setImageResource(R.drawable.android);
				else {
					loader.DisplayImage(object.get(position).getName(),
							((BitmapDrawable) getApkIcon(dir)).getBitmap(),
							holder.fileicon);
					icon[position] = getApkIcon(dir);
				}
			}

			holder.filename.setText(filename);
			holder.filedate.setText(filedate);
			holder.fileperm.setText(txtPerm);
			holder.filesize.setText(filesize);
			holder.check.setVisibility(isSelected[position]);
			return row;
		}
	}

	static class ViewHolder {
		ImageView fileicon;
		TextView filename;
		TextView filedate;
		TextView fileperm;
		TextView filesize;
		ImageView check;
	}
}