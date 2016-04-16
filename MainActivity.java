package com.example.valeria.toneanalyz;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;




public class MainActivity extends AppCompatActivity {

    private Handler handler;
    Button btnPING;
    MyToneScore RetScore;//будет возвращать значение указанного поля класса
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPING = (Button) findViewById(R.id.btnPING);

        View.OnClickListener oclBtnPING = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToneAnalyz speech = new ToneAnalyz("6fa0e7fc-eaeb-4b5f-8203-1219281e75a3", "1vLsmQ5g0Tnm", "Content-Type: application/json");
                handler = new Handler() {  // посрденик между потоками(он нам понадобиться для того, чтобы передать данные из другого потока в поток UI)
                    public void handleMessage(android.os.Message msg) {
                        RetScore = (MyToneScore) msg.obj;
                        btnPING.setText(RetScore.GetScoreA().toString());//Жень,как у тебя возвращаю значение поля моего класс
                        //Использую метод GetScore_, где_ первая буква нужного тона
                    }
                };
            speech.setHandler(handler);

            }
        };

        btnPING.setOnClickListener(oclBtnPING);
    }
}