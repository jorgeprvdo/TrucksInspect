package com.example.infologin.ui.Pendientes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.infologin.R;
import com.example.infologin.ui.Pendientes.PendientesViewModel;

public class PendientesFragment extends Fragment {

    private PendientesViewModel pendientesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        pendientesViewModel =
                new ViewModelProvider(this).get(PendientesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_pendientes, container, false);
        final TextView textView = root.findViewById(R.id.text_pendientes);
        pendientesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) { textView.setText(s); }
        });
        return root;
    }
}