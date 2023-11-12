package es.ucm.fdi.readcycle.presentacion;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import es.ucm.fdi.readcycle.R;
import kotlin.jvm.internal.Intrinsics;

public class AddLibroFragment extends Fragment {

    private EditText resumen;
    private NumberPicker num_paginas;
    ArrayList<EditText> editTextObligatorios = new ArrayList<>();

    private Button añadirBtn;

    private String MSG_FORM_INCOMPLETO = "Formulario incompleto";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_addlibro, container, false);
        añadirBtn = view.findViewById(R.id.buttonAñadir);

        añadirBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {

                /* Hago un array de EditText porque todos son obligatorios para registrar un libro y
                * ahorrarme escribir cuatro veces la comprobación de dentro del if.*/
                editTextObligatorios.add(view.findViewById(R.id.formtitulo));
                editTextObligatorios.add(view.findViewById(R.id.formgenero));
                editTextObligatorios.add(view.findViewById(R.id.formautor));
                editTextObligatorios.add(view.findViewById(R.id.formestado));

                // Dejo el resumen como opcional
                resumen = view.findViewById(R.id.formresumen);
                boolean form_valido = true;

                for (EditText editText: editTextObligatorios){
                    if (editText.getText().toString().trim().equals("")){
                        editText.setError("Requerido");
                        form_valido = false;
                    }
                }

                if (!form_valido) {
                    Toast.makeText(view.getContext(), MSG_FORM_INCOMPLETO, Toast.LENGTH_LONG).show();
                } else {
                    // Guardar en DB, se debería comprobar si el usuario ya tiene un libro igual no??
                }
            }
        });

        Intrinsics.checkNotNullParameter(inflater, "inflater");
        return view;
    }




}
