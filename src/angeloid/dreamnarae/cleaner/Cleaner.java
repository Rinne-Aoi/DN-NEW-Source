/*=========================================================================
 *
 *  PROJECT:  SlimRoms
 *            Team Slimroms (http://www.slimroms.net)
 *
 *  COPYRIGHT Copyright (C) 2013 Slimroms http://www.slimroms.net
 *            All rights reserved
 *
 *  LICENSE   http://www.gnu.org/licenses/gpl-2.0.html GNU/GPL
 *
 *  AUTHORS:     fronti90
 *  DESCRIPTION: SlimSizer: manage your apps
 *
 *=========================================================================
 */
package angeloid.dreamnarae.cleaner;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import angeloid.dreamnarae.BaseSlidingActivity;
import angeloid.dreamnarae.R;

public class Cleaner extends BaseSlidingActivity {
    private final int STARTUP_DIALOG = 1;
    private final int DELETE_DIALOG = 2;
    private final int DELETE_MULTIPLE_DIALOG = 3;

    private ArrayList<String> mSysApp;

    Process superUser;
    DataOutputStream ds;

    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bam_cleaner);
        final Button delButton = (Button) findViewById(R.id.btn_delete);

        // create arraylist of apps not to be removed
        final ArrayList<String> safetyList = new ArrayList<String>();
        safetyList.add("BackupRestoreConfirmation.apk");
        safetyList.add("CertInstaller.apk");
        safetyList.add("Contacts.apk");
        safetyList.add("ContactsProvider.apk");
        safetyList.add("DefaultContainerService.apk");
        safetyList.add("DownloadProvider.apk");
        safetyList.add("DrmProvider.apk");
        safetyList.add("MediaProvider.apk");
        safetyList.add("Mms.apk");
        safetyList.add("PackageInstaller.apk");
        safetyList.add("Phone.apk");
        safetyList.add("Settings.apk");
        safetyList.add("SettingsProvider.apk");
        safetyList.add("Superuser.apk");
        safetyList.add("SystemUI.apk");
        safetyList.add("TelephonyProvider.apk");

        // create arraylist from /system/app content
        final String path = "/system/app";
        File system = new File(path);
        String[] sysappArray = system.list();
        mSysApp = new ArrayList<String>(
                Arrays.asList(sysappArray));

        // remove "apps not to be removed" from list and sort list
        mSysApp.removeAll(safetyList);
        Collections.sort(mSysApp);

        // populate listview via arrayadapter
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, mSysApp);

        // startup dialog
        showDialog(STARTUP_DIALOG, null, adapter);

        final ListView lv = (ListView) findViewById(R.string.listsystem);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lv.setAdapter(adapter);

        // longclick an entry
        lv.setOnItemLongClickListener(new OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                    final int arg2, long arg3) {
                // create deletion dialog
                String item = lv.getAdapter().getItem(arg2).toString();
                showDialog(DELETE_DIALOG, item, adapter);
                return false;
            }
        });
        // click button delete
        delButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // check which items are selected
                String item = null;
                int len = lv.getCount();
                SparseBooleanArray checked = lv.getCheckedItemPositions();
                for (int i = len - 1; i > 0; i--) {
                    if (checked.get(i)) {
                        item = mSysApp.get(i);
                    }
                }
                if (item == null) {
                    toast(getResources().getString(
                            R.string.sizer_message_noselect));
                    return;
                } else {
                    showDialog(DELETE_MULTIPLE_DIALOG, item, adapter);
                }
            }
        });
    }

    private void showDialog(int id, final String item,
            final ArrayAdapter<String> adapter) {
        // startup dialog
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        if (id == STARTUP_DIALOG) {
            // create warning dialog
            alert.setMessage(R.string.sizer_message_startup)
                    .setTitle(R.string.caution)
                    .setCancelable(true)
                    .setPositiveButton(R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                        int id) {
                                    // action for ok
                                    try {
                                        superUser = Runtime.getRuntime().exec("su");
                                        ds = new DataOutputStream(superUser.getOutputStream());
                                        ds.writeBytes("mount -o remount,rw /system" + "\n");
                                        ds.flush();
                                    } catch (IOException e) {

                                    }
                                    dialog.cancel();
                                }
                            });
            // delete dialog
        } else if (id == DELETE_DIALOG) {
            alert.setMessage(R.string.sizer_message_delete)
                    .setPositiveButton(R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                        int id) {
                                    // action for ok
                                    // call delete
                                    boolean successDel = delete(item);
                                    if (successDel == true) {
                                        // remove list entry
                                        adapter.remove(item);
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                            })
                    .setNegativeButton(R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                        int id) {
                                    // action for cancel
                                    dialog.cancel();
                                }
                            });
        } else if (id == DELETE_MULTIPLE_DIALOG) {
            alert.setMessage(R.string.sizer_message_delete)
                    .setPositiveButton(R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                        int id) {
                                    String itemMulti = null;
                                    final ListView lv = (ListView) findViewById(R.string.listsystem);
                                    int len = lv.getCount();
                                    SparseBooleanArray checked = lv.getCheckedItemPositions();
                                    for (int i = len - 1; i > 0; i--) {
                                        if (checked.get(i)) {
                                            itemMulti = mSysApp.get(i);
                                            // call delete
                                            boolean successDel = delete(itemMulti);
                                            if (successDel == true) {
                                                // remove list entry
                                                lv.setItemChecked(i, false);
                                                adapter.remove(itemMulti);
                                                adapter.notifyDataSetChanged();
                                            }
                                        }
                                    }
                                }
                            })
                    .setNegativeButton(R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                        int id) {
                                    // action for cancel
                                    dialog.cancel();
                                }
                            });
        }
        // show warning dialog
        alert.show();
    }


    public void toast(String text) {
        // easy toasts for all!
        Toast toast = Toast.makeText(getApplicationContext(), text,
                Toast.LENGTH_SHORT);
        toast.show();
    }

    private boolean delete(String appname) {
        String item = appname;
        final String path = "/system/app";
        File app = new File(path + "/" + item);

        try {
            ds.writeBytes("rm -rf " + app + "\n");
            ds.flush();
            Thread.sleep(1500);
        } catch (IOException e) {
        } catch (InterruptedException e) {

        }

        // check if app was deleted
        if (app.exists() == true) {
            toast(getResources().getString(R.string.delete_fail) + " " + item);
            return false;
        } else {
            toast(getResources().getString(R.string.delete_success) + " " + item);
            return true;
        }

    }
}
