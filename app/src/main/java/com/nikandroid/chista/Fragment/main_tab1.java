package com.nikandroid.chista.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.nikandroid.chista.Activity.adviser_list;
import com.nikandroid.chista.Activity.prefilter;
import com.nikandroid.chista.Activity.search;
import com.nikandroid.chista.Adapters.main_list_adviser_adapter;
import com.nikandroid.chista.Adapters.main_list_adviser_horezontal_adapter;
import com.nikandroid.chista.Adapters.main_list_cat_adapter;
import com.nikandroid.chista.Adapters.main_slider_adapter;
import com.nikandroid.chista.Functions.Function;
import com.nikandroid.chista.Params.Params;
import com.nikandroid.chista.R;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;

import me.relex.circleindicator.CircleIndicator;

public class main_tab1 extends Fragment {

    private SharedPreferences sp;
    private Function fun;
    String Claim="",content="";
    public static Timer ttrs;

    ////////////////////////////////////xml
    LinearLayout mainlayout1;
    ScrollView main_scroll;
    LinearLayout mainlayout2;



    public static main_tab1 newInstance(int position,String c) {
        main_tab1 f = new main_tab1();
        Bundle b = new Bundle();
        b.putInt("position", position);
        b.putString("content", c);
        f.setArguments(b);
        return f;

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int position = getArguments().getInt("position");
        content=getArguments().getString("content");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        fun = new Function(getActivity());
        View view = inflater.inflate(R.layout.main_tab1, container, false);
        init(view);

        makerows(content);

        return view;
    }



    private void init(View vv){
        sp=getActivity().getSharedPreferences(Params.spmain,getActivity().MODE_PRIVATE);
        Claim=sp.getString(Params.spClaim,"-");
        mainlayout1=vv.findViewById(R.id.main_mainlayout);
    }



    private void makerows(String jsonres){

        try {

            JSONObject hpage = new JSONObject(jsonres);
            JSONObject rows = new JSONObject(hpage.getString("homepage"));

            for(int i=1;i<=rows.length();i++) {

                JSONObject param = new JSONObject(rows.getString("row"+i));
                if(param.getString("type").equals("search")){
                    make_search_line();

                }
                if(param.getString("type").equals("slider")){
                    make_slider_line(param.getString("value"));
                }
                if(param.getString("type").equals("category")){
                    make_list_cats(param.getString("value"),param.getString("title"));
                }
                if(param.getString("type").equals("adviser")){

                    main_scroll.fullScroll(ScrollView.FOCUS_DOWN);
                    make_list_adviser1(param.getString("value"),param.getString("title"),param.getString("orientation"),param.getString("cat_id"));

                }
                if(param.getString("type").equals("image")){
                    make_sample_pic(param.getString("value"));
                }

            }

        } catch (JSONException e) {
        }

    }



