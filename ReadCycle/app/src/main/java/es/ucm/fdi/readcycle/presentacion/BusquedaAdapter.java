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

public class BusquedaAdapter extends RecyclerView.Adapter<BusquedaAdapter.BusquedaViewHolder> {

    private ArrayList<BookInfo> books;

    public void setBooksData(ArrayList<BookInfo> book){
        books = book;
    }

    public void notifyData(){
        notifyDataSetChanged();
    };
    @NonNull
    @Override
    public BusquedaAdapter.BusquedaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_busqueda, parent, false);
        return new BusquedaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BusquedaAdapter.BusquedaViewHolder holder, int position) {
        BookInfo b_current = books.get(position);
        Log.d("MCAGO", b_current.getAuthor());
        holder.titulo.setText(b_current.getTitle());
        holder.autor.setText(b_current.getAuthor());
        String[] booksArray = holder.card.getContext().getResources().getStringArray(R.array.books_array);
        holder.estado.setText(booksArray[b_current.getState()]);
        Glide.with(holder.card)
                .load(b_current.getSelectedImage())
                .placeholder(R.drawable.libro)
                .into(holder.imagen);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public static class BusquedaViewHolder extends RecyclerView.ViewHolder{

        public ImageView imagen;
        public TextView titulo;
        public TextView autor;
        public TextView estado;
        public CardView card;
        public BusquedaViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.b_titulo);
            autor = itemView.findViewById(R.id.b_autor);
            estado = itemView.findViewById(R.id.b_estado);
            imagen = itemView.findViewById(R.id.b_imagen);
            card = itemView.findViewById(R.id.b_card);
        }
    }
}
