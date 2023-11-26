package es.ucm.fdi.readcycle.presentacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import es.ucm.fdi.readcycle.R;
import es.ucm.fdi.readcycle.negocio.SAUser;
import es.ucm.fdi.readcycle.negocio.UserInfo;

public class Login extends AppCompatActivity {

    private EditText correo, contraseña;
    private TextView registrarseBtn;
    private Button entrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button b = findViewById(R.id.loginBtn);
        b.setBackgroundColor( getResources().getColor(R.color.fondoOscuro));

        correo = findViewById(R.id.correoInput);
        contraseña = findViewById(R.id.contraseñaInput);
        registrarseBtn = findViewById(R.id.RegistrarseBtn);
        entrar = findViewById(R.id.loginBtn);

        registrarseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registro.class);
                startActivity(intent);
            }
        });

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (correo.getText().toString().trim().equals("")){
                    correo.setError(getString(R.string.req));

                } else if (contraseña.getText().toString().trim().equals("")) {
                    contraseña.setError(getString(R.string.req));
                }
                else{
                    SAUser saUser = new SAUser();
                    saUser.entrarUsuario(correo.getText().toString(), contraseña.getText().toString());
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            Intent intent2 = new Intent(Login.this, MainActivity.class);
                            startActivity(intent2);
                        }
                    }, 1000);
                }


            }
        });
    }
}