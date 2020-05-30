package com.nikandroid.chista.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.florent37.shapeofview.shapes.CircleView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.nikandroid.chista.Adapters.RtlGridLayoutManager;
import com.nikandroid.chista.Adapters.list_tags_adapter;
import com.nikandroid.chista.Functions.Function;
import com.nikandroid.chista.Params.Params;
import com.nikandroid.chista.R;
import com.nikandroid.chista.TagGroup;
import com.nikandroid.chista.Utils.MyHttpEntity;
import com.nikandroid.chista.Utils.StringChooser;
import com.nikandroid.chista.ViewModelFactory.SubmitAdviserModelFactory;
import com.nikandroid.chista.databinding.SubmitAdviserBinding;
import com.nikandroid.chista.viewModels.SubmitAdviserViewModel;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.skydoves.elasticviews.ElasticButton;
import com.skydoves.elasticviews.ElasticLayout;
import com.squareup.picasso.Picasso;
import com.wefika.flowlayout.FlowLayout;
import com.yalantis.ucrop.UCrop;

import net.alhazmy13.mediapicker.Video.VideoPicker;
import net.steamcrafted.materialiconlib.MaterialIconView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import pl.aprilapps.easyphotopicker.ChooserType;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.MediaFile;
import pl.aprilapps.easyphotopicker.MediaSource;

public class Submit_Adviser extends AppCompatActivity {



    private String adid;
    private SharedPreferences sp;
    String Claim="";
    Function fun;
    int blackcolor=0;
    int bluecolor=0;
    ScrollView mainScroll;
    ImageView actback;

    ImageView mainpic,pic1,pic2,pic3,pic4,pic5,pic6,pic7,pic8,pic9,pic10;
    MaterialIconView picremove1,picremove2,picremove3,picremove4,picremove5,picremove6,picremove7,picremove8,picremove9,picremove10;
    ImageView curent_image_uploader;
    LinearLayout cityarea;

    TextView city;
    public static String city_name="";
    String curent_file_name="";
    ElasticLayout submitbtn;




    //all steps
    LinearLayout step1,step2,step3,step4;
    int cpage=1;
    SweetAlertDialog erdi;
    int khakestrai0=0,khakestari1=0,Abi1=0;
    ImageView pageprogress;
    TextView pagenumber;

    ////step1
    EditText name,family,codemeli,sheba;
    LinearLayout sexarea,bdatearea;
    TextView sexname,step1label,bdatename;
    int sexid=0;


    ////step2
    LinearLayout selectcats,selectsubcats,selecttags;
    String catscontent="",subcatscontent="",tagscontent="";
    TextView catsname,subcatsname,tagsname;

    ArrayList<String> subcats;
    ArrayList<String> subcats_name;
    ArrayList<String> tags;
    ArrayList<String> tags_name;
    String cats_id="",subcatsstring="",tagsstring="";


    //step3
    LinearLayout selectostan;
    EditText field,price,longdes;
    TextView ostanname,fieldlimit,longdeslimit;
    String citycontent="";
    String city_id="";
    JSONObject tempsearch;
    JSONObject cityfullitems;


    //step4
    RecyclerView listdocs;
    String docscontent="";
    LinearLayout myimagesarea;
    HorizontalScrollView myimagesscroll;






    VideoView video;
/*
    load_listsubcat_adapter llsa;
    load_selectedtags_adapter lsta;
    tag_list_adapter tla1;
    docs_list_adapter dla1;
    */

    boolean searchtag=true;


    JSONObject cats_json,subcats_json;
    JSONArray docslistjson;
    JSONObject tagslistjsonarray;





    EasyImage easyImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SubmitAdviserBinding binding= DataBindingUtil.setContentView(this,R.layout.submit__adviser);
        binding.setSubmitadviserModel(ViewModelProviders.of(this,new SubmitAdviserModelFactory(this)).get(SubmitAdviserViewModel.class));

        init();
        make_page_step1();
        //make_page_step2();
        //make_page_step3();
        //make_page_step4();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //city.setText(city_name);
    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));    }

    private Context getActivity(){
        return Submit_Adviser.this;
    }

    private void init(){

        sp=getSharedPreferences(Params.spmain,MODE_PRIVATE);
        fun=new Function(this);
        Claim=sp.getString(Params.spClaim,"-");
        blackcolor=getResources().getColor(R.color.siyah);
        bluecolor=getResources().getColor(R.color.Abi2);

        subcats=new ArrayList<String>();
        subcats_name=new ArrayList<String>();

        tags=new ArrayList<String>();
        tags_name=new ArrayList<String>();


        getSupportActionBar().hide();


        actback=findViewById(R.id.submitadviser_actionbar_back);
        actback.setOnClickListener(v -> {

            if(cpage==4){
                make_page_step3();
            }else
            if(cpage==3){
                make_page_step2();
            }else
            if(cpage==2){
                make_page_step1();
            }else
            if(cpage==1){
                finish();
            }


        });







        //all
        mainScroll=findViewById(R.id.sa_mainscroll);
        erdi=new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE);
        erdi.setTitleText("عدم اتصال به اینترنت");
        erdi.setContentText("بروز خطا در دریافت اطلاعات");
        erdi.setConfirmText("خروج");
        erdi.setCancelable(false);
        erdi.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                finish();
            }
        });
        khakestrai0=getResources().getColor(R.color.khakestari0);
        khakestari1=getResources().getColor(R.color.khakestari1);
        Abi1=getResources().getColor(R.color.Abi1);
        pagenumber=findViewById(R.id.sa_actionbar_pagetitle);
        pageprogress=findViewById(R.id.sa_actionbar_pageprog);


        //step1
        step1=findViewById(R.id.submitadviser_step1area);
        name=findViewById(R.id.sa_et_name);
        family=findViewById(R.id.sa_et_family);
        codemeli=findViewById(R.id.sa_et_codemai);
        sheba=findViewById(R.id.sa_et_sheba);
        sexname=findViewById(R.id.sa_tv_sex);
        bdatename=findViewById(R.id.sa_tv_bdatename);
        step1label=findViewById(R.id.sa_tv_step1label);
        sexarea=findViewById(R.id.sa_ll_sex);
        bdatearea=findViewById(R.id.sa_ll_bdate);


        //step2
        step2=findViewById(R.id.submitadviser_step2area);
        selectcats=findViewById(R.id.sa_ll_selectcats);
        selectsubcats=findViewById(R.id.sa_ll_selectsubcats);
        selecttags=findViewById(R.id.sa_ll_selecttags);
        catsname=findViewById(R.id.sa_tv_catsname);
        subcatsname=findViewById(R.id.sa_tv_subcatsname);
        tagsname=findViewById(R.id.sa_tv_tagsname);


        //step3
        step3=findViewById(R.id.submitadviser_step3area);
        selectostan=findViewById(R.id.sa_ll_ostan);
        field=findViewById(R.id.sa_et_field);
        ostanname=findViewById(R.id.sa_ll_ostanname);
        fieldlimit=findViewById(R.id.sa_tv_field_limit);
        longdes=findViewById(R.id.sa_et_longdes);
        longdeslimit=findViewById(R.id.sa_tv_longdes_limit);
        price=findViewById(R.id.sa_et_price);


        //step4
        step4=findViewById(R.id.submitadviser_step4area);
        myimagesarea=findViewById(R.id.sa_line_myimages_area);
        myimagesscroll=findViewById(R.id.sa_sv_myimages);
        String docscontent="";





        get_cats_list();



        //city=findViewById(R.id.sa_tv_city);
        //cityarea=findViewById(R.id.sa_ll_changecity);

        //mr=findViewById(R.id.sa_cb_mr);
        // mrs=findViewById(R.id.sa_cb_mrs);

        video=findViewById(R.id.sa_videoview);


        //select_cat=findViewById(R.id.sa_spiner_selectcat);
        //select_subcat=findViewById(R.id.sa_spiner_selectsubcat);




        listdocs=findViewById(R.id.sa_list_docs);
        submitbtn=findViewById(R.id.sa_el_submit);
