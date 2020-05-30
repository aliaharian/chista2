package com.nikandroid.chista.ViewModelFactory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.nikandroid.chista.viewModels.CallsViewModel;
import com.nikandroid.chista.viewModels.Credit2ViewModel;
import com.nikandroid.chista.viewModels.CreditViewModel;

public class Credit2ModelFactory extends ViewModelProvider.NewInstanceFactory {

    private Context context;


    public Credit2ModelFactory(Context context) {
        this.context=context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new Credit2ViewModel(context);
    }
}
