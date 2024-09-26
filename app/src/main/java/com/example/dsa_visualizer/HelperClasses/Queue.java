package com.example.dsa_visualizer.HelperClasses;

public class Queue {
    private int maxSize;
    private int[] queueArray;
    private int front;
    private int rear;
    private int currentSize;

    public Queue(int size) {
        maxSize = size;
        queueArray = new int[maxSize];
        front = 0;
        rear = -1;
        currentSize = 0;
    }
    public int getMaxSize(){
        return maxSize;
    }

    public boolean isFull() {
        return currentSize == maxSize;
    }

    public boolean isEmpty() {
        return currentSize == 0;
    }

    public void enqueue(int value) {
        if (!isFull()) {
            if (rear == maxSize - 1) {
                rear = -1; // wrap-around if needed
            }
            queueArray[++rear] = value;
            currentSize++;
        }
    }

    public int dequeue() {
        if (!isEmpty()) {
            int dequeuedValue = queueArray[front++];
            if (front == maxSize) {
                front = 0; // wrap-around if needed
            }
            currentSize--;
            return dequeuedValue;
        }
        return -1; // Indicates the queue is empty
    }

    public int peek() {
        if (!isEmpty()) {
            return queueArray[front];
        }
        return -1; // Indicates the queue is empty
    }

    public int[] getQueueArray() {
        return queueArray;
    }

    public int getFront() {
        return front;
    }

    public int getRear() {
        return rear;
    }
}
