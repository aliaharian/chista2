package com.nikandroid.chista.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

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
import com.nikandroid.chista.ViewModelFactory.HelpsModelFactory;
import com.nikandroid.chista.databinding.HelpsBinding;
import com.nikandroid.chista.viewModels.HelpsViewModel;

import net.steamcrafted.materialiconlib.MaterialIconView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class Helps extends AppCompatActivity {

    private String adid;
    private SharedPreferences sp;
    String Claim="";
    Function fun;
    RecyclerView list;
    ProgressBar prog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HelpsBinding binding= DataBindingUtil.setContentView(this,R.layout.helps);
        binding.setHelpsModel(ViewModelProviders.of(this,new HelpsModelFactory(this)).get(HelpsViewModel.class));

        init();
    }


    @Override
    protected void onResume() {
        super.onResume();
        get_helps();
    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));    }

    private Context getActivity(){
        return Helps.this;
    }

    private void init(){

        sp=getSharedPreferences(Params.spmain,MODE_PRIVATE);
        fun=new Function(this);
        Claim=sp.getString(Params.spClaim,"-");



        View actv=fun.Actionbar_4(R.layout.actionbar_main);


        TextView acttitle=actv.findViewById(R.id.actionbar_main_title);
        acttitle.setText("راهنما");
        MaterialIconView actback=actv.findViewById(R.id.actionbar_main_back);
        actback.setOnClickListener(v -> finish());


        list=findViewById(R.id.helps_list);
        prog=findViewById(R.id.helps_progress);

    }

    private void get_helps(){

        list.setVisibility(View.GONE);
        prog.setVisibility(View.VISIBLE);

        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_getcomment,
                response -> {
                    list.setVisibility(View.VISIBLE);
                    prog.setVisibility(View.GONE);
                    make_list_helps(response);
                },
                error -> {
                    list.setVisibility(View.VISIBLE);
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
