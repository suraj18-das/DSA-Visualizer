package com.example.dsa_visualizer.HelperClasses;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class QueueCanvas extends View {
    private QueueList queue;
    private Paint paint;

    private int nodeWidth = 150;  // Width of each node
    private int nodeHeight = 100;  // Height of each node
    private int spaceBetweenNodes = 50;  // Space between two nodes

    public QueueCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }

    public void setQueue(QueueList queue) {
        this.queue = queue;
        requestLayout();  // Triggers a new layout pass
        invalidate();     // Forces a redraw
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int totalWidth = getTotalWidth();  // Calculate total width needed based on queue size
        int totalHeight = nodeHeight + getPaddingTop() + getPaddingBottom() + 100;  // Added extra space for pointers
        setMeasuredDimension(totalWidth, totalHeight);  // Set the width and height
    }

    private int getTotalWidth() {
        if (queue == null || queue.front == null) {
            return getPaddingLeft() + getPaddingRight();  // Minimum width when queue is empty
        }
        int nodeCount = getNodeCount();
        return getPaddingLeft() + getPaddingRight() + (nodeCount * (nodeWidth + spaceBetweenNodes));
    }

    private int getNodeCount() {
        int count = 0;
        QueueList.Node currentNode = queue.front;
        while (currentNode != null) {
            count++;
            currentNode = currentNode.next;
        }
        return count;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (queue == null || queue.front == null) {
            return;  // Do nothing if the queue is empty
        }

        int x = getPaddingLeft() + 50;  // Starting X position for nodes
        int y = getPaddingTop() + 150;  // Fixed Y position for nodes (pushed down for pointers)

        QueueList.Node currentNode = queue.front;
        int nodeIndex = 0;

        while (currentNode != null) {
            // Draw the rectangle for the node
            paint.setColor(Color.LTGRAY);
            canvas.drawRect(x, y, x + nodeWidth, y + nodeHeight, paint);

            // Draw the data inside the node
            paint.setColor(Color.BLACK);
            paint.setTextSize(40);
            canvas.drawText(String.valueOf(currentNode.data), x + (nodeWidth / 2) - 20, y + (nodeHeight / 2) + 15, paint);

            // Draw an arrow pointing to the next node
            if (currentNode.next != null) {
                paint.setColor(Color.BLACK);
                canvas.drawLine(x + nodeWidth, y + nodeHeight / 2, x + nodeWidth + spaceBetweenNodes, y + nodeHeight / 2, paint);
            }

            // Check if the current node is the front or rear and draw pointers
            if (nodeIndex == 0) {  // Front pointer for the first node
                paint.setColor(Color.RED);
                paint.setTextSize(50);
                canvas.drawText("Front", x + (nodeWidth / 4), y - 100, paint);  // Label above the node
                // Draw an arrow pointing to the front node
                canvas.drawLine(x + (nodeWidth / 2), y - 50, x + (nodeWidth / 2), y, paint);
            }

            if (currentNode.next == null) {  // Rear pointer for the last node
                paint.setColor(Color.BLUE);
                paint.setTextSize(50);
                canvas.drawText("Rear",x + (nodeWidth / 4), y -100, paint);  // Label below the node
                // Draw an arrow pointing to the rear node
                canvas.drawLine(x + (nodeWidth / 2), y + nodeHeight, x + (nodeWidth / 2), y + nodeHeight + 50, paint);
            }

            // Move to the next node position
            x += nodeWidth + spaceBetweenNodes;
            currentNode = currentNode.next;
            nodeIndex++;
        }
    }
}
