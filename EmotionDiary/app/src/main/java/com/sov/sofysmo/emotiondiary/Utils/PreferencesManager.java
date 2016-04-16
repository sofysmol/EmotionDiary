package com.sov.sofysmo.emotiondiary.Utils;

import android.content.Context;
import android.widget.Toast;

//import com.google.gson.GsonBuilder;
import com.sov.sofysmo.emotiondiary.Model.Diary;
import com.sov.sofysmo.emotiondiary.Model.Page;

import java.util.ArrayList;
import java.util.List;

//import com.google.gson.Gson;

/**
 * Created by SofySmo on 19.02.2016.
 */
public class PreferencesManager {
    private static PreferencesManager preferencesManager;
    private TinyDB tinyDB;
    private Context context;
    //private String nameDiary="Daily_1";
    public static PreferencesManager getInstance() {
        if (preferencesManager == null) {
            preferencesManager = new PreferencesManager();
        }
        return preferencesManager;
    }
    public void init(Context appContext) {
        tinyDB = new TinyDB(appContext);
        context=appContext;
    }
    public void saveDiary(Diary diary)
    {
       /* GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        tinyDB.putString(diary.name, gson.toJson(diary));
        Toast.makeText(context.getApplicationContext(),"save"+diary.name,Toast.LENGTH_SHORT);*/
    }
    public  Diary getDiary(String nameDiary)
    {
        /*GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        if(tinyDB.contains(nameDiary))
            return gson.fromJson(tinyDB.getString(nameDiary), Diary.class);
        else return new Diary(nameDiary,new ArrayList<Page>());*/
        return new Diary("gg",new ArrayList<Page>());
    }

}
