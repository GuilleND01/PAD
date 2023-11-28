package es.ucm.fdi.readcycle.presentacion;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import es.ucm.fdi.readcycle.R;
import es.ucm.fdi.readcycle.negocio.BookInfo;
import kotlin.jvm.internal.Intrinsics;

public class VerLibroFragment extends Fragment {

    private static final String ARG_BOOK_INFO = "book_info";

    private TextView textTitulo, textGenero, textAutor, textPag, textEstado, textResumen, titResumen;
    private ImageView img;

    private FrameLayout layout;

    public static VerLibroFragment newInstance(BookInfo bookInfo) {
        VerLibroFragment fragment = new VerLibroFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_BOOK_INFO, bookInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        View v = inflater.inflate(R.layout.fragment_verlibro, container, false);

        Bundle args = getArguments();
        if (args != null) {
            BookInfo bookInfo = (BookInfo) args.getSerializable(ARG_BOOK_INFO);
            Log.d("JULIA", bookInfo.getTitle());

            textTitulo =  v.findViewById(R.id.tit_verlibro);
            textGenero = v.findViewById(R.id.libro_genero);
            textAutor  = v.findViewById(R.id.libro_autor);
            textPag  = v.findViewById(R.id.libro_numpaginas);
            textEstado  = v.findViewById(R.id.libro_estado);
            textResumen = v.findViewById(R.id.libro_resumen);

            img = v.findViewById(R.id.libro_imagen);
            layout = v.findViewById(R.id.libro_layout);

            //asiganamos los valores
            textTitulo.setText(bookInfo.getTitle());
            textAutor.setText(bookInfo.getAuthor());
            textPag.setText(bookInfo.getPages().toString());

            //Genero un string con los generos
            ArrayList<String> generos = bookInfo.getGenre();
            String g = "";
            for(int i = 0; i<generos.size();i++){
                g += generos.get(i);
                if(i != generos.size()-1){ //Si no es el útimo elmento añado la coma
                    g += ", ";
                }
            }
            textGenero.setText(g);

            //Asignamos el estado dependiendo del valor
            String[] booksArray = getResources().getStringArray(R.array.books_array);
            textEstado.setText(booksArray[bookInfo.getState()]);

            //Si no hay descripcion no muestro el campo
            if(bookInfo.getDescription().equals("")){
                titResumen = v.findViewById(R.id.libro_resumen_tit);
                titResumen.setVisibility(View.GONE);
                textResumen.setVisibility(View.GONE);
            }else{
                textResumen.setText(bookInfo.getDescription());
            }

            Glide.with(layout)
                    .load(bookInfo.getSelectedImage())
                    .placeholder(R.drawable.libro)
                    .into(img);

        }
        return v;
    }
}
