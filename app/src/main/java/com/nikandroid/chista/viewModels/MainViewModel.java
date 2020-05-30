package com.nikandroid.chista.viewModels;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;



public class MainViewModel  extends ViewModel {

    public MutableLiveData<String> LL_main=new MutableLiveData<>();


    private Context context;

    public MainViewModel(Context context){
        this.context=context;


    }




}
