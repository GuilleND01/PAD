package es.ucm.fdi.readcycle.negocio;

import android.provider.ContactsContract;

public class UserInfo {
    private String nombre, correo, contacto,zona, contraseña, confirmarContraseña;

    public UserInfo(String nombre, String correo, String contraseña, String zona, String contacto){
        this.contraseña = contraseña;
        this.nombre = nombre;
        this.zona = zona;
        this.contacto = contacto;

        this.correo = correo;
    }

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


}
