package es.ucm.fdi.readcycle.presentacion;

import android.util.Log;
import android.util.TypedValue;
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
import java.util.List;

import es.ucm.fdi.readcycle.R;
import es.ucm.fdi.readcycle.negocio.BookInfo;

public class BookAdapter extends RecyclerView.Adapter {

    private ArrayList<BookInfo> booksData;
    private static final int NORMAL_BOOK = 1;
    private static final int ADD_BOOK = 0;

    public void setBookData(List<BookInfo> booksData){
        this.booksData = ( ArrayList<BookInfo>) booksData;
    }

    @Override
    public int getItemViewType(int position) {
        // Devuelve el tipo de vista según la posición

        return (position == 0) ? ADD_BOOK : NORMAL_BOOK;
    }

    public static class ViewHolderBook extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView img;
        private CardView cardView;

        public ViewHolderBook(View view) {
            super(view);

            title = view.findViewById(R.id.titleBook);
            img = view.findViewById(R.id.imgBook);
            cardView =  view.findViewById(R.id.cardView);

        }
    }

    public static class ViewHolderAdd extends RecyclerView.ViewHolder{
        private CardView addCardView;
        public ViewHolderAdd(View view){
            super(view);
            addCardView = view.findViewById(R.id.cardView2);

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v;
        switch (viewType){
            case ADD_BOOK:
                v = inflater.inflate(R.layout.card_addbook,parent,false);
                return new ViewHolderAdd(v);
            case NORMAL_BOOK:
                v = inflater.inflate(R.layout.card_book, parent, false);
                return new ViewHolderBook(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        //tiene mas sentido hacerlo con el switch ADD_BOOK / NORMAL_BOOK pero por alguna razón
        //que no logro entender no quiere hacer caso al switch :)
        if(position == 0){
            ViewHolderAdd vha = (ViewHolderAdd) holder;
            vha.addCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO
                    Log.d("HOLA", "implementar add book");

                    FragmentManager fragmentManager = ((FragmentActivity) holder.itemView.getContext()).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    // Reemplaza o agrega tu nuevo fragmento
                    AddLibroFragment nuevoLibroFragment = new AddLibroFragment();
                    fragmentTransaction.replace(R.id.frameLayout, nuevoLibroFragment);
                    fragmentTransaction.commit();
                }
            });

        }
        else{
            ViewHolderBook vhb = (ViewHolderBook) holder;

            BookInfo book = booksData.get(position);
            vhb.title.setText(book.getTitle());
            vhb.title.setAutoSizeTextTypeUniformWithConfiguration(8, 24, 2, TypedValue.COMPLEX_UNIT_SP);

            Glide.with(vhb.cardView)
                    .load(book.getSelectedImage())
                    .placeholder(R.drawable.libro)
                    .into(vhb.img);


            vhb.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    // Obtener el libro específico según la posición en el RecyclerView
                    BookInfo selectedBook = booksData.get(holder.getAdapterPosition());

                    // Crear una instancia del fragmento VerLibroFragment y pasar el objeto BookInfo
                    VerLibroFragment nuevoLibroFragment = VerLibroFragment.newInstance(selectedBook);

                    // FragmentTransaction para reemplazar el fragmento actual
                    FragmentManager fragmentManager = ((FragmentActivity) holder.itemView.getContext()).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, nuevoLibroFragment);
                    fragmentTransaction.addToBackStack(null); // Opcional, para agregar a la pila de retroceso
                    fragmentTransaction.commit();
                    //TODO
                    /*
                    Log.d("HOLA", "implementar add book");

                    FragmentManager fragmentManager = ((FragmentActivity) holder.itemView.getContext()).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    // Reemplaza o agrega tu nuevo fragmento
                    VerLibroFragment nuevoLibroFragment = new VerLibroFragment();
                    fragmentTransaction.replace(R.id.frameLayout, nuevoLibroFragment);
                    fragmentTransaction.commit();*/
                }
            });


        }

    }


    @Override
    public int getItemCount() {
        return booksData.size();
    }





}
