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

public class infixToprefix extends AppCompatActivity {

    private EditText infixInput;
    private TextView prefixOutput;
    private Button convertButton;
    private TableLayout conversionTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infix_toprefix);
        infixInput = findViewById(R.id.infixInput);
        prefixOutput = findViewById(R.id.prefixOutput);
        convertButton = findViewById(R.id.convertButton);
        conversionTable = findViewById(R.id.conversionTable);

        convertButton.setOnClickListener(v -> {
            String infix = infixInput.getText().toString().trim();
            if (TextUtils.isEmpty(infix)) {
                Toast.makeText(this, "Please enter an infix expression", Toast.LENGTH_SHORT).show();
                return;
            }
            // Clear any previous conversion steps
            clearTableRows();

            // Convert to prefix and display the steps
            String prefix = convertInfixToPrefix(infix);
            prefixOutput.setText(prefix);
        });
    }

    // Helper method to clear previous table rows
    private void clearTableRows() {
        int childCount = conversionTable.getChildCount();
        if (childCount > 1) {
            conversionTable.removeViews(1, childCount - 1); // Keep the header
        }
    }

    // Method to convert infix expression to prefix and show steps
    private String convertInfixToPrefix(String infix) {
        // Step 1: Reverse the infix expression
        StringBuilder reversedInfix = new StringBuilder(infix).reverse();
        for (int i = 0; i < reversedInfix.length(); i++) {
            if (reversedInfix.charAt(i) == '(') {
                reversedInfix.setCharAt(i, ')');
            } else if (reversedInfix.charAt(i) == ')') {
                reversedInfix.setCharAt(i, '(');
            }
        }

        // Step 2: Convert the reversed infix to postfix
        String reversedPostfix = convertInfixToPostfix(reversedInfix.toString());

        // Step 3: Reverse the postfix expression to get prefix
        return new StringBuilder(reversedPostfix).reverse().toString();
    }

    // Reuse the same infix-to-postfix method
    private String convertInfixToPostfix(String infix) {
        Stack<Character> stack = new Stack<>();
        StringBuilder postfix = new StringBuilder();
        int stepCount = 0;

        for (char ch : infix.toCharArray()) {
            if (Character.isLetterOrDigit(ch)) {
                postfix.append(ch);
                addRowToTable(stepCount++, String.valueOf(ch), stack.toString(), postfix.toString());
            } else if (ch == '(') {
                stack.push(ch);
                addRowToTable(stepCount++, String.valueOf(ch), stack.toString(), postfix.toString());
            } else if (ch == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.pop());
                    addRowToTable(stepCount++, ")", stack.toString(), postfix.toString());
                }
                if (!stack.isEmpty() && stack.peek() != '(')
                    return "Invalid Expression"; // Invalid case
                else
                    stack.pop();
            } else { // operator encountered
                while (!stack.isEmpty() && precedence(ch) <= precedence(stack.peek())) {
                    postfix.append(stack.pop());
                    addRowToTable(stepCount++, String.valueOf(ch), stack.toString(), postfix.toString());
                }
                stack.push(ch);
                addRowToTable(stepCount++, String.valueOf(ch), stack.toString(), postfix.toString());
            }
        }

        while (!stack.isEmpty()) {
            postfix.append(stack.pop());
            addRowToTable(stepCount++, "", stack.toString(), postfix.toString());
        }

        return postfix.toString();
    }

    // Helper method to add rows dynamically to the table
    private void addRowToTable(int step, String expression, String stackState, String prefix) {
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

        TextView prefixView = new TextView(this);
        prefixView.setText(prefix);
        prefixView.setPadding(16, 8, 16, 8);
        prefixView.setGravity(Gravity.CENTER);
        prefixView.setBackgroundResource(R.drawable.table_row_border);
        row.addView(prefixView);

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