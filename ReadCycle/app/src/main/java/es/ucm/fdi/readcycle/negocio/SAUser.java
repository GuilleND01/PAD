package es.ucm.fdi.readcycle.negocio;

import es.ucm.fdi.readcycle.integracion.DAOUser;

public class SAUser {

    private UserInfo user;

    public SAUser(){

    }

    public void crearUsuario(UserInfo u){
        DAOUser dao = new DAOUser();
        dao.createAccount(u);
    }

}
