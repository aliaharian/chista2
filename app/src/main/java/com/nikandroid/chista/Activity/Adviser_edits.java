package com.nikandroid.chista.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.nikandroid.chista.Functions.Function;
import com.nikandroid.chista.Params.Params;
import com.nikandroid.chista.R;
import com.nikandroid.chista.Utils.MyHttpEntity;
import com.nikandroid.chista.Utils.PCalendar;
import com.nikandroid.chista.Utils.StringChooser;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.skydoves.elasticviews.ElasticImageView;
import com.skydoves.elasticviews.ElasticLayout;
import com.squareup.picasso.Picasso;
import com.wefika.flowlayout.FlowLayout;
import com.yalantis.ucrop.UCrop;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import pl.aprilapps.easyphotopicker.ChooserType;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.MediaFile;
import pl.aprilapps.easyphotopicker.MediaSource;

public class Adviser_edits extends AppCompatActivity {



    EasyImage easyImage;
    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 5469;


    private SharedPreferences sp;
    Function fun;
    String Claim="",flag="";
    SweetAlertDialog erdi;
    ElasticImageView act_back;
    ProgressBar prog;
    SweetAlertDialog di;
    JSONObject vals;
    JSONObject cityfullitems;
    JSONObject tempsearch;
    String citycontent="",catscontent="",subcatscontent="",tagscontent="",cats_id="",subcatsstring="",tagsstring="";
    String city_id="";
    int khakestrai0=0,khakestari1=0,Abi1=0;
    int mplus=0;
    int ghermez,tire0;
    TextView oldtext;
    ArrayList<String> subcatsarray;
    ArrayList<String> subcats_name;
    ArrayList<String> tagsarray;
    ArrayList<String> tags_name;



