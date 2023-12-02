package es.ucm.fdi.readcycle.integracion;

import java.util.ArrayList;

import es.ucm.fdi.readcycle.negocio.BookInfo;
import es.ucm.fdi.readcycle.negocio.UserInfo;

public interface UsuarioCallBacks {

    void onCallback(UserInfo u);
    void onCallbackBookInfo(BookInfo b);
    void onCallbackBooks(ArrayList<BookInfo> bs);
}
