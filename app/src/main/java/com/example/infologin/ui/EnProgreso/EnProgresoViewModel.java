package com.example.infologin.ui.EnProgreso;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EnProgresoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EnProgresoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Pantalla de tickets en progreso");
    }

    public LiveData<String> getText() {
        return mText;
    }
}