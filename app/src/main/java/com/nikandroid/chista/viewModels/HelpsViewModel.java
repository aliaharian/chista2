package com.nikandroid.chista.viewModels;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HelpsViewModel  extends ViewModel {

    public MutableLiveData<String> LL_main=new MutableLiveData<>();


    private Context context;

    public HelpsViewModel(Context context){
        this.context=context;


    }




}