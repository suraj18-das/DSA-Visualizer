package com.example.dsa_visualizer.HelperClasses;


public class QueueList {

    public Node front, rear;

    // Nested class to represent a node
    public static class Node {
        int data;
        Node next;

        public Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    public QueueList() {
        this.front = this.rear = null;
    }
    public void clear() {
        rear=null;
        front=null;
    }

    // Method to add an element to the queue
    public void enqueue(int data) {
        Node newNode = new Node(data);

        if (rear == null) {
            front = rear = newNode;
            return;
        }

        rear.next = newNode;
        rear = newNode;
    }

    // Method to remove an element from the queue
    public void dequeue() {
        if (front == null) {
            return;
        }

        front = front.next;

        if (front == null) {
            rear = null;
        }
    }
}
