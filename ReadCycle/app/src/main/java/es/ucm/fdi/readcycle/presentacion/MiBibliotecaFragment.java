package es.ucm.fdi.readcycle.presentacion;

import android.annotation.SuppressLint;
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

        addBtn = view.findViewById(R.id.addBtn);
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

                transaction.commit();
            }
        });

        //TODO QUITAR ESTO, esta solo para ver que va el recyclerView
        BookInfo b1 = new BookInfo("HOLA", "");
        BookInfo b2 = new BookInfo("DOS", "");
        BookInfo b3 = new BookInfo("POP", "");
        ArrayList<BookInfo> a = new ArrayList<>();
        a.add(b1);
        a.add(b2);
        a.add(b3);

        BookAdapter ba = new BookAdapter();
        ba.setBookData(a);
        RecyclerView recyclerView = view.findViewById(R.id.reclyclerView);
        recyclerView.setAdapter(ba);





        return view;

    }
}
