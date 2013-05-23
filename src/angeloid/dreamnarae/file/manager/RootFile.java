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

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.os.PatternMatcher;
import android.util.Log;

import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.execution.Command;

@SuppressWarnings("serial")
public class RootFile extends File {

    private ArrayList<String> outLines;
    private String mPath;
    private String mName;
    private String mPerms;
    private String mSym;
    private Long mSize = null;

    public RootFile(String path) {
        super(path);
        
        mPath = path.substring(0, path.lastIndexOf("/"));
        if(!mPath.endsWith("/")) mPath += "/";
        
        mName = path.substring(path.lastIndexOf("/")+1);
        String w = "busybox ls -la '" + mPath + mName + "'";

        outLines = new ArrayList<String>();
        try 
        {
            Command cmd = new Command(0, w) {
                @Override
                public void output(int id, String line) 
                {
                    if(line.indexOf("\n") > -1) 
                    {
                        for(String s : line.split("\n")) output(id, s);
                    } 
                    else outLines.add(line);
                }
            };
            
            RootTools.getShell(true).add(cmd).waitForFinish();
            for(String line : outLines) 
            {
                String parsedName = getName(line);
                if(this.getPath().equals(parsedName) || parsedName.equals(".")) { setDetails(line); break; }
            }
        } 
        catch(Exception e) { Log.e("Exception", e.getMessage()); }
    }

    @Override
    public boolean canRead() {
        // Always TRUE
        return true;
    }

    @Override
    public boolean canWrite() {
        // Always TRUE
        return true;
    }

    @Override
    public boolean delete() {
        boolean result = true;
        RootTools.remount(this.getPath(), "rw");
        String w = "busybox rm -r '" + this.getPath() + "'";
        outLines = new ArrayList<String>();
        try 
        {
            Command cmd = new Command(0, w) {
                @Override
                public void output(int id, String line) {
                    if(line.indexOf("\n") > -1) 
                    {
                        for(String s : line.split("\n")) output(id, s);
                    } 
                    else outLines.add(line);
                }
            };
            
            RootTools.getShell(true).add(cmd).waitForFinish();
            for(String line : outLines) {
                if(line.contains("failed") || line.contains("can't remove")) result = false;
            }
        } catch(Exception e) { Log.e("Exception", e.getMessage()); }
        return result;
    }

    @Override
    public boolean exists() {
        return RootTools.exists(this.getPath());
    }

    @Override
    public boolean isDirectory() {
        if(mPerms != null) 
        {
            if(mPerms.startsWith("d")) return true;
            if(mPerms.startsWith("l")) return (new RootFile(mSym)).isDirectory();
        }
        
        return super.isDirectory();
    }

    @Override
    public boolean isFile() {
        if(mPerms != null) 
        {
            if(mPerms.startsWith("-")) return true;
            if(mPerms.startsWith("l")) return (new RootFile(mSym)).isFile();
        }
        return super.isFile();
    }

    @Override
    public long length() {
        if(mSize != null) return mSize;
        else return 0;
    }

    @Override
    public RootFile[] listFiles() {
        final ArrayList<RootFile> files = new ArrayList<RootFile>();
        if(!this.isDirectory()) return null;
        String actualPath = this.getPath().endsWith("/") ? this.getPath() : this.getPath() + "/";
        final String w = "busybox ls -la '" + actualPath + "'";

        outLines = new ArrayList<String>();
        try 
        {
            Command cmd = new Command(0, w) {
                @Override
                public void output(int id, String line) {
                    if(line.indexOf("\n") > -1) 
                    {
                        for(String s : line.split("\n")) output(id, s);
                    } 
                    else outLines.add(line);
                }
            };
            
            RootTools.getShell(true).add(cmd).waitForFinish();
            for(String line : outLines) 
            {
            	if(line.startsWith("total")) continue;
                line = getName(line);
                if(!line.equals(".") && !line.equals("..")) 
                {
                	files.add(new RootFile(actualPath + line));
                }
            }
        } catch(Exception e) { Log.e("Exception", e.getMessage()); }

        return files.toArray(new RootFile[0]);
    }

    @Override
    public boolean mkdir() {
        boolean result = true;
        RootTools.remount(this.getPath(), "rw");
        String w = "busybox mkdir '" + this.getPath() + "' && chmod 777 '" + this.getPath() + "'";
        outLines = new ArrayList<String>();
        try 
        {
            Command cmd = new Command(0, w) {
                @Override
                public void output(int id, String line) 
                {
                    if(line.indexOf("\n") > -1) 
                    {
                        for(String s : line.split("\n")) output(id, s);
                    } 
                    else outLines.add(line);
                }
            };
            
            RootTools.getShell(true).add(cmd).waitForFinish();
            for(String line : outLines) 
            {
                if(line.contains("failed") || line.contains("can't create")) result = false;
            }
            
        } 
        catch(Exception e) { Log.e("Exception", e.getMessage()); }
        return result;
    }

