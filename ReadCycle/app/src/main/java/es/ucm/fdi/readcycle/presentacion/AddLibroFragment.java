package es.ucm.fdi.readcycle.presentacion;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import es.ucm.fdi.readcycle.R;
import es.ucm.fdi.readcycle.negocio.BookInfo;
import es.ucm.fdi.readcycle.negocio.SABook;
import kotlin.jvm.internal.Intrinsics;

public class AddLibroFragment extends Fragment {

    private EditText resumen, titulo, autor, estado, genero, num_paginas;
    //private NumberPicker num_paginas;
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

                titulo = view.findViewById(R.id.formtitulo);
                genero = view.findViewById(R.id.formgenero);
                autor = view.findViewById(R.id.formautor);
                estado = view.findViewById(R.id.formestado);
                resumen = view.findViewById(R.id.formresumen);

                ArrayList<EditText> editTextObligatorios = new ArrayList<EditText>() {{add(titulo);
                        add(genero); add(autor); add(estado); add(resumen);}};

                boolean form_valido = true;

                /* Hago un array de EditText porque todos son obligatorios para registrar un libro y
                 * ahorrarme escribir cuatro veces la comprobación de dentro del if.*/
                for (EditText editText: editTextObligatorios){
                    if (editText.getText().toString().trim().equals("")){
                        editText.setError("Requerido");
                        form_valido = false;
                    }
                }

                if (!form_valido) {
                    Toast.makeText(view.getContext(), MSG_FORM_INCOMPLETO, Toast.LENGTH_LONG).show();
                } else {
                    BookInfo nuevo_libro = new BookInfo(titulo.getText().toString(), genero.getText().toString(),
                            autor.getText().toString(), estado.getText().toString(), resumen.getText().toString(), "",
                            Integer.parseInt(num_paginas.getText().toString()));

                    SABook saBookInfo = new SABook();

                    // Guardar en DB, se debería comprobar si el usuario ya tiene un libro igual no??
                }
            }
        });

        Intrinsics.checkNotNullParameter(inflater, "inflater");
        return view;
    }




}
