package com.example.dsa_visualizer;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dsa_visualizer.HelperClasses.Queue;

public class Queue_array extends AppCompatActivity {

    private Queue queue;
    private LinearLayout queueView,indexView;
    private EditText inputValue;
    private TextView resultView, frontView, rearView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue_array);

        queue = new Queue(15); // Queue with 15 elements
        queueView = findViewById(R.id.queueView);
        indexView=findViewById(R.id.indexView);
        inputValue = findViewById(R.id.inputValue);
        resultView = findViewById(R.id.resultView);
        frontView = findViewById(R.id.frontView);
        rearView = findViewById(R.id.rearView);

        Button enqueueButton = findViewById(R.id.enqueueButton);
        Button dequeueButton = findViewById(R.id.dequeueButton);
        Button peekButton = findViewById(R.id.peekButton);
        Button resest=findViewById(R.id.resetQueue);

        enqueueButton.setOnClickListener(v -> {
            String input = inputValue.getText().toString();
            if (!input.isEmpty()) {
                int value = Integer.parseInt(input);
                queue.enqueue(value);
                updateQueueView();
                inputValue.setText("");
            }
        });

        dequeueButton.setOnClickListener(v -> {
            int dequeuedValue = queue.dequeue();
            resultView.setText("Dequeued: " + (dequeuedValue == -1 ? "Queue is empty" : dequeuedValue));
            updateQueueView();
        });

        peekButton.setOnClickListener(v -> {
            int frontValue = queue.peek();
            resultView.setText("Peek: " + (frontValue == -1 ? "Queue is empty" : frontValue));
        });
        resest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queue.resetQueue();
                updateQueueView();
            }
        });

        updateQueueView();
    }

    private void updateQueueView() {
        queueView.removeAllViews();
        indexView.removeAllViews();

        int[] queueArray = queue.getQueueArray();
        int front = queue.getFront();
        int rear = queue.getRear();
        int maxSize = queue.getMaxSize(); // Assuming you add a method to return max size

        for (int i = 0; i < maxSize; i++) {
            // Create TextView for queue items
            TextView queueItem = new TextView(this);
            queueItem.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            queueItem.setText(" " + (i >= front && i <= rear ? queueArray[i] : " ") + " ");
            queueItem.setTextSize(20);
            queueItem.setBackgroundResource(R.drawable.queue_item_background);
            queueItem.setPadding(10, 10, 10, 10);

            // Add queue items to the view
            queueView.addView(queueItem);

            // Create TextView for indexes
            TextView indexItem = new TextView(this);
            indexItem.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            indexItem.setText(String.valueOf(i));
            indexItem.setTextSize(14);
            indexItem.setTextColor(Color.BLUE);

            // Add index to the view
            indexView.addView(indexItem);
        }

        frontView.setText(String.valueOf(front));
        rearView.setText(String.valueOf(rear));
    }

}
