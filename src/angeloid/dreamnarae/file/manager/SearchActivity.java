package angeloid.dreamnarae.file.manager;

import java.io.File;
import java.util.ArrayList;
import angeloid.dreamnarae.R;


import android.app.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends ListActivity {
	
	ArrayList<SearchedFileProperty> filelist;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String appTheme = sharedPrefs.getString("AppTheme", null);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && appTheme != null)
        {
	        if(appTheme.equals("Light")) setTheme(android.R.style.Theme_Holo_Light_DarkActionBar);
	        if(appTheme.equals("Dark")) setTheme(android.R.style.Theme_Holo);
        }
        
        setContentView(R.layout.file_search);
        
        Intent intent = this.getIntent();
        filelist = intent.getParcelableArrayListExtra("filelist");
        
        FileAdapter adapter = new FileAdapter(filelist);
        ListView list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(adapter);
    }
    
	@Override
	protected void onListItemClick(ListView parent, View view, int position, long id) 
	{
		File file = new File(filelist.get(position).getPath());
		
		String name = file.getName();
		int length = name.length() - 1;
		StringBuffer ext = new StringBuffer();
		while(true)
		{
			if(name.charAt(length) != 46) ext.append(name.charAt(length--));
			else break;
			if(length <= 0) break;
		}
		
		StringBuffer temp = new StringBuffer();
		if(ext != null) temp = ext.reverse();
		String extension = temp.toString();
		String mimeType =  MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.toLowerCase());
		if(mimeType == null) { showToast(getString(R.string.NoAppsToOpen)); return; }
		runFile(file, mimeType);
	}
	
	public void runFile(File file, String MimeType) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		Uri uri = Uri.fromFile(file);
		intent.setDataAndType(uri, MimeType);
		startActivity(intent);
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
			if(length == 0) break;
		}
		
		StringBuffer temp = new StringBuffer();
		if(ext != null) temp = ext.reverse();
		return temp.toString();
	}
	
	public String getMIME(String ext) {
		return MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext.toLowerCase());
	}
	
	public void showToast(final String Msg) {
		this.runOnUiThread(new Runnable() {
			  public void run() {
			    Toast.makeText(getApplicationContext(), Msg, Toast.LENGTH_SHORT).show();
			  }
		});
	}
	
    public class FileAdapter extends BaseAdapter {
		private ArrayList<SearchedFileProperty> object;
		
		public FileAdapter(ArrayList<SearchedFileProperty> object) {
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
				LayoutInflater inflater = LayoutInflater.from(SearchActivity.this);
				convertView = inflater.inflate(R.layout.file_searchrow, parent, false);
				holder = new ViewHolder();
				
				// Find View
				holder.fileicon = (ImageView) convertView.findViewById(R.id.icon);
				holder.filename = (TextView) convertView.findViewById(R.id.filename);
				holder.filepath = (TextView) convertView.findViewById(R.id.filepath);
				holder.filedate = (TextView) convertView.findViewById(R.id.filedate);
				holder.filesize = (TextView) convertView.findViewById(R.id.filesize);
				convertView.setTag(holder);
			}
			else holder = (ViewHolder) convertView.getTag();
			
			String fileicon = object.get(position).getIcon();
			String filename = object.get(position).getName();
			String filepath = object.get(position).getPath();
			String filedate = object.get(position).getDate();
			String filesize = object.get(position).getSize();
			
			// Set Resources
			holder.fileicon.setImageDrawable(getIcon(fileicon, filepath));
			holder.filename.setText(filename);
			holder.filepath.setText(filepath);
			holder.filedate.setText(filedate);
			holder.filesize.setText(filesize);
			return convertView;
		}
		
		class ViewHolder {
			ImageView fileicon;
			TextView filename;
			TextView filepath;
			TextView filedate;
			TextView filesize;
		}
	}
}