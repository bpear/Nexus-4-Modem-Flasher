package com.bpear.makomodem;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class EasyActivity extends Activity {

    TextView versionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy);
        versionView=new TextView(this);
        versionView=(TextView)findViewById(R.id.versionView);
        versionView.setText("Android version detected: " + Build.VERSION.RELEASE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.easy, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_advanced:
                Intent advanced = new Intent(EasyActivity.this,AdvancedActivity.class);
                startActivity(advanced);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
