package es.ucm.fdi.readcycle.negocio;

import android.util.Pair;

import es.ucm.fdi.readcycle.integracion.DAOUser;
import es.ucm.fdi.readcycle.integracion.CallBacks;

public class SAUser {

    private UserInfo user;
    private final String EMAILPATTERN = "^([a-zA-Z][a-zA-Z0-9._-]*[a-zA-Z0-9]|[a-zA-Z])@[a-zA-Z]+\\.[a-zA-Z]{2,4}$";
    private final String NAMEPATTERN = "^[a-zA-Z][a-zA-Z ]*[a-zA-Z]$";
    private final String PASSWORDPATTERN = "^(?=.*\\d)(?=.*[a-zA-Z])[a-zA-Z0-9]{6,}$"; //al menos 6 caracteres, al menos 1 numero y esta compuesto por numeros y letras


    public SAUser(){

    }

    public void crearUsuario(UserInfo u, CallBacks cb){
        DAOUser dao = new DAOUser();
        dao.createAccount(u, cb);
    }

    public void entrarUsuario(String correo, String contraseña, CallBacks cb){
        DAOUser dao = new DAOUser();
        dao.entrar(correo, contraseña, cb);
    }

    public void infoUsuario(String correo, CallBacks callBacks) {
        DAOUser dao = new DAOUser();
        dao.getUsuario(correo, callBacks);
    }

    public Pair<Boolean, Integer> validarUsuario(UserInfo u){
        if(!u.getNombre().matches(NAMEPATTERN)){
            return new Pair<>(false, 1);
        } else if (!u.getCorreo().matches(EMAILPATTERN)) {
            return new Pair<>(false, 2);
        } else if (!u.getContraseña().matches(PASSWORDPATTERN)) {
            return new Pair<>(false, 3);
        }
        return  new Pair<>(true, 0);
    }

    public void editarZona(String zona){
        DAOUser dao = new DAOUser();
        dao.editZona(zona);
    }

    public void editarContacto(String contacto){
        DAOUser dao = new DAOUser();
        dao.editContacto(contacto);
    }

    public void editarZonaYContacto(String zona, String contacto){
        DAOUser dao = new DAOUser();
        dao.editZona(zona);
        dao.editContacto(contacto);
    }

    public void getBiblioteca(String correo, CallBacks callBacks){
        DAOUser dao = new DAOUser();
        dao.getBiblioteca(correo, callBacks);
    }

    public void buscarUsuarios(UserInfo u, CallBacks callBacks){
        DAOUser dao = new DAOUser();
        dao.buscarUsuarios(u, callBacks);
    }

    public void aniadirNotificacion(String body, String fecha, String email) {
        DAOUser dao = new DAOUser();
        dao.anadirNotificacion(body, fecha, email);
    }

    public void getNotificaciones(String correo, CallBacks callBacks){
        DAOUser dao = new DAOUser();
        dao.getNotifs(correo, callBacks);
    }
}
