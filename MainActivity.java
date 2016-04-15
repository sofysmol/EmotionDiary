package ru.startandroid.diary;

import android.graphics.Color;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechModel;

import java.util.List;
import java.util.jar.Manifest;


public class MainActivity extends AppCompatActivity {

    private Handler handler;
    Button btnPING;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.INTERNET, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},1); //необходимо вставить если будет версия андройд 6.0

        btnPING = (Button) findViewById(R.id.btnPING); //создаем конпку

        View.OnClickListener oclBtnPING = new View.OnClickListener() {  //создаем обработчик для кнопки
            @Override
            public void onClick(View v) {
                SpeechConnect speech = new SpeechConnect("a98bc899-46e3-49db-8486-83cb49063c65", "flcYOaGGP73M"); // создаем объект класса SpeechConnect с параметрами "username, password"

                handler = new Handler() {  // посрденик между потоками(он нам понадобиться для того, чтобы передать данные из другого потока в поток UI)
                    public void handleMessage(android.os.Message msg) {
                          btnPING.setText(msg.obj.toString()); // !!!!--- тут наше текстовое сообщение(вот отсюда бери Сонь и ставь куда хочешь)!!!!
                    }
                };

                speech.setHandler(handler);

                String filepath = Environment.getExternalStorageDirectory().getPath() + "/Download/1460663190593.wav"; //сдесь мы получаем путь до аудиозаписи

                String audio = "";

                speech.runRequestServiceSpeechToText(filepath); //запускаем запрос
            }
        };
        btnPING.setOnClickListener(oclBtnPING);
    }
}
