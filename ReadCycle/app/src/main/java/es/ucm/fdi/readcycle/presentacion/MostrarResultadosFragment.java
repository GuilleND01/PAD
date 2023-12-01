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
import android.widget.TextView;

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

        ArrayList<BookInfo> bookInfoList = (ArrayList<BookInfo>) getArguments().getSerializable("bookInfoList");

        TextView res = v.findViewById(R.id.n_resultados);
        if(bookInfoList != null) {
            String s = String.valueOf(bookInfoList.size());
            res.setText(s);
        }
        else{
            res.setText("0");
        }

        BusquedaAdapter adapter = new BusquedaAdapter();
        adapter.setBooksData(bookInfoList);

        RecyclerView recyclerView = v.findViewById(R.id.r_mostrar_busquedas);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        return v;
    }
}