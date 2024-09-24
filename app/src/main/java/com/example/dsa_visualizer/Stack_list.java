package com.example.dsa_visualizer;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dsa_visualizer.HelperClasses.StackCanvas;
import com.example.dsa_visualizer.HelperClasses.StackList;

public class Stack_list extends AppCompatActivity {
    private StackList stack;
    private StackCanvas stackCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stack_list);

        stack = new StackList();
        stackCanvas = findViewById(R.id.stackCanvas);

        findViewById(R.id.pushButton).setOnClickListener(v -> {
            String input = ((EditText) findViewById(R.id.inputData)).getText().toString();
            if (!input.isEmpty()) {
                int value = Integer.parseInt(input);
                ((EditText) findViewById(R.id.inputData)).setText("");
                stack.push(value);
                stackCanvas.setStack(stack);
                stackCanvas.invalidate(); // Redraw the canvas
            }
        });

        findViewById(R.id.popButton).setOnClickListener(v -> {
            stack.pop();
            stackCanvas.setStack(stack);
            stackCanvas.invalidate(); // Redraw the canvas
        });

        findViewById(R.id.clearStackButton).setOnClickListener(v -> {
            stack.clear();
            stackCanvas.invalidate(); // Redraw the canvas
        });

    }

    // Custom View class to draw the stac
}