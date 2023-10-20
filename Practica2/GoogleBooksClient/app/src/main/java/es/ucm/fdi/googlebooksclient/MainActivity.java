package es.ucm.fdi.googlebooksclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;


import android.os.Bundle;
import android.view.View;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private BookLoaderCallbacks bookLoaderCallbacks;
    private static final int BOOK_LOADER_ID  = 53785;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bookLoaderCallbacks = new BookLoaderCallbacks(this, "","");
        LoaderManager loaderManager = LoaderManager.getInstance(this);
        if(loaderManager.getLoader(BOOK_LOADER_ID) != null){
            loaderManager.initLoader(BOOK_LOADER_ID, null, bookLoaderCallbacks);
        }
    }

    public void searchBooks(View view){

    }


}