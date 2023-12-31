package es.ucm.fdi.readcycle.negocio;

import android.telecom.Call;

import es.ucm.fdi.readcycle.integracion.CallBacks;
import es.ucm.fdi.readcycle.integracion.DAOBook;

public class SABook {
    /* No voy a hacer SABookImp ni hacerlo singleton porque si no nos queda el proyecto enorme, pero
    * esta clase se encarga de validar la lógica de negocio antes de guardar un libro*/

    public void guardarLibro(BookInfo book, CallBacks callBacks){
        DAOBook daoBook = new DAOBook();
        daoBook.guardarLibro(book, callBacks);
    }

    public void bucarLibros(BookInfo b, CallBacks callBacks){
        DAOBook daoBook = new DAOBook();
        daoBook.bucarLibros(b, callBacks);
    }

    /*public BookInfo getLibroById(String id){
        DAOBook daoBook = new DAOBook();
        return daoBook.getLibroById(id);
    }*/

    public void eliminarLibro(BookInfo book, CallBacks callBacks){
        DAOBook daoBook = new DAOBook();
        daoBook.eliminarLibro(book, callBacks);
    }
}
