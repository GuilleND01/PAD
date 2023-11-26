package es.ucm.fdi.readcycle.negocio;

import android.provider.ContactsContract;

public class UserInfo {
    private String nombre, correo, contacto,zona, contraseña, confirmarContraseña;

    //contructor para creacion de usuario
    public UserInfo(String nombre, String correo, String contraseña, String zona, String contacto){
        this.contraseña = contraseña;
        this.nombre = nombre;
        this.zona = zona;
        this.contacto = contacto;
        this.correo = correo;
    }

    //constructo para usuario ya creado

    public UserInfo(){

    };


    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContacto() {
        return contacto;
    }

    public String getZona() {
        return zona;
    }

    public String getContraseña() {
        return contraseña;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }
}
