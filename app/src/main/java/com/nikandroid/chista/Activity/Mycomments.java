package com.nikandroid.chista.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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

public class Mycomments extends AppCompatActivity {

    private String adid;
    private SharedPreferences sp;
    String Claim="";
    Function fun;
    RecyclerView list;
    PowerMenu powerMenu;
    ElasticImageView back;
    LinearLayout novaluearea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycomments);

        init();




    }
    @Override
    protected void onResume() {
        super.onResume();
        get_mycomments();
    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));    }

    private Context getActivity(){
        return Mycomments.this;
    }

    private void init(){

        sp=getSharedPreferences(Params.spmain,MODE_PRIVATE);
        fun=new Function(this);
        Claim=sp.getString(Params.spClaim,"-");

        getSupportActionBar().hide();
        back=findViewById(R.id.mycomments_actionbar_back);
        novaluearea=findViewById(R.id.mycomments_novaluearea);

        back.setOnClickListener(v -> finish());
        list=findViewById(R.id.mycomments_list);

    }

    private void get_mycomments(){

        list.setVisibility(View.GONE);
        fun.show_debug_dialog(1,"{\"adviser_id\":"+adid+"}", Params.ws_mycomments);
        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_mycomments,
                response -> {
                    fun.show_debug_dialog(2,response, Params.ws_mycomments);
                    list.setVisibility(View.VISIBLE);
                    make_list_mycomments(response);
                },
                error -> {
                    list.setVisibility(View.VISIBLE);
                })

        {
            @Override
            public byte[] getBody() {
                String str = "{\"adviser_id\":"+adid+"}";
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


    private void make_list_mycomments(String res)  {

        if(res.length()<5){
            novaluearea.setVisibility(View.VISIBLE);
            list.setVisibility(View.INVISIBLE);
        }else{
            novaluearea.setVisibility(View.GONE);
            list.setVisibility(View.VISIBLE);
        }


        Log.e("mcc",res);
        JSONArray items = null;
        try {
            items = new JSONArray(res);

            final comments_adapter rva1=new comments_adapter(items,getActivity(),fun);
            list.setLayoutManager(new LinearLayoutManager(getActivity()));
            list.setItemAnimator(new DefaultItemAnimator());
            list.setAdapter(rva1);

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }


    public class comments_adapter extends RecyclerView.Adapter<comments_adapter.MyViewHolder> {

        private JSONArray list;
        private AppCompatActivity appCompat;
        private Context context;
        private Function fun;
        private PowerMenu powerMenucm;
        public class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView name,text,date;
            public ImageView img;
            public MaterialIconView menu;
            public MyViewHolder(View view) {
                super(view);
                name =  view.findViewById(R.id.mycomments_row_name);
                text =  view.findViewById(R.id.mycomments_row_text);
                img =  view.findViewById(R.id.mycomments_row_image);
                menu =  view.findViewById(R.id.mycomments_row_menu);
            }
        }


        public comments_adapter(JSONArray list,Context c,Function f) {
            this.list = list;
            context=c;
            appCompat=(AppCompatActivity)c;
            fun=f;
        }

        @Override
        public comments_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.comments_row1, parent, false);
            return new comments_adapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final comments_adapter.MyViewHolder holder, final int position) {

            JSONObject tmp = null;
            try {
                tmp = new JSONObject(list.getString(position ));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {

                holder.name.setText(tmp.getString("adviser_name"));
                holder.text.setText(tmp.getString("text"));
                holder.date.setText(tmp.getString("date"));

            }catch (Exception e){
            }

            holder.menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PowerMenu pm = new PowerMenu.Builder(getActivity())
                            .addItem(new PowerMenuItem("پاسخ دادن"))
                            .addItem(new PowerMenuItem("گزارش"))
                            .setAnimation(MenuAnimation.SHOWUP_BOTTOM_RIGHT) // Animation start point (TOP | LEFT).
                            .setMenuRadius(10f) // sets the corner radius.
                            .setMenuShadow(10f) // sets the shadow.
                            .setTextColor(ContextCompat.getColor(getActivity(), R.color.siyah))
                            .setTextGravity(Gravity.RIGHT)
                            .setSelectedTextColor(Color.RED)
                            .setMenuColor(Color.WHITE)
                            .setSelectedMenuColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary))
                            .setOnMenuItemClickListener(new OnMenuItemClickListener<PowerMenuItem>() {
                                @Override
                                public void onItemClick(int position, PowerMenuItem item) {



                                }
                            })
                            .build();
                }
            });


            try {
                Picasso
                        .get()
                        .load(Params.pic_adviseravatae+tmp.getString("adviser_avatar"))
                        .error(R.drawable.nopic)
                        .into(holder.img);
            } catch (JSONException e) {
                e.printStackTrace();
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
