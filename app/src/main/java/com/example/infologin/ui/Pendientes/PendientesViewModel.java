package com.example.infologin.ui.Pendientes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PendientesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PendientesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Pantalla de tickets Pendientes");
    }

    public LiveData<String> getText() {
        return mText;
    }
}