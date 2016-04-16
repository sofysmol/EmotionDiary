package com.sov.sofysmo.emotiondiary.Model;

import com.sov.sofysmo.emotiondiary.ToneAnalyz;

import java.io.Serializable;

/**
 * Created by SofySmo on 16.04.2016.
 */
public class Page implements Serializable {
    public String title;
    public String date;
    public String text;
    public MyToneScore tone;


    public Page(String title, String date, String text, MyToneScore tone) {
        this.title = title;
        this.date = date;
        this.text = text;
        this.tone = tone;
    }
}
