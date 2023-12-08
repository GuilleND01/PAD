package es.ucm.fdi.readcycle.presentacion;

import static android.content.ContentValues.TAG;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import es.ucm.fdi.readcycle.R;
import es.ucm.fdi.readcycle.negocio.SAUser;
import es.ucm.fdi.readcycle.negocio.UserInfo;
import androidx.lifecycle.ViewModelProvider;



public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }
        else{
            setContentView(R.layout.activity_main);

            mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

            //Si hay aguna instancia guardada para la rotación
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new MiBibliotecaFragment()).commit();
            } else {
                int currentFragmentId = mainViewModel.getCurrentFragmentId();
                loadFragment(currentFragmentId);
            }

            //La navbar
            BottomNavigationView navbar = findViewById(R.id.navigationView);
            navbar.setOnItemSelectedListener(item -> {
                int fragmentId;
                if (item.getItemId() == R.id.navbar_biblioteca) {
                    fragmentId = R.id.fragment_biblioteca;
                } else if (item.getItemId() == R.id.navbar_buscar) {
                    fragmentId = R.id.fragment_buscar;
                } else if (item.getItemId() == R.id.navbar_perfil) {
                    fragmentId = R.id.fragment_perfil;
                } else {
                    return false;
                }

                loadFragment(fragmentId);
                return true;
            });
        }
        handleNotificationIntent(getIntent());

        /* Para no dejar volver al login */
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

            }
        };

        this.getOnBackPressedDispatcher().addCallback(this, callback);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // Si la actividad ya estaba en primer plano y se recibe una notificación
        handleNotificationIntent(intent);
    }

    private void handleNotificationIntent(Intent intent) {
        if (intent != null && intent.hasExtra("fragmentToLoad")) {
            String fragmentName = intent.getStringExtra("fragmentToLoad");

            // Verificamos q fragmento se debe abrir
            if (fragmentName != null && fragmentName.equals("UsuarioBibliotecaFragment")) {
                UsuarioBibliotecaFragment usuarioBibliotecaFragment = new UsuarioBibliotecaFragment();

                // Recogemos los datos adicionales
                Bundle bundle = new Bundle();
                if(intent.hasExtra("propietario")) {
                    String propietario = intent.getStringExtra("propietario");
                    bundle.putString("propietario", propietario);
                }
                usuarioBibliotecaFragment.setArguments(bundle);

                // Abrimos el fragmento
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, usuarioBibliotecaFragment)
                        .addToBackStack(null)
                        .commit();
            }
        }
    }


    private void loadFragment(int fragmentId) {
        Fragment fragment;
        //cargo el fragmento correspodiente
        if(fragmentId == R.id.fragment_biblioteca){
            fragment = new MiBibliotecaFragment();
        }else if(fragmentId == R.id.fragment_buscar){
            fragment = new BuscarFragment();
        }else if(fragmentId == R.id.fragment_perfil){
            fragment = new PerfilFragment();
        }else{//por defecto voy a la biblioteca
            fragment = new MiBibliotecaFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
        mainViewModel.setCurrentFragmentId(fragmentId);
    }

}