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

public class RootFileProperty extends FileProperty {

	String FilePerm;

	public RootFileProperty() {}

	public RootFileProperty(String _icon, String _name, String _date, String _size, String _perm) {
		super(_icon, _name, _date, _size);
		FilePerm = _perm;
	}

	public String getPerm() { return FilePerm; }

	public void setPerm(String _perm) { FilePerm = _perm; }
}