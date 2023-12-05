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

public class MainActivity extends AppCompatActivity {

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
            //CARGAMOS LA BIBLIOTECA DE PRIMERAS
            this.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, (Fragment) new MiBibliotecaFragment()).commit();

            //Navbar
            BottomNavigationView navbar = (BottomNavigationView) this.findViewById(R.id.navigationView);
            navbar.setOnItemSelectedListener((NavigationBarView.OnItemSelectedListener)(new NavigationBarView.OnItemSelectedListener(){
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if(item.getItemId() == R.id.navbar_biblioteca){
                        MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, (Fragment) new MiBibliotecaFragment()).commit();//remplazo el blanco por el fragmento nuevo
                        return true;
                    }
                    else if(item.getItemId() == R.id.navbar_buscar){
                        MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, (Fragment) new BuscarFragment()).commit();
                        return true;
                    }
                    else if(item.getItemId() == R.id.navbar_perfil){
                        MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, (Fragment) new PerfilFragment()).commit();
                        return true;
                    }
                    return true;
                }
            }));

        }

        /* Para no dejar volver al login */
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

            }
        };

        this.getOnBackPressedDispatcher().addCallback(this, callback);

    }


}