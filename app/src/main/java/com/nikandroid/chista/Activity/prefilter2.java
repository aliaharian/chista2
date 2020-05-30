package com.nikandroid.chista.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.marcoscg.dialogsheet.DialogSheet;
import com.nikandroid.chista.Functions.Function;
import com.nikandroid.chista.Params.Params;
import com.nikandroid.chista.R;
import com.skydoves.elasticviews.ElasticImageView;
import com.skydoves.elasticviews.ElasticLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.zip.Inflater;

import es.dmoral.toasty.Toasty;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class prefilter2 extends AppCompatActivity {



    private SharedPreferences sp;
    Function fun;
    String Claim="",flag="";

    TextView act_title,act_remove;
    ElasticImageView act_back;
    ProgressBar prog;
    ElasticLayout submit;

    int pricestart=0,priceend=0;
    int khakestrai0=0,khakestrai1=0;
    String ss="",ee="";

    ///gender area
    CheckBox man,woman,both;
    LinearLayout gender_area;

    BottomSheetDialog di;
    int distatus=0;


    //price
    LinearLayout price_area;
    LinearLayout price1,price2;
    TextView ptitle1,ptitle2;
    ElasticImageView pdelete1,pdelete2;

    ///gender area
    CheckBox offline,online,busy;
    LinearLayout status_area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prefilter2);


        init();
        clicks();

        fill_page();

    }


    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));    }

    private Context getActivity(){
        return prefilter2.this;
    }

    private void init(){

        sp=getSharedPreferences(Params.spmain,MODE_PRIVATE);
        fun=new Function(getActivity());
        Claim=sp.getString(Params.spClaim,"-");

        flag=getIntent().getExtras().getString("flag");


        getSupportActionBar().hide();
        act_title=findViewById(R.id.prefilter2_actionbar_title);
        act_back=findViewById(R.id.prefilter2_actionbar_back);
        act_remove=findViewById(R.id.prefilter2_actionbar_remove);

        if(flag.equals("gender")){
            act_title.setText("جنسیت");
        }else if(flag.equals("range")){
            act_title.setText("قیمت");
        }else if(flag.equals("onlines")){
            act_title.setText("وضعیت");
        }

        prog=findViewById(R.id.prefilter2_prog);
        submit=findViewById(R.id.prefilter2_submit);

        gender_area=findViewById(R.id.prefilter2_layout_gender);
        man=findViewById(R.id.prefilter2_gender_man);
        woman=findViewById(R.id.prefilter2_gender_woman);
        both=findViewById(R.id.prefilter2_gender_both);

        status_area=findViewById(R.id.prefilter2_layout_status);
        online=findViewById(R.id.prefilter2_status_online);
        offline=findViewById(R.id.prefilter2_status_offline);
        busy=findViewById(R.id.prefilter2_status_busy);

        price_area=findViewById(R.id.prefilter2_layout_price);
        price1=findViewById(R.id.prefilter2_price1);
        price2=findViewById(R.id.prefilter2_price2);

        ptitle1=findViewById(R.id.prefilter2_price1_title);
        ptitle2=findViewById(R.id.prefilter2_price2_title);

        pdelete1=findViewById(R.id.prefilter2_price1_delete);
        pdelete2=findViewById(R.id.prefilter2_price2_delete);

        khakestrai0=getResources().getColor(R.color.khakestari0);
        khakestrai1=getResources().getColor(R.color.khakestari1);

    }

    private void fill_page(){


        if(flag.equals("gender")){
            gender_area.setVisibility(View.VISIBLE);

            String value=getIntent().getExtras().getString("value");
            if(value.equals("1.0")){
                both.setChecked(true);
            }else if(value.equals("1")){
                man.setChecked(true);
            }else if(value.equals("0")){
                woman.setChecked(true);
            }
        }else if(flag.equals("range")){

            act_remove.setVisibility(View.INVISIBLE);

            price_area.setVisibility(View.VISIBLE);

            price1.setOnClickListener(v -> {
                show_dialog_price("start");
            });

            price2.setOnClickListener(v -> {
                show_dialog_price("end");
            });

            pdelete2.setOnClickListener(v -> {
                priceend=0;
                pdelete2.setVisibility(View.INVISIBLE);
                ptitle2.setText("مثلا ۲،۰۰۰ تومان");
                ptitle2.setTextColor(khakestrai1);
            });

            pdelete1.setOnClickListener(v -> {
                priceend=0;
                pdelete1.setVisibility(View.INVISIBLE);
                ptitle1.setText("مثلا ۲،۰۰۰ تومان");
                ptitle1.setTextColor(khakestrai1);
            });





        }else if(flag.equals("onlines")) {
            status_area.setVisibility(View.VISIBLE);

            String value = getIntent().getExtras().getString("value");


            if (prefilter.isonline.contains(0)) {
                offline.setChecked(true);
            }
            if (prefilter.isonline.contains(1)) {
                online.setChecked(true);
            }
            if (prefilter.isonline.contains(2)) {
                busy.setChecked(true);
            }
        }




    }

    private void show_dialog_price(String flag){

        LayoutInflater inflater = this.getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog_prefilter_price,null,false);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        RecyclerView plist=view.findViewById(R.id.dialog_prefilter_price_list);

        di=new BottomSheetDialog(this,R.style.SheetDialog);


        final list_price_adapter rva2=new list_price_adapter(fill_price_json(),flag);


        plist.setLayoutManager(new LinearLayoutManager(getActivity()));
        plist.setItemAnimator(new DefaultItemAnimator());
        plist.setAdapter(rva2);


        //di.requestWindowFeature(Window.FEATURE_NO_TITLE);
       // di.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        di.setContentView(view);

        di.show();
        distatus=1;

        di.setOnDismissListener(dialog -> {
            distatus=0;
            act_remove.setVisibility(View.VISIBLE);
        });
    }

    private JSONObject fill_price_json(){

        JSONObject items = new JSONObject();
        try {


            JSONObject row = new JSONObject();
            row.put("name", "2 هزار تومان");
            row.put("value", 2000);
            items.put("0",row);

            row = new JSONObject();
            row.put("name", "4 هزار تومان");
            row.put("value", 4000);
            items.put("1",row);

            row = new JSONObject();
            row.put("name", "6 هزار تومان");
            row.put("value", 6000);
            items.put("2",row);


            row = new JSONObject();
            row.put("name", "8 هزار تومان");
            row.put("value", 8000);
            items.put("3",row);


            row = new JSONObject();
            row.put("name", "10 هزار تومان");
            row.put("value", 10000);
            items.put("4",row);


            row = new JSONObject();
            row.put("name", "12 هزار تومان");
            row.put("value", 12000);
            items.put("5",row);


            row = new JSONObject();
            row.put("name", "14 هزار تومان");
            row.put("value", 14000);
            items.put("6",row);


            row = new JSONObject();
            row.put("name", "16 هزار تومان");
            row.put("value", 16000);
            items.put("7",row);


        }catch (Exception e){

        }

        return items;
    }


    public class list_price_adapter extends RecyclerView.Adapter<list_price_adapter.MyViewHolder> {

        private JSONObject list;
        private String flag;
        public class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView name;
            public MyViewHolder(View view) {
                super(view);
                name = view.findViewById(R.id.dialog_prefilter_price_row_title);
            }
        }

        public list_price_adapter(JSONObject list,String flag) {
            this.list = list;
            this.flag = flag;
        }

        @Override
        public list_price_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.dialog_prefilter_price_row, parent, false);
            return new list_price_adapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final list_price_adapter.MyViewHolder holder, final int position) {

            try {
                JSONObject row=new JSONObject(list.getString(position+""));
                holder.name.setText(row.getString("name"));

                holder.itemView.setOnClickListener(v -> {
                    if(flag.equals("start")){

                        try {
                            ptitle1.setText(row.getString("name"));
                            ptitle1.setTextColor(khakestrai0);
                            pricestart =row.getInt("value");
                            pdelete1.setVisibility(View.VISIBLE);
                            ss=row.getString("name");
                            di.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }else if(flag.equals("end")){
                        try {
                            ptitle2.setText(row.getString("name"));
                            ptitle2.setTextColor(khakestrai0);
                            priceend =row.getInt("value");
                            pdelete2.setVisibility(View.VISIBLE);
                            ee=row.getString("name");
                            di.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public int getItemCount() {
            return list.length();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }
    }


    private void clicks(){

        act_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                man.setChecked(false);
                woman.setChecked(false);
                both.setChecked(false);
                online.setChecked(false);
                offline.setChecked(false);
                busy.setChecked(false);
                ptitle1.setText("");
                ptitle2.setText("");

                act_remove.setVisibility(View.INVISIBLE);
            }
        });
        act_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag.equals("gender")){
                    if(man.isChecked() || woman.isChecked() || both.isChecked()){
                        ArrayList<Integer> gender_id=new ArrayList<Integer>();
                        if(man.isChecked()) {
                            gender_id.add(1);
                        }
                        if(woman.isChecked()) {
                            gender_id.add(0);
                        }
                        if(both.isChecked() && !man.isChecked() && !woman.isChecked()) {
                            gender_id.add(1);
                            gender_id.add(0);
                        }
                        prefilter.gender_id=gender_id;
                        finish();
                    }else{
                        Toasty.warning(getActivity(),"لطفا حداقل یک گزینه را انتخاب نمایید",Toasty.LENGTH_LONG).show();
                    }

                }else if(flag.equals("onlines")){


                    if(online.isChecked() || offline.isChecked() || busy.isChecked()){
                        ArrayList<Integer> isonlineid=new ArrayList<Integer>();
                        if(online.isChecked()) {
                            isonlineid.add(1);
                        }
                        if(offline.isChecked()) {
                            isonlineid.add(0);
                        }
                        if(busy.isChecked()) {
                            isonlineid.add(2);
                        }
                        prefilter.isonline=isonlineid;
                        finish();
                    }else{
                        Toasty.warning(getActivity(),"لطفا حداقل یک گزینه را انتخاب نمایید",Toasty.LENGTH_LONG).show();
                    }


                }else if(flag.equals("range")){

                    if(priceend==0 || pricestart==0){
                        Toasty.success(getActivity(),"لطفا بازه قیمت را مشخص کنید",Toasty.LENGTH_LONG).show();
                    }else {
                        prefilter.maxrange=priceend+"";
                        prefilter.minrange=pricestart+"";
                        prefilter.namerange="از "+ss+" تا "+ee;
                        finish();
                    }



                }
            }
        });

    }







}


