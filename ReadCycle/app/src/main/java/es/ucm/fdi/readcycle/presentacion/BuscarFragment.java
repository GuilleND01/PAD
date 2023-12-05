package es.ucm.fdi.readcycle.presentacion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import es.ucm.fdi.readcycle.R;
import es.ucm.fdi.readcycle.integracion.CallBacks;
import es.ucm.fdi.readcycle.negocio.BookInfo;
import es.ucm.fdi.readcycle.negocio.SABook;
import es.ucm.fdi.readcycle.negocio.SAUser;
import es.ucm.fdi.readcycle.negocio.UserInfo;
import kotlin.jvm.internal.Intrinsics;

public class BuscarFragment extends Fragment {

    private RadioGroup opt_busqueda;
    private SearchView searchView;

    private RadioGroup busqueda_users;

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
        busqueda_users = view.findViewById(R.id.busqueda_users);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SABook service = new SABook();
                SAUser saUser = new SAUser();
                BookInfo b = new BookInfo();
                UserInfo u = new UserInfo();
                if(opt_busqueda.getCheckedRadioButtonId() == R.id.radioButtonTitulo){
                    b.setTitle(query);
                    b.setAuthor(null);
                }
                else if(opt_busqueda.getCheckedRadioButtonId() == R.id.radioButtonAutor){
                    b.setTitle(null);
                    b.setAuthor(query);
                }
                else if(busqueda_users.getCheckedRadioButtonId() == R.id.opt_users){
                    u.setNombre(query);
                }
                else{
                    Toast.makeText(getActivity(), R.string.aviso_no_opcion, Toast.LENGTH_SHORT).show();
                }
                if(b.getAuthor() != null || b.getTitle() != null){
                    service.bucarLibros(b, new CallBacks() {
                        @Override
                        public void onCallbackBooks(ArrayList<BookInfo> bs) {
                            MostrarResultadosFragment mostrarResultadosFragment = new MostrarResultadosFragment();
                            // Crear un Bundle para pasar datos al nuevo fragmento
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("bookInfoList", bs);

                            // Asignar el Bundle al fragmento
                            mostrarResultadosFragment.setArguments(bundle);
                            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.frameLayout, mostrarResultadosFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                    });
                }
                else if(u.getNombre() != null){
                    saUser.buscarUsuarios(u, new CallBacks() {
                        @Override
                        public void onCallbackUsers(ArrayList<UserInfo> us) {
                            MostrarResultadosFragment mostrarResultadosFragment = new MostrarResultadosFragment();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("userInfoList", us);

                            mostrarResultadosFragment.setArguments(bundle);
                            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.frameLayout, mostrarResultadosFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                    });
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