    /////xml
    ImageView mainimage;
    TextView name,sexname,codemeli,bdate,sheba,city,field,longdesc,price,cats,subcats,tags;
    ElasticLayout changepic;
    ElasticImageView editname,editsex,editcodemeli,editbdate,editsheba,editcity,editfield,editprice,editdesc,editcat,editsubcat,edittags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adviser_edits);

        init();
        checkPermission();
        fill_page();
    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));    }

    private Context getActivity(){
        return Adviser_edits.this;
    }

    private void init(){

        sp=getSharedPreferences(Params.spmain,MODE_PRIVATE);
        fun=new Function(getActivity());
        Claim=sp.getString(Params.spClaim,"-");

        di=new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        di.setTitleText("لطفا صبر کنید");
        di.setContentText("در حال ارسال اطلاعات");

        getSupportActionBar().hide();
        act_back=findViewById(R.id.adviseredit_actionbar_back);
        mainimage=findViewById(R.id.ae_avatar_image);
        changepic=findViewById(R.id.ae_changepic);
        name=findViewById(R.id.ae_et_fullname);
        sexname=findViewById(R.id.ae_et_sexname);
        codemeli=findViewById(R.id.ae_et_codemeli);
        bdate=findViewById(R.id.ae_et_bdate);
        sheba=findViewById(R.id.ae_et_sheba);
        field=findViewById(R.id.ae_et_field);
        city=findViewById(R.id.ae_et_city);
        price=findViewById(R.id.ae_et_price);
        cats=findViewById(R.id.ae_tv_cats);
        subcats=findViewById(R.id.ae_tv_subcats);
        tags=findViewById(R.id.ae_tv_tags);
        longdesc=findViewById(R.id.ae_et_longdesc);

        editname=findViewById(R.id.ae_ei_editname);
        editsex=findViewById(R.id.ae_ei_editsex);
        editcodemeli=findViewById(R.id.ae_ei_editcodemeli);
        editbdate=findViewById(R.id.ae_ei_editbdate);
        editsheba=findViewById(R.id.ae_ei_editsheba);
        editcity=findViewById(R.id.ae_ei_editcity);
        editfield=findViewById(R.id.ae_ei_editfield);
        editprice=findViewById(R.id.ae_ei_editprice);
        editdesc=findViewById(R.id.ae_ei_editlongdesc);
        editcat=findViewById(R.id.ae_ei_editcat);
        editsubcat=findViewById(R.id.ae_ei_editsubcat);
        edittags=findViewById(R.id.ae_ei_edittags);


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
        ghermez=getResources().getColor(R.color.ghermez);
        tire0=getResources().getColor(R.color.tire0);


        try {
            vals=new JSONObject();
            vals.put("name",sp.getString(Params.sp_name,""));
            vals.put("last_name",sp.getString(Params.sp_lastname,""));
            vals.put("username",sp.getString(Params.sp_username,""));
            vals.put("national_code",sp.getString(Params.sp_national_code,""));
            vals.put("email",sp.getString(Params.sp_email,""));
            vals.put("city_id",sp.getString(Params.sp_city_id,""));
            vals.put("phone",sp.getString(Params.sp_mobile,""));
            vals.put("birth_time",sp.getString(Params.sp_bdate,""));
            vals.put("sheba",sp.getString(Params.sp_sheba,""));
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    private void fill_page(){

        load_imgs();
        get_city_list();
        get_cats_list();
        name.setText(sp.getString(Params.sp_name,"")+" "+sp.getString(Params.sp_lastname,""));

        ////////Q
        sexname.setText("مرد");
        codemeli.setText("-");
        sheba.setText("-");
        city.setText("-");
        bdate.setText("");


        easyImage = new EasyImage.Builder(Adviser_edits.this)
                .setChooserTitle("انتخاب تصویر")
                .setChooserType(ChooserType.CAMERA_AND_GALLERY)
                .setCopyImagesToPublicGalleryFolder(false)
                .allowMultiple(false)
                .build();

        changepic.setOnClickListener(view -> easyImage.openChooser(Adviser_edits.this));

        editname.setOnClickListener(v -> show_edit_box1("name"));
        editsex.setOnClickListener(v -> show_dialog_sex());
        editbdate.setOnClickListener(v ->  show_bdate_dialog("تاریخ تولد"));
        //editbdate.setOnClickListener(v ->  show_calendar_dialog("از تاریخ"));
        editcodemeli.setOnClickListener(v -> show_edit_box1("codemeli"));
        editcity.setOnClickListener(v -> show_select_city_dialog());
        editsheba.setOnClickListener(v -> show_edit_box1("sheba"));
        editfield.setOnClickListener(v -> show_edit_box1("field"));
        editprice.setOnClickListener(v -> show_edit_box1("price"));
        editdesc.setOnClickListener(v -> show_edit_box1("longdes"));

        editcat.setOnClickListener(v -> show_select_cat_dialog());
        editsubcat.setOnClickListener(v -> show_select_subcat_dialog());
        edittags.setOnClickListener(v -> show_select_tags_dialog());


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


        if(flag.equals("name")){

            f1.setText(sp.getString(Params.sp_name,""));
            f2.setText(sp.getString(Params.sp_lastname,""));
            title.setText("ویرایش");
            f1title.setText("نام");
            f1.setHint("نام");
            f2title.setText("نام خانوادگی");
            f2.setHint("نام خانوادگی");
            submit.setOnClickListener(v -> {

                name.setText(f1.getText().toString()+" "+f2.getText().toString());
                di.dismiss();
            });
            di.setContentView(view);
            di.show();
        }

        if(flag.equals("codemeli")){

            f1.setText(sp.getString(Params.sp_national_code,""));
            title.setText("ویرایش");
            f1title.setText("کد ملی");
            f1.setHint("کد ملی");
            f1.setInputType(InputType.TYPE_CLASS_NUMBER);
            f2ll.setVisibility(View.GONE);
            f1img.setImageResource(R.drawable.main_icon_userprofile);
            submit.setOnClickListener(v -> {
                codemeli.setText(f1.getText().toString());
                di.dismiss();
            });
            di.setContentView(view);
            di.show();
        }

        if(flag.equals("sheba")){

            f1.setText(sp.getString(Params.sp_sheba,""));
            title.setText("ویرایش");
            f1title.setText("شماره شبا");
            f1.setHint("شماره شبا");
            f2ll.setVisibility(View.GONE);
            f1.setInputType(InputType.TYPE_CLASS_NUMBER);
            f1img.setImageResource(R.drawable.main_icon_bank);
            submit.setOnClickListener(v -> {
                sheba.setText(f1.getText().toString());
                di.dismiss();
            });
            di.setContentView(view);
            di.show();
        }

        if(flag.equals("field")){


            title.setText("ویرایش");
            f1title.setText("جمله معرفی");
            f1.setHint("جمله معرفی");
            f2ll.setVisibility(View.GONE);
            f1img.setImageResource(R.drawable.main_icon_bank);
            submit.setOnClickListener(v -> {
                field.setText(f1.getText().toString());
                di.dismiss();
            });
            di.setContentView(view);
            di.show();
        }
        if(flag.equals("price")){

            title.setText("ویرایش");
            f1title.setText("هزینه مشاور به ازای  هر دقیقه");
            f1.setHint("هزینه مشاور به ازای  هر دقیقه");
            f1.setInputType(InputType.TYPE_CLASS_NUMBER);
            f2ll.setVisibility(View.GONE);
            f1img.setImageResource(R.drawable.main_icon_bank);
            submit.setOnClickListener(v -> {

                price.setText(f1.getText().toString());
                di.dismiss();

            });
            di.setContentView(view);
            di.show();
        }

        if(flag.equals("longdes")){

            title.setText("ویرایش");
            f1title.setText("شرح");
            f1.setHint("شرح");
            f1.setLines(5);
            f2ll.setVisibility(View.GONE);
            f1img.setImageResource(R.drawable.main_icon_bank);
            submit.setOnClickListener(v -> {

                longdesc.setText(f1.getText().toString());
                di.dismiss();

            });
            di.setContentView(view);
            di.show();
        }


        back.setOnClickListener(v -> di.dismiss());



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

        /*
        if(sexid==0){
            item1.setChecked(true);
        }else{
            item2.setChecked(true);
        }*/


        disub.setOnClickListener(v -> {
            if (item1.isChecked()){
                sexname.setText("مرد");
                di.dismiss();
            }else if (item2.isChecked()){
                sexname.setText("زن");
                di.dismiss();
            }else{
                Toast.makeText(getApplicationContext(),"لطفا یک گزینه انتخاب نمایید",Toast.LENGTH_LONG).show();
            }

        });



        di.show();
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


        title.setText("انتخاب شهر");
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
                        city.setText(tmp.getString("name")+"");
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



    private void get_cats_list(){

        cats.setText("لطفا صبر کنید...");
        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_prefilter,
                response -> {

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
    public void show_select_cat_dialog(){
        LayoutInflater inflater = getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog_sa_selectcats,null,false);
        BottomSheetDialog di=new BottomSheetDialog(getActivity(),R.style.SheetDialog);

        TextView title=view.findViewById(R.id.dialog_sa_selectcats_title);
        ImageView back=view.findViewById(R.id.dialog_sa_selectcats_backbtn);
        RecyclerView list=view.findViewById(R.id.dialog_sa_selectcats_list);

        try {

            //cats_json=new JSONObject();

            //add content
            JSONObject st=new JSONObject(catscontent);

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
                holder.itemView.setOnClickListener(v -> {

                    holder.name.setTextColor(Abi1);
                    holder.icon.setColorFilter(Abi1);
                    holder.tick.setVisibility(View.VISIBLE);

                    try {
                        cats_id=row.getString("val");
                        cats.setText(row.getString("name"));
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

        subcats.setText("لطفا صبر کنید ....");
        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_prefilter,
                response -> {
                    //make_list_subcatspinner(response);
                    subcats.setText("-");
                    subcatscontent=response;
                },
                error -> {
                    Toast.makeText(getActivity(),"خطا در دریافت اطلاعات",Toast.LENGTH_LONG).show();
                })

        {
            @Override
            public byte[] getBody() {
                String str = "{\"type\":\"listcat\",\"val\":\""+cats_id+"\",\"remove_first\":\"true\"}";
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
    public void show_select_subcat_dialog(){

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
                if(subcatsarray.size()>0){

                    subcatsstring="";
                    for(int i=0;i<subcatsarray.size();i++){
                        subcatsstring+=subcatsarray.get(i)+",";
                    }
                    get_tags_list(subcatsstring);
                    di.dismiss();

                }else{
                    Toast.makeText(getApplicationContext(),"لطفا یک گزینه انتخاب نمایید",Toast.LENGTH_LONG).show();
                }
            }
        });
        try {

            JSONObject sclist=new JSONObject(subcatscontent);

            for(int i=0;i<sclist.length();i++){

                View v=getLayoutInflater().inflate(R.layout.row_tags2,null,false);
                TextView tt1=v.findViewById(R.id.row_tags2_name);
                LinearLayout ll=v.findViewById(R.id.row_tags2_ll);

                JSONObject tt=new JSONObject(sclist.getString(i+"")); ;
                tt1.setText(tt.getString("name"));

                FlowLayout.LayoutParams llp=new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                llp.setMargins(20,10,20,10);
                v.setLayoutParams(llp);

                ll.setOnClickListener(v1 -> {

                    try {
                        if(subcatsarray.contains(tt.getString("val"))){

                            subcatsarray.remove(tt.getString("val"));
                            subcats_name.remove(tt.getString("name"));
                            ll.setBackgroundResource(R.drawable.row_tags2_back1);
                            tt1.setTextColor(khakestrai0);

                        }else{

                            subcatsarray.add(tt.getString("val"));
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
        for(int i=0;i<subcatsarray.size();i++){
            temp+=subcats_name.get(i)+" , ";
        }
        if(temp.length()>3){
            temp = temp.substring(0, temp.length() - 3);
        }
        subcats.setText(temp);


    }


    private void get_tags_list(String subcats){
        tags.setText("لطفا صبر کنید ...");
        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_gettags,
                response -> {
                    //fill_list_tags(response);
                    tags.setText("-");
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
    public void show_select_tags_dialog(){

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
                if(tagsarray.size()>0){

                    tagsstring="";
                    for(int i=0;i<tagsarray.size();i++){
                        tagsstring+=tagsarray.get(i)+",";
                    }
                    di.dismiss();

                }else{
                    Toast.makeText(getApplicationContext(),"لطفا یک گزینه انتخاب نمایید",Toast.LENGTH_LONG).show();
                }
            }
        });
        try {

            JSONObject sclist=new JSONObject(tagscontent);
            if(sclist.length()==0){
                Toast.makeText(getApplicationContext(),"محتوایی برای نمایش موجود نیست",Toast.LENGTH_LONG).show();
            }
            for(int i=0;i<sclist.length();i++){

                View v=getLayoutInflater().inflate(R.layout.row_tags2,null,false);
                TextView tt1=v.findViewById(R.id.row_tags2_name);
                LinearLayout ll=v.findViewById(R.id.row_tags2_ll);

                JSONObject tt=new JSONObject(sclist.getString(i+"")); ;
                tt1.setText(tt.getString("name"));

                FlowLayout.LayoutParams llp=new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                llp.setMargins(10,10,10,10);
                v.setLayoutParams(llp);

                ll.setOnClickListener(v1 -> {

                    try {
                        if(tagsarray.contains(tt.getString("id"))){

                            tagsarray.remove(tt.getString("id"));
                            tags_name.remove(tt.getString("name"));
                            ll.setBackgroundResource(R.drawable.row_tags2_back1);
                            tt1.setTextColor(khakestrai0);

                        }else{

                            tagsarray.add(tt.getString("id"));
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
        for(int i=0;i<tagsarray.size();i++){
            temp+=tags_name.get(i)+" , ";
        }
        if(temp.length()>3){
            temp = temp.substring(0, temp.length() - 3);
        }
        tags.setText(temp);


    }


    //calendar
    public void show_calendar_dialog(String name){

        LayoutInflater inflater = getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog_calendar,null,false);
        BottomSheetDialog di=new BottomSheetDialog(getActivity(),R.style.SheetDialog);

        TextView title=view.findViewById(R.id.dialog_calendar_title);
        ImageView back=view.findViewById(R.id.dialog_calendar_backbtn);
        RecyclerView list=view.findViewById(R.id.dialog_calendar_list);
        TextView month=view.findViewById(R.id.dialog_calendar_month);
        ImageView pmonth=view.findViewById(R.id.dialog_calendar_pmonth);
        ImageView nmonth=view.findViewById(R.id.dialog_calendar_nmonth);




        PCalendar cl=new PCalendar(Adviser_edits.this);
        refresh_calendar(cl,list,month);



        pmonth.setOnClickListener(v -> {
            mplus-=1;
            refresh_calendar(cl,list,month);
        });
        nmonth.setOnClickListener(v -> {
            mplus+=1;
            refresh_calendar(cl,list,month);
        });




        title.setText(name);
        back.setOnClickListener(v -> di.dismiss());
        di.setContentView(view);
        di.show();





    }
    private void refresh_calendar(PCalendar cl,RecyclerView list,TextView month){

        ArrayList<ArrayList<String>> tmp=cl.get_month(mplus);
        month.setText(tmp.get(16).get(8));
        adaptercal rvm=new adaptercal(tmp);
        RtlGridLayoutManager layoutManager = new RtlGridLayoutManager(Adviser_edits.this, 7);
        list.setLayoutManager(layoutManager);
        list.setItemAnimator(new DefaultItemAnimator());
        list.setAdapter(rvm);


    }
    public static class RtlGridLayoutManager extends GridLayoutManager {

        public RtlGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        public RtlGridLayoutManager(Context context, int spanCount) {
            super(context, spanCount);
        }



        public RtlGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
            super(context, spanCount, orientation, reverseLayout);
        }

        @Override
        protected boolean isLayoutRTL(){
            return true;
        }
    }
    public class adaptercal extends RecyclerView.Adapter<adaptercal.MyViewHolder> {

        private ArrayList<ArrayList<String>> list;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView name;
            public MyViewHolder(View view) {
                super(view);
                name =  view.findViewById(R.id.calendar_item_text);
            }
        }


        public adaptercal(ArrayList<ArrayList<String>> list) {
            this.list = list;
        }

        @Override
        public adaptercal.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.calendar_item, parent, false);

            return new adaptercal.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final adaptercal.MyViewHolder holder, int position) {

            //final String t[]=list[position].split("↔");
            ArrayList<String> row=list.get(position);
            holder.name.setText(row.get(6));

            if(position>=0 && position<=5){
                holder.name.setTextColor(tire0);
            }
            if(position==6 || position==13 || position==20 || position ==27 || position==34 || position==41){
                holder.name.setTextColor(ghermez);
            }
            if(position>6){
                holder.name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            }

            holder.itemView.setOnClickListener(v -> {

                try{
                    oldtext.setBackground(null);
                    oldtext.setTextColor(getResources().getColor(R.color.khakestari0));
                }catch (Exception e){

                }

                holder.name.setBackgroundResource(R.drawable.btn_back1);
                holder.name.setTextColor(getResources().getColor(R.color.sefid));

                oldtext=holder.name;

            });


        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }
    // calendar


    public void show_bdate_dialog(String name){

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

            bdate.setText(rooz.getSelectedString()+" "+mah.getSelectedString()+" "+sal.getSelectedString());
            di.dismiss();
        });


        title.setText(name);
        back.setOnClickListener(v -> di.dismiss());
        di.setContentView(view);
        di.show();





    }




    public void checkPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 3670);
        } else {

        }
    }

    private void load_imgs(){

        Picasso.get()
                .load(Params.pic_adviseravatae+sp.getString(Params.sp_avatar,"-"))
                .placeholder(R.drawable.avatar_tmp1)
                .resize(200,200)
                .error(R.drawable.avatar_tmp1)
                .into(mainimage);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {

            final Uri resultUri = UCrop.getOutput(data);
            mainimage.setImageURI(resultUri);
            File f = new File(resultUri.getPath());
            UploadAsyncTask uploadAsyncTask = new UploadAsyncTask(Adviser_edits.this, f);
            uploadAsyncTask.execute();



        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);

        } else {
            easyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
                @Override
                public void onMediaFilesPicked(MediaFile[] imageFiles, MediaSource source) {
                    UCrop.Options options = new UCrop.Options();
                    options.setCompressionQuality(80);
                    options.setMaxBitmapSize(10000);

                    UCrop.of(Uri.fromFile(new File(imageFiles[0].getFile().toString())), Uri.fromFile(new File(getCacheDir(), "SampleCropImage")))
                            .withAspectRatio(1, 1)
                            .withMaxResultSize(450, 450)
                            .withOptions(options)
                            .start(Adviser_edits.this);
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


    private class UploadAsyncTask extends AsyncTask<Void, Integer, String> {

        HttpClient httpClient = new DefaultHttpClient();
        private Context context;
        private Exception exception;
        private File file;
        private UploadAsyncTask(Context context, File f) {
            this.context = context;
            file=f;
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
            //Toast.makeText(getApplicationContext(),"در حال ارسال تصویر ...",Toast.LENGTH_LONG).show();
            di.show();
        }

        @Override
        protected void onPostExecute(String result) {
            fun.show_debug_dialog(2,result,Params.ws_uploadavatar);
            di.dismiss();
            try {
                JSONObject part=new JSONObject(result);
                if(!part.getString("image_original").equals("")){
                    Toast.makeText(getApplicationContext(),"تصویر با موفقیت بروز شد",Toast.LENGTH_LONG).show();

                    Main.doact="chprofile";
                    Main.tabc=0;
                    SharedPreferences.Editor ed=sp.edit();
                    ed.putString(Params.sp_avatar,part.getString("image_original"));
                    ed.commit();
                }else{
                    Toast.makeText(getApplicationContext(),"بروز خطا در بروزرسانی تصویر",Toast.LENGTH_LONG).show();
                }


            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(),"بروز خطا در بروزرسانی تصویر",Toast.LENGTH_LONG).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
        }
    }



}
