package com.bpear.makomodem;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.bpear.makomodem.adapter.TabsPagerAdapter;
import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.exceptions.RootDeniedException;
import com.stericson.RootTools.execution.CommandCapture;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MainActivity extends FragmentActivity implements
        ActionBar.TabListener {

    String url, filename;
    int position;

    private ViewPager viewPager;
    private ActionBar actionBar;

    // Tab titles
    private String[] tabs = {"Stock modems", "LTE hybrid modems", "Build.prop"};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_twrp:
                openTWRP();
                return true;
            case R.id.action_phone:
                openTesting(); // *#*#4636#*#*
                return true;
            case R.id.action_help:
                openHelp(); // *#*#4636#*#*
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void openTesting() { // Open testing menu, allows network change etc *#*#4636#*#*
        Intent in = new Intent(Intent.ACTION_MAIN);
        in.setClassName("com.android.settings", "com.android.settings.TestingSettings");
        startActivity(in);
    }

    public void imgDownload() { // Download modem function
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        request.setDestinationInExternalPublicDir("/Modems", filename);

        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE); // get download service and enqueue file
        manager.enqueue(request);
    }

    protected void openTWRP() { // Installs TWRP recovery
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        url = "http://goo.gl/dUjlKI";
                        filename = "openrecovery-twrp-2.7.0.0-mako.img";
                        imgDownload();
                        BroadcastReceiver onComplete = new BroadcastReceiver() { //Check if download is done
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                CommandCapture command = new CommandCapture(0, "dd if=/sdcard/Modems/openrecovery-twrp-2.7.0.0-mako.img of=/dev/block/mmcblk0p7", "rm -f /sdcard/Modems/openrecovery-twrp-2.7.0.0-mako.img");
                                try {
                                    RootTools.getShell(true).add(command); // run command with SU privileges
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (TimeoutException e) {
                                    e.printStackTrace();
                                } catch (RootDeniedException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        registerReceiver(onComplete, new IntentFilter(
                                DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This will install TeamWin Recovery onto your device. Do you want to continue?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }


    protected void openHelp() { // Installs TWRP recovery
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=oNOUbXboZXY")));
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Not sure what to do? Watch a great how to video by OfficialSoftModder!").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (RootTools.isAccessGiven()) {
            // Do something maybe
        } else {
            Toast.makeText(this, "You are not rooted!", Toast.LENGTH_SHORT).show();
        }

        // Check if app is running on Nexus 4
        if (android.os.Build.MODEL.equals("Nexus 4")) {
            Toast.makeText(this, "Your device: Nexus 4", Toast.LENGTH_SHORT).show();
        } else {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            //Yes button clicked
                            finish();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("It appears that you are not using a Nexus 4. Using this app could harm your device. Do you want to exit now?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }

        // Initialization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        TabsPagerAdapter mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }

        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
        position = tab.getPosition();
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onBackPressed() {
        if (position > 0) {
            viewPager.setCurrentItem(position - 1, true); // Move position back 1 tab
        } else {
            moveTaskToBack(true); // Keep app open in background
        }
    }
}
