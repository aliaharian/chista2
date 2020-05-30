package com.nikandroid.chista.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nikandroid.chista.Functions.Function;
import com.nikandroid.chista.Params.Params;
import com.nikandroid.chista.R;
import com.nikandroid.chista.ViewModelFactory.LoginModelFactory;
import com.nikandroid.chista.databinding.LoginBinding;
import com.nikandroid.chista.viewModels.LoginViewModel;
import com.skydoves.elasticviews.ElasticLayout;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class login extends AppCompatActivity {



    private EditText phonenumber;
    private EditText vcode1,vcode2,vcode3,vcode4;
    private ProgressBar progress,countdownprogress;
    private ElasticLayout next,next2,next3;
    private RequestQueue mRequestQueue;
    private ImageView icon;
    private String rolid="",uxid="";
    private TextView des,editphone,title,countdowntext;
    private LinearLayout phonearea,codearea,call_area;
    private String level="getphone",fullphone="",Vcode="",Claim="";
    private SharedPreferences sp;
    private Function fun;
    private CountDownTimer mtimer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LoginBinding binding= DataBindingUtil.setContentView(this,R.layout.login);
        binding.setLoginModel(ViewModelProviders.of(this,new LoginModelFactory(this)).get(LoginViewModel.class));

        init();

        if(level.equals("getphone")){
            get_phone_level();
        }else if(level.equals("getcode")){
            fullphone=sp.getString(Params.login_phone,"0000");
            get_code_level();
        }else if(level.equals("complete")){
            startActivity(new Intent(login.this, Main.class));
            finish();
        }



        editphone.setOnClickListener(v -> {
            mtimer.cancel();
            mtimer=null;
            phonenumber.setText("");
            SharedPreferences.Editor ed=sp.edit();
            ed.putString(Params.login_level,"getphone");
            ed.putString(Params.login_phone,"00");
            ed.commit();
            get_phone_level();
        });

        next3.setOnClickListener(v -> {
            SharedPreferences.Editor ed=sp.edit();
            ed.putString(Params.login_level,"getphone");
            ed.putString(Params.login_phone,"00");
            ed.commit();
            get_phone_level();
        });



    }


    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));    }



    private void init(){



        sp=getSharedPreferences(Params.spmain,MODE_PRIVATE);
        fun=new Function(login.this);
        fun.setwh();

        fun.Actionbar_1();



        phonenumber=findViewById(R.id.login_et_phonenumber);
        next=findViewById(R.id.login_btn_next);
        next2=findViewById(R.id.login_btn_next2);
        next3=findViewById(R.id.login_btn_next3);
        icon=findViewById(R.id.login_phone_logo);
        des=findViewById(R.id.login_tv_des);
        title=findViewById(R.id.login_tv_title);
        editphone=findViewById(R.id.login_tv_editphone);
        countdowntext=findViewById(R.id.login_countdown_text);

        phonearea=findViewById(R.id.login_ll_phonearea);
        codearea=findViewById(R.id.login_ll_codearea);
        call_area=findViewById(R.id.login_phone_call_area);

        vcode1=findViewById(R.id.login_et_vcode1);
        vcode2=findViewById(R.id.login_et_vcode2);
        vcode3=findViewById(R.id.login_et_vcode3);
        vcode4=findViewById(R.id.login_et_vcode4);
        //callphone=findViewById(R.id.login_call_phone);
        progress=findViewById(R.id.login_progress);
        countdownprogress=findViewById(R.id.login_countdown_progress);

        mRequestQueue = Volley.newRequestQueue(this);
        level=sp.getString(Params.login_level,"getphone");


    }

    private void get_phone_level(){

        des.setText("لطفا شماره همراه خود را وارد کنید");
        title.setText("ورود / ثبت نام");
        editphone.setVisibility(View.GONE);
        phonearea.setVisibility(View.VISIBLE);
        codearea.setVisibility(View.GONE);
        call_area.setVisibility(View.GONE);
        next.setVisibility(View.VISIBLE);
        next2.setVisibility(View.GONE);
        next3.setVisibility(View.GONE);
        phonenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(phonenumber.getText().toString().length()==11){
                    fullphone=phonenumber.getText().toString();
                    View view = getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
            }
        });
        next.setOnClickListener(v -> {
            if(phonenumber.getText().toString().length()==11){
                fullphone=phonenumber.getText().toString();
                send_phone();
            }else{
                Toasty.warning(getApplicationContext(),"شماره تماس اشتباه است",Toasty.LENGTH_LONG).show();
            }
        });
    }

    private void send_phone(){

        progress.setVisibility(View.VISIBLE);
        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_codesend,
                response -> {
                    progress.setVisibility(View.GONE);
                    try {
                        JSONObject obj = new JSONObject(response);
                        if(obj.getString("message").equals("OK") && obj.getString("responseCode").equals("200")){
                          //  Toasty.success(getApplicationContext(),"پیام ارسال شد",Toasty.LENGTH_LONG).show();
                            SharedPreferences.Editor ed=sp.edit();
                            ed.putString(Params.login_level,"getcode");
                            ed.putString(Params.login_phone,fullphone);
                            ed.commit();
                            get_code_level();
                        }
                    } catch (Exception e) {
                        Toasty.warning(getApplicationContext(), e.toString(),Toasty.LENGTH_LONG).show();
                    }
                },
                error -> {
                    progress.setVisibility(View.GONE);
                    Toasty.error(getApplicationContext(),"خطا در اتصال به اینترنت",Toasty.LENGTH_LONG).show();
                })
        {

            @Override
            public byte[] getBody() {
                String str = "{\"username\":\""+fullphone+"\",\"uxId\":\"2701\",\"roleId\":\"2202\"}";
                return str.getBytes();
            };

            public String getBodyContentType()
            {
                return "application/json; charset=utf-8";
            }
            @Override
            protected  Map<String, String> getParams()  throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        Volley.newRequestQueue(login.this).add(rq);
    }

    private void send_phone_forcall(){
        progress.setVisibility(View.VISIBLE);
        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_callsend,
                response -> {
                    progress.setVisibility(View.GONE);
                    try {
                        JSONObject obj = new JSONObject(response);
                        if(obj.getString("message").equals("OK") && obj.getString("responseCode").equals("200")){
                            //status.setText("درخواست شما ثبت شد. به زودی با شماره "+fullphone+"تماس گرفته میشود");
                            editphone.setOnClickListener(v -> {
                            });
                                SharedPreferences.Editor ed=sp.edit();
                                ed.putString(Params.login_level,"getcode");
                                ed.putString(Params.login_phone,fullphone);
                                ed.commit();
                        }else{
                           // status.setText("ّبروز خطا در ثبت درخواست شما");
                        }
                    } catch (Exception e) {
                        Toasty.warning(getApplicationContext(), e.toString(),Toasty.LENGTH_LONG).show();
                    }
                },
                error -> {
                    Log.e("call",error.toString());
                    progress.setVisibility(View.GONE);
                    Toasty.error(getApplicationContext(),"خطا در اتصال به اینترنت",Toasty.LENGTH_LONG).show();
                })
        {
            @Override
            public byte[] getBody() throws com.android.volley.AuthFailureError {
                String str = "{\"username\":\""+fullphone+"\",\"uxId\":\"2701\",\"roleId\":\"2202\"}";
                return str.getBytes();
            };
            public String getBodyContentType()
            {
                return "application/json; charset=utf-8";
            }
            @Override
            protected  Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        Volley.newRequestQueue(login.this).add(rq);
    }

    private void get_code_level(){

        //icon.setImageResource(R.drawable.phone_sms_logo2);
        title.setText("تاییدیه پیامکی");
        des.setText("کد ارسال شده به "+fullphone+" را وارد کنید.");
        editphone.setVisibility(View.VISIBLE);


        call_area.setOnClickListener(v -> send_phone_forcall());
        call_area.setVisibility(View.VISIBLE);
        next.setVisibility(View.GONE);
        next2.setVisibility(View.VISIBLE);
        next3.setVisibility(View.GONE);
        phonearea.setVisibility(View.GONE);
        codearea.setVisibility(View.VISIBLE);

        vcode1.setText("");
        vcode2.setText("");
        vcode3.setText("");
        vcode4.setText("");


        start_countdown();


        vcode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(vcode1.getText().toString().length()==1){
                    vcode1.setBackgroundResource(R.drawable.login_edittext_code);
                    vcode2.requestFocus();
                }
            }
        });

        vcode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(vcode2.getText().toString().length()==1){
                    vcode2.setBackgroundResource(R.drawable.login_edittext_code);
                    vcode3.requestFocus();
                }
            }
        });

        vcode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(vcode3.getText().toString().length()==1){
                    vcode3.setBackgroundResource(R.drawable.login_edittext_code);
                    vcode4.requestFocus();
                }
            }
        });


        vcode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(vcode4.getText().toString().length()==1){
                    vcode4.setBackgroundResource(R.drawable.login_edittext_code);
                    if(vcode1.getText().toString().length()==1 && vcode2.getText().toString().length()==1 && vcode3.getText().toString().length()==1 && vcode4.getText().toString().length()==1){

                        View view = getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }

                        Vcode=vcode1.getText().toString()+vcode2.getText().toString()+vcode3.getText().toString()+vcode4.getText().toString();
                        send_vcode();
                    }else{
                        next.requestFocus();
                    }
                }
            }
        });

        next.setOnClickListener(v -> {
            if(vcode1.getText().toString().length()==1 && vcode2.getText().toString().length()==1 && vcode3.getText().toString().length()==1 && vcode4.getText().toString().length()==1){
                Vcode=vcode1.getText().toString()+vcode2.getText().toString()+vcode3.getText().toString()+vcode4.getText().toString();
                send_vcode();
            }else{
                Toasty.warning(getApplicationContext(), "کدها را وارد کنید",Toasty.LENGTH_LONG).show();
            }

        });


    }

    private void send_vcode(){

        progress.setVisibility(View.VISIBLE);
        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_codeverify,
                response -> {
                    progress.setVisibility(View.GONE);
                    try {
                        JSONObject obj = new JSONObject(response);
                        if(obj.getString("message").equals("true") && obj.getString("responseCode").equals("200")){
                            send_add_info();
                        }
                    } catch (Throwable t) {
                        Toasty.warning(getApplicationContext(),"ّخطا در تایید کد1",Toasty.LENGTH_LONG).show();
                    }
                },
                error -> {
                    progress.setVisibility(View.GONE);
                    Toasty.warning(getApplicationContext(),"ّخطا در تایید کد2",Toasty.LENGTH_LONG).show();
                })
        {
            @Override
            public byte[] getBody() {
                String str = "{\"username\":\""+fullphone+"\",\"validationCode\":\""+Vcode+"\",\"uxId\":\"2701\",\"roleId\":\"2202\"}";
                return str.getBytes();
            };
            public String getBodyContentType()
            {
                return "application/json; charset=utf-8";
            }
            @Override
            protected  Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                Map<String, String> responseHeaders = response.headers;
                String rawCookies = responseHeaders.get("Set-Cookie");
                Claim=rawCookies;
                return super.parseNetworkResponse(response);
            }
        };
        Volley.newRequestQueue(login.this).add(rq);

    }

    private void send_add_info(){
        progress.setVisibility(View.VISIBLE);
        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_add_nfo,
                response -> {
                    progress.setVisibility(View.GONE);
                    try {
                        JSONObject obj = new JSONObject(response);
                        if(obj.getString("message").equals("success") ){
                            SharedPreferences.Editor ed=sp.edit();
                            ed.putString(Params.login_data,response);
                            ed.putString(Params.spClaim,Claim);
                            ed.putString(Params.login_level,"complete");
                            ed.commit();
                            startActivity(new Intent(login.this, Main.class));
                            finish();
                           // Toasty.success(getApplicationContext(),"خوش آمدید",Toasty.LENGTH_LONG).show();
                        }else{
                            Toasty.warning(getApplicationContext(),"خطا در لاگین",Toasty.LENGTH_LONG).show();
                            Toasty.warning(getApplicationContext(),response,Toasty.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toasty.warning(getApplicationContext(),e.toString(),Toasty.LENGTH_LONG).show();
                    }
                },
                error -> {
                    progress.setVisibility(View.GONE);
                    Toasty.warning(getApplicationContext(),"ّخطا در تایید کد3",Toasty.LENGTH_LONG).show();
                })
        {
            @Override
            public byte[] getBody() {
                String str = "{\"mobile\":\""+fullphone+"\",\"uxId\":\"2701\",\"roleId\":\"2202\"}";
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
        Volley.newRequestQueue(login.this).add(rq);
    }



    private void start_countdown() {

        if (mtimer != null) {
            mtimer = null;
        }
            final int[] i = {120};

            countdownprogress.setProgress(i[0]);
            mtimer = new CountDownTimer(120000, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    i[0]--;
                    countdownprogress.setProgress(i[0]);
                    String t = "";
                    if (i[0] >= 60) {
                        t = "01:" + (i[0] - 60);
                    } else {
                        t = "00:" + i[0];
                    }
                    countdowntext.setText(t);
                }

                @Override
                public void onFinish() {
                    //Do what you want
                    i[0]++;
                    countdownprogress.setProgress(0);

                    next2.setVisibility(View.GONE);
                    next.setVisibility(View.GONE);
                    next3.setVisibility(View.VISIBLE);


                }
            };
            mtimer.start();


    }


}
