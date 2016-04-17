package com.sov.sofysmo.emotiondiary.Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import com.ibm.watson.developer_cloud.personality_insights.v2.model.Profile;

import com.ibm.watson.developer_cloud.personality_insights.v2.model.Trait;
import com.sov.sofysmo.emotiondiary.R;
import com.sov.sofysmo.emotiondiary.Utils.PersonalityConnect;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.List;

public class PersonalAnalisesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_analises);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        initSlider();
        initButton();

        //LinearLayout ll=(LinearLayout) findViewById(R.id.agreeabl_plus);
        //ll.getLayoutParams().height=1300;


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_diary) {
            Intent intent = new Intent(this, DiaryActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_personal_analysis) {

        } else if (id == R.id.nav_mood) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void showMoreInformation(View view, LinearLayout ll)
    {
        int height=ll.getLayoutParams().height;
        if(height!=0)
        {
            height=0;
            RotateAnimation rotate = new RotateAnimation(180.0f, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotate.setFillAfter(true);

            view.startAnimation(rotate);
        }
        else
        {
            RotateAnimation rotate = new RotateAnimation(0.0f, 180.0f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotate.setFillAfter(true);

            view.startAnimation(rotate);
            height=1000;
        }
        ll.getLayoutParams().height=height;
        ll.requestLayout();
    }
    private void initSlider()
    {
        PersonalityConnect personality;
        Handler handlerPersonality= new Handler() {  // посрденик между потоками(он нам понадобиться для того, чтобы передать данные из другого потока в поток UI)
            public void handleMessage(android.os.Message msg) {
                Profile profile = (Profile) msg.obj; //Получаем profile от Watson
                List<Trait> data=profile.getTree().getChildren().get(0).getChildren();
                initOpeninness(data.get(0).getChildren().get(0));
                initConscientiousness(data.get(0).getChildren().get(1));
                initExtraversion(data.get(0).getChildren().get(2));
                initAgreeabl(data.get(0).getChildren().get(3));

                }
        };;
        personality = new PersonalityConnect("47c494e1-40b4-4f7b-9dfc-aa6d744a7480", "KMCFyQeEK4BN");
        personality.setHandler(handlerPersonality);
           personality.runRequestServiceSpeechToText(getIntent().getStringExtra("Text"));


    }

    private void initButton()
    {
        ((Button) findViewById(R.id.agreeabl_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout ll3 = (LinearLayout) findViewById(R.id.agreeabl_plus);
                showMoreInformation(view, ll3);
            }
        });
        ((Button) findViewById(R.id.intr_extr_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout ll3 = (LinearLayout) findViewById(R.id.intr_extr_plus);
                showMoreInformation(view, ll3);
            }
        });
        ((Button) findViewById(R.id.emotional_range_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout ll3 = (LinearLayout) findViewById(R.id.emotional_range_plus);
                showMoreInformation(view, ll3);
            }
        });
        ((Button) findViewById(R.id.openness_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout ll3 = (LinearLayout) findViewById(R.id.openness_plus);
                showMoreInformation(view, ll3);
            }
        });
        ((Button) findViewById(R.id.conscientiousness_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout ll3 = (LinearLayout) findViewById(R.id.conscientiousness_plus);
                showMoreInformation(view, ll3);
            }
        });
    }
    private void initOpeninness(Trait openinness)
    {
        ((DiscreteSeekBar) findViewById(R.id.openness)).setProgress((int)(openinness.getPercentage()*100));
        List<Trait> children=openinness.getChildren();
        ((DiscreteSeekBar) findViewById(R.id.openness_adventurousness)).setProgress((int)(children.get(0).getPercentage()*100));
        ((DiscreteSeekBar) findViewById(R.id.openness_artistic_interests)).setProgress((int)(children.get(1).getPercentage()*100));
        ((DiscreteSeekBar) findViewById(R.id.openness_emotionality)).setProgress((int)(children.get(2).getPercentage()*100));
        ((DiscreteSeekBar) findViewById(R.id.openness_imagination)).setProgress((int)(children.get(3).getPercentage()*100));
        ((DiscreteSeekBar) findViewById(R.id.openness_intellect)).setProgress((int)(children.get(4).getPercentage()*100));
        ((DiscreteSeekBar) findViewById(R.id.openness_authority_challenging)).setProgress((int)(children.get(5).getPercentage()*100));
    }
    private void initConscientiousness(Trait openinness)
    {
        ((DiscreteSeekBar) findViewById(R.id.conscientiousness)).setProgress((int)(openinness.getPercentage()*100));
        List<Trait> children=openinness.getChildren();
        ((DiscreteSeekBar) findViewById(R.id.conscientiousness_achievement_striving)).setProgress((int)(children.get(0).getPercentage()*100));
        ((DiscreteSeekBar) findViewById(R.id.conscientiousness_cautiousness)).setProgress((int)(children.get(1).getPercentage()*100));
        ((DiscreteSeekBar) findViewById(R.id.conscientiousness_dutifulness)).setProgress((int)(children.get(2).getPercentage()*100));
        ((DiscreteSeekBar) findViewById(R.id.conscientiousness_imagination)).setProgress((int)(children.get(3).getPercentage()*100));
        ((DiscreteSeekBar) findViewById(R.id.conscientiousness_self_discipline)).setProgress((int)(children.get(4).getPercentage()*100));
    }
    private void initExtraversion(Trait openinness)
    {
        ((DiscreteSeekBar) findViewById(R.id.intr_extr)).setProgress((int)(openinness.getPercentage()*100));
        List<Trait> children=openinness.getChildren();
        ((DiscreteSeekBar) findViewById(R.id.intr_extr_activity_level)).setProgress((int)(children.get(0).getPercentage()*100));
        ((DiscreteSeekBar) findViewById(R.id.intr_extr_assertiveness)).setProgress((int)(children.get(1).getPercentage()*100));
        ((DiscreteSeekBar) findViewById(R.id.intr_extr_cheerfulness)).setProgress((int)(children.get(2).getPercentage()*100));
        ((DiscreteSeekBar) findViewById(R.id.intr_extr_excitement_seeking)).setProgress((int)(children.get(3).getPercentage()*100));
        ((DiscreteSeekBar) findViewById(R.id.intr_extr_gregariousness)).setProgress((int)(children.get(4).getPercentage()*100));
    }
    private void initAgreeabl(Trait openinness)
    {
        ((DiscreteSeekBar) findViewById(R.id.agreeabl)).setProgress((int)(openinness.getPercentage()*100));
        List<Trait> children=openinness.getChildren();
        ((DiscreteSeekBar) findViewById(R.id.agreeabl_altruism)).setProgress((int)(children.get(0).getPercentage()*100));
        ((DiscreteSeekBar) findViewById(R.id.agreeabl_cooperation)).setProgress((int)(children.get(1).getPercentage()*100));
        ((DiscreteSeekBar) findViewById(R.id.agreeabl_modesty)).setProgress((int)(children.get(2).getPercentage()*100));
        ((DiscreteSeekBar) findViewById(R.id.agreeabl_sympath)).setProgress((int)(children.get(3).getPercentage()*100));
        ((DiscreteSeekBar) findViewById(R.id.agreeabl_trust)).setProgress((int)(children.get(4).getPercentage()*100));
    }

}
