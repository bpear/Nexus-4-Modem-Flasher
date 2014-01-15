package com.bpear.makomodem;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

// Thanks to Stericson for RootTools project! https://code.google.com/p/roottools/
import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.exceptions.RootDeniedException;
import com.stericson.RootTools.execution.CommandCapture;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class BuildpropFragment extends Fragment implements View.OnClickListener {
    int type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // inflate ...
        View view = inflater.inflate(R.layout.fragment_buildprop,  container, false);
        assert view != null;
        Button b = (Button) view.findViewById(R.id.EnableLTE);
        b.setOnClickListener(this);
        return view;
    }

    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button cb1 = (CheckBox) getActivity().findViewById(R.id.checkBoxLTE);
        cb1.setOnClickListener(next_Listener);


        Button cb2 = (CheckBox) getActivity().findViewById(R.id.checkBoxReboot);
        cb2.setOnClickListener(next_Listener);


        Button cb3 = (CheckBox) getActivity().findViewById(R.id.checkBoxNOLTE);
        cb3.setOnClickListener(next_Listener);

    }

    private View.OnClickListener next_Listener = new View.OnClickListener() {
        public void onClick(View v) {

            //xml find out which radio button has been checked ...
            CheckBox cb1=(CheckBox)getActivity().findViewById(R.id.checkBoxLTE);
            CheckBox cb3=(CheckBox)getActivity().findViewById(R.id.checkBoxNOLTE);
            CheckBox cb2=(CheckBox)getActivity().findViewById(R.id.checkBoxReboot);

            if(cb1.isChecked() && !cb2.isChecked() && !cb3.isChecked()) {
                type = 1;
            }
            if(!cb1.isChecked() && cb2.isChecked() && !cb3.isChecked()) {
                type = 2;
            }
            if(!cb1.isChecked() && !cb2.isChecked() && cb3.isChecked()) {
                type = 3;
            }
            if(!cb1.isChecked() && cb2.isChecked() && cb3.isChecked()) {
                type = 4;
            }
            if(cb1.isChecked() && cb2.isChecked() && cb3.isChecked()) {
                type = 5;
            }
            if(cb1.isChecked() && !cb2.isChecked() && cb3.isChecked()) {
                type = 6;
            }
            if(cb1.isChecked() && cb2.isChecked() && !cb3.isChecked()) {
                type = 7;
            }
        }
    };


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.EnableLTE:
                switch (type) {
                    case 1:
                        Toast.makeText(getActivity(), "Takes affect on reboot", Toast.LENGTH_SHORT).show();
                        CommandCapture command1 = new CommandCapture(0, "grep -Ev 'telephony.lteOnGsmDevice|ro.telephony.default_network|ro.ril.def.preferred.network' /system/build.prop > /sdcard/build.prop", "echo 'telephony.lteOnGsmDevice=1' >> /sdcard/build.prop", "echo 'ro.telephony.default_network=9' >> /sdcard/build.prop", "echo 'ro.ril.def.preferred.network=9' >> /sdcard/build.prop", "mount -ro remount,rw /system", "rm /system/build.prop", "dd if=/sdcard/build.prop of=/system/build.prop", "chmod 644 /system/build.prop", "rm -f /sdcard/build.prop", "mount -ro remount,ro /system");
                        try {
                            RootTools.getShell(true).add(command1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (TimeoutException e) {
                            e.printStackTrace();
                        } catch (RootDeniedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        Toast.makeText(getActivity(), "I think there are easier ways to reboot your phone", Toast.LENGTH_SHORT).show();
                        CommandCapture command2 = new CommandCapture(0, "reboot");
                        try {
                            RootTools.getShell(true).add(command2);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (TimeoutException e) {
                            e.printStackTrace();
                        } catch (RootDeniedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3:
                        Toast.makeText(getActivity(), "Removing LTE build.prop lines!", Toast.LENGTH_SHORT).show();
                        CommandCapture command3 = new CommandCapture(0, "grep -Ev 'telephony.lteOnGsmDevice|ro.telephony.default_network|ro.ril.def.preferred.network' /system/build.prop > /sdcard/build.prop", "mount -ro remount,rw /system", "rm /system/build.prop", "dd if=/sdcard/build.prop of=/system/build.prop", "chmod 644 /system/build.prop", "rm -f /sdcard/build.prop", "mount -ro remount,ro /system");
                        try {
                            RootTools.getShell(true).add(command3);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (TimeoutException e) {
                            e.printStackTrace();
                        } catch (RootDeniedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 4:
                        Toast.makeText(getActivity(), "Removing LTE and rebooting!", Toast.LENGTH_SHORT).show();
                        CommandCapture command4 = new CommandCapture(0, "grep -Ev 'telephony.lteOnGsmDevice|ro.telephony.default_network|ro.ril.def.preferred.network' /system/build.prop > /sdcard/build.prop", "mount -ro remount,rw /system", "rm /system/build.prop", "dd if=/sdcard/build.prop of=/system/build.prop", "chmod 644 /system/build.prop", "rm -f /sdcard/build.prop", "mount -ro remount,ro /system", "reboot");
                        try {
                            RootTools.getShell(true).add(command4);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (TimeoutException e) {
                            e.printStackTrace();
                        } catch (RootDeniedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 5:
                        Toast.makeText(getActivity(), "All 3! Take 2 you greedy person!", Toast.LENGTH_SHORT).show();
                        break;
                    case 6:
                        Toast.makeText(getActivity(), "The contradiction is strong with this one", Toast.LENGTH_SHORT).show();
                        break;
                    case 7:
                        Toast.makeText(getActivity(), "Enabling LTE and rebooting!", Toast.LENGTH_SHORT).show();
                        CommandCapture command7 = new CommandCapture(0, "grep -Ev 'telephony.lteOnGsmDevice|ro.telephony.default_network|ro.ril.def.preferred.network' /system/build.prop > /sdcard/build.prop", "echo 'telephony.lteOnGsmDevice=1' >> /sdcard/build.prop", "echo 'ro.telephony.default_network=9' >> /sdcard/build.prop", "echo 'ro.ril.def.preferred.network=9' >> /sdcard/build.prop", "mount -ro remount,rw /system", "rm /system/build.prop", "dd if=/sdcard/build.prop of=/system/build.prop", "chmod 644 /system/build.prop", "rm -f /sdcard/build.prop", "mount -ro remount,ro /system", "reboot");
                        try {
                            RootTools.getShell(true).add(command7);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (TimeoutException e) {
                            e.printStackTrace();
                        } catch (RootDeniedException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;
                }
                break;
        }
    }
}
