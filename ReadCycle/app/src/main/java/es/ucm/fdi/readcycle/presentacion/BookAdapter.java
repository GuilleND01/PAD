package es.ucm.fdi.readcycle.presentacion;

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
import java.util.List;

import es.ucm.fdi.readcycle.R;
import es.ucm.fdi.readcycle.negocio.BookInfo;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private ArrayList<BookInfo> booksData;

    public void setBookData(List<BookInfo> booksData){
        this.booksData = ( ArrayList<BookInfo>) booksData;
    }

    @NonNull
    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_book, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.ViewHolder holder, int position) {

        BookInfo book = booksData.get(position);
        holder.title.setText(book.getTitle());
        Glide.with(holder.cardView)
                .load(book.getImg())
                .placeholder(R.drawable.libro)
                .into(holder.img);

    }

    @Override
    public int getItemCount() {
        return booksData.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView img;
        private CardView cardView;

        public ViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.titleBook);
            img = view.findViewById(R.id.imgBook);
            cardView =  view.findViewById(R.id.cardView);

        }
    }

}
