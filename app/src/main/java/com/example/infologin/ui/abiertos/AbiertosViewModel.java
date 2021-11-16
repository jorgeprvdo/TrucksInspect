package com.example.infologin.ui.abiertos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AbiertosViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AbiertosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Pantalla de tickets abiertos");
    }

    public LiveData<String> getText() {
        return mText;
    }
}