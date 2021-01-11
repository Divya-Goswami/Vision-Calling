package com.agora.cv.ui;

import android.content.Intent;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;

import com.agora.cv.R;
import com.agora.cv.model.ConstantApp;

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            ab.setCustomView(R.layout.ard_agora_actionbar);
        }
    }

    @Override
    protected void initUIandEvent() {
        EditText v_channel = (EditText) findViewById(R.id.channel_name);

    }

    @Override
    protected void deInitUIandEvent() {
    }

//
//
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items

                return super.onOptionsItemSelected(item);

    }

    public void onClickJoin(View view) {
        forwardToRoom();
    }

    public void forwardToRoom() {

    }



//    @Override
//    public void permissionGranted() {
//
//    }


}
