package com.example.pavellevshin.testsoundrecorder;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ibm.watson.developer_cloud.personality_insights.v2.model.Profile;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;


public class MainActivity extends AppCompatActivity {

    private Handler handlerSpeach;
    private Handler handlerPersonality;
    private Button btn;
    private TextView textSpeechToText;
    private MainActivity Content;
    private SpeechConnect speech;
    private PersonalityConnect personality;

    private SoundRecorder recorder;
    private final int  MY_PERMISSIONS_REQUEST_RECORD = 1;


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Content = this;
        btn = (Button) findViewById(R.id.btn);
        textSpeechToText = (TextView)findViewById(R.id.textFilePath);

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {       //Only Android Marshmallow version and more
            ActivityCompat.requestPermissions(this,                 //Starting with Android 6.0 and above need to get access rights directly in the code
                    new String[]{Manifest.permission.INTERNET, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, //This is an example of how to do it
                    MY_PERMISSIONS_REQUEST_RECORD);
        } else recorder = new SoundRecorder(); //If the version is higher, and the permissions requested by the manifest, it is possible to immediately initiate Recorder


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,                             //Only Android Marshmallow version and more
                                           String permissions[], int[] grantResults) {  //When you programmatically send a request for access rights,
        switch (requestCode) {                                                          //the request is sent to the system of asynchronous method. After the query is called onRequestPermissionsResult function
            case MY_PERMISSIONS_REQUEST_RECORD: {                                       //After the query is called onRequestPermissionsResult function
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0                                             //Check whether we have access
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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

    public void onClick(View v) {                       //By pressing button
        switch(v.getId()){
            case R.id.btn:{
                if (!recorder.isRecording()) {      //unless there is a sound recording
                    btn.setText(getString(R.string.stop_read));
                    recorder.startRecording();      //start Voice recording
                }
                else {      //otherwise
                    String filepath = recorder.stopRecording(); //Finish the sound recording and get the path to the wav file

                    speech = new SpeechConnect("a98bc899-46e3-49db-8486-83cb49063c65", "flcYOaGGP73M");

                    handlerSpeach = new Handler() {  // посрденик между потоками(он нам понадобиться для того, чтобы передать данные из другого потока в поток UI)
                        public void handleMessage(android.os.Message msg) {
                            textSpeechToText.setText(msg.obj.toString()); // !!!!--- тут наше текстовое сообщение(вот отсюда бери Сонь и ставь куда хочешь)!!!!
                            btn.setText(getString(R.string.start_read));
                            btn.setEnabled(true);

                            personality = new PersonalityConnect("47c494e1-40b4-4f7b-9dfc-aa6d744a7480", "KMCFyQeEK4BN");
                            personality.setHandler(handlerPersonality);
                            String myProfile = "Call me Ishmael. Some years ago-never mind how long precisely-having little or no money in my purse, and nothing particular to interest me on shore, I thought I would sail about a little and see the watery part of the world. It is a way I have of driving off the spleen and regulating the circulation. Whenever I find myself growing grim about the mouth; whenever it is a damp, drizzly November in my soul; whenever I find myself involuntarily pausing before coffin warehouses, and bringing up the rear of every funeral I meet; and especially whenever my hypos get such an upper hand of me, that it requires a strong moral principle to prevent me from deliberately stepping into the street, and methodically knocking people's hats off-then, I account it high time to get to sea as soon as I can.";
                            personality.runRequestServiceSpeechToText(myProfile);
                            btn.setText(getString(R.string.wait_analyzer));

                        }
                    };

                    handlerPersonality = new Handler() {  // посрденик между потоками(он нам понадобиться для того, чтобы передать данные из другого потока в поток UI)
                        public void handleMessage(android.os.Message msg) {
                            Profile profile = (Profile)msg.obj; //Получаем profile от Watson
                        }
                    };

                    speech.setHandler(handlerSpeach);
                    speech.runRequestServicePersonality(filepath);

                    btn.setEnabled(false);
                }
                break;
            }
        }
    }
}
