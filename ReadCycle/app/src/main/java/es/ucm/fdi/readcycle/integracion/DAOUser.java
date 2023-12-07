package es.ucm.fdi.readcycle.integracion;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.messaging.FirebaseMessaging;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import es.ucm.fdi.readcycle.negocio.BookInfo;
import es.ucm.fdi.readcycle.negocio.UserInfo;

public class DAOUser {
    private FirebaseAuth mAuth;
    private final String NOMBRE = "nombre";
    private final String CONTACTO = "contacto";
    private final String CORREO = "correo";
    private final String TOKEN = "token";
    private final String ZONA = "zona";
    private final String LIBRO = "ID_Libros";
    private final String NOTIFICACIONES = "notificaciones";


    private final String COL_USUARIOS = "Usuarios";
    private final String COL_LIBROS = "Libros";





    public DAOUser(){
        this.mAuth = FirebaseAuth.getInstance();
    }


    /* Añado un nuevo parámetro token en la BD. Cuando un dispositivo se registra para recibir
    notificaciones push a través de Firebase Cloud Messaging, se le asigna un token que
    identifica de manera única ese dispositivo. De esta manera, cuando se le solicite un libro a
    un usuario, al tener vinculado el token, se puede recuperar rápido y enviarle la notificación. */
    public void createAccount(UserInfo usuarioInsertar, CallBacks cb)  {

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()){
                    String token = task.getResult();

                    mAuth.createUserWithEmailAndPassword(usuarioInsertar.getCorreo(), usuarioInsertar.getContraseña())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task)  {

                                    if (task.isSuccessful()) {

                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("USUARIO", "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();

                                        new ArrayList<String>();

                                        //metemos en la bd la informacion del usuario
                                        Map<String, Object> data = new HashMap<>();
                                        data.put(NOMBRE, usuarioInsertar.getNombre());
                                        data.put(CONTACTO, usuarioInsertar.getContacto());
                                        data.put(ZONA, usuarioInsertar.getZona());
                                        data.put(CORREO, usuarioInsertar.getCorreo());
                                        data.put(LIBRO, new ArrayList<String>());
                                        data.put(NOTIFICACIONES, new ArrayList<Map<String,String>>());
                                        data.put(TOKEN, token);

                                        //getUID() me devuelve el user id de la tabla de usuarios para emparejarlo con el usuario correspondiente
                                        SingletonDataBase.getInstance().getDB().collection(COL_USUARIOS).document(user.getUid()).set(data);
                                        cb.onCallbackExito(true);


                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("USUARIO", "createUserWithEmail:failure", task.getException());
                                        cb.onCallbackExito(false);
                                    }
                                }
                            });
                } else {
                    cb.onCallbackExito(false);
                }
            }
        });


    }

    public void logOut(){
        FirebaseAuth.getInstance().signOut();
    }

    public void entrar(String correo, String contraseña, CallBacks cb){
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()){
                    String newToken = task.getResult();

                    mAuth.signInWithEmailAndPassword(correo, contraseña)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("CLAU", "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Map<String, Object> updates = new HashMap<>();
                                        updates.put("token", newToken);
                                        SingletonDataBase.getInstance().getDB()
                                                .collection("Usuarios").document(user.getUid()).update(updates);

                                        cb.onCallbackExito(true);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("CLAU", "signInWithEmail:failure", task.getException());
                                        cb.onCallbackExito(false);

                                    }
                                }
                            });
                    }
            }
        });
    }


    public void getUsuario(String email, CallBacks callBacks){
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
    public void getBiblioteca(String correo, CallBacks cb){
        ArrayList<BookInfo> biblioteca = new ArrayList<>();
        try {

            CollectionReference usersCollection = SingletonDataBase.getInstance().getDB().collection(COL_USUARIOS);

            DAOBook daoBook = new DAOBook();
            usersCollection.whereEqualTo(CORREO, correo).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot d : task.getResult()) {
                        // Obtenesmos el array ID_Libros del documento del usuario
                        List<String> idLibros = (List<String>) d.get(LIBRO);
                        AtomicInteger count = new AtomicInteger(idLibros.size());
                        for(String libro: idLibros){
                            daoBook.getLibroById(libro, new CallBacks() {
                                @Override
                                public void onCallbackBookInfo(BookInfo b) {
                                    if(b != null){
                                        biblioteca.add(b);
                                    }
                                    if (count.decrementAndGet() == 0) {
                                        // Todos los libros se han cargado, llamar al callback
                                        cb.onCallbackBooks(biblioteca);
                                    }
                                }
                            });
                        }
                        //No habia ningun libro
                        cb.onCallbackBooks(biblioteca);

                    }
                }
            });

        }
         catch (Exception e){

        }
    }

    public void buscarUsuarios(UserInfo u, CallBacks cb){
        ArrayList<UserInfo> us = new ArrayList<>();
        SingletonDataBase.getInstance().getDB().collection(COL_USUARIOS).whereEqualTo(NOMBRE,
                u.getNombre()).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                for (QueryDocumentSnapshot d: task.getResult()){
                    UserInfo user = new UserInfo();
                    user.setNombre(d.get(NOMBRE).toString());
                    user.setZona(d.get(ZONA).toString());
                    user.setCorreo(d.get(CORREO).toString());
                    user.setContacto(d.get(CONTACTO).toString());
                    us.add(user);
                }
                cb.onCallbackUsers(us);
            }
        });
    }

    public void anadirNotificacion (String body, String fecha, String email){
        CollectionReference usersCollection = SingletonDataBase.getInstance().getDB().collection(COL_USUARIOS);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        Map<String, String> not =  new HashMap<>();
        not.put("mensaje", body);
        not.put("fecha", fecha);
        not.put("email", email);

        usersCollection.document(currentUser.getUid()).update(NOTIFICACIONES,
                FieldValue.arrayUnion(not));;
    }

    public void getNotifs(String email, CallBacks callBacks){
        SingletonDataBase.getInstance().getDB().collection(COL_USUARIOS).whereEqualTo(CORREO,
                email).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                ArrayList<Map<String,String>> ls = new ArrayList<>();
                for (QueryDocumentSnapshot d: task.getResult()){
                    for(Map<String,String> str : (ArrayList<Map<String,String>>) d.get(NOTIFICACIONES))
                        ls.add(str);
                }
                callBacks.onCallback(ls);
            }
        });

    }
}
