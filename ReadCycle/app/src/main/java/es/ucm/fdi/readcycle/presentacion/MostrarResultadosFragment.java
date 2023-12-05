package es.ucm.fdi.readcycle.presentacion;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import es.ucm.fdi.readcycle.integracion.CallBacks;
import es.ucm.fdi.readcycle.negocio.BookInfo;
import es.ucm.fdi.readcycle.negocio.SABook;
import es.ucm.fdi.readcycle.negocio.SAUser;
import es.ucm.fdi.readcycle.negocio.UserInfo;
import kotlin.jvm.internal.Intrinsics;

public class MostrarResultadosFragment extends Fragment {

    private RadioGroup opt_busqueda;
    private SearchView s;

    private TextView res;

    private BusquedaAdapter adapter;

    private RadioGroup opt_users;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        View v = inflater.inflate(R.layout.fragment_mostrar_resultados, container, false);

        ArrayList<BookInfo> bookInfoList = (ArrayList<BookInfo>) getArguments().getSerializable("bookInfoList");
        ArrayList<UserInfo> userInfoList = (ArrayList<UserInfo>) getArguments().getSerializable("userInfoList");

        res = v.findViewById(R.id.n_resultados);
        adapter = new BusquedaAdapter();
        if(bookInfoList != null) {
            adapter.setBooksData(bookInfoList);
            String s = String.valueOf(bookInfoList.size());
            res.setText(s);
        }
        else if(userInfoList != null){
            adapter.setUsersData(userInfoList);
            String s = String.valueOf(userInfoList.size());
            res.setText(s);
        }
        else{
            res.setText("0");
        }

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
        opt_users = view.findViewById(R.id.bus_user);

        s.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SABook service = new SABook();
                SAUser saUser = new SAUser();
                BookInfo b = new BookInfo();
                UserInfo u = new UserInfo();
                if(opt_busqueda.getCheckedRadioButtonId() == R.id.radioButtonTitulo2){
                    b.setTitle(query);
                    b.setAuthor(null);
                }
                else if(opt_busqueda.getCheckedRadioButtonId() == R.id.radioButtonAutor2){
                    b.setTitle(null);
                    b.setAuthor(query);
                }
                else if(opt_users.getCheckedRadioButtonId() == R.id.opt_users2){
                    u.setNombre(query);
                }
                else{
                    Toast.makeText(getActivity(), R.string.aviso_no_opcion, Toast.LENGTH_SHORT).show();
                }
                if(b.getAuthor() != null || b.getTitle() != null){
                    service.bucarLibros(b, new CallBacks() {
                        @Override
                        public void onCallbackBooks(ArrayList<BookInfo> bs) {
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
                else if(u != null){
                    saUser.buscarUsuarios(u, new CallBacks() {
                        @Override
                        public void onCallbackUsers(ArrayList<UserInfo> us) {
                            adapter.setUsersData(us);
                            if(us != null){
                                String s = String.valueOf(us.size());
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