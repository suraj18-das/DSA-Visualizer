package com.example.dsa_visualizer.HelperClasses;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

public class BarView extends View {
    private List<Bar> bars;
    private Paint paint;

    public BarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }

    public void setBars(List<Bar> bars) {
        this.bars = bars;
        invalidate();  // Redraw the view
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (bars == null || bars.isEmpty()) return;

        int width = getWidth() / bars.size();
        for (int i = 0; i < bars.size(); i++) {
            Bar bar = bars.get(i);
            paint.setColor(Color.parseColor(bar.color));
            int barHeight = bar.height * 10;
            canvas.drawRect(i * width, getHeight() - barHeight, (i + 1) * width, getHeight(), paint);
        }
    }
}
