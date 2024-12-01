package com.example.dsa_visualizer;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Stack;

public class postfixToinfix_prefix extends AppCompatActivity {
    private EditText postfixInput;
    private TextView conversionOutput;
    private Button infixButton, prefixButton;
    private TableLayout conversionTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postfix_toinfix_prefix);
        postfixInput = findViewById(R.id.postfixInput);
        conversionOutput = findViewById(R.id.conversionOutput);
        infixButton = findViewById(R.id.infixButton);
        prefixButton = findViewById(R.id.prefixButton);
        conversionTable = findViewById(R.id.conversionTable);

        infixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postfix = postfixInput.getText().toString().trim();
                if (TextUtils.isEmpty(postfix)) {
                    Toast.makeText(postfixToinfix_prefix.this, "Please enter a postfix expression", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Clear previous table rows
                clearTableRows();

                // Convert to infix and display steps
                String infix = convertPostfixToInfix(postfix);
                conversionOutput.setText(infix);
            }
        });

        prefixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postfix = postfixInput.getText().toString().trim();
                if (TextUtils.isEmpty(postfix)) {
                    Toast.makeText(postfixToinfix_prefix.this, "Please enter a postfix expression", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Clear previous table rows
                clearTableRows();

                // Convert to prefix and display steps
                String prefix = convertPostfixToPrefix(postfix);
                conversionOutput.setText(prefix);
            }
        });
    }

    private void clearTableRows() {
        int childCount = conversionTable.getChildCount();
        if (childCount > 1) {
            conversionTable.removeViews(1, childCount - 1); // Keep header row
        }
    }

    // Method to convert postfix to infix and show steps
    private String convertPostfixToInfix(String postfix) {
        Stack<String> stack = new Stack<>();
        int stepCount = 0;

        for (char ch : postfix.toCharArray()) {
            if (Character.isLetterOrDigit(ch)) {
                stack.push(String.valueOf(ch));
                addRowToTable(stepCount++, String.valueOf(ch), stack.toString(), "");
            } else {
                String operand2 = stack.pop();
                String operand1 = stack.pop();
                String infix = "(" + operand1 + ch + operand2 + ")";
                stack.push(infix);
                addRowToTable(stepCount++, String.valueOf(ch), stack.toString(), infix);
            }
        }

        return stack.peek();
    }

    // Method to convert postfix to prefix and show steps
    private String convertPostfixToPrefix(String postfix) {
        Stack<String> stack = new Stack<>();
        int stepCount = 0;

        for (char ch : postfix.toCharArray()) {
            if (Character.isLetterOrDigit(ch)) {
                stack.push(String.valueOf(ch));
                addRowToTable(stepCount++, String.valueOf(ch), stack.toString(), "");
            } else {
                String operand2 = stack.pop();
                String operand1 = stack.pop();
                String prefix = ch + operand1 + operand2;
                stack.push(prefix);
                addRowToTable(stepCount++, String.valueOf(ch), stack.toString(), prefix);
            }
        }

        return stack.peek();
    }

    private void addRowToTable(int step, String expression, String stackState, String result) {
        TableRow row = new TableRow(this);

        TextView stepView = new TextView(this);
        stepView.setText(String.valueOf(step));
        stepView.setPadding(16, 8, 16, 8);
        stepView.setGravity(Gravity.CENTER);
        stepView.setBackgroundResource(R.drawable.table_row_border);
        row.addView(stepView);

        TextView expressionView = new TextView(this);
        expressionView.setText(expression);
        expressionView.setPadding(8, 8, 8, 8);
        expressionView.setGravity(Gravity.CENTER);
        expressionView.setBackgroundResource(R.drawable.table_row_border);

        row.addView(expressionView);

        TextView stackView = new TextView(this);
        stackView.setText(stackState);
        stackView.setPadding(16, 8, 16, 8);
        stackView.setGravity(Gravity.CENTER);
        stackView.setBackgroundResource(R.drawable.table_row_border);

        row.addView(stackView);

        TextView resultView = new TextView(this);
        resultView.setText(result);
        resultView.setPadding(16, 8, 16, 8);
        resultView.setBackgroundResource(R.drawable.table_row_border);
        resultView.setGravity(Gravity.CENTER);
        row.addView(resultView);

        // Add the row to the table layout
        conversionTable.addView(row);
    }
}
