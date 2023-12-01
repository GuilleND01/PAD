package es.ucm.fdi.readcycle.integracion;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import es.ucm.fdi.readcycle.negocio.BookInfo;

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





    public boolean guardarLibro (BookInfo libro) {

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
                            });

                        });

                    });

            return true;
        } catch (Exception e){
            return false;
        }
    }

    public void bucarLibros(@NonNull BookInfo b, BuscarCallBacks callBacks){

        /*ArrayList<BookInfo> bs = new ArrayList<>();
        if(b.getAuthor() != null){
            SingletonDataBase.getInstance().getDB().collection(COL_LIBROS).whereEqualTo("Autor",
                    b.getAuthor()).get().addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot d: task.getResult()){
                        BookInfo book = new BookInfo(d.get("Titulo").toString(),
                                (ArrayList<String>) d.get("Genero"), d.get("Autor").toString(),
                                Integer.parseInt(d.get("Estado").toString()),
                                d.get("Descripcion").toString(), null,
                                Integer.parseInt(d.get("Paginas").toString()));
                        book.setPropietario(d.get("Propietario").toString());
                        bs.add(book);
                    }
                    callBacks.onCallback(bs);
                }
            });
        }
        else{
            SingletonDataBase.getInstance().getDB().collection(COL_LIBROS).whereEqualTo("Titulo",
                    b.getTitle()).get().addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot d: task.getResult()){
                                BookInfo book = new BookInfo(d.get("Titulo").toString(),
                                        (ArrayList<String>) d.get("Genero"), d.get("Autor").toString(),
                                        Integer.parseInt(d.get("Estado").toString()),
                                        d.get("Descripcion").toString(), null,
                                        Integer.parseInt(d.get("Paginas").toString()));
                                book.setPropietario(d.get("Propietario").toString());
                                bs.add(book);
                            }
                            callBacks.onCallback(bs);
                        }
                    });
        } */

    }

    public BookInfo getLibroById(String id){

   
        try{
            DocumentReference bookDocument = SingletonDataBase.getInstance().getDB().collection(COL_LIBROS).document(id);
            bookDocument.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    DocumentSnapshot ds = task.getResult();

                    Log.d("ah", ds.getData().toString());
                //(ArrayList<Integer>) ds.getData().get(GENERO)


                    bookInfo = new BookInfo(ds.getData().get(TITULO).toString(), (ArrayList<Integer>) ds.getData().get(GENERO),ds.getData().get(AUTOR).toString(), (Integer) ds.getData().get(ESTADO),
                            ds.getData().get(DESC).toString(), (Integer) ds.getData().get(NUM_PAGINAS), (android.net.Uri)ds.getData().get(RUTA_IMAGEN));


                    Log.d("CLAU", bookInfo.getAuthor());
                }

            });

            return bookInfo;


        }catch (Exception e){

        }
        return null;
    }
    public boolean eliminarLibro(BookInfo libro){
        //TODO
        return true;
    }


}
