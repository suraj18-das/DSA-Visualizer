package com.example.dsa_visualizer.HelperClasses;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class StackList {
    public class Node {
        int data;
        Node next;

        public Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node top;
    public StackList() {
        top = null;
    }

    // Push operation
    public void push(int data) {
        Node newNode = new Node(data);
        newNode.next = top;
        top = newNode;
    }

    // Pop operation
    public int pop() {
        if (top == null) {
            throw new IllegalStateException("Stack is empty");
        }
        int value = top.data;
        top = top.next;
        return value;
    }

    // Peek operation
    public int peek() {
        if (top == null) {
            throw new IllegalStateException("Stack is empty");
        }
        return top.data;
    }

    // Clear stack
    public void clear() {
        top = null;
    }

    public int getSize() {
        int count = 0;
        Node current = top;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }

    public void drawStack(Canvas canvas) {
        if (top == null) return;

        Node current = top;
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(35);

        // Calculate center position
        int canvasWidth = canvas.getWidth();
        int x = canvasWidth / 2 - 100; // Adjust for the width of the node
        int y = 100; // Starting Y position

        while (current != null) {
            // Draw the node's data and next pointer (visualized as two rectangles)
            paint.setColor(Color.GREEN); // Node color
            canvas.drawRect(x, y, x + 100, y + 80, paint); // Data part
            canvas.drawRect(x + 100, y, x + 180, y + 80, paint); // Next pointer part

            // Draw text for the data
            paint.setColor(Color.BLACK);
            canvas.drawText(String.valueOf(current.data), x + 30, y + 50, paint); // Data text

            // Draw an arrow to the next node or "NULL" if no next
            if (current.next != null) {
                paint.setColor(Color.BLACK);
                // Label for next pointer
                canvas.drawText("Next", x + 200, y + 50, paint);
                paint.setStrokeWidth(5); // Thicker line for arrow

                // Draw arrow connecting to the next node
                float startX = x + 160;
                float startY = y + 40;
                float endX = x + 160;
                float endY = y + 160;

                // Draw the arrow connecting to the next node
                canvas.drawLine(startX+120, startY, startX+200, startY, paint); // Vertical arrow
                canvas.drawLine(startX+200, startY, endX+200, endY-60, paint); // Vertical arrow
                canvas.drawLine(endX+200, endY-60, startX-50, endY-60, paint); // Vertical arrow
                canvas.drawLine(startX-50, endY-60,startX-50 , endY, paint); // Vertical arrow4

            } else {
                canvas.drawText("NULL", x + 200, y + 50, paint); // NULL for last node
            }

            // Move position for next node
            current = current.next;
            y += 160; // Adjust Y position for the next node
        }

        // Draw the head label for the top of the stack
        paint.setColor(Color.BLACK);
        canvas.drawText("TOP", x - 90, 150, paint); // Head pointing to top node
    }


}
