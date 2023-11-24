package es.ucm.fdi.readcycle.presentacion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import es.ucm.fdi.readcycle.R;
import es.ucm.fdi.readcycle.negocio.SAUser;
import es.ucm.fdi.readcycle.negocio.UserInfo;

public class Registro extends AppCompatActivity {


private EditText nombre, correo, contraseña, confirmarContraseña, zona, contacto;
private Button registrarse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nombre = findViewById(R.id.nombreRegistro);
        correo = findViewById(R.id.correoRegistro);
        contraseña = findViewById(R.id.contraseñaRegistro);
        confirmarContraseña = findViewById(R.id.confimContraseñaRegistro);
        contacto = findViewById(R.id.formaContactoRegistro);
        zona = findViewById(R.id.zonaLocalizacionRegistro);
        registrarse = findViewById(R.id.registro);


        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SAUser saUser = new SAUser();
                UserInfo u = new UserInfo(nombre.getText().toString(), correo.getText().toString(), contraseña.getText().toString(), zona.getText().toString(), contacto.getText().toString());
                saUser.crearUsuario(u);

            }
        });

    }
}