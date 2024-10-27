package com.example.domashnee_zadanie_3.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView display;
    private StringBuilder input = new StringBuilder();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a vertical LinearLayout
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        // Create display TextView
        display = new TextView(this);
        display.setTextSize(36);
        display.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        display.setPadding(16, 16, 16, 16);
        layout.addView(display);

        // Create a grid layout for buttons
        LinearLayout buttonLayout = new LinearLayout(this);
        buttonLayout.setOrientation(LinearLayout.VERTICAL);

        String[][] buttonTexts = {
                {"7", "8", "9", "/"},
                {"4", "5", "6", "*"},
                {"1", "2", "3", "-"},
                {"0", "C", "=", "+"}
        };

        for (String[] row : buttonTexts) {
            LinearLayout rowLayout = new LinearLayout(this);
            for (String text : row) {
                Button button = new Button(this);
                button.setText(text);
                button.setLayoutParams(new LinearLayout.LayoutParams(
                        0, // Width is set to 0 and weight is used to fill the space
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1.0f)); // Equal weight for equal distribution
                button.setOnClickListener(buttonClickListener);
                rowLayout.addView(button);
            }
            buttonLayout.addView(rowLayout);
        }

        layout.addView(buttonLayout);
        setContentView(layout);


    }

    // Button click listener
    private final View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View view) {
            Button button = (Button) view;
            String buttonText = button.getText().toString();

            if (buttonText.equals("C")) {
                input.setLength(0); // Clear input
                display.setText("");
            } else if (buttonText.equals("=")) {
                // Evaluate the expression
                try {
                    String result = String.valueOf(eval(input.toString()));
                    display.setText(result);
                    input.setLength(0); // Clear input after showing result
                } catch (Exception e) {
                    display.setText("Error");
                }
            } else {
                input.append(buttonText);
                display.setText(input.toString());
            }
        }
    };

    private double eval(String expression) {
        // Replace the operators with spaces for simple parsing
        String[] tokens = expression.split("(?=[-+*/])|(?<=[-+*/])");
        double result = 0;
        double currentNumber = 0;
        String operator = "+";

        for (String token : tokens) {
            token = token.trim();
            if (token.isEmpty()) continue;

            if (Character.isDigit(token.charAt(0))) {
                currentNumber = Double.parseDouble(token);
            } else {
                switch (operator) {
                    case "+":
                        result += currentNumber;
                        break;
                    case "-":
                        result -= currentNumber;
                        break;
                    case "*":
                        result *= currentNumber;
                        break;
                    case "/":
                        result /= currentNumber;
                        break;
                }
                operator = token;
                currentNumber = 0;
            }
        }

        switch (operator) {
            case "+":
                result += currentNumber;
                break;
            case "-":
                result -= currentNumber;
                break;
            case "*":
                result *= currentNumber;
                break;
            case "/":
                result /= currentNumber;
                break;
        }

        return result;
    }



}