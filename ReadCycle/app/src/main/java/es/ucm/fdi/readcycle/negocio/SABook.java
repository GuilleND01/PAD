package es.ucm.fdi.readcycle.negocio;

import java.util.ArrayList;

import es.ucm.fdi.readcycle.integracion.BuscarCallBacks;
import es.ucm.fdi.readcycle.integracion.DAOBook;

public class SABook {
    /* No voy a hacer SABookImp ni hacerlo singleton porque si no nos queda el proyecto enorme, pero
    * esta clase se encarga de validar la lógica de negocio antes de guardar un libro*/

    public int guardarLibro(BookInfo book){
        DAOBook daoBook = new DAOBook();
            if(daoBook.guardarLibro(book)){
                return 1;
            } else return -1;

    }

    public void bucarLibros(BookInfo b, BuscarCallBacks callBacks){
        DAOBook daoBook = new DAOBook();
        daoBook.bucarLibros(b, callBacks);
    }

    public int eliminarLibro(BookInfo book){
        DAOBook daoBook = new DAOBook();
        if(daoBook.eliminarLibro(book)){
            return 1;
        } else return -1;
    }
}
