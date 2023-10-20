package es.ucm.fdi.googlebooksclient;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class BookLoader extends AsyncTaskLoader<String> {
    final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?";
    final String QUERY_PARAM = "q";
    final String MAX_RESULTS = "maxResults";
    final String PRINT_TYPE = "printType";
    String queryString;
    String printType;
    public BookLoader(@NonNull Context context, String queryString, String printType) {
        super(context);
        this.printType = printType;
        this.queryString = queryString;
    }

    @Nullable
    @Override
    public String loadInBackground() {
        Uri builtURI = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, queryString)
                .appendQueryParameter(MAX_RESULTS, "10")
                .appendQueryParameter(PRINT_TYPE, printType)
                .build();
        try {
            URL requestURL = new URL(builtURI.toString());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
