package com.example.infologin.ui.cerrados;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CerradosViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CerradosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Pantalla de tickets cerrados");
    }

    public LiveData<String> getText() {
        return mText;
    }
}