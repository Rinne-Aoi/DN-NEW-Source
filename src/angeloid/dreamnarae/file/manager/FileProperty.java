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

import android.view.View;

public class FileProperty {
	
	private String FileIcon;	// Extension
	private String FileName;	// Name
	private String FileDate;	// Date
	private String FileSize;	// Size
	private int checked = View.INVISIBLE;
	
	public FileProperty() {}
	
	public FileProperty(String _icon, String _name, String _date, String _size) {
		FileIcon = _icon;
		FileName = _name;
		FileDate = _date;
		FileSize = _size;
	}
	
	public String getIcon() { return FileIcon; }
	
	public void setIcon(String _icon) { FileIcon = _icon; }
	
	public String getName() { return FileName; }
	
	public void setName(String _name) { FileName = _name; }
	
	public String getDate() { return FileDate; }
	
	public void setDate(String _date) { FileDate = _date; }
	
	public String getSize() { return FileSize; }
	
	public void setSize(String _size) { FileSize = _size; }

	public int getChecked() { return checked; }

	public void setChecked(int _checked) { checked = _checked; }
}