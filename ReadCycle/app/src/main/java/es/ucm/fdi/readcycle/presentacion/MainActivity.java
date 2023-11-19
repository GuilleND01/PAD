package es.ucm.fdi.readcycle.presentacion;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import es.ucm.fdi.readcycle.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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



        //conexion con la base de datos//////////////////
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        /*
        //prueba de lectura
        db.collection("Usuarios")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
        setContentView(R.layout.activity_main);


     */

    }

}