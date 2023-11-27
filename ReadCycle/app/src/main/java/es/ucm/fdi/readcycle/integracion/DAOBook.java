package es.ucm.fdi.readcycle.integracion;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
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
    // Campos de la BD:
    private final String AUTOR = "Autor";
    private final String DESC = "Descripcion";
    private final String ESTADO = "Estado";
    private final String GENERO = "Genero";
    private final String NUM_PAGINAS = "Paginas";
    private final String TITULO = "Titulo";
    private final String PROPIETARIO = "Propietario";
    private final String RUTA_IMAGEN = "Ruta_Imagen";





    public boolean guardarLibro (BookInfo libro) {

        try {
            // Id provisonal hay que pensar cuál dar
            FirebaseImageStorage imageStorage = new FirebaseImageStorage();
            CollectionReference booksCollection = SingletonDataBase.getInstance().getDB()
                    .collection(COL_LIBROS);

            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();

            String id = String.format("%s-%s", libro.getTitle(), libro.getAuthor());
            Map<String, Object> data = new HashMap<>();
            data.put(AUTOR, libro.getAuthor());
            data.put(TITULO, libro.getTitle());
            data.put(DESC, libro.getDescription());
            data.put(NUM_PAGINAS, libro.getPages());
            data.put(ESTADO, libro.getState());
            data.put(GENERO, libro.getGenre());
            data.put(PROPIETARIO, currentUser.getEmail());

            StorageReference fileReference = imageStorage.getStorageRef().child(id + ".png");

            fileReference.putFile(libro.getSelectedImage())
                    .addOnSuccessListener(taskSnapshot -> {

                        fileReference.getDownloadUrl().addOnSuccessListener(uri -> {

                            /* Esta es una URL HTTP que se puede utilizar directamente en un
                            navegador web o con bibliotecas de carga de imágenes (como Glide o
                            Picasso en Android) para recuperar y mostrar la imagen. */
                            String downloadUrl = uri.toString();
                            data.put(RUTA_IMAGEN, downloadUrl);

                            Task<Void> updateTask = booksCollection.
                                    document(currentUser.getEmail()).collection("Libros").
                                    document(id).set(data);

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
}
