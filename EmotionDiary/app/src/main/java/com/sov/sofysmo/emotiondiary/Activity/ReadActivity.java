package com.sov.sofysmo.emotiondiary.Activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.sov.sofysmo.emotiondiary.R;

public class ReadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        Intent intent = getIntent();

        ((TextView)findViewById(R.id.title_read)).setText(intent.getStringExtra("Title"));
        ((TextView)findViewById(R.id.date_read)).setText(intent.getStringExtra("Date"));
        ((TextView)findViewById(R.id.story_read)).setText(intent.getStringExtra("Text"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            switch (intent.getStringExtra("Tone")) {
                case "Angry":
                    ((Toolbar) findViewById(R.id.toolbarR)).setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                    this.getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
                    break;
                case "Joy":
                    ((Toolbar) findViewById(R.id.toolbarR)).setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorHappy)));
                    this.getWindow().setStatusBarColor(getResources().getColor(R.color.colorHappy));
                    break;
                case "Disgust":
                    ((Toolbar) findViewById(R.id.toolbarR)).setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.disgust)));
                    this.getWindow().setStatusBarColor(getResources().getColor(R.color.disgust));
                    break;
                case "Fear":
                    ((Toolbar) findViewById(R.id.toolbarR)).setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.fear)));
                    this.getWindow().setStatusBarColor(getResources().getColor(R.color.fear));
                    break;
                case "Sadness":
                    ((Toolbar) findViewById(R.id.toolbarR)).setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.sadness)));
                    this.getWindow().setStatusBarColor(getResources().getColor(R.color.sadness));
                    break;
            }
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarR);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_white_24dp);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_save:
            {

                //Женя твой код
            }
        }

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

}