/*
        mainpic=findViewById(R.id.sa_img_mainpic);
        pic1=findViewById(R.id.sa_img_pic1);
        pic2=findViewById(R.id.sa_img_pic2);
        pic3=findViewById(R.id.sa_img_pic3);
        pic4=findViewById(R.id.sa_img_pic4);
        pic5=findViewById(R.id.sa_img_pic5);
        pic6=findViewById(R.id.sa_img_pic6);
        pic7=findViewById(R.id.sa_img_pic7);
        pic8=findViewById(R.id.sa_img_pic8);
        pic9=findViewById(R.id.sa_img_pic9);
        pic10=findViewById(R.id.sa_img_pic10);

        picremove1=findViewById(R.id.sa_pic1_remove);
        picremove2=findViewById(R.id.sa_pic2_remove);
        picremove3=findViewById(R.id.sa_pic3_remove);
        picremove4=findViewById(R.id.sa_pic4_remove);
        picremove5=findViewById(R.id.sa_pic5_remove);
        picremove6=findViewById(R.id.sa_pic6_remove);
        picremove7=findViewById(R.id.sa_pic7_remove);
        picremove8=findViewById(R.id.sa_pic8_remove);
        picremove9=findViewById(R.id.sa_pic9_remove);
        picremove10=findViewById(R.id.sa_pic10_remove);
/*
        easyImage = new EasyImage.Builder(getActivity())
                .setChooserTitle("انتخاب رسانه")
                .setChooserType(ChooserType.CAMERA_AND_GALLERY)
                .setCopyImagesToPublicGalleryFolder(false)
                .allowMultiple(false)
                .build();

        mainScroll.post(new Runnable()
        {
            public void run() {
                mainScroll.fullScroll(ScrollView.FOCUS_UP);
                name.clearFocus();
            }
        });
*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(cpage==4){
            make_page_step3();
        }else
        if(cpage==3){
            make_page_step2();
        }else
        if(cpage==2){
            make_page_step1();
        }else
        if(cpage==1){
            finish();
        }
    }

    private void make_page_step1(){

        setpage(1);
        //step3.setVisibility(View.GONE);
        //step4.setVisibility(View.GONE);
        name.setText(sp.getString(Params.sp_name,""));
        family.setText(sp.getString(Params.sp_lastname,""));
        codemeli.setText("");
        sheba.setText(sp.getString(Params.sp_sheba,""));
        step1label.setText("مشخصات زیر را وارد نمایید ، این اطلاعات می بایست لزوما متعلق به مالک شماره تلفن همراه "+sp.getString(Params.sp_mobile,"")+" باشد");
        sexarea.setOnClickListener(v -> {

            show_dialog_sex();

        });
        bdatearea.setOnClickListener(v -> {
            show_bdate_dialog(bdatename);
        });
        submitbtn.setOnClickListener(v -> {

            if(name.getText().toString().length()<1){
                Toasty.warning(getActivity(),"مقدار نام صحیح وارد نشده است",Toasty.LENGTH_LONG).show();
            }else if(family.getText().toString().length()<1){
                Toasty.warning(getActivity(),"مقدار نام خانوادگی صحیح وارد نشده است",Toasty.LENGTH_LONG).show();
            }else if(codemeli.getText().toString().length()!=10){
                Toasty.warning(getActivity(),"مقدار کد ملی صحیح وارد نشده است",Toasty.LENGTH_LONG).show();
            }else if(sheba.getText().toString().length()<15) {
                Toasty.warning(getActivity(), "مقدار شماره شبا صحیح وارد نشده است", Toasty.LENGTH_LONG).show();
            }else{
                make_page_step2();
            }

        });


/*
        city_name=sp.getString(Params.sp_city,"");
        city_id=sp.getString(Params.sp_city_id,"");

        desc_line.setText("");
        desc_long.setText("");
        desc_short.setText("");


        get_cats_list();
        reload_listsubcats();

        reload_listtags();

        tag_Search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){

                    mainScroll.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mainScroll.scrollTo(0, mainScroll.getScrollY()+550);
                        }
                    },1000);


                    String tmpsub="";
                    for (int i=0;i<subcats.size();i++)
                    {
                        tmpsub+=subcats.get(i)+",";
                    }
                    get_tags_list("",tmpsub);
                }
            }
        });
        tag_Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(searchtag){
                    String tmpsub="";
                    for (int i=0;i<subcats.size();i++)
                    {
                        tmpsub+=subcats.get(i)+",";
                    }

                    get_tags_list(tag_Search.getText().toString(),tmpsub);
                }else{
                    searchtag=true;
                }



            }
        });


        Picasso.get()
                .load(Params.pic_adviseravatae+sp.getString(Params.sp_avatar,"-"))
                .placeholder(R.drawable.avatar_tmp1)
                .resize(200,200)
                .error(R.drawable.avatar_tmp1)
                .into(mainpic);
        mainpic.setOnClickListener(v -> start_upload("advpic",mainpic));




        pic1.setOnClickListener(v -> start_upload("g_2",pic1));
        pic2.setOnClickListener(v -> start_upload("g_3",pic2));
        pic3.setOnClickListener(v -> start_upload("g_4",pic3));
        pic4.setOnClickListener(v -> start_upload("g_5",pic4));
        pic5.setOnClickListener(v -> start_upload("g_6",pic5));
        pic6.setOnClickListener(v -> start_upload("g_7",pic6));
        pic7.setOnClickListener(v -> start_upload("g_8",pic7));
        pic8.setOnClickListener(v -> start_upload("g_9",pic8));
        pic9.setOnClickListener(v -> start_upload("g_10",pic9));
        pic10.setOnClickListener(v -> start_upload("g_11",pic10));

        picremove1.setOnClickListener(v -> delete_pic("g_2",pic1));
        picremove2.setOnClickListener(v -> delete_pic("g_3",pic2));
        picremove3.setOnClickListener(v -> delete_pic("g_4",pic3));
        picremove4.setOnClickListener(v -> delete_pic("g_5",pic4));
        picremove5.setOnClickListener(v -> delete_pic("g_6",pic5));
        picremove6.setOnClickListener(v -> delete_pic("g_7",pic6));
        picremove7.setOnClickListener(v -> delete_pic("g_8",pic7));
        picremove8.setOnClickListener(v -> delete_pic("g_9",pic8));
        picremove9.setOnClickListener(v -> delete_pic("g_10",pic9));
        picremove10.setOnClickListener(v -> delete_pic("g_11",pic10));



        cityarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),select_city.class);
                i.putExtra("ccity",Params.sp_city_id);
                i.putExtra("flag","submitadviser");
                startActivity(i);
            }
        });

        video_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new VideoPicker.Builder(Submit_Adviser.this)
                        .mode(VideoPicker.Mode.GALLERY)
                        .directory(VideoPicker.Directory.DEFAULT)
                        .extension(VideoPicker.Extension.MP4)
                        .enableDebuggingMode(false)
                        .build();

            }
        });

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit_data();
            }
        });*/

    }

    private void make_page_step2(){

        setpage(2);

        selectcats.setOnClickListener(v -> {

            if(catscontent.equals("")){
                Toast.makeText(getActivity(),"لطفا مجدد کلیک کنید",Toast.LENGTH_LONG).show();
            }else{
                show_select_cat_dialog(catscontent);
            }

        });

        selectsubcats.setOnClickListener(v -> {

            if(cats_id.equals("")){
                Toast.makeText(getActivity(),"لطفا ابتدا یک گروه انتخاب کنید",Toast.LENGTH_LONG).show();
            }else{
                show_select_subcat_dialog(subcatscontent);

            }


        });

        selecttags.setOnClickListener(v -> {
            if(subcatsstring.equals("")) {
                Toast.makeText(getActivity(),"لطفا ابتدا دسته بندی ها را انتخاب کنید",Toast.LENGTH_LONG).show();
            }else{
                show_select_tags_dialog(tagscontent);

            }
        });

        submitbtn.setOnClickListener(v -> {

            if(tagscontent.length()<2){
                Toasty.warning(getActivity(),"لطفا همه فیلدها را پر کنید",Toasty.LENGTH_LONG).show();
            }else{
                make_page_step3();
            }

        });




/*
        city_name=sp.getString(Params.sp_city,"");
        city_id=sp.getString(Params.sp_city_id,"");

        desc_line.setText("");
        desc_long.setText("");
        desc_short.setText("");


        get_cats_list();
        reload_listsubcats();

        reload_listtags();

        tag_Search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){

                    mainScroll.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mainScroll.scrollTo(0, mainScroll.getScrollY()+550);
                        }
                    },1000);


                    String tmpsub="";
                    for (int i=0;i<subcats.size();i++)
                    {
                        tmpsub+=subcats.get(i)+",";
                    }
                    get_tags_list("",tmpsub);
                }
            }
        });
        tag_Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(searchtag){
                    String tmpsub="";
                    for (int i=0;i<subcats.size();i++)
                    {
                        tmpsub+=subcats.get(i)+",";
                    }

                    get_tags_list(tag_Search.getText().toString(),tmpsub);
                }else{
                    searchtag=true;
                }



            }
        });


        Picasso.get()
                .load(Params.pic_adviseravatae+sp.getString(Params.sp_avatar,"-"))
                .placeholder(R.drawable.avatar_tmp1)
                .resize(200,200)
                .error(R.drawable.avatar_tmp1)
                .into(mainpic);
        mainpic.setOnClickListener(v -> start_upload("advpic",mainpic));




        pic1.setOnClickListener(v -> start_upload("g_2",pic1));
        pic2.setOnClickListener(v -> start_upload("g_3",pic2));
        pic3.setOnClickListener(v -> start_upload("g_4",pic3));
        pic4.setOnClickListener(v -> start_upload("g_5",pic4));
        pic5.setOnClickListener(v -> start_upload("g_6",pic5));
        pic6.setOnClickListener(v -> start_upload("g_7",pic6));
        pic7.setOnClickListener(v -> start_upload("g_8",pic7));
        pic8.setOnClickListener(v -> start_upload("g_9",pic8));
        pic9.setOnClickListener(v -> start_upload("g_10",pic9));
        pic10.setOnClickListener(v -> start_upload("g_11",pic10));

        picremove1.setOnClickListener(v -> delete_pic("g_2",pic1));
        picremove2.setOnClickListener(v -> delete_pic("g_3",pic2));
        picremove3.setOnClickListener(v -> delete_pic("g_4",pic3));
        picremove4.setOnClickListener(v -> delete_pic("g_5",pic4));
        picremove5.setOnClickListener(v -> delete_pic("g_6",pic5));
        picremove6.setOnClickListener(v -> delete_pic("g_7",pic6));
        picremove7.setOnClickListener(v -> delete_pic("g_8",pic7));
        picremove8.setOnClickListener(v -> delete_pic("g_9",pic8));
        picremove9.setOnClickListener(v -> delete_pic("g_10",pic9));
        picremove10.setOnClickListener(v -> delete_pic("g_11",pic10));



        cityarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),select_city.class);
                i.putExtra("ccity",Params.sp_city_id);
                i.putExtra("flag","submitadviser");
                startActivity(i);
            }
        });

        video_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new VideoPicker.Builder(Submit_Adviser.this)
                        .mode(VideoPicker.Mode.GALLERY)
                        .directory(VideoPicker.Directory.DEFAULT)
                        .extension(VideoPicker.Extension.MP4)
                        .enableDebuggingMode(false)
                        .build();

            }
        });

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit_data();
            }
        });*/

    }

    private void make_page_step3(){

        setpage(3);
        get_city_list();
        longdeslimit.setText("( "+"0"+" / 500 "+"کاراکتر )");
        fieldlimit.setText("( "+"0"+" / 25 "+"کاراکتر )");
        field.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                int l=field.getText().toString().length();
                fieldlimit.setText("( "+l+" / 25 "+"کاراکتر )");
            }
        });

        longdes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                int l=longdes.getText().toString().length();
                longdeslimit.setText("( "+l+" / 500 "+"کاراکتر )");
            }
        });


        selectostan.setOnClickListener(v -> {
            show_select_city_dialog();
        });

        submitbtn.setOnClickListener(v -> {

            if(city_id.equals("") && longdes.getText().toString().length()<2 && field.getText().toString().length()<2){
                Toasty.warning(getActivity(),"لطفا همه فیلدها را پر کنید",Toasty.LENGTH_LONG).show();
            }else{
                make_page_step4();
            }

        });




    }

    private void make_page_step4(){

        setpage(4);

        fill_list_docs(docscontent);

        fill_myimagearea();

        submitbtn.setOnClickListener(v -> {



        });



    }


    public void show_bdate_dialog(TextView name){

        LayoutInflater inflater = getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog_bdate,null,false);
        BottomSheetDialog di=new BottomSheetDialog(getActivity(),R.style.SheetDialog);
        di.setCancelable(false);

        ImageView back=view.findViewById(R.id.dialog_bdate_backbtn);
        TextView title=view.findViewById(R.id.dialog_bdate_title);
        ElasticLayout submit=view.findViewById(R.id.dialog_bdate_submit);

        StringChooser sal = view.findViewById(R.id.dialog_bdate_sal);
        StringChooser mah = view.findViewById(R.id.dialog_bdate_mah);
        StringChooser rooz = view.findViewById(R.id.dialog_bdate_rooz);

        List<String> salha = new ArrayList<String>();
        for(int i=1300;i<1400;i++){
            salha.add(i+"");
        }
        sal.setStrings(salha);
        sal.setNestedScrollingEnabled(false);

        List<String> roozha = new ArrayList<String>();
        for(int i=1;i<31;i++){
            roozha.add(i+"");
        }
        rooz.setStrings(roozha);
        rooz.setNestedScrollingEnabled(false);

        List<String> mahha = new ArrayList<String>();
        mahha.add("فروردین");
        mahha.add("اردیبهشت");
        mahha.add("خرداد");
        mahha.add("تیر");
        mahha.add("مرداد");
        mahha.add("شهریور");
        mahha.add("مهر");
        mahha.add("آبان");
        mahha.add("آذر");
        mahha.add("دی");
        mahha.add("بهمن");
        mahha.add("اسفند");
        mah.setStrings(mahha);
        mah.setNestedScrollingEnabled(false);


        submit.setOnClickListener(v -> {
            name.setText(rooz.getSelectedString()+"  "+mah.getSelectedString()+"  "+sal.getSelectedString());
            di.dismiss();
        });


        title.setText("تاریخ تولد");
        back.setOnClickListener(v -> di.dismiss());
        di.setContentView(view);
        di.show();





    }


    private void get_cats_list(){

        selectcats.setAlpha(0.4f);
        catsname.setText("لطفا صبر کنید...");
        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_prefilter,
                response -> {
                    selectcats.setAlpha(1f);
                    catsname.setText("انتخاب کنید");
                    catscontent=response;
                },
                error -> {
                    erdi.show();
                })
        {
            @Override
            public byte[] getBody() {
                String str = "{\"type\":\"listcat\"}";
                return str.getBytes();
            };

            public String getBodyContentType()
            {
                return "application/json; charset=utf-8";
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("cookie", Claim);
                return params;
            }

        };
        Volley.newRequestQueue(getActivity()).add(rq);
    }
    public void show_select_cat_dialog(String res){
        LayoutInflater inflater = getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog_sa_selectcats,null,false);
        BottomSheetDialog di=new BottomSheetDialog(getActivity(),R.style.SheetDialog);

        TextView title=view.findViewById(R.id.dialog_sa_selectcats_title);
        ImageView back=view.findViewById(R.id.dialog_sa_selectcats_backbtn);
        RecyclerView list=view.findViewById(R.id.dialog_sa_selectcats_list);

        try {

            //cats_json=new JSONObject();

            //add content
            JSONObject st=new JSONObject(res);

            /*
            for(int i=0;i<st.length();i++){
                cats_json.put(""+(i+1),new JSONObject(st.getString(""+i)));
            }*/

            Log.e("Eee",st.toString());
            final sa_cats_adapter rva1=new sa_cats_adapter(st,di);
            list.setLayoutManager(new LinearLayoutManager(getActivity()));
            list.setItemAnimator(new DefaultItemAnimator());
            list.setNestedScrollingEnabled(false);
            list.setAdapter(rva1);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        title.setText("گروه");
        back.setOnClickListener(v -> di.dismiss());
        di.setContentView(view);
        di.show();


    }
    public class sa_cats_adapter extends RecyclerView.Adapter<sa_cats_adapter.MyViewHolder> {

        private JSONObject list;
        private BottomSheetDialog di;
        public class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView name;
            ImageView icon,tick;
            public MyViewHolder(View view) {
                super(view);
                name =  view.findViewById(R.id.row_sa_catsitem_title);
                icon =  view.findViewById(R.id.row_sa_catsitem_icon);
                tick =  view.findViewById(R.id.row_sa_catsitem_tick);
            }
        }

        public sa_cats_adapter(JSONObject list,BottomSheetDialog ddi) {
            this.list = list;
            this.di = ddi;
        }

        @Override
        public sa_cats_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_sa_catsitem, parent, false);
            return new sa_cats_adapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final sa_cats_adapter.MyViewHolder holder, final int position) {

            try {
                JSONObject row=new JSONObject(list.getString(position+""));
                holder.name.setText(row.getString("name"));
                holder.tick.setVisibility(View.GONE);
                Picasso
                        .get()
                        .load(Params.pic_cat+row.getString("icon"))
                        .into(holder.icon);
                holder.icon.setColorFilter(khakestrai0);
                if(cats_id.equals(row.getString("val"))){
                    holder.name.setTextColor(Abi1);
                    holder.icon.setColorFilter(Abi1);
                }

                holder.itemView.setOnClickListener(v -> {

                    holder.name.setTextColor(Abi1);
                    holder.icon.setColorFilter(Abi1);
                    holder.tick.setVisibility(View.VISIBLE);

                    try {
                        cats_id=row.getString("val");
                        catsname.setText(row.getString("name"));
                        get_subcats_list();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                di.dismiss();
                            }
                        },100);
                    } catch (JSONException e) {
                        e.printStackTrace();
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


    private void get_subcats_list(){

        subcatsname.setText("لطفا صبر کنید ....");
        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_prefilter,
                response -> {
                    //make_list_subcatspinner(response);
                    subcatsname.setText("انتخاب کنید");
                    Log.e("xxxxx",response);
                    subcatscontent=response;
                },
                error -> {
                    Toast.makeText(getActivity(),"خطا در دریافت اطلاعات",Toast.LENGTH_LONG).show();
                })

        {
            @Override
            public byte[] getBody() {
                String str = "{\"type\":\"listcat\",\"val\":\""+cats_id+"\",\"remove_first\":\"true\"}";
                Log.e("bbbbb",str);
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
    public void show_select_subcat_dialog(String res){

        LayoutInflater inflater = getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog_sa_tags,null,false);
        BottomSheetDialog di=new BottomSheetDialog(getActivity(),R.style.SheetDialog);

        TextView title=view.findViewById(R.id.dialog_sa_tags_title);
        ImageView back=view.findViewById(R.id.dialog_sa_tags_back);
        FlowLayout tlist=view.findViewById(R.id.dialog_sa_tags_tagslist);
        ElasticLayout disubmit=view.findViewById(R.id.dialog_sa_tags_submit);

        disubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(subcats.size()>0){

                    subcatsstring="";
                    for(int i=0;i<subcats.size();i++){
                        subcatsstring+=subcats.get(i)+",";
                    }
                    get_tags_list(subcatsstring);
                    get_docs_list(subcatsstring);
                    di.dismiss();

                }else{
                    Toast.makeText(getApplicationContext(),"لطفا یک گزینه انتخاب نمایید",Toast.LENGTH_LONG).show();
                }
            }
        });
        try {

            JSONObject sclist=new JSONObject(res);

            for(int i=0;i<sclist.length();i++){

                View v=getLayoutInflater().inflate(R.layout.row_tags2,null,false);
                TextView tt1=v.findViewById(R.id.row_tags2_name);
                LinearLayout ll=v.findViewById(R.id.row_tags2_ll);



                JSONObject tt=new JSONObject(sclist.getString(i+"")); ;
                tt1.setText(tt.getString("name"));
                if(subcats.contains(tt.getString("val"))){
                    ll.setBackgroundResource(R.drawable.row_tags2_back2);
                    tt1.setTextColor(Abi1);
                }



                FlowLayout.LayoutParams llp=new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                llp.setMargins(20,10,20,10);
                v.setLayoutParams(llp);

                ll.setOnClickListener(v1 -> {

                    try {
                        if(subcats.contains(tt.getString("val"))){

                            subcats.remove(tt.getString("val"));
                            subcats_name.remove(tt.getString("name"));
                            ll.setBackgroundResource(R.drawable.row_tags2_back1);
                            tt1.setTextColor(khakestrai0);

                        }else{

                            subcats.add(tt.getString("val"));
                            subcats_name.add(tt.getString("name"));
                            ll.setBackgroundResource(R.drawable.row_tags2_back2);
                            tt1.setTextColor(Abi1);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    reload_subcats_name();
                });

                tlist.addView(v);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        title.setText("دسته ها");
        back.setOnClickListener(v -> di.dismiss());
        di.setContentView(view);
        di.show();


    }
    private void reload_subcats_name(){
        String temp="";
        for(int i=0;i<subcats.size();i++){
            temp+=subcats_name.get(i)+" , ";
        }
        if(temp.length()>3){
            temp = temp.substring(0, temp.length() - 3);
        }
        subcatsname.setText(temp);


    }


    private void get_tags_list(String subcats){
        tagsname.setText("لطفا صبر کنید ...");
        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_gettags,
                response -> {
                    //fill_list_tags(response);
                    tagsname.setText("انتخاب کنید");
                    Log.e("gettags",response);
                    tagscontent=response;
                },
                error -> {})

        {
            @Override
            public byte[] getBody() {
                String str = "{\"q\":\"\",\"category_id\":\""+subcats+"\"}";
                Log.e("gettagsbody",str);
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
    public void show_select_tags_dialog(String res){

        LayoutInflater inflater = getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog_sa_tags,null,false);
        BottomSheetDialog di=new BottomSheetDialog(getActivity(),R.style.SheetDialog);

        TextView title=view.findViewById(R.id.dialog_sa_tags_title);
        ImageView back=view.findViewById(R.id.dialog_sa_tags_back);
        FlowLayout tlist=view.findViewById(R.id.dialog_sa_tags_tagslist);
        ElasticLayout disubmit=view.findViewById(R.id.dialog_sa_tags_submit);

        disubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tags.size()>0){

                    tagsstring="";
                    for(int i=0;i<tags.size();i++){
                        tagsstring+=tags.get(i)+",";
                    }
                    di.dismiss();

                }else{
                    Toast.makeText(getApplicationContext(),"لطفا یک گزینه انتخاب نمایید",Toast.LENGTH_LONG).show();
                }
            }
        });
        try {

            JSONObject sclist=new JSONObject(res);
            if(sclist.length()==0){
                Toast.makeText(getApplicationContext(),"محتوایی برای نمایش موجود نیست",Toast.LENGTH_LONG).show();
            }
            for(int i=0;i<sclist.length();i++){

                View v=getLayoutInflater().inflate(R.layout.row_tags2,null,false);
                TextView tt1=v.findViewById(R.id.row_tags2_name);
                LinearLayout ll=v.findViewById(R.id.row_tags2_ll);

                JSONObject tt=new JSONObject(sclist.getString(i+"")); ;
                tt1.setText(tt.getString("name"));

                if(tags.contains(tt.getString("id"))){
                    ll.setBackgroundResource(R.drawable.row_tags2_back2);
                    tt1.setTextColor(Abi1);
                }

                FlowLayout.LayoutParams llp=new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                llp.setMargins(10,10,10,10);
                v.setLayoutParams(llp);

                ll.setOnClickListener(v1 -> {

                    try {
                        if(tags.contains(tt.getString("id"))){

                            tags.remove(tt.getString("id"));
                            tags_name.remove(tt.getString("name"));
                            ll.setBackgroundResource(R.drawable.row_tags2_back1);
                            tt1.setTextColor(khakestrai0);

                        }else{

                            tags.add(tt.getString("id"));
                            tags_name.add(tt.getString("name"));
                            ll.setBackgroundResource(R.drawable.row_tags2_back2);
                            tt1.setTextColor(Abi1);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    reload_tags_name();
                });

                tlist.addView(v);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        title.setText("برچسب ها");
        back.setOnClickListener(v -> di.dismiss());
        di.setContentView(view);
        di.show();


    }
    private void reload_tags_name(){
        String temp="";
        for(int i=0;i<tags.size();i++){
            temp+=tags_name.get(i)+" , ";
        }
        if(temp.length()>3){
            temp = temp.substring(0, temp.length() - 3);
        }
        tagsname.setText(temp);


    }


    public void show_select_city_dialog(){

        LayoutInflater inflater = getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog_sa_city,null,false);
        BottomSheetDialog di=new BottomSheetDialog(getActivity(),R.style.SheetDialog);

        TextView title=view.findViewById(R.id.dialog_sa_city_title);
        ImageView back=view.findViewById(R.id.dialog_sa_city_backbtn);
        RecyclerView tlist=view.findViewById(R.id.dialog_sa_city_list);
        EditText search=view.findViewById(R.id.dialog_sa_city_search);


        start_listing_citylist(cityfullitems,tlist,di);


        title.setText("برچسب ها");
        back.setOnClickListener(v -> di.dismiss());
        di.setContentView(view);
        di.show();

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {

                String searchword=search.getText().toString();
                tempsearch=new JSONObject();
                try {
                    int ctr=0;

                    for (int i=0;i<cityfullitems.length();i++){

                        JSONObject tmp=new JSONObject(cityfullitems.getString(i+""));
                        if(tmp.getString("name").indexOf(searchword)>=0){
                            tempsearch.put(ctr+"",tmp);
                            ctr++;
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                start_listing_citylist(tempsearch,tlist,di);
            }
        });




    }
    private void get_city_list(){

        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_prefilter,
                response -> {
                    Log.e("citylist",response);
                    citycontent=response;
                    try {
                        cityfullitems =new JSONObject(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    fun.show_debug_dialog(2,error.toString(), Params.ws_prefilter);
                    fun.show_error_internet();
                })

        {
            @Override
            public byte[] getBody() {
                String str = "{\"type\":\"listcity\",\"val\":0}";
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
    private void start_listing_citylist(JSONObject res,RecyclerView list,BottomSheetDialog di){

        final sa_city_adapter rva1=new sa_city_adapter(res,di);
        list.setItemAnimator(new DefaultItemAnimator());
        list.setAdapter(rva1);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));

    }
    public class sa_city_adapter extends RecyclerView.Adapter<sa_city_adapter.MyViewHolder> {

        public JSONObject mItemList;
        private BottomSheetDialog di;
        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView name;
            CheckBox check;
            ImageView icon;
            public MyViewHolder(View view) {
                super(view);
                name = itemView.findViewById(R.id.prefilter_row_listtag_name);
                check = itemView.findViewById(R.id.prefilter_row_listtag_check);
                icon = itemView.findViewById(R.id.prefilter_row_listtag_icon);

            }
        }

        public sa_city_adapter(JSONObject itemList,BottomSheetDialog ddi) {
            mItemList = itemList;
            this.di = ddi;
        }

        @Override
        public sa_city_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.prefilter_row_listtag, parent, false);
            return new sa_city_adapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final sa_city_adapter.MyViewHolder holder, final int position) {

            try {

                final JSONObject tmp=new JSONObject(mItemList.getString(position+""));
                holder.name.setText(tmp.getString("name"));
                holder.check.setVisibility(View.GONE);
                holder.name.setPadding(0,0,50,0);
                holder.itemView.setOnClickListener(v -> {
                    try {
                        city_id=tmp.getInt("val")+"";
                        ostanname.setText(tmp.getString("name")+"");
                        di.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });



            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        @Override
        public int getItemCount() {
            return mItemList.length();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }
    }


    ////step 4
    private void get_docs_list(String subcats){


        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_getcertificate,
                response -> {
                    //fill_list_docs(response);
                    docscontent=response;
                    Log.e("get_docs",response);
                },
                error -> {})

        {
            @Override
            public byte[] getBody() {
                String str = "{\"category_id\":\""+subcats+"\"}";
                Log.e("get_docs_bode",str);
                return str.getBytes();
            };

            public String getBodyContentType()
            {
                return "application/json; charset=utf-8";
            }
            @Override
            public Map<String, String> getHeaders()  throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("cookie", Claim);
                return params;
            }

        };
        Volley.newRequestQueue(getActivity()).add(rq);
    }
    private void fill_list_docs(String res){








        try {

            docslistjson=new JSONArray();
            JSONObject tmp1=new JSONObject();
            tmp1.put("type","new");
            docslistjson.put(tmp1);

            Log.e("cccc",docslistjson.toString());

            docs_list_adapter dla1 = new docs_list_adapter();
            listdocs.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true));
            listdocs.setItemAnimator(new DefaultItemAnimator());
            listdocs.setAdapter(dla1);


        }catch (Exception e){
            Log.e("err1",e.toString());
        }
    }
    public class docs_list_adapter extends RecyclerView.Adapter<docs_list_adapter.MyViewHolder> {

        public class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView name;
            public ImageView image;
            public LinearLayout placeholder;
            public MyViewHolder(View view) {
                super(view);
                name =  view.findViewById(R.id.row_sa_docs_text);
                image =  view.findViewById(R.id.row_sa_docs_image);
                placeholder =  view.findViewById(R.id.row_sa_docs_placeholder);

            }
        }

        public docs_list_adapter() {
        }

        @Override
        public docs_list_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_sa_docs, parent, false);
            return new docs_list_adapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final docs_list_adapter.MyViewHolder holder, final int position) {


            try {
                JSONObject tmp=new JSONObject(docslistjson.getString(position));

                if(tmp.getString("type").equals("education")){

                    holder.name.setText(tmp.getString("grade_name")+" "+tmp.getString("name"));
                    holder.image.setOnClickListener(v -> {
                        try {
                            start_upload("edu_"+tmp.getString("grade_id")+"_"+tmp.getString("id"),holder.image);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                    /*
                    holder.remove.setOnClickListener(v -> {
                        try {
                            delete_pic("edu_"+tmp.getString("grade_id")+"_"+tmp.getString("id"),holder.image);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });*/
                }else if(tmp.getString("type").equals("certificate")){
                    holder.name.setText(tmp.getString("name"));
                    holder.image.setOnClickListener(v -> {
                        try {
                            start_upload("cert_"+tmp.getString("id"),holder.image);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                    /*
                    holder.remove.setOnClickListener(v -> {
                        try {
                            delete_pic("cert_"+tmp.getString("id"),holder.image);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });*/

                }else if(tmp.getString("type").equals("new")){



                }





            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public int getItemCount() {
            return docslistjson.length();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }
    }

    private void fill_myimagearea(){

        View item=getLayoutInflater().inflate(R.layout.row_sa_docs,null,false);
        ImageView img=item.findViewById(R.id.row_sa_docs_image);
        myimagesarea.addView(item);
/*
        item.setOnClickListener(v -> {
            start_upload("g_4",img);
        });*/

        myimagesscroll.post(() -> myimagesscroll.fullScroll(View.FOCUS_RIGHT));

    }


    private void start_upload(String name,ImageView imgv){
        curent_file_name=name;
        curent_image_uploader=imgv;
        easyImage.openChooser(Submit_Adviser.this);
    }
    private void delete_pic(String name,ImageView img){

        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_removefiles,
                response -> {
                    if(response.equals("deleted")){
                        Toasty.success(getActivity(),"تصویر حذف شد",Toasty.LENGTH_SHORT).show();
                        Picasso.get().load(R.drawable.tmp_image2).into(img);
                        img.setAlpha(0.4f);
                    }else{
                        Toasty.error(getActivity(),"خطا در حذف تصویر",Toasty.LENGTH_SHORT).show();
                    }
                },
                error -> {})

        {
            @Override
            public byte[] getBody() {
                String str = "{\"file_name\":\""+name+"\"}";
                return str.getBytes();
            };

            public String getBodyContentType()
            {
                return "application/json; charset=utf-8";
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("cookie", Claim);
                return params;
            }

        };
        Volley.newRequestQueue(getActivity()).add(rq);
    }





//old code
/*
    private void submit_data(){


        String tmpsub=cats_id+",";
        for (int i=0;i<subcats.size();i++)
        {
            tmpsub+=subcats.get(i)+",";
        }

        String tmptags="";
        for (int i=0;i<tags.size();i++)
        {
            tmptags+=tags.get(i)+",";
        }

        if(name.getText().toString().length()<1){
            Toasty.warning(getActivity(),"مقدار نام صحیح وارد نشده است",Toasty.LENGTH_LONG).show();
        }else if(family.getText().toString().length()<1){
            Toasty.warning(getActivity(),"مقدار نام خانوادگی صحیح وارد نشده است",Toasty.LENGTH_LONG).show();
        }else if(codemeli.getText().toString().length()!=10){
            Toasty.warning(getActivity(),"مقدار کد ملی صحیح وارد نشده است",Toasty.LENGTH_LONG).show();

        }else if(price.getText().toString().length()<2){
            Toasty.warning(getActivity(),"قیمت اشتباه وارد شده است",Toasty.LENGTH_LONG).show();
        }else if(city_id.equals("")){
            Toasty.warning(getActivity(),"لطفا شهر را انتخاب کنید",Toasty.LENGTH_LONG).show();
        }else if(sheba.getText().toString().length()<10){
            Toasty.warning(getActivity(),"مقدار شماره شبا صحیح وارد نشده است",Toasty.LENGTH_LONG).show();
        }else if(desc_long.getText().toString().length()<5){
            Toasty.warning(getActivity(),"برای معرفی خود چیزی بنویسید",Toasty.LENGTH_LONG).show();
        }else if(desc_line.getText().toString().length()<5){
            Toasty.warning(getActivity(),"توضیح یک خطی را کامل وارد کنید",Toasty.LENGTH_LONG).show();
        }else if(desc_short.getText().toString().length()<5){
            Toasty.warning(getActivity(),"توضیح کوتاه را کامل کنید",Toasty.LENGTH_LONG).show();
        }else if(subcats.size()==0){
            Toasty.warning(getActivity(),"حداقل یک دسته بندی انتخاب کنید",Toasty.LENGTH_LONG).show();
        }else if(tags.size()==0){
            Toasty.warning(getActivity(),"حداقل یک تگ انتخاب کنید",Toasty.LENGTH_LONG).show();
        }else{


            try {
                JSONObject field=new JSONObject();
                field.put("sheba",sheba.getText().toString());
                field.put("tags",tmptags);
                field.put("categories",tmpsub);
                field.put("short_desc",desc_short.getText().toString());
                field.put("long_desc",desc_long.getText().toString());
                field.put("price",Integer.parseInt(price.getText().toString()));
                field.put("field",desc_line.getText().toString());

                if(mr.isChecked()){
                    field.put("gender",0);
                }else {
                    field.put("gender",1);
                }
                //field.put("phone",phone.getText().toString());
                field.put("city_id",Integer.parseInt(city_id));

                send_data_submit(field);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }




    }
    private void send_data_submit(JSONObject json){

        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_submitadviser,
                response -> {

                    if(response.equals("\"ok\"")){
                        new SweetAlertDialog(getActivity(),SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("پیغام")
                                .setContentText("اطلاعات شما با موفقیت ثبت شد")
                                .setConfirmText("باشه")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        finish();
                                    }
                                })
                                .show();
                    }else{
                        new SweetAlertDialog(getActivity(),SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("خطا")
                                .setContentText(response)
                                .setConfirmText("باشه")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                    }
                                })
                                .show();
                    }

                },
                error -> {
                    new SweetAlertDialog(getActivity(),SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("خطا")
                            .setContentText(error.toString())
                            .setConfirmText("باشه")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            })
                            .show();
                })

        {
            @Override
            public byte[] getBody() {
                Log.e("submit_body",json.toString());
                String str = json.toString();
                return str.getBytes();
            };

            public String getBodyContentType()
            {
                return "application/json; charset=utf-8";
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("cookie", Claim);
                return params;
            }

        };
        Volley.newRequestQueue(getActivity()).add(rq);
    }


    private void start_upload(String name,ImageView imgv){


        curent_file_name=name;
        curent_image_uploader=imgv;
        easyImage.openChooser(Submit_Adviser.this);



    }

    private void delete_pic(String name,ImageView img){

        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_removefiles,
                response -> {
                    if(response.equals("deleted")){
                        Toasty.success(getActivity(),"تصویر حذف شد",Toasty.LENGTH_SHORT).show();
                        Picasso.get().load(R.drawable.tmp_image2).into(img);
                        img.setAlpha(0.4f);
                    }else{
                        Toasty.error(getActivity(),"خطا در حذف تصویر",Toasty.LENGTH_SHORT).show();
                    }
                },
                error -> {})

        {
            @Override
            public byte[] getBody() {
                String str = "{\"file_name\":\""+name+"\"}";
                return str.getBytes();
            };

            public String getBodyContentType()
            {
                return "application/json; charset=utf-8";
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("cookie", Claim);
                return params;
            }

        };
        Volley.newRequestQueue(getActivity()).add(rq);
    }


    private void make_list_catspinner(String res){
        Log.e("ceatlist",res);
        try {

            cats_json=new JSONObject();

            //add first item
            JSONObject ft=new JSONObject();
            ft.put("name","لطفا انتخاب کنید");
            ft.put("val","0");
            cats_json.put("0",ft);

            //add content
            JSONObject st=new JSONObject(res);


            for(int i=0;i<st.length();i++){
                cats_json.put(""+(i+1),new JSONObject(st.getString(""+i)));
            }


            select_cat.setAdapter(new select_cat_adapter(getActivity()));
        } catch (JSONException e) {
            e.printStackTrace();
        }



        select_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    cats_id=new JSONObject(cats_json.getString(position+"")).getString("val");
                    if(cats_id.equals("0")){
                        select_subcat.setVisibility(View.INVISIBLE);
                    }else{
                        select_subcat.setVisibility(View.VISIBLE);
                        get_subcats_list(cats_id);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





    }
    private class select_cat_adapter extends BaseAdapter {
        LayoutInflater inflter;

        public select_cat_adapter(Context applicationContext) {
            inflter = (LayoutInflater.from(applicationContext));
        }

        @Override
        public int getCount() {
            return cats_json.length();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = inflter.inflate(R.layout.spinner_row1, null);


            try {
                JSONObject row=new JSONObject(cats_json.getString(i+""));
                TextView name =  view.findViewById(R.id.spinner_row1_text);
                MaterialIconView arow=view.findViewById(R.id.spinner_row1_arrow);
                if(i==0){
                    arow.setVisibility(View.VISIBLE);
                }
                name.setText(row.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }



            return view;
        }
    }



    private void get_subcats_list(String catid){
        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_prefilter,
                response -> {
                    make_list_subcatspinner(response);
                },
                error -> {})

        {
            @Override
            public byte[] getBody() {
                String str = "{\"type\":\"listcat\",\"val\":\""+catid+"\",\"remove_first\":\"true\"}";
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
    private void make_list_subcatspinner(String res){
        Log.e("subceatlist",res);
        try {
            subcats_json=new JSONObject();

            //add first item
            JSONObject ft=new JSONObject();
            ft.put("name","لطفا انتخاب کنید");
            ft.put("val","0");
            subcats_json.put("0",ft);

            //add content
            JSONObject st=new JSONObject(res);

            for(int i=0;i<st.length();i++){
                subcats_json.put(""+(i+1),new JSONObject(st.getString(""+i)));
            }

            select_subcat.setAdapter(new select_subcat_adapter(getActivity()));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        select_subcat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {
                    JSONObject tmp=new JSONObject(subcats_json.getString(position+""));
                    String subcats_id=tmp.getString("val");
                    String subcats_n=tmp.getString("name");
                    if(subcats_id.equals("0")){

                    }else{
                        if(subcats.contains(subcats_id)){//hast
                            for(int i=0;i<subcats.size();i++){
                                if(subcats.get(i).equals(subcats_id)){
                                    subcats.remove(i);
                                    subcats_name.remove(i);
                                }
                            }
                        }else{//nist
                            subcats.add(subcats_id);
                            subcats_name.add(subcats_n);
                        }
                        llsa.notifyItemInserted(subcats.size());
                    }

                    String tmpsub="";
                    for (int i=0;i<subcats.size();i++)
                    {
                        tmpsub+=subcats.get(i)+",";
                    }
                    get_docs_list(tmpsub);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });







    }
    private class select_subcat_adapter extends BaseAdapter {
        LayoutInflater inflter;

        public select_subcat_adapter(Context applicationContext) {
            inflter = (LayoutInflater.from(applicationContext));
        }

        @Override
        public int getCount() {
            return subcats_json.length();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = inflter.inflate(R.layout.spinner_row1, null);
            try {
                JSONObject row=new JSONObject(subcats_json.getString(i+""));
                TextView name =  view.findViewById(R.id.spinner_row1_text);
                MaterialIconView arow=view.findViewById(R.id.spinner_row1_arrow);
                if(i==0){
                    arow.setVisibility(View.VISIBLE);
                }
                name.setText(row.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return view;
        }
    }

    private void reload_listsubcats(){


        llsa=new load_listsubcat_adapter();
        RtlGridLayoutManager layoutManager = new RtlGridLayoutManager(getActivity(), 2);
        subcatslist.setLayoutManager(layoutManager);
        subcatslist.setItemAnimator(new DefaultItemAnimator());
        subcatslist.setAdapter(llsa);

        subcatslist.post(() -> {

        });



    }
    public class load_listsubcat_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final int VIEW_TYPE_SELECTED= 2;


        public load_listsubcat_adapter() {
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_tag_row1, parent, false);
            return new load_listsubcat_adapter.selectedholder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

            selected((load_listsubcat_adapter.selectedholder) viewHolder, position);
        }

        @Override
        public int getItemCount() {
            return subcats.size();
        }


        @Override
        public int getItemViewType(int position) {

            return VIEW_TYPE_SELECTED;

        }


        private class selectedholder extends RecyclerView.ViewHolder {

            public TextView name;
            MaterialIconView remove;
            public LinearLayout ll;

            public selectedholder(@NonNull View itemView) {
                super(itemView);
                name =  itemView.findViewById(R.id.category_tag_row1_name);
                remove =  itemView.findViewById(R.id.category_tag_row1_remove);
                ll =  itemView.findViewById(R.id.category_tag_row1_ll);

            }
        }
        private void selected(final load_listsubcat_adapter.selectedholder holder, int position) {


            holder.ll.setVisibility(View.VISIBLE);
            holder.name.setText(subcats_name.get(position));
            holder.ll.setBackgroundResource(R.drawable.z_catgory_tag_back1);
            holder.ll.setOnClickListener(v -> {

                try{
                    String subcats_id=subcats.get(position);
                    for(int i=0;i<subcats.size();i++){
                        if(subcats.get(i).equals(subcats_id)){
                            subcats.remove(i);
                            subcats_name.remove(i);
                            notifyDataSetChanged();
                            tags=new ArrayList<String>();
                            tags_name=new ArrayList<String>();
                            lsta.notifyDataSetChanged();
                        }
                    }
                    String tmpsub="";
                    for (int i=0;i<subcats.size();i++)
                    {
                        tmpsub+=subcats.get(i)+",";
                    }
                    get_docs_list(tmpsub);
                }catch (Exception e){
                }

            });


        }





    }



    private void getd_tags_list(String q,String subcats){
        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_gettags,
                response -> {
                    fill_list_tags(response);
                    Log.e("gettags",response);
                },
                error -> {})

        {
            @Override
            public byte[] getBody() {
                String str = "{\"q\":\""+q+"\",\"category_id\":\""+subcats+"\"}";
                Log.e("gettagsbody",str);
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
    private void fill_list_tags(String res){
        try {

            tagslistjsonarray=new JSONObject(res);
            tla1 = new tag_list_adapter();
            taglist.setLayoutManager(new LinearLayoutManager(getActivity()));
            taglist.setItemAnimator(new DefaultItemAnimator());
            taglist.setAdapter(tla1);


        }catch (Exception e){

        }
    }
    public class tag_list_adapter extends RecyclerView.Adapter<tag_list_adapter.MyViewHolder> {

        public class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView name,des;
            public MaterialIconView remove,icon;
            public MyViewHolder(View view) {
                super(view);
                name =  view.findViewById(R.id.row_search_history_text);
                des =  view.findViewById(R.id.row_search_history_des);
                remove =  view.findViewById(R.id.row_search_history_remove);
                icon =  view.findViewById(R.id.row_search_history_icon);
            }
        }

        public tag_list_adapter() {
        }

        @Override
        public tag_list_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_search_history, parent, false);
            return new tag_list_adapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final tag_list_adapter.MyViewHolder holder, final int position) {


            try {
                JSONObject tmp=new JSONObject(tagslistjsonarray.getString(position+""));
                holder.name.setText(tmp.getString("name"));
                holder.name.setTextSize(12);
                holder.remove.setVisibility(View.INVISIBLE);
                holder.name.setTextColor(blackcolor);
                holder.icon.setVisibility(View.INVISIBLE);

                if(tags.contains(tmp.getString("id"))){
                    holder.name.setTextColor(bluecolor);
                }

                holder.itemView.setOnClickListener(v -> {

                    try {
                        if(tags.contains(tmp.getString("id"))){//hast
                            for(int i=0;i<tags.size();i++){
                                if(tags.get(i).equals(tmp.getString("id"))){
                                    tags.remove(i);
                                    tags_name.remove(i);
                                }
                            }
                        }else{//nist
                            tags.add(tmp.getString("id"));
                            tags_name.add(tmp.getString("name"));

                        }
                        String tmpsub="";
                        for (int i=0;i<subcats.size();i++)
                        {
                            tmpsub+=subcats.get(i)+",";
                        }

                        //notifyDataSetChanged();
                        lsta.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    searchtag=false;
                    tag_Search.setText("");
                    tagslistjsonarray=new JSONObject();
                    tla1.notifyItemRangeRemoved(0, 100);
                  //  llsa.notifyItemInserted(subcats.size());

                });
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public int getItemCount() {
            return tagslistjsonarray.length();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }
    }


    private void reload_listtags(){
        lsta=new load_selectedtags_adapter();

        selectedtagslist.setLayoutManager(new RtlGridLayoutManager(getActivity(),3));
        selectedtagslist.setItemAnimator(new DefaultItemAnimator());
        selectedtagslist.setAdapter(lsta);
    }
    public class load_selectedtags_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final int VIEW_TYPE_SELECTED= 2;


        public load_selectedtags_adapter() {
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_tag_row1, parent, false);
            return new load_selectedtags_adapter.selectedholder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
            selected((load_selectedtags_adapter.selectedholder) viewHolder, position);
        }

        @Override
        public int getItemCount() {
            return tags.size();
        }


        @Override
        public int getItemViewType(int position) {

            return VIEW_TYPE_SELECTED;

        }


        private class selectedholder extends RecyclerView.ViewHolder {

            public TextView name;
            MaterialIconView remove;
            public LinearLayout ll;

            public selectedholder(@NonNull View itemView) {
                super(itemView);
                name =  itemView.findViewById(R.id.category_tag_row1_name);
                remove =  itemView.findViewById(R.id.category_tag_row1_remove);
                ll =  itemView.findViewById(R.id.category_tag_row1_ll);

            }
        }
        private void selected(final load_selectedtags_adapter.selectedholder holder, int position) {


            holder.ll.setVisibility(View.VISIBLE);
            holder.remove.setVisibility(View.GONE);
            holder.name.setText(tags_name.get(position));
            holder.name.setTextSize(12);
            holder.ll.setBackgroundResource(R.drawable.z_catgory_tag_back1);
            holder.ll.setOnClickListener(v -> {

                try{
                    String tagsid_id=tags.get(position);
                    for(int i=0;i<tags.size();i++){
                        if(tags.get(i).equals(tagsid_id)){
                            tags.remove(i);
                            tags_name.remove(i);
                            tla1.notifyDataSetChanged();
                            notifyDataSetChanged();
                        }
                    }



                }catch (Exception e){
                }

            });


        }





    }


    private void get_docs_list(String subcats){


        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_getcertificate,
                response -> {
                    fill_list_docs(response);
                    Log.e("get_docs",response);
                },
                error -> {})

        {
            @Override
            public byte[] getBody() {
                String str = "{\"category_id\":\""+subcats+"\"}";
                Log.e("get_docs_bode",str);
                return str.getBytes();
            };

            public String getBodyContentType()
            {
                return "application/json; charset=utf-8";
            }
            @Override
            public Map<String, String> getHeaders()  throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("cookie", Claim);
                return params;
            }

        };
        Volley.newRequestQueue(getActivity()).add(rq);
    }
    private void fill_list_docs(String res){
        try {

            docslistjson=new JSONArray(res);
            dla1 = new docs_list_adapter();
            listdocs.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true));
            listdocs.setItemAnimator(new DefaultItemAnimator());
            listdocs.setAdapter(dla1);


        }catch (Exception e){
            Log.e("err1",e.toString());
        }
    }
    public class docs_list_adapter extends RecyclerView.Adapter<docs_list_adapter.MyViewHolder> {

        public class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView name;
            public MaterialIconView remove;
            public ImageView image;
            public MyViewHolder(View view) {
                super(view);
                name =  view.findViewById(R.id.row_sa_docs_text);
                remove =  view.findViewById(R.id.row_sa_docs_remove);
                image =  view.findViewById(R.id.row_sa_docs_image);
            }
        }

        public docs_list_adapter() {
        }

        @Override
        public docs_list_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_sa_docs, parent, false);
            return new docs_list_adapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final docs_list_adapter.MyViewHolder holder, final int position) {


            try {
                JSONObject tmp=new JSONObject(docslistjson.getString(position));

                if(tmp.getString("type").equals("education")){
                    holder.name.setText(tmp.getString("grade_name")+" "+tmp.getString("name"));
                    holder.image.setOnClickListener(v -> {
                        try {
                            start_upload("edu_"+tmp.getString("grade_id")+"_"+tmp.getString("id"),holder.image);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                    holder.remove.setOnClickListener(v -> {
                        try {
                            delete_pic("edu_"+tmp.getString("grade_id")+"_"+tmp.getString("id"),holder.image);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                }else if(tmp.getString("type").equals("certificate")){
                    holder.name.setText(tmp.getString("name"));
                    holder.image.setOnClickListener(v -> {
                        try {
                            start_upload("cert_"+tmp.getString("id"),holder.image);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                    holder.remove.setOnClickListener(v -> {
                        try {
                            delete_pic("cert_"+tmp.getString("id"),holder.image);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                }





            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public int getItemCount() {
            return docslistjson.length();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }
    }

    */


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == VideoPicker.VIDEO_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {

            List<String> mPaths = data.getStringArrayListExtra(VideoPicker.EXTRA_VIDEO_PATH);

            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(Submit_Adviser.this, Uri.parse(mPaths.get(0)));
            //String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            video_procesor(mPaths.get(0));


        }else if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {

            if(curent_file_name.equals("avatar")){
                final Uri resultUri = UCrop.getOutput(data);
                curent_image_uploader.setImageURI(resultUri);
                File f = new File(resultUri.getPath());
                AvatarUploadAsyncTask uploader = new AvatarUploadAsyncTask(getActivity(), f);
                uploader.execute();
            }else{
                final Uri resultUri = UCrop.getOutput(data);
                curent_image_uploader.setImageURI(resultUri);
                File f = new File(resultUri.getPath());
                ImageUploadAsyncTask uploader = new ImageUploadAsyncTask(getActivity(), f);
                uploader.execute();
            }




        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);

        } else {
            easyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
                @Override
                public void onMediaFilesPicked(MediaFile[] imageFiles, MediaSource source) {
                    UCrop.Options options = new UCrop.Options();
                    options.setCompressionQuality(80);
                    options.setMaxBitmapSize(10000);

                    UCrop.of(Uri.fromFile(new File(imageFiles[0].getFile().toString())), Uri.fromFile(new File(getCacheDir(), curent_file_name)))
                            .withOptions(options)
                            .start(Submit_Adviser.this);
                }

                @Override
                public void onImagePickerError(@NonNull Throwable error, @NonNull MediaSource source) {
                    error.printStackTrace();
                }
                @Override
                public void onCanceled(@NonNull MediaSource source) {
                }
            });
        }






    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 3670:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                }else{
                    finish();
                }
                break;

            default:
                break;
        }
    }

    private void video_procesor(final String input){

        File ff=new File(input);
        int file_size = Integer.parseInt(String.valueOf(ff.length()/1024));
        if(file_size>10000){
            Toasty.warning(getActivity(),"متاسفانه ویدیو شما بیشتر از 10 مگابایت است",Toasty.LENGTH_LONG).show();
        }else {
            //star_up(ff);
            curent_file_name="g_1";
            VideoUploadAsyncTask uploader=new VideoUploadAsyncTask(getActivity(),ff);
            uploader.execute();


        }

    }

    private class ImageUploadAsyncTask extends AsyncTask<Void, Integer, String> {

        HttpClient httpClient = new DefaultHttpClient();
        private Context context;
        private Exception exception;
        private File file;
        SweetAlertDialog prog;
        private ImageUploadAsyncTask(Context context, File f) {
            this.context = context;
            file=f;
            prog=new SweetAlertDialog(getActivity(),SweetAlertDialog.PROGRESS_TYPE);
            prog.setTitleText("لطفا صبر کنید");
            prog.setContentText("در حال آپلود عکس");
        }

        @Override
        protected String doInBackground(Void... params) {

            HttpResponse httpResponse = null;
            HttpEntity httpEntity = null;
            String responseString = "-";

            try {
                HttpPost httpPost = new HttpPost(Params.ws_uploadfiles);
                MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();


                multipartEntityBuilder.addPart("file", new FileBody(file));
                multipartEntityBuilder.addPart("file_name", new StringBody(curent_file_name, ContentType.TEXT_PLAIN));


                MyHttpEntity.ProgressListener progressListener =
                        progress -> publishProgress((int) progress);

                httpPost.setEntity(new MyHttpEntity(multipartEntityBuilder.build(),progressListener));

                httpPost.setHeader("cookie", Claim);

                httpResponse = httpClient.execute(httpPost);
                httpEntity = httpResponse.getEntity();

                int statusCode = httpResponse.getStatusLine().getStatusCode();

                if (statusCode == 200) {
                    responseString = EntityUtils.toString(httpEntity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }
            } catch (UnsupportedEncodingException | ClientProtocolException e) {
                e.printStackTrace();
                responseString=e.toString();
                this.exception = e;
            } catch (IOException e) {
                responseString=e.toString();
                e.printStackTrace();
            }
            return responseString;
        }

        @Override
        protected void onPreExecute() {
            prog.show();
        }

        @Override
        protected void onPostExecute(String result) {
            prog.dismiss();
            try {
                JSONObject res = new JSONObject(result);
                if(!res.getString("type").equals("")){
                    curent_image_uploader.setAlpha(1f);
                }
            } catch (JSONException e) {
                Toast.makeText(getActivity(),"بروز خطا در آپلود عکس",Toast.LENGTH_LONG).show();
                Picasso.get().load(R.drawable.tmp_image2).into(curent_image_uploader);
                curent_image_uploader.setAlpha(0.4f);
            }
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
        }
    }

    private class VideoUploadAsyncTask extends AsyncTask<Void, Integer, String> {

        HttpClient httpClient = new DefaultHttpClient();
        private Context context;
        private Exception exception;
        private File file;
        SweetAlertDialog prog;
        private VideoUploadAsyncTask(Context context, File f) {
            this.context = context;
            file=f;
            prog=new SweetAlertDialog(getActivity(),SweetAlertDialog.PROGRESS_TYPE);
            prog.setTitleText("لطفا صبر کنید");
            prog.setContentText("در حال آپلود ویدیو");
        }

        @Override
        protected String doInBackground(Void... params) {

            HttpResponse httpResponse = null;
            HttpEntity httpEntity = null;
            String responseString = "-";

            try {
                HttpPost httpPost = new HttpPost(Params.ws_uploadfiles);
                MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();

                Log.e("filename1",file.getName());
                Log.e("filename2",curent_file_name);

                multipartEntityBuilder.addPart("file", new FileBody(file));
                multipartEntityBuilder.addPart("file_name", new StringBody(curent_file_name, ContentType.TEXT_PLAIN));


                MyHttpEntity.ProgressListener progressListener =
                        progress -> publishProgress((int) progress);

                httpPost.setEntity(new MyHttpEntity(multipartEntityBuilder.build(),progressListener));

                httpPost.setHeader("cookie", Claim);

                httpResponse = httpClient.execute(httpPost);
                httpEntity = httpResponse.getEntity();

                int statusCode = httpResponse.getStatusLine().getStatusCode();

                if (statusCode == 200) {
                    responseString = EntityUtils.toString(httpEntity);
                } else {
                    responseString = "Server Error! Status Code: "
                            + statusCode;
                }
            } catch (UnsupportedEncodingException | ClientProtocolException e) {
                e.printStackTrace();
                responseString=e.toString();
                this.exception = e;
            } catch (IOException e) {
                responseString=e.toString();
                e.printStackTrace();
            }
            return responseString;
        }

        @Override
        protected void onPreExecute() {
            prog.show();
        }

        @Override
        protected void onPostExecute(String result) {
            prog.dismiss();
            Log.e("videoupload",result);


            Toasty.error(getActivity(),result,Toast.LENGTH_LONG).show();
            /*
            try {
                JSONObject res = new JSONObject(result);
                if(!res.getString("type").equals("")){
                    curent_image_uploader.setAlpha(1f);
                }
            } catch (JSONException e) {
                Toast.makeText(getActivity(),"بروز خطا در آپلود ویدیو",Toast.LENGTH_LONG).show();
                Picasso.get().load(R.drawable.tmp_image2).into(curent_image_uploader);
                curent_image_uploader.setAlpha(0.4f);
            }*/
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
        }
    }

    private class AvatarUploadAsyncTask extends AsyncTask<Void, Integer, String> {

        HttpClient httpClient = new DefaultHttpClient();
        private Context context;
        private Exception exception;
        private File file;
        SweetAlertDialog prog;
        private AvatarUploadAsyncTask(Context context, File f) {
            this.context = context;
            file=f;
            prog=new SweetAlertDialog(getActivity(),SweetAlertDialog.PROGRESS_TYPE);
            prog.setTitleText("لطفا صبر کنید");
            prog.setContentText("در حال آپلود عکس");
        }

        @Override
        protected String doInBackground(Void... params) {

            HttpResponse httpResponse = null;
            HttpEntity httpEntity = null;
            String responseString = "-";

            try {
                HttpPost httpPost = new HttpPost(Params.ws_uploadavatar);
                MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();

                multipartEntityBuilder.addPart("avatar", new FileBody(file));

                MyHttpEntity.ProgressListener progressListener =
                        progress -> publishProgress((int) progress);

                httpPost.setEntity(new MyHttpEntity(multipartEntityBuilder.build(),progressListener));

                httpPost.setHeader("cookie", Claim);

                httpResponse = httpClient.execute(httpPost);
                httpEntity = httpResponse.getEntity();

                int statusCode = httpResponse.getStatusLine().getStatusCode();

                if (statusCode == 200) {
                    responseString = EntityUtils.toString(httpEntity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }
            } catch (UnsupportedEncodingException | ClientProtocolException e) {
                e.printStackTrace();
                responseString=e.toString();
                this.exception = e;
            } catch (IOException e) {
                responseString=e.toString();
                e.printStackTrace();
            }
            return responseString;
        }

        @Override
        protected void onPreExecute() {
            prog.show();
        }

        @Override
        protected void onPostExecute(String result) {
            prog.dismiss();


            try {
                JSONObject part=new JSONObject(result);
                if(!part.getString("image_original").equals("")){
                    curent_image_uploader.setAlpha(1f);
                    SharedPreferences.Editor ed=sp.edit();
                    ed.putString(Params.sp_avatar,part.getString("image_original"));
                    ed.commit();
                    Toasty.success(getActivity(),"آپلود با موفقیت انجام شد",Toast.LENGTH_LONG).show();
                }else{
                    Toasty.error(getActivity(),"بروز خطا در آپلود عکس",Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                Toasty.error(getActivity(),"بروز خطا در آپلود عکس",Toast.LENGTH_LONG).show();
            }



        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
        }
    }





    /////new ui
    private void setpage(int c){

        step1.setVisibility(View.GONE);
        step2.setVisibility(View.GONE);
        step3.setVisibility(View.GONE);
        step4.setVisibility(View.GONE);
        switch (c){
            case 1:
                step1.setVisibility(View.VISIBLE);
                pagenumber.setText(fun.farsi_sazi_adad("1/4"));
                Picasso.get().load(R.drawable.main_progressstep1).resize(150,150).into(pageprogress);
                break;

            case 2:
                step2.setVisibility(View.VISIBLE);
                pagenumber.setText(fun.farsi_sazi_adad("2/4"));
                Picasso.get().load(R.drawable.main_progressstep2).resize(150,150).into(pageprogress);
                break;

            case 3:
                step3.setVisibility(View.VISIBLE);
                pagenumber.setText(fun.farsi_sazi_adad("3/4"));
                Picasso.get().load(R.drawable.main_progressstep3).resize(150,150).into(pageprogress);
                break;

            case 4:
                step4.setVisibility(View.VISIBLE);
                pagenumber.setText(fun.farsi_sazi_adad("4/4"));
                Picasso.get().load(R.drawable.main_progressstep4).resize(150,150).into(pageprogress);
                break;

        }
        cpage=c;


    }
    private void show_dialog_sex(){

        final BottomSheetDialog di=new BottomSheetDialog(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_drop2, null);
        di.setContentView(dialogView);
        di.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ElasticLayout disub=di.findViewById(R.id.dialog_drop2_submit);
        final RadioButton item1=di.findViewById(R.id.dialog_drop2_item1);
        final RadioButton item2=di.findViewById(R.id.dialog_drop2_item2);

        if(sexid==0){
            item1.setChecked(true);
        }else{
            item2.setChecked(true);
        }


        disub.setOnClickListener(v -> {
            if (item1.isChecked()){
                sexid=0;
                sexname.setText("مرد");
                di.dismiss();
            }else if (item2.isChecked()){
                sexid=1;
                sexname.setText("زن");
                di.dismiss();
            }else{
                Toast.makeText(getApplicationContext(),"لطفا یک گزینه انتخاب نمایید",Toast.LENGTH_LONG).show();
            }

        });



        di.show();
    }


}
