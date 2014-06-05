package com.bpear.makomodem;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.exceptions.RootDeniedException;
import com.stericson.RootTools.execution.CommandCapture;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeoutException;

// Thanks to Stericson for RootTools project! https://code.google.com/p/roottools/


public class StockModemFragment extends Fragment implements View.OnClickListener {
    int type = 0;
    int keep = 1;
    Random r = new Random();
    CommandCapture command;
    CommandCapture command2;
    int mirror = r.nextInt(3 - 1 + 1) + 1;
    String url, zipname;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflate ...
        View view = inflater.inflate(R.layout.fragment_stock, container, false);
        assert view != null;
        Button b = (Button) view.findViewById(R.id.sFlash_Button);
        b.setOnClickListener(this); // listen for "Flash" button click
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        Button rb1 = (Button) getActivity().findViewById(R.id.radio_s98); // Button listening
        rb1.setOnClickListener(next_Listener);

        Button rb2 = (Button) getActivity().findViewById(R.id.radio_s97);
        rb2.setOnClickListener(next_Listener);

        Button rb3 = (Button) getActivity().findViewById(R.id.radio_s84);
        rb3.setOnClickListener(next_Listener);

        Button rb4 = (Button) getActivity().findViewById(R.id.radio_s83);
        rb4.setOnClickListener(next_Listener);

        Button rb5 = (Button) getActivity().findViewById(R.id.radio_s54);
        rb5.setOnClickListener(next_Listener);

        Button rb6 = (Button) getActivity().findViewById(R.id.radio_s48);
        rb6.setOnClickListener(next_Listener);

        Button rb7 = (Button) getActivity().findViewById(R.id.radio_s33);
        rb7.setOnClickListener(next_Listener);

        Button rb8 = (Button) getActivity().findViewById(R.id.radio_s27);
        rb8.setOnClickListener(next_Listener);

        Button rb9 = (Button) getActivity().findViewById(R.id.radio_s24);
        rb9.setOnClickListener(next_Listener);

        Button rb10 = (Button) getActivity().findViewById(R.id.radio_s02); // Button listening
        rb10.setOnClickListener(next_Listener);

