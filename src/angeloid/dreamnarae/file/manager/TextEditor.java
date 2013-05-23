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

import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.execution.Command;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.EditText;
import angeloid.dreamnarae.R;

public class TextEditor extends Activity {

	@SuppressLint("InlinedApi")
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

        if(appTheme == null) appTheme = "Light";
        
        setContentView(R.layout.texteditor);
        Intent intent = this.getIntent();
        String filepath = intent.getStringExtra("filepath");
        
        EditText e = (EditText) findViewById(R.id.TextEdit);
        
        final StringBuilder outLines = new StringBuilder();
        try 
        {
        	String w = "busybox cat " + filepath;
        	Command cmd = new Command(0, w) {
                @Override
                public void output(int id, String line) {
                    if(line.indexOf("\n") > -1) 
                    {
                        for(String s : line.split("\n")) output(id, s);
                    }
                    else if(!outLines.toString().equals("")) outLines.append("\n" + line);
                    else outLines.append(line);
                }
            };
            
            RootTools.getShell(true).add(cmd).waitForFinish();
        } catch(Exception ex) { Log.e("Exception", ex.getMessage()); }
        
        e.setText(outLines);
    }
}