package es.ucm.fdi.readcycle.integracion;

import java.util.ArrayList;

import es.ucm.fdi.readcycle.negocio.BookInfo;
import es.ucm.fdi.readcycle.negocio.UserInfo;

public interface CallBacks {

    default void onCallback(UserInfo u){}

    default void onCallbackBookInfo(BookInfo b){}
    default void onCallbackBooks(ArrayList<BookInfo> bs){}
    default void onCallbackExito(Boolean exito){}


}
