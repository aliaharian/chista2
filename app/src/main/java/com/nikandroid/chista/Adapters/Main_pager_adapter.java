package com.nikandroid.chista.Adapters;

import android.content.SharedPreferences;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.nikandroid.chista.Fragment.main_tab1;
import com.nikandroid.chista.Fragment.main_tab2;
import com.nikandroid.chista.Fragment.main_tab3;
import com.nikandroid.chista.Fragment.main_tab3_adviser;
import com.nikandroid.chista.Params.Params;


public class Main_pager_adapter extends FragmentPagerAdapter {
    String part1;
    SharedPreferences sp;
    public Main_pager_adapter(FragmentManager fm, String content, SharedPreferences sp) {
        super(fm);
        part1=content;
        this.sp=sp;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }
    @Override
    public int getCount() {
        return 3;
    }
    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            if(sp.getString(Params.sp_isadviser,"1").equals("1")){
                return main_tab3_adviser.newInstance(position,part1);
                //return main_tab3.newInstance(position,part1);
            }else{
                return main_tab3.newInstance(position,part1);
            }


        } else if (position == 1){
            return main_tab2.newInstance(position,part1);
        } else{
            return main_tab1.newInstance(position,part1);
        }
    }

}
