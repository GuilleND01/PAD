package es.ucm.fdi.readcycle.presentacion;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import es.ucm.fdi.readcycle.R;
import es.ucm.fdi.readcycle.negocio.BookInfo;
import kotlin.jvm.internal.Intrinsics;

public class MostrarResultadosFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        View v = inflater.inflate(R.layout.fragment_mostrar_resultados, container, false);

        BusquedaAdapter adapter = new BusquedaAdapter();
        ArrayList<Integer> g = new ArrayList<Integer>();
        g.add(0);
        android.net.Uri selectedImage =null;
        BookInfo book1 = new BookInfo("Hola",g, "Manolo",1,"", 1, selectedImage);
        BookInfo book2 = new BookInfo("ADIOS",g, "Esther",1,"", 1, selectedImage);
        BookInfo book3 = new BookInfo("DCNDJKC",g, "Gerardo",0,"", 1, selectedImage);
        ArrayList<BookInfo> a = new ArrayList<>();
        a.add(book1);
        a.add(book2);
        a.add(book3);
        adapter.setBooksData(a);

        RecyclerView recyclerView = v.findViewById(R.id.r_mostrar_busquedas);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        return v;
    }
}