package com.sov.sofysmo.emotiondiary.Model;

import java.util.List;

/**
 * Created by SofySmo on 16.04.2016.
 */
public class Diary {
    public String name;
    public List<Page> pages;
    public Diary(String name,List<Page> pages)
    {
        this.name=name;
        this.pages=pages;
    }
}
