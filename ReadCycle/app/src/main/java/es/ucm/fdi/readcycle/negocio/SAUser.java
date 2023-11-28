package es.ucm.fdi.readcycle.negocio;

import android.util.Pair;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.auth.User;

import es.ucm.fdi.readcycle.integracion.DAOUser;
import es.ucm.fdi.readcycle.integracion.UsuarioCallBacks;

public class SAUser {

    private UserInfo user;
    private final String EMAILPATTERN = "^([a-zA-Z][a-zA-Z0-9._-]*[a-zA-Z0-9]|[a-zA-Z])@[a-zA-Z]+\\.[a-zA-Z]{2,4}$";
    private final String NAMEPATTERN = "^[a-zA-Z][a-zA-Z ]*[a-zA-Z]$";
    private final String PASSWORDPATTERN = "^(?=.*\\d)(?=.*[a-zA-Z])[a-zA-Z0-9]{6,}$"; //al menos 6 caracteres, al menos 1 numero y esta compuesto por numeros y letras


    public SAUser(){

    }

    public void crearUsuario(UserInfo u){
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

    public void editarZona(String zona, String correo){
        DAOUser dao = new DAOUser();
        dao.editZona(zona, correo);
    }

    public void editarContacto(String contacto, String correo){
        DAOUser dao = new DAOUser();
        dao.editContacto(contacto, correo);
    }

    public void editarZonaYContacto(String zona, String contacto, String correo){
        DAOUser dao = new DAOUser();
        dao.editZona(zona, correo);
        dao.editContacto(contacto, correo);
    }
}
