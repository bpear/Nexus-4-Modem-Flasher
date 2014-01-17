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
import android.widget.RadioButton;
import android.widget.Toast;

import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.exceptions.RootDeniedException;
import com.stericson.RootTools.execution.CommandCapture;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

// Thanks to Stericson for RootTools project! https://code.google.com/p/roottools/



public class LTEHybridFragment extends Fragment implements View.OnClickListener {

    int type;
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
    }

    private View.OnClickListener next_Listener = new View.OnClickListener() {
        public void onClick(View v) {

            //xml find out which radio button has been checked ...
            RadioButton rb1 = (RadioButton) getActivity().findViewById(R.id.radio_h33_98);
            RadioButton rb2 = (RadioButton) getActivity().findViewById(R.id.radio_h27_98);
            RadioButton rb3 = (RadioButton) getActivity().findViewById(R.id.radio_h33_84);
            RadioButton rb4 = (RadioButton) getActivity().findViewById(R.id.radio_h33_54);
            RadioButton rb5 = (RadioButton) getActivity().findViewById(R.id.radio_h27_54);
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
        }
    };

    public void modemDownload() { // Download modem function
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        request.setDestinationInExternalPublicDir("/Modems", zipname);

        DownloadManager manager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE); // get download service and enqueue file
        manager.enqueue(request);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hFlash_Button:
                Toast.makeText(getActivity(), "Phone will reboot when modem is downloaded!", Toast.LENGTH_SHORT).show();
                switch (type) { // Do following cases depending on which button is checked
                    case 1:
                        url = "https://rebel-rom.googlecode.com/files/98-33.zip";
                        zipname = "LTE Hybrid 0.98 + 0.33.zip";
                        modemDownload(); // Start download
                        BroadcastReceiver onComplete = new BroadcastReceiver() { //Check if download is done
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                CommandCapture command = new CommandCapture(0, "echo '--update_package=/sdcard/0/Modems/Stock 0.98.zip' > /cache/recovery/command", "reboot recovery"); // add recovery install script commands and reboot
                                try {
                                    RootTools.getShell(true).add(command);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (TimeoutException e) {
                                    e.printStackTrace();
                                } catch (RootDeniedException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        getActivity().registerReceiver(onComplete, new IntentFilter(
                                DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                        break;

                    case 2:
                        url = "http://rebel-rom.googlecode.com/files/98-27.zip";
                        zipname = "LTE Hybrid 0.98 + 0.27.zip";
                        modemDownload();
                        onComplete = new BroadcastReceiver() { //Check if download is done
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                CommandCapture command = new CommandCapture(0, "echo '--update_package=/sdcard/0/Modems/LTE Hybrid 0.98 + 0.27.zip' > /cache/recovery/command", "reboot recovery");
                                try {
                                    RootTools.getShell(true).add(command);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (TimeoutException e) {
                                    e.printStackTrace();
                                } catch (RootDeniedException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        getActivity().registerReceiver(onComplete, new IntentFilter(
                                DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                        break;

                    case 3:
                        url = "https://rebel-rom.googlecode.com/files/LTEhybrid33-84.zip";
                        zipname = "LTE Hybrid 0.84 + 0.33.zip";
                        modemDownload();
                        onComplete = new BroadcastReceiver() { //Check if download is done
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                CommandCapture command = new CommandCapture(0, "echo '--update_package=/sdcard/0/Modems/LTE Hybrid 0.84 + 0.33.zip' > /cache/recovery/command", "reboot recovery");
                                try {
                                    RootTools.getShell(true).add(command);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (TimeoutException e) {
                                    e.printStackTrace();
                                } catch (RootDeniedException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        getActivity().registerReceiver(onComplete, new IntentFilter(
                                DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                        break;

                    case 4:
                        url = "http://rebel-rom.googlecode.com/files/LTEhybrid33-54.zip";
                        zipname = "LTE Hybrid 0.54 + 0.33.zip";
                        modemDownload();
                        onComplete = new BroadcastReceiver() { //Check if download is done
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                CommandCapture command = new CommandCapture(0, "echo '--update_package=/sdcard/0/Modems/LTE Hybrid 0.54 + 0.33.zip' > /cache/recovery/command", "reboot recovery");
                                try {
                                    RootTools.getShell(true).add(command);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (TimeoutException e) {
                                    e.printStackTrace();
                                } catch (RootDeniedException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        getActivity().registerReceiver(onComplete, new IntentFilter(
                                DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                        break;

                    case 5:
                        url = "http://rebel-rom.googlecode.com/files/LTEhybrid27-54.zip";
                        zipname = "LTE Hybrid 0.54 + 0.27.zip";
                        modemDownload();
                        onComplete = new BroadcastReceiver() { //Check if download is done
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                CommandCapture command = new CommandCapture(0, "echo '--update_package=/sdcard/0/Modems/LTE Hybrid 0.54 + 0.27.zip' > /cache/recovery/command", "reboot recovery");
                                try {
                                    RootTools.getShell(true).add(command);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (TimeoutException e) {
                                    e.printStackTrace();
                                } catch (RootDeniedException e) {
                                    e.printStackTrace();
                                }
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
