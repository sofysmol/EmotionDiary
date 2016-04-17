package com.sov.sofysmo.emotiondiary.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.sov.sofysmo.emotiondiary.Model.MyToneScore;
import com.sov.sofysmo.emotiondiary.R;
import com.sov.sofysmo.emotiondiary.Utils.SharedPref;
import com.sov.sofysmo.emotiondiary.Utils.SoundRecorder;
import com.sov.sofysmo.emotiondiary.Utils.SpeechConnect;
import com.sov.sofysmo.emotiondiary.Utils.ToneAnalyz;

import java.text.SimpleDateFormat;

public class NewCardActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private MenuItem save_button;
    private TextInputEditText text;
    private TextInputEditText title;
    private TextView dateText;
    private SpeechConnect speech;
    private SoundRecorder recorder;
    private ToneAnalyz tone;
    private Handler handlerSpeach;
    private Handler handlerTone;
    private SharedPref pref;
    private boolean isRequest = false;
    private boolean isTone = false;
    private final String PREF = "pref";
    private NewCardActivity nca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);
        nca = this;

        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.yyyy HH:mm");

        dateText = (TextView)findViewById(R.id.date_create);
        dateText.setText(sdf.format(date));

        SharedPreferences mySettings = getSharedPreferences(PREF, Activity.MODE_PRIVATE);
        pref = new SharedPref(mySettings);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {       //Only Android Marshmallow version and more
            ActivityCompat.requestPermissions(this,                 //Starting with Android 6.0 and above need to get access rights directly in the code
                    new String[]{Manifest.permission.INTERNET, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, //This is an example of how to do it
                    0);
        } else recorder = new SoundRecorder(); //If the version is higher, and the permissions requested by the manifest, it is possible to immediately initiate Recorder

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_white_24dp);

        text = (TextInputEditText) findViewById(R.id.story_new_card);
        title = (TextInputEditText) findViewById(R.id.title_new_card);
        fab = (FloatingActionButton) findViewById(R.id.fab_new);
        save_button = (MenuItem) findViewById(R.id.action_save);

        /*fab_add.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {

                                       }
                                   }
        );*/

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isTone)
                {
                    if (!recorder.isRecording()) {      //unless there is a sound recording
                        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorBad)));
                        //fab.setBackgroundDrawable(R.drawable.);
                        recorder.startRecording();      //start Voice recording
                    } else {      //otherwise
                        String filepath = recorder.stopRecording(); //Finish the sound recording and get the path to the wav file
                        Animation anim = AnimationUtils.loadAnimation(
                                getApplicationContext(), R.anim.rotate);

                        fab.startAnimation(anim);
                        speech = new SpeechConnect("1da283bd-f7f5-4255-85d1-10ba658211a3", "2G9iSmV3FbTA");

                        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGood)));
                        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_backup_restore_white_48dp));
                        handlerSpeach = new Handler() {  // посрденик между потоками(он нам понадобиться для того, чтобы передать данные из другого потока в поток UI)
                            public void handleMessage(android.os.Message msg) {
                                text.append(" " + msg.obj.toString()); // !!!!--- тут наше текстовое сообщение(вот отсюда бери Сонь и ставь куда хочешь)!!!!
                                fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                                fab.clearAnimation();
                                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_microphone_white_48dp));
                                fab.setEnabled(true);
                            }
                        };

                        speech.setHandler(handlerSpeach);
                        speech.runRequestServicePersonality(filepath);

                        fab.setEnabled(false);
                    }
                }
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_card, menu);
        return true;
    }
    public void ololo()
    {
        NavUtils.navigateUpFromSameTask(this);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (!isTone)
        {
            switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
                case android.R.id.home:
                    NavUtils.navigateUpFromSameTask(this);
                    return true;
                case R.id.action_save: {

                    tone = new ToneAnalyz("ef85198d-8fc9-462c-b52f-4a7df2db0367", "45fh2BppcWwl", text.getText().toString());
                    handlerTone = new Handler() {  // посрденик между потоками(он нам понадобиться для того, чтобы передать данные из другого потока в поток UI)
                        public void handleMessage(android.os.Message msg) {

                            pref.addCard(title.getText().toString(), dateText.getText().toString(), text.getText().toString(), (MyToneScore) msg.obj);
                            isTone = false;
                            text.setEnabled(true);
                            title.setEnabled(true);

                            fab.clearAnimation();
                            fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_microphone_white_48dp));

                            ololo();
                        }
                    };


                    Animation anim = AnimationUtils.loadAnimation(
                            getApplicationContext(), R.anim.rotate);
                    fab.startAnimation(anim);
                    fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGood)));
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_backup_restore_white_48dp));

                    tone.setHandler(handlerTone);
                    tone.startRequest();
                    isTone = true;
                    text.setEnabled(false);
                    title.setEnabled(false);
                    return true;
                }
            }
        }

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    public void onRequestPermissionsResult(int requestCode,                             //Only Android Marshmallow version and more
                                           String permissions[], int[] grantResults) {  //When you programmatically send a request for access rights,
        switch (requestCode) {                                                          //the request is sent to the system of asynchronous method. After the query is called onRequestPermissionsResult function
            case 0: {                                       //After the query is called onRequestPermissionsResult function
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0                                             //Check whether we have access
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    recorder = new SoundRecorder();                                     //If all is well, then initialize the record (Yes, that's because it's complicated to Android 6.0  =) )
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
