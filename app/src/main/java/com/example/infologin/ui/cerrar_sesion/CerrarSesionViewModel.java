package com.example.infologin.ui.cerrar_sesion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CerrarSesionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CerrarSesionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Pantalla de tickets en progreso");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
