package es.ucm.fdi.readcycle.presentacion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import es.ucm.fdi.readcycle.R;
import es.ucm.fdi.readcycle.integracion.CallBacks;
import es.ucm.fdi.readcycle.negocio.BookInfo;
import es.ucm.fdi.readcycle.negocio.SAUser;
import es.ucm.fdi.readcycle.negocio.UserInfo;
import kotlin.jvm.internal.Intrinsics;

public class UsuarioBibliotecaFragment extends Fragment {


    private ImageButton addBtn;

    private TextView nombre;
    private String user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater"); //comprobamos que lo que llega no es ulo
        View view = inflater.inflate(R.layout.fragment_user_biblioteca, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            user = bundle.getString("propietario", "");

            nombre = view.findViewById(R.id.nombre_usuario);

            SAUser saUser = new SAUser();
            //Obtenemos el usuario
            saUser.infoUsuario(user, new CallBacks() {
                @Override
                public void onCallback(UserInfo u) {
                    nombre.setText( u.getNombre() + getResources().getString(R.string.bibliotecaDe));
                }
            });

            //Obtenemos la biblioteca
            BookAdapter b = new BookAdapter();
            saUser.getBiblioteca(user, new CallBacks() {
                @Override
                public void onCallbackBooks(ArrayList<BookInfo> bs) {
                    b.setBookData(bs, false);
                    RecyclerView recyclerView = view.findViewById(R.id.reclyclerViewBook);
                    recyclerView.setAdapter(b);
                }

            });
        }

        return view;
    }
}
