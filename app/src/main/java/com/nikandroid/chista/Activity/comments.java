package com.nikandroid.chista.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nikandroid.chista.Adapters.user_comments_adapter;
import com.nikandroid.chista.Functions.Function;
import com.nikandroid.chista.Params.Params;
import com.nikandroid.chista.R;
import com.nikandroid.chista.ViewModelFactory.CommentsModelFactory;
import com.nikandroid.chista.ViewModelFactory.MainModelFactory;
import com.nikandroid.chista.databinding.CommentsBinding;
import com.nikandroid.chista.databinding.MainBinding;
import com.nikandroid.chista.viewModels.CommentsViewModel;
import com.nikandroid.chista.viewModels.MainViewModel;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class comments extends AppCompatActivity {



    private String adid;
    private SharedPreferences sp;
    String Claim="";
    Function fun;
    RecyclerView list;
    ProgressBar prog;
    FloatingActionButton newcom;
    PowerMenu powerMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommentsBinding binding= DataBindingUtil.setContentView(this,R.layout.comments);
        binding.setCommentsModel(ViewModelProviders.of(this,new CommentsModelFactory(this)).get(CommentsViewModel.class));


        init();

        newcom.setOnClickListener(v -> {

            Intent i=new Intent(comments.this,new_comments.class);
            i.putExtra("adid",adid);
            startActivity(i);
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        get_comment();
    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));    }

    private Context getActivity(){
        return comments.this;
    }

    private void init(){

        sp=getSharedPreferences(Params.spmain,MODE_PRIVATE);
        fun=new Function(this);
        Claim=sp.getString(Params.spClaim,"-");


        adid=getIntent().getExtras().getString("adid");

        View actv=fun.Actionbar_4(R.layout.actionbar_main);


        TextView acttitle=actv.findViewById(R.id.actionbar_main_title);
        acttitle.setText("نظرات");
        MaterialIconView actback=actv.findViewById(R.id.actionbar_main_back);
        actback.setOnClickListener(v -> finish());


        list=findViewById(R.id.comments_list);
        prog=findViewById(R.id.comments_progress);
        newcom=findViewById(R.id.comments_newcomments);

    }

    private void get_comment(){

        list.setVisibility(View.GONE);
        prog.setVisibility(View.VISIBLE);
        fun.show_debug_dialog(2,"{\"adviser_id\":"+adid+"}", Params.ws_getcomment);
        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_getcomment,
                response -> {
                    fun.show_debug_dialog(2,response, Params.ws_getcomment);
                    list.setVisibility(View.VISIBLE);
                    prog.setVisibility(View.GONE);
                    make_list_comments(response);
                },
                error -> {})

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



    private void make_list_comments(String res)  {


        JSONObject items = null;
        try {
            items = new JSONObject(res);

            final user_comments_adapter rva1=new user_comments_adapter(items,getActivity(),fun);
            list.setLayoutManager(new LinearLayoutManager(getActivity()));
            list.setItemAnimator(new DefaultItemAnimator());
            list.setAdapter(rva1);

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }




}