        Switch s1 = (Switch) getActivity().findViewById(R.id.switchKeep);
        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getActivity(), "Modem file will be deleted after install.", Toast.LENGTH_SHORT).show(); // Prompt toast message
                    keep = 0;
                } else {
                    Toast.makeText(getActivity(), "Modem will remain in /sdcard/Modems folder.", Toast.LENGTH_SHORT).show(); // Prompt toast message
                    keep = 1;
                }
            }
        });
    }

    private View.OnClickListener next_Listener = new View.OnClickListener() {
        public void onClick(View v) {

            //xml find out which radio button has been checked ...
            RadioButton rb1 = (RadioButton) getActivity().findViewById(R.id.radio_s98);  //Link buttons
            RadioButton rb2 = (RadioButton) getActivity().findViewById(R.id.radio_s97);
            RadioButton rb3 = (RadioButton) getActivity().findViewById(R.id.radio_s84);
            RadioButton rb4 = (RadioButton) getActivity().findViewById(R.id.radio_s83);
            RadioButton rb5 = (RadioButton) getActivity().findViewById(R.id.radio_s54);
            RadioButton rb6 = (RadioButton) getActivity().findViewById(R.id.radio_s48);
            RadioButton rb7 = (RadioButton) getActivity().findViewById(R.id.radio_s33);
            RadioButton rb8 = (RadioButton) getActivity().findViewById(R.id.radio_s27);
            RadioButton rb9 = (RadioButton) getActivity().findViewById(R.id.radio_s24);
            RadioButton rb10 = (RadioButton) getActivity().findViewById(R.id.radio_s02);
            if (rb1.isChecked()) { // check which radio button is checked
                type = 1;
            }
            if (rb2.isChecked()) {
                type = 2;
            }
            if (rb3.isChecked()) {
                type = 3;
            }
            if (rb4.isChecked()) {
                type = 4;
            }
            if (rb5.isChecked()) {
                type = 5;
            }
            if (rb6.isChecked()) {
                type = 6;
            }
            if (rb7.isChecked()) {
                type = 7;
            }
            if (rb8.isChecked()) {
                type = 8;
            }
            if (rb9.isChecked()) {
                type = 9;
            }
            if (rb10.isChecked()) {
                type = 10;
            }
        }
    };

    public void modemDownload() { // Download modem function
        String file = android.os.Environment.getExternalStorageDirectory().getPath() + "/Modems/" + zipname;
        File f = new File(file);
        if (f.exists()) { // Check if file exists, and flash without downloading
            Toast.makeText(getActivity(), "File already exists", Toast.LENGTH_SHORT).show();
            flashModem();
        } else { // run if file does not exist
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

            request.setDestinationInExternalPublicDir("/Modems", zipname);

            DownloadManager manager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE); // get download service and enqueue file
            manager.enqueue(request);
        }
    }

    public void flashModem() {
        try {
            if (keep == 1) { // check if user wants to keep or delete modem file.
                RootTools.getShell(true).add(command); // run command with SU privileges
            } else {
                RootTools.getShell(true).add(command2); // run command with SU privileges
            }
            RootTools.getShell(true).add(command);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (RootDeniedException e) {
            e.printStackTrace();
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sFlash_Button:
                if (type > 0) {
                    Toast.makeText(getActivity(), "Phone will reboot when modem is downloaded!", Toast.LENGTH_SHORT).show(); // Prompt toast message
                }
                else {
                    Toast.makeText(getActivity(), "Nothing will be flashed, please select a modem.", Toast.LENGTH_SHORT).show(); // Prompt toast message
                }
                switch (type) { // Do following cases depending on which button is checked
                    case 1:
                        if (mirror == 2) {
                            url = "http://www.bpear.me/downloads/mako/stock/0.98.zip";
                        } else {
                            url = "http://rebel-rom.googlecode.com/files/cwm-radio-mako-m9615a-cefwmazm-2.0.1700.98.zip"; // Set URL to download
                        }
                        zipname = "Stock_0.98.zip"; // Set download name
                        command = new CommandCapture(0, "echo '--update_package=/sdcard/0/Modems/Stock_0.98.zip' > /cache/recovery/command", "reboot recovery"); // flash
                        command2 = new CommandCapture(0, "dd if=/sdcard/Modems/Stock_0.98.zip of=/cache/recovery/Stock_0.98.zip", "rm /sdcard/Modems/Stock_0.98.zip", "echo '--update_package=/cache/recovery/Stock_0.98.zip' > /cache/recovery/command", "reboot recovery"); // Flash and delete
                        modemDownload(); // Start download
                        BroadcastReceiver onComplete = new BroadcastReceiver() { //Check if download is done
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                flashModem();
                            }
                        };

                        getActivity().registerReceiver(onComplete, new IntentFilter(
                                DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                        break;

                    case 2:
                        if (mirror == 2) {
                            url = "http://www.bpear.me/downloads/mako/stock/0.97.zip";
                        } else {
                            url = "http://rebel-rom.googlecode.com/files/cwm-radio-mako-m9615a-cefwmazm-2.0.1700.97.zip";
                        }
                        zipname = "Stock_0.97.zip";
                        command = new CommandCapture(0, "echo '--update_package=/sdcard/0/Modems/Stock_0.97.zip' > /cache/recovery/command", "reboot recovery");
                        command2 = new CommandCapture(0, "dd if=/sdcard/Modems/Stock_0.97.zip of=/cache/recovery/Stock_0.97.zip", "rm /sdcard/Modems/Stock_0.97.zip", "echo '--update_package=/cache/recovery/Stock_0.97.zip' > /cache/recovery/command", "reboot recovery"); // Flash and delete
                        modemDownload();
                        onComplete = new BroadcastReceiver() { //Check if download is done
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                flashModem();
                            }
                        };

                        getActivity().registerReceiver(onComplete, new IntentFilter(
                                DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                        break;

                    case 3:
                        if (mirror == 2) {
                            url = "http://www.bpear.me/downloads/mako/stock/0.84.zip";
                        } else {
                            url = "http://rebel-rom.googlecode.com/files/cwm-radio-mako-m9615a-cefwmazm-2.0.1700.84.zip";
                        }
                        zipname = "Stock_0.84.zip";
                        command = new CommandCapture(0, "echo '--update_package=/sdcard/0/Modems/Stock_0.84.zip' > /cache/recovery/command", "reboot recovery");
                        command2 = new CommandCapture(0, "dd if=/sdcard/Modems/Stock_0.84.zip of=/cache/recovery/Stock_0.84.zip", "rm /sdcard/Modems/Stock_0.84.zip", "echo '--update_package=/cache/recovery/Stock_0.84.zip' > /cache/recovery/command", "reboot recovery"); // Flash and delete
                        modemDownload();
                        onComplete = new BroadcastReceiver() { //Check if download is done
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                flashModem();
                            }
                        };

                        getActivity().registerReceiver(onComplete, new IntentFilter(
                                DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                        break;

                    case 4:
                        if (mirror == 2) {
                            url = "http://www.bpear.me/downloads/mako/stock/0.83.zip";
                        } else {
                            url = "http://rebel-rom.googlecode.com/files/cwm-radio-mako-m9615a-cefwmazm-2.0.1700.83.zip";
                        }
                        zipname = "Stock_0.83.zip";
                        command = new CommandCapture(0, "echo '--update_package=/sdcard/0/Modems/Stock_0.83.zip' > /cache/recovery/command", "reboot recovery");
                        command2 = new CommandCapture(0, "dd if=/sdcard/Modems/Stock_0.83.zip of=/cache/recovery/Stock_0.83.zip", "rm /sdcard/Modems/Stock_0.83.zip", "echo '--update_package=/cache/recovery/Stock_0.83.zip' > /cache/recovery/command", "reboot recovery"); // Flash and delete
                        modemDownload();
                        onComplete = new BroadcastReceiver() { //Check if download is done
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                flashModem();
                            }
                        };

                        getActivity().registerReceiver(onComplete, new IntentFilter(
                                DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                        break;

                    case 5:
                        if (mirror == 2) {
                            url = "http://www.bpear.me/downloads/mako/stock/0.54.zip";
                        } else {
                            url = "http://rebel-rom.googlecode.com/files/cwm-radio-mako-m9615a-cefwmazm-2.0.1700.54.zip";
                        }
                        zipname = "Stock_0.54.zip";
                        command = new CommandCapture(0, "echo '--update_package=/sdcard/0/Modems/Stock_0.54.zip' > /cache/recovery/command", "reboot recovery");
                        command2 = new CommandCapture(0, "dd if=/sdcard/Modems/Stock_0.54.zip of=/cache/recovery/Stock_0.54.zip", "rm /sdcard/Modems/Stock_0.54.zip", "echo '--update_package=/cache/recovery/Stock_0.54.zip' > /cache/recovery/command", "reboot recovery"); // Flash and delete
                        modemDownload();
                        onComplete = new BroadcastReceiver() { //Check if download is done
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                flashModem();
                            }
                        };

                        getActivity().registerReceiver(onComplete, new IntentFilter(
                                DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                        break;

                    case 6:
                        if (mirror == 2) {
                            url = "http://www.bpear.me/downloads/mako/stock/0.48.zip";
                        } else {
                            url = "http://rebel-rom.googlecode.com/files/cwm-radio-mako-m9615a-cefwmazm-2.0.1700.48.zip";
                        }
                        zipname = "Stock_0.48.zip";
                        command = new CommandCapture(0, "echo '--update_package=/sdcard/0/Modems/Stock_0.48.zip' > /cache/recovery/command", "reboot recovery");
                        command2 = new CommandCapture(0, "dd if=/sdcard/Modems/Stock_0.48.zip of=/cache/recovery/Stock_0.48.zip", "rm /sdcard/Modems/Stock_0.48.zip", "echo '--update_package=/cache/recovery/Stock_0.48.zip' > /cache/recovery/command", "reboot recovery"); // Flash and delete
                        modemDownload();
                        onComplete = new BroadcastReceiver() { //Check if download is done
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                flashModem();
                            }
                        };

                        getActivity().registerReceiver(onComplete, new IntentFilter(
                                DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                        break;

                    case 7:
                        if (mirror == 2) {
                            url = "http://www.bpear.me/downloads/mako/stock/0.33.zip";
                        } else {
                            url = "http://rebel-rom.googlecode.com/files/cwm-radio-mako-m9615a-cefwmazm-2.0.1700.33.zip";
                        }
                        zipname = "Stock_0.33.zip";
                        command = new CommandCapture(0, "echo '--update_package=/sdcard/0/Modems/Stock_0.33.zip' > /cache/recovery/command", "reboot recovery");
                        command2 = new CommandCapture(0, "dd if=/sdcard/Modems/Stock_0.33.zip of=/cache/recovery/Stock_0.33.zip", "rm /sdcard/Modems/Stock_0.33.zip", "echo '--update_package=/cache/recovery/Stock_0.33.zip' > /cache/recovery/command", "reboot recovery"); // Flash and delete
                        modemDownload();
                        onComplete = new BroadcastReceiver() { //Check if download is done
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                flashModem();
                            }
                        };

                        getActivity().registerReceiver(onComplete, new IntentFilter(
                                DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                        break;

                    case 8:
                        if (mirror == 2) {
                            url = "http://www.bpear.me/downloads/mako/stock/0.27.zip";
                        } else {
                            url = "http://rebel-rom.googlecode.com/files/cwm-radio-mako-m9615a-cefwmazm-2.0.1700.27.zip";
                        }
                        zipname = "Stock_0.27.zip";
                        command = new CommandCapture(0, "echo '--update_package=/sdcard/0/Modems/Stock_0.27.zip' > /cache/recovery/command", "reboot recovery");
                        command2 = new CommandCapture(0, "dd if=/sdcard/Modems/Stock_0.27.zip of=/cache/recovery/Stock_0.27.zip", "rm /sdcard/Modems/Stock_0.27.zip", "echo '--update_package=/cache/recovery/Stock_0.27.zip' > /cache/recovery/command", "reboot recovery"); // Flash and delete
                        modemDownload();
                        onComplete = new BroadcastReceiver() { //Check if download is done
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                flashModem();
                            }
                        };

                        getActivity().registerReceiver(onComplete, new IntentFilter(
                                DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                        break;

                    case 9:
                        if (mirror == 2) {
                            url = "http://www.bpear.me/downloads/mako/stock/0.24.zip";
                        } else {
                            url = "http://rebel-rom.googlecode.com/files/cwm-radio-mako-m9615a-cefwmazm-2.0.1700.24.zip";
                        }
                        zipname = "Stock_0.24.zip";
                        command = new CommandCapture(0, "echo '--update_package=/sdcard/0/Modems/Stock_0.24.zip' > /cache/recovery/command", "reboot recovery");
                        command2 = new CommandCapture(0, "dd if=/sdcard/Modems/Stock_0.24.zip of=/cache/recovery/Stock_0.24.zip", "rm /sdcard/Modems/Stock_0.24.zip", "echo '--update_package=/cache/recovery/Stock_0.24.zip' > /cache/recovery/command", "reboot recovery"); // Flash and delete
                        modemDownload();
                        onComplete = new BroadcastReceiver() { //Check if download is done
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                flashModem();
                            }
                        };

                        getActivity().registerReceiver(onComplete, new IntentFilter(
                                DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                        break;

                    case 10:
                        if (mirror == 2) {
                            url = "http://goo.gl/drPlzQ";
                        } else {
                            url = "http://goo.gl/HHRLC8";
                        }
                        zipname = "Stock_0.02.zip";
                        command = new CommandCapture(0, "echo '--update_package=/sdcard/0/Modems/Stock_0.02.zip' > /cache/recovery/command", "reboot recovery");
                        command2 = new CommandCapture(0, "dd if=/sdcard/Modems/Stock_0.02.zip of=/cache/recovery/Stock_0.02.zip", "rm /sdcard/Modems/Stock_0.02.zip", "echo '--update_package=/cache/recovery/Stock_0.02.zip' > /cache/recovery/command", "reboot recovery"); // Flash and delete
                        modemDownload();
                        onComplete = new BroadcastReceiver() { //Check if download is done
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                flashModem();
                            }
                        };

                        getActivity().registerReceiver(onComplete, new IntentFilter(
                                DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                        break;

                    default:
                        break;
                }
                break;
        }
    }
}
