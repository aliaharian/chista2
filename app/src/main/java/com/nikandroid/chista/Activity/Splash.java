package com.nikandroid.chista.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.nikandroid.chista.Functions.Function;
import com.nikandroid.chista.Params.Params;
import com.nikandroid.chista.R;
import com.skydoves.elasticviews.ElasticLayout;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class Splash extends AppCompatActivity {


    private SharedPreferences sp;
    private Function fun;
    String level="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        init();
        Handler hd=new Handler();
        if(level.equals("getphone")){

            hd.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Splash.this, login.class));
                    finish();
                }
            },1500);

        }else if(level.equals("getcode")){
            hd.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Splash.this, login.class));
                    finish();
                }
            },2500);
        }else if(level.equals("complete")){
            hd.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Splash.this, Main.class));
                    finish();
                }
            },2500);
        }


    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));    }



    private void init(){



        sp=getSharedPreferences(Params.spmain,MODE_PRIVATE);
        fun=new Function(Splash.this);
        fun.setwh();

        fun.Actionbar_1();


        level=sp.getString(Params.login_level,"getphone");


    }



}
