package com.sov.sofysmo.emotiondiary.Utils;

/**
 * Created by SofySmo on 17.04.2016.
 */
import android.os.Handler;
import android.os.Message;

import com.ibm.watson.developer_cloud.personality_insights.v2.PersonalityInsights;
import com.ibm.watson.developer_cloud.personality_insights.v2.model.Profile;
import com.ibm.watson.developer_cloud.speech_to_text.v1.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
import com.ibm.watson.developer_cloud.speech_to_text.v1.websocket.BaseRecognizeDelegate;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


/**
 * Created by Asus on 14.04.2016.
 */

public class PersonalityConnect {
    private Thread threadtopersonality;
    private PersonalityInsights  service;
    private String username, password;
    private Handler handler;
    private String text;

    public PersonalityConnect(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    private void requestPersonalityOnWatsonBySpeech(String myProfile) {
        service = new PersonalityInsights();                                                 //
        service.setUsernameAndPassword(username, password);                           // устанавливаем необходимые данные для отправки

        RecognizeOptions options = new RecognizeOptions().contentType("audio/wav")      //выставляем необходмые параметры для получения текста
                .continuous(true).interimResults(true);

        Profile personalityProfile = service.getProfile(myProfile);

        Message message = handler.obtainMessage(0, personalityProfile);
        handler.sendMessage(message);
    }


    public void runRequestServiceSpeechToText(final String myProfile) {
        threadtopersonality = new Thread(new Runnable() {  //создаем новый поток
            @Override
            public void run(){
                requestPersonalityOnWatsonBySpeech(myProfile); //запускаем основной алгоритм

            }
        });
        threadtopersonality.start();
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
