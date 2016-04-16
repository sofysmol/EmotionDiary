package com.sov.sofysmo.emotiondiary.Model;

import java.io.Serializable;

/**
 * Created by SofySmo on 16.04.2016.
 */
public class Page implements Serializable {
    public String title;
    public String date;
    public String story;
    Page(String title,String date, String story){
        this.title=title;
        this.date=date;
        this.story=date;
    }
}
