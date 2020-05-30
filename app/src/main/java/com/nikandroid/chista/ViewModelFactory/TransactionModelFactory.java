package com.nikandroid.chista.ViewModelFactory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.nikandroid.chista.viewModels.MainViewModel;
import com.nikandroid.chista.viewModels.SearchViewModel;
import com.nikandroid.chista.viewModels.TransactionViewModel;

public class TransactionModelFactory extends ViewModelProvider.NewInstanceFactory {

    private Context context;


    public TransactionModelFactory(Context context) {
        this.context=context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new TransactionViewModel(context);
    }
}
