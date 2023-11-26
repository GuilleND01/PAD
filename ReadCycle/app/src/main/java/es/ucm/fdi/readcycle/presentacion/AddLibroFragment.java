package es.ucm.fdi.readcycle.presentacion;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;

import es.ucm.fdi.readcycle.R;
import es.ucm.fdi.readcycle.negocio.BookInfo;
import es.ucm.fdi.readcycle.negocio.SABook;
import kotlin.jvm.internal.Intrinsics;

public class AddLibroFragment extends Fragment {

    private EditText resumen, titulo, autor, genero, num_paginas;
    private Spinner estado;

    private android.net.Uri selectedImage;

    private Button añadirBtn, añadirGenero, resetGeneros;

    private ImageButton añadirFoto;
    private TextView lista_generos_view;

    private ArrayList<String> lista_generos = new ArrayList<String>();


    private String MSG_FORM_INCOMPLETO = "Formulario incompleto";
    private String MSG_EROR_YA_EXISTE = "El libro ya existe en tu biblioteca";
    private String MSG_EROR_EXITO = "Libro añadido con éxito";
    private String MSG_ERROR_GENERAL = "Algo ha salido mal. Vuelve a intentarlo";
    private int estado_sel;
    private View view;

    private static final int PICK_IMAGE_REQUEST = 1;




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_addlibro, container, false);
        añadirBtn = view.findViewById(R.id.buttonAñadir);
        añadirGenero = view.findViewById(R.id.btn_anadir_genero);

        añadirGenero.setBackgroundColor(getResources().getColor(R.color.fondoOscuro));
        añadirBtn.setBackgroundColor(getResources().getColor(R.color.botonAdd));

        genero = view.findViewById(R.id.formgenero);
        resetGeneros = view.findViewById(R.id.btn_reset_genero);
        lista_generos_view = view.findViewById(R.id.generos_anadidos);

        añadirFoto = view.findViewById(R.id.addPhotoBtn);
        estado = (Spinner) view.findViewById(R.id.formestado);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.books_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        estado.setAdapter(adapter);

        estado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Obtener el elemento seleccionado
                estado_sel = (int) position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Acciones cuando no se ha seleccionado nada
            }
        });

        resetGeneros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lista_generos.clear();
                lista_generos_view.setText("No ha generos añadidos"); //TODO CAMBIAR A STRINGS
            }
        });
        añadirGenero.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String new_genero = genero.getText().toString();
                if(!new_genero.trim().equals("")){
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
                        add(autor);}};

                boolean form_valido = true;

                /* Hago un array de EditText porque todos son obligatorios para registrar un libro y
                 * ahorrarme escribir cuatro veces la comprobación de dentro del if.*/
                for (EditText editText: editTextObligatorios){
                    if (editText.getText().toString().trim().equals("")){
                        editText.setError("Requerido");
                        form_valido = false;
                    }
                }

                if (lista_generos.isEmpty()){
                    form_valido = false;
                    genero.setError("Requerido");
                }
                if (!form_valido) {
                    Toast.makeText(view.getContext(), MSG_FORM_INCOMPLETO, Toast.LENGTH_LONG).show();
                } else {
                    BookInfo nuevo_libro = new BookInfo(titulo.getText().toString(), lista_generos,
                            autor.getText().toString(), estado_sel, resumen.getText().toString(), "",
                            Integer.parseInt(num_paginas.getText().toString()), selectedImage);

                    SABook saBookInfo = new SABook();
                    int res_guardar = saBookInfo.guardarLibro(nuevo_libro);
                    if (res_guardar == 1) {
                        Toast.makeText(view.getContext(), MSG_EROR_EXITO, Toast.LENGTH_LONG).show();
                    } else Toast.makeText(view.getContext(), MSG_ERROR_GENERAL, Toast.LENGTH_LONG).show();
                }
            }
        });


        añadirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creamos un intent para acceder a la galeria
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
                startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
            }
        });

        Intrinsics.checkNotNullParameter(inflater, "inflater");
        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("CLAU", "ESTAMOS EN EL onactivity");
        super.onActivityResult(requestCode, resultCode, data);
        //seleccionamos la imagen y la sustituimos por el boton
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            Glide.with(requireContext())
                    .load(selectedImageUri)
                    .into(añadirFoto);
            selectedImage = data.getData();
            // Hacer algo con la URI de la imagen seleccionada
        }
    }



}
