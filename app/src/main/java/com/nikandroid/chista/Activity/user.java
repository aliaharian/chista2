package com.nikandroid.chista.Activity;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Bundle;
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
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.fuzzproductions.ratingbar.RatingBar;
import com.nikandroid.chista.Adapters.user_comments_adapter;
import com.nikandroid.chista.Adapters.user_gallery_adapter;
import com.nikandroid.chista.Adapters.user_similar_adapter;
import com.nikandroid.chista.Functions.Function;
import com.nikandroid.chista.Params.Params;
import com.nikandroid.chista.R;

import com.nikandroid.chista.TagGroup;
import com.nikandroid.chista.ViewModelFactory.UserModelFctory;
import com.nikandroid.chista.databinding.UserBinding;
import com.nikandroid.chista.viewModels.UserViewModel;
import com.sasank.roundedhorizontalprogress.RoundedHorizontalProgressBar;
import com.skydoves.elasticviews.ElasticImageView;
import com.skydoves.elasticviews.ElasticLayout;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;
import com.squareup.picasso.Picasso;
import com.taufiqrahman.reviewratings.BarLabels;
import com.taufiqrahman.reviewratings.RatingReviews;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class user extends AppCompatActivity {

    private String adid="";
    private SharedPreferences sp;
    private TextView name,shotdes,star,usercity,followers,activityhr,longdes,cubstarcount,seemore,mainscore,maincommntcount;
    private ImageView userimage,userstatus;
    String Claim="";
    RecyclerView cmlist;
    RecyclerView gallerylist,similralist;
    Function fun;
    ProgressBar prog;
    MaterialIconView markicon,menuicon;
    ElasticLayout newcomment,newcomment1,call_offline,call_online;
    ElasticImageView btnback;
    ScrollView mainscroll;
    YoYo.YoYoString shakeyoyo;
    LinearLayout cubcomment;
    PowerMenu powerMenu1;
    RatingBar mainrating;
    RoundedHorizontalProgressBar p1,p2,p3,p4,p5;
    LinearLayout nocommentsarea,commentsarea;



    public static boolean getcovetvideo=false;
    public static Bitmap videocover;
    TagGroup tags;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.user);
        UserBinding binding= DataBindingUtil.setContentView(this,R.layout.user);
        binding.setUserModel(ViewModelProviders.of(this,new UserModelFctory(this)).get(UserViewModel.class));

        init();

        fun.Actionbar_3();

        get_full_user();

    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));    }

    private void init(){

        sp=getSharedPreferences(Params.spmain,MODE_PRIVATE);
        Claim=sp.getString(Params.spClaim,"-");
        fun=new Function(getActivity());

        adid=getIntent().getExtras().getString("adid");

        name=findViewById(R.id.user_name);
        shotdes=findViewById(R.id.user_shortdes);
        usercity=findViewById(R.id.user_city);
        star=findViewById(R.id.user_star);
        followers=findViewById(R.id.user_followers);
        activityhr=findViewById(R.id.user_activity);
        longdes=findViewById(R.id.user_longdes);
        userimage=findViewById(R.id.user_image);
        cmlist=findViewById(R.id.user_commentarea_list);
        gallerylist=findViewById(R.id.user_gallery_list);
        similralist=findViewById(R.id.user_similar_list);
        markicon=findViewById(R.id.user_markicon);
        menuicon=findViewById(R.id.user_menuicon);
        prog=findViewById(R.id.user_prog);
        mainscroll=findViewById(R.id.user_mainscroll);
        cubcomment=findViewById(R.id.user_cub_comments);
        cubstarcount=findViewById(R.id.user_cubscore_commentcount);
        seemore=findViewById(R.id.user_btn_seemore);
        mainscore=findViewById(R.id.user_mainscore);
        mainrating=findViewById(R.id.user_mainstar);
        maincommntcount=findViewById(R.id.user_maincommentscount);
        newcomment=findViewById(R.id.user_btn_newcomment);
        newcomment1=findViewById(R.id.user_btn_newcomment1);
        btnback=findViewById(R.id.user_btn_back);
        userstatus=findViewById(R.id.user_main_onlinestatus);

        nocommentsarea=findViewById(R.id.user_commentsarea_nocomments);
        commentsarea=findViewById(R.id.user_commentsarea_bycomments);
        tags=findViewById(R.id.user_tags);

        p1=findViewById(R.id.user_ratingbar_p1);
        p2=findViewById(R.id.user_ratingbar_p2);
        p3=findViewById(R.id.user_ratingbar_p3);
        p4=findViewById(R.id.user_ratingbar_p4);
        p5=findViewById(R.id.user_ratingbar_p5);

        call_offline=findViewById(R.id.user_btn_call_offline);
        call_online=findViewById(R.id.user_btn_call_online);

    }

    private Context getActivity(){
        return user.this;
    }

    private void get_full_user(){

        prog.setVisibility(View.VISIBLE);
        mainscroll.setVisibility(View.GONE);
        mainscroll.fullScroll(ScrollView.FOCUS_UP);
        fun.show_debug_dialog(1,"{\"adviser_id\":\""+adid+"\"}", Params.ws_sadviser);
        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_sadviser ,
                response -> {
                    Log.e("full_user",response);
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

        Volley.newRequestQueue(user.this).add(rq);

    }

    private void set_mark_server(){

        shakeyoyo = YoYo.with(Techniques.Shake).duration(700).repeat(300).playOn(markicon);
        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_addadviser,
                response -> {
                    shakeyoyo.stop();
                    if(response.equals("1")){
                        Main.doact="shurefresh";
                        Main.tabc=2;
                        markicon.setIcon(MaterialDrawableBuilder.IconValue.BOOKMARK);
                    }else{
                        Main.doact="shurefresh";
                        Main.tabc=2;
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
        Volley.newRequestQueue(user.this).add(rq);


    }

    private void fill_page(String res){

        try {





            JSONObject tt=new JSONObject(res);
            JSONObject info=new JSONObject(tt.getString("adviser_info"));
            JSONObject uinfo=new JSONObject(info.getString("user_info"));

            name.setText(uinfo.getString("full_name"));
            shotdes.setText(info.getString("field"));
            usercity.setText(uinfo.getString("city"));
            followers.setText(fun.farsi_sazi_adad(info.getString("comments_count"))+" نفر" );
            activityhr.setText(fun.farsi_sazi_adad(info.getString("chat_time"))+" ساعت" );
            mainscore.setText(fun.farsi_sazi_adad(info.getString("star")));
            maincommntcount.setText("("+"از"+fun.farsi_sazi_adad(info.getString("comments_count"))+"نظر"+")");


            if(uinfo.getString("is_online").equals("2")){
                userstatus.setImageResource(R.drawable.z_circle_busy);
                call_online.setVisibility(View.GONE);
                call_offline.setVisibility(View.VISIBLE);
            }else if(uinfo.getString("is_online").equals("1")){
                userstatus.setImageResource(R.drawable.z_circle_online);
                call_online.setVisibility(View.VISIBLE);
                call_offline.setVisibility(View.GONE);
            }else{
                userstatus.setImageResource(R.drawable.z_circle_offline);
                call_online.setVisibility(View.GONE);
                call_offline.setVisibility(View.VISIBLE);
            }


////////////////////////////

            cubstarcount.setText(fun.farsi_sazi_adad(info.getString("comments_count"))+" نظر " );

            star.setText(info.getString("star"));

            longdes.setText(info.getString("long_desc"));

            markicon.setVisibility(View.VISIBLE);
            if(uinfo.getInt("saved")==0){
                markicon.setIcon(MaterialDrawableBuilder.IconValue.BOOKMARK_OUTLINE);
            }else{
                markicon.setIcon(MaterialDrawableBuilder.IconValue.BOOKMARK);
            }





            if(info.getString("comments_count").equals("0")){
                nocommentsarea.setVisibility(View.VISIBLE);
                commentsarea.setVisibility(View.GONE);
            }else{
                nocommentsarea.setVisibility(View.GONE);
                commentsarea.setVisibility(View.VISIBLE);
            }


            ///////////////////////////////TAGS////////////////////////////////
            JSONObject tagg=new JSONObject(info.getString("tags"));
            String[] tagtmp=new String[tagg.length()];
            for(int i=0;i<tagtmp.length;i++){
                tagtmp[i]=tagg.getString(i+"");
            }
            tags.setGravity(TagGroup.TagGravity.RIGHT);
            tags.setTags(tagtmp);
            /////////////////////////////////////////////////////////////////



            cubcomment.setOnClickListener(v -> {
                /*
                Intent i=new Intent(user.this,comments.class);
                i.putExtra("adid",adid);
                startActivity(i);*/
            });

            if(longdes.getLineCount()<5){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    longdes.setForeground(null);
                }
                seemore.setVisibility(View.GONE);
            }


            seemore.setOnClickListener(v -> {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    longdes.setForeground(null);
                }
                longdes.setMaxLines(100);
                seemore.setVisibility(View.GONE);

            });




            btnback.setOnClickListener(v -> {
                finish();
            });

            newcomment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        Intent i=new Intent(user.this,new_comments.class);
                        i.putExtra("adid",adid);
                        i.putExtra("name",uinfo.getString("full_name"));
                        startActivity(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
            newcomment1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(user.this,new_comments.class);
                    i.putExtra("adid",adid);
                    startActivity(i);
                }
            });


            markicon.setOnClickListener(v -> set_mark_server());

            menuicon.setOnClickListener(v -> {
                powerMenu1 = new PowerMenu.Builder(getActivity())
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
                        .setOnMenuItemClickListener(new OnMenuItemClickListener<PowerMenuItem>() {
                            @Override
                            public void onItemClick(int position, PowerMenuItem item) {
                                if(position==0) {
                                    powerMenu1.dismiss();
                                    fun.block_adviser(adid);
                                }else{
                                    powerMenu1.dismiss();
                                }
                            }
                        })
                        .build();
                powerMenu1.showAsDropDown(v);
            });

            Picasso
                    .get()
                    .load(Params.pic_adviseravatae+uinfo.getString("avatar"))
                    .error(R.drawable.nopic)
                    .into(userimage);



            JSONObject scorebord=new JSONObject(info.getString("score_board"));

            p1.setProgress(scorebord.getInt("1"));
            p2.setProgress(scorebord.getInt("2"));
            p3.setProgress(scorebord.getInt("3"));
            p4.setProgress(scorebord.getInt("4"));
            p5.setProgress(scorebord.getInt("5"));



            load_comments(info.getString("comments"));
            Log.e("gallery",info.getString("gallery"));
            load_gallery(info.getString("gallery"));
            load_similar(info.getString("similar"));
            mainscroll.fullScroll(ScrollView.FOCUS_UP);

        } catch (JSONException e) {
            e.printStackTrace();
            prog.setVisibility(View.GONE);
            mainscroll.setVisibility(View.GONE);
            finish();
        }


    }


    private void load_comments(String res)  {

        cmlist.setNestedScrollingEnabled(false);
        JSONObject items = null;
        try {
            items = new JSONObject(res);
            final user_comments_adapter rva1=new user_comments_adapter(items,getActivity(),fun);
            cmlist.setLayoutManager(new LinearLayoutManager(getActivity()));
            cmlist.setItemAnimator(new DefaultItemAnimator());
            cmlist.setAdapter(rva1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    private void load_gallery(String res)  {

        JSONObject items = null;
        try {
            items = new JSONObject(res);


            JSONObject endpart=new JSONObject();
            endpart.put("id","0");
            endpart.put("adviser_id","0");
            endpart.put("file_id","0");
            endpart.put("type","image");
            endpart.put("created_at","0");
            endpart.put("updated_at","0");
            endpart.put("file_path","end");


            items.put(items.length()+"",endpart);

            final user_gallery_adapter rva1=new user_gallery_adapter(items,res,getActivity(),fun);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);
            gallerylist.setLayoutManager(layoutManager);
            gallerylist.setItemAnimator(new DefaultItemAnimator());
            gallerylist.setAdapter(rva1);
            gallerylist.post(new Runnable() {
                @Override
                public void run() {
                    mainscroll.fullScroll(ScrollView.FOCUS_UP);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void load_similar(String res)  {

        JSONObject items = null;
        try {
            items = new JSONObject(res);


            final user_similar_adapter rva1=new user_similar_adapter(items,res,getActivity(),fun);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);
            similralist.setLayoutManager(layoutManager);
            similralist.setItemAnimator(new DefaultItemAnimator());
            similralist.setAdapter(rva1);
            mainscroll.fullScroll(ScrollView.FOCUS_UP);

            similralist.post(new Runnable() {
                @Override
                public void run() {
                    mainscroll.fullScroll(ScrollView.FOCUS_UP);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }








}
