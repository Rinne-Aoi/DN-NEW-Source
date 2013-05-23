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
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.*;
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
import android.widget.AdapterView.OnItemLongClickListener;

import java.io.*;
import java.util.*;

import angeloid.dreamnarae.R;

import com.stericson.RootTools.RootTools;

public class MainActivity extends ListActivity {
	
	ArrayList<FileProperty> item = null;
	ArrayList<String> path = null;
	final int MAX_LIST_ITEMS = 1000;
	String[] clipboard = new String [MAX_LIST_ITEMS + 1];
	int NumberofClipboardItems = 0;
	ListView list;
	String root;
	TextView myPath;
	int nowlevel = -1;
	Parcelable list_state[] = new Parcelable [MAX_LIST_ITEMS + 1];
	int isSelected[] = new int [MAX_LIST_ITEMS + 1];
	int NumberofSelectedItems = 0;
	long backPressedTime = 0;
	String nowPath = "";
	boolean showMultiSelectToast = true;
	boolean Renaming = false;
	int NumberOfImageFiles = 0;
	String sfilename;
	String appTheme;
	
	@SuppressLint("InlinedApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if(RootTools.isAccessGiven())
        {
        	if(!RootTools.isBusyboxAvailable())
        	{
        		showToast(getString(R.string.RequestBusybox));
        		RootTools.offerBusyBox(this);
        	}
        	Intent root = new Intent(MainActivity.this, RootActivity.class);
        	startActivity(root);
        	finish();
        }
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        appTheme = sharedPrefs.getString("AppTheme", null);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && appTheme != null)
        {
	        if(appTheme.equals("Light")) setTheme(android.R.style.Theme_Holo_Light_DarkActionBar);
	        if(appTheme.equals("Dark")) setTheme(android.R.style.Theme_Holo);
        }
        
        if(appTheme == null) appTheme = "Light";
        
        setContentView(R.layout.file_main);
        myPath = (TextView) findViewById(R.id.path);
        root = Environment.getExternalStorageDirectory().toString();	// Get Storage Path
        
