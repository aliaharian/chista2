package com.nikandroid.chista.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.nikandroid.chista.R;

public class main_tab2 extends Fragment {


    public static main_tab2 newInstance(int position,String c) {
        main_tab2 f = new main_tab2();
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
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.main_tab2, container, false);


        return view;
    }






}
