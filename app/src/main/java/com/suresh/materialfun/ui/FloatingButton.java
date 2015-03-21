package com.suresh.materialfun.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
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
    private int btnSize;
    private final float BTN_SIZE_DP = 56f;


    public FloatingButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray arr = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.FloatingButton, 0, 0);

        try {
            btnColor = arr.getColor(R.styleable.FloatingButton_fb_buttonColor, Color.BLACK);
            int iconRes = arr.getResourceId(R.styleable.FloatingButton_fb_icon, 0);
            if (iconRes != 0) btnIcon = getResources().getDrawable(iconRes);
        } finally {
            arr.recycle();
        }

        init(context);
    }

    private void init(Context context) {
        //Initialising button background paint
        btnPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        btnPaint.setColor(btnColor);

        //Calculating button size
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        btnSize = (int)((BTN_SIZE_DP * displayMetrics.density) + 0.5);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        int viewPadding = calculateViewPadding();
        int btnStart = viewPadding;
        int btnEnd = viewPadding + btnSize;
        circleRect = new RectF(btnStart, btnStart, btnEnd, btnEnd);

        int iconPadding = (int) (btnSize * 0.3);
        int iconStart = btnStart + iconPadding;
        int iconEnd = btnEnd - iconPadding;
        btnIcon.setBounds(iconStart, iconStart, iconEnd, iconEnd);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int totalPadding = calculateViewPadding() * 2;
        Log.v(TAG, "total padding = " + totalPadding);
        setMeasuredDimension(btnSize + totalPadding, btnSize + totalPadding);
        Log.v(TAG, "set dimension = " + (btnSize + totalPadding));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawOval(circleRect, btnPaint);
        btnIcon.draw(canvas);
    }

    private int calculateViewPadding() {
        return (int) (btnSize * 0.2);
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
