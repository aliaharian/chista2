package com.nikandroid.chista.viewModels;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class RulesViewModel extends ViewModel {
    public MutableLiveData<String> LL_main=new MutableLiveData<>();


    private Context context;

    public RulesViewModel(Context context){
        this.context=context;


    }

}
