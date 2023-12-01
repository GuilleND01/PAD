package es.ucm.fdi.readcycle.presentacion;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import es.ucm.fdi.readcycle.R;
import es.ucm.fdi.readcycle.integracion.BuscarCallBacks;
import es.ucm.fdi.readcycle.negocio.BookInfo;
import es.ucm.fdi.readcycle.negocio.SABook;
import kotlin.jvm.internal.Intrinsics;

public class MostrarResultadosFragment extends Fragment {

    private RadioGroup opt_busqueda;
    private SearchView s;

    private TextView res;

    private BusquedaAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        View v = inflater.inflate(R.layout.fragment_mostrar_resultados, container, false);

        ArrayList<BookInfo> bookInfoList = (ArrayList<BookInfo>) getArguments().getSerializable("bookInfoList");

        res = v.findViewById(R.id.n_resultados);
        if(bookInfoList != null) {
            String s = String.valueOf(bookInfoList.size());
            res.setText(s);
        }
        else{
            res.setText("0");
        }

        adapter = new BusquedaAdapter();
        adapter.setBooksData(bookInfoList);

        RecyclerView recyclerView = v.findViewById(R.id.r_mostrar_busquedas);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        opt_busqueda = view.findViewById(R.id.opt_busqueda_2);
        s = view.findViewById(R.id.n_barra_busqueda);

        s.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SABook service = new SABook();
                BookInfo b = new BookInfo();
                if(opt_busqueda.getCheckedRadioButtonId() == R.id.radioButtonTitulo2){
                    b.setTitle(query);
                    b.setAuthor(null);
                }
                else if(opt_busqueda.getCheckedRadioButtonId() == R.id.radioButtonAutor2){
                    b.setTitle(null);
                    b.setAuthor(query);
                }
                else{
                    Toast.makeText(getActivity(), R.string.aviso_no_opcion, Toast.LENGTH_SHORT).show();
                }
                if(b.getAuthor() != null || b.getTitle() != null){
                    service.bucarLibros(b, new BuscarCallBacks() {
                        @Override
                        public void onCallback(ArrayList<BookInfo> bs) {
                            adapter.setBooksData(bs);
                            if(bs != null) {
                                String s = String.valueOf(bs.size());
                                res.setText(s);
                            }
                            else{
                                res.setText("0");
                            }
                            adapter.notifyData();
                        }
                    });
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
}