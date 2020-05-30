package com.nikandroid.chista.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nikandroid.chista.Adapters.RtlGridLayoutManager;
import com.nikandroid.chista.Adapters.list_tags_adapter;
import com.nikandroid.chista.Functions.Function;
import com.nikandroid.chista.Params.Params;
import com.nikandroid.chista.R;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;
import com.squareup.picasso.Picasso;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class user_more extends AppCompatActivity {

    private String adid="";
    private SharedPreferences sp;
    private TextView name,shotdes,longdes,phone;
    private ImageView userimage;
    String Claim="";
    RecyclerView listtags;
    Function fun;
    ProgressBar prog;
    MaterialIconView markicon,menuicon;
    ScrollView mainscroll;
    YoYo.YoYoString shakeyoyo;
    LinearLayout cubcomment;
    PowerMenu powerMenucm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_more);


        init();

        get_full_user();


    }


    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));    }

    private void init(){

        adid=getIntent().getExtras().getString("adid");
        sp=getSharedPreferences(Params.spmain,MODE_PRIVATE);
        Claim=sp.getString(Params.spClaim,"-");
        fun=new Function(getActivity());

        fun.Actionbar_3();

        name=findViewById(R.id.user_more_name);
        shotdes=findViewById(R.id.user_more_shortdes);
        longdes=findViewById(R.id.user_more_longdes);
        userimage=findViewById(R.id.user_more_image);
        listtags=findViewById(R.id.user_more_tags_list);
        markicon=findViewById(R.id.user_more_markicon);
        menuicon=findViewById(R.id.user_more_menuicon);
        prog=findViewById(R.id.user_more_prog);
        mainscroll=findViewById(R.id.user_more_mainscroll);
        phone=findViewById(R.id.user_more_phone);

    }

    private Context getActivity(){
        return user_more.this;
    }

    private void get_full_user(){

        prog.setVisibility(View.VISIBLE);
        mainscroll.setVisibility(View.GONE);
        fun.show_debug_dialog(1,"{\"adviser_id\":\""+adid+"\"}", Params.ws_sadviser);
        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_sadviser,
                response -> {
                    fun.show_debug_dialog(2,response, Params.ws_sadviser);
                    prog.setVisibility(View.GONE);
                    mainscroll.setVisibility(View.VISIBLE);
                    fill_page(response);
                },
                error -> {

                    prog.setVisibility(View.GONE);
                    mainscroll.setVisibility(View.VISIBLE);
                    finish();
                })

        {
            @Override
            public byte[] getBody() {
                String str = "{\"adviser_id\":\""+adid+"\"}";
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

    private void set_mark_server(){
        shakeyoyo = YoYo.with(Techniques.Shake).duration(700).repeat(300).playOn(markicon);
        fun.show_debug_dialog(1,"{\"adviser_id\":\""+adid+"\"}", Params.ws_addadviser);
        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_addadviser,
                response -> {
                    fun.show_debug_dialog(2,response, Params.ws_addadviser);
                    shakeyoyo.stop();
                    if(response.equals("1")){
                        markicon.setIcon(MaterialDrawableBuilder.IconValue.BOOKMARK);
                    }else{
                        markicon.setIcon(MaterialDrawableBuilder.IconValue.BOOKMARK_OUTLINE);
                    }
                },
                error -> {
                    shakeyoyo.stop();
                })

        {

            @Override
            public byte[] getBody() {
                String str = "{\"adviser_id\":\""+adid+"\"}";
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

    private void fill_page(String res){

        try {

            JSONObject tt=new JSONObject(res);
            JSONObject info=new JSONObject(tt.getString("adviser_info"));
            JSONObject uinfo=new JSONObject(info.getString("user_info"));
            name.setText(uinfo.getString("name"));
            shotdes.setText(info.getString("field"));
            longdes.setText(info.getString("long_desc"));
            phone.setText("شماره تلفن مشاور: "+info.getString("mobile"));
            markicon.setVisibility(View.VISIBLE);
            if(uinfo.getInt("saved")==0){
                markicon.setIcon(MaterialDrawableBuilder.IconValue.BOOKMARK_OUTLINE);
            }else{
                markicon.setIcon(MaterialDrawableBuilder.IconValue.BOOKMARK);
            }

            markicon.setOnClickListener(v -> set_mark_server());

            menuicon.setOnClickListener(v -> {
                PowerMenu powerMenu = new PowerMenu.Builder(getActivity())
                        .addItem(new PowerMenuItem("بلاک"))
                        .addItem(new PowerMenuItem("بلاک و ریپورت"))
                        .setAnimation(MenuAnimation.SHOWUP_TOP_LEFT) // Animation start point (TOP | LEFT).
                        .setMenuRadius(10f) // sets the corner radius.
                        .setMenuShadow(10f) // sets the shadow.
                        .setTextColor(ContextCompat.getColor(getActivity(), R.color.siyah))
                        .setTextGravity(Gravity.RIGHT)
                        .setSelectedTextColor(Color.RED)
                        .setMenuColor(Color.WHITE)
                        .setSelectedMenuColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary))
                        .setOnMenuItemClickListener((position, item) -> {
                            if(position==0){
                                fun.block_adviser(adid);
                            }
                        })
                        .build();
                powerMenu.showAsDropDown(v);
            });


            Picasso
                    .get()
                    .load(Params.pic_adviseravatae+uinfo.getString("avatar"))
                    .error(R.drawable.nopic)
                    .into(userimage);

            load_tags(info.getString("tags"));

        } catch (JSONException e) {
            e.printStackTrace();
            prog.setVisibility(View.GONE);
            mainscroll.setVisibility(View.GONE);
            finish();
        }


    }




    private void load_tags(String res)  {

        JSONObject items = null;
        try {
            items = new JSONObject(res);
            final list_tags_adapter rva1=new list_tags_adapter(items);
            RtlGridLayoutManager layoutManager = new RtlGridLayoutManager(getActivity(), 5);
            listtags.setLayoutManager(layoutManager);
            listtags.setItemAnimator(new DefaultItemAnimator());
            listtags.setAdapter(rva1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }










}
