package ru.startandroid.diary;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.ibm.watson.developer_cloud.speech_to_text.v1.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechModel;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
import com.ibm.watson.developer_cloud.speech_to_text.v1.websocket.BaseRecognizeDelegate;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;


/**
 * Created by Asus on 14.04.2016.
 */

public class SpeechConnect {
    private Thread threadtospeech;
    private SpeechToText service;
    private String username, password;
    private Handler handler;
    private String text;


    public SpeechConnect(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public void requestTextOnWatsonBySpeech(String filepath) throws JSONException {
        service = new SpeechToText();
        service.setUsernameAndPassword(username, password);
        service.setEndPoint("https://stream.watsonplatform.net/speech-to-text/api");

        RecognizeOptions options = new RecognizeOptions().contentType("audio/wav")
                .continuous(true).interimResults(true);

        //String filepath = Environment.getExternalStorageDirectory().getPath() + "/Download/1460663190593.wav";

        try {
            service.recognizeUsingWebSockets(new FileInputStream(filepath),
                    options, new BaseRecognizeDelegate()
                    {
                        @Override
                        public void onMessage(SpeechResults speechResults) {
                            text = speechResults.toString();

                        }

                        @Override
                        public void onDisconnected()
                        {
                            try {
                                JSONObject resultSpeechToText = new JSONObject(text);
                                resultSpeechToText = resultSpeechToText.getJSONArray("results").getJSONObject(0);
                                resultSpeechToText = resultSpeechToText.getJSONArray("alternatives").getJSONObject(0);
                                text = resultSpeechToText.getString("transcript");
                                Message message = handler.obtainMessage(0, text);
                                handler.sendMessage(message);
                            } catch (org.json.JSONException e){
                                e.printStackTrace();
                            }
                        }
                    }
            );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//27649
    }


    public void runRequestServiceSpeechToText(final String filepath) {
        threadtospeech = new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    requestTextOnWatsonBySpeech(filepath);

                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        });
        threadtospeech.start();
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
