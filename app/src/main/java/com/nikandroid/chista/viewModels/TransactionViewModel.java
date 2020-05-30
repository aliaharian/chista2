package com.nikandroid.chista.viewModels;

import android.content.Context;
import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class TransactionViewModel  extends ViewModel {

    public MutableLiveData<String> LL_main=new MutableLiveData<>();


    private Context context;

    public TransactionViewModel(Context context){
        this.context=context;


    }




}
