package com.sov.sofysmo.emotiondiary.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sov.sofysmo.emotiondiary.R;
import com.sov.sofysmo.emotiondiary.Model.Diary;
import com.sov.sofysmo.emotiondiary.Model.Page;
import com.sov.sofysmo.emotiondiary.Utils.PreferencesManager;
//import com.sov.sofysmo.emotiondiary.Activity.TextBlankActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by SofySmo on 17.02.2016.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PageViewHolder> {
    Diary diary;
    public Context context;
    static public final String NamePage="com.sov.sofysmo.emotiondiary.Controller.namePage";
    String namePage;
    public RVAdapter(Context context, String nameDiary){

       this.diary = PreferencesManager.getInstance().getDiary(nameDiary);
        this.context=context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_diary, viewGroup, false);
        PageViewHolder pvh = new PageViewHolder(v);
        //Toast.makeText(context.getApplicationContext(),"[[[[",Toast.LENGTH_SHORT);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PageViewHolder pageViewHolder, int i) {
        /*pageViewHolder.pageDate.setText(dialy.pages.get(i).date);
        pageViewHolder.pageContent.setText(dialy.pages.get(i).contant);*/

    }

    @Override
    public int getItemCount() {
        return diary.pages.size();
    }

    public void addItem(int position, Page page) {
        diary.pages.add(position, page);
        notifyItemInserted(position);
        PreferencesManager.getInstance().saveDiary(diary);
    }
    public void removeItem(int position)
    {
        diary.pages.remove(position);
        notifyItemRemoved(position);
        PreferencesManager.getInstance().saveDiary(diary);
    }
    public List<Page> getDialy()
    {
        return diary.pages;
    }
    public static class PageViewHolder extends RecyclerView.ViewHolder{// implements View.OnClickListener, View.OnLongClickListener {

        CardView cv;
        TextView pageDate;
        TextView pageContent;

        PageViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            pageDate = (TextView)itemView.findViewById(R.id.date_page);
            pageContent = (TextView)itemView.findViewById(R.id.content_page);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Intent intent=new Intent(v.getContext(), TextBlankActivity.class);
                    intent.putExtra(NamePage,pageDate.getText().toString());
                    v.getContext().startActivity(intent);*/
                }
            });
        }
       /* @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }*/
    }
   /* public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }
    public void setOnItemClickListener(ClickListener clickListener){
        RVAdapter.clickListener = clickListener;
    }*/
}
