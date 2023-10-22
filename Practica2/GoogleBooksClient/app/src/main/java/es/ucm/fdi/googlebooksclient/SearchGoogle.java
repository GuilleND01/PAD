package es.ucm.fdi.googlebooksclient;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class SearchGoogle extends AsyncTask<String, Void, String> {

    private Context context;

    public SearchGoogle(Context context) {
        this.context = context;
    }


    @Override
    protected String doInBackground(String... strings) {
        Uri uri = Uri.parse(strings[0]);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        context.startActivity(intent);

        return null;

    }

}
