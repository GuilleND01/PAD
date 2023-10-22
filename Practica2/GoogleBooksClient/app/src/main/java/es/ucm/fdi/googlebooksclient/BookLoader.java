package es.ucm.fdi.googlebooksclient;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<BookInfo>> {
    final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?";
    final String QUERY_PARAM = "q";
    final String MAX_RESULTS = "maxResults";
    final String PRINT_TYPE = "printType";
    final String KEY_VALUE = "AIzaSyCB7ezrFGV8cCcMbq1oauWKttTYrKsbBJo";
    final String KEY = "key";

    final String MAX_RES = "40";
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
    public List<BookInfo> loadInBackground() {
        Uri builtURI = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, queryString)
                .appendQueryParameter(MAX_RESULTS, MAX_RES)
                .appendQueryParameter(PRINT_TYPE, printType)
                .appendQueryParameter(KEY, KEY_VALUE)
                .build();
        try {
            this.requestURL = new URL(builtURI.toString());
            String json = getBookInfoJson();
            return BookInfo.fromJsonResponse(json);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onStartLoading(){
        forceLoad();
    }

    public String getBookInfoJson() throws IOException {
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
