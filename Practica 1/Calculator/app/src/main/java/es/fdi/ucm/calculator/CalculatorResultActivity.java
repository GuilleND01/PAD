package es.fdi.ucm.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

public class CalculatorResultActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_result);

        Intent intent = getIntent();
        Double result = intent.getDoubleExtra("result", 0);

        textView = findViewById(R.id.textView);
        textView.setText(String.valueOf(result));
    }
}