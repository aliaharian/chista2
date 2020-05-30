package com.nikandroid.chista.viewModels;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class SettingViewModel  extends ViewModel {

    public MutableLiveData<String> LL_main=new MutableLiveData<>();


    private Context context;

    public SettingViewModel(Context context){
        this.context=context;


    }




}
