package es.ucm.fdi.readcycle.presentacion;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import es.ucm.fdi.readcycle.R;
import es.ucm.fdi.readcycle.negocio.BookInfo;
import kotlin.jvm.internal.Intrinsics;

public class MiBibliotecaFragment extends Fragment {


    ImageButton addBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater"); //comprobamos que lo que llega no es ulo
        View view = inflater.inflate(R.layout.fragment_biblioteca, container, false);

        //PRUEBA PARA LAS CARDS

        ArrayList<String> g = new ArrayList<String>();
        g.add("Terror");g.add("Terror");g.add("Terror");g.add("Terror");g.add("Terror");g.add("Terror");
        g.add("Suspense");
        g.add("Comedia");

        //android.net.Uri selectedImage =null;
        android.net.Uri selectedImage = Uri.parse("https://books.google.com/books/content?id=e8DwuncELaoC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api");;

        BookInfo book1 = new BookInfo("Hola",g, "",0,"","", 1, selectedImage);
        BookInfo book2 = new BookInfo("Jonas",g, "Nick",0,"mu bonito el libro ooooooo ooo ooo oo  oo oooooo ooo o oo oo o oo o o oooooooo o oo oo oo oo oo o oo ooo","", 100, selectedImage);
        BookInfo book3 = new BookInfo("PRUEBA",g, "antonio",3,"","", 1, selectedImage);
        ArrayList<BookInfo> a = new ArrayList<>();
        a.add(book1);
        a.add(book2);
        a.add(book3);

        BookAdapter b = new BookAdapter();
        b.setBookData(a);
        RecyclerView recyclerView = view.findViewById(R.id.reclyclerViewBook);
        recyclerView.setAdapter(b);




        /*addBtn = view.findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {

                //  Creamos el nuevo libro
                AddLibroFragment nuevoLibroFragment = new AddLibroFragment();
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();

                // Indicamos el contenedor a reemplazar
                transaction.replace(R.id.frameLayout, nuevoLibroFragment);
                transaction.addToBackStack(null);

                //String title, String genre, String author, String state, String description, String img, Integer pages

                transaction.commit();
            }
        });*/

        return view;

    }
}
