package es.ucm.fdi.googlebooksclient;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BookLoaderCallbacks bookLoaderCallbacks;
    private BooksResultListAdapter booksResultListAdapter;
    private String queryString = "";
    private String printType = "";
    private RadioGroup r1;
    private RadioGroup r2;
    private RecyclerView recyclerView;
    private TextView resultados;


    private static final int BOOK_LOADER_ID  = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        List<BookInfo> l = new ArrayList<>();
        booksResultListAdapter = new BooksResultListAdapter(); //se debe tener mWordList

        booksResultListAdapter.setBooksData(l);


        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(booksResultListAdapter);

        r1 = findViewById(R.id.r1);
        r2 = findViewById(R.id.r2);

        r1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                ((RadioGroup) findViewById(R.id.r2)).clearCheck();
                if (radioGroup.getCheckedRadioButtonId() != R.id.libro) {
                    findViewById(R.id.autor).setEnabled(false);
                    findViewById(R.id.titulo).setEnabled(true);
                }
                else {
                    findViewById(R.id.autor).setEnabled(true);
                    findViewById(R.id.titulo).setEnabled(true);
                }
            }
        });

        resultados = findViewById(R.id.resId);

        SearchView searchView = findViewById(R.id.search);
        searchBooks(searchView);

    }

    public void searchBooks(View view) {
        ((SearchView) view).setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                queryString ="";

                if(r2.getCheckedRadioButtonId() == R.id.autor) {
                    queryString = "inauthor:";
                }
                else if(r2.getCheckedRadioButtonId() == R.id.titulo) {
                    queryString = "intitle:";
                }
                else {
                    Toast.makeText(MainActivity.this, "Seleccione una opción", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "No hay argumentos");
                }
                if (queryString.length() > 0) {
                    queryString += s;

                    if (r1.getCheckedRadioButtonId() == R.id.libro) {
                        printType = "books";
                    }
                    else if (r1.getCheckedRadioButtonId() == R.id.revista) {
                        printType = "magazines";
                    }
                    else {
                        printType = "all";
                    }
                    resultados.setText(getString(R.string.carg));

                    queryString.replace(" ", "-");
                    bookLoaderCallbacks = new BookLoaderCallbacks(MainActivity.this, queryString, printType);
                    LoaderManager loaderManager = LoaderManager.getInstance(MainActivity.this);
                    if(loaderManager.getLoader(BOOK_LOADER_ID) == null){
                        loaderManager.initLoader(BOOK_LOADER_ID, null, bookLoaderCallbacks);
                    }

                    Bundle queryBundle = new Bundle();
                    queryBundle.putString(BookLoaderCallbacks.EXTRA_QUERY, queryString);
                    queryBundle.putString(BookLoaderCallbacks.EXTRA_PRINT_TYPE, printType);
                    LoaderManager.getInstance(MainActivity.this)
                            .restartLoader(BOOK_LOADER_ID, queryBundle, bookLoaderCallbacks);

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) { return false; }
        });
    }

    void updateBooksResultList(List<BookInfo> bookInfos) {
        if(!bookInfos.isEmpty()){
            resultados.setText(getString(R.string.hayRes1) +" " +  bookInfos.size() + " " +getString(R.string.hayRes2));
        }
        else{
            resultados.setText(getString(R.string.noHayRes));
        }
        booksResultListAdapter.setBooksData(bookInfos);
        booksResultListAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(0);
    }
}