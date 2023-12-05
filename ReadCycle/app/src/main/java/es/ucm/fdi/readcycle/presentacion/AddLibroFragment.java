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

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;

import es.ucm.fdi.readcycle.R;
import es.ucm.fdi.readcycle.integracion.CallBacks;
import es.ucm.fdi.readcycle.negocio.BookInfo;
import es.ucm.fdi.readcycle.negocio.SABook;
import kotlin.jvm.internal.Intrinsics;

public class AddLibroFragment extends Fragment {

    private EditText resumen, titulo, autor, num_paginas;
    private Spinner estado, genero;

    private android.net.Uri selectedImage = null;

    private Button añadirBtn, añadirGenero, resetGeneros;

    private ImageButton añadirFoto;
    private TextView lista_generos_view;

    private ArrayList<Integer> lista_generos = new ArrayList<Integer>();

    private ArrayList<String> generoText = new ArrayList<String>();
    private int estado_sel, genero_sel;
    private View view;

    private static final int PICK_IMAGE_REQUEST = 1;




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                MiBibliotecaFragment bibliotecaFragment = new MiBibliotecaFragment();
                fragmentTransaction.replace(R.id.frameLayout, bibliotecaFragment);
                fragmentTransaction.commit();
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(this.getViewLifecycleOwner(), callback);


        view = inflater.inflate(R.layout.fragment_addlibro, container, false);
        añadirBtn = view.findViewById(R.id.buttonAñadir);
        añadirGenero = view.findViewById(R.id.btn_anadir_genero);
        resetGeneros = view.findViewById(R.id.btn_reset_genero);

        añadirGenero.setBackgroundColor(getResources().getColor(R.color.botonAdd));
        añadirBtn.setBackgroundColor(getResources().getColor(R.color.botonAdd));
        resetGeneros.setBackgroundColor(getResources().getColor(R.color.botonAdd));

        lista_generos_view = view.findViewById(R.id.generos_anadidos);

        añadirFoto = view.findViewById(R.id.addPhotoBtn);
        estado = (Spinner) view.findViewById(R.id.formestado);
        genero = (Spinner) view.findViewById(R.id.formestado2);

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

        /*GENERO NO BiNARIE*/
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(),
                R.array.genero_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        genero.setAdapter(adapter2);

        genero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Obtener el elemento seleccionado
                genero_sel = (int) position;
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
                generoText.clear();
                lista_generos_view.setText(R.string.nogeneros);
            }
        });
        añadirGenero.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String[] generoArray = getResources().getStringArray(R.array.genero_array);

                if (lista_generos_view.getError() != null){
                    lista_generos_view.setError(null);
                }
                if (!generoText.contains(generoArray[genero_sel])){
                    lista_generos.add(genero_sel);
                    generoText.add(generoArray[genero_sel]);
                }

                lista_generos_view.setText(R.string.generos_añadidos + generoText.toString());

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
                        add(autor); add(num_paginas);}};

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
                    lista_generos_view.setText(R.string.minimo_un_genero);
                    lista_generos_view.setError("");
                    form_valido = false;
                }

                if (selectedImage == null) {
                    form_valido = false;
                }
                if (!form_valido) {
                    if (selectedImage == null){
                        añadirFoto.setBackgroundResource(R.drawable.shape_bad);
                    }
                    Toast.makeText(view.getContext(), R.string.MSG_FORM_INCOMPLETO, Toast.LENGTH_LONG).show();

                } else {
                    BookInfo nuevo_libro = new BookInfo(titulo.getText().toString(), lista_generos,
                            autor.getText().toString(), estado_sel, resumen.getText().toString(),
                            Integer.parseInt(num_paginas.getText().toString()), selectedImage);

                    SABook saBookInfo = new SABook();
                    int res_guardar = saBookInfo.guardarLibro(nuevo_libro, new CallBacks() {
                        @Override
                        public void onCallbackExito(Boolean exito) {

                        }
                    });
                    
                    if (res_guardar == 1) {

                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        MiBibliotecaFragment bibliotecaFragment = new MiBibliotecaFragment();
                        fragmentTransaction.replace(R.id.frameLayout, bibliotecaFragment);
                        fragmentTransaction.commit();

                        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                            @Override
                            public void onBackStackChanged() {
                                // Muestra el Toast en el contexto del nuevo fragmento
                                Toast.makeText(bibliotecaFragment.getActivity(), R.string.MSG_EXITO, Toast.LENGTH_LONG).show();

                                // Remueve el listener para que no se ejecute nuevamente innecesariamente
                                fragmentManager.removeOnBackStackChangedListener(this);
                            }
                        });

                    } else Toast.makeText(view.getContext(), R.string.MSG_ERROR_GENERAL, Toast.LENGTH_LONG).show();
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
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            Glide.with(requireContext())
                    .load(selectedImageUri)
                    .into(añadirFoto);
            selectedImage = data.getData();
        }
    }

}
