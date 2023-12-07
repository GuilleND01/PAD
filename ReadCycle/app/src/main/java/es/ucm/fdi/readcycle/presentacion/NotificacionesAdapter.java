package es.ucm.fdi.readcycle.presentacion;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

import es.ucm.fdi.readcycle.R;

public class NotificacionesAdapter extends RecyclerView.Adapter<NotificacionesAdapter.ViewHolder>  {
    private ArrayList<Map<String,String>> notif;
    public void setNotifs(ArrayList<Map<String,String>> data) {
        this.notif = data;
    }
    @NonNull
    @Override
    public NotificacionesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Cargar la vista desde el xml (con View)
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_notificacion, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificacionesAdapter.ViewHolder holder, int position) {
        Map<String,String> mCurrent = notif.get(position);

        holder.date.setText(mCurrent.get("fecha"));
        holder.message.setText(mCurrent.get("mensaje"));

        NotificacionesAdapter.ViewHolder vha = (NotificacionesAdapter.ViewHolder) holder;
        vha.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsuarioBibliotecaFragment usuarioBibliotecaFragment = new UsuarioBibliotecaFragment();

                // Crear un Bundle para pasar el correo
                Bundle bundle = new Bundle();
                bundle.putString("propietario", mCurrent.get("email"));

                // Asignar el Bundle al fragmento
                usuarioBibliotecaFragment.setArguments(bundle);

                // Iniciar la transacción del fragmento
                FragmentManager fragmentManager = ((FragmentActivity) holder.itemView.getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, usuarioBibliotecaFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }


    @Override
    public int getItemCount() {
        return notif.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // public TextView wordItemView;
        private View lay;
        public TextView date;
        public TextView message;
        private CardView cardView;

        public ViewHolder(View view) {
            super(view);
            this.lay = view;

            date = view.findViewById(R.id.fecha_n);
            message = view.findViewById(R.id.mensaje_notif);
            cardView = view.findViewById(R.id.notif_card);
        }

    }

}