        findViewById(R.id.CopyBtn).setVisibility(View.INVISIBLE);
	    findViewById(R.id.PasteBtn).setVisibility(View.INVISIBLE);
	    findViewById(R.id.MoveBtn).setVisibility(View.INVISIBLE);
	    findViewById(R.id.DeleteBtn).setVisibility(View.INVISIBLE);
	    PackageInfo pi = null;
		try { pi = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0); } 
		catch (NameNotFoundException e) {}
	    String version = pi.versionName;
	    showToast("Version " + version);
        getDir(root);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }
    
	private void getDir(String dirPath) {
		int path_len = myPath.getText().toString().length();		// Get Path's Length
		int tag_len = 10;	// Length of "Location: "
		if(path_len - tag_len < dirPath.length()) nowlevel++;	// Go into
		else if(path_len - tag_len > dirPath.length()) nowlevel--;	// Go back
	    nowPath = dirPath;
	    item = new ArrayList<FileProperty>();
	    path = new ArrayList<String>();
	    list = (ListView) findViewById(android.R.id.list);
	    list.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				SelectionItems(parent, position);
				if(showMultiSelectToast) { showToast(getString(R.string.NowStartMultiSelectMode)); showMultiSelectToast = false; }
				return true;
			}
	    });
	    
	    File f = new File(dirPath);
	    File[] files = f.listFiles();
	    
	    for(int i = 0; i <= MAX_LIST_ITEMS; i++) isSelected[i] = View.INVISIBLE;	// initialize isSelected with View.INVISIBLE [No Selected Items]
	    NumberofSelectedItems = 0;
        
        if(!dirPath.equals(root))
        {
        	item.add(new FileProperty("FOLDER", "../", getString(R.string.ParentFolder), ""));
        	path.add(f.getParent()); 
	    }

	    for(int i = 0; i < files.length; i++)
	    {
	    	File file = files[i];
	      
	    	if(!file.isHidden() && file.canRead())
	    	{
	    		path.add(file.getPath());
	    		
	    		String filesize = file.isDirectory() ? "" : formatFileSize(file.length());
	    		item.add(new FileProperty(
	    				getExtension(file),
	    				file.getName(),
	    				DateFormat.format("yyyy.MM.dd kk:mm", file.lastModified()).toString(),
	    				filesize));
	    	}
	    }

	    // Sort Array 'item'
	    for(int i = 0; i <= item.size()-2; i++)
	    {
	    	if(i == 0 && !nowPath.equals(root)) i = 1;
	    	for(int j = i+1; j <= item.size()-1; j++)
	    	{
	    		if(item.get(i).getName().compareTo(item.get(j).getName()) > 0)
	    		{
	    			FileProperty temp = item.get(i);
	    			item.remove(i);
	    			item.add(i, item.get(j-1));
	    			item.remove(j);
	    			item.add(j, temp);
	    		}
	    	}
	    }
	    
	    // Sort Array 'path'
	    Collections.sort(path);
	    FileAdapter adapter = new FileAdapter(item);
	    if(path_len - tag_len <= dirPath.length()) // Go Into
	    {
	    	list_state[nowlevel] = list.onSaveInstanceState();
	    	list.setAdapter(adapter);
	    }
	    else if(path_len - tag_len > dirPath.length())	// Go Back
	    {
	    	list.setAdapter(adapter);
	    	list.onRestoreInstanceState(list_state[nowlevel+1]);
	    }
	    myPath.setText(getString(R.string.Path) + " " + dirPath);
	}

	@Override
	protected void onListItemClick(ListView parent, View view, int position, long id) 
	{
		if(Renaming) 
		{ 
			FileRename(path.get(position));
			Renaming = false;
			return; 
		}
		
		if(NumberofSelectedItems > 0)	// If now Selecting File/Dirs
		{
			SelectionItems(parent, position);
			return;
		}
		
		File file = new File(path.get(position));
		if (file.isDirectory()) getDir(path.get(position));
		else
		{
			String name = file.getName();
			int length = name.length() - 1;
			StringBuffer ext = new StringBuffer();
			while(true)
			{
				if(name.charAt(length) != 46) ext.append(name.charAt(length--));
				else break;
				if(length == 0) break;
			}
			
			StringBuffer temp = new StringBuffer();
			if(ext != null) temp = ext.reverse();
			String extension = temp.toString();
			String mimeType =  MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.toLowerCase());
			if(mimeType == null) { showToast(getString(R.string.NoAppsToOpen)); return; }
			runFile(file, mimeType);
		}
		
		if(NumberofClipboardItems == 0)
		{
			findViewById(R.id.CopyBtn).setVisibility(View.INVISIBLE);
		    findViewById(R.id.PasteBtn).setVisibility(View.INVISIBLE);
		    findViewById(R.id.MoveBtn).setVisibility(View.INVISIBLE);
		    findViewById(R.id.DeleteBtn).setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void onBackPressed() {
		if(nowlevel > 0 && NumberofSelectedItems == 0) getDir(path.get(0));
		else if(nowlevel >= 0 && NumberofSelectedItems > 0)
		{
			for(int i = 0; i <= MAX_LIST_ITEMS; i++) isSelected[i] = View.INVISIBLE;	// initialize isSelected with View.INVISIBLE [No Selected Items]
		    NumberofSelectedItems = 0;
		    if(showMultiSelectToast) showToast(getString(R.string.EndofMultiSelectMode));
		    getDir(nowPath);
		    findViewById(R.id.CopyBtn).setVisibility(View.INVISIBLE);
		    findViewById(R.id.PasteBtn).setVisibility(View.INVISIBLE);
		    findViewById(R.id.MoveBtn).setVisibility(View.INVISIBLE);
		    findViewById(R.id.DeleteBtn).setVisibility(View.INVISIBLE);
		}
		else 
		{
			long tempTime = System.currentTimeMillis();
			long intervalTime = tempTime - backPressedTime;
			
			if (0 <= intervalTime && 2000 >= intervalTime) super.onBackPressed(); 
			else 
			{ 
				backPressedTime = tempTime; 
				showToast(getString(R.string.PressAgainToExit));
			}
		}
	}
	
	public void onCopyBtnPress(View v) {
		clipboard = new String [NumberofSelectedItems];
		int n = 0, i = 0;
		while(true)
		{
			if(isSelected[i] == View.VISIBLE)
			{
				clipboard[n] = path.get(i);
				if(n < NumberofSelectedItems) n++;
				else break;
			}
			if(i < MAX_LIST_ITEMS) i++;
			else break;
		}
		
		NumberofClipboardItems = NumberofSelectedItems;
		for(i = 0; i <= MAX_LIST_ITEMS; i++) isSelected[i] = View.INVISIBLE;
		NumberofSelectedItems = 0;
		getDir(nowPath);
		findViewById(R.id.CopyBtn).setVisibility(View.INVISIBLE);
		findViewById(R.id.PasteBtn).setVisibility(View.VISIBLE);
		findViewById(R.id.MoveBtn).setVisibility(View.VISIBLE);
	}
	
	public void onPasteBtnPress(View v) {
		final ProgressDialog dialog = ProgressDialog.show(this, getString(R.string.Copying), getString(R.string.Wait), true);
		
		final Handler handler = new Handler() {
		    @Override
		    public void handleMessage(Message msg) {
		    	getDir(nowPath);
		    }
		};
		
		new Thread(new Runnable() {
	        @Override
	        public void run() {
	            try
	            {
					for(int i = 0; i < NumberofClipboardItems; i++)
					{
						copyDirectory(new File(clipboard[i]), new File(nowPath));
					}
				} 
				catch (IOException e) 
				{
					showToast(e.getMessage());
				}
	            handler.sendEmptyMessage(0);
				dialog.dismiss();
			}
	    }).start();
	    
	    findViewById(R.id.CopyBtn).setVisibility(View.INVISIBLE);
	    findViewById(R.id.PasteBtn).setVisibility(View.INVISIBLE);
	    findViewById(R.id.MoveBtn).setVisibility(View.INVISIBLE);
	    findViewById(R.id.DeleteBtn).setVisibility(View.INVISIBLE);
	}
	
	public void onDeleteBtnPress(View v) {
		final ProgressDialog dialog = ProgressDialog.show(this, getString(R.string.Deleting), getString(R.string.Wait), true);
		
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
	            while(true)
	            {
					if(isSelected[i] == View.VISIBLE)
					{
						DeleteFile(path.get(i));
					}
					if(i < MAX_LIST_ITEMS) i++;
					else break;
				}
				
	            handler.sendEmptyMessage(0);
	            dialog.dismiss();
			}
	    }).start();
		
		findViewById(R.id.CopyBtn).setVisibility(View.INVISIBLE);
		findViewById(R.id.PasteBtn).setVisibility(View.INVISIBLE);
		findViewById(R.id.MoveBtn).setVisibility(View.INVISIBLE);
		findViewById(R.id.DeleteBtn).setVisibility(View.INVISIBLE);
	}
	
	public void onMoveBtnPress(View v) {
		final ProgressDialog dialog = ProgressDialog.show(this, getString(R.string.Moving), getString(R.string.Wait), true);
		final Handler handler = new Handler() {
		    @Override
		    public void handleMessage(Message msg) {
		    	getDir(nowPath);
		    }
		};
		
		new Thread(new Runnable() {
	        @Override
	        public void run() {
	            try
	            {
					for(int i = 0; i < NumberofClipboardItems; i++)
					{
						copyDirectory(new File(clipboard[i]), new File(nowPath));
						DeleteFile(clipboard[i]);
					}
				} 
				catch (IOException e) 
				{
					showToast(e.getMessage());
				}
	            
	            handler.sendEmptyMessage(0);
				dialog.dismiss();
			}
	    }).start();
	    
	    findViewById(R.id.CopyBtn).setVisibility(View.INVISIBLE);
	    findViewById(R.id.PasteBtn).setVisibility(View.INVISIBLE);
	    findViewById(R.id.MoveBtn).setVisibility(View.INVISIBLE);
	    findViewById(R.id.DeleteBtn).setVisibility(View.INVISIBLE);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.NewFolder:
	        	MakeNewFolder();
	            return true;
	            
	        case R.id.Rename:
	        	Renaming = true;
	        	showToast(getString(R.string.StartRenaming));
	        	return true;
	        	
	        case R.id.ChangeStorage:
	        	ChangeStorage();
	        	return true;
	        	
	        case R.id.Search:
	        	InitializeSearch();
	        	return true;
	   
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public void SelectionItems(AdapterView<?> parent, int position) {
		Button CopyBtn = (Button) findViewById(R.id.CopyBtn);
		Button DeleteBtn = (Button) findViewById(R.id.DeleteBtn);
		
		if(isSelected[position] == View.VISIBLE)	// Already Selected
		{
			// When NumberofSelectedItems == 1, After this process it will be 0, then set copy/delete button invisible
			if(NumberofSelectedItems == 1)
			{
				CopyBtn.setVisibility(View.INVISIBLE);
				DeleteBtn.setVisibility(View.INVISIBLE);
			}
			
			isSelected[position] = View.INVISIBLE;	// Deselect
			NumberofSelectedItems--;
			int firstPos = parent.getFirstVisiblePosition();	// Now 'Showing' First Position
			int wantedPos = position - firstPos;	// Position : Now 'Showing' Selected Position, wantedPos : Now absolute position
			View v = parent.getChildAt(wantedPos);	// getChildAt() 's Parameter must an absolute position number.
			ImageView check = (ImageView) v.findViewById(R.id.check);
			check.setVisibility(View.INVISIBLE);	// Uncheck it
			check.refreshDrawableState();	// Refresh Button
			return;
		}
		
		CopyBtn.setVisibility(View.VISIBLE);	// Set Visible
		DeleteBtn.setVisibility(View.VISIBLE);	// Set Visible
		isSelected[position] = View.VISIBLE;	// Set Visible
		NumberofSelectedItems++;
		int firstPos = parent.getFirstVisiblePosition();
		int wantedPos = position - firstPos;
		View v = parent.getChildAt(wantedPos);
		ImageView check = (ImageView) v.findViewById(R.id.check);
		check.setVisibility(View.VISIBLE);
		check.refreshDrawableState();	// Refresh
		return;
	}
	
	public String formatFileSize(long size) {
		if (size < 1024) return new Long(size).toString() + " bytes";
		else if (size < 1024 * 1024)
		{
			String t = String.format("%.2f KB", (double) size / 1024);
			return t;
		}
		else if (size < 1024 * 1024 * 1024)
		{
			String t = String.format("%.2f MB", (double) size / 1024 / 1024);
			return t;
		}
		else
		{
			String t = String.format("%.2f GB", (double) size / 1024 / 1024 / 1024);
			return t;
		}
	}
	
	public void runFile(File file, String MimeType) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		Uri uri = Uri.fromFile(file);
		intent.setDataAndType(uri, MimeType);
		startActivity(intent);
	}
	
	public void FileRename(final String filepath) {
		Context mContext = getApplicationContext();
    	LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
    	final View layout = inflater.inflate(R.layout.file_rename, null);
    	EditText name = (EditText) layout.findViewById(R.id.NewName);
    	if(appTheme.equals("Light")) name.setTextColor(Color.BLACK);
    	if(appTheme.equals("Dark")) name.setTextColor(Color.WHITE);
    	AlertDialog.Builder aDialog = new AlertDialog.Builder(MainActivity.this);
    	aDialog.setTitle(getString(R.string.Renaming));
    	aDialog.setView(layout);
    	
    	aDialog.setPositiveButton(getString(R.string.Finish), new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int which) {
    			EditText NewName = (EditText) layout.findViewById(R.id.NewName);
    			String newNameStr = NewName.getText().toString();
    			
    			File now = new File(filepath);
    			now.renameTo(new File(nowPath + "/" + newNameStr));
    			getDir(nowPath);
    			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    			imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    		}
    	});
    	aDialog.setNegativeButton(getString(R.string.Cancel), new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int which) {
    			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    			imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    		}
    	});
    	
    	AlertDialog ad = aDialog.create();
    	ad.show();
    	InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    	imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
	}
	
	public void MakeNewFolder() {
		Context mContext = getApplicationContext();
    	LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
    	final View layout = inflater.inflate(R.layout.file_getname, null);
    	final EditText FolderName = (EditText) layout.findViewById(R.id.gettingName);
    	if(appTheme.equals("Light")) FolderName.setTextColor(Color.BLACK);
    	if(appTheme.equals("Dark")) FolderName.setTextColor(Color.WHITE);
		FolderName.setHint(R.string.newFolder_Hint);
    	AlertDialog.Builder aDialog = new AlertDialog.Builder(MainActivity.this);
    	aDialog.setTitle(getString(R.string.newFolder));
    	aDialog.setView(layout);
    	
    	aDialog.setPositiveButton(getString(R.string.Finish), new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int which) {
    			String newFolderName = FolderName.getText().toString();
    			
    			File file = new File(nowPath + "/" + newFolderName);
    			file.mkdir();
    			getDir(nowPath);
    			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    			imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    		}
    	});
    	aDialog.setNegativeButton(getString(R.string.Cancel), new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int which) {
    			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    			imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    		}
    	});
    	
    	AlertDialog ad = aDialog.create();
    	ad.show();
    	InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    	imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
	}
	
	public void ChangeStorage() {
    	AlertDialog.Builder aDialog = new AlertDialog.Builder(MainActivity.this);
    	aDialog.setTitle(getString(R.string.ChangeStorage));
    	
    	StorageList.getStorageOptions();
    	if (!(Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD))
    		showToast(getString(R.string.ShownDataCanIncorrect));
    	else showToast(getString(R.string.CannotGetStorageDataOnFroyo));
    	
    	String[] items = new String[StorageList.paths.length];
    	
    	int NowSelected = 0;
    	for(int i = 0; i < StorageList.paths.length; i++)
    	{
    		items[i] = StorageList.paths[i] + "\n[" + getString(StorageList.labels[i]) + "]";
    		if(StorageList.paths[i].equalsIgnoreCase(root)) NowSelected = i;
    	}

    	aDialog.setSingleChoiceItems(items, NowSelected, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				item = null;
				path = null;
				clipboard = new String [MAX_LIST_ITEMS + 1];
				NumberofClipboardItems = 0;
				nowlevel = -1;
				list_state = new Parcelable [MAX_LIST_ITEMS + 1];
				isSelected = new int [MAX_LIST_ITEMS + 1];
				NumberofSelectedItems = 0;
				backPressedTime = 0;
				nowPath = "";
				showMultiSelectToast = true;
				Renaming = false;
				
				root = StorageList.paths[which];
				myPath.setText("/");
				getDir(root);
				
				dialog.dismiss();
			}
    	});
    	
    	AlertDialog ad = aDialog.create();
    	ad.show();
	}
	
	public void InitializeSearch() {
		Context mContext = getApplicationContext();
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.file_getname, null);
		final EditText SearchingFileName = (EditText) layout.findViewById(R.id.gettingName);
    	if(appTheme.equals("Light")) SearchingFileName.setTextColor(Color.BLACK);
    	if(appTheme.equals("Dark")) SearchingFileName.setTextColor(Color.WHITE);
		SearchingFileName.setHint(R.string.Search_Hint);
		AlertDialog.Builder aDialog = new AlertDialog.Builder(MainActivity.this);
    	aDialog.setTitle(getString(R.string.Search));
    	aDialog.setView(layout);
    	
    	aDialog.setPositiveButton(getString(R.string.Finish), new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int which) {
    			sfilename = SearchingFileName.getText().toString();
    			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    			imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    			
    			final ProgressDialog pdialog = ProgressDialog.show(MainActivity.this, 
    					getString(R.string.Searching), getString(R.string.Wait), true);
    			new Thread(new Runnable() {
    		        @Override
    		        public void run() {
    		        	ArrayList<SearchedFileProperty> arr = Search(nowPath, sfilename, 
    		        			new ArrayList<SearchedFileProperty>());
    		        	
			        	Intent searchActivity = new Intent(MainActivity.this, SearchActivity.class);
			        	searchActivity.putParcelableArrayListExtra("filelist", arr);
			        	startActivity(searchActivity);
				        pdialog.dismiss();
    				}
    		    }).start();
    		}
    	});
    	aDialog.setNegativeButton(getString(R.string.Cancel), new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int which) {
    			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    			imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    		}
    	});
    	
    	AlertDialog ad = aDialog.create();
    	ad.show();
    	InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    	imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
		
	}
	
	public ArrayList<SearchedFileProperty> Search(String Path, String Name, ArrayList<SearchedFileProperty> arr) {
		File file = new File(Path);
		if(file.equals(null)) return null;
		File list[] = file.listFiles();
		for(int i = 0; i < list.length; i++)
		{
			if(list[i].isDirectory() && !list[i].isHidden()) Search(list[i].getAbsolutePath(), Name, arr);
			else
			{
				if(list[i].getName().contains(Name)) 
					arr.add(new SearchedFileProperty(
						getExtension(list[i]),
						list[i].getName(),
						DateFormat.format("yyyy.MM.dd kk:mm", list[i].lastModified()).toString(),
	    				formatFileSize(list[i].length()),
	    				list[i].getAbsolutePath()));
			}
		}
		
		return arr;
	}
	
	public Drawable getIcon(String file, String filePath) {
		Resources res = getApplicationContext().getResources();
		Drawable icon = res.getDrawable(R.drawable.others);
		if (new File(filePath).isDirectory()) return res.getDrawable(R.drawable.folder);
		if (file.equals("apk")) {
			PackageInfo packageInfo = 
					getPackageManager().getPackageArchiveInfo(filePath, PackageManager.GET_ACTIVITIES);
			if(packageInfo == null) return icon;
			ApplicationInfo appInfo = packageInfo.applicationInfo;
			appInfo.sourceDir = filePath;
			appInfo.publicSourceDir = filePath;
			icon = appInfo.loadIcon(getPackageManager());
		}
		
		if (	file.equals("zip") || 
				file.equals("7z")  || 
				file.equals("rar") ||
				file.equals("tar")) icon = res.getDrawable(R.drawable.compressed);

		String mimeType = getMIME(file);
		if(mimeType == null) return icon;
		
		if(mimeType.startsWith("image")) icon = res.getDrawable(R.drawable.image);
		
		if(mimeType.startsWith("audio")) icon = res.getDrawable(R.drawable.audio);
		return icon;
	}
	
	public String getExtension(File file) {
		String name = file.getName();
		int length = name.length() - 1;
		StringBuffer ext = new StringBuffer();
		while(true)
		{
			if(name.charAt(length) != '.') ext.append(name.charAt(length--));
			else break;
			if(length <= 0) break;
		}
		
		StringBuffer temp = new StringBuffer();
		if(ext != null) temp = ext.reverse();
		return temp.toString();
	}
	
	public String getMIME(String ext) {
		return MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext.toLowerCase());
	}
	
    public boolean copyDirectory(File sourceLocation, File targetLocation) throws IOException {
    	if (sourceLocation.getParent().equals(targetLocation.getAbsolutePath())) return false;
        if (sourceLocation.isDirectory()) 
        {
        	File TargetFolder = new File(targetLocation, sourceLocation.getName());
        	if(!TargetFolder.exists()) TargetFolder.mkdirs();
        	String[] children = sourceLocation.list();
			for(int i = 0; i < children.length; i++) 
	        {
				copyDirectory(new File(sourceLocation, children[i]),
			        	new File(TargetFolder.toString()));
	        }
        }
        else 
        {
        	String s = sourceLocation.getName();
        	File Target = makeFile(targetLocation, s);
        	if(Target == null) return false;
        	InputStream in = new FileInputStream(sourceLocation);
        	OutputStream out = new FileOutputStream(Target);
            
        	byte[] buf = new byte[1024];
        	int len;
        	while ((len = in.read(buf)) > 0) out.write(buf, 0, len);
        	in.close();
        	out.close();
        }
        
        return true;
    }
    
    private File makeFile(File dir, String file_path) {
        File file = null;
        if(dir.isDirectory()) {
            file = new File(dir.toString(), file_path);
            if(file != null && !file.exists() && !file.isDirectory()) {
                try { file.createNewFile(); }
                catch (IOException e) { showToast(e.getMessage()); }
            }
            else if(file.exists()) { showToast(getString(R.string.FileAlreadyExists)); return null; }
        }
        return file;
    }
    
    public void DeleteFile(String dir) {
    	File fileOrDirectory = new File(dir);
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                DeleteFile(child.toString());

        fileOrDirectory.delete();
    }
    
	public void showToast(final String Msg) {
		this.runOnUiThread(new Runnable() {
			  public void run() {
			    Toast.makeText(getApplicationContext(), Msg, Toast.LENGTH_SHORT).show();
			  }
		});
	}
	
	public class FileAdapter extends BaseAdapter {
		private ArrayList<FileProperty> object;
		
		public FileAdapter(ArrayList<FileProperty> object) {
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
			ViewHolder holder;
			if(convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
				convertView = inflater.inflate(R.layout.file_row, parent, false);
				holder = new ViewHolder();
				
				// Find View
				holder.fileicon = (ImageView) convertView.findViewById(R.id.icon);
				holder.filename = (TextView) convertView.findViewById(R.id.filename);
				holder.filedate = (TextView) convertView.findViewById(R.id.filedate);
				holder.filesize = (TextView) convertView.findViewById(R.id.filesize);
				holder.check = (ImageView) convertView.findViewById(R.id.check);
				convertView.setTag(holder);
			}
			else holder = (ViewHolder) convertView.getTag();
			
			String fileicon = object.get(position).getIcon();
			String filename = object.get(position).getName();
			String filedate = object.get(position).getDate();
			String filesize = object.get(position).getSize();
			
			// Set Resources
			holder.fileicon.setImageDrawable(getIcon(fileicon, nowPath + "/" + filename));
			holder.filename.setText(filename);
			holder.filedate.setText(filedate);
			holder.filesize.setText(filesize);
			holder.check.setVisibility(isSelected[position]);
			return convertView;
		}
		
		class ViewHolder {
			ImageView fileicon;
			TextView filename;
			TextView filedate;
			TextView filesize;
			ImageView check;
		}
	}
}