package es.ucm.fdi.googlebooksclient;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

public class BookLoaderCallbacks implements LoaderManager.LoaderCallbacks<String> {
    private Context ctx;
    private String queryS;
    private String printType;

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new BookLoader(ctx , queryS, printType);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    public BookLoaderCallbacks(Context ctx, String queryString, String printType){
        this.ctx = ctx;
        this.printType = printType;
        this.queryS = queryString;

    }


}
