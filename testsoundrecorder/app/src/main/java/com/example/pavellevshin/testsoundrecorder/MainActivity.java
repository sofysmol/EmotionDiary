package com.example.pavellevshin.testsoundrecorder;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pavellevshin.testsoundrecorder.SoundRecorder;


public class MainActivity extends AppCompatActivity {

    private SoundRecorder recorder;
    private final int  MY_PERMISSIONS_REQUEST_RECORD = 1;


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {       //Only Android Marshmallow version and more
            ActivityCompat.requestPermissions(this,                 //Starting with Android 6.0 and above need to get access rights directly in the code
                    new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, //This is an example of how to do it
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
                    ((Button) findViewById(R.id.btn)).setText(getString(R.string.stop_read));
                    recorder.startRecording();      //start Voice recording
                }
                else {      //otherwise
                    String filepath = recorder.stopRecording(); //Finish the sound recording and get the path to the wav file
                    ((Button)findViewById(R.id.btn)).setText(getString(R.string.start_read));
                    ((TextView)findViewById(R.id.textFilePath)).setText(filepath);
                }
                break;
            }
        }
    }
}
