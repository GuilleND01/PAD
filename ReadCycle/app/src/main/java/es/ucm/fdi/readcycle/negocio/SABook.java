package es.ucm.fdi.readcycle.negocio;

import es.ucm.fdi.readcycle.integracion.DAOBook;

public class SABook {
    /* No voy a hacer SABookImp ni hacerlo singleton porque si no nos queda el proyecto enorme, pero
    * esta clase se encarga de validar la lógica de negocio antes de guardar un libro*/

    public boolean guardarLibro(BookInfo book){
        DAOBook daoBook = new DAOBook();
        if (!daoBook.existeLibro(book)){
            return daoBook.guardarLibro(book);
        } return false;
    }
}
