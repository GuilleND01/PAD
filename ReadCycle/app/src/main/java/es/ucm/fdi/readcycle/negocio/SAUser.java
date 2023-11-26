package es.ucm.fdi.readcycle.negocio;

import com.google.android.gms.tasks.Task;

import es.ucm.fdi.readcycle.integracion.DAOUser;
import es.ucm.fdi.readcycle.integracion.UsuarioCallBacks;

public class SAUser {

    private UserInfo user;

    public SAUser(){

    }

    public void crearUsuario(UserInfo u){
        //TODO AMORES HAY QUE HACER LA LOGICA DE NEGOCIO
        DAOUser dao = new DAOUser();
        dao.createAccount(u);
    }

    public void entrarUsuario(String correo, String contraseña){
        DAOUser dao = new DAOUser();
        dao.entrar(correo, contraseña);
    }

    public void infoUsuario(String correo, UsuarioCallBacks callBacks) {
        DAOUser dao = new DAOUser();
        dao.getUsuario(correo, callBacks);
    }

}
