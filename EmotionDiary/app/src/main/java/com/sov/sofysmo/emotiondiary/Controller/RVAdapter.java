package com.sov.sofysmo.emotiondiary.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;
import android.widget.Toast;

import com.sov.sofysmo.emotiondiary.Activity.PersonalAnalisesActivity;
import com.sov.sofysmo.emotiondiary.Activity.ReadActivity;
import com.sov.sofysmo.emotiondiary.Model.MyToneScore;
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
        pageViewHolder.pageTitle.setText(getDialy().get(i).title);
        pageViewHolder.pageDate.setText(getDialy().get(i).date);
        pageViewHolder.pageContent.setText(getDialy().get(i).text);
        List<Double> list = new ArrayList<Double>();
        list.add(getDialy().get(i).tone.GetScoreA());
        list.add(getDialy().get(i).tone.GetScoreJ());
        list.add(getDialy().get(i).tone.GetScoreD());
        list.add(getDialy().get(i).tone.GetScoreF());
        list.add(getDialy().get(i).tone.GetScoreS());
        int max_ind = 0;
        for (int j = 1; j < list.size(); j++)
        {
            if (list.get(j) > list.get(max_ind))
            {
                max_ind = j;
            }
        }
        switch (max_ind)
        {
            case 0:
                pageViewHolder.tone.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                pageViewHolder.textTone.setText("Angry");
                break;
            case 1:
                pageViewHolder.tone.setBackgroundColor(context.getResources().getColor(R.color.colorHappy));
                pageViewHolder.textTone.setText("Joy");
                break;
            case 2:
                pageViewHolder.tone.setBackgroundColor(context.getResources().getColor(R.color.disgust));
                pageViewHolder.textTone.setText("Disgust");
                break;
            case 3:
                pageViewHolder.tone.setBackgroundColor(context.getResources().getColor(R.color.fear));
                pageViewHolder.textTone.setText("Fear");
                break;
            case 4:
                pageViewHolder.tone.setBackgroundColor(context.getResources().getColor(R.color.sadness));
                pageViewHolder.textTone.setText("Sadness");
                break;
        }
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
        TextView pageTitle;
        View tone;
        TextView textTone;

        PageViewHolder(View itemView) {
            super(itemView);

            cv = (CardView)itemView.findViewById(R.id.cv);
            pageTitle = (TextView)itemView.findViewById(R.id.title_page);
            pageDate = (TextView)itemView.findViewById(R.id.date_page);
            pageContent = (TextView)itemView.findViewById(R.id.content_page);
            tone = (View)itemView.findViewById(R.id.rect);
            textTone = (TextView)itemView.findViewById(R.id.status_ton);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(cv.getContext(), ReadActivity.class);
                    intent.putExtra("Title", pageTitle.getText().toString());
                    intent.putExtra("Date", pageDate.getText().toString());
                    intent.putExtra("Text", pageContent.getText().toString());
                    intent.putExtra("Tone", textTone.getText().toString());
                    cv.getContext().startActivity(intent);
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
