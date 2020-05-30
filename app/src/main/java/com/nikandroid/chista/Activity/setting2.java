package com.nikandroid.chista.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.nikandroid.chista.Functions.Function;
import com.nikandroid.chista.Params.Params;
import com.nikandroid.chista.R;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconView;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class setting2 extends AppCompatActivity {

    private SharedPreferences sp;
    String Claim="";
    Function fun;

    TextView actlabel;
    LinearLayout item1,item2,item3,item4,item5,item6;
    TextView item1label,item2label,item3label,item4label,item5label,item6label;
    MaterialIconView item6icon,item5icon2,item6icon2;
    Switch item1sw,item2sw,item3sw,item4sw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting2);
        init();

        make_menus(getIntent().getExtras().getString("key"));

    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));    }

    private Context getActivity(){
        return setting2.this;
    }

    private void init(){

        sp=getSharedPreferences(Params.spmain,MODE_PRIVATE);
        fun=new Function(this);
        Claim=sp.getString(Params.spClaim,"-");

        getSupportActionBar().hide();




        actlabel=findViewById(R.id.setting2_title);
        actlabel.setText("");
        ImageView actback=findViewById(R.id.setting2_actionbar_back);
        actback.setOnClickListener(v -> finish());



        item1=findViewById(R.id.setting2_item1);
        item2=findViewById(R.id.setting2_item2);
        item3=findViewById(R.id.setting2_item3);
        item4=findViewById(R.id.setting2_item4);
        item5=findViewById(R.id.setting2_item5);
        item6=findViewById(R.id.setting2_item6);

        item1label=findViewById(R.id.setting2_item1_name);
        item2label=findViewById(R.id.setting2_item2_name);
        item3label=findViewById(R.id.setting2_item3_name);
        item4label=findViewById(R.id.setting2_item4_name);
        item5label=findViewById(R.id.setting2_item5_name);
        item6label=findViewById(R.id.setting2_item6_name);


        item6icon=findViewById(R.id.setting2_item6_icon);
        item5icon2=findViewById(R.id.setting2_item5_icon2);
        item6icon2=findViewById(R.id.setting2_item6_icon2);


        item1sw=findViewById(R.id.setting2_item1_sw);
        item2sw=findViewById(R.id.setting2_item2_sw);
        item3sw=findViewById(R.id.setting2_item3_sw);
        item4sw=findViewById(R.id.setting2_item4_sw);


    }

    private void make_menus(String flag){

        if(flag.equals("chatsetting")){

            actlabel.setText("تنظیمات چت تصویری");
            item1label.setText("حالت ذخیره اینترنت");

            item2label.setText("عدم نمایش تصویر من در چت");

            item3label.setText("نمایش تایمر تماس به صورت ثابت");

            item4.setVisibility(View.GONE);
            item5.setVisibility(View.GONE);
            item6.setVisibility(View.GONE);

        }

        if(flag.equals("notifsetting")){

            actlabel.setText("تنظیمات نوتیفیکیشن");

            item1label.setText("غیر فعال کردن نوتیفیکیشن ها");

            item2label.setText("نوتیفیکیشن های چت");

            item3label.setText("نوتیفیکیشن اطلاعیه ها");

            item4.setVisibility(View.GONE);
            item5.setVisibility(View.GONE);
            item6.setVisibility(View.GONE);

        }

        if(flag.equals("commentssetting")){

            actlabel.setText("تنظیمات ثبت نظر");

            item1label.setText("نمایش نوتیفیکیشن پاسخ به نظر من");

            item2label.setText("پخش صدای پاسخ به نظر من");

            item3label.setText("لرزش در پاسخ به نظر من");

            item4label.setText("ارسال به صورت ناشناس");

            String uc=sp.getString(Params.sp_unknowcomment,"0");
            if(uc.equals("1")){
                item4sw.setChecked(true);
            }else{
                item4sw.setChecked(false);
            }

            item4sw.setOnClickListener(v -> fun.set_unknow_comment(item4sw));
            item4label.setOnClickListener(v -> fun.set_unknow_comment(item4sw));


            item5.setVisibility(View.GONE);
            item6.setVisibility(View.GONE);

        }

        if(flag.equals("advisermanager")){

            actlabel.setText("مدیریت مشاوران");

            item5label.setText("مشاوران من");
            item5.setOnClickListener(v -> {
                Intent i=new Intent(getActivity(),adviser_list.class);
                i.putExtra("flag","myadv");
                startActivity(i);

            });

            item6label.setText("مشاوران بلاک شده");
            item6icon.setIcon(MaterialDrawableBuilder.IconValue.BLOCK_HELPER);
            item6.setOnClickListener(v -> {
                Intent i=new Intent(getActivity(),adviser_list.class);
                i.putExtra("flag","block");
                startActivity(i);
            });

            item1.setVisibility(View.GONE);
            item2.setVisibility(View.GONE);
            item3.setVisibility(View.GONE);
            item4.setVisibility(View.GONE);

        }

    }


}
