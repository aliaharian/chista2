package com.nikandroid.chista.ViewModelFactory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.nikandroid.chista.viewModels.CommentsViewModel;


public class CommentsModelFactory extends ViewModelProvider.NewInstanceFactory {

    private Context context;


    public CommentsModelFactory(Context context) {
        this.context=context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new CommentsViewModel(context);
    }
}
