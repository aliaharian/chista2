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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.nikandroid.chista.Functions.Function;
import com.nikandroid.chista.Params.Params;
import com.nikandroid.chista.R;
import com.nikandroid.chista.ViewModelFactory.MainModelFactory;
import com.nikandroid.chista.ViewModelFactory.SettingModelFactory;
import com.nikandroid.chista.databinding.MainBinding;
import com.nikandroid.chista.databinding.SettingBinding;
import com.nikandroid.chista.viewModels.MainViewModel;
import com.nikandroid.chista.viewModels.SettingViewModel;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import net.steamcrafted.materialiconlib.MaterialIconView;

import es.dmoral.toasty.Toasty;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class setting extends AppCompatActivity {



    private SharedPreferences sp;
    String Claim="";
    Function fun;
    LinearLayout usersetting,chatsetting,notifsetting,advisermanage,commentsetting,clear_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SettingBinding binding= DataBindingUtil.setContentView(this,R.layout.setting);
        binding.setSettingModel(ViewModelProviders.of(this,new SettingModelFactory(this)).get(SettingViewModel.class));

        init();

        click();

    }


    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));    }


    private Context getActivity(){
        return setting.this;
    }


    private void init(){


        sp=getSharedPreferences(Params.spmain,MODE_PRIVATE);
        fun=new Function(this);
        Claim=sp.getString(Params.spClaim,"-");
        getSupportActionBar().hide();




        ImageView actback=findViewById(R.id.setting_actionbar_back);
        actback.setOnClickListener(v -> finish());


        usersetting=findViewById(R.id.setting_usersetting);
        chatsetting=findViewById(R.id.setting_chatsetting);
        notifsetting=findViewById(R.id.setting_notifsetting);
        advisermanage=findViewById(R.id.setting_manage_adviser);
        clear_history=findViewById(R.id.setting_clear_history);
        commentsetting=findViewById(R.id.setting_commentsetting);





    }


    private void click(){

        usersetting.setOnClickListener(v -> {
            Intent i=new Intent(getActivity(),user_edits.class);
            startActivity(i);

        });

        chatsetting.setOnClickListener(v -> {
            Intent i=new Intent(getActivity(),setting2.class);
            i.putExtra("key","chatsetting");
            startActivity(i);

        });

        notifsetting.setOnClickListener(v -> {
            Intent i=new Intent(getActivity(),setting2.class);
            i.putExtra("key","notifsetting");
            startActivity(i);

        });

        advisermanage.setOnClickListener(v -> {
            Intent i=new Intent(getActivity(),setting2.class);
            i.putExtra("key","advisermanager");
            startActivity(i);

        });

        commentsetting.setOnClickListener(v -> {
            Intent i=new Intent(getActivity(),setting2.class);
            i.putExtra("key","commentssetting");
            startActivity(i);
        });

        clear_history.setOnClickListener(v -> new SweetAlertDialog(getActivity(),SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("پاک کردن سوابق")
                .setContentText("آیا از پاک کردن سوابق جستجو مطمئن هستید؟")
                .setConfirmText("بله")
                .setCancelText("خیر")
                .setCancelClickListener(sweetAlertDialog -> sweetAlertDialog.dismiss())
                .setConfirmClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.dismiss();
                    SharedPreferences.Editor ed=sp.edit();
                    ed.remove(Params.spsearchhistory);
                    ed.commit();
                    Toasty.success(getActivity(),"سوابق جستجو با موفقیت خالی شد",Toasty.LENGTH_LONG).show();

                })
                .show());









    }


}
