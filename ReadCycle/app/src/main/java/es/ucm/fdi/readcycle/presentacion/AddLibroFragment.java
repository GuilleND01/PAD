package es.ucm.fdi.readcycle.presentacion;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.lang.reflect.Array;
import java.util.ArrayList;

import es.ucm.fdi.readcycle.R;
import es.ucm.fdi.readcycle.negocio.BookInfo;
import es.ucm.fdi.readcycle.negocio.SABook;
import kotlin.jvm.internal.Intrinsics;

public class AddLibroFragment extends Fragment {

    private EditText resumen, titulo, autor, estado, genero, num_paginas;
    private Button añadirBtn, añadirGenero, resetGeneros;

    private TextView lista_generos_view;

    private ArrayList<String> lista_generos;

    private String MSG_FORM_INCOMPLETO = "Formulario incompleto";
    private String MSG_EROR_YA_EXISTE = "El libro ya existe en tu biblioteca";
    private String MSG_EROR_EXITO = "Libro añadido con éxito";
    private String MSG_ERROR_GENERAL = "Algo ha salido mal. Vuelve a intentarlo";
    private View view;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_addlibro, container, false);
        añadirBtn = view.findViewById(R.id.buttonAñadir);
        añadirGenero = view.findViewById(R.id.btn_anadir_genero);

        añadirGenero.setBackgroundColor(getResources().getColor(R.color.fondoOscuro));
        añadirBtn.setBackgroundColor(getResources().getColor(R.color.botonAdd));

        genero = view.findViewById(R.id.formgenero);
        resetGeneros = view.findViewById(R.id.btn_reset_genero);
        lista_generos = new ArrayList<String>();

        resetGeneros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lista_generos.clear();
                lista_generos_view.setText("Géneros añadidos: ");
            }
        });
        añadirGenero.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String new_genero = genero.getText().toString();
                if(!new_genero.trim().equals("")){
                    lista_generos_view = view.findViewById(R.id.generos_anadidos);
                    lista_generos.add(genero.getText().toString());
                    genero.setText("");
                    lista_generos_view.setText("Géneros añadidos: " + lista_generos.toString());
                }
            }
        });
        añadirBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {

                titulo = view.findViewById(R.id.formtitulo);
                autor = view.findViewById(R.id.formautor);
                estado = view.findViewById(R.id.formestado);
                resumen = view.findViewById(R.id.formresumen);
                num_paginas = view.findViewById(R.id.formnumpaginas);

                ArrayList<EditText> editTextObligatorios = new ArrayList<EditText>() {{add(titulo);
                        add(genero); add(autor); add(estado);}};

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
                    BookInfo nuevo_libro = new BookInfo(titulo.getText().toString(), lista_generos,
                            autor.getText().toString(), estado.getText().toString(), resumen.getText().toString(), "",
                            Integer.parseInt(num_paginas.getText().toString()));

                    SABook saBookInfo = new SABook();
                    int res_guardar = saBookInfo.guardarLibro(nuevo_libro);
                    if (res_guardar == 1) {
                        Toast.makeText(view.getContext(), MSG_EROR_EXITO, Toast.LENGTH_LONG).show();
                    } else if (res_guardar == 0){
                        Toast.makeText(view.getContext(), MSG_EROR_YA_EXISTE, Toast.LENGTH_LONG).show();
                    } else Toast.makeText(view.getContext(), MSG_ERROR_GENERAL, Toast.LENGTH_LONG).show();
                }
            }
        });

        Intrinsics.checkNotNullParameter(inflater, "inflater");
        return view;
    }




}
