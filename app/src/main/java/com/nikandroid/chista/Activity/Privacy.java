package com.nikandroid.chista.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nikandroid.chista.Functions.Function;
import com.nikandroid.chista.Params.Params;
import com.nikandroid.chista.R;
import com.nikandroid.chista.ViewModelFactory.PrivecyModelFactory;
import com.nikandroid.chista.ViewModelFactory.RulesModelFactory;
import com.nikandroid.chista.databinding.PrivacyBinding;
import com.nikandroid.chista.databinding.RulesBinding;
import com.nikandroid.chista.viewModels.PrivecyViewModel;
import com.nikandroid.chista.viewModels.RulesViewModel;

import net.steamcrafted.materialiconlib.MaterialIconView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class Privacy extends AppCompatActivity {

    private String adid;
    private SharedPreferences sp;
    String Claim="";
    Function fun;
    ProgressBar prog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PrivacyBinding binding= DataBindingUtil.setContentView(this,R.layout.privacy);
        binding.setPrivecyModel(ViewModelProviders.of(this,new PrivecyModelFactory(this)).get(PrivecyViewModel.class));

        init();
    }


    @Override
    protected void onResume() {
        super.onResume();
        get_privecy();
    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));    }

    private Context getActivity(){
        return Privacy.this;
    }

    private void init(){

        sp=getSharedPreferences(Params.spmain,MODE_PRIVATE);
        fun=new Function(this);
        Claim=sp.getString(Params.spClaim,"-");

        View actv=fun.Actionbar_4(R.layout.actionbar_main);

        TextView acttitle=actv.findViewById(R.id.actionbar_main_title);
        acttitle.setText("قوانین و مقررات");
        MaterialIconView actback=actv.findViewById(R.id.actionbar_main_back);
        actback.setOnClickListener(v -> finish());


        prog=findViewById(R.id.privecy_progress);

    }

    private void get_privecy(){

        prog.setVisibility(View.VISIBLE);

        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_getcomment,
                response -> {
                    prog.setVisibility(View.GONE);
                    make_list_privecy(response);
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

    }

    private void make_list_privecy(String res)  {


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
