package com.example.valeria.toneanalyz;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by VALERIA on 16.04.2016.
 */

 class MyToneScore implements Serializable {
    private Double Anger ;
    private Double Joy ;
    private Double Disgust;
    private Double Fear;
    private Double Sadness;


    public void SetScoreA(Double Score)
    {
        this.Anger= Score;
    }
    public Double GetScoreA ()
    {
        return Anger;
    }
    public void SetScoreJ(Double Score)
    {
        this.Joy= Score;
    }
    public Double GetScoreJ ()
    {
        return Joy;
    }
    public void SetScoreD(Double Score)
    {
        this.Disgust= Score;
    }
    public Double GetScoreD ()
    {
        return Disgust;
    }
    public void SetScoreF(Double Score)
    {
        this.Fear= Score;
    }
    public Double GetScoreF ()
    {
        return Fear;
    }
    public void SetScoreS(Double Score)
    {
        this.Sadness= Score;
    }
    public Double GetScoreS ()
    {
        return Sadness;
    }


}
