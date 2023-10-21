package es.ucm.fdi.googlebooksclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;


import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BookLoaderCallbacks bookLoaderCallbacks;
    private BooksResultListAdapter booksResultListAdapter;
    private String queryString;
    private String printType;
    private RadioGroup r1;
    private RadioGroup r2;
    private static final int BOOK_LOADER_ID  = 53785;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        r1 = findViewById(R.id.r1);
        r2 = findViewById(R.id.r2);
        r1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() != 0) {
                    findViewById(R.id.autor).setEnabled(false);
                    findViewById(R.id.titulo).setEnabled(true);
                }
                else {
                    findViewById(R.id.autor).setEnabled(true);
                    findViewById(R.id.titulo).setEnabled(true);
                }
            }
        });
        /* bookLoaderCallbacks = new BookLoaderCallbacks(this, "","");
        LoaderManager loaderManager = LoaderManager.getInstance(this);
        if(loaderManager.getLoader(BOOK_LOADER_ID) != null){
            loaderManager.initLoader(BOOK_LOADER_ID, null, bookLoaderCallbacks);
        } */
    }

    public void searchBooks(View view) {
        // TODO obtener queryString y printType
        Bundle queryBundle = new Bundle();
        queryBundle.putString(BookLoaderCallbacks.EXTRA_QUERY, queryString);
        queryBundle.putString(BookLoaderCallbacks.EXTRA_PRINT_TYPE, printType);
        LoaderManager.getInstance(this)
                .restartLoader(BOOK_LOADER_ID, queryBundle, bookLoaderCallbacks);
    }

    void updateBooksResultList(List<BookInfo> bookInfos) {
        booksResultListAdapter.setBooksData(bookInfos);
        booksResultListAdapter.notifyDataSetChanged();
    }
}