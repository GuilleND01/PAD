package es.ucm.fdi.readcycle.integracion;

import java.util.ArrayList;

import es.ucm.fdi.readcycle.negocio.BookInfo;

public interface BuscarCallBacks {
    void onCallback(ArrayList<BookInfo> bs);
}
