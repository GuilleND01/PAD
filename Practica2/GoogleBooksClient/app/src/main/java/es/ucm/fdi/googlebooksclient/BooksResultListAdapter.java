package es.ucm.fdi.googlebooksclient;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BooksResultListAdapter extends RecyclerView.Adapter<BooksResultListAdapter.ViewHolder>  {
    private ArrayList<BookInfo> mBooksData;
    public void setBooksData(List<BookInfo> data) {
        this.mBooksData = (ArrayList<BookInfo>) data;
    }
    @NonNull
    @Override
    public BooksResultListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Cargar la vista desde el xml (con View)
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksResultListAdapter.ViewHolder holder, int position) {
        BookInfo mCurrent = mBooksData.get(position);
        holder.tit.setText(mCurrent.getTitle());
        holder.aut.setText(mCurrent.getAuthors());
        holder.link.setText(mCurrent.getinfoLink().toString());
        holder.type.setText(mCurrent.getPrintType());
        int a = R.string.noAut;


    }

    @Override
    public int getItemCount() {
        return mBooksData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // public TextView wordItemView;
        private View lay;
        public TextView tit;
        public TextView aut;
        public TextView link;

        public TextView type;

        public ViewHolder(View view) {
            super(view);
            this.lay = view;

            tit = view.findViewById(R.id.cardT);
            aut = view.findViewById(R.id.cardA);
            link = view.findViewById(R.id.cardL);
            type = view.findViewById(R.id.type);

        }
    }


}
