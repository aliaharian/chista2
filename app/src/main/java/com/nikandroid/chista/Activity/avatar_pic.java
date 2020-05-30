package com.nikandroid.chista.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.nikandroid.chista.Functions.Function;
import com.nikandroid.chista.Params.Params;
import com.nikandroid.chista.R;
import com.nikandroid.chista.Utils.MyHttpEntity;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import net.steamcrafted.materialiconlib.MaterialIconView;

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

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import pl.aprilapps.easyphotopicker.ChooserType;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.MediaFile;
import pl.aprilapps.easyphotopicker.MediaSource;

public class avatar_pic extends AppCompatActivity {

    EasyImage easyImage;
    LinearLayout area;
    TextView status;
    ImageView img;
    TextView actlabel;
    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 5469;

    private SharedPreferences sp;
    String Claim="";
    Function fun;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avatar_pic);

        init();

        checkPermission();


        load_imgs();


        easyImage = new EasyImage.Builder(avatar_pic.this)
                .setChooserTitle("انتخاب رسانه")
                .setChooserType(ChooserType.CAMERA_AND_GALLERY)
                .setCopyImagesToPublicGalleryFolder(false)
                .allowMultiple(false)
                .build();


        area.setOnClickListener(view -> easyImage.openChooser(avatar_pic.this));


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
        actlabel.setText("تغییر تصویر آواتار");
        MaterialIconView actback=actv.findViewById(R.id.actionbar_main_back);
        actback.setOnClickListener(v -> finish());


        area=findViewById(R.id.avatar_pic_changearea);
        img=findViewById(R.id.avatar_pic_img);
        status=findViewById(R.id.avatar_pic_status);
    }


    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));    }



    private void load_imgs(){

            Picasso.get()
                    .load(Params.pic_adviseravatae+sp.getString(Params.sp_avatar,"-"))
                    .placeholder(R.drawable.avatar_tmp1)
                    .resize(200,200)
                    .error(R.drawable.avatar_tmp1)
                    .into(img);

        }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {

                final Uri resultUri = UCrop.getOutput(data);
                img.setImageURI(resultUri);
                File f = new File(resultUri.getPath());
                UploadAsyncTask uploadAsyncTask = new UploadAsyncTask(avatar_pic.this, f);
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
                                .start(avatar_pic.this);
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
            status.setText("در حال آپلود تصویر ...");
        }

        @Override
        protected void onPostExecute(String result) {
            fun.show_debug_dialog(2,result,Params.ws_uploadavatar);
            try {
                JSONObject part=new JSONObject(result);
                if(!part.getString("image_original").equals("")){
                    status.setText("تصویر با موفقیت بروز شد");
                    Main.doact="chprofile";
                    Main.tabc=0;
                    status.setTextColor(getResources().getColor(R.color.sabz2));
                    SharedPreferences.Editor ed=sp.edit();
                    ed.putString(Params.sp_avatar,part.getString("image_original"));
                    ed.commit();
                }else{
                    status.setText("بروز خطا در بروزرسانی تصویر");
                    status.setTextColor(getResources().getColor(R.color.ghermez));
                }


            } catch (JSONException e) {
                status.setText("بروز خطا در بروزرسانی تصویر");
                status.setTextColor(getResources().getColor(R.color.ghermez));
            }

        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
        }
    }



    public void checkPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 3670);
        } else {


        }
    }




}
