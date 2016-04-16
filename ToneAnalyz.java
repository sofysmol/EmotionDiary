package com.example.valeria.toneanalyz;

import android.os.Debug;
import android.os.Environment;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneScore;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

import android.os.Environment;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;


/**
 * Created by VALERIA on 15.04.2016.
 */
public class ToneAnalyz {
    private Thread threadtotext;
    private ToneAnalyzer service; //Отвечает за общение с Watson
    private String username, password;
    private String SendText;//Анализируемый текст
    private String AnswerJS;//Полученный ответ от Watson
    private Handler handler;
    Double ScoreTone; //Для преобразования
    MyToneScore add_score; //Объект написанного класса, для хранения полученного числового результата  ответа от Watson


    public ToneAnalyz(String username, String password, String SendText) {
        this.username = username;
        this.password = password;
        this.SendText = SendText;

        threadtotext = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    AddTextForAnalyz();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        threadtotext.start();
    }

    public void AddTextForAnalyz() throws JSONException,InterruptedException {
       //Отправка запроса,использует стандартные классы Watson
        service = new ToneAnalyzer(ToneAnalyzer.VERSION_DATE_2016_02_11);
        service.setUsernameAndPassword(username, password);

        ToneAnalysis tone = service.getTone(SendText);//Отправляем запрос к Watson
        AnswerJS = tone.toString();//Получаем ответ
        String answ = tone.getDocumentTone().getTones().get(0).toString();//Взяли необходимый массив с данными

        add_score = new MyToneScore();//Создаем объект,для дальнейшего добавления в него числовых значений
        //Начинаем парсить и сохранять в экземпляр класса MyToneScope
        JsonParser parser = new JsonParser();
        JsonObject mainObject = parser.parse(answ).getAsJsonObject();
        JsonArray pItem = mainObject.getAsJsonArray("tones");
        for (JsonElement user : pItem) {

            JsonObject userObject = user.getAsJsonObject();
            if (userObject.get("tone_id").getAsString().equals("anger")) {
                String str = userObject.get("score").toString();
                ScoreTone = Double.valueOf(str);
                add_score.SetScoreA(ScoreTone);
                continue;
            }

            if (userObject.get("tone_id").getAsString().equals("disgust")) {
                String str = userObject.get("score").toString();
                ScoreTone = Double.valueOf(str);
                add_score.SetScoreD(ScoreTone);
                continue;
            }
            if (userObject.get("tone_id").getAsString().equals("fear")) {
                String str = userObject.get("score").toString();
                ScoreTone = Double.valueOf(str);
                add_score.SetScoreF(ScoreTone);
                continue;
            }
            if (userObject.get("tone_id").getAsString().equals("joy")) {
                String str = userObject.get("score").toString();
                ScoreTone = Double.valueOf(str);
                add_score.SetScoreJ(ScoreTone);
                continue;
            }
            if (userObject.get("tone_id").getAsString().equals("sadness")) {
                String str = userObject.get("score").toString();
                ScoreTone = Double.valueOf(str);
                add_score.SetScoreS(ScoreTone);
                continue;
            }
        }
        Message message = handler.obtainMessage(0,add_score );   // передаем сообещние в поток U
        handler.sendMessage(message);
    }
    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}






