package com.example.dsa_visualizer;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dsa_visualizer.HelperClasses.QueueCanvas;
import com.example.dsa_visualizer.HelperClasses.QueueList;

public class Queue_list extends AppCompatActivity {
    private QueueList queue;
    private QueueCanvas queueCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue_list);

        queue = new QueueList();
        queueCanvas = findViewById(R.id.queueCanvas);

        findViewById(R.id.enqueueButton).setOnClickListener(v -> {
            String input = ((EditText) findViewById(R.id.inputData)).getText().toString();
            if (!input.isEmpty()) {
                int value = Integer.parseInt(input);
                ((EditText) findViewById(R.id.inputData)).setText("");
                queue.enqueue(value);
                queueCanvas.setQueue(queue);
                queueCanvas.invalidate(); // Redraw the canvas
            }
        });

        findViewById(R.id.dequeueButton).setOnClickListener(v -> {
            queue.dequeue();
            queueCanvas.setQueue(queue);
            queueCanvas.invalidate(); // Redraw the canvas
        });

        findViewById(R.id.clearQueueButton).setOnClickListener(v -> {
            queue.clear();
            queueCanvas.setQueue(queue);
            queueCanvas.invalidate(); // Redraw the canvas
        });
    }
}
