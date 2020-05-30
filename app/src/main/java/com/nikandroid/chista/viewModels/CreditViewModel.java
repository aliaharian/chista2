package com.nikandroid.chista.viewModels;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CreditViewModel extends ViewModel {

    public MutableLiveData<String> LL_main=new MutableLiveData<>();


    private Context context;

    public CreditViewModel(Context context){
        this.context=context;


    }




}
