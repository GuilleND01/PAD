package es.ucm.fdi.readcycle.integracion;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import es.ucm.fdi.readcycle.negocio.UserInfo;
import es.ucm.fdi.readcycle.presentacion.Registro;

public class DAOUser {
    private FirebaseAuth mAuth;
    private final String NOMBRE = "nombre;";
    private final String CONTACTO = "contacto";
    private final String CORREO = "correo";
    private final String ZONA = "zona";

    private final String COL_USUARIOS = "Usuarios";



    public DAOUser(){
        this.mAuth = FirebaseAuth.getInstance();
    }


    public void createAccount(UserInfo usuarioInsertar){
        mAuth.createUserWithEmailAndPassword(usuarioInsertar.getCorreo(), usuarioInsertar.getContraseña())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("USUARIO", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);

                            //metemos en la bd la informacion del usuario
                            try {
                                // Id provisonal hay que pensar cuál dar
                                Map<String, Object> data = new HashMap<>();
                                data.put(NOMBRE, usuarioInsertar.getNombre());
                                data.put(CONTACTO, usuarioInsertar.getContacto());
                                data.put(ZONA, usuarioInsertar.getZona());
                                data.put(CORREO, usuarioInsertar.getCorreo());

                                //getUID() me devuelve el user id de la tabla de usuarios para emparejarlo con el usuario correspondiente
                                SingletonDataBase.getInstance().getDB().collection(COL_USUARIOS).document(user.getUid()).set(data);

                            } catch (Exception e){

                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("USUARIO", "createUserWithEmail:failure", task.getException());
                            //Toast.makeText(Registro.this, "Authentication failed.", Toast.LENGTH_SHORT).show(); TODO UN BOOL
                            //updateUI(null);*/
                        }
                    }
                });
    }

    public void logOut(){
        FirebaseAuth.getInstance().signOut();
    }

    public void entrar(String correo, String contraseña){
        mAuth.signInWithEmailAndPassword(correo, contraseña)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("CLAU", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("CLAU", "signInWithEmail:failure", task.getException());

                        }
                    }
                });
    }

}
