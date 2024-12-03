package com.example.dsa_visualizer.HelperClasses;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class AVLTreeView extends View {
    private Node root;
    private Paint paint = new Paint();

    public AVLTreeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setRoot(Node root) {
        this.root = root;
        invalidate(); // Redraw the tree
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (root != null) {
            drawTree(canvas, root, getWidth() / 2, 150, getWidth() / 4);
        }
    }

    private void drawTree(Canvas canvas, Node node, float x, float y, float xOffset) {
        // If the node is highlighted, draw a green border around it
        if (node.isHighlighted) {
            paint.setColor(Color.GREEN); // Set color for highlight
            paint.setStyle(Paint.Style.STROKE); // Set to stroke style for border
            paint.setStrokeWidth(5); // Set border width
            canvas.drawCircle(x, y, 65, paint); // Slightly larger radius for the border
        }

        // Draw the node circle
        paint.setColor(Color.parseColor("#ADD8E6")); // Light blue
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x, y, 60, paint);

        // Draw the node value
        paint.setColor(Color.BLACK);
        paint.setTextSize(30);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(String.valueOf(node.value), x, y + 10, paint);

        // Draw the balance factor
        paint.setColor(Color.RED);
        canvas.drawText("BF: " + node.balanceFactor, x, y + 40, paint);

        // Draw the left and right connections
        if (node.left != null) {
            paint.setColor(Color.GRAY);
            paint.setStrokeWidth(5);
            canvas.drawLine(x, y + 45, x - xOffset, y + 150, paint);
            drawTree(canvas, node.left, x - xOffset, y + 150, xOffset / 2);
        }
        if (node.right != null) {
            paint.setColor(Color.GRAY);
            paint.setStrokeWidth(5);
            canvas.drawLine(x, y + 45, x + xOffset, y + 150, paint);
            drawTree(canvas, node.right, x + xOffset, y + 150, xOffset / 2);
        }
    }

    // Node class for the visual representation
    public static class Node {
        int value;
        public Node left;
        public Node right;
        public int balanceFactor;
        public boolean isHighlighted;  // Track whether the node is highlighted

        public Node(int value) {
            this.value = value;
            this.left = null;
            this.right = null;
            this.balanceFactor = 0;
            this.isHighlighted = false;  // By default, nodes are not highlighted
        }
    }

    // Method to highlight a node
    public void highlightNode(Node node) {
        node.isHighlighted = true; // Set the highlighted flag to true for this node
        invalidate(); // Redraw the tree to reflect the changes
    }

    // Method to reset the highlight of all nodes
    public void resetHighlight() {
        resetHighlight(root); // Recursively reset highlights
        invalidate(); // Redraw the tree
    }

    // Helper method to reset highlight recursively
    private void resetHighlight(Node node) {
        if (node != null) {
            node.isHighlighted = false;
            resetHighlight(node.left);
            resetHighlight(node.right);
        }
    }
}
