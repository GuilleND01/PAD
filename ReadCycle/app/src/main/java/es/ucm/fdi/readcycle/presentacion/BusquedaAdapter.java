package es.ucm.fdi.readcycle.presentacion;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import es.ucm.fdi.readcycle.R;
import es.ucm.fdi.readcycle.negocio.BookInfo;

public class BusquedaAdapter extends RecyclerView.Adapter<BusquedaAdapter.ViewHolder> {

    private ArrayList<BookInfo> bs;

    public void setBookData(ArrayList<BookInfo> bs){
        this.bs = new ArrayList<>(bs);
    }

    @NonNull
    @Override
    public  BusquedaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_busqueda, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BusquedaAdapter.ViewHolder holder, int position) {
        BookInfo b_current = bs.get(position);
        Log.d("MCAGO", b_current.getAuthor());
        holder.titulo.setText(b_current.getTitle());
        holder.autor.setText(b_current.getAuthor());
        holder.estado.setText(b_current.getState());
        Glide.with(holder.card)
                .load(b_current.getImg())
                .placeholder(R.drawable.libro)
                .into(holder.imagen);
    }

    @Override
    public int getItemCount() {
        return bs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView titulo;
        public TextView autor;
        public TextView estado;
        public CardView card;
        public ImageView imagen;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.b_titulo);
            autor = itemView.findViewById(R.id.b_autor);
            estado = itemView.findViewById(R.id.b_estado);
            imagen = itemView.findViewById(R.id.b_imagen);
            card = itemView.findViewById(R.id.b_card);
        }
    }
}
