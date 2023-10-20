package es.ucm.fdi.googlebooksclient;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BookLoader extends AsyncTaskLoader<String> {
    final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?";
    final String QUERY_PARAM = "q";
    final String MAX_RESULTS = "maxResults";
    final String PRINT_TYPE = "printType";
    String queryString;
    String printType;

    URL requestURL;
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
            this.requestURL = new URL(builtURI.toString());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    protected void onStartLoading(){
        forceLoad();
    }

    public String getBookInfoJson(String queryString, String printType) throws IOException {
        HttpURLConnection conn = null;
        InputStream is = null;

        try{
            conn = (HttpURLConnection) this.requestURL.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int response = conn.getResponseCode();
            is = conn.getInputStream();
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }
            if (builder.length() == 0) {
                return null;
            }
            return builder.toString();
        }catch(Exception e){
            //TODO
        }
        finally {
            conn.disconnect();
            if (is != null) {
                is.close();
            }
        }
        return null;
    }
}
