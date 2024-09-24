package com.example.dsa_visualizer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

public class CustomView extends View {
    private List<Integer> array;
    private int lowIndex = -1;
    private int highIndex = -1;
    private final Paint paint = new Paint();

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setArray(List<Integer> array) {
        this.array = array;
        invalidate(); // Redraw the view
    }

    public void setRange(int lowIndex, int highIndex) {
        this.lowIndex = lowIndex;
        this.highIndex = highIndex;
        invalidate(); // Redraw the view
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (array == null) return;

        int width = getWidth();
        int height = getHeight();
        int barWidth = width / array.size();

        for (int i = 0; i < array.size(); i++) {
            int value = array.get(i);
            paint.setColor(i == lowIndex || i == highIndex ? Color.RED : Color.BLUE);
            canvas.drawRect(i * barWidth, height - value * 10, (i + 1) * barWidth, height, paint);
        }
    }
}

