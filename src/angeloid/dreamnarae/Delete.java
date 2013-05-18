/* DreamNarae, Emotional Android Tools. / ZipDownloader / RootTools
 *
    Copyright (C) 2013 Seo, Dong-Gil in Angeloid Team.
    Copyright (c) 2011 Michael J. Portuesi (http://www.jotabout.com)
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

package angeloid.dreamnarae;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.exceptions.RootDeniedException;
import com.stericson.RootTools.execution.CommandCapture;

public class Delete extends Activity {
    Button apply;
    ImageView imageview;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete);
        apply = (Button) findViewById(R.id.apply);
        imageview = (ImageView) findViewById(R.id.imageview);
        apply.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (RootTools.isAccessGiven()) {
                    try {
                        Delete_File();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    } catch (RootDeniedException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(Delete.this, R.string.noroottoast,
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
        public void Delete_File() throws InterruptedException, IOException,
            TimeoutException, RootDeniedException {
            RootTools.remount("/system/", "RW");
            CommandCapture command = new CommandCapture(0,
                    "rm /system/98banner_dreamnarae_spica",
                    "rm /system/98banner_dreamnarae_miracle",
                    "rm /system/98banner_dreamnarae_save",
                    "rm /system/98banner_dreamnarae_prev",
                    "rm /system/98banner_dreamnarae_pure",
                    "rm /system/98banner_dreamnarae_brand",
                    "rm /system/etc/set.sh",
                    "rm /system/98banner_dreamnarae_spisave", "rm /system/allflag",
                    "rm /system/etc/init.d/00prop", "rm /system/etc/init.d/01io",
                    "rm /system/etc/init.d/02freq",
                    "rm /system/etc/init.d/00propss", "rm /system/etc/init.d/01ioss",
                    "rm /system/etc/init.d/02freqss",
                    "rm /system/etc/init.d/03zipalign",
                    "rm /system/etc/init.d/01kswapd0",
                    "rm /system/etc/init.d/02io", "rm /system/etc/init.d/03freq",
                    "rm /system/etc/init.d/04zipalign",
                    "rm /system/etc/init.d/00set",
                    "rm /system/etc/init.d/01property",
                    "rm /system/etc/init.d/02vsls", "rm /system/etc/init.d/03dch",
                    "rm /system/etc/init.d/04zip", "rm /system/etc/init.d/01vsls",
                    "rm /system/etc/init.d/02dch", "rm /system/etc/init.d/00sp",
                    "rm /system/etc/init.d/01v", "rm /system/etc/init.d/02deep",
                    "rm /system/etc/init.d/03zip",
                    "rm /system/etc/init.d/00proppv",
                    "rm /system/etc/init.d/01kswapd0pv",
                    "rm /system/etc/init.d/02iopv",
                    "rm /system/etc/init.d/03freqpv",
                    "rm /system/etc/init.d/04zippv",
                    "rm /system/etc/init.d/01iopv",
                    "rm /system/etc/init.d/02freqpv",
                    "rm /system/etc/init.d/00cpu", "rm /system/etc/init.d/01loosy",
                    "rm /system/etc/init.d/02memory",
                    "rm /system/etc/init.d/03prop",
                    "rm /system/etc/init.d/04cleaning",
                    "rm /system/etc/init.d/00b", "rm /system/etc/init.d/01r",
                    "rm /system/etc/init.d/02and",
                    "rm /system/etc/init.d/00cleaning",
                    "rm /system/etc/init.d/01cpu",
                    "rm /system/etc/init.d/02sysctl",
                    "rm /system/etc/init.d/03memory",
                    "rm /system/etc/init.d/04prop",
                    "rm /system/etc/init.d/05zipalign",
                    "rm /system/etc/init.d/06sysctl",
                    "rm /system/etc/init.d/00cpu",
                    "rm /system/etc/init.d/01memory",
                    "rm /system/etc/init.d/02prop",
                    "rm /system/etc/init.d/03cleaning",
                    "rm /system/etc/init.d/04zipalign",
                    "rm /system/etc/dreamnarae.sh");
            RootTools.getShell(true).add(command).waitForFinish();
            DeleteComplete();
        }
    public void DeleteComplete() throws IOException {
        File file = new File("/system/etc/dreamnarae.sh");
        if (file.length() > 0) {
            View view1 = this.getLayoutInflater().inflate(
                    R.layout.customdialog, null);

            Log.d("Install", "Delete Failed");
            TextView txtTitle = (TextView) view1.findViewById(R.id.title);
            txtTitle.setText(R.string.errortitle);
            txtTitle.setTextColor(Color.WHITE);
            txtTitle.setTextSize(20);
            TextView message = (TextView) view1.findViewById(R.id.message);
            message.setText(R.string.error2message);
            message.setTextColor(Color.WHITE);
            AlertDialog.Builder builder = new AlertDialog.Builder(
                  Delete.this);
            builder.setView(view1);
            builder.setCancelable(false);
            builder.setPositiveButton(R.string.infoclose,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            
                        }
                    }

            ).show();
        } else {
            View view = this.getLayoutInflater().inflate(R.layout.customdialog,
                    null);
            TextView txtTitle = (TextView) view.findViewById(R.id.title);
            txtTitle.setText(R.string.reboottitle);
            txtTitle.setTextColor(Color.WHITE);
            txtTitle.setTextSize(20);
            TextView message = (TextView) view.findViewById(R.id.message);
            message.setText(R.string.rebootmessage);
            message.setTextColor(Color.WHITE);
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    Delete.this);
            builder.setView(view);
            builder.setCancelable(false);
            builder.setPositiveButton(R.string.yes,
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            try {
                                CommandCapture command = new CommandCapture(0,
                                        "reboot");
                                try {
                                    RootTools.getShell(true).add(command)
                                            .waitForFinish();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (TimeoutException e) {
                                    e.printStackTrace();
                                } catch (RootDeniedException e) {
                                    e.printStackTrace();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    })

                    .setNegativeButton(R.string.no,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.dismiss();
                                    imageview.setImageResource(R.drawable.apply);

                                }
                            }).show();

        }
    }
}
