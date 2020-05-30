package com.nikandroid.chista.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.ViewSkeletonScreen;
import com.nikandroid.chista.CustomViewPager;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nikandroid.chista.Adapters.Main_pager_adapter;
import com.nikandroid.chista.Functions.Function;
import com.nikandroid.chista.Params.Params;
import com.nikandroid.chista.R;
import com.nikandroid.chista.ViewModelFactory.LoginModelFactory;
import com.nikandroid.chista.ViewModelFactory.MainModelFactory;
import com.nikandroid.chista.databinding.LoginBinding;
import com.nikandroid.chista.databinding.MainBinding;
import com.nikandroid.chista.viewModels.LoginViewModel;
import com.nikandroid.chista.viewModels.MainViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import devlight.io.library.ntb.NavigationTabBar;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class Main extends AppCompatActivity {

    private NavigationTabBar ntb ;
    public static CustomViewPager pager;
    Function fun;
    private SharedPreferences sp;
    String Claim="";
    public static String doact="";
    public static int tabc=2;
    private ProgressBar prog;
    RelativeLayout rootview;
    ViewSkeletonScreen ssc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainBinding binding= DataBindingUtil.setContentView(this,R.layout.main);
        binding.setMainModel(ViewModelProviders.of(this,new MainModelFactory(this)).get(MainViewModel.class));


        init();

        fun.like_splash();

        Tab_main();

        get_homepage();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(doact.equals("blockadviser") || doact.equals("chprofile") || doact.equals("shurefresh")){
            get_homepage();
            doact="";
        }


    }

    private void init(){

        tabc=2;
        sp=getSharedPreferences(Params.spmain,MODE_PRIVATE);
        Claim=sp.getString(Params.spClaim,"-");
        fun=new Function(this);



        ntb=  findViewById(R.id.main_tab1);
        pager= findViewById(R.id.main_pager);
        prog= findViewById(R.id.main_prog);
        rootview= findViewById(R.id.main_rooview);

        fun.Actionbar_2();

    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }


    private void Tab_main(){

        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.main_bottom_profile),
                        Color.parseColor("#00000000"))
                        .selectedIcon(getResources().getDrawable(R.drawable.main_bottom_profile_off))
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.main_bottom_chat_off),
                        Color.parseColor("#00000000"))
                        .selectedIcon(getResources().getDrawable(R.drawable.main_bottom_chat_on))
                        .build()
        );

        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.main_bottom_home_off),
                        Color.parseColor("#00000000"))
                        .selectedIcon(getResources().getDrawable(R.drawable.main_bottom_home_on))
                        .build()
        );

        ntb.setTypeface("fonts/Yekan_Bakh_Light.ttf");
        ntb.setModels(models);

    }

    private void get_homepage(){


        ssc = Skeleton.bind(rootview)
                .load(R.layout.layout_skeleton)
                .shimmer(true)
                .angle(15)
                .show();


        fun.show_debug_dialog(1,"cookie = "+ Claim, Params.ws_homepage);
        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_homepage,
                response -> {
                    fun.show_debug_dialog(2,response, Params.ws_homepage);
                    Make_Pager(response);
                },
                error -> {
                    fun.show_debug_dialog(2,error.toString(), Params.ws_prefilter);
                })

        {
            public String getBodyContentType()
            {
                return "application/json; charset=utf-8";
            }
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("cookie", Claim);
                return params;
            }
        };

        Volley.newRequestQueue(getActivity()).add(rq);

    }

    private Context getActivity(){
        return Main.this;
    }

    private void Make_Pager(String res){

        Log.e("qqqqqqq",sp.getString(Params.sp_isadviser,"1"));
        pager.setOffscreenPageLimit(3);
        Main_pager_adapter adapter=null;
        adapter= new Main_pager_adapter(getSupportFragmentManager(),res,sp);
        pager.removeAllViews();
        pager.setAdapter(adapter);


        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        ntb.setViewPager(pager);


        pager.setVisibility(View.VISIBLE);
        pager.post(() -> {
            pager.setCurrentItem(tabc,false);
        });

        new Handler().postDelayed(() -> {
            ssc.hide();
        },700);
/*
        pager.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        },500);

        new Handler().postDelayed(() -> {
            ssc.hide();
        },500);*/



    }







}
