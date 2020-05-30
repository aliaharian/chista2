package com.nikandroid.chista.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nikandroid.chista.Adapters.block_list_adaptor;
import com.nikandroid.chista.Adapters.my_adviser_adapter;
import com.nikandroid.chista.Functions.Function;
import com.nikandroid.chista.Params.Params;
import com.nikandroid.chista.R;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.squareup.picasso.Picasso;

import net.steamcrafted.materialiconlib.MaterialIconView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class adviser_list extends AppCompatActivity {




    private SharedPreferences sp;
    String Claim="";
    RecyclerView list;
    ProgressBar prog;
    TextView actlabel;
    Function fun;
    String from ="";
    String flag="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adviser_list);
        init();


        if(flag.equals("block")){
            get_block_list();

        }else if(flag.equals("myadv")){
            get_myadv_list();
        }






    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));    }

    private Context getActivity(){
        return adviser_list.this;
    }

    private void init(){

        flag=getIntent().getExtras().getString("flag");
        sp=getSharedPreferences(Params.spmain,MODE_PRIVATE);
        fun=new Function(this);
        Claim=sp.getString(Params.spClaim,"-");

        //View actv=fun.Actionbar_4(R.layout.actionbar_main);
        getSupportActionBar().hide();

        //actlabel=actv.findViewById(R.id.actionbar_main_title);
       // actlabel.setText("مشاوران من");
        //MaterialIconView actback=actv.findViewById(R.id.actionbar_main_back);
        //actback.setOnClickListener(v -> finish());


        list=findViewById(R.id.adviser_list_list);
        prog=findViewById(R.id.adviser_list_prog);


    }

    private void get_block_list(){

        list.setVisibility(View.GONE);
        prog.setVisibility(View.VISIBLE);
        fun.show_debug_dialog(1,"-", Params.ws_blocklist);
        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_blocklist,
                response -> {
                    fun.show_debug_dialog(2,response, Params.ws_blocklist);
                    make_list_adviser1(response);

                },
                error -> {})
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

    private void get_myadv_list(){

        list.setVisibility(View.GONE);
        prog.setVisibility(View.VISIBLE);

        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_myadviser,
                response -> {
                    make_list_adviser1(response);
                },
                error ->{})
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



    private void make_list_adviser1(String respons)  {

        JSONObject items = null;
        try {
            list.setVisibility(View.VISIBLE);
            prog.setVisibility(View.GONE);

            items = new JSONObject(respons);
            if(items.length()==0){
                Toasty.warning(getActivity(),"نتیجه ای یافت نشد", Toasty.LENGTH_LONG).show();
            }
            if(flag.equals("block")){
                final block_list_adaptor rva1=new block_list_adaptor(items,getActivity(),fun);
                list.setLayoutManager(new LinearLayoutManager(getActivity()));
                list.setItemAnimator(new DefaultItemAnimator());
                list.setAdapter(rva1);
            }else if(flag.equals("myadv")){
                final my_adviser_adapter rva1=new my_adviser_adapter(items,getActivity(),fun);
                list.setLayoutManager(new LinearLayoutManager(getActivity()));
                list.setItemAnimator(new DefaultItemAnimator());
                list.setAdapter(rva1);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }



}
