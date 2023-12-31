package es.ucm.fdi.readcycle.integracion;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import es.ucm.fdi.readcycle.negocio.BookInfo;
import es.ucm.fdi.readcycle.negocio.UserInfo;

/* Clase que accede a la colección "Libros" y realiza las operaciones CRUD básicas*/
public class DAOBook {

    private final String COL_LIBROS = "Libros";
    private final String COL_USUARIOS = "Usuarios";
    // Campos de la BD:
    private final String AUTOR = "Autor";
    private final String DESC = "Descripcion";
    private final String ESTADO = "Estado";
    private final String GENERO = "Genero";
    private final String NUM_PAGINAS = "Paginas";
    private final String TITULO = "Titulo";
    private final String PROPIETARIO = "Propietario";
    private final String RUTA_IMAGEN = "Ruta_Imagen";

    private BookInfo bookInfo;


    private final String CORREO = "correo";
    private final String LIBROS_LISTA = "ID_Libros";
    private static boolean exito;



    public void guardarLibro (BookInfo libro, CallBacks callbacks) {

        boolean exito = true;
        try {

            FirebaseImageStorage imageStorage = new FirebaseImageStorage();
            CollectionReference booksCollection = SingletonDataBase.getInstance().getDB()
                    .collection(COL_LIBROS);

            CollectionReference usersCollection = SingletonDataBase.getInstance().getDB()
                    .collection(COL_USUARIOS);

            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();

            String id = String.format("%s-%s-%s", libro.getTitle(), libro.getAuthor(), currentUser.getEmail().toString()).replace("", "");
            Map<String, Object> data = new HashMap<>();
            data.put(AUTOR, libro.getAuthor());
            data.put(TITULO, libro.getTitle());
            data.put(DESC, libro.getDescription());
            data.put(NUM_PAGINAS, libro.getPages());
            data.put(ESTADO, libro.getState());
            data.put(GENERO, libro.getGenre());
            data.put(PROPIETARIO, currentUser.getEmail());

            /* Se crea la referencia a la imagene en la base de datos*/
            StorageReference fileReference = imageStorage.getStorageRef().child(id + ".png");

            // TODO GESTIONAR EXEPCIONES DE TODO ESTO PROCESO
            /* Se rellena esa referencia con la imagen*/
            fileReference.putFile(libro.getSelectedImage())
                    .addOnSuccessListener(taskSnapshot -> {

                        /* Una vez guardada la imagen, se tiene el atributo "uri" que contiene la ruta absoluta a la imagen.
                        * Ya conseguido este atributo, ahora se procede a guardar el libro. */
                        fileReference.getDownloadUrl().addOnSuccessListener(uri -> {

                            /* Esta es una URL HTTP que se puede utilizar directamente en un
                            navegador web o con bibliotecas de carga de imágenes (como Glide o
                            Picasso en Android) para recuperar y mostrar la imagen. */
                            data.put(RUTA_IMAGEN, uri.toString());

                            /* Primero se añade el libro a una colección general de ellos.*/
                            Task<Void> booksCollectionTask = booksCollection.add(data).continueWithTask(new Continuation<DocumentReference, Task<Void>>() {

                                /* Seguido se añade el identificador del libro al array de libros del propio usuario */
                                @Override
                                public Task<Void> then(@NonNull Task<DocumentReference> task) throws Exception {
                                    String nuevoLibroId = task.getResult().getId();
                                    return usersCollection.document(currentUser.getUid()).update("ID_Libros", FieldValue.arrayUnion(nuevoLibroId));

                                }
                            }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    callbacks.onCallbackExito(true);
                                }
                            });

                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                callbacks.onCallbackExito(false);
                            }
                        });

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            callbacks.onCallbackExito(false);
                        }
                    });

        } catch (Exception e){
            callbacks.onCallbackExito(false);
        }
    }

    public void bucarLibros(@NonNull BookInfo b, CallBacks callBacks){

        ArrayList<BookInfo> bs = new ArrayList<>();
        if(b.getAuthor() != null){
            SingletonDataBase.getInstance().getDB().collection(COL_LIBROS).whereEqualTo(AUTOR,
                    b.getAuthor()).get().addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot d: task.getResult()){

                        ArrayList<Long> datosLong = (ArrayList<Long>) d.getData().get(GENERO);
                        ArrayList<Integer> generosInteger = new ArrayList<>();
                        for (Long datoLong : datosLong) {
                            generosInteger.add(datoLong.intValue());
                        }

                        BookInfo book = new BookInfo(d.get(TITULO).toString(),
                                generosInteger, d.get(AUTOR).toString(),
                                Integer.parseInt(d.get(ESTADO).toString()),
                                d.get(DESC).toString(),
                                Integer.parseInt(d.get(NUM_PAGINAS).toString()), Uri.parse(d.get(RUTA_IMAGEN).toString()));
                        book.setPropietario(d.get(PROPIETARIO).toString());
                        book.setId(d.getId());
                        bs.add(book);
                    }
                    callBacks.onCallbackBooks(bs);
                }
            });
        }
        else{
            SingletonDataBase.getInstance().getDB().collection(COL_LIBROS).whereEqualTo(TITULO,
                    b.getTitle()).get().addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot d: task.getResult()){

                        ArrayList<Long> datosLong = (ArrayList<Long>) d.getData().get(GENERO);
                        ArrayList<Integer> generosInteger = new ArrayList<>();
                        for (Long datoLong : datosLong) {
                            generosInteger.add(datoLong.intValue());
                        }

                        BookInfo book = new BookInfo(d.get(TITULO).toString(),
                                generosInteger, d.get(AUTOR).toString(),
                                Integer.parseInt(d.get(ESTADO).toString()),
                                d.get(DESC).toString(),
                                Integer.parseInt(d.get(NUM_PAGINAS).toString()), Uri.parse(d.get(RUTA_IMAGEN).toString()));
                        book.setPropietario(d.get(PROPIETARIO).toString());
                        book.setId(d.getId());
                        bs.add(book);
                    }
                    callBacks.onCallbackBooks(bs);
                }
            });
        }

    }
    public void getLibroById(String id, CallBacks callBacks){

   
        try{
            DocumentReference bookDocument = SingletonDataBase.getInstance().getDB().collection(COL_LIBROS).document(id);
            bookDocument.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        DocumentSnapshot ds = task.getResult();
                        if(ds.exists()){

                            //Hacemos el parse de los generos
                            ArrayList<Long> datosLong = (ArrayList<Long>) ds.getData().get(GENERO);
                            ArrayList<Integer> generosInteger = new ArrayList<>();
                            for (Long datoLong : datosLong) {
                                generosInteger.add(datoLong.intValue());
                            }
                            bookInfo = new BookInfo(ds.getData().get(TITULO).toString(), generosInteger,ds.getData().get(AUTOR).toString(), Integer.parseInt(ds.getData().get(ESTADO).toString()),
                                    ds.getData().get(DESC).toString(), Integer.parseInt(ds.getData().get(NUM_PAGINAS).toString()), Uri.parse(ds.getData().get(RUTA_IMAGEN).toString()));

                            //Asignamos el propietario e id por separado
                            bookInfo.setPropietario(ds.getData().get(PROPIETARIO).toString());
                            bookInfo.setId(ds.getId());
                        }
                    }
                    callBacks.onCallbackBookInfo(bookInfo);
                }

            });


        }catch (Exception e){

        }


    }

    public void eliminarLibro(BookInfo libro, CallBacks callBacks){

        DocumentReference bookDocument = SingletonDataBase.getInstance().getDB().collection(COL_LIBROS).document(libro.getId());
        CollectionReference usersCollection = SingletonDataBase.getInstance().getDB().collection(COL_USUARIOS);
        usersCollection.whereEqualTo(CORREO, libro.getPropietario()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot d : task.getResult()) {

                    // Creaamos una referencia al documento del usuario
                    String userID = d.getId();
                    DocumentReference userRef = usersCollection.document(userID);

                    // Obtenesmos el array ID_Libros del documento del usuario
                    List<String> idLibros = (List<String>) d.get(LIBROS_LISTA);

                    // Eliminamos un libro del array ID_Libros
                    if (idLibros != null) {
                        String libroEliminar = libro.getId();
                        idLibros.remove(libroEliminar);

                        // Actualizamos el campo ID_Libros con el array sin el libro eliminado
                        userRef.update(LIBROS_LISTA, idLibros).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Task<Void> booksCollectionTask = bookDocument.delete()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d("JULIA", "Libro eliminado");
                                                    FirebaseImageStorage imageStorage = new FirebaseImageStorage();

                                                    String id = String.format("%s-%s-%s", libro.getTitle(), libro.getAuthor(), libro.getPropietario()).replace("", "");
                                                    StorageReference fileReference = imageStorage.getStorageRef().child(id + ".png");
                                                    fileReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            callBacks.onCallbackExito(true);
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception exception) {
                                                            callBacks.onCallbackExito(false);
                                                        }
                                                    });
                                                    //callBacks.onCallbackExito(true);
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w("JULIA", "Error al eliminar libro", e);
                                                    callBacks.onCallbackExito(false);
                                                }
                                            });
                                } else {
                                    Log.w("JULIA", "Error al eliminar libro de biblioteca");
                                    callBacks.onCallbackExito(false);
                                }
                            }
                        });
                    }

                }
            }
        });
    }






}
