package es.ucm.fdi.readcycle.presentacion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import es.ucm.fdi.readcycle.R;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button b = findViewById(R.id.loginBtn);

        b.setBackgroundColor( getResources().getColor(R.color.fondoOscuro));
    }
}