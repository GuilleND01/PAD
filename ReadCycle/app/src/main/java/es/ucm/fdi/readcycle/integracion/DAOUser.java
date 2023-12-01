package es.ucm.fdi.readcycle.integracion;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import es.ucm.fdi.readcycle.negocio.BookInfo;
import es.ucm.fdi.readcycle.negocio.UserInfo;
import es.ucm.fdi.readcycle.presentacion.Registro;

public class DAOUser {
    private FirebaseAuth mAuth;
    private final String NOMBRE = "nombre";
    private final String CONTACTO = "contacto";
    private final String CORREO = "correo";
    private final String ZONA = "zona";
    private final String LIBRO = "ID_Libros";

    private final String COL_USUARIOS = "Usuarios";
    private final String COL_LIBROS = "Libros";





    public DAOUser(){
        this.mAuth = FirebaseAuth.getInstance();
    }


    public void createAccount(UserInfo usuarioInsertar)  {

        mAuth.createUserWithEmailAndPassword(usuarioInsertar.getCorreo(), usuarioInsertar.getContraseña())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)  {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("USUARIO", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);

                            //metemos en la bd la informacion del usuario

                            // Id provisonal hay que pensar cuál dar
                            Map<String, Object> data = new HashMap<>();
                            data.put(NOMBRE, usuarioInsertar.getNombre());
                            data.put(CONTACTO, usuarioInsertar.getContacto());
                            data.put(ZONA, usuarioInsertar.getZona());
                            data.put(CORREO, usuarioInsertar.getCorreo());

                            //getUID() me devuelve el user id de la tabla de usuarios para emparejarlo con el usuario correspondiente
                            SingletonDataBase.getInstance().getDB().collection(COL_USUARIOS).document(user.getUid()).set(data);



                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("USUARIO", "createUserWithEmail:failure", task.getException());
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

    public void getUsuario(String email, UsuarioCallBacks callBacks){
        UserInfo user = new UserInfo();
        SingletonDataBase.getInstance().getDB().collection(COL_USUARIOS).whereEqualTo(CORREO,
                email).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                for (QueryDocumentSnapshot d: task.getResult()){

                     user.setNombre(d.get(NOMBRE).toString());
                     user.setContacto(d.get(CONTACTO).toString());
                     user.setCorreo(d.get(CORREO).toString());
                     user.setZona(d.get(ZONA).toString());
                }
                callBacks.onCallback(user);
            }
        });

    }

    public void editZona(String nuevaZona){

        CollectionReference usersCollection = SingletonDataBase.getInstance().getDB().collection(COL_USUARIOS);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        Map<String, Object> data = new HashMap<>();
        data.put(ZONA, nuevaZona);
        usersCollection.document(currentUser.getUid()).update(data);

    }

    public void editContacto(String nuevoContacto){
        CollectionReference usersCollection = SingletonDataBase.getInstance().getDB().collection(COL_USUARIOS);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        Map<String, Object> data = new HashMap<>();
        data.put(CONTACTO, nuevoContacto);
        usersCollection.document(currentUser.getUid()).update(data);
    }

    //Te trae la biblioteca del carreo q le llega
    public ArrayList<BookInfo> getBiblioteca(String correo){
        ArrayList<BookInfo> biblioteca = new ArrayList<>();
        try {

            CollectionReference usersCollection = SingletonDataBase.getInstance().getDB().collection(COL_USUARIOS);

            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            DAOBook daoBook = new DAOBook();
            usersCollection.whereEqualTo(CORREO, currentUser.getEmail()).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot d : task.getResult()) {
                        biblioteca.add(daoBook.getLibroById(d.toString()));

                    }
                }
            });
            return biblioteca;
        }
        
         catch (Exception e){
            return biblioteca;
        }
    }


}
