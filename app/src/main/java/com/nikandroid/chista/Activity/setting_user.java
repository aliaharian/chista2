package com.nikandroid.chista.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.nikandroid.chista.Functions.Function;
import com.nikandroid.chista.Params.Params;
import com.nikandroid.chista.R;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.squareup.picasso.Picasso;

import net.steamcrafted.materialiconlib.MaterialIconView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class setting_user extends AppCompatActivity {

    private SharedPreferences sp;
    String Claim="";
    Function fun;

    TextView actlabel;

    LinearLayout username_area,name_area,family_area,phone_area,email_area,city_area,sheba_area,bdate_area;
    TextView username,name,family,phone,email,city,sheba,bdate;
    JSONObject vals;
    SweetAlertDialog wdi;
    ImageView avatar_img;

    public static boolean refreshpage=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_user);
        init();
        clicks();
    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));    }

    private Context getActivity(){
        return setting_user.this;
    }

    private void init(){


        sp=getSharedPreferences(Params.spmain,MODE_PRIVATE);
        fun=new Function(this);
        Claim=sp.getString(Params.spClaim,"-");



        View actv=fun.Actionbar_4(R.layout.actionbar_main);


        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        getSupportActionBar().setCustomView(actv, layoutParams);

        actlabel=actv.findViewById(R.id.actionbar_main_title);
        actlabel.setText("تنظیمان مشخصات فردی");
        MaterialIconView actback=actv.findViewById(R.id.actionbar_main_back);
        actback.setOnClickListener(v -> finish());




        wdi=new SweetAlertDialog(getActivity(),SweetAlertDialog.PROGRESS_TYPE);
        wdi.setTitleText("لطفا صبر کنید");
        wdi.setContentText("در حال ثبت اطلاعات");

        avatar_img=findViewById(R.id.setting_user_avatar_image);


        username=findViewById(R.id.setting_user_username2);
        name=findViewById(R.id.setting_user_name2);
        family=findViewById(R.id.setting_user_family2);
        phone=findViewById(R.id.setting_user_phone2);
        email=findViewById(R.id.setting_user_email2);
        city=findViewById(R.id.setting_user_city2);
        sheba=findViewById(R.id.setting_user_sheba2);
        bdate=findViewById(R.id.setting_user_bdate2);

        username_area=findViewById(R.id.setting_user_username_area);
        name_area=findViewById(R.id.setting_user_name_area);
        family_area=findViewById(R.id.setting_user_family_area);
        phone_area=findViewById(R.id.setting_user_phone_area);
        email_area=findViewById(R.id.setting_user_email_area);
        city_area=findViewById(R.id.setting_user_city_area);
        bdate_area=findViewById(R.id.setting_user_bdate_area);
        sheba_area=findViewById(R.id.setting_user_sheba_area);


        username.setText(sp.getString(Params.sp_username,""));
        name.setText(sp.getString(Params.sp_name,""));
        family.setText(sp.getString(Params.sp_lastname,""));
        phone.setText(sp.getString(Params.sp_mobile,""));
        email.setText(sp.getString(Params.sp_email,""));
        city.setText(sp.getString(Params.sp_city,""));
        sheba.setText(sp.getString(Params.sp_sheba,""));
        bdate.setText(sp.getString(Params.sp_bdatename,""));




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


    @Override
    protected void onResume() {
        super.onResume();
        load_avatar();

        if(refreshpage){
            refreshpage=false;
            city.setText(sp.getString(Params.sp_city,""));
        }


    }

    private void load_avatar(){
        Picasso.get()
                .load(Params.pic_adviseravatae+sp.getString(Params.sp_avatar,"-"))
                .placeholder(R.drawable.avatar_tmp1)
                .resize(200,200)
                .error(R.drawable.avatar_tmp1)
                .into(avatar_img);

    }

    private void clicks(){

        avatar_img.setOnClickListener(v -> startActivity(new Intent(getActivity(),avatar_pic.class)));

        username_area.setOnClickListener(v -> show_box_edit("نام کاربری","username",InputType.TYPE_CLASS_TEXT,Params.sp_username,username));

        name_area.setOnClickListener(v -> show_box_edit("نام","name",InputType.TYPE_CLASS_TEXT,Params.sp_name,name));

        family_area.setOnClickListener(v -> show_box_edit("نام خانوادگی","last_name",InputType.TYPE_CLASS_TEXT,Params.sp_lastname,family));

        phone_area.setOnClickListener(v -> show_box_edit("شماره موبایل","phone",InputType.TYPE_CLASS_NUMBER,Params.sp_mobile,phone));


        email_area.setOnClickListener(v -> show_box_edit("پست الکترونیکی","email",InputType.TYPE_CLASS_TEXT,Params.sp_email,email));

        sheba_area.setOnClickListener(v -> show_box_edit("شماره شبا","sheba",InputType.TYPE_CLASS_NUMBER,Params.sp_sheba,sheba));

        city_area.setOnClickListener(v -> {

            Intent i=new Intent(getActivity(),select_city.class);
            i.putExtra("ccity",Params.sp_city_id);
            i.putExtra("vals",vals.toString());
            i.putExtra("flag","usersetting");
            startActivity(i);

        });

        bdate_area.setOnClickListener(v -> show_bdate_cal());



    }

    private void show_box_edit(final String name, final String jsonname, int type, final String paramsname, final TextView org){


        LayoutInflater inflater = this.getLayoutInflater();
        View hdview = inflater.inflate(R.layout.dialog_setting_editbox, null);

        TextView label=hdview.findViewById(R.id.dialog_setting_editbox_label);
        final EditText editval=hdview.findViewById(R.id.dialog_setting_editbox_value);
        label.setText(name);
        editval.setInputType(type);

        try {
            editval.setText(vals.getString(jsonname));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(this)
                .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                .addButton("ثبت", getResources().getColor(R.color.sefid), getResources().getColor(R.color.Abi2), CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {
                    update_user_info(jsonname,editval.getText().toString(),dialog,paramsname,org);
                    dialog.dismiss();
                })
                .addButton("انصراف", getResources().getColor(R.color.khakestari2), getResources().getColor(R.color.khakestari0), CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {
                    dialog.dismiss();
                });

        builder.setHeaderView(hdview);





        builder.show();



/*
        final Dialog di=new Dialog(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_setting_editbox, null);
        di.setContentView(dialogView);
        di.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        TextView label=di.findViewById(R.id.dialog_setting_editbox_label);
        final EditText editval=di.findViewById(R.id.dialog_setting_editbox_value);
        editval.setInputType(type);
        LinearLayout submit=di.findViewById(R.id.dialog_setting_editbox_submit);

        label.setText(name);

        try {
            editval.setText(vals.getString(jsonname));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        submit.setOnClickListener(v -> update_user_info(jsonname,editval.getText().toString(),di,paramsname,org));


        di.show();*/

    }


    private void update_user_info(String name, final String value, final DialogInterface di, final String paramsname, final TextView org){


        try {
            vals.put(name,value);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        di.dismiss();
        wdi.show();
        fun.show_debug_dialog(1,vals.toString(), Params.ws_get_updateuserinfo);
        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_get_updateuserinfo,
                response -> {
                    fun.show_debug_dialog(2,response, Params.ws_get_updateuserinfo);
                    wdi.dismiss();
                    try {
                        JSONObject part=new JSONObject(response);
                        if(part.getString("message").equals("success")){
                            Toasty.success(getActivity(),"تغییر با موفقیت ثبت شد", Toasty.LENGTH_LONG).show();
                            SharedPreferences.Editor ed=sp.edit();
                            Main.doact="chprofile";
                            Main.tabc=0;
                            ed.putString(paramsname,value);
                            org.setText(value);
                            ed.commit();

                        }else{
                            Toasty.error(getActivity(),"بروز خطا"+"\n"+response, Toasty.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        Toasty.error(getActivity(),"بروز خطا"+"\n"+e.toString(), Toasty.LENGTH_LONG).show();
                    }
                },
                error -> {
                    wdi.dismiss();
                    Toasty.error(getActivity(),"بروز خطا"+"\n"+error.toString(), Toasty.LENGTH_LONG).show();
                })

        {

            @Override
            public byte[] getBody() {
                String str = vals.toString();
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

    private void show_bdate_cal(){

        PersianDatePickerDialog picker = new PersianDatePickerDialog(this)
                .setPositiveButtonString("باشه")
                .setNegativeButton("بیخیال")
                .setMinYear(1300)
                .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                .setActionTextColor(Color.BLACK)
                .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
                .setShowInBottomSheet(true)
                .setListener(new Listener() {
                    @Override
                    public void onDateSelected(final PersianCalendar persianCalendar) {

                        try {
                            vals.put("birth_time",persianCalendar.getTimeInMillis());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        wdi.show();
                        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_get_updateuserinfo,
                                response -> {
                                    wdi.dismiss();
                                    try {
                                        JSONObject part=new JSONObject(response);
                                        if(part.getString("message").equals("success")){
                                            Toasty.success(getActivity(),"تغییر با موفقیت ثبت شد", Toasty.LENGTH_LONG).show();
                                            SharedPreferences.Editor ed=sp.edit();
                                            ed.putString(Params.sp_bdate,persianCalendar.getTimeInMillis()+"");
                                            ed.putString(Params.sp_bdatename,persianCalendar.getPersianLongDate());
                                            bdate.setText(persianCalendar.getPersianLongDate());
                                            ed.commit();

                                        }else{
                                            Toasty.error(getActivity(),"بروز خطا"+"\n"+response, Toasty.LENGTH_LONG).show();
                                        }


                                    } catch (JSONException e) {
                                        Toasty.error(getActivity(),"بروز خطا"+"\n"+e.toString(), Toasty.LENGTH_LONG).show();
                                    }
                                },
                                error -> {
                                    wdi.dismiss();
                                    Toasty.error(getActivity(),"بروز خطا"+"\n"+error.toString(), Toasty.LENGTH_LONG).show();
                                })

                        {

                            @Override
                            public byte[] getBody() {
                                String str = vals.toString();
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

                    @Override
                    public void onDismissed() {

                    }
                });

        picker.show();

    }


}
