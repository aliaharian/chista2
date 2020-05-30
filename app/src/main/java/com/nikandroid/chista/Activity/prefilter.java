package com.nikandroid.chista.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.innovattic.rangeseekbar.RangeSeekBar;
import com.nikandroid.chista.Functions.Function;
import com.nikandroid.chista.Params.Params;
import com.nikandroid.chista.R;
import com.nikandroid.chista.ViewModelFactory.MainModelFactory;
import com.nikandroid.chista.ViewModelFactory.PrefilterModelFactory;
import com.nikandroid.chista.databinding.MainBinding;
import com.nikandroid.chista.databinding.PrefilterBinding;
import com.nikandroid.chista.viewModels.MainViewModel;
import com.nikandroid.chista.viewModels.PrefilterViewModel;
import com.skydoves.elasticviews.ElasticImageView;
import com.skydoves.elasticviews.ElasticLayout;
import com.squareup.picasso.Picasso;

import net.steamcrafted.materialiconlib.MaterialIconView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class prefilter extends AppCompatActivity {

    private SharedPreferences sp;
    Function fun;
    String Claim="";

    RecyclerView list;
    ProgressBar prog;
    FloatingActionButton newcom;
    public static String body="";
    String subcatbody="";
    String fbody="",clevel="main";
    String catsbody="",subcatsbody="",tagsbody="",citbodey="";
    EditText search;
    LinearLayout searcharea;
    int khakestrai0=0,khakestari1=0;
    TextView act_title,act_remove,submit_name;
    ElasticImageView act_back;



    //tags
    ArrayList<Integer> subcats_id;
    ArrayList<Integer> tags_id;
    ArrayList<Integer> city_id;
    public static ArrayList<Integer> gender_id;
    public static ArrayList<Integer> isonline;
    ArrayList<String[]> fields;
    public static String minrange="0",maxrange="0",namerange="";

    JSONObject fullitems = null;
    JSONObject tempsearch;
    ElasticLayout submit;
    String submit_act="";
    Typeface bakh_bold;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PrefilterBinding binding= DataBindingUtil.setContentView(this,R.layout.prefilter);
        binding.setPrefilterModel(ViewModelProviders.of(this,new PrefilterModelFactory(this)).get(PrefilterViewModel.class));

        init();

        clicks();


    }

    @Override
    public void onBackPressed() {



        if(clevel.equals("main")){
            body="";
            finish();

        }else if(clevel.equals("checkcity") || clevel.equals("cats") ){
            String tmpcity="";
            String tmptag="";
            for (int s : tags_id)
            {
                tmptag+=s+",";
            }
            body="{\"type\":\"listmain\",\"valtag\":\""+tmptag+"\",\"valcity\":\""+tmpcity+"\"}";
            clevel="main";
            get_list();
        }else if(clevel.equals("tags") ){
            body=subcatbody;
            clevel="subcats";
            get_list();
        }else if(clevel.equals("subcats") ){
            body=catsbody;
            clevel="cats";
            get_list();
        }else if(clevel.equals("listcity")){
            clevel="main";
            body=citbodey;
            get_list();
        }



/*
        if(submit_act.equals("main")){
            if(body.equals("")){
                finish();
            }else{
                if(clevel.equals("main")){
                    body="";
                    finish();
                }else
                if(clevel.equals("cats")){
                    body="";
                    clevel="main";
                    get_list();
                }else
                if(clevel.equals("subcats")){
                    body=catsbody;
                    clevel="cats";
                    get_list();
                }else if(clevel.equals("tags") ){
                    body=subcatbody;
                    clevel="subcats";
                    get_list();
                }
            }

        }else if(submit_act.equals("checkcity") ){
            String tmpcity="";
            String tmptag="";
            for (int s : tags_id)
            {
                tmptag+=s+",";
            }
            body="{\"type\":\"listmain\",\"valtag\":\""+tmptag+"\",\"valcity\":\""+tmpcity+"\"}";
            get_list();
        }else if(submit_act.equals("listtag") ){
            body=subcatbody;
            clevel="subcats";
            get_list();
        }else if(submit_act.equals("listsubcat") ){
            body=catsbody;
            clevel="cats";
            get_list();
        }
*/
    }

    @Override
    protected void onResume() {
        super.onResume();


        try{
            if(body.equals("")){
                //   body=getIntent().getExtras().getString("body");
            }
            try {
                JSONObject tmp=new JSONObject(body);
                tmp.put("type","listmain");
                //isonline.add(Integer.parseInt(tmp.getString("isonline")));
                body=tmp.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }catch (Exception e){

        }

        get_list();
    }


    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));    }


    private Context getActivity(){
        return prefilter.this;
    }

    private void init(){

        sp=getSharedPreferences(Params.spmain,MODE_PRIVATE);
        fun=new Function(getActivity());
        Claim=sp.getString(Params.spClaim,"-");

        //View actv =fun.Actionbar_5();
        getSupportActionBar().hide();
        act_title=findViewById(R.id.prefilter_actionbar_title);
        act_back=findViewById(R.id.prefilter_actionbar_back);
        act_remove=findViewById(R.id.prefilter_actionbar_remove);
        act_remove.setVisibility(View.INVISIBLE);


        search=findViewById(R.id.prefilter_search);
        searcharea=findViewById(R.id.prefilter_searcharea);
        submit_name=findViewById(R.id.prefilter_submit_name);

        bakh_bold=Typeface.createFromAsset(getAssets(),"fonts/Yekan_Bakh_Bold.ttf");

        subcats_id=new ArrayList<Integer>();
        tags_id=new ArrayList<Integer>();
        city_id=new ArrayList<Integer>();
        gender_id=new ArrayList<Integer>();
        isonline=new ArrayList<Integer>();
        fields=new ArrayList<String[]>();

        list=findViewById(R.id.prefilter_list);
        prog=findViewById(R.id.prefilter_prog);
        submit=findViewById(R.id.prefilter_submit);

        khakestrai0=getResources().getColor(R.color.khakestari0);
        khakestari1=getResources().getColor(R.color.khakestari1);
        try {
            body=getIntent().getExtras().getString("body");
        }catch (Exception e){

        }

    }

    private void clicks(){


        submit.setOnClickListener(v -> {

            String tmpsubcat1="";
            for (int s : subcats_id)
            {
                tmpsubcat1+=s+",";
            }

            if(submit_act.equals("main")){

                String tmpcity="";
                for (int s : city_id)
                {
                    tmpcity+=s+",";
                }
                String tmpsubcat="";
                for (int s : subcats_id)
                {
                    tmpsubcat+=s+",";
                }

                String tmpgen="";
                for (int s : gender_id)
                {
                    tmpgen+=s+",";
                }
                String tmpsts="";
                for (int s : isonline)
                {
                    tmpsts+=s+",";
                }

                if(minrange.equals("0") && maxrange.equals("0")){
                    fbody+="\"type\":\"filter_result\",\"q\":\"\",\"valcat\":\""+tmpsubcat+"\",\"valtag\":\"\",\"valcity\":\""+tmpcity+"\",\"range\":\"\",";
                }else{
                    fbody+="\"type\":\"filter_result\",\"q\":\"\",\"valcat\":\""+tmpsubcat+"\",\"valtag\":\"\",\"valcity\":\""+tmpcity+"\",\"range\":\""+minrange+","+maxrange+"\",";
                }
                fbody+="\"gender\":\""+tmpgen+"\",";


                fbody+="\"isonline\":\""+tmpsts+"\"";

                String tmpfield="";
                for (int i=0;i<fields.size();i++){
                    String[] tt=fields.get(i);
                    try{
                        if(tt[0]!=null && tt[1]!=null){
                            tmpfield+=",\""+tt[0]+"\":\""+tt[1]+"\"";
                        }
                    }catch (Exception e){
                    }

                }
                fbody+=tmpfield;
                fbody="{"+fbody+"}";
                Log.e("fbody",body);
                Intent i=new Intent(getActivity(),search.class);
                i.putExtra("from","prefilter");
                i.putExtra("body",fbody);
                startActivity(i);

                fbody="";


            }else{
                InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(search.getWindowToken(), 0);

                String tmpcity="";
                for (int s : city_id)
                {
                    tmpcity+=s+",";
                }
                String tmpsubcats="";
                for (int s : subcats_id)
                {
                    tmpsubcats+=s+",";
                }
                body="{\"type\":\"listmain\",\"valcat\":\""+tmpsubcats+"\",\"valcity\":\""+tmpcity+"\"}";
                Log.e("body8",body);
                get_list();
            }


        });

        act_remove.setOnClickListener(v -> {

            tags_id=new ArrayList<Integer>();
            subcats_id=new ArrayList<Integer>();
            city_id=new ArrayList<Integer>();
            gender_id=new ArrayList<Integer>();
            isonline=new ArrayList<Integer>();
            fields=new ArrayList<String[]>();
            maxrange="0";
            minrange="0";
            namerange="";
            clevel="main";
            body="";
            fbody="";
            get_list();
        });

        act_back.setOnClickListener(v -> {


            if(clevel.equals("main")){
                body="";
                finish();

            }else if(clevel.equals("checkcity") || clevel.equals("cats") ){
                String tmpcity="";
                String tmptag="";
                for (int s : tags_id)
                {
                    tmptag+=s+",";
                }
                body="{\"type\":\"listmain\",\"valtag\":\""+tmptag+"\",\"valcity\":\""+tmpcity+"\"}";
                clevel="main";
                get_list();
            }else if(clevel.equals("tags") ){
                body=subcatbody;
                clevel="subcats";
                get_list();
            }else if(clevel.equals("subcats") ){
                body=catsbody;
                clevel="cats";
                get_list();
            }else if(clevel.equals("listcity")){
                clevel="main";
                body=citbodey;
                get_list();
            }



/*
            if(submit_act.equals("main")){
                if(body.equals("")){
                    finish();
                }else{
                    if(clevel.equals("main")){
                        body="";
                        finish();
                    }else
                    if(clevel.equals("cats")){
                        body="";
                        clevel="main";
                        get_list();
                    }else
                    if(clevel.equals("subcats")){
                        body=catsbody;
                        clevel="cats";
                        get_list();
                    }else if(clevel.equals("tags") ){

                        body=subcatbody;
                        clevel="subcats";
                        get_list();
                    }
                }

            }else if(submit_act.equals("checkcity") ){
                String tmpcity="";
                String tmptag="";
                for (int s : tags_id)
                {
                    tmptag+=s+",";
                }

                body="{\"type\":\"listmain\",\"valtag\":\""+tmptag+"\",\"valcity\":\""+tmpcity+"\"}";

                get_list();

            }else if(submit_act.equals("listtag") ){

                body=subcatbody;
                clevel="subcats";
                get_list();

            }else if(submit_act.equals("listsubcat") ){

                body=catsbody;
                clevel="cats";
                get_list();


            }
*/

        });


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
                    for (int i=0;i<fullitems.length();i++){

                        JSONObject tmp=new JSONObject(fullitems.getString(i+""));
                        if(tmp.getString("name").indexOf(searchword)>=0){
                            tempsearch.put(ctr+"",tmp);
                            ctr++;
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                start_listing(tempsearch);
            }
        });


    }


    private void get_list(){
        list.setVisibility(View.GONE);
        prog.setVisibility(View.VISIBLE);
        searcharea.setVisibility(View.GONE);
        fun.show_debug_dialog(1,body, Params.ws_prefilter);
        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_prefilter,
                response -> {
                    Log.e("prefilter",response);
                    fun.show_debug_dialog(2,response, Params.ws_prefilter);
                    list.setVisibility(View.VISIBLE);
                    prog.setVisibility(View.GONE);
                    make_list(response);
                },
                error -> {
                    fun.show_debug_dialog(2,error.toString(), Params.ws_prefilter);
                    fun.show_error_internet();
                })

        {
            @Override
            public byte[] getBody() {
                String str = body;
                Log.e("body",str+"-");
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


    private void make_list(String res)  {

        try {
            fullitems = new JSONObject(res);
            for (int i=0;i<fullitems.length();i++){

                fields.add(new String[2]);
            }
            start_listing(fullitems);
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    private void start_listing(JSONObject jb){

        final filters_adapter rva1=new filters_adapter(jb);
        list.setItemAnimator(new DefaultItemAnimator());
        list.setAdapter(rva1);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.postDelayed(() -> {
            if(submit_act.equals("checkcity")){
                searcharea.setVisibility(View.VISIBLE);
            }else{
                searcharea.setVisibility(View.GONE);
            }

        },10);
    }

    public class filters_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final int VIEW_TYPE_LISTSUBCAT= 9;
        private final int VIEW_TYPE_LISTCITY2= 8;
        private final int VIEW_TYPE_LISTDROP= 7;
        private final int VIEW_TYPE_LISTTAG = 6;
        private final int VIEW_TYPE_LISTSELECT1 = 5;
        private final int VIEW_TYPE_LISTRANGE = 4;
        private final int VIEW_TYPE_LISTCITY = 3;
        private final int VIEW_TYPE_LISTCAT = 2;
        private final int VIEW_TYPE_LOADING = 1;

        public JSONObject mItemList;


        public filters_adapter(JSONObject itemList) {

            mItemList = itemList;
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


            switch (viewType){
                case VIEW_TYPE_LISTCAT:
                    return new listcatholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.prefilter_row_list1, parent, false));
                case VIEW_TYPE_LISTCITY:
                    return new listcityholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.prefilter_row_list1, parent, false));
                case VIEW_TYPE_LISTRANGE:
                    return new listrangeholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.prefilter_row_list1, parent, false));
                case VIEW_TYPE_LISTSELECT1:
                    return new listselectholder1(LayoutInflater.from(parent.getContext()).inflate(R.layout.prefilter_row_list1, parent, false));
                case VIEW_TYPE_LISTTAG:
                    return new listtagholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.prefilter_row_listtag, parent, false));
                case VIEW_TYPE_LOADING:
                    return new LoadingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_loading, parent, false));
                case VIEW_TYPE_LISTDROP:
                    return new listdropholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.prefilter_row_list1, parent, false));
                case VIEW_TYPE_LISTCITY2:
                    return new listcity2holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.prefilter_row_listtag, parent, false));
                case VIEW_TYPE_LISTSUBCAT:
                    return new listsubcatholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.prefilter_row_listtag, parent, false));
                default:
                    return new LoadingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_loading, parent, false));
            }



        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

            if (viewHolder instanceof listcatholder) {
                listcat((listcatholder) viewHolder, position);
            } else if (viewHolder instanceof listcityholder) {
                listcity((listcityholder) viewHolder, position);
            } else if (viewHolder instanceof listrangeholder) {
                listrange((listrangeholder) viewHolder, position);
            } else if (viewHolder instanceof listselectholder1) {
                listselect1((listselectholder1) viewHolder, position);
            } else if (viewHolder instanceof listtagholder) {
                listtag((listtagholder) viewHolder, position);
            } else if (viewHolder instanceof listdropholder) {
                listdrop((listdropholder) viewHolder, position);
            } else if (viewHolder instanceof listcity2holder) {
                listcity2((listcity2holder) viewHolder, position);
            } else if (viewHolder instanceof listsubcatholder) {
                listsubcat((listsubcatholder) viewHolder, position);
            }else{
                showLoadingView((LoadingViewHolder) viewHolder, position);
            }

        }

        @Override
        public int getItemCount() {

            return mItemList == null ? 0 : mItemList.length();
        }


        @Override
        public int getItemViewType(int position) {

            try {
                JSONObject tt=new JSONObject(mItemList.getString(position+""));

                switch (tt.getString("type")) {

                    case "listcat":
                        act_title.setText("انتخاب دسته بندی");
                        submit_name.setText("محدود کردن");
                        submit_act="main";
                        act_remove.setVisibility(View.INVISIBLE);
                        submit.setVisibility(View.GONE);
                        return VIEW_TYPE_LISTCAT;

                    case "listsubcat":
                        submit_act="listsubcat";
                        //subcats_id=new ArrayList<Integer>();
                        act_title.setText("انتخاب زیر دسته بندی");
                        submit_name.setText("محدود کردن");
                        act_remove.setVisibility(View.INVISIBLE);
                        submit.setVisibility(View.VISIBLE);
                        return VIEW_TYPE_LISTSUBCAT;

                    case "listcity":
                        submit_act="main";
                        act_title.setText("محدود کردن");
                        submit_name.setText("محدود کردن");
                        act_remove.setVisibility(View.VISIBLE);
                        submit.setVisibility(View.VISIBLE);
                        return VIEW_TYPE_LISTCITY;

                    case "range":
                        act_title.setText("محدود کردن");
                        submit_name.setText("محدود کردن");
                        submit.setVisibility(View.VISIBLE);
                        act_remove.setVisibility(View.VISIBLE);
                        submit_act="main";
                        return VIEW_TYPE_LISTRANGE;

                    case "gender":
                    case "onlines":
                        act_title.setText("محدود کردن");
                        submit_name.setText("محدود کردن");
                        submit_act="main";
                        submit.setVisibility(View.VISIBLE);
                        act_remove.setVisibility(View.VISIBLE);
                        return VIEW_TYPE_LISTSELECT1;

                    case "listtag":
                        act_title.setText("تگ ها");
                        submit_name.setText("محدود کردن");
                        submit_act="listtag";
                        submit.setVisibility(View.VISIBLE);
                        return VIEW_TYPE_LISTTAG;

                    case "checkcity":
                        act_title.setText("شهر");
                        submit_name.setText("تایید");
                        submit_act="checkcity";
                        submit.setVisibility(View.VISIBLE);
                        act_remove.setVisibility(View.INVISIBLE);
                        return VIEW_TYPE_LISTCITY2;
                    case "drop":
                        submit_act="main";
                        act_title.setText("محدود کردن");
                        submit_name.setText("محدود کردن");
                        submit.setVisibility(View.VISIBLE);
                        act_remove.setVisibility(View.VISIBLE);
                        return VIEW_TYPE_LISTDROP;
                    default:
                        return VIEW_TYPE_LOADING;
                }
            } catch (JSONException e) {
                return VIEW_TYPE_LOADING;
            }


        }


        private class listcatholder extends RecyclerView.ViewHolder {

            TextView name,des;
            MaterialIconView arrow;
            ImageView icon,blue;

            public listcatholder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.prefilter_row_list1_name);
                icon = itemView.findViewById(R.id.prefilter_row_list1_icon);
                arrow = itemView.findViewById(R.id.prefilter_row_list1_arrow);
                des = itemView.findViewById(R.id.prefilter_row_list1_des);
                blue = itemView.findViewById(R.id.prefilter_row_list1_blupoint);

            }
        }
        private void listcat(final listcatholder holder, int position) {

            try {
                final JSONObject tmp=new JSONObject(mItemList.getString(position+""));
                holder.name.setText(tmp.getString("name"));
                if(submit_act.equals("main")) {
                    if(tmp.getString("desc").equals("")){
                        holder.des.setText("انتخاب");
                        holder.des.setTextColor(khakestari1);
                        holder.blue.setVisibility(View.GONE);
                    }else{
                        holder.des.setText(tmp.getString("desc"));
                        holder.des.setTypeface(bakh_bold);
                        holder.blue.setVisibility(View.VISIBLE);
                    }
                }else{
                    holder.des.setText("");
                    holder.blue.setVisibility(View.GONE);
                }


                if(!tmp.getString("icon").equals("")){
                    Picasso
                            .get()
                            .load(Params.pic_cat+tmp.getString("icon"))
                            .into(holder.icon);
                }else{
                    holder.icon.setVisibility(View.GONE);
                }
                holder.icon.setColorFilter(khakestrai0);

                holder.itemView.setOnClickListener(v -> {
                    try {
                        String vv=tmp.getString("val");
                        subcatbody=body;
                        body="{\"type\":\"listcat\",\"val\":"+vv+"}";
                        if(clevel.equals("main")){
                            clevel="cats";
                            catsbody=body;
                        }else
                        if(clevel.equals("cats")){
                            clevel="subcats";
                            subcatbody=body;
                        }else
                        if(clevel.equals("subcats")){
                            clevel="tags";
                            tagsbody=body;
                        }
                        get_list();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                });




            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        private class listsubcatholder extends RecyclerView.ViewHolder {

            TextView name;
            CheckBox check;
            ImageView icon;
            public listsubcatholder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.prefilter_row_listtag_name);
                check = itemView.findViewById(R.id.prefilter_row_listtag_check);
                icon = itemView.findViewById(R.id.prefilter_row_listtag_icon);
            }
        }
        private void listsubcat(final listsubcatholder holder, int position) {


            try {
                if(position==0){
                    holder.icon.setVisibility(View.VISIBLE);
                    holder.check.setVisibility(View.GONE);
                }
                final JSONObject tmp=new JSONObject(mItemList.getString(position+""));
                holder.name.setText(tmp.getString("name"));
                holder.check.setClickable(false);
                if(subcats_id.contains(tmp.getInt("val"))){
                    holder.check.setChecked(true);
                }else{
                    holder.check.setChecked(false);
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if(position==0){
                                subcats_id=new ArrayList<Integer>();
                                subcats_id.add(tmp.getInt("val"));
                                submit.callOnClick();
                            }else{
                                if(!holder.check.isChecked()){
                                    holder.check.setChecked(true);
                                    subcats_id.add(tmp.getInt("val"));
                                }else{
                                    for (int i=0;i<subcats_id.size();i++){
                                        if(subcats_id.get(i)==tmp.getInt("val")){
                                            subcats_id.remove(i);
                                        }
                                    }
                                    holder.check.setChecked(false);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }



        private class listcityholder extends RecyclerView.ViewHolder {

            TextView name,des;
            MaterialIconView arrow;
            ImageView icon,blue;

            public listcityholder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.prefilter_row_list1_name);
                icon = itemView.findViewById(R.id.prefilter_row_list1_icon);
                arrow = itemView.findViewById(R.id.prefilter_row_list1_arrow);
                des = itemView.findViewById(R.id.prefilter_row_list1_des);
                blue = itemView.findViewById(R.id.prefilter_row_list1_blupoint);


            }
        }
        private void listcity(final listcityholder holder, int position) {

            try {
                JSONObject tmp=new JSONObject(mItemList.getString(position+""));
                holder.name.setText(tmp.getString("name"));
                if(!tmp.getString("icon").equals("")){

                    Picasso
                            .get()
                            .load(Params.pic_cat+tmp.getString("icon"))
                            .into(holder.icon);
                }else{
                    holder.icon.setVisibility(View.GONE);
                }
                holder.icon.setVisibility(View.GONE);
                if(tmp.getString("desc").equals("")){
                    holder.des.setText("انتخاب");
                    holder.des.setTextColor(khakestari1);
                    holder.blue.setVisibility(View.GONE);
                }else{
                    holder.des.setText(tmp.getString("desc"));
                    holder.des.setTypeface(bakh_bold);
                    holder.blue.setVisibility(View.VISIBLE);
                }



                holder.itemView.setOnClickListener(v -> {
                    citbodey=body;
                    body="{\"type\":\"listcity\",\"val\":0}";
                    clevel="listcity";
                    get_list();

                });

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        private class listrangeholder extends RecyclerView.ViewHolder {

            TextView name,des;
            MaterialIconView arrow;
            ImageView icon,blue;

            public listrangeholder(@NonNull View itemView) {
                super(itemView);

                name = itemView.findViewById(R.id.prefilter_row_list1_name);
                icon = itemView.findViewById(R.id.prefilter_row_list1_icon);
                arrow = itemView.findViewById(R.id.prefilter_row_list1_arrow);
                des = itemView.findViewById(R.id.prefilter_row_list1_des);
                blue = itemView.findViewById(R.id.prefilter_row_list1_blupoint);

            }
        }
        private void listrange(final listrangeholder holder, int position) {

            try {
                final JSONObject tmp=new JSONObject(mItemList.getString(position+""));
                holder.name.setText(tmp.getString("name"));


                if(namerange.equals("")){
                    holder.des.setText("انتخاب");
                    holder.des.setTextColor(khakestari1);
                    holder.blue.setVisibility(View.GONE);
                }else{
                    holder.des.setText(namerange);
                    holder.des.setTypeface(bakh_bold);
                    holder.blue.setVisibility(View.VISIBLE);
                }



                if(!tmp.getString("icon").equals("")){
                    Picasso
                            .get()
                            .load(Params.pic_cat+tmp.getString("icon"))
                            .into(holder.icon);
                }else{
                    holder.icon.setVisibility(View.GONE);
                }
                //holder.icon.setVisibility(View.GONE);

                holder.itemView.setOnClickListener(v -> {
                    try {
                        Intent i=new Intent(getActivity(),prefilter2.class);
                        i.putExtra("flag",tmp.getString("type"));

                        /*
                        if(gender_id.contains(1) && gender_id.contains(0)){
                            i.putExtra("value","1.0");
                        }else if(gender_id.contains(1)){
                            i.putExtra("value","1");
                        }else if(gender_id.contains(0)){
                            i.putExtra("value","0");
                        }else{
                            i.putExtra("value","-");
                        }*/

                        startActivity(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    /*
                    try {
                        String vv=tmp.getString("val");
                        subcatbody=body;
                        body="{\"type\":\"listcat\",\"val\":"+vv+"}";
                        if(clevel.equals("main")){
                            clevel="cats";
                            catsbody=body;
                        }else
                        if(clevel.equals("cats")){
                            clevel="subcats";
                            subcatbody=body;
                        }else
                        if(clevel.equals("subcats")){
                            clevel="tags";
                            tagsbody=body;
                        }
                        get_list();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    */

                });
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        private class listselectholder1 extends RecyclerView.ViewHolder {


            TextView name,des;
            MaterialIconView arrow;
            ImageView icon,blue;

            public listselectholder1(@NonNull View itemView) {

                super(itemView);
                name = itemView.findViewById(R.id.prefilter_row_list1_name);
                icon = itemView.findViewById(R.id.prefilter_row_list1_icon);
                arrow = itemView.findViewById(R.id.prefilter_row_list1_arrow);
                des = itemView.findViewById(R.id.prefilter_row_list1_des);
                blue = itemView.findViewById(R.id.prefilter_row_list1_blupoint);

            }
        }
        private void listselect1(final listselectholder1 holder, int position) {

            try {
                final JSONObject tmp=new JSONObject(mItemList.getString(position+""));
                holder.name.setText(tmp.getString("name"));
                if(tmp.getString("type").equals("gender") && gender_id.size()>0) {
                    if(gender_id.contains(1) && gender_id.contains(0)){
                        holder.des.setText("هردو");
                        holder.des.setTypeface(bakh_bold);
                        holder.blue.setVisibility(View.VISIBLE);
                    }else if(gender_id.contains(1)){
                        holder.des.setText("مرد");
                        holder.des.setTypeface(bakh_bold);
                        holder.blue.setVisibility(View.VISIBLE);
                    }else if(gender_id.contains(0)){
                        holder.des.setText("زن");
                        holder.des.setTypeface(bakh_bold);
                        holder.blue.setVisibility(View.VISIBLE);
                    }else{
                        holder.des.setText("انتخاب");
                        holder.des.setTextColor(khakestari1);
                        holder.blue.setVisibility(View.GONE);
                    }

                }else if(tmp.getString("type").equals("onlines") && isonline.size()>0) {
                    String dd="";
                    holder.des.setTypeface(bakh_bold);
                    holder.blue.setVisibility(View.VISIBLE);
                    if(isonline.contains(0)){
                        dd+="آفلاین،";
                    }
                    if(isonline.contains(1)){
                        dd+="آنلاین،";
                    }
                    if(isonline.contains(2)){
                        dd+="مشغول،";
                    }
                    dd = dd.substring(0, dd.length() - 1);
                    holder.des.setText(dd);

                }else{
                    holder.des.setText("انتخاب");
                    holder.des.setTextColor(khakestari1);
                    holder.blue.setVisibility(View.GONE);
                }

                holder.icon.setVisibility(View.GONE);

                holder.itemView.setOnClickListener(v -> {
                    try {
                        if(tmp.getString("type").equals("gender")) {

                                Intent i = new Intent(getActivity(), prefilter2.class);
                                i.putExtra("flag", tmp.getString("type"));
                                if (gender_id.contains(1) && gender_id.contains(0)) {
                                    i.putExtra("value", "1.0");
                                } else if (gender_id.contains(1)) {
                                    i.putExtra("value", "1");
                                } else if (gender_id.contains(0)) {
                                    i.putExtra("value", "0");
                                } else {
                                    i.putExtra("value", "-");
                                }
                                startActivity(i);

                        }else if(tmp.getString("type").equals("onlines")){

                            Intent i = new Intent(getActivity(), prefilter2.class);
                            i.putExtra("flag", tmp.getString("type"));
                            i.putExtra("value", "-");
                            startActivity(i);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                });
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        private class listtagholder extends RecyclerView.ViewHolder {

            TextView name;
            CheckBox check;

            public listtagholder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.prefilter_row_listtag_name);
                check = itemView.findViewById(R.id.prefilter_row_listtag_check);
            }
        }
        private void listtag(final listtagholder holder, int position) {


            try {

                final JSONObject tmp=new JSONObject(mItemList.getString(position+""));
                holder.name.setText(tmp.getString("name"));
                holder.name.setOnClickListener(v -> {
                    try {

                        if(holder.check.isChecked()){
                            holder.check.setChecked(false);
                            tags_id.add(tmp.getInt("val"));
                        }else{
                            for (int i=0;i<tags_id.size();i++){
                                if(tags_id.get(i)==tmp.getInt("val")){
                                    tags_id.remove(i);
                                }
                            }
                            holder.check.setChecked(true);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });

                holder.check.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    try {
                        if(submit_act.equals("listtag")){
                            if(isChecked){
                                tags_id.add(tmp.getInt("val"));
                            }else{

                                for (int i=0;i<tags_id.size();i++){
                                    if(tags_id.get(i)==tmp.getInt("val")){
                                        tags_id.remove(i);
                                    }
                                }

                            }
                        }else if(submit_act.equals("checkcity")){
                            if(isChecked){
                                city_id.add(tmp.getInt("val"));
                            }else{

                                for (int i=0;i<city_id.size();i++){
                                    if(city_id.get(i)==tmp.getInt("val")){
                                        city_id.remove(i);
                                    }
                                }

                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });




            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        private class listcity2holder extends RecyclerView.ViewHolder {

            TextView name;
            CheckBox check;

            public listcity2holder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.prefilter_row_listtag_name);
                check = itemView.findViewById(R.id.prefilter_row_listtag_check);
            }
        }
        private void listcity2(final listcity2holder holder, int position) {

            try {

                final JSONObject tmp=new JSONObject(mItemList.getString(position+""));
                holder.name.setText(tmp.getString("name"));
                if(city_id.contains(tmp.getInt("val"))){
                    holder.check.setChecked(true);
                }else{
                    holder.check.setChecked(false);
                }
                holder.name.setOnClickListener(v -> {
                    try {

                        if(holder.check.isChecked()){
                            city_id.add(tmp.getInt("val"));
                            holder.check.setChecked(false);
                        }else{
                            holder.check.setChecked(true);
                            for (int i=0;i<city_id.size();i++){
                                if(city_id.get(i)==tmp.getInt("val")){
                                    city_id.remove(i);
                                }
                            }

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });

                holder.check.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    try {

                        if(isChecked){
                            city_id.add(tmp.getInt("val"));
                        }else{

                            for (int i=0;i<city_id.size();i++){
                                if(city_id.get(i)==tmp.getInt("val")){
                                    city_id.remove(i);
                                }
                            }

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });




            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        private class listdropholder extends RecyclerView.ViewHolder {

            TextView name,des;
            MaterialIconView arrow;
            ImageView icon;

            public listdropholder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.prefilter_row_list1_name);
                icon = itemView.findViewById(R.id.prefilter_row_list1_icon);
                arrow = itemView.findViewById(R.id.prefilter_row_list1_arrow);
                des = itemView.findViewById(R.id.prefilter_row_list1_des);
            }
        }
        private void listdrop(final listdropholder holder, int position) {

            holder.itemView.setVisibility(View.INVISIBLE);/////////////////////////////
            try {
                final JSONObject tmp=new JSONObject(mItemList.getString(position+""));
                holder.name.setText(tmp.getString("name"));
                holder.arrow.setVisibility(View.GONE);
                holder.des.setVisibility(View.GONE);

                if(!tmp.getString("icon").equals("")){
                    Picasso
                            .get()
                            .load(Params.pic_cat+tmp.getString("icon"))
                            .into(holder.icon);
                }else{
                    holder.icon.setVisibility(View.GONE);
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            show_drop1(tmp.getString("val"),tmp.getString("id"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });




            } catch (JSONException e) {
                e.printStackTrace();
            }

        }



        private class LoadingViewHolder extends RecyclerView.ViewHolder {

            ProgressBar progressBar;

            public LoadingViewHolder(@NonNull View itemView) {
                super(itemView);
                progressBar = itemView.findViewById(R.id.row_loading_progres);
            }
        }
        private void showLoadingView(LoadingViewHolder viewHolder, int position) {
            //ProgressBar would be displayed

        }

    }


    private void show_drop1(String res,String id){

        final Dialog di=new Dialog(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_drop1, null);
        di.setContentView(dialogView);
        di.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        RecyclerView d1list=di.findViewById(R.id.dialog_drop1_list);
        LinearLayout disub=di.findViewById(R.id.dialog_drop1_submit);

        disub.setOnClickListener(v -> di.dismiss());

        JSONObject items = null;
        try {
            items = new JSONObject(res);
            final list_tag_adapter rva2=new list_tag_adapter(items,id);
            d1list.setLayoutManager(new LinearLayoutManager(getActivity()));
            d1list.setItemAnimator(new DefaultItemAnimator());
            d1list.setAdapter(rva2);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        di.show();
    }

    public class list_tag_adapter extends RecyclerView.Adapter<list_tag_adapter.MyViewHolder> {

        private JSONObject list;
        String id;
        public class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView name;
            public CheckBox check;
            public MyViewHolder(View view) {
                super(view);
                name = view.findViewById(R.id.prefilter_row_listtag_name);
                check = view.findViewById(R.id.prefilter_row_listtag_check);

            }
        }


        public list_tag_adapter(JSONObject list,String id) {
            this.list = list;
            this.id = id;
        }

        @Override
        public list_tag_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.prefilter_row_listtag, parent, false);

            return new list_tag_adapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final list_tag_adapter.MyViewHolder holder, final int position) {


            try {
                final JSONObject tmp=new JSONObject(list.getString(position+""));
                holder.name.setText(tmp.getString("name"));


                for (int i=0;i<fields.size();i++){
                    try {
                        if(fields.get(i)[0].equals("val"+id)){
                            if(fields!=null){
                                if(fields.get(i)[1].indexOf(tmp.getString("id")+",")>=0){
                                    holder.check.setChecked(true);
                                }
                            }

                        }
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }
                }

                holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if(isChecked){
                            boolean sw=false;
                            for (int i=0;i<fields.size();i++){
                                try {
                                    if(fields.get(i)[0].equals("val"+id)){
                                        sw=true;
                                        String[] tt = new String[]{"val"+id,fields.get(i)[1]+tmp.getString("id")+","};
                                        fields.set(i,tt);
                                    }
                                } catch (Exception e) {
                                    //e.printStackTrace();
                                }
                            }
                            if(sw==false){
                                try {
                                    String[] tt = new String[]{"val"+id,tmp.getString("id")+","};
                                    fields.add(tt);
                                } catch (JSONException e) {
                                    //e.printStackTrace();
                                }
                            }
                        }else{
                            for (int i=0;i<fields.size();i++){
                                try {
                                    if(fields.get(i)[0].equals("val"+id)){
                                        String[] tt = new String[]{"val"+id,fields.get(i)[1].replace(tmp.getString("id")+",","")};
                                        fields.set(i,tt);
                                    }
                                } catch (Exception e) {
                                    //e.printStackTrace();
                                }
                            }

                        }
                    }
                });
            } catch (JSONException e) {
                //e.printStackTrace();
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


}
