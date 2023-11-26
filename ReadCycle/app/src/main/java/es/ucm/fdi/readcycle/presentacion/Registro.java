package es.ucm.fdi.readcycle.presentacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

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


                ArrayList<EditText> editTextObligatorios = new ArrayList<EditText>();
                editTextObligatorios.add(nombre);
                editTextObligatorios.add(correo);
                editTextObligatorios.add(contraseña);
                editTextObligatorios.add(confirmarContraseña);
                editTextObligatorios.add(zona);
                editTextObligatorios.add(contacto);


                boolean form_completado = true;

                /* Hago un array de EditText porque todos son obligatorios para registrar un libro y
                 * ahorrarme escribir cuatro veces la comprobación de dentro del if.*/
                for (EditText editText: editTextObligatorios){
                    if (editText.getText().toString().trim().equals("")){
                        editText.setError(getString(R.string.req));
                        form_completado = false;
                    }
                }


                if(form_completado){
                    SAUser saUser = new SAUser();
                    UserInfo u = new UserInfo(nombre.getText().toString(), correo.getText().toString(), contraseña.getText().toString(), zona.getText().toString(), contacto.getText().toString());
                    Pair<Boolean, Integer> validado = saUser.validarUsuario(u);

                    if(!validado.first){
                        String err = "";
                        switch (validado.second){
                            case 1:
                                //err = getString(R.string.req)
                                nombre.setError(getString(R.string.req));
                                break;
                            case 2:
                                //err = getString(R.string.errorCorreo)
                                correo.setError(getString(R.string.errorCorreo));
                                break;
                            case 3:
                                //err = getString(R.string.errorContraseña)
                                contraseña.setError(getString(R.string.errorContraseña));
                                break;
                        }
                        //Toast.makeText(Registro.this, err, Toast.LENGTH_LONG).show();

                    }
                    else{
                        if(!contraseña.getText().toString().equals(confirmarContraseña.getText().toString())){
                            //Toast.makeText(Registro.this, getString(R.string.contraseñasDiferentes), Toast.LENGTH_SHORT).show();
                            confirmarContraseña.setError(getString(R.string.contraseñasDiferentes));
                        }
                        else{
                            try {
                                saUser.crearUsuario(u);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        Intent intent2 = new Intent(Registro.this, MainActivity.class);
                                        startActivity(intent2);
                                    }
                                }, 2000);
                            } catch (Exception e){
                                Toast.makeText(Registro.this, getString(R.string.correoExiste), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                }




            }
        });

    }
}