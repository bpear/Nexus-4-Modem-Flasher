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


public class LTEHybridFragment extends Fragment implements View.OnClickListener {

    int type;
    int keep = 1;
    Random r = new Random();
    CommandCapture command;
    CommandCapture command2;
    int mirror = r.nextInt(3 - 1 + 1) + 1;
    String url, zipname;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflate ...
        View view = inflater.inflate(R.layout.fragment_hybrid, container, false);
        assert view != null;
        Button b = (Button) view.findViewById(R.id.hFlash_Button); // listen for "Flash" button press
        b.setOnClickListener(this);
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button rb1 = (Button) getActivity().findViewById(R.id.radio_h33_98); //Button listening
        rb1.setOnClickListener(next_Listener);

        Button rb2 = (Button) getActivity().findViewById(R.id.radio_h27_98);
        rb2.setOnClickListener(next_Listener);

        Button rb3 = (Button) getActivity().findViewById(R.id.radio_h33_84);
        rb3.setOnClickListener(next_Listener);

        Button rb4 = (Button) getActivity().findViewById(R.id.radio_h33_54);
        rb4.setOnClickListener(next_Listener);

        Button rb5 = (Button) getActivity().findViewById(R.id.radio_h27_54);
        rb5.setOnClickListener(next_Listener);

        Button rb6 = (Button) getActivity().findViewById(R.id.radio_h33_97);
        rb6.setOnClickListener(next_Listener);

        Button rb7 = (Button) getActivity().findViewById(R.id.radio_h27_97);
        rb7.setOnClickListener(next_Listener);

        Button rb8 = (Button) getActivity().findViewById(R.id.radio_h27_02);
        rb8.setOnClickListener(next_Listener);

        Button rb9 = (Button) getActivity().findViewById(R.id.radio_h33_02);
        rb9.setOnClickListener(next_Listener);

        Switch s1 = (Switch) getActivity().findViewById(R.id.switchKeep2);
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
            RadioButton rb1 = (RadioButton) getActivity().findViewById(R.id.radio_h33_98);
            RadioButton rb2 = (RadioButton) getActivity().findViewById(R.id.radio_h27_98);
            RadioButton rb3 = (RadioButton) getActivity().findViewById(R.id.radio_h33_84);
            RadioButton rb4 = (RadioButton) getActivity().findViewById(R.id.radio_h33_54);
            RadioButton rb5 = (RadioButton) getActivity().findViewById(R.id.radio_h27_54);
            RadioButton rb6 = (RadioButton) getActivity().findViewById(R.id.radio_h33_97);
            RadioButton rb7 = (RadioButton) getActivity().findViewById(R.id.radio_h27_97);
            RadioButton rb8 = (RadioButton) getActivity().findViewById(R.id.radio_h27_02);
            RadioButton rb9 = (RadioButton) getActivity().findViewById(R.id.radio_h33_02);
            if (rb1.isChecked()) { // If button 1 is checked set type int to 1
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
            case R.id.hFlash_Button:
                Toast.makeText(getActivity(), "Phone will reboot when modem is downloaded!", Toast.LENGTH_SHORT).show();
                switch (type) { // Do following cases depending on which button is checked
                    case 1:
                        if (mirror == 2) {
                            url = "http://www.bpear.me/downloads/mako/hybrid/98-33.zip";
                        } else {
                            url = "https://rebel-rom.googlecode.com/files/98-33.zip";
                        }
                        zipname = "LTE_Hybrid_0.98_+_0.33.zip";
                        command = new CommandCapture(0, "echo '--update_package=/sdcard/0/Modems/LTE_Hybrid_0.98_+_0.33.zip' > /cache/recovery/command", "reboot recovery"); // add recovery install script commands and reboot
                        command2 = new CommandCapture(0, "dd if=/sdcard/Modems/LTE_Hybrid_0.98_+_0.33.zip of=/cache/recovery/LTE_Hybrid_0.98_+_0.33.zip", "rm /sdcard/Modems/LTE_Hybrid_0.98_+_0.33.zip", "echo '--update_package=/cache/recovery/LTE_Hybrid_0.98_+_0.33.zip' > /cache/recovery/command", "reboot recovery"); // Flash and delete
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
                            url = "http://www.bpear.me/downloads/mako/hybrid/98-27.zip";
                        } else {
                            url = "http://rebel-rom.googlecode.com/files/98-27.zip";
                        }
                        zipname = "LTE_Hybrid_0.98_+_0.27.zip";
                        command = new CommandCapture(0, "echo '--update_package=/sdcard/0/Modems/LTE_Hybrid_0.98_+_0.27.zip' > /cache/recovery/command", "reboot recovery");
                        command2 = new CommandCapture(0, "dd if=/sdcard/Modems/LTE_Hybrid_0.98_+_0.27.zip of=/cache/recovery/LTE_Hybrid_0.98_+_0.27.zip", "rm /sdcard/Modems/LTE_Hybrid_0.98_+_0.27.zip", "echo '--update_package=/cache/recovery/LTE_Hybrid_0.98_+_0.27.zip' > /cache/recovery/command", "reboot recovery"); // Flash and delete
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
                            url = "http://www.bpear.me/downloads/mako/hybrid/LTEhybrid33-84.zip";
                        } else {
                            url = "https://rebel-rom.googlecode.com/files/LTEhybrid33-84.zip";
                        }
                        zipname = "LTE_Hybrid_0.84_+_0.33.zip";
                        command = new CommandCapture(0, "echo '--update_package=/sdcard/0/Modems/LTE_Hybrid_0.84_+_0.33.zip' > /cache/recovery/command", "reboot recovery");
                        command2 = new CommandCapture(0, "dd if=/sdcard/Modems/LTE_Hybrid_0.84_+_0.33.zip of=/cache/recovery/LTE_Hybrid_0.84_+_0.33.zip", "rm /sdcard/Modems/LTE_Hybrid_0.84_+_0.33.zip", "echo '--update_package=/cache/recovery/LTE_Hybrid_0.84_+_0.33.zip' > /cache/recovery/command", "reboot recovery"); // Flash and delete
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
                            url = "http://www.bpear.me/downloads/mako/hybrid/LTEhybrid33-54.zip";
                        } else {
                            url = "http://rebel-rom.googlecode.com/files/LTEhybrid33-54.zip";
                        }
                        zipname = "LTE_Hybrid_0.54_+_0.33.zip";
                        command = new CommandCapture(0, "echo '--update_package=/sdcard/0/Modems/LTE_Hybrid_0.54_+_0.33.zip' > /cache/recovery/command", "reboot recovery");
                        command2 = new CommandCapture(0, "dd if=/sdcard/Modems/LTE_Hybrid_0.54_+_0.33.zip of=/cache/recovery/LTE_Hybrid_0.54_+_0.33.zip", "rm /sdcard/Modems/LTE_Hybrid_0.54_+_0.33.zip", "echo '--update_package=/cache/recovery/LTE_Hybrid_0.54_+_0.33.zip' > /cache/recovery/command", "reboot recovery"); // Flash and delete
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
                            url = "http://www.bpear.me/downloads/mako/hybrid/LTEhybrid27-54.zip";
                        } else {
                            url = "http://rebel-rom.googlecode.com/files/LTEhybrid27-54.zip";
                        }
                        zipname = "LTE_Hybrid_0.54_+_0.27.zip";
                        command = new CommandCapture(0, "echo '--update_package=/sdcard/0/Modems/LTE_Hybrid_0.54_+_0.27.zip' > /cache/recovery/command", "reboot recovery");
                        command2 = new CommandCapture(0, "dd if=/sdcard/Modems/LTE_Hybrid_0.54_+_0.27.zip of=/cache/recovery/LTE_Hybrid_0.54_+_0.27.zip", "rm /sdcard/Modems/LTE_Hybrid_0.54_+_0.27.zip", "echo '--update_package=/cache/recovery/LTE_Hybrid_0.54_+_0.27.zip > /cache/recovery/command", "reboot recovery"); // Flash and delete
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
                            url = "http://soleedus.me/bpear/W97C33O97.zip";
                        } else {
                            url = "http://goo.gl/uOQu6X";
                        }
                        zipname = "LTE_Hybrid_0.97_+_0.33.zip";
                        command = new CommandCapture(0, "echo '--update_package=/sdcard/0/Modems/LTE_Hybrid_0.97_+_0.33.zip' > /cache/recovery/command", "reboot recovery");
                        command2 = new CommandCapture(0, "dd if=/sdcard/Modems/LTE_Hybrid_0.97_+_0.33.zip of=/cache/recovery/LTE_Hybrid_0.97_+_0.33.zip", "rm /sdcard/Modems/LTE_Hybrid_0.97_+_0.33.zip", "echo '--update_package=/cache/recovery/LTE_Hybrid_0.97_+_0.33.zip' > /cache/recovery/command", "reboot recovery"); // Flash and delete
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
                            url = "http://soleedus.me/bpear/W97C27MO97.zip";
                        } else {
                            url = "http://goo.gl/APoHBH";
                        }
                        zipname = "LTE_Hybrid_0.97_+_0.27.zip";
                        command = new CommandCapture(0, "echo '--update_package=/sdcard/0/Modems/LTE_Hybrid_0.97_+_0.27.zip' > /cache/recovery/command", "reboot recovery");
                        command2 = new CommandCapture(0, "dd if=/sdcard/Modems/LTE_Hybrid_0.97_+_0.27.zip of=/cache/recovery/LTE_Hybrid_0.97_+_0.27.zip", "rm /sdcard/Modems/LTE_Hybrid_0.97_+_0.27.zip", "echo '--update_package=/cache/recovery/LTE_Hybrid_0.97_+_0.27.zip' > /cache/recovery/command", "reboot recovery"); // Flash and delete
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
                            url = "http://goo.gl/H3aFlN";
                        } else {
                            url = "http://goo.gl/Y2PuMg";
                        }
                        zipname = "LTE_Hybrid_0.02_+_0.27.zip";
                        command = new CommandCapture(0, "echo '--update_package=/sdcard/0/Modems/LTE_Hybrid_0.02_+_0.27.zip' > /cache/recovery/command", "reboot recovery");
                        command2 = new CommandCapture(0, "dd if=/sdcard/Modems/LTE_Hybrid_0.02_+_0.27.zip of=/cache/recovery/LTE_Hybrid_0.02_+_0.27.zip", "rm /sdcard/Modems/LTE_Hybrid_0.02_+_0.27.zip", "echo '--update_package=/cache/recovery/LTE_Hybrid_0.02_+_0.27.zip' > /cache/recovery/command", "reboot recovery"); // Flash and delete
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
                            url = "http://goo.gl/MSAuKq";
                        } else {
                            url = "http://goo.gl/OEq6aq";
                        }
                        zipname = "LTE_Hybrid_0.02_+_0.33.zip";
                        command = new CommandCapture(0, "echo '--update_package=/sdcard/0/Modems/LTE_Hybrid_0.02_+_0.33.zip' > /cache/recovery/command", "reboot recovery");
                        command2 = new CommandCapture(0, "dd if=/sdcard/Modems/LTE_Hybrid_0.02_+_0.33.zip of=/cache/recovery/LTE_Hybrid_0.02_+_0.33.zip", "rm /sdcard/Modems/LTE_Hybrid_0.02_+_0.33.zip", "echo '--update_package=/cache/recovery/LTE_Hybrid_0.02_+_0.33.zip' > /cache/recovery/command", "reboot recovery"); // Flash and delete
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
