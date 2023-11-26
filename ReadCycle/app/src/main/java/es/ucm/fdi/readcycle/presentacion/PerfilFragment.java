package es.ucm.fdi.readcycle.presentacion;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import es.ucm.fdi.readcycle.R;
import es.ucm.fdi.readcycle.integracion.BuscarCallBacks;
import es.ucm.fdi.readcycle.integracion.DAOUser;
import es.ucm.fdi.readcycle.integracion.UsuarioCallBacks;
import es.ucm.fdi.readcycle.negocio.BookInfo;
import es.ucm.fdi.readcycle.negocio.SAUser;
import es.ucm.fdi.readcycle.negocio.UserInfo;
import kotlin.jvm.internal.Intrinsics;


public class PerfilFragment extends Fragment {

    private Button logout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");


        View v = inflater.inflate(R.layout.fragment_perfil, container, false);

        logout = v.findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        SAUser saUser = new SAUser();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        saUser.infoUsuario(currentUser.getEmail().toString(), new UsuarioCallBacks() {
            @Override
            public void onCallback(UserInfo u) {

                Log.d("CLAU", u.getNombre());
            }

        });

        return v;

    }
}
