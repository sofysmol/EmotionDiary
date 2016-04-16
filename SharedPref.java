package com.sov.sofysmo.emotiondiary.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sov.sofysmo.emotiondiary.Model.MyToneScore;
import com.sov.sofysmo.emotiondiary.Model.Page;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * Created by Asus on 16.04.2016.
 */
public class SharedPref {

    private SharedPreferences.Editor editor;
    private SharedPreferences mysettings;
    private List<Page> pages;



    private static final String NUM = "number";

    public List<Page> getPages()
    {
        int count = mysettings.getInt(NUM, 0);
        Page page;
        for (int i = 0; i < count; i++)
        {
            page = getCard(i);
            if (page != null)
            {
                pages.add(page);
            }
        }
        return pages;
    }

    public SharedPref(SharedPreferences mysettings)
    {
        this.mysettings = mysettings;
        Map<String, ?> allPreferences = mysettings.getAll();
        if (allPreferences == null)
        {
             editor.putInt(NUM, 0);
        }
    }

    public void addCard(String title, String date, String text, MyToneScore tone)
    {
        int count = mysettings.getInt(NUM, 0);
        Page page = new Page(title, date, text, tone);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        editor.putString("card_" + count, gson.toJson(page));
        editor.putInt(NUM, count+1);
        editor.apply();

    }

    public Page getCard(int i)
    {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        if (mysettings.getAll().containsKey("card_" + i))
        {
            return gson.fromJson(mysettings.getString("card_" + i, ""), Page.class);
        }
        else return null;
    }

}
