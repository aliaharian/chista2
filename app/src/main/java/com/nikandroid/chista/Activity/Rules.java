package com.nikandroid.chista.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nikandroid.chista.Functions.Function;
import com.nikandroid.chista.Params.Params;
import com.nikandroid.chista.R;
import com.nikandroid.chista.ViewModelFactory.HelpsModelFactory;
import com.nikandroid.chista.ViewModelFactory.RulesModelFactory;
import com.nikandroid.chista.databinding.HelpsBinding;
import com.nikandroid.chista.databinding.RulesBinding;
import com.nikandroid.chista.viewModels.HelpsViewModel;
import com.nikandroid.chista.viewModels.RulesViewModel;

import net.steamcrafted.materialiconlib.MaterialIconView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class Rules extends AppCompatActivity {

    private String adid;
    private SharedPreferences sp;
    String Claim="";
    Function fun;
    ProgressBar prog;
    String flag="";
    TextView title;
    ImageView mainicon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RulesBinding binding= DataBindingUtil.setContentView(this,R.layout.rules);
        binding.setRulesModel(ViewModelProviders.of(this,new RulesModelFactory(this)).get(RulesViewModel.class));

        flag=getIntent().getExtras().getString("flag");
        init();


    }

    @Override
    protected void onResume() {
        super.onResume();
        get_rules();
    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));    }

    private Context getActivity(){
        return Rules.this;
    }

    private void init(){

        sp=getSharedPreferences(Params.spmain,MODE_PRIVATE);
        fun=new Function(this);
        Claim=sp.getString(Params.spClaim,"-");
        getSupportActionBar().hide();
        ImageView actback=findViewById(R.id.rules_actionbar_back);
        actback.setOnClickListener(v -> finish());

        prog=findViewById(R.id.rules_progress);
        title=findViewById(R.id.rules_actionbar_title);
        mainicon=findViewById(R.id.rules_mainicon);

        if(flag.equals("rules")){
            title.setText("قوانین و مقررات");
            mainicon.setImageResource(R.drawable.main_icon_rules);
        }else{
            title.setText("حریم خصوصی");
            mainicon.setImageResource(R.drawable.main_icon_privacy);
        }



    }

    private void get_rules(){
/*
        prog.setVisibility(View.VISIBLE);

        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_getcomment,
                response -> {
                    prog.setVisibility(View.GONE);
                    make_list_helps(response);
                },
                error -> {
                    prog.setVisibility(View.GONE);
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
*/
    }

    private void make_list_helps(String res)  {


        JSONObject items = null;
        try {
            items = new JSONObject(res);
/*
            final user_comments_adapter rva1=new user_comments_adapter(items,getActivity(),fun);
            list.setLayoutManager(new LinearLayoutManager(getActivity()));
            list.setItemAnimator(new DefaultItemAnimator());
            list.setAdapter(rva1);
*/
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }




}
