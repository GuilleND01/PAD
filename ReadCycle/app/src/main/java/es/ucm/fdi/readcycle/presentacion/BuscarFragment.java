package es.ucm.fdi.readcycle.presentacion;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import es.ucm.fdi.readcycle.R;
import es.ucm.fdi.readcycle.integracion.BuscarCallBacks;
import es.ucm.fdi.readcycle.negocio.BookInfo;
import es.ucm.fdi.readcycle.negocio.SABook;
import kotlin.jvm.internal.Intrinsics;

public class BuscarFragment extends Fragment {

    private RadioGroup opt_busqueda;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        return inflater.inflate(R.layout.fragment_buscar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        opt_busqueda = view.findViewById(R.id.opt_busqueda); // Asegúrate de tener un RadioGroup con este id en tu layout
        searchView = view.findViewById(R.id.search); // Asegúrate de tener un SearchView con este id en tu layout

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SABook service = new SABook();
                BookInfo b = new BookInfo();
                if(opt_busqueda.getCheckedRadioButtonId() == R.id.radioButtonTitulo){
                    b.setTitle(query);
                    b.setAuthor(null);
                    service.bucarLibros(b, new BuscarCallBacks() {
                        @Override
                        public void onCallback(ArrayList<BookInfo> bs) {
                            Log.d("BUS", bs.toString());
                        }
                    });
                }
                else if(opt_busqueda.getCheckedRadioButtonId() == R.id.radioButtonAutor){
                    b.setTitle(null);
                    b.setAuthor(query);
                    service.bucarLibros(b, new BuscarCallBacks() {
                        @Override
                        public void onCallback(ArrayList<BookInfo> bs) {
                            Log.d("BUS", bs.toString());
                        }
                    });
                }
                else{
                    Toast.makeText(getActivity(), R.string.aviso_no_opcion, Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Aquí puedes manejar la consulta de búsqueda cuando el texto de la búsqueda cambia
                return false;
            }
        });
    }

}
