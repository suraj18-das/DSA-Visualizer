package com.example.dsa_visualizer.HelperClasses;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class StackCanvas extends View {
    private StackList stack;

    public StackCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setStack(StackList stack) {
        this.stack = stack;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        if (stack != null) {
//            stack.drawStack(canvas);
//        }

        if (stack != null) {
            int nodeHeight = 160; // Height per node (matches the Y offset)
            int nodeCount = stack.getSize(); // Add a method to get the size of the stack

            // Set the minimum height dynamically based on the number of nodes
            this.setMinimumHeight(nodeHeight * nodeCount);

            stack.drawStack(canvas); // Draw the stack on the canvas
        }
    }
}