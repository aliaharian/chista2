package com.nikandroid.chista.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nikandroid.chista.Functions.Function;
import com.nikandroid.chista.Params.Params;
import com.nikandroid.chista.R;
import com.nikandroid.chista.ViewModelFactory.CreditModelFactory;
import com.nikandroid.chista.ViewModelFactory.MainModelFactory;
import com.nikandroid.chista.databinding.CreditBinding;
import com.nikandroid.chista.databinding.MainBinding;
import com.nikandroid.chista.viewModels.CreditViewModel;
import com.nikandroid.chista.viewModels.MainViewModel;
import com.skydoves.elasticviews.ElasticImageView;
import com.skydoves.elasticviews.ElasticLayout;
import com.skydoves.powermenu.PowerMenu;

import net.steamcrafted.materialiconlib.MaterialIconView;

import org.json.JSONException;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class Credit extends AppCompatActivity {


    private String adid;
    private SharedPreferences sp;
    String Claim="";
    Function fun;
    ProgressBar prog;
    ElasticLayout variz,bardasht;
    ElasticImageView back;
    TextView value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CreditBinding binding= DataBindingUtil.setContentView(this,R.layout.credit);
        binding.setCreditModel(ViewModelProviders.of(this,new CreditModelFactory(this)).get(CreditViewModel.class));

        init();
        onClick();

    }
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));    }

    private Context getActivity(){
        return Credit.this;
    }

    private void init(){

        sp=getSharedPreferences(Params.spmain,MODE_PRIVATE);
        fun=new Function(this);
        Claim=sp.getString(Params.spClaim,"-");
        getSupportActionBar().hide();


        variz=findViewById(R.id.credit_variz);
        bardasht=findViewById(R.id.credit_bardasht);
        back=findViewById(R.id.credit_actionbar_back);
        value=findViewById(R.id.credit_value);

        back.setOnClickListener(v -> finish());

        value.setText(fun.change_adad(sp.getString(Params.sp_lastcredit,"0")));

    }

    private void onClick(){

        bardasht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show_box("get","برداشت از حساب");
                Intent i=new Intent(getActivity(),credit2.class);
                i.putExtra("flag","b");
                startActivity(i);
            }
        });

        variz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show_box("put","افزایش اعتبار");
                Intent i=new Intent(getActivity(),credit2.class);
                i.putExtra("flag","v");
                startActivity(i);
            }
        });


    }






}
