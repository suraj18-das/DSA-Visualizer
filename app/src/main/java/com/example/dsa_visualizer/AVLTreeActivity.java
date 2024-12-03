package com.example.dsa_visualizer;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import com.example.dsa_visualizer.HelperClasses.AVLTree;
import com.example.dsa_visualizer.HelperClasses.AVLTreeView;
import android.view.View;
import android.widget.Toast;

public class AVLTreeActivity extends AppCompatActivity {
    private AVLTree avlTree;
    private AVLTreeView avlTreeView;
    private EditText inputValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avltree);

        avlTree = new AVLTree();
        avlTreeView = findViewById(R.id.avlTreeView);
        inputValue = findViewById(R.id.etNodeValue);

        // Set the RotationListener to update the tree visualization
        avlTree.setRotationListener(new AVLTree.RotationListener() {
            @Override
            public void onRotationPerformed(String rotationType) {
                // No need to show toast, just update the tree visualization
                runOnUiThread(() -> {
                    Toast.makeText(AVLTreeActivity.this, "Rotation Performed: " + rotationType, Toast.LENGTH_SHORT).show();
                });
                updateTreeVisualization();
            }

            @Override
            public void onNodeFound(int value) {
                // Trigger node highlight during search
                updateTreeVisualization();
            }

            @Override
            public void onNodeNotFound(int value) {
                updateTreeVisualization();
            }
        });

        Button insertButton = findViewById(R.id.btnInsert);
        Button deleteButton = findViewById(R.id.btnDelete);
        Button findButton = findViewById(R.id.btnFind);

        insertButton.setOnClickListener(v -> {
            try {
                int value = Integer.parseInt(inputValue.getText().toString());
                avlTree.insert(value);
                updateTreeVisualization();
                inputValue.setText("");
            } catch (NumberFormatException e) {
                // Handle invalid input
            }
        });

        deleteButton.setOnClickListener(v -> {
            try {
                int value = Integer.parseInt(inputValue.getText().toString());
                avlTree.delete(value);
                updateTreeVisualization();
                inputValue.setText("");
            } catch (NumberFormatException e) {
                // Handle invalid input
            }
        });

        findButton.setOnClickListener(v -> {
            try {
                int value = Integer.parseInt(inputValue.getText().toString());
                avlTree.findNodeWithAnimation(value);  // Trigger search and highlight
                inputValue.setText("");
            } catch (NumberFormatException e) {
                // Handle invalid input
            }
        });
    }

    private void updateTreeVisualization() {
        avlTreeView.setRoot(convertToDrawableTree(avlTree.getRoot()));
    }


    private AVLTreeView.Node convertToDrawableTree(AVLTree.Node root) {
        if (root == null) return null;

        AVLTreeView.Node drawableNode = new AVLTreeView.Node(root.value);
        drawableNode.balanceFactor = getBalanceFactor(root);
        drawableNode.left = convertToDrawableTree(root.left);
        drawableNode.right = convertToDrawableTree(root.right);

        // Pass the highlighted node information to AVLTreeView
        if (avlTree.getHighlightedNode() != null && avlTree.getHighlightedNode().value == root.value) {
            drawableNode.isHighlighted = true;
        }

        return drawableNode;
    }

    private int getBalanceFactor(AVLTree.Node node) {
        return node == null ? 0 : (node.left != null ? node.left.height : 0) - (node.right != null ? node.right.height : 0);
    }
}
