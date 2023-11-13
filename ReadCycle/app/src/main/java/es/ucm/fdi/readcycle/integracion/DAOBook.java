package es.ucm.fdi.readcycle.integracion;

import es.ucm.fdi.readcycle.negocio.BookInfo;

public class DAOBook {

    /* Clase que accede a la colección "Libros" y realiza las operaciones CRUD básicas*/

    public boolean existeLibro (BookInfo libro){
        //SingletonDataBase.getInstance().getDB().collection("Libros").document("...").get();
        // return doc.exists()
        return true;
    }

    public boolean guardarLibro (BookInfo libro) {
        return true;
    }
}
