package com.sov.sofysmo.emotiondiary.Utils;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

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

    private void requestTextOnWatsonBySpeech(String filepath) throws JSONException {
        service = new SpeechToText();                                                 //
        service.setUsernameAndPassword(username, password);                           // устанавливаем необходимые данные для отправки
        service.setEndPoint("https://stream.watsonplatform.net/speech-to-text/api");  //

        RecognizeOptions options = new RecognizeOptions().contentType("audio/wav")      //выставляем необходмые параметры для получения текста
                .continuous(true).interimResults(true);


        try {

            service.recognizeUsingWebSockets(new FileInputStream(filepath),  // передаем нашу аудиозапись
                    options, new BaseRecognizeDelegate()
                    {
                        @Override
                        public void onMessage(SpeechResults speechResults) {   // при получении сообщения от Watson пишем в переменную text,
                            text = speechResults.toString();                   // но так как нам нужна конечное сообщение, то не добавляем новые данные(т.е. не text += ..)
                                                                             // onMessage - стандартная функция
                        }

                        @Override
                        public void onDisconnected()        // при разрыве соединения
                        {
                            try {
                                if (text != null) {
                                    JSONObject resultSpeechToText = new JSONObject(text);   // создаем JSON объект
                                    resultSpeechToText = resultSpeechToText.getJSONArray("results").getJSONObject(0);   // парсим наше текс
                                    resultSpeechToText = resultSpeechToText.getJSONArray("alternatives").getJSONObject(0);  // P.S. да, в одну строчку не получается делать =(, ругается.
                                    text = resultSpeechToText.getString("transcript"); // готовое сообщение
                                    Message message = handler.obtainMessage(0, text);   // передаем сообещние в поток UI
                                    handler.sendMessage(message);                       //
                                }
                            } catch (org.json.JSONException e){
                                e.printStackTrace();
                            }
                        }
                    }
            );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void runRequestServicePersonality(final String filepath) {
        threadtospeech = new Thread(new Runnable() {  //создаем новый поток
            @Override
            public void run() {
                try
                {
                    requestTextOnWatsonBySpeech(filepath); //запускаем основной алгоритм

                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        });
        threadtospeech.setName("lolol");
        threadtospeech.start();
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
