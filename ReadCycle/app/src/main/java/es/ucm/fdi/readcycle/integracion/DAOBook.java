package es.ucm.fdi.readcycle.integracion;

import java.util.HashMap;
import java.util.Map;

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







    public boolean existeLibro (BookInfo libro){
        //SingletonDataBase.getInstance().getDB().collection(COL_LIBROS).document("...").get();
        // return doc.exists()
        return false;
    }

    public boolean guardarLibro (BookInfo libro) {

        try {
            // Id provisonal hay que pensar cuál dar
            String id = String.format("%s-%s", libro.getTitle(), "Nadie de momento");
            Map<String, Object> data = new HashMap<>();
            data.put(AUTOR, libro.getAuthor());
            data.put(TITULO, libro.getTitle());
            data.put(DESC, libro.getDescription());
            data.put(NUM_PAGINAS, libro.getPages());
            data.put(ESTADO, libro.getState());
            data.put(GENERO, libro.getGenre());
            data.put(PROPIETARIO, "Nadie de momento");

            SingletonDataBase.getInstance().getDB().collection(COL_LIBROS).document(id).set(data);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
