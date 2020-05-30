package com.nikandroid.chista.Functions;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.nikandroid.chista.Activity.Main;
import com.nikandroid.chista.Activity.login;
import com.nikandroid.chista.Activity.user;
import com.nikandroid.chista.Params.Params;
import com.nikandroid.chista.R;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.skydoves.elasticviews.ElasticLayout;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;


public class Function {

    public Context mcontext;
    public AppCompatActivity appCompat;
    private SharedPreferences sp;
    String Claim;

    public Function(Context context) {
        mcontext = context;
        appCompat=(AppCompatActivity)mcontext;
        sp=context.getSharedPreferences(Params.spmain,context.MODE_PRIVATE);
        Claim=sp.getString(Params.spClaim,"-");

    }

    public String tocode(String s) {
        return Base64.encodeToString(s.toString().getBytes(), Base64.DEFAULT);
    }

    public String tosrc(String c) {

        return new String(Base64.decode(c, Base64.DEFAULT));

    }

    public String change_adad(String res) {


        DecimalFormat decimalFormat = new DecimalFormat("#,###.###");
        decimalFormat.setGroupingSize(3);
        res = decimalFormat.format(Long.parseLong(res));


        char[] persian = {'۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹'};

        res = res.replace("0", persian[0] + "");
        res = res.replace("1", persian[1] + "");
        res = res.replace("2", persian[2] + "");
        res = res.replace("3", persian[3] + "");
        res = res.replace("4", persian[4] + "");
        res = res.replace("5", persian[5] + "");
        res = res.replace("6", persian[6] + "");
        res = res.replace("7", persian[7] + "");
        res = res.replace("8", persian[8] + "");
        res = res.replace("9", persian[9] + "");

        return res;

    }

    public String farsi_sazi_adad(String res) {

        char[] persian = {'۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹'};

        res = res.replace("0", persian[0] + "");
        res = res.replace("1", persian[1] + "");
        res = res.replace("2", persian[2] + "");
        res = res.replace("3", persian[3] + "");
        res = res.replace("4", persian[4] + "");
        res = res.replace("5", persian[5] + "");
        res = res.replace("6", persian[6] + "");
        res = res.replace("7", persian[7] + "");
        res = res.replace("8", persian[8] + "");
        res = res.replace("9", persian[9] + "");
        return res;

    }

    public Boolean userislogin(SharedPreferences sp) {

        if (sp.getString(Params.spClaim,"--").equals("--")) {
            return false;
        } else {
            if (sp.getString(Params.login_level, "").equals("complete")) {
                return true;
            } else {
                return false;
            }
        }
    }

    public Typeface get_font_typeface(String name){

        return Typeface.createFromAsset(mcontext.getAssets(), "fonts/"+name+".ttf");

    }

    public void setwh(){

        if(sp.getInt("sh",0)==0){
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((AppCompatActivity)mcontext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;
            SharedPreferences.Editor ed=sp.edit();
            ed.putInt("sh",height);
            ed.putInt("sw",width);
            ed.commit();
        }


    }

    public void Actionbar_1(){
       appCompat.getSupportActionBar().hide();
        final Window window = appCompat.getWindow();
        try {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } catch (Exception e) {
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(mcontext.getResources().getColor(R.color.sefid));
        }
    }

    public void Actionbar_2(){
        appCompat.getSupportActionBar().hide();
        appCompat.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

    }

    public void Actionbar_3(){
        appCompat.getSupportActionBar().hide();
        final Window window = appCompat.getWindow();
        try {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } catch (Exception e) {
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(mcontext.getResources().getColor(R.color.Abi1));
        }

    }

    public View Actionbar_4(int layout){

        appCompat.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        appCompat.getSupportActionBar().setDisplayShowCustomEnabled(true);
        appCompat.getSupportActionBar().setCustomView(layout);
        appCompat.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(mcontext.getResources().getColor(R.color.khakestari0)));
        View actv=appCompat.getSupportActionBar().getCustomView();
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        appCompat.getSupportActionBar().setCustomView(actv, layoutParams);



        final Window window = appCompat.getWindow();
        try {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } catch (Exception e) {
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(mcontext.getResources().getColor(R.color.khakestari1));
        }

        appCompat.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        return actv;

    }

    public View Actionbar_5(){


        final Window window = appCompat.getWindow();
        try {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } catch (Exception e) {
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(mcontext.getResources().getColor(R.color.sefid));
        }

        appCompat.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        appCompat.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        appCompat.getSupportActionBar().setDisplayShowCustomEnabled(true);
        appCompat.getSupportActionBar().setCustomView(R.layout.actionbar_filter);
        appCompat.getSupportActionBar().setElevation(0);
        appCompat.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(mcontext.getResources().getColor(R.color.sefid)));
        View actv=appCompat.getSupportActionBar().getCustomView();
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        appCompat.getSupportActionBar().setCustomView(actv, layoutParams);





