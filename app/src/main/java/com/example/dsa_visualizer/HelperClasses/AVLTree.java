package com.example.dsa_visualizer.HelperClasses;

public class AVLTree {
    private Node root;
    private Node highlightedNode;
    private RotationListener rotationListener;

    public void setRotationListener(RotationListener listener) {
        this.rotationListener = listener;
    }

    // Insert method (Basic AVL insert logic goes here)

    public void insert(int value) {
        root = insert(root, value);
    }
    // In AVLTree.java
    public Node getRoot() {
        return root;
    }


    private Node insert(Node node, int value) {
        if (node == null) {
            return new Node(value);
        }

        if (value < node.value) {
            node.left = insert(node.left, value);
        } else if (value > node.value) {
            node.right = insert(node.right, value);
        } else {
            return node; // Duplicate value, do nothing
        }

        // Balance the tree (rotation logic)
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        return rebalance(node,value);

    }

    // Deletion method (Basic AVL delete logic goes here)
    public void delete(int value) {
        root = delete(root, value);
    }

    private Node delete(Node node, int value) {
        if (node == null) {
            return node;
        }

        if (value < node.value) {
            node.left = delete(node.left, value);
        } else if (value > node.value) {
            node.right = delete(node.right, value);
        } else {
            // Node to be deleted is found
            if (node.left == null || node.right == null) {
                node = (node.left != null) ? node.left : node.right;
            } else {
                Node temp = findMin(node.right);
                node.value = temp.value;
                node.right = delete(node.right, temp.value);
            }
        }

        if (node == null) {
            return node;
        }

        // Balance the tree
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        return rebalance(node,value);
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // Find method (with animation and highlighting)
    public void findNodeWithAnimation(int value) {
        animateNodeTraversal(root, value);
    }
    private void animateNodeTraversal(Node currentNode, int value) {
        if (currentNode == null) {
            if (rotationListener != null) {
                rotationListener.onNodeNotFound(value);  // Node not found callback
            }
            return;
        }
        // Highlight current node
        setHighlightedNode(currentNode);  // This function will highlight the current node
        highlightedNode = currentNode;
        if (rotationListener != null) {
            rotationListener.onNodeFound(currentNode.value);  // Notify the activity to highlight the node

        }
        // Delay to visualize the search step-by-step

        if (currentNode.value == value) {
            if (rotationListener != null) {
                rotationListener.onNodeFound(value);  // Notify node found
            }
            return;
        } else if (value < currentNode.value) {
            animateNodeTraversal(currentNode.left, value);
        } else {
            animateNodeTraversal(currentNode.right, value);
        }
    }


    private void setHighlightedNode(Node node) {
        this.highlightedNode = node;
        if (rotationListener != null) {
            rotationListener.onNodeFound(node.value);  // Optionally notify that node is found
        }
    }

    public Node getHighlightedNode() {
        return highlightedNode;
    }

    // Balance the node after insertion or deletion
    private Node rebalance(Node node, int value) {
        int balance = getBalance(node);

        // Left-Left case
        if (balance > 1 && getBalance(node.left) >= 0) {
            if (rotationListener != null) rotationListener.onRotationPerformed("Right Rotation (LL Case)");
            return rotateRight(node);
        }

        // Left-Right case
        if (balance > 1 && getBalance(node.left) < 0) {
            if (rotationListener != null) rotationListener.onRotationPerformed("Left-Right Rotation (LR Case)");
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // Right-Right case
        if (balance < -1 && getBalance(node.right) <= 0) {
            if (rotationListener != null) rotationListener.onRotationPerformed("Left Rotation (RR Case)");
            return rotateLeft(node);
        }

        // Right-Left case
        if (balance < -1 && getBalance(node.right) > 0) {
            if (rotationListener != null) rotationListener.onRotationPerformed("Right-Left Rotation (RL Case)");
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }
    // Rotation methods (left and right rotation)

    private Node rotateLeft(Node node) {
        Node newRoot = node.right;
        node.right = newRoot.left;
        newRoot.left = node;
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        newRoot.height = Math.max(height(newRoot.left), height(newRoot.right)) + 1;
        return newRoot;
    }

    private Node rotateRight(Node node) {
        Node newRoot = node.left;
        node.left = newRoot.right;
        newRoot.right = node;
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        newRoot.height = Math.max(height(newRoot.left), height(newRoot.right)) + 1;
        return newRoot;
    }

    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    private int getBalance(Node node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    // Node class for the AVL tree
    public static class Node {
        public int value;
        public Node left;
        public Node right;
        public int height;
        public Node(int value) {
            this.value = value;
            this.left = this.right = null;
            this.height = 1;
        }
    }

    // RotationListener interface to communicate with the Activity
    public interface RotationListener {
        void onRotationPerformed(String rotationType);
        void onNodeFound(int value);
        void onNodeNotFound(int value);
    }
}
