package es.fdi.ucm.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Calculator calc;
    EditText editTextX;
    EditText editTextY;
    Button but;
    ArrayList<Button> keys;
    int op_mode = 0; // Si es 0, escribe en X y si es 1 escribe en Y.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calc = new Calculator();
        editTextX = findViewById(R.id.x);
        editTextY = findViewById(R.id.y);
        /* Starts the button of layout 1 */
        but = findViewById(R.id.button);

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addXY();
            }
        });

        /* Starts the buttons of layout 2 */
        keys = new ArrayList<>();
        keys.add(findViewById(R.id.b0));
        keys.add(findViewById(R.id.b1));
        keys.add(findViewById(R.id.b2));
        keys.add(findViewById(R.id.b3));
        keys.add(findViewById(R.id.b4));
        keys.add(findViewById(R.id.b5));
        keys.add(findViewById(R.id.b6));
        keys.add(findViewById(R.id.b7));
        keys.add(findViewById(R.id.b8));
        keys.add(findViewById(R.id.b9));
        keys.add(findViewById(R.id.bplus));
        keys.add(findViewById(R.id.bequal));
        keys.add(findViewById(R.id.bclear));
        keys.add(findViewById(R.id.bdecimal));

        for(int i = 0; i < 10; i++) {
            final int aux = i;
            keys.get(i).setOnClickListener(view -> useCalculator(String.valueOf(aux)));
        }

        keys.get(10).setOnClickListener(view -> useCalculator("+"));
        keys.get(11).setOnClickListener(view -> addXY());
        keys.get(12).setOnClickListener(view -> useCalculator("C"));
        keys.get(13).setOnClickListener(view -> useCalculator("."));
    }

    protected void addXY() {

        Log.i("MiApp", editTextX.getText() + " + " + editTextY.getText());

        if( !editTextX.getText().toString().isEmpty() && !editTextY.getText().toString().isEmpty()){
            try{
                Intent intent = new Intent(this, CalculatorResultActivity.class);
                intent.putExtra("result", calc.addNumbers(Double.valueOf(editTextX.getText().toString()),
                        Double.valueOf(editTextY.getText().toString())));

                startActivity(intent);
            }catch (Exception e){
                Log.w("MiApp", "Formato incorrecto");
            }
        }
        else{
            Log.w("MiApp", "TextField vacío");
        }


    }

    protected void useCalculator(String e) {
        if (e == "+") {
            op_mode = 1;
            return;
        }
        else if (e == "C") {
            editTextX.setText("");
            editTextY.setText("");
            op_mode = 0;
            return;
        }

        String aux;
        /* Vemos si estamos en op_mode 0 o 1 */
        if (op_mode == 0) { // X
            aux = editTextX.getText().toString();
            editTextX.setText(aux + e);
        }
        else { // Y
            aux = editTextY.getText().toString();
            editTextY.setText(aux + e);
        }
    }
}