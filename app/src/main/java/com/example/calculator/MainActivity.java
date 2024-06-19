package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity {

    private TextView resultDisplay;
    private boolean lastNumeric = false;
    private boolean stateError = false;
    private boolean lastDot = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultDisplay = findViewById(R.id.result_display);

        setNumericOnClickListener();
        setOperatorOnClickListener();
    }

    private void setNumericOnClickListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stateError) {
                    resultDisplay.setText(((MaterialButton) v).getText());
                    stateError = false;
                } else {
                    resultDisplay.append(((MaterialButton) v).getText());
                }
                lastNumeric = true;
            }
        };

        findViewById(R.id.button_0).setOnClickListener(listener);
        findViewById(R.id.button_1).setOnClickListener(listener);
        findViewById(R.id.button_2).setOnClickListener(listener);
        findViewById(R.id.button_3).setOnClickListener(listener);
        findViewById(R.id.button_4).setOnClickListener(listener);
        findViewById(R.id.button_5).setOnClickListener(listener);
        findViewById(R.id.button_6).setOnClickListener(listener);
        findViewById(R.id.button_7).setOnClickListener(listener);
        findViewById(R.id.button_8).setOnClickListener(listener);
        findViewById(R.id.button_9).setOnClickListener(listener);
    }

    private void setOperatorOnClickListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastNumeric && !stateError) {
                    resultDisplay.append(((MaterialButton) v).getText());
                    lastNumeric = false;
                    lastDot = false;
                }
            }
        };

        findViewById(R.id.button_add).setOnClickListener(listener);
        findViewById(R.id.button_subtract).setOnClickListener(listener);
        findViewById(R.id.button_multiply).setOnClickListener(listener);
        findViewById(R.id.button_divide).setOnClickListener(listener);
        findViewById(R.id.button_open_bracket).setOnClickListener(listener);
        findViewById(R.id.button_close_bracket).setOnClickListener(listener);

        findViewById(R.id.button_c).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultDisplay.setText("");
                lastNumeric = false;
                stateError = false;
                lastDot = false;
            }
        });

        findViewById(R.id.button_equals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEqual();
            }
        });

        findViewById(R.id.button_dot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastNumeric && !stateError && !lastDot) {
                    resultDisplay.append(".");
                    lastNumeric = false;
                    lastDot = true;
                }
            }
        });
    }

    private void onEqual() {
        if (lastNumeric && !stateError) {
            String text = resultDisplay.getText().toString();
            Expression expression = new ExpressionBuilder(text).build();
            try {
                double result = expression.evaluate();
                resultDisplay.setText(Double.toString(result));
                lastDot = true; // Result contains a dot (.)
            } catch (ArithmeticException ex) {
                resultDisplay.setText("Error");
                stateError = true;
                lastNumeric = false;
            }
        }
    }
}