    @Override
    public boolean renameTo(File dest) {
        boolean result = true;
        RootTools.remount(this.getPath(), "rw");
        String w = "busybox mv '" + this.getPath() + "' '" + dest.getPath() + "'";
        outLines = new ArrayList<String>();
        try 
        {
            Command cmd = new Command(0, w) {
                @Override
                public void output(int id, String line) {
                    if(line.indexOf("\n") > -1) 
                    {
                        for(String s : line.split("\n")) output(id, s);
                    } 
                    else outLines.add(line);
                }
            };
            
            RootTools.getShell(true).add(cmd).waitForFinish();
            for(String line : outLines) 
            {
                if(line.contains("failed") || line.contains("can't rename")) result = false;
            }
            
        } 
        catch(Exception e) { Log.e("Exception", e.getMessage()); }
        return result;
    }

    public String getName(String line) {
    	String theName = "";

        PatternMatcher pmLong = new PatternMatcher(" [1-2][0-9][0-9][0-9]\\-[0-9]+\\-[0-9]+ ",
                PatternMatcher.PATTERN_SIMPLE_GLOB);
        boolean bLong = pmLong.match(line);
        Pattern p = Pattern.compile("[0-9][0-9]\\:[0-9][0-9] "
                + (bLong ? "[1-2][0-9][0-9][0-9] " : ""));
        Matcher m = p.matcher(line);
        boolean success = false;
        if(m.matches()) {
            theName = line.substring(m.end());
        }
        if(!success) {
            String[] parts = line.split(" +");
            if(parts.length >= 9)  {
                for(int i = 8; i < parts.length; i++) {
                    theName += parts[i] + " ";
                }
                theName = theName.trim();
            } else {
                theName = parts[parts.length-1];
            }

        }
        if(theName.indexOf(" -> ") > -1) {
            mSym = theName.substring(theName.indexOf(" -> ") + 4);
            String s = theName.substring(0, theName.indexOf(" -> ")).trim();
            if(s.indexOf("/") > -1) {
                theName = s.substring(s.lastIndexOf("/") +1);
            } else {
                theName = s;
            }
        }
        return theName;
    }

    public String getPerms() {
    	if(mPerms == null) return "";
		return mPerms.substring(1);
    	
    }
    
    @SuppressLint("SimpleDateFormat")
	@SuppressWarnings("deprecation")
	private void setDetails(String line) {
        java.text.DateFormat DateFormatInstance = new SimpleDateFormat("MMM dd yyyy HH:mm:ss");

        String theName = "";

        PatternMatcher pmLong = new PatternMatcher(" [1-2][0-9][0-9][0-9]\\-[0-9]+\\-[0-9]+ ",
                PatternMatcher.PATTERN_SIMPLE_GLOB);
        boolean bLong = pmLong.match(line);
        Pattern p = Pattern.compile("[0-9][0-9]\\:[0-9][0-9] "
                + (bLong ? "[1-2][0-9][0-9][0-9] " : ""));
        Matcher m = p.matcher(line);
        boolean success = false;
        if(m.matches()) {
            theName = line.substring(m.end());
            try {
                String sDate = line.substring(m.start(), m.end() - 1).trim();
                Date.parse(sDate);
                mSize = Long.parseLong(line.substring(line.lastIndexOf(" ", m.start()),
                        m.start() - 1).trim());
                success = true;
            } catch (Exception e) {
                Log.e("Couldn't parse date.", e.getMessage());
            }
            mPerms = line.split(" ")[0];
        }
        if(!success) {
            String[] parts = line.split(" +");
            if(parts.length > 5) {
                mPerms = parts[0];
                int i = 4;
                try {
                    if(parts.length >= 7)
                        mSize = Long.parseLong(parts[i++]);
                } catch (NumberFormatException e) {
                }
                try {
                    if(parts[i + 1].matches("(Sun|Mon|Tue|Wed|Thu|Fri|Sat)"))
                        i++;
                    String sDate = parts[i + 1] + " " + parts[i + 2];
                    if(parts.length > i + 3 && parts[i + 4].length() <= 4)
                        sDate += " " + parts[i + 4];
                    else {
                        sDate += " " + (Calendar.getInstance().get(Calendar.YEAR) + 1900);
                        i--;
                    }
                    if(parts.length > i + 2 && parts[i + 3].indexOf(":") > -1)
                        sDate += " " + parts[i + 3]; // Add Time
                    DateFormatInstance.parse(sDate).getTime();
                    success = true;
                } catch (Exception e) {
                }
            }
            theName = parts[parts.length - 1];
        }
        if(theName.indexOf(" -> ") > -1) {
            mSym = theName.substring(theName.indexOf(" -> ") + 4);
            theName = theName.substring(0, theName.indexOf(" -> ") - 1).trim();
        }
    }

}