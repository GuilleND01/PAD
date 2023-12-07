package es.ucm.fdi.readcycle.presentacion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import es.ucm.fdi.readcycle.R;
import es.ucm.fdi.readcycle.negocio.BookInfo;
import kotlin.jvm.internal.Intrinsics;

import android.widget.ImageButton;
import android.widget.TextView;

public class NotificacionesFragment extends Fragment {
    private TextView no_resultados;
    private ImageButton btnVolver;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        View view = inflater.inflate(R.layout.fragment_notificaciones, container, false);

        no_resultados = view.findViewById(R.id.no_notificaciones);
        btnVolver = view.findViewById(R.id.btn_back_profile);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        NotificacionesAdapter na = new NotificacionesAdapter();
        ArrayList<Map<String,String>> notifs = (ArrayList<Map<String, String>>) getArguments().getSerializable("notifHist");

        if(notifs.size() == 0)
            no_resultados.setText(R.string.no_notif);
        else
            Collections.reverse(notifs);

        na.setNotifs(notifs);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewNotif);
        recyclerView.setAdapter(na);
        return view;
    }
}
