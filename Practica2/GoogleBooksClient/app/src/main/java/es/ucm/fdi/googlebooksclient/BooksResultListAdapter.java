package es.ucm.fdi.googlebooksclient;

import android.view.View;
import android.view.ViewGroup;

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
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BooksResultListAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // public TextView wordItemView;
        private View lay;
        public ViewHolder(View view) {
            super(view);
            // TODO cambiar por nuestro layout
            this.lay = view;
            // wordItemView = view.findViewById(R.id.word);
        }
    }
}
