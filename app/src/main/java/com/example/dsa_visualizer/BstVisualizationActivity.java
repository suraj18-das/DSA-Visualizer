package com.example.dsa_visualizer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BstVisualizationActivity extends AppCompatActivity {

    private EditText etNodeValue;
    private FrameLayout treeArea;
    private BST bst;
    private TreeView treeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bst_visualization);
        etNodeValue = findViewById(R.id.etNodeValue);
        treeArea = findViewById(R.id.treeArea);
        Button btnInsert = findViewById(R.id.btnInsert);
        Button btnDelete = findViewById(R.id.btnDelete);

        bst = new BST();
        treeView = new TreeView(this, bst);
        treeArea.addView(treeView);

        btnInsert.setOnClickListener(v -> {
            String value = etNodeValue.getText().toString();
            if (!value.isEmpty()) {
                int nodeValue = Integer.parseInt(value);
                bst.insert(nodeValue);
                treeView.invalidate(); // Redraw the tree
                etNodeValue.setText("");
            }
        });

        btnDelete.setOnClickListener(v -> {
            String value = etNodeValue.getText().toString();
            if (!value.isEmpty()) {
                int nodeValue = Integer.parseInt(value);
                bst.delete(nodeValue);
                treeView.invalidate(); // Redraw the tree
                etNodeValue.setText("");
            }
        });
    }

    // BST Implementation
    static class BST {
        private Node root;

        static class Node {
            int value;
            Node left, right;

            Node(int value) {
                this.value = value;
            }
        }

        void insert(int value) {
            root = insertRec(root, value);
        }

        private Node insertRec(Node root, int value) {
            if (root == null) return new Node(value);
            if (value < root.value) root.left = insertRec(root.left, value);
            else if (value > root.value) root.right = insertRec(root.right, value);
            return root;
        }

        void delete(int value) {
            root = deleteRec(root, value);
        }

        private Node deleteRec(Node root, int value) {
            if (root == null) return null;

            if (value < root.value) root.left = deleteRec(root.left, value);
            else if (value > root.value) root.right = deleteRec(root.right, value);
            else {
                if (root.left == null) return root.right;
                if (root.right == null) return root.left;

                root.value = findMin(root.right);
                root.right = deleteRec(root.right, root.value);
            }
            return root;
        }

        private int findMin(Node root) {
            int min = root.value;
            while (root.left != null) {
                min = root.left.value;
                root = root.left;
            }
            return min;
        }

        Node getRoot() {
            return root;
        }
    }

    // Tree Visualization
    static class TreeView extends View {
        private final Paint paint;
        private final BST bst;

        TreeView(BstVisualizationActivity context, BST bst) {
            super(context);
            this.bst = bst;
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setTextSize(40);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (bst.getRoot() != null)
                drawTree(canvas, bst.getRoot(), getWidth() / 2, 100, getWidth() / 4);
        }

        private void drawTree(Canvas canvas, BST.Node node, float x, float y, float xOffset) {
            // Set paint properties for the circle
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.parseColor("#ADD8E6")); // Light blue for the circle
            canvas.drawCircle(x, y, 50, paint); // Draw the node circle

            // Set paint properties for the text
            paint.setColor(Color.BLACK); // Text color
            paint.setTextSize(40);
            paint.setTextAlign(Paint.Align.CENTER); // Center the text inside the circle
            canvas.drawText(String.valueOf(node.value), x, y + 15, paint); // Draw the node's value

            // Recursively draw left and right children
            if (node.left != null) {
                paint.setColor(Color.GRAY); // Line color
                paint.setStrokeWidth(5); // Line thickness
                canvas.drawLine(x, y+30, x - xOffset, y + 150, paint); // Draw line to the left child
                drawTree(canvas, node.left, x - xOffset, y + 150, xOffset / 2); // Recurse for left child
            }

            if (node.right != null) {
                paint.setColor(Color.GRAY); // Line color
                paint.setStrokeWidth(5); // Line thickness
                canvas.drawLine(x, y+30, x + xOffset, y + 150, paint); // Draw line to the right child
                drawTree(canvas, node.right, x + xOffset, y + 150, xOffset / 2); // Recurse for right child
            }
        }

    }
}
