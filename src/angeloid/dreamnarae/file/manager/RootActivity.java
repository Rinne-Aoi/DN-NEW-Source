package angeloid.dreamnarae.file.manager;

import angeloid.dreamnarae.R;

import android.annotation.SuppressLint;
import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.*;
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
import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.execution.Command;

@SuppressLint("HandlerLeak")
public class RootActivity extends ListActivity {
	
	ArrayList<RootFileProperty> item = null;
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
        
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        appTheme = sharedPrefs.getString("AppTheme", null);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && appTheme != null)
        {
	        if(appTheme.equals("Light")) setTheme(android.R.style.Theme_Holo_Light_DarkActionBar);
	        if(appTheme.equals("Dark")) setTheme(android.R.style.Theme_Holo);
        }
        
        if(appTheme == null) appTheme = "Light";
        
        setContentView(R.layout.file_rootmain);
        myPath = (TextView) findViewById(R.id.rpath);
        root = "/";
        findViewById(R.id.rCopyBtn).setVisibility(View.INVISIBLE);
	    findViewById(R.id.rPasteBtn).setVisibility(View.INVISIBLE);
	    findViewById(R.id.rMoveBtn).setVisibility(View.INVISIBLE);
	    findViewById(R.id.rDeleteBtn).setVisibility(View.INVISIBLE);
        getDir(root);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.root, menu);
        return true;
    }
    
	private void getDir(String dirPath) {
		int path_len = myPath.getText().toString().length();		// Get Path's Length
		int tag_len = 10;	// Length of "Location: "
		if(path_len - tag_len < dirPath.length()) nowlevel++;	// Go into
		else if(path_len - tag_len > dirPath.length()) nowlevel--;	// Go back
	    nowPath = dirPath;
	    item = new ArrayList<RootFileProperty>();
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
	    
	    RootFile f = new RootFile(dirPath);
	    RootFile files[] = f.listFiles();
	    
	    for(int i = 0; i <= MAX_LIST_ITEMS; i++) isSelected[i] = View.INVISIBLE;	// initialize isSelected with View.INVISIBLE [No Selected Items]
	    NumberofSelectedItems = 0;
        
        if(!dirPath.equals(root))
        {
        	item.add(new RootFileProperty("FOLDER", "../", getString(R.string.ParentFolder), "", ""));
        	path.add(f.getParent()); 
	    }

	    for(int i = 0; i < files.length; i++)
	    {
	    	RootFile file = files[i];
	      
	    	if(!file.isHidden() && file.canRead())
	    	{
	    		path.add(file.getPath());
	    		
	    		String filesize = file.isDirectory() ? "" : formatFileSize(file.length());
	    		item.add(new RootFileProperty(
	    				getExtension(file),
	    				file.getName(),
	    				DateFormat.format("yyyy.MM.dd kk:mm", file.lastModified()).toString(),
	    				filesize,
	    				file.getPerms()));
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
	    			RootFileProperty temp = item.get(i);
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
		
		RootFile file = new RootFile(path.get(position));
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
				if(length <= 0) { ext = null; break; }
			}
			
			if(ext == null) ext = new StringBuffer().append("");
			StringBuffer temp = new StringBuffer();
			if(ext != null) temp = ext.reverse();
			String extension = temp.toString();
			String mimeType =  MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.toLowerCase());
			if(mimeType == null) { showToast(getString(R.string.NoAppsToOpen)); return; }
			runFile(file, mimeType);
		}
		
		if(NumberofClipboardItems == 0)
		{
			findViewById(R.id.rCopyBtn).setVisibility(View.INVISIBLE);
		    findViewById(R.id.rPasteBtn).setVisibility(View.INVISIBLE);
		    findViewById(R.id.rMoveBtn).setVisibility(View.INVISIBLE);
		    findViewById(R.id.rDeleteBtn).setVisibility(View.INVISIBLE);
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
		    findViewById(R.id.rCopyBtn).setVisibility(View.INVISIBLE);
		    findViewById(R.id.rPasteBtn).setVisibility(View.INVISIBLE);
		    findViewById(R.id.rMoveBtn).setVisibility(View.INVISIBLE);
		    findViewById(R.id.rDeleteBtn).setVisibility(View.INVISIBLE);
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
	
	public void onrCopyBtnPress(View v) {
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
		findViewById(R.id.rCopyBtn).setVisibility(View.INVISIBLE);
		findViewById(R.id.rPasteBtn).setVisibility(View.VISIBLE);
		findViewById(R.id.rMoveBtn).setVisibility(View.VISIBLE);
	}
	
	public void onrPasteBtnPress(View v) {
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
						copyDirectory(new RootFile(clipboard[i]), new RootFile(nowPath));
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
	    
	    findViewById(R.id.rCopyBtn).setVisibility(View.INVISIBLE);
	    findViewById(R.id.rPasteBtn).setVisibility(View.INVISIBLE);
	    findViewById(R.id.rMoveBtn).setVisibility(View.INVISIBLE);
	    findViewById(R.id.rDeleteBtn).setVisibility(View.INVISIBLE);
	}
	
	public void onrDeleteBtnPress(View v) {
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
						new RootFile(path.get(i)).delete();
					}
					if(i < MAX_LIST_ITEMS) i++;
					else break;
				}
				
	            handler.sendEmptyMessage(0);
	            dialog.dismiss();
			}
	    }).start();
		
		findViewById(R.id.rCopyBtn).setVisibility(View.INVISIBLE);
		findViewById(R.id.rPasteBtn).setVisibility(View.INVISIBLE);
		findViewById(R.id.rMoveBtn).setVisibility(View.INVISIBLE);
		findViewById(R.id.rDeleteBtn).setVisibility(View.INVISIBLE);
	}
	
	public void onrMoveBtnPress(View v) {
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
	            for(int i = 0; i < NumberofClipboardItems; i++)
				{
	            	RootTools.remount(nowPath, "rw");
					final String w = "busybox mv " + clipboard[i] + " " + nowPath;
					Command cmd = new Command(0, w) {
				        @Override
				        public void output(int id, String line) {}
				    };
				    
				    try { RootTools.getShell(true).add(cmd).waitForFinish(); } 
				    catch (Exception e) { showToast(e.getMessage()); }
				}
	            
	            handler.sendEmptyMessage(0);
				dialog.dismiss();
			}
	    }).start();
	    
	    findViewById(R.id.rCopyBtn).setVisibility(View.INVISIBLE);
	    findViewById(R.id.rPasteBtn).setVisibility(View.INVISIBLE);
	    findViewById(R.id.rMoveBtn).setVisibility(View.INVISIBLE);
	    findViewById(R.id.rDeleteBtn).setVisibility(View.INVISIBLE);
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
		Button rCopyBtn = (Button) findViewById(R.id.rCopyBtn);
		Button rDeleteBtn = (Button) findViewById(R.id.rDeleteBtn);
		
		if(isSelected[position] == View.VISIBLE)	// Already Selected
		{
			// When NumberofSelectedItems == 1, After this process it will be 0, then set copy/delete button invisible
			if(NumberofSelectedItems == 1)
			{
				rCopyBtn.setVisibility(View.INVISIBLE);
				rDeleteBtn.setVisibility(View.INVISIBLE);
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
		
		rCopyBtn.setVisibility(View.VISIBLE);	// Set Visible
		rDeleteBtn.setVisibility(View.VISIBLE);	// Set Visible
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
	
	public void runFile(RootFile file, String MimeType) {
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
    	AlertDialog.Builder aDialog = new AlertDialog.Builder(RootActivity.this);
    	aDialog.setTitle(getString(R.string.Renaming));
    	aDialog.setView(layout);
    	
    	aDialog.setPositiveButton(getString(R.string.Finish), new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int which) {
    			EditText NewName = (EditText) layout.findViewById(R.id.NewName);
    			String newNameStr = NewName.getText().toString();
    			
    			RootFile now = new RootFile(filepath);
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
    	AlertDialog.Builder aDialog = new AlertDialog.Builder(RootActivity.this);
    	aDialog.setTitle(getString(R.string.newFolder));
    	aDialog.setView(layout);
    	
    	aDialog.setPositiveButton(getString(R.string.Finish), new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int which) {
    			String newFolderName = FolderName.getText().toString();
    			
    			RootFile file = new RootFile(nowPath + "/" + newFolderName);
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
    	AlertDialog.Builder aDialog = new AlertDialog.Builder(RootActivity.this);
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
		AlertDialog.Builder aDialog = new AlertDialog.Builder(RootActivity.this);
    	aDialog.setTitle(getString(R.string.Search));
    	aDialog.setView(layout);
    	
    	aDialog.setPositiveButton(getString(R.string.Finish), new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int which) {
    			sfilename = SearchingFileName.getText().toString();
    			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    			imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    			
    			final ProgressDialog pdialog = ProgressDialog.show(RootActivity.this, 
    					getString(R.string.Searching), getString(R.string.Wait), true);
    			new Thread(new Runnable() {
    		        @Override
    		        public void run() {
    		        	ArrayList<SearchedRootFileProperty> arr = Search(nowPath, sfilename, 
    		        			new ArrayList<SearchedRootFileProperty>());
    		        	
			        	Intent searchActivity = new Intent(RootActivity.this, RootSearchActivity.class);
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
	
	public ArrayList<SearchedRootFileProperty> Search(String Path, String Name, ArrayList<SearchedRootFileProperty> arr) {
		RootFile file = new RootFile(Path);
		if(file.equals(null)) return null;
	    RootFile[] list = file.listFiles();
	    
		for(int i = 0; i < list.length; i++)
		{
			if(list[i].isDirectory() && !list[i].isHidden()) Search(list[i].getAbsolutePath(), Name, arr);
			else
			{
				if(list[i].getName().contains(Name)) 
					arr.add(new SearchedRootFileProperty(
						getExtension(list[i]),
						list[i].getName(),
						DateFormat.format("yyyy.MM.dd kk:mm", list[i].lastModified()).toString(),
	    				formatFileSize(list[i].length()),
	    				list[i].getPerms(),
	    				list[i].getAbsolutePath()));
			}
		}
		
		return arr;
	}
	
	public Drawable getIcon(String file, String filePath) {
		Resources res = getApplicationContext().getResources();
		Drawable icon = res.getDrawable(R.drawable.others);
		if (new RootFile(filePath).isDirectory()) return res.getDrawable(R.drawable.folder);
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
	
	public String getExtension(RootFile file) {
		String name = file.getName();
		int length = name.length() - 1;
		StringBuffer ext = new StringBuffer();
		while(true)
		{
			if(name.charAt(length) != '.') ext.append(name.charAt(length--));
			else break;
			if(length <= 0) return "";
		}
		
		StringBuffer temp = new StringBuffer();
		if(ext != null) temp = ext.reverse();
		return temp.toString();
	}
	
	public String getMIME(String ext) {
		return MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext.toLowerCase());
	}
	
    public boolean copyDirectory(RootFile sourceLocation, RootFile targetLocation) throws IOException {
    	RootTools.remount(targetLocation.getAbsolutePath(), "rw");
    	final String w = "busybox cp -r " + sourceLocation.getAbsolutePath() + " " + targetLocation.getAbsolutePath();
    	Command cmd = new Command(0, w) {
            @Override
            public void output(int id, String line) {}
        };
        
        try { RootTools.getShell(true).add(cmd).waitForFinish(); } 
        catch (Exception e) { showToast(e.getMessage()); }
        return true;
    }
    
    public int calcPerm(String perm) {
    	int ret = 0;
    	if(perm.charAt(0) == 'r') ret += 400;
    	if(perm.charAt(1) == 'w') ret += 200;
    	if(perm.charAt(2) == 'x') ret += 100;
    	if(perm.charAt(3) == 'r') ret += 40;
    	if(perm.charAt(4) == 'w') ret += 20;
    	if(perm.charAt(5) == 'x') ret += 10;
    	if(perm.charAt(6) == 'r') ret += 4;
    	if(perm.charAt(7) == 'w') ret += 2;
    	if(perm.charAt(8) == 'x') ret += 1;
    	
    	return ret;
    }
    
	public void showToast(final String Msg) {
		this.runOnUiThread(new Runnable() {
			  public void run() {
			    Toast.makeText(getApplicationContext(), Msg, Toast.LENGTH_SHORT).show();
			  }
		});
	}
	
	public class FileAdapter extends BaseAdapter {
		private ArrayList<RootFileProperty> object;
		
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
			ViewHolder holder;
			if(convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(RootActivity.this);
				convertView = inflater.inflate(R.layout.file_rootrow, parent, false);
				holder = new ViewHolder();
				
				// Find View
				holder.fileicon = (ImageView) convertView.findViewById(R.id.icon);
				holder.filename = (TextView) convertView.findViewById(R.id.filename);
				holder.filedate = (TextView) convertView.findViewById(R.id.filedate);
				holder.fileperm = (TextView) convertView.findViewById(R.id.fileperm);
				holder.filesize = (TextView) convertView.findViewById(R.id.filesize);
				holder.check = (ImageView) convertView.findViewById(R.id.check);
				convertView.setTag(holder);
			}
			else holder = (ViewHolder) convertView.getTag();
			
			String fileicon = object.get(position).getIcon();
			String filename = object.get(position).getName();
			String filedate = object.get(position).getDate();
			String fileperm = object.get(position).getPerm();
			String filesize = object.get(position).getSize();
			
			String txtPerm = fileperm.equals("") ? "" : fileperm + " [" + Integer.toString(calcPerm(fileperm)) + "]";
			// Set Resources
			holder.fileicon.setImageDrawable(getIcon(fileicon, nowPath + "/" + filename));
			holder.filename.setText(filename);
			holder.filedate.setText(filedate);
			holder.fileperm.setText(txtPerm);
			holder.filesize.setText(filesize);
			holder.check.setVisibility(isSelected[position]);
			return convertView;
		}
		
		class ViewHolder {
			ImageView fileicon;
			TextView filename;
			TextView filedate;
			TextView fileperm;
			TextView filesize;
			ImageView check;
		}
	}
}