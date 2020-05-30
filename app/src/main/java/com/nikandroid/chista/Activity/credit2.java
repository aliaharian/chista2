package com.nikandroid.chista.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.nikandroid.chista.Functions.Function;
import com.nikandroid.chista.Params.Params;
import com.nikandroid.chista.R;
import com.nikandroid.chista.ViewModelFactory.Credit2ModelFactory;
import com.nikandroid.chista.ViewModelFactory.CreditModelFactory;
import com.nikandroid.chista.databinding.Credit2Binding;
import com.nikandroid.chista.databinding.CreditBinding;
import com.nikandroid.chista.viewModels.Credit2ViewModel;
import com.nikandroid.chista.viewModels.CreditViewModel;
import com.skydoves.elasticviews.ElasticImageView;
import com.skydoves.elasticviews.ElasticLayout;
import com.squareup.picasso.Picasso;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class credit2 extends AppCompatActivity {

    private String adid;
    private SharedPreferences sp;
    String Claim="";
    Function fun;
    ProgressBar prog;
    TextView title,final_paybtn_text,tmp1_title,tmp2_title,tmp3_title,fp_name,fp_bank,fp_sheba,fp_price,text1,text2;
    EditText finalvalue;

    ElasticLayout tmp1,tmp2,tmp3;
    ElasticImageView back;
    String flag="";
    int Abi1,khakstrai0;
    LinearLayout sugestarea,final_payarea,valuearea;
    ElasticLayout submit;
    ElasticLayout shebaedit;
    ImageView bank_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Credit2Binding binding= DataBindingUtil.setContentView(this, R.layout.credit2);
        binding.setCredit2Model(ViewModelProviders.of(this,new Credit2ModelFactory(this)).get(Credit2ViewModel.class));

        init();

        onClick();
    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));    }

    private Context getActivity(){
        return credit2.this;
    }

    private void init(){

        sp=getSharedPreferences(Params.spmain,MODE_PRIVATE);
        fun=new Function(this);
        Claim=sp.getString(Params.spClaim,"-");
        getSupportActionBar().hide();
        flag=getIntent().getExtras().getString("flag");

        back=findViewById(R.id.credit2_actionbar_back);
        title=findViewById(R.id.credit2_title);
        tmp1=findViewById(R.id.credit2_tmp1);
        tmp2=findViewById(R.id.credit2_tmp2);
        tmp3=findViewById(R.id.credit2_tmp3);
        tmp1_title=findViewById(R.id.credit2_tmp1_title);
        tmp2_title=findViewById(R.id.credit2_tmp2_title);
        tmp3_title=findViewById(R.id.credit2_tmp3_title);
        finalvalue=findViewById(R.id.credit2_finalvalue);
        sugestarea=findViewById(R.id.credit2_sugestarea);
        text1=findViewById(R.id.credit2_msg_text1);
        text2=findViewById(R.id.credit2_msg_text2);
        submit=findViewById(R.id.credit2_btn_finalpay);
        final_paybtn_text=findViewById(R.id.credit2_btn_finalpay_text);
        final_payarea=findViewById(R.id.credit2_finalpayarea);
        valuearea=findViewById(R.id.credit2_valuearea);
        shebaedit=findViewById(R.id.credit2_shebaedit);

        fp_price=findViewById(R.id.credit2_finalpaytext);
        fp_name=findViewById(R.id.credit2_finalpaytext_name);
        fp_sheba=findViewById(R.id.credit2_finalpaytext_sheba);
        fp_bank=findViewById(R.id.credit2_finalpaytext_bank);
        bank_img=findViewById(R.id.credit2_finalpaytext_bankimg);



        Abi1=getResources().getColor(R.color.Abi1);
        khakstrai0=getResources().getColor(R.color.khakestari0);

        tmp1_title.setText(fun.change_adad("100000")+" تومان");
        tmp2_title.setText(fun.change_adad("50000")+" تومان");
        tmp3_title.setText(fun.change_adad("20000")+" تومان");

        if(flag.equals("b")){

            title.setText("واریز به بانک");
            sugestarea.setVisibility(View.GONE);

        }else if(flag.equals("v")){

            title.setText("افزایش اعتبار");

        }

        back.setOnClickListener(v -> finish());



    }

    private void onClick(){


        tmp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp1.setBackgroundResource(R.drawable.btn_back4);
                tmp2.setBackgroundResource(R.drawable.btn_back8);
                tmp3.setBackgroundResource(R.drawable.btn_back8);

                tmp1_title.setTextColor(Abi1);
                tmp2_title.setTextColor(khakstrai0);
                tmp3_title.setTextColor(khakstrai0);
                finalvalue.setText("100000");

            }
        });

        tmp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp2.setBackgroundResource(R.drawable.btn_back4);
                tmp1.setBackgroundResource(R.drawable.btn_back8);
                tmp3.setBackgroundResource(R.drawable.btn_back8);
                finalvalue.setText("50000");
                tmp2_title.setTextColor(Abi1);
                tmp1_title.setTextColor(khakstrai0);
                tmp3_title.setTextColor(khakstrai0);

            }
        });

        tmp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tmp3.setBackgroundResource(R.drawable.btn_back4);
                tmp2.setBackgroundResource(R.drawable.btn_back8);
                tmp1.setBackgroundResource(R.drawable.btn_back8);
                finalvalue.setText("20000");
                tmp3_title.setTextColor(Abi1);
                tmp2_title.setTextColor(khakstrai0);
                tmp1_title.setTextColor(khakstrai0);

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               procces();
            }
        });

        shebaedit.setOnClickListener(v -> {

            show_edit_box1("sheba");

        });



    }

    private void procces(){
        detect_bank_img(sp.getString(Params.sp_sheba,"-"));
        if(!finalvalue.getText().toString().equals("")){
            if(flag.equals("b")){
                if(Integer.parseInt(sp.getString(Params.sp_lastcredit,"0"))<Integer.parseInt(finalvalue.getText().toString())){
                    fun.show_error_dialog("موجودی ناکافی","مبلغ بیشتر از موجودی کیف پول شماست");
                }else{

                   title.setText("انتقال پول به بانک");
                   valuearea.setVisibility(View.GONE);
                   final_payarea.setVisibility(View.VISIBLE);
                   final_paybtn_text.setText("انتقال");
                   fp_price.setText(fun.change_adad(finalvalue.getText().toString()));
                   text1.setVisibility(View.VISIBLE);
                   text2.setVisibility(View.GONE);
                   fp_name.setText(sp.getString(Params.sp_fulllname,"-"));
                   fp_sheba.setText(sp.getString(Params.sp_sheba,"-"));


                }
            }
        }else{
            Toast.makeText(getActivity(),"مبلغ وارد شود",Toast.LENGTH_LONG).show();
        }
    }


    public void show_edit_box1(String flag){

        LayoutInflater inflater = getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog_ae_edit1,null,false);
        BottomSheetDialog di=new BottomSheetDialog(getActivity(),R.style.SheetDialog);

        TextView title=view.findViewById(R.id.dialog_ae_edit1_title);
        ElasticImageView back=view.findViewById(R.id.dialog_ae_edit1_backbtn);
        LinearLayout f1ll=view.findViewById(R.id.dialog_ae_edit1_f1ll);
        LinearLayout f2ll=view.findViewById(R.id.dialog_ae_edit1_f2ll);
        ImageView f1img=view.findViewById(R.id.dialog_ae_edit1_f1img);
        ImageView f2img=view.findViewById(R.id.dialog_ae_edit1_f2img);
        TextView f1title=view.findViewById(R.id.dialog_ae_edit1_f1title);
        TextView f2title=view.findViewById(R.id.dialog_ae_edit1_f2title);
        EditText f1=view.findViewById(R.id.dialog_ae_edit1_f1);
        EditText f2=view.findViewById(R.id.dialog_ae_edit1_f2);
        ElasticLayout submit=view.findViewById(R.id.dialog_ae_edit1_submit);




        if(flag.equals("sheba")){

            f1.setText(sp.getString(Params.sp_sheba,""));
            title.setText("ویرایش");
            f1title.setText("شماره شبا");
            f1.setHint("شماره شبا");
            f2ll.setVisibility(View.GONE);
            f1.setInputType(InputType.TYPE_CLASS_NUMBER);
            f1img.setImageResource(R.drawable.main_icon_bank);
            submit.setOnClickListener(v -> {

                fp_sheba.setText(f1.getText().toString());
                di.dismiss();

            });
            di.setContentView(view);
            di.show();
        }



        back.setOnClickListener(v -> {

            di.dismiss();

        });



    }




    private void detect_bank_img(String sheba){

        try{


        if(sheba.substring(2,5).equals("018")){
            Picasso.get().load(R.drawable.b_tejarat).into(bank_img);
            fp_bank.setText("بانک تجارت");
        }else
        if(sheba.substring(2,5).equals("055")){
            Picasso.get().load(R.drawable.b_eghtesanno).into(bank_img);
            fp_bank.setText("بانک اقتصاد نوین");
        }else
        if(sheba.substring(2,5).equals("054")){
            Picasso.get().load(R.drawable.b_parsian).into(bank_img);
            fp_bank.setText("بانک پارسیان");
        }else
        if(sheba.substring(2,5).equals("057")){
            Picasso.get().load(R.drawable.b_pasargad).into(bank_img);
            fp_bank.setText("بانک پاسارگاد");
        }else
        if(sheba.substring(2,5).equals("021")){
            Picasso.get().load(R.drawable.b_postbank).into(bank_img);
            fp_bank.setText("پست بانک");
        }else
        if(sheba.substring(2,5).equals("013")){
            Picasso.get().load(R.drawable.b_refah).into(bank_img);
            fp_bank.setText("بانک رفاه");
        }else
        if(sheba.substring(2,5).equals("056")){
            Picasso.get().load(R.drawable.b_saman).into(bank_img);
            fp_bank.setText("بانک سامان");
        }else
        if(sheba.substring(2,5).equals("015")){
            Picasso.get().load(R.drawable.b_sepah).into(bank_img);
            fp_bank.setText("بانک سپه");
        }else
        if(sheba.substring(2,5).equals("019")){
            Picasso.get().load(R.drawable.b_saderat).into(bank_img);
            fp_bank.setText("بانک صادرات");
        }else
        if(sheba.substring(2,5).equals("014")){
            Picasso.get().load(R.drawable.b_maskan).into(bank_img);
            fp_bank.setText("بانک مسکن");
        }else
        if(sheba.substring(2,5).equals("012")){
            Picasso.get().load(R.drawable.b_melat).into(bank_img);
            fp_bank.setText("بانک ملت");
        }else
        if(sheba.substring(2,5).equals("017")){
            Picasso.get().load(R.drawable.b_meli).into(bank_img);
            fp_bank.setText("بانک ملی");
        }else{
            bank_img.setVisibility(View.INVISIBLE);
            fp_bank.setText("نا مشخص");
        }

        }catch (Exception e){
            bank_img.setVisibility(View.INVISIBLE);
            fp_bank.setText("نا مشخص");
        }




    }



}
