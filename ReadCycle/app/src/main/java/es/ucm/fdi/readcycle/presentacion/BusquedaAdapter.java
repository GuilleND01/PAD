package es.ucm.fdi.readcycle.presentacion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import es.ucm.fdi.readcycle.negocio.BookInfo;

public class BusquedaAdapter extends RecyclerView.Adapter {

    private ArrayList<BookInfo> bs;

    public void setBookData(ArrayList<BookInfo> bs){
        this.bs = bs;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
