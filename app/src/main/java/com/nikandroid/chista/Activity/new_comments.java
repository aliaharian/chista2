package com.nikandroid.chista.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fuzzproductions.ratingbar.RatingBar;
import com.nikandroid.chista.Functions.Function;
import com.nikandroid.chista.Params.Params;
import com.nikandroid.chista.R;
import com.skydoves.elasticviews.ElasticButton;
import com.skydoves.elasticviews.ElasticImageView;
import com.skydoves.elasticviews.ElasticLayout;

import net.steamcrafted.materialiconlib.MaterialIconView;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class new_comments extends AppCompatActivity {


    private String adid;
    private SharedPreferences sp;
    String Claim="";
    Function fun;
    TextView name;
    EditText cmtext;
    ElasticLayout submit,cancel;
    RatingBar rating;
    ProgressBar prog;
    String fullname="";
    TextView name4,ratingname;
    ElasticImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_comments);


        init();

        clicks();


    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));    }

    private Context getActivity(){
        return new_comments.this;
    }

    private void init(){

        sp=getSharedPreferences(Params.spmain,MODE_PRIVATE);
        Claim=sp.getString(Params.spClaim,"-");
        fun=new Function(getActivity());


        getSupportActionBar().hide();
        final Window window = getWindow();
        try {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } catch (Exception e) {
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.sefid));
        }

        cmtext=findViewById(R.id.newcomment_textcm);
        submit=findViewById(R.id.newcomment_submit);
        cancel=findViewById(R.id.newcomment_cancele);
        rating=findViewById(R.id.newcomment_stars);
        prog=findViewById(R.id.newcomment_prog);
        name4=findViewById(R.id.newcomment_title);
        ratingname=findViewById(R.id.newcomment_starstitle);
        back=findViewById(R.id.new_comments_back);

        adid=getIntent().getExtras().getString("adid");
        fullname=getIntent().getExtras().getString("name");

        name4.setText("به "+fullname+" نظر می دهم");

        back.setOnClickListener(v -> {
            finish();
        });

    }

    private void clicks(){




        rating.setOnRatingBarChangeListener((ratingBar, ratin, fromUser) -> {
            if(fromUser){

                //Toast.makeText(getApplicationContext(),ratin+"",Toast.LENGTH_LONG).show();
                if(ratin<=4f ){
               //     rating.setRating(1);
               // }else{
                    ratingBar.setRating(ratin+1.0f);
                }
                set_name_rating(ratingBar.getRating());
            }

        });


        submit.setOnClickListener(v -> {
            if(cmtext.getText().toString().length()>5){
                if(rating.getRating()==0){
                    Toasty.error(getActivity(),"لطفا ستاره دهید",Toasty.LENGTH_LONG).show();
                }else {
                    set_com(cmtext.getText().toString(),(int)rating.getRating());
                }
            }else{
                Toasty.error(getActivity(),"متن نظر کوتاه است",Toasty.LENGTH_LONG).show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void set_name_rating(float rating){

        if(rating>4 && rating<=5){
            ratingname.setText("عالی");
        }
        if(rating>3 && rating<=4){
            ratingname.setText("خوب");
        }
        if(rating>2 && rating<=3){
            ratingname.setText("متوسط");
        }
        if(rating>1 && rating<=2){
            ratingname.setText("ضعیف");
        }
        if(rating>0 && rating<=1){
            ratingname.setText("خیلی ضعیف");
        }


    }



    private void set_com(final String commenttext, final int starcount){

        prog.setVisibility(View.VISIBLE);
        submit.setEnabled(false);
        cmtext.setEnabled(false);
        fun.show_debug_dialog(1,"{\"adviser_id\":"+adid+",\"star\":"+starcount+",\"text\":\""+commenttext+"\"}", Params.ws_setcomment);
        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_setcomment,
                response -> {
                    fun.show_debug_dialog(2,response, Params.ws_setcomment);
                    prog.setVisibility(View.GONE);
                    if (response.length()>10){
                        Toasty.success(getActivity(),"نظر شما با موفقیت ثبت شد",Toasty.LENGTH_LONG).show();
                        finish();
                    }else{
                        submit.setEnabled(true);
                        cmtext.setEnabled(true);
                        Toasty.error(getActivity(),"خطا در ثبت نظر",Toasty.LENGTH_LONG).show();

                    }
                },
                error -> {
                    submit.setEnabled(true);
                    cmtext.setEnabled(true);
                    Toasty.error(getActivity(),"خطا در ثبت نظر",Toasty.LENGTH_LONG).show();
                })

        {

            @Override
            public byte[] getBody() {
                String str = "{\"adviser_id\":"+adid+",\"star\":"+starcount+",\"text\":\""+commenttext+"\"}";
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

}