    private void make_search_line(){

        View sv=getLayoutInflater().inflate(R.layout.main_line_search,null,false);
        LinearLayout searchview=sv.findViewById(R.id.main_line_search_searchview);
        ImageView filterbtn=sv.findViewById(R.id.main_line_search_filters);

        LinearLayout.LayoutParams llp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        llp.setMargins(20,10,20,0);
        sv.setLayoutParams(llp);

        searchview.setOnClickListener(v -> {
            Intent i=new Intent(getActivity(),search.class);
            i.putExtra("body","-");
            startActivity(i);
        });

        filterbtn.setOnClickListener(v -> {

            Intent i=new Intent(getActivity(), prefilter.class);
            startActivity(i);

        });

        //mainlayout1.addView(sv);

        main_scroll=new ScrollView(getActivity());
        main_scroll.setVerticalScrollBarEnabled(false);
        mainlayout2 =new LinearLayout(getActivity());

        mainlayout2.setOrientation(LinearLayout.VERTICAL);
        mainlayout2.setPadding(0,0,0,480);
        main_scroll.addView(mainlayout2);
        mainlayout2.addView(sv);///////
        LinearLayout.LayoutParams llp2=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1);
        main_scroll.setLayoutParams(llp2);
        mainlayout1.addView(main_scroll);

    }

    private void make_slider_line(String sliderval){

        try {
            JSONObject rows = new JSONObject(sliderval);

            final ViewPager slidepager=new ViewPager(getActivity());
            RelativeLayout ssla=new RelativeLayout(getActivity());
            ssla.setPadding((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, getResources().getDisplayMetrics()),0,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, getResources().getDisplayMetrics()),0);
            int h=(sp.getInt("sh",1000)/100)*26;
            LinearLayout.LayoutParams vpsp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, h);
            ssla.setLayoutParams(vpsp);
            RelativeLayout.LayoutParams sslps=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
            slidepager.setLayoutParams(sslps);
            ssla.addView(slidepager);


            CircleIndicator ind=new CircleIndicator(getActivity());
            RelativeLayout.LayoutParams indparam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            indparam.addRule(RelativeLayout.CENTER_HORIZONTAL);
            indparam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            indparam.setMargins(0,0,0,5);
            ind.setLayoutParams(indparam);

            final main_slider_adapter ppssdf=new main_slider_adapter(rows,getActivity(),fun);

            slidepager.setAdapter(ppssdf);
            ind.setViewPager(slidepager);
            ssla.addView(ind);
            mainlayout2.addView(ssla);

        }catch (Exception e){
        }

    }

    private void make_list_cats(String catsval,String title) throws JSONException {

        JSONObject items = new JSONObject(catsval);
        View cv=getLayoutInflater().inflate(R.layout.main_line_cats,null,false);


        RecyclerView list= cv.findViewById(R.id.main_lines_cats_list);

        /*
        filterbtn.setOnClickListener(v -> {

            Intent i=new Intent(getActivity(), prefilter.class);
            startActivity(i);

        });*/


        final main_list_cat_adapter rva1=new main_list_cat_adapter(items,getActivity());
        rva1.setHasStableIds(true);
        list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true));
        list.setItemAnimator(new DefaultItemAnimator());
        list.setAdapter(rva1);

        LinearLayout.LayoutParams llp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        llp.setMargins(0,20,20,0);
        cv.setLayoutParams(llp);
        mainlayout2.addView(cv);
    }


    private void make_list_adviser1(String adval, String title, String oriention, final String catid) {


        JSONObject items = null;
        try {
            items = new JSONObject(adval);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        View cv=getLayoutInflater().inflate(R.layout.main_line_adviserbox,null,false);

        TextView label= cv.findViewById(R.id.main_line_adviserbox1label);
        LinearLayout seemore= cv.findViewById(R.id.main_line_adviserbox1seemorell);

        RecyclerView list= cv.findViewById(R.id.main_line_adviserbox1list);
        label.setText(title);
        list.setNestedScrollingEnabled(false);


        seemore.setOnClickListener(v -> {

            if(catid.equals("-1")){
                Intent i=new Intent(getActivity(), adviser_list.class);
                i.putExtra("flag","myadv");
                startActivity(i);
            }else{
                String body="{\"type\":\"filter_result\",\"q\":\"\",\"valcat\":\""+catid+",\",\"valtag\":\"\",\"valcity\":\"\",\"range\":\"\",\"gender\":\"\",\"isonline\":\"\"}";
                Intent i=new Intent(getActivity(),search.class);
                i.putExtra("body",body);
                startActivity(i);

            }

        });



        if(oriention.equals("v")){

            final main_list_adviser_adapter rva1=new main_list_adviser_adapter(items,getActivity(),fun);
            list.setLayoutManager(new LinearLayoutManager(getActivity()));
            list.setItemAnimator(new DefaultItemAnimator());
            list.setAdapter(rva1);


        }else{
            final main_list_adviser_adapter rva1=new main_list_adviser_adapter(items,getActivity(),fun);
            list.setLayoutManager(new LinearLayoutManager(getActivity()));
            list.setItemAnimator(new DefaultItemAnimator());
            list.setAdapter(rva1);


/*
            final main_list_adviser_horezontal_adapter rva1=new main_list_adviser_horezontal_adapter(items,getActivity());
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);
            list.setLayoutManager(layoutManager);
            list.setItemAnimator(new DefaultItemAnimator());
            list.setAdapter(rva1);*/

        }


        LinearLayout.LayoutParams llp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        llp.setMargins(20,20,20,0);
        cv.setLayoutParams(llp);
        mainlayout2.addView(cv);




    }


    private void make_sample_pic(String samplepicval){

        try {
            JSONObject part = new JSONObject(samplepicval);
            LinearLayout.LayoutParams implp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ImageView img=new ImageView(getActivity());
            img.setAdjustViewBounds(true);
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            img.setLayoutParams(implp);

            Picasso
                    .get()
                    .load(Params.main_domain+part.getString("image"))
                    .error(R.drawable.nopic)
                    .into(img);

            final String act=part.getString("act");
            final String actval=part.getString("act_val");
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fun.intent_actions(act,actval);
                }
            });
            mainlayout2.addView(img);
        }catch (Exception e){

        }
    }


}