        return actv;

    }

    public void intent_actions(String act,String actval){

        if(act.equals("adviser")){
            Intent i=new Intent(mcontext,user.class);
            i.putExtra("adid",actval);
            appCompat.startActivity(i);
        }else if(act.equals("link")){
            appCompat.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(actval)));
            //////link
        }else if(act.equals("text")){
            new SweetAlertDialog(mcontext,SweetAlertDialog.NORMAL_TYPE)
                    .setTitleText("پیام")
                    .setContentText(actval)
                    .setConfirmText("بستن")
                    .setConfirmClickListener(sweetAlertDialog -> sweetAlertDialog.dismissWithAnimation())
                    .show();
        }else if(act.equals("instagram")){
            String scheme = "http://instagram.com/_u/"+actval;
            String path = "https://instagram.com/"+actval;
            String nomPackageInfo ="com.instagram.android";
            Intent intentAiguilleur;
            try {
                appCompat.getPackageManager().getPackageInfo(nomPackageInfo, 0);
                intentAiguilleur = new Intent(Intent.ACTION_VIEW, Uri.parse(scheme));
            } catch (Exception e) {
                intentAiguilleur = new Intent(Intent.ACTION_VIEW, Uri.parse(path));
            }
            appCompat.startActivity(intentAiguilleur);

        }

    }

    public void set_mark_server(MaterialIconView markicon,String adid){

        show_debug_dialog(1,"{\"adviser_id\":\""+adid+"\"}", Params.ws_addadviser);
        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_addadviser,
                response -> {
                    show_debug_dialog(2,response, Params.ws_addadviser);
                    if(response.equals("1")){
                        Main.doact="shurefresh";
                        Main.tabc=2;
                        markicon.setIcon(MaterialDrawableBuilder.IconValue.BOOKMARK);
                    }else{
                        Main.doact="shurefresh";
                        Main.tabc=2;
                        markicon.setIcon(MaterialDrawableBuilder.IconValue.BOOKMARK_OUTLINE);
                    }
                },
                error -> {

                })
        {

            @Override
            public byte[] getBody() {
                String str = "{\"adviser_id\":\""+adid+"\"}";
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
        Volley.newRequestQueue(mcontext).add(rq);


    }



    public void set_like_comment(final String cmid, final ImageView icon, final TextView likecount){

        icon.setAlpha(0.4f);

        show_debug_dialog(1,"{\"comment_id\":\""+cmid+"\"}", Params.ws_likecomment);
        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_likecomment,
                response -> {
                    show_debug_dialog(2,response, Params.ws_likecomment);
                    icon.setAlpha(1f);
                    if(response.equals("1")){
                        icon.setImageResource(R.drawable.main_icon_like2);
                        likecount.setText((Integer.parseInt(likecount.getText().toString())+1)+"");
                    }else{
                        icon.setImageResource(R.drawable.main_icon_like1);
                        likecount.setText((Integer.parseInt(likecount.getText().toString())-1)+"");
                    }
                },
                error -> {
                    show_debug_dialog(2,error.toString(), Params.ws_prefilter);
                })

        {

            @Override
            public byte[] getBody() {
                String str = "{\"comment_id\":\""+cmid+"\"}";
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
        Volley.newRequestQueue(mcontext).add(rq);

    }


    public void block_adviser(final String adid){

        show_debug_dialog(1,"{\"adviser_id\":\""+adid+"\"}", Params.ws_blockadviser);
        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_blockadviser,
                response -> {
                    show_debug_dialog(2,response, Params.ws_blockadviser);
                    if(response.equals("1")){
                        Toasty.success(mcontext,"این مشاور مسدود شد", Toasty.LENGTH_LONG).show();
                        Main.doact="blockadviser";
                        Main.tabc=2;
                        ((AppCompatActivity)mcontext).finish();
                    }else if(response.equals("0")){
                        Toasty.success(mcontext,"این مشاور از حالت مسدود بودن خارج شد", Toasty.LENGTH_LONG).show();
                        ((AppCompatActivity)mcontext).finish();
                    }else{
                        Toasty.error(mcontext,"بروز خطا", Toasty.LENGTH_LONG).show();
                    }
                },
                error -> {
                    Toasty.error(mcontext,"بروز خطا", Toasty.LENGTH_LONG).show();
                    show_debug_dialog(2,error.toString(), Params.ws_prefilter);})

        {

            @Override
            public byte[] getBody() {
                String str = "{\"adviser_id\":\""+adid+"\"}";
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
        Volley.newRequestQueue(mcontext).add(rq);

    }

    public void report_comment(final String cmid){
        show_debug_dialog(1,"{\"comment_id\":"+cmid+"}", Params.ws_reportcomment);
        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_reportcomment,
                response -> {
                    show_debug_dialog(2,response, Params.ws_reportcomment);
                    if(response.equals("1")){
                        Toasty.success(mcontext,"گزارش شما ثبت شد", Toasty.LENGTH_LONG).show();
                    }else if(response.equals("0")){
                        Toasty.success(mcontext,"گزارش شما حذف شد", Toasty.LENGTH_LONG).show();
                    }else{
                        Toasty.error(mcontext,"بروز خطا", Toasty.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toasty.error(mcontext,"بروز خطا", Toasty.LENGTH_LONG).show();
                        show_debug_dialog(2,error.toString(), Params.ws_prefilter);

                    }
                })

        {

            @Override
            public byte[] getBody() {
                String str = "{\"comment_id\":"+cmid+"}";
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
        Volley.newRequestQueue(mcontext).add(rq);

    }

    public void set_unknow_comment(Switch sw){
        show_debug_dialog(1,"{}", Params.ws_unknowcomment);
        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_unknowcomment,
                response -> {
                    show_debug_dialog(2,response, Params.ws_unknowcomment);
                    if(response.equals("1")){
                        Toasty.success(mcontext,"کامنت ناشناس فعال شد", Toasty.LENGTH_LONG).show();
                        sw.setChecked(true);

                        SharedPreferences.Editor ed=sp.edit();
                        ed.putString(Params.sp_unknowcomment,"1");
                        ed.commit();


                    }else if(response.equals("0")){
                        Toasty.error(mcontext,"کامنت ناشناس غیرفعال شد", Toasty.LENGTH_LONG).show();
                        sw.setChecked(false);
                        SharedPreferences.Editor ed=sp.edit();
                        ed.putString(Params.sp_unknowcomment,"0");
                        ed.commit();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toasty.error(mcontext,"بروز خطا", Toasty.LENGTH_LONG).show();
                        show_debug_dialog(2,error.toString(), Params.ws_prefilter);

                    }
                })

        {

            @Override
            public byte[] getBody() {
                String str = "{}";
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
        Volley.newRequestQueue(mcontext).add(rq);

    }

    public void like_splash(){


        show_debug_dialog(1,"cookie = "+ Claim, Params.ws_get_userinfo);



        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_get_userinfo,
                response -> {
                    show_debug_dialog(2,response, Params.ws_get_userinfo);
                    try {
                        JSONObject part=new JSONObject(response);
                        Log.e("like_splash",part.toString());
                        SharedPreferences.Editor ed=sp.edit();

                        if(part.getString("name").equals("null")){
                            part.put("name","");
                        }
                        if(part.getString("last_name").equals("null")){
                            part.put("last_name","");
                        }
                        if(part.getString("full_name").equals("null")){
                            part.put("full_name","");
                        }
                        if(part.getString("mobile").equals("null")){
                            part.put("mobile","");
                        }
                        if(part.getString("national_code").equals("null")){
                            part.put("national_code","");
                        }
                        if(part.getString("avatar").equals("null")){
                            part.put("avatar","");
                        }
                        if(part.getString("username").equals("null")){
                            part.put("username","");
                        }
                        if(part.getString("email").equals("null")){
                            part.put("email","");
                        }
                        if(part.getString("city").equals("null")){
                            part.put("city","");
                        }
                        if(part.getString("city_id").equals("null")){
                            part.put("city_id","");
                        }
                        if(part.getString("sheba").equals("null")){
                            part.put("sheba","");
                        }

                        if(part.getString("birth_time").equals("null")){
                            part.put("birth_time","");
                        }

                        if(part.getString("bdatename").equals("null")){
                            part.put("bdatename","");
                        }

                        ////walet
                        JSONObject w0=new JSONObject(part.getString("wallets"));
                        //JSONObject w1=new JSONObject(w0.getString("0"));

                        ed.putString(Params.sp_name,part.getString("name"));
                        ed.putString(Params.sp_lastname,part.getString("last_name"));
                        ed.putString(Params.sp_fulllname,part.getString("full_name"));
                        ed.putString(Params.sp_mobile,part.getString("mobile"));
                        ed.putString(Params.sp_national_code,part.getString("national_code"));
                        ed.putString(Params.sp_avatar,part.getString("avatar"));
                        ed.putString(Params.sp_username,part.getString("username"));
                        ed.putString(Params.sp_email,part.getString("email"));
                        ed.putString(Params.sp_city,part.getString("city"));
                        ed.putString(Params.sp_city_id,part.getString("city_id"));
                        ed.putString(Params.sp_sheba,part.getString("sheba"));
                        ed.putString(Params.sp_bdate,part.getString("birth_time"));
                        ed.putString(Params.sp_bdatename,part.getString("bdatename"));
                        ed.putString(Params.sp_unknowcomment,part.getString("unknown_comment"));
                        ed.putString(Params.sp_isdebug,part.getInt("debug")+"");
                        ed.putString(Params.sp_lastcredit,part.getInt("credit")+"");
                        //ed.putString(Params.sp_isadviser,part.getInt("adviser_status")+"");
                        //ed.putString(Params.sp_isadviser,"0");
                        ed.commit();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        show_debug_dialog(2,e.toString(), Params.ws_prefilter);
                    }

                },
                error -> {
                    show_debug_dialog(2,error.toString(), Params.ws_prefilter);
                })

        {
            public String getBodyContentType()
            {
                return "application/json; charset=utf-8";
            }
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                Log.e("calim",Claim);
                params.put("cookie", Claim);
                return params;
            }
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                Log.e("user_info_statuscode",mStatusCode+"-");
                return super.parseNetworkResponse(response);
            }
        };
        Volley.newRequestQueue(mcontext).add(rq);

    }

    public void show_error_internet(){

        new SweetAlertDialog(mcontext,SweetAlertDialog.ERROR_TYPE)
                .setTitleText("بروز خطا")
                .setContentText("عدم اتصال به اینترنت")
                .setConfirmText("خروج")
                .setConfirmClickListener(sweetAlertDialog -> appCompat.finish())
                .show();

    }

    public void retriveVideoFrameFromVideo(String videoPath, final ImageView img)
    {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try
        {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(videoPath);
            bitmap = mediaMetadataRetriever.getFrameAtTime();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }

        final Bitmap finalBitmap = bitmap;
        appCompat.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                img.setImageBitmap(finalBitmap);
            }
        });

        user.videocover=bitmap;
        user.getcovetvideo=true;
    }

    public void show_error_dialog(String title,String text){
        LayoutInflater inflater = appCompat.getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog_msg1,null,false);
        BottomSheetDialog di=new BottomSheetDialog(mcontext,R.style.SheetDialog);
        TextView t=view.findViewById(R.id.dialog_msg1_title);
        TextView t2=view.findViewById(R.id.dialog_msg1_text);
        ElasticLayout btn=view.findViewById(R.id.dialog_msg1_btn);
        t.setText(title);
        t2.setText(text);
        btn.setOnClickListener(v -> {
            di.dismiss();
        });


        di.setContentView(view);
        di.show();


    }


    public void show_debug_dialog(int sr,String content,String url){




        if(sp.getString(Params.sp_isdebug,"0").equals("1")){
            String name="";
            if(sr==1){
                name="send";
            }else if(sr==2){
                name="receive";
            }else{
                name="unknow";
            }


            LayoutInflater inflater = ((AppCompatActivity)mcontext).getLayoutInflater();
            View hdview = inflater.inflate(R.layout.dialog_debug_log, null);

            TextView label=hdview.findViewById(R.id.dialog_debug_log_label);
            TextView text=hdview.findViewById(R.id.dialog_debug_log_text);
            TextView urll=hdview.findViewById(R.id.dialog_debug_log_url);
            label.setText(name);
            text.setText(content);
            urll.setText(url);




            CFAlertDialog.Builder builder = new CFAlertDialog.Builder(mcontext)
                    .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                    .addButton("ok", mcontext.getResources().getColor(R.color.sefid), mcontext.getResources().getColor(R.color.Abi2), CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {

                        dialog.dismiss();
                    });


            builder.setHeaderView(hdview);
            builder.show();



        }

    }


    public void Sign_out(){


        new SweetAlertDialog(mcontext, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("خروج از حساب کاربری")
                .setContentText("آیا مطمئنید که میخواهید از حساب کاربری خود خارج شوید؟ تمامی اطلاعات شما ذخیره شده است و با ورود مجدد به حساب خود به آنها دسترسی خواهید داشت")
                .setConfirmText("بله خارج شو")
                .setCancelText("انصراف")
                .setConfirmClickListener(sweetAlertDialog -> {
                   // File spufile = new File("/data/data/com.nikandroid.chista/shared_prefs/"+Params.spmain+".xml");
                   // spufile.delete();
                    SharedPreferences.Editor ed=sp.edit();
                    ed.putString(Params.login_level,"getphone");
                    ed.putString(Params.login_data,"");
                    ed.putString(Params.spClaim,"");
                    ed.commit();
                    ((AppCompatActivity)mcontext).startActivity(new Intent(mcontext, login.class));
                    ((AppCompatActivity)mcontext).finish();


                })
                .setCancelClickListener(sweetAlertDialog -> sweetAlertDialog.dismiss())
                .show();




    }







}
