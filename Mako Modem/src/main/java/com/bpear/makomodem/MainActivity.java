package com.bpear.makomodem;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.bpear.makomodem.adapter.TabsPagerAdapter;
import com.stericson.RootTools.RootTools;

public class MainActivity extends FragmentActivity implements
        ActionBar.TabListener {

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
            //case R.id.action_settings:
            //openSettings();
            //return true;
            case R.id.action_phone:
                openTesting(); // *#*#4636#*#*
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (RootTools.isAccessGiven()) {
            // Do something maybe
        } else {
            Toast.makeText(this, "You are not rooted!", Toast.LENGTH_SHORT).show();
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
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }

}
