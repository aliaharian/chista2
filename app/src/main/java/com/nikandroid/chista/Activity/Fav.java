package com.nikandroid.chista.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nikandroid.chista.Adapters.main_list_adviser_adapter;
import com.nikandroid.chista.Functions.Function;
import com.nikandroid.chista.Params.Params;
import com.nikandroid.chista.R;
import com.skydoves.elasticviews.ElasticImageView;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;
import com.squareup.picasso.Picasso;

import net.steamcrafted.materialiconlib.MaterialIconView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class Fav extends AppCompatActivity {

    private String adid;
    private SharedPreferences sp;
    String Claim="";
    Function fun;
    RecyclerView list;
    PowerMenu powerMenu;
    ElasticImageView back;
    LinearLayout novaluearea;
    ProgressBar prog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fav);

        init();




    }
    @Override
    protected void onResume() {
        super.onResume();
        get_myfavs();
    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));    }

    private Context getActivity(){
        return Fav.this;
    }

    private void init(){

        sp=getSharedPreferences(Params.spmain,MODE_PRIVATE);
        fun=new Function(this);
        Claim=sp.getString(Params.spClaim,"-");

        getSupportActionBar().hide();
        back=findViewById(R.id.fav_actionbar_back);
        novaluearea=findViewById(R.id.fav_novaluearea);

        back.setOnClickListener(v -> finish());
        list=findViewById(R.id.fav_list);

    }

    private void get_myfavs(){

        list.setVisibility(View.GONE);
        fun.show_debug_dialog(1,"{\"adviser_id\":"+adid+"}", Params.ws_myadviser);
        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_myadviser,
                response -> {
                    Log.e("myadviser",response+"-");
                    fun.show_debug_dialog(2,response, Params.ws_mycomments);
                    list.setVisibility(View.VISIBLE);
                    make_list_favs(response);
                },
                error -> {
                    list.setVisibility(View.VISIBLE);
                })

        {/*
            @Override
            public byte[] getBody() {
                String str = "{\"adviser_id\":"+adid+"}";
                return str.getBytes();
            };*/

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






    private void make_list_favs(String res)  {

        novaluearea.setVisibility(View.VISIBLE);
        list.setVisibility(View.INVISIBLE);




        try {
            JSONObject items = new JSONObject(res);

            list.setVisibility(View.VISIBLE);

            if(items.length()==0){
                //Toasty.warning(getActivity(),"نتیجه ای یافت نشد", Toasty.LENGTH_LONG).show();
                novaluearea.setVisibility(View.VISIBLE);
            }else{
                novaluearea.setVisibility(View.GONE);
            }
            final main_list_adviser_adapter rva1=new main_list_adviser_adapter(items,Fav.this,fun);
            list.setLayoutManager(new LinearLayoutManager(getActivity()));
            list.setItemAnimator(new DefaultItemAnimator());
            list.setAdapter(rva1);


        } catch (JSONException e) {
            e.printStackTrace();
        }





    }


    public class main_list_adviser_adapter extends RecyclerView.Adapter<main_list_adviser_adapter.MyViewHolder> {

        private JSONObject list;
        private AppCompatActivity appCompat;
        private Context context;
        Function fun;
        public class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView name,des,price,star;
            public ImageView img,ost;
            public CardView maincard;
            public MyViewHolder(View view) {
                super(view);
                name =  view.findViewById(R.id.main_line_adviserbox1_row_name);
                des =  view.findViewById(R.id.main_line_adviserbox1_row_des);
                //hr =  view.findViewById(R.id.main_line_adviserbox1_row_hr);
                // loc =  view.findViewById(R.id.main_line_adviserbox1_row_loc);
                price =  view.findViewById(R.id.main_line_adviserbox1_row_price);
                img =  view.findViewById(R.id.main_line_adviserbox1_row_img);
                ost =  view.findViewById(R.id.main_line_adviserbox1_row_onlinestatus);
                star =  view.findViewById(R.id.main_line_adviserbox1_row_star);

            }
        }


        public main_list_adviser_adapter(JSONObject list, Context c, Function fun) {
            this.list = list;
            context=c;
            appCompat=(AppCompatActivity)c;
            this.fun=fun;
        }

        @Override
        public main_list_adviser_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.main_line_adviser1_row, parent, false);

            return new main_list_adviser_adapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final main_list_adviser_adapter.MyViewHolder holder, final int position) {


            try {
                final JSONObject tmp = new JSONObject(list.getString(position+"" ));

                Picasso
                        .get()
                        .load(Params.pic_adviseravatae+tmp.getString("avatar"))
                        .into(holder.img);

                holder.name.setText(tmp.getString("name"));
                holder.des.setVisibility(View.GONE);
                //holder.price.setText(fun.change_adad(fun.farsi_sazi_adad(tmp.getString("price")))+" تومان");
                //holder.star.setText(fun.farsi_sazi_adad(tmp.getString("star")));

                if(tmp.getString("is_online").equals("2")){
                    holder.ost.setImageResource(R.drawable.z_circle_busy);
                }else if(tmp.getString("is_online").equals("1")){
                    holder.ost.setImageResource(R.drawable.z_circle_online);
                }else{
                    holder.ost.setImageResource(R.drawable.z_circle_offline);
                }

                holder.itemView.setOnClickListener(v -> {
                    try {
                        Intent i=new Intent(context, user.class);
                        i.putExtra("adid",tmp.getString("id"));
                        appCompat.startActivity(i);
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







}
