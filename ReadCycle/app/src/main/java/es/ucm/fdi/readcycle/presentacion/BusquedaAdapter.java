package es.ucm.fdi.readcycle.presentacion;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import es.ucm.fdi.readcycle.R;
import es.ucm.fdi.readcycle.negocio.BookInfo;
import es.ucm.fdi.readcycle.negocio.UserInfo;

public class BusquedaAdapter extends RecyclerView.Adapter<BusquedaAdapter.BusquedaViewHolder> {

    private ArrayList<BookInfo> books;

    private ArrayList<UserInfo> users;

    private Boolean typeBook;

    public void setBooksData(ArrayList<BookInfo> book){
        books = book; typeBook = true;
    }

    public void setUsersData(ArrayList<UserInfo> users){
        this.users = users;
        typeBook = false;
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
        if(typeBook) {
            BookInfo b_current = books.get(position);
            holder.titulo.setText(b_current.getTitle());
            holder.autor.setText(b_current.getAuthor());
            String[] booksArray = holder.card.getContext().getResources().getStringArray(R.array.books_array);
            holder.estado.setText(booksArray[b_current.getState()]);
            Glide.with(holder.card)
                    .load(b_current.getSelectedImage())
                    .placeholder(R.drawable.libro)
                    .into(holder.imagen);

            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BookInfo selectedBook = books.get(holder.getAdapterPosition());

                    // Crear una instancia del fragmento VerLibroFragment y pasar el objeto BookInfo
                    VerLibroFragment nuevoLibroFragment = VerLibroFragment.newInstance(selectedBook);

                    // FragmentTransaction para reemplazar el fragmento actual
                    FragmentManager fragmentManager = ((FragmentActivity) holder.itemView.getContext()).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, nuevoLibroFragment);
                    fragmentTransaction.addToBackStack(null); // Opcional, para agregar a la pila de retroceso
                    fragmentTransaction.commit();
                }
            });
        }
        else{
            UserInfo u_current = users.get(position);
            holder.titulo.setText(u_current.getNombre());
            holder.autor.setText(u_current.getZona());
            holder.estado.setText("");

            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(typeBook) {
            return books.size();
        }
        else{
            return users.size();
        }
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
