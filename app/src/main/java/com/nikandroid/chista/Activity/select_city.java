package com.nikandroid.chista.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nikandroid.chista.Functions.Function;
import com.nikandroid.chista.Params.Params;
import com.nikandroid.chista.R;

import net.steamcrafted.materialiconlib.MaterialIconView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class select_city extends AppCompatActivity {


    private SharedPreferences sp;
    String Claim="";
    Function fun;
    RecyclerView list;
    ProgressBar prog;
    int ccityid=0;
    JSONObject vals;
    String flag="";
    JSONObject fullres,tempsearch;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_city);

        init();

        try {
            flag=getIntent().getExtras().getString("flag");
            if(flag.equals("usersetting")){
                vals=new JSONObject(getIntent().getExtras().getString("vals"));
            }else if(flag.equals("submitadviser")){

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {

                String searchword=search.getText().toString();
                tempsearch=new JSONObject();
                try {
                    int ctr=0;
                    for (int i=0;i<fullres.length();i++){

                        JSONObject tmp=new JSONObject(fullres.getString(i+""));
                        if(tmp.getString("name").indexOf(searchword)>=0){
                            tempsearch.put(ctr+"",tmp);
                            ctr++;
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                make_list_city(tempsearch);
            }
        });





        get_list();

    }



    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));    }

    private Context getActivity(){
        return select_city.this;
    }

    private void init(){
        ccityid=getIntent().getExtras().getInt("ccity");


        sp=getSharedPreferences(Params.spmain,MODE_PRIVATE);
        fun=new Function(this);
        Claim=sp.getString(Params.spClaim,"-");


        View actv=fun.Actionbar_4(R.layout.actionbar_main);

        TextView acttitle=actv.findViewById(R.id.actionbar_main_title);
        acttitle.setText("انتخاب شهر");
        MaterialIconView actback=actv.findViewById(R.id.actionbar_main_back);
        actback.setOnClickListener(v -> finish());

        list=findViewById(R.id.select_city_list);
        prog=findViewById(R.id.select_city_progress);
        search=findViewById(R.id.selectcity_search);


    }

    private void get_list(){

        list.setVisibility(View.GONE);
        prog.setVisibility(View.VISIBLE);
        fun.show_debug_dialog(1,"{\"type\":\"listcity\",\"val\":0}", Params.ws_prefilter);
        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_prefilter,
                response -> {
                    fun.show_debug_dialog(2,response, Params.ws_prefilter);
                    list.setVisibility(View.VISIBLE);
                    prog.setVisibility(View.GONE);

                    try {
                        fullres=new JSONObject(response);
                        make_list_city(fullres);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {})

        {

            @Override
            public byte[] getBody() {
                String str = "{\"type\":\"listcity\",\"val\":0}";
                return str.getBytes();
            };

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






    private void make_list_city(JSONObject json)  {

        final city_adaptor rva1=new city_adaptor(json);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setItemAnimator(new DefaultItemAnimator());
        list.setAdapter(rva1);
    }

    public class city_adaptor extends RecyclerView.Adapter<city_adaptor.MyViewHolder> {

        private JSONObject list;
        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView name;
            CheckBox check;

            public MyViewHolder(View view) {
                super(view);
                name = itemView.findViewById(R.id.prefilter_row_listtag_name);
                check = itemView.findViewById(R.id.prefilter_row_listtag_check);
            }
        }


        public city_adaptor(JSONObject list) {
            this.list = list;
        }

        @Override
        public city_adaptor.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.prefilter_row_listtag, parent, false);
            return new city_adaptor.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final city_adaptor.MyViewHolder holder, final int position) {

            try {

                final JSONObject tmp=new JSONObject(list.getString(position+""));
                holder.name.setText(tmp.getString("name"));
                holder.check.setVisibility(View.GONE);
                if(ccityid==tmp.getInt("val")){
                    holder.name.setTextColor(getResources().getColor(R.color.Abi2));
                    holder.name.setTextSize(18);
                }

                holder.name.setOnClickListener(v -> {

                    try {
                        update_user_city_id(tmp.getString("name"),tmp.getString("val"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });

            }catch (Exception e){
            }

        }

        @Override
        public int getItemCount() {
            return list.length();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }
    }



    public void update_user_city_id(final String cityname, final String cityid){

        if(flag.equals("usersetting")){

            try {
                vals.put("city_id",cityid);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Toasty.normal(getActivity(),"لطفا صبر کنید",Toasty.LENGTH_LONG).show();
            StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_get_updateuserinfo,
                    response -> {
                        try {
                            JSONObject part=new JSONObject(response);
                            if(part.getString("message").equals("success")){
                                Toasty.success(getActivity(),"تغییر با موفقیت ثبت شد", Toasty.LENGTH_LONG).show();
                                SharedPreferences.Editor ed=sp.edit();
                                setting_user.refreshpage=true;
                                ed.putString(Params.sp_city_id,cityid);
                                ed.putString(Params.sp_city,cityname);
                                ed.commit();
                                finish();
                            }else{
                                Toasty.error(getActivity(),"بروز خطا"+"\n"+response, Toasty.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toasty.error(getActivity(),"بروز خطا"+"\n"+e.toString(), Toasty.LENGTH_LONG).show();
                        }
                    },
                    error -> {
                        Toasty.error(getActivity(),"بروز خطا"+"\n"+error.toString(), Toasty.LENGTH_LONG).show();
                    })

            {

                @Override
                public byte[] getBody() {
                    String str = vals.toString();
                    return str.getBytes();
                };
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


        }else if(flag.equals("submitadviser")){
/*
            Submit_Adviser.city_id=cityid;
            Submit_Adviser.city_name=cityname;
            finish();*/
        }





    }




}
