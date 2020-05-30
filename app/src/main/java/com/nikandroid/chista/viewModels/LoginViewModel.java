package com.nikandroid.chista.viewModels;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;



public class LoginViewModel  extends ViewModel {

    public MutableLiveData<String> tv_desc=new MutableLiveData<>();
    public MutableLiveData<String> et_phonenumber=new MutableLiveData<>();
    public MutableLiveData<String> et_vcode1=new MutableLiveData<>();
    public MutableLiveData<String> et_vcode2=new MutableLiveData<>();
    public MutableLiveData<String> et_vcode3=new MutableLiveData<>();
    public MutableLiveData<String> et_vcode4=new MutableLiveData<>();
    public MutableLiveData<String> tv_status=new MutableLiveData<>();
    public MutableLiveData<String> tv_callphone=new MutableLiveData<>();

    private Context context;

    public LoginViewModel(Context context){
        this.context=context;





    }







}
