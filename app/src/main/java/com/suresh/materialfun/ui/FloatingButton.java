package com.suresh.materialfun.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.suresh.materialfun.R;

public class FloatingButton extends View {

    private final String TAG = "FloatingButton";

    //Drawing tools
    private Paint btnPaint;

    //Button attributes
    private int btnColor;
    private Drawable btnIcon;
    private RectF circleRect;


    public FloatingButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray arr = context.getTheme().obtainStyledAttributes(attrs, R.styleable.FloatingButton, 0, 0);

        try {
            btnColor = arr.getColor(R.styleable.FloatingButton_fb_buttonColor, Color.BLACK);
            int iconRes = arr.getResourceId(R.styleable.FloatingButton_fb_icon, 0);
            if (iconRes != 0) btnIcon = getResources().getDrawable(iconRes);
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

        int diameter = Math.min(width, height);
        circleRect = new RectF(0, 0, diameter, diameter);
        btnIcon.setBounds(0, 0, diameter, diameter);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawOval(circleRect, btnPaint);
        btnIcon.draw(canvas);
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
