package com.nikandroid.chista.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.nikandroid.chista.Functions.Function;
import com.nikandroid.chista.Params.Params;
import com.nikandroid.chista.R;
import com.nikandroid.chista.Utils.MyHttpEntity;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.skydoves.elasticviews.ElasticImageView;
import com.skydoves.elasticviews.ElasticLayout;
import com.squareup.picasso.Picasso;
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

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import pl.aprilapps.easyphotopicker.ChooserType;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.MediaFile;
import pl.aprilapps.easyphotopicker.MediaSource;

public class user_edits extends AppCompatActivity {



    EasyImage easyImage;
    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 5469;


    private SharedPreferences sp;
    Function fun;
    String Claim="",flag="";

    ElasticImageView act_back;
    ProgressBar prog;
    ElasticLayout submit,changepic;
    ImageView mainimage;
    EditText name,family;
    SweetAlertDialog di;
    JSONObject vals;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_edits);

        init();

        checkPermission();
        load_imgs();

        fill_page();


    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));    }

    private Context getActivity(){
        return user_edits.this;
    }

    private void init(){

        sp=getSharedPreferences(Params.spmain,MODE_PRIVATE);
        fun=new Function(getActivity());
        Claim=sp.getString(Params.spClaim,"-");

        di=new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        di.setTitleText("لطفا صبر کنید");
        di.setContentText("در حال ارسال اطلاعات");


        getSupportActionBar().hide();
        act_back=findViewById(R.id.useredit_actionbar_back);

        act_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //prog=findViewById(R.id.prefilter2_prog);
        changepic=findViewById(R.id.useredit_changepic);
        submit=findViewById(R.id.useredit_submit);
        mainimage=findViewById(R.id.useredit_avatar_image);

        name=findViewById(R.id.useredit_et_name);
        family=findViewById(R.id.useredit_et_family);

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

        name.setText(sp.getString(Params.sp_name,""));
        family.setText(sp.getString(Params.sp_lastname,""));


        easyImage = new EasyImage.Builder(user_edits.this)
                .setChooserTitle("انتخاب تصویر")
                .setChooserType(ChooserType.CAMERA_AND_GALLERY)
                .setCopyImagesToPublicGalleryFolder(false)
                .allowMultiple(false)
                .build();


        changepic.setOnClickListener(view -> easyImage.openChooser(user_edits.this));
        submit.setOnClickListener(view -> submit_changes());



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


    private void submit_changes(){

        String n=name.getText().toString();
        String f=family.getText().toString();
        if(n.length()>1 | f.length()>1){


            try {
                vals.put("name",n);
                vals.put("last_name",f);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            di.show();
            StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_get_updateuserinfo,
                    response -> {
                        fun.show_debug_dialog(2,response, Params.ws_get_updateuserinfo);
                        di.dismiss();
                        try {
                            JSONObject part=new JSONObject(response);
                            if(part.getString("message").equals("success")){
                                Toasty.success(getActivity(),"تغییر با موفقیت ثبت شد", Toasty.LENGTH_LONG).show();
                                SharedPreferences.Editor ed=sp.edit();
                                Main.doact="chprofile";
                                Main.tabc=0;
                                ed.putString(Params.sp_name,n);
                                ed.putString(Params.sp_lastname,f);
                                ed.commit();
                                finish();
                            }else{
                                Toasty.error(getActivity(),"بروز خطا"+"\n"+response, Toasty.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            Toasty.error(getActivity(),"بروز خطا"+"\n"+e.toString(), Toasty.LENGTH_LONG).show();
                        }
                    },
                    error -> {
                        di.dismiss();
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





        }else{
            Toast.makeText(getApplicationContext(),"مقادیر وارد شده اشتباه است",Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {

            final Uri resultUri = UCrop.getOutput(data);
            mainimage.setImageURI(resultUri);
            File f = new File(resultUri.getPath());
            UploadAsyncTask uploadAsyncTask = new UploadAsyncTask(user_edits.this, f);
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
                            .start(user_edits.this);
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
