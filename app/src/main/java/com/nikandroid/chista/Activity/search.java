package com.nikandroid.chista.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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
import com.github.florent37.shapeofview.shapes.CircleView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.nikandroid.chista.Adapters.main_list_adviser_adapter;
import com.nikandroid.chista.Functions.Function;
import com.nikandroid.chista.Params.Params;
import com.nikandroid.chista.R;
import com.nikandroid.chista.ViewModelFactory.MainModelFactory;
import com.nikandroid.chista.ViewModelFactory.SearchModelFactory;
import com.nikandroid.chista.databinding.MainBinding;
import com.nikandroid.chista.databinding.SearchBinding;
import com.nikandroid.chista.viewModels.MainViewModel;
import com.nikandroid.chista.viewModels.SearchViewModel;
import com.skydoves.elasticviews.ElasticImageView;
import com.skydoves.elasticviews.ElasticLayout;
import com.squareup.picasso.Picasso;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class search extends AppCompatActivity {



    private SharedPreferences sp;
    String Claim="";
    String body="";
    RecyclerView tlist,list;
    ProgressBar prog;
    int blackcolor=0;
    String word="";
    EditText eword;
    Function fun;
    ImageView icon,remove;
    //MaterialIconView backicon;
    int twop=0;
    ElasticImageView filterbutton;
    StringRequest psrq;
    String from ="";
    LinearLayout notfound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SearchBinding binding= DataBindingUtil.setContentView(this,R.layout.search);
        binding.setSearchModel(ViewModelProviders.of(this,new SearchModelFactory(this)).get(SearchViewModel.class));


        init();



        clicks();



        body=getIntent().getExtras().getString("body");

        try{
            from=getIntent().getExtras().getString("from")+"";
        }catch (Exception e){

        }

        if(body.equals("-")){
            body="{\"type\":\"filter_result\",\"q\":\"\",\"valcat\":\"\",\"valtag\":\"\",\"valcity\":\"\",\"range\":\"\",\"gender\":\"\",\"isonline\":\"\"}";
            fill_list_history();
            filterbutton.setImageResource(R.drawable.main_filter_btn);
            filterbutton.setBackgroundResource(R.drawable.login_phone_edittext);
        }else{
            filterbutton.setImageResource(R.drawable.main_filter_btn2);
            filterbutton.setBackgroundResource(R.drawable.login_phone_edittext2);
            get_search();
        }

    }

    private void clicks(){

        eword.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch("cl");
                return true;
            }
            return false;
        });

        eword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count==0 && start==0){
                    if(from.equals("prefilter")){
                        get_search();
                    }else{
                        fill_list_history();
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!eword.getText().toString().equals("")){
                    icon.setImageResource(R.drawable.main_icon_backarrow);
                    remove.setVisibility(View.VISIBLE);
                    performSearch("ch");
                }else{
                    icon.setImageResource(R.drawable.main_search_icon);
                    remove.setVisibility(View.GONE);
                    psrq.cancel();
                }
            }
        });

        eword.setOnClickListener(v -> {

        });

        eword.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                fill_list_history();
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                word="";
                eword.setText("");
                try {
                    JSONObject tmp=new JSONObject(body);
                    tmp.put("q","");
                    body=tmp.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(from.equals("prefilter")){
                    get_search();
                }else{
                    fill_list_history();
                }
            }
        });


        icon.setOnClickListener(v -> {

            if(eword.getText().toString().equals("")){
            }else {
                finish();
            }



        });

      //  backicon.setOnClickListener(v -> click_back());

        filterbutton.setOnClickListener(v -> {
            if(from.equals("prefilter")){
                prefilter.body=body;
                finish();

            }else{

                Intent i=new Intent(getActivity(),prefilter.class);
                i.putExtra("body",body);
                startActivity(i);

            }
        });

    }

    private void click_back(){

        /*
        if(from.equals("prefilter") && !eword.getText().toString().equals("")){
            eword.setText("");
            try {
                JSONObject tmp=new JSONObject(body);
                tmp.put("q","");
                body=tmp.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            get_search();
        }else {

            finish();
        }
        */


        if(from.equals("prefilter") && !eword.getText().toString().equals("")) {
            eword.setText("");
            get_search();

        }else if(from.equals("prefilter") && eword.hasFocus()){
            eword.setText("");
            get_search();
            eword.clearFocus();
        }else if(from.equals("prefilter")){
            prefilter.body=body;
            finish();
        }else{
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        click_back();
    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));    }

    private Context getActivity(){
        return search.this;
    }

    private void init(){

        sp=getSharedPreferences(Params.spmain,MODE_PRIVATE);
        fun=new Function(getActivity());
        Claim=sp.getString(Params.spClaim,"-");



        //View actv=fun.Actionbar_4(R.layout.actionbar_search);
        getSupportActionBar().hide();
        eword=findViewById(R.id.search_et_search);
        icon=findViewById(R.id.search_img_icon);
        remove=findViewById(R.id.search_img_remove2);
        //backicon=actv.findViewById(R.id.actionbar_search_back);
        filterbutton=findViewById(R.id.search_btn_filter);
        eword.requestFocus();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(eword, InputMethodManager.SHOW_IMPLICIT);
        blackcolor=getResources().getColor(R.color.siyah);

        list=findViewById(R.id.search_list);
        tlist=findViewById(R.id.search_tagslist);
        prog=findViewById(R.id.search_prog);
        notfound=findViewById(R.id.search_notfound);


    }

    private void performSearch(String flag){

        word=eword.getText().toString();

        try {
            JSONObject tmp=new JSONObject(body);
            tmp.put("q",word);
            body=tmp.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("flag",flag);
        if(flag.equals("cl")){
            if(sp.getString(Params.spsearchhistory,"-").indexOf(","+word)>=0){
            }else{
                SharedPreferences.Editor ed=sp.edit();
                ed.putString(Params.spsearchhistory,sp.getString(Params.spsearchhistory,"")+word+",");
                ed.commit();
                if(!sp.getString(Params.spsearchhistory,"-").startsWith(",")){
                    ed.putString(Params.spsearchhistory,","+sp.getString(Params.spsearchhistory,""));
                    ed.commit();
                }
            }
            get_search();

        }else if(flag.equals("ch")){
            if(word.length()>0){
                icon.setVisibility(View.VISIBLE);
            }else{
                icon.setVisibility(View.INVISIBLE);
            }

            get_pre_search();
        }

    }

    private void get_pre_search(){

        fun.show_debug_dialog(1,"{\"q\":\""+word+"\"}", Params.ws_presearch);

        psrq = new StringRequest(Request.Method.POST, Params.ws_presearch,

                response -> {
                    fun.show_debug_dialog(2,response, Params.ws_presearch);
                    Log.e("sugest",response);
                    make_list_sugest(response);

                },
                error -> {
                fun.show_debug_dialog(2,error.toString(), Params.ws_prefilter);
                //fun.show_error_internet();
                     })

        {

            @Override
            public byte[] getBody() {
                String str = "{\"q\":\""+word+"\"}";
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

        psrq.setShouldCache(false);
        Volley.newRequestQueue(getActivity()).add(psrq);

    }

    private void get_search(){



        eword.clearFocus();
        list.setVisibility(View.GONE);
        prog.setVisibility(View.VISIBLE);


        fun.show_debug_dialog(1,body, Params.ws_prefilter);
        StringRequest rq = new StringRequest(Request.Method.POST, Params.ws_prefilter,
                response -> {
                    fun.show_debug_dialog(2,response, Params.ws_prefilter);
                    try {

                        JSONObject tt=new JSONObject(response);

                        fill_list_tags(tt.getString("selected"),tt.getString("unselected"));

                        make_list_adviser1(tt.getString("advisers"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    fun.show_debug_dialog(2,error.toString(), Params.ws_prefilter);
            fun.show_error_internet();})
        {

            @Override
            public byte[] getBody() {
                String str = body;
                Log.e("prefiltersend",body);
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


    private void fill_list_history(){
        try {
            String hr = sp.getString(Params.spsearchhistory, "-");
            hr = hr.substring(1, hr.length() - 1);
            String[] history = hr.split(",");

            if(history.length==0){
                //Toasty.warning(getActivity(),"نتیجه ای یافت نشد", Toasty.LENGTH_LONG).show();
                //notfound.setVisibility(View.VISIBLE);
            }else{
                notfound.setVisibility(View.GONE);
            }


            final search_history_dapter rva1 = new search_history_dapter(history);
            list.setLayoutManager(new LinearLayoutManager(getActivity()));
            list.setItemAnimator(new DefaultItemAnimator());
            list.setAdapter(rva1);
        }catch (Exception e){

        }
    }
    public class search_history_dapter extends RecyclerView.Adapter<search_history_dapter.MyViewHolder> {

        private String[] list;
        public class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView name,desc;
            public MaterialIconView remove,icon;
            LinearLayout ll;
            public MyViewHolder(View view) {
                super(view);
                name =  view.findViewById(R.id.row_search_history_text);
                desc =  view.findViewById(R.id.row_search_history_des);
                remove =  view.findViewById(R.id.row_search_history_remove);
                icon =  view.findViewById(R.id.row_search_history_icon);
                ll =  view.findViewById(R.id.row_search_history_lll);
            }
        }


        public search_history_dapter(String[] list) {
            this.list = list;
        }

        @Override
        public search_history_dapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_search_history, parent, false);
            return new search_history_dapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final search_history_dapter.MyViewHolder holder, final int position) {


            holder.desc.setVisibility(View.GONE);
            if(list[position].length()<2){
                holder.itemView.setVisibility(View.GONE);
            }else{
                holder.name.setText(list[position]);
            }

            holder.ll.setOnClickListener(v -> {
                eword.setText(list[position]);
                performSearch("cl");
            });

            holder.remove.setOnClickListener(v -> {
                SharedPreferences.Editor ed=sp.edit();
                ed.putString(Params.spsearchhistory,sp.getString(Params.spsearchhistory,"-").replace(","+list[position]+",",","));
                ed.commit();
                fill_list_history();
            });

        }

        @Override
        public int getItemCount() {
            return list.length;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }



    }



    private void make_list_sugest(String respons)  {

        JSONObject items = null;
        try {
            JSONObject part = new JSONObject(respons);
            final search_suggest_adapter rva1=new search_suggest_adapter(part);
            list.setLayoutManager(new LinearLayoutManager(getActivity()));
            list.setItemAnimator(new DefaultItemAnimator());
            list.setAdapter(rva1);

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
    public class search_suggest_adapter extends RecyclerView.Adapter<search_suggest_adapter.MyViewHolder> {

        private JSONObject list;
        public class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView name,des;
            public MaterialIconView remove,icon,icon2;
            CircleView cimg;
            ImageView img;
            public MyViewHolder(View view) {
                super(view);
                name =  view.findViewById(R.id.row_search_history_text);
                des =  view.findViewById(R.id.row_search_history_des);
                remove =  view.findViewById(R.id.row_search_history_remove);
                icon =  view.findViewById(R.id.row_search_history_icon);
                icon2 =  view.findViewById(R.id.row_search_history_iconarrow);

                cimg =  view.findViewById(R.id.row_search_history_imgcircle);
                img =  view.findViewById(R.id.row_search_history_img);
            }
        }

        public search_suggest_adapter(JSONObject list) {
            this.list = list;
        }

        @Override
        public search_suggest_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_search_history, parent, false);
            return new search_suggest_adapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final search_suggest_adapter.MyViewHolder holder, final int position) {

            holder.remove.setVisibility(View.INVISIBLE);
            try {
                final JSONObject part = new JSONObject(list.getString((position+1)+""));
                holder.name.setText(part.getString("name"));
                holder.des.setText("در "+part.getString("desc"));
                holder.name.setTextColor(blackcolor);
                holder.icon.setVisibility(View.GONE);
                holder.icon2.setVisibility(View.VISIBLE);

                if(part.getString("type").equals("adviser")){
                    holder.cimg.setVisibility(View.VISIBLE);

                    Picasso
                            .get()
                            .load(Params.pic_adviseravatae+part.getString("avatar"))
                            .error(R.drawable.avatar_tmp1)
                            .into(holder.img);
                }

            holder.itemView.setOnClickListener(v -> {

                try {
                    //final JSONObject part = new JSONObject(list.getString((position+1)+""));
                    word=part.getString("name");
                    eword.setText(part.getString("name") );

                    Log.e("11111",part.getString("type"));

                    if(part.getString("type").equals("tag")){
                        Log.e("555555",part.getString("type"));
                        JSONObject tmp=new JSONObject(body);
                        String c=tmp.getString("valtag");
                        c+=part.getString("id")+",";
                        tmp.put("valtag",c);
                        body=tmp.toString();
                        performSearch("cl");

                    }else if(part.getString("type").equals("cat")){
                        Log.e("44444",part.getString("type"));
                        JSONObject tmp=new JSONObject(body);
                        String c=tmp.getString("valcat");
                        c+=part.getString("id")+",";
                        tmp.put("valcat",c);
                        body=tmp.toString();
                        performSearch("cl");

                    }else if(part.getString("type").equals("adviser")){
                        Log.e("22222",part.getString("type"));
                        try {
                            Log.e("33333",part.getString("type"));
                            Intent i=new Intent(getActivity(),user.class);
                            i.putExtra("adid",part.getString("id"));
                            startActivity(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }



                    //performSearch("cl");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });

            }catch (JSONException e) {
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


    private void fill_list_tags(String selected,String unselected){
        try {


            JSONObject sel = new JSONObject(selected);
            JSONObject unsel = new JSONObject(unselected);

            if(unsel.length()>0){
                filterbutton.setImageResource(R.drawable.main_filter_btn);
                filterbutton.setBackgroundResource(R.drawable.login_phone_edittext);
            }else{
                filterbutton.setImageResource(R.drawable.main_filter_btn2);
                filterbutton.setBackgroundResource(R.drawable.login_phone_edittext2);
            }

            final search_list_tags_adapter rva1=new search_list_tags_adapter(sel,unsel);
            tlist.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true));
            tlist.setItemAnimator(new DefaultItemAnimator());
            tlist.setAdapter(rva1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tlist.post(new Runnable() {
            @Override
            public void run() {
                twop=0;
            }
        });



    }
    public class search_list_tags_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final int VIEW_TYPE_SELECTED= 2;
        private final int VIEW_TYPE_UNSELECTED= 1;

        private JSONObject selected,unselected;



        public search_list_tags_adapter(JSONObject sel,JSONObject unsel) {

            this.selected = sel;
            this.unselected = unsel;
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            if (viewType == VIEW_TYPE_SELECTED) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_tag_row1, parent, false);
                return new search_list_tags_adapter.selectedholder(view);
            }else{
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_tag_row1, parent, false);
                return new search_list_tags_adapter.unselectedholder(view);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

            if (viewHolder instanceof search_list_tags_adapter.selectedholder) {
                selected((search_list_tags_adapter.selectedholder) viewHolder, position);
            }else{
                unselected((search_list_tags_adapter.unselectedholder) viewHolder, position);
            }

        }

        @Override
        public int getItemCount() {
            return selected.length()+unselected.length()+1;
        }


        @Override
        public int getItemViewType(int position) {

            if((position)<=selected.length()){
                return VIEW_TYPE_SELECTED;
            }else{
                return VIEW_TYPE_UNSELECTED;
            }

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
        private void selected(final search_list_tags_adapter.selectedholder holder, int position) {


            if(position==0){
                holder.itemView.setVisibility(View.GONE);
/*
                holder.ll.setVisibility(View.VISIBLE);
                holder.name.setText("فیلترها");
                holder.remove.setIcon(MaterialDrawableBuilder.IconValue.FILTER);
                holder.ll.setBackgroundResource(R.drawable.z_catgory_tag_back1);
                holder.ll.setOnClickListener(v -> {


                    if(from.equals("prefilter")){
                        prefilter.body=body;
                        finish();

                    }else{

                        Intent i=new Intent(getActivity(),prefilter.class);
                        i.putExtra("body",body);
                        startActivity(i);

                    }

                });*/
            }else{
                try {
                    holder.ll.setVisibility(View.VISIBLE);
                    final JSONObject tmp=new JSONObject(selected.getString("item"+(position)));
                    holder.name.setText(tmp.getString("name"));
                    holder.ll.setBackgroundResource(R.drawable.z_catgory_tag_back1);
                    holder.ll.setOnClickListener(v -> {
                        try {
                            make_selected_jason(tmp.getString("type"),tmp.getString("id"),tmp.getString("name"),"m");
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }

                    });


                }catch (Exception e) {

                }
            }





        }


        private class unselectedholder extends RecyclerView.ViewHolder {

            public TextView name;
            MaterialIconView remove;
            public LinearLayout ll;

            public unselectedholder(@NonNull View itemView) {
                super(itemView);
                name =  itemView.findViewById(R.id.category_tag_row1_name);
                remove =  itemView.findViewById(R.id.category_tag_row1_remove);
                ll =  itemView.findViewById(R.id.category_tag_row1_ll);

            }
        }
        private void unselected(final search_list_tags_adapter.unselectedholder holder, int position) {

            try {
                holder.ll.setVisibility(View.VISIBLE);
                final JSONObject tmp2=new JSONObject(unselected.getString("item"+(position-(selected.length()))));
                holder.name.setText(tmp2.getString("name"));
                holder.name.setTextColor(getResources().getColor(R.color.khakestari1));
                holder.remove.setVisibility(View.GONE);
                holder.ll.setBackgroundResource(R.drawable.z_category_tag_back2);
                holder.ll.setOnClickListener(v -> {


                    try {
                        make_selected_jason(tmp2.getString("type"),tmp2.getString("id"),tmp2.getString("name"),"p");
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }

                });
            }catch (Exception ee){
            }

        }




    }



    private void make_list_adviser1(String respons)  {

        JSONObject items = null;
        try {
            list.setVisibility(View.VISIBLE);
            prog.setVisibility(View.GONE);
            items = new JSONObject(respons);

            if(items.length()==0){
                //Toasty.warning(getActivity(),"نتیجه ای یافت نشد", Toasty.LENGTH_LONG).show();
                notfound.setVisibility(View.VISIBLE);
            }else{
                notfound.setVisibility(View.GONE);
            }
            final main_list_adviser_adapter rva1=new main_list_adviser_adapter(items,getActivity(),fun);
            list.setLayoutManager(new LinearLayoutManager(getActivity()));
            list.setItemAnimator(new DefaultItemAnimator());
            list.setAdapter(rva1);


        } catch (JSONException e) {
            e.printStackTrace();
        }



    }





    private void make_selected_jason(String type,String id,String name,String flag) {

        if(type.equals("valcat") || type.equals("valcity")){
            if(flag.equals("p")){
                try {
                    JSONObject tmp=new JSONObject(body);
                    String c=tmp.getString(type);
                    c+=id+",";
                    tmp.put(type,c);
                    body=tmp.toString();
                    get_search();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                if(id.equals("0")){
                    try {
                        JSONObject tmp=new JSONObject(body);
                        String c=tmp.getString(type);
                        tmp.put(type,"");
                        body=tmp.toString();
                        get_search();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        JSONObject tmp=new JSONObject(body);
                        String c=tmp.getString(type);

                        c=c.replace(id+",","");

                        tmp.put(type,c);
                        body=tmp.toString();
                        get_search();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }else if(type.equals("range")){

            try {
                JSONObject tmp=new JSONObject(body);
                String c=tmp.getString(type);
                tmp.put(type,"");
                body=tmp.toString();
                get_search();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else if(type.equals("isonline")){

            if(flag.equals("p")){
                show_dialog_status(id);
            }else{
                try {
                    JSONObject tmp=new JSONObject(body);
                    tmp.put("isonline","");
                    body=tmp.toString();
                    get_search();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



/*
            try {
                JSONObject tmp=new JSONObject(body);
                String c=tmp.getString(type);

                if(c.equals("1")){
                    c="0";
                }else{
                    c="1";
                }
                tmp.put(type,"");
                body=tmp.toString();
                get_search();
            } catch (JSONException e) {
                e.printStackTrace();
            }*/

        } else if(type.equals("clearcat")){
            try {
                JSONObject tmp=new JSONObject(body);
                tmp.put("valcat","");
                body=tmp.toString();
                get_search();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        } else if(type.equals("gender")){


            //show_dialog_sex(id);

            if(flag.equals("p")){
                show_dialog_sex(id);
            }else{
                try {
                    JSONObject tmp=new JSONObject(body);
                    tmp.put("gender","");
                    body=tmp.toString();
                    get_search();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }else{
            try {
                JSONObject tmp=new JSONObject(body);
                String c=tmp.getString(type);
                tmp.remove(type);
                body=tmp.toString();
                get_search();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }







    }

    private void show_drop1(String id){

        final Dialog di=new Dialog(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_drop2, null);
        di.setContentView(dialogView);
        di.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        LinearLayout disub=di.findViewById(R.id.dialog_drop2_submit);
        final CheckBox item1=di.findViewById(R.id.dialog_drop2_item1);
        final CheckBox item2=di.findViewById(R.id.dialog_drop2_item2);

        if (id.equals("1")){
            item1.setChecked(true);
        }
        if (id.equals("0")){
            item2.setChecked(true);
        }


        disub.setOnClickListener(v -> {

            String tmpg="";
            if(item1.isChecked()){
                tmpg="1,";
            }
            if (item2.isChecked()){
                tmpg="0,";
            }
            if (item1.isChecked() && item2.isChecked()){
                tmpg="1,0,";
            }

            try {
                JSONObject tmp=new JSONObject(body);
                tmp.put("gender",tmpg);
                body=tmp.toString();
                get_search();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            di.dismiss();

        });



        di.show();
    }


    private void show_dialog_sex(String sexid){


        LayoutInflater inflater = this.getLayoutInflater();
        BottomSheetDialog di=new BottomSheetDialog(getActivity(),R.style.SheetDialog);
        View dialogView = inflater.inflate(R.layout.dialog_drop2, null);
        di.setContentView(dialogView);
        di.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ElasticLayout disub=di.findViewById(R.id.dialog_drop2_submit);
        final RadioButton item1=di.findViewById(R.id.dialog_drop2_item1);
        final RadioButton item2=di.findViewById(R.id.dialog_drop2_item2);

        if(sexid.equals("0")){
            item1.setChecked(true);
        }else if(sexid.equals("1")){
            item2.setChecked(true);
        }




        disub.setOnClickListener(v -> {

            if (item1.isChecked()){


                try {
                    JSONObject tmp=new JSONObject(body);
                    tmp.put("gender","1");
                    body=tmp.toString();
                    get_search();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                di.dismiss();
            }else if (item2.isChecked()){

                try {
                    JSONObject tmp=new JSONObject(body);
                    tmp.put("gender","0");
                    body=tmp.toString();
                    get_search();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                di.dismiss();
            }else{
                Toast.makeText(getApplicationContext(),"لطفا یک گزینه انتخاب نمایید",Toast.LENGTH_LONG).show();
            }

        });



        di.show();
    }

    private void show_dialog_status(String stid){


        LayoutInflater inflater = this.getLayoutInflater();
        BottomSheetDialog di=new BottomSheetDialog(getActivity(),R.style.SheetDialog);
        View dialogView = inflater.inflate(R.layout.dialog_drop3, null);
        di.setContentView(dialogView);
        di.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ElasticLayout disub=di.findViewById(R.id.dialog_drop3_submit);
        final RadioButton item1=di.findViewById(R.id.dialog_drop3_item1);
        final RadioButton item2=di.findViewById(R.id.dialog_drop3_item2);


        if(stid.equals("1")){
            item1.setChecked(true);
        }else if(stid.equals("0")){
            item2.setChecked(true);
        }




        disub.setOnClickListener(v -> {

            if (item1.isChecked()){


                try {
                    JSONObject tmp=new JSONObject(body);
                    tmp.put("isonline","1");
                    body=tmp.toString();
                    get_search();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                di.dismiss();
            }else if (item2.isChecked()){

                try {
                    JSONObject tmp=new JSONObject(body);
                    tmp.put("isonline","0");
                    body=tmp.toString();
                    get_search();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                di.dismiss();
            }else{
                Toast.makeText(getApplicationContext(),"لطفا یک گزینه انتخاب نمایید",Toast.LENGTH_LONG).show();
            }

        });



        di.show();
    }


}
