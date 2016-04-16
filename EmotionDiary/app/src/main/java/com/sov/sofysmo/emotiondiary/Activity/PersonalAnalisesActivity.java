package com.sov.sofysmo.emotiondiary.Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.sov.sofysmo.emotiondiary.R;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

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
        DiscreteSeekBar seekBar=(DiscreteSeekBar) findViewById(R.id.agreeabl);
        seekBar.setProgress(50);
        ((Button)findViewById(R.id.agreeabl_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout ll=(LinearLayout) findViewById(R.id.agreeabl_plus);
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
        });

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
}
