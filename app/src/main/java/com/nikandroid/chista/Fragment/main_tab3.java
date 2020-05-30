package com.nikandroid.chista.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.nikandroid.chista.Activity.Credit;
import com.nikandroid.chista.Activity.Fav;
import com.nikandroid.chista.Activity.Mycomments;
import com.nikandroid.chista.Activity.Score;
import com.nikandroid.chista.Activity.Privacy;
import com.nikandroid.chista.Activity.Rules;
import com.nikandroid.chista.Activity.Submit_Adviser;
import com.nikandroid.chista.Activity.setting;
import com.nikandroid.chista.Activity.user_edits;
import com.nikandroid.chista.Functions.Function;
import com.nikandroid.chista.Params.Params;
import com.nikandroid.chista.R;
import com.skydoves.elasticviews.ElasticImageView;
import com.squareup.picasso.Picasso;


public class main_tab3 extends Fragment {

        private SharedPreferences sp;
        private Function fun;
        String Claim="",content="";
        ImageView avatar_pic;

        ////////////////////////////////////xml
        LinearLayout creditarea,myfavs,mycommentsarea,callsarea,settingarea,rulesarea,privecyarea,submitadviserarea;
        ElasticImageView logout,qu,useredit;
        TextView fullname,phone,creditvalue1;


    public static main_tab3 newInstance(int position, String c) {

            main_tab3 f = new main_tab3();
            Bundle b = new Bundle();
            b.putInt("position", position);
            b.putString("content", c);
            f.setArguments(b);
            return f;

        }



        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            int position = getArguments().getInt("position");
            content=getArguments().getString("content");
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


            fun = new Function(getActivity());
            sp=getActivity().getSharedPreferences(Params.spmain,getActivity().MODE_PRIVATE);
            View view = inflater.inflate(R.layout.main_tab3, container, false);

            phone=view.findViewById(R.id.main_tab3_phonenumber);
            avatar_pic=view.findViewById(R.id.main_tab3_avatar_image);
            fullname=view.findViewById(R.id.main_tab3_fullname);
            creditarea=view.findViewById(R.id.main_tab3_creditarea);
            mycommentsarea=view.findViewById(R.id.main_tab3_mycommentsarea);
            myfavs=view.findViewById(R.id.main_tab3_myfavarea);
            callsarea=view.findViewById(R.id.main_tab3_callsarea);
            settingarea=view.findViewById(R.id.main_tab3_settingarea);
            rulesarea=view.findViewById(R.id.main_tab3_rulesarea);
            privecyarea=view.findViewById(R.id.main_tab3_privecyarea);
            submitadviserarea=view.findViewById(R.id.main_tab3_submitadviserarea);
            logout=view.findViewById(R.id.main_tab3_exit);
            qu=view.findViewById(R.id.main_tab3_qu);
            useredit=view.findViewById(R.id.main_tab3_useredit);
            creditvalue1=view.findViewById(R.id.main_tab3_creditvalue1);

            creditvalue1.setText(sp.getString(Params.sp_lastcredit,"0")+" تومان");


            clicks();

            load_avatar();

            return view;
        }



        private void load_avatar(){

            fullname.setText(sp.getString(Params.sp_name,"-")+" "+sp.getString(Params.sp_lastname,"-"));
            phone.setText(sp.getString(Params.sp_mobile,"-"));
            Picasso.get()
                    .load(Params.pic_adviseravatae+sp.getString(Params.sp_avatar,"-"))
                    .placeholder(R.drawable.avatar_tmp1)
                    .resize(200,200)
                    .error(R.drawable.avatar_tmp1)
                    .into(avatar_pic);

        }



        private void clicks(){

           // editprofile.setOnClickListener(v -> startActivity(new Intent(getActivity(), setting_user.class)));
            useredit.setOnClickListener(v -> startActivity(new Intent(getActivity(), user_edits.class)));

            creditarea.setOnClickListener(v -> startActivity(new Intent(getActivity(), Credit.class)));
            mycommentsarea.setOnClickListener(v -> startActivity(new Intent(getActivity(), Mycomments.class)));
            myfavs.setOnClickListener(v -> startActivity(new Intent(getActivity(), Fav.class)));
           // callsarea.setOnClickListener(v -> startActivity(new Intent(getActivity(), Calls.class)));

            settingarea.setOnClickListener(v -> startActivity(new Intent(getActivity(), setting.class)));
            rulesarea.setOnClickListener(v -> {
                Intent i=new Intent(getActivity(), Rules.class);
                i.putExtra("flag","rules");
                startActivity(i);
            });

            privecyarea.setOnClickListener(v -> {
                Intent i=new Intent(getActivity(), Rules.class);
                i.putExtra("flag","privacy");
                startActivity(i);
            });
            submitadviserarea.setOnClickListener(v -> startActivity(new Intent(getActivity(), Submit_Adviser.class)));

            logout.setOnClickListener(v -> {
                fun.Sign_out();
            });

            qu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor ed=sp.edit();
                    ed.putString(Params.sp_isadviser,"1");
                    ed.commit();
                    Toast.makeText(getActivity(),"شما هم اکنون مشاور هستید. خارج شوید از برنامه و مجدد وارد شوید",Toast.LENGTH_LONG).show();
                }
            });


        }



}
