package es.ucm.fdi.readcycle.negocio;

import es.ucm.fdi.readcycle.integracion.DAOUser;

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

}
