package com.suresh.materialfun.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.suresh.materialfun.R;

public class FloatingButton extends View {

    //Drawing tools
    private Paint btnPaint;

    //Button attributes
    private int btnColor;
    private int btnDiameter;
    private RectF btnCircle;


    public FloatingButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray arr = context.getTheme().obtainStyledAttributes(attrs, R.styleable.FloatingButton, 0, 0);

        try {
            btnColor = arr.getColor(R.styleable.FloatingButton_buttonColor, Color.BLACK);
        } finally {
            arr.recycle();
        }

        init();
    }

    private void init() {
        btnPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        btnPaint.setColor(btnColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        int width = w - getPaddingLeft() - getPaddingRight();
        int height = h - getPaddingTop() - getPaddingBottom();

        btnDiameter = Math.min(width, height);
        btnCircle = new RectF(0, 0, btnDiameter, btnDiameter);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawOval(btnCircle, btnPaint);
    }

    public int getButtonColor() {
        return btnColor;
    }

    public void setButtonColorRes(int resId) {
        setButtonColor(getResources().getColor(resId));
    }


    public void setButtonColor(int btnColor) {
        this.btnColor = btnColor;
    }
}
