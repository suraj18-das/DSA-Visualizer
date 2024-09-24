package com.example.dsa_visualizer;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Stack;

public class prefixTopostfix extends AppCompatActivity {

    private EditText prefixInput;
    private TextView postfixOutput;
    private Button convertButton;
    private TableLayout conversionTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefix_topostfix);
        prefixInput = findViewById(R.id.prefixInput);
        postfixOutput = findViewById(R.id.postfixOutput);
        convertButton = findViewById(R.id.convertButton);
        conversionTable = findViewById(R.id.conversionTable);

        convertButton.setOnClickListener(v -> {
            String prefix = prefixInput.getText().toString().trim();
            if (TextUtils.isEmpty(prefix)) {
                Toast.makeText(this, "Please enter an infix expression", Toast.LENGTH_SHORT).show();
                return;
            }
            // Clear any previous conversion steps
            clearTableRows();

            // Convert to postfix and display the steps
            String postfix = convertPrefixToPostfix(reversePrefix(prefix));
            postfixOutput.setText(postfix);
        });

    }
    private void clearTableRows() {
        int childCount = conversionTable.getChildCount();
        if (childCount > 1) {
            conversionTable.removeViews(1, childCount - 1); // Keep the header
        }
    }

    private String reversePrefix(String prefix) {
        StringBuilder sb = new StringBuilder();
        for (int i = prefix.length() - 1; i >= 0; i--) {
            sb.append(prefix.charAt(i));
        }
        return sb.toString();
    }

    // Method to convert infix expression to postfix (using the reversed prefix)
    private String convertPrefixToPostfix(String prefix) {
        Stack<Character> stack = new Stack<>();
        StringBuilder postfix = new StringBuilder();
        int stepCount = 0;

        for (char ch : prefix.toCharArray()) {
            if (Character.isLetterOrDigit(ch)) {
                // If the character is an operand, push it to the stack
                stack.push(ch);
                addRowToTable(stepCount++, String.valueOf(ch), stack.toString(), "");
            } else { // an operator is encountered
                while (!stack.isEmpty()) {
                    postfix.append(stack.pop());
                    addRowToTable(stepCount++, String.valueOf(ch), stack.toString(), postfix.toString());
                }
                stack.push(ch);
                addRowToTable(stepCount++, String.valueOf(ch), stack.toString(), postfix.toString());
            }
        }

        // Pop all the operands from the stack (already reversed order)
        while (!stack.isEmpty()) {
            postfix.append(stack.pop());
            addRowToTable(stepCount++, "", stack.toString(), postfix.toString());
        }

        return postfix.toString();
    }


    private void addRowToTable(int step, String expression, String stackState, String postfix) {
        TableRow row = new TableRow(this);

        // Set row background to include border
//        row.setBackgroundResource(R.drawable.table_row_border);

        // Create TextViews for each column
        TextView stepView = new TextView(this);
        stepView.setText(String.valueOf(step));
        stepView.setPadding(16, 8, 16, 8);  // Padding for better appearance
        stepView.setGravity(Gravity.CENTER); // Center alignment
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

        TextView postfixView = new TextView(this);
        postfixView.setText(postfix);
        postfixView.setPadding(16, 8, 16, 8);
        postfixView.setGravity(Gravity.CENTER);
        postfixView.setBackgroundResource(R.drawable.table_row_border);
        row.addView(postfixView);

        // Add the row to the table
        conversionTable.addView(row);
    }


    // Define operator precedence
    private int precedence(char ch) {
        switch (ch) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
        }
        return -1;
    }
}