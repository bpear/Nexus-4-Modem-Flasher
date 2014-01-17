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



public class StockModemFragment extends Fragment implements View.OnClickListener {
    int type;
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
            case R.id.sFlash_Button:
                Toast.makeText(getActivity(), "Phone will reboot when modem is downloaded!", Toast.LENGTH_SHORT).show(); // Prompt toast message
                switch (type) { // Do following cases depending on which button is checked
                    case 1:
                        url = "http://rebel-rom.googlecode.com/files/cwm-radio-mako-m9615a-cefwmazm-2.0.1700.98.zip"; // Set URL to download
                        zipname = "Stock 0.98.zip"; // Set download name
                        modemDownload(); // Start download
                        BroadcastReceiver onComplete = new BroadcastReceiver() { //Check if download is done
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                CommandCapture command = new CommandCapture(0, "echo '--update_package=/sdcard/0/Modems/Stock 0.98.zip' > /cache/recovery/command", "reboot recovery"); // add recovery install script commands and reboot
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

                        getActivity().registerReceiver(onComplete, new IntentFilter(
                                DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                        break;

                    case 2:
                        url = "http://rebel-rom.googlecode.com/files/cwm-radio-mako-m9615a-cefwmazm-2.0.1700.97.zip";
                        zipname = "Stock 0.97.zip";
                        modemDownload();
                        onComplete = new BroadcastReceiver() { //Check if download is done
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                CommandCapture command = new CommandCapture(0, "echo '--update_package=/sdcard/0/Modems/Stock 0.97.zip' > /cache/recovery/command", "reboot recovery");
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
                        url = "http://rebel-rom.googlecode.com/files/cwm-radio-mako-m9615a-cefwmazm-2.0.1700.84.zip";
                        zipname = "Stock 0.84.zip";
                        modemDownload();
                        onComplete = new BroadcastReceiver() { //Check if download is done
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                CommandCapture command = new CommandCapture(0, "echo '--update_package=/sdcard/0/Modems/Stock 0.84.zip' > /cache/recovery/command", "reboot recovery");
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
                        url = "http://rebel-rom.googlecode.com/files/cwm-radio-mako-m9615a-cefwmazm-2.0.1700.83.zip";
                        zipname = "Stock 0.83.zip";
                        modemDownload();
                        onComplete = new BroadcastReceiver() { //Check if download is done
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                CommandCapture command = new CommandCapture(0, "echo '--update_package=/sdcard/0/Modems/Stock 0.83.zip' > /cache/recovery/command", "reboot recovery");
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
                        url = "http://rebel-rom.googlecode.com/files/cwm-radio-mako-m9615a-cefwmazm-2.0.1700.54.zip";
                        zipname = "Stock 0.54.zip";
                        modemDownload();
                        onComplete = new BroadcastReceiver() { //Check if download is done
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                CommandCapture command = new CommandCapture(0, "echo '--update_package=/sdcard/0/Modems/Stock 0.54.zip' > /cache/recovery/command", "reboot recovery");
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

                    case 6:
                        url = "http://rebel-rom.googlecode.com/files/cwm-radio-mako-m9615a-cefwmazm-2.0.1700.48.zip";
                        zipname = "Stock 0.48.zip";
                        modemDownload();
                        onComplete = new BroadcastReceiver() { //Check if download is done
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                CommandCapture command = new CommandCapture(0, "echo '--update_package=/sdcard/0/Modems/Stock 0.48.zip' > /cache/recovery/command", "reboot recovery");
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

                    case 7:
                        url = "http://rebel-rom.googlecode.com/files/cwm-radio-mako-m9615a-cefwmazm-2.0.1700.33.zip";
                        zipname = "Stock 0.33.zip";
                        modemDownload();
                        onComplete = new BroadcastReceiver() { //Check if download is done
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                CommandCapture command = new CommandCapture(0, "echo '--update_package=/sdcard/0/Modems/Stock 0.33.zip' > /cache/recovery/command", "reboot recovery");
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

                    case 8:
                        url = "http://rebel-rom.googlecode.com/files/cwm-radio-mako-m9615a-cefwmazm-2.0.1700.27.zip";
                        zipname = "Stock 0.27.zip";
                        modemDownload();
                        onComplete = new BroadcastReceiver() { //Check if download is done
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                CommandCapture command = new CommandCapture(0, "echo '--update_package=/sdcard/0/Modems/Stock 0.27.zip' > /cache/recovery/command", "reboot recovery");
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

                    case 9:
                        url = "http://rebel-rom.googlecode.com/files/cwm-radio-mako-m9615a-cefwmazm-2.0.1700.24.zip";
                        zipname = "Stock 0.24.zip";
                        modemDownload();
                        onComplete = new BroadcastReceiver() { //Check if download is done
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                CommandCapture command = new CommandCapture(0, "echo '--update_package=/sdcard/0/Modems/Stock 0.24.zip' > /cache/recovery/command", "reboot recovery");
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
