package es.ucm.fdi.googlebooksclient;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.util.List;

public class BookLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<BookInfo>> {
    private Context ctx;
    private String queryS;
    private String printType;
    public static final String EXTRA_QUERY = "queryString";
    public static final String EXTRA_PRINT_TYPE = "printType";
    @NonNull
    @Override
    public Loader<List<BookInfo>> onCreateLoader(int id, @Nullable Bundle args) {
        return new BookLoader(ctx , queryS, printType);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<BookInfo>> loader, List<BookInfo> data) {

    }


    @Override
    public void onLoaderReset(@NonNull Loader<List<BookInfo>> loader) {

    }

    public BookLoaderCallbacks(Context ctx, String queryString, String printType){
        this.ctx = ctx;
        this.printType = printType;
        this.queryS = queryString;
    }


}
