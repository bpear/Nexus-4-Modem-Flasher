package me.bpear.makomodem.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import me.bpear.makomodem.BuildpropFragment;
import me.bpear.makomodem.LTEHybridFragment;
import me.bpear.makomodem.StockModemFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return new StockModemFragment(); // Stock Modem fragment activity
            case 1:
                return new LTEHybridFragment(); // LTE Hybrid fragment activity
            case 2:
                return new BuildpropFragment(); // Build.prop fragment activity
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3; // get item count - equal to number of tabs
    }

}
