package com.suresh.materialfun.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
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

    private enum Type { NORMAL, MINI }

    //Drawing tools
    private Paint btnPaint;
    private Paint shadowPaint;

    //Button attributes
    private int btnColor;
    private int btnSize;
    private RectF btnCircleRect;
    private Type btnType;

    //Icon attributes
    private Drawable icon;
    private int iconSize;

    //Shadow attributes
    private RectF shadowCircleRect;


    public FloatingButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray arr = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.FloatingButton, 0, 0);

        try {
            btnColor = arr.getColor(R.styleable.FloatingButton_fb_buttonColor, Color.BLACK);

            int typeIndex = arr.getInteger(R.styleable.FloatingButton_fb_button_type, 0);
            btnType = Type.values()[typeIndex];

            int iconRes = arr.getResourceId(R.styleable.FloatingButton_fb_icon, 0);
            if (iconRes != 0) icon = getResources().getDrawable(iconRes);
        } finally {
            arr.recycle();
        }

        init(context);
    }

    private void init(Context context) {
        //Setting layer type to SOFTWARE to make the blur mask filter work (for shadow)
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        setupPaints();

        setupDimensions(context);
    }

    private void setupPaints() {
        //Initialising button background paint
        btnPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        btnPaint.setColor(btnColor);

        //Initialising the shadow paint
        if (!isInEditMode()) {
            shadowPaint = new Paint(0);
            shadowPaint.setColor(Color.argb(255 , 120, 120, 120));
            shadowPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL));
        }
    }

    private void setupDimensions(Context context) {
        btnSize = (int) ((btnType == Type.NORMAL)
                ? context.getResources().getDimension(R.dimen.fb_button_size)
                : context.getResources().getDimension(R.dimen.fb_mini_button_size));

        iconSize = (int) ((btnType == Type.NORMAL)
                ? context.getResources().getDimension(R.dimen.fb_icon_size)
                : context.getResources().getDimension(R.dimen.fb_mini_icon_size));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int totalPadding = calculateViewPadding() * 2;
        setMeasuredDimension(btnSize + totalPadding, btnSize + totalPadding);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        int canvasSize = Math.min(w, h);
        int btnStart = (int) (canvasSize * 0.2);
        int btnEnd = btnStart + (int) (canvasSize * 0.6);
        btnCircleRect = new RectF(btnStart, btnStart, btnEnd, btnEnd);

        int positionOffset = 5, sizeOffset = 2;
        shadowCircleRect = new RectF(btnStart + sizeOffset, btnStart + positionOffset,
                btnEnd - sizeOffset, btnEnd + positionOffset);

        if (icon != null) {
            int midPoint = canvasSize / 2;
            int iconStart = midPoint - (iconSize / 2);
            int iconEnd = iconStart + iconSize;
            icon.setBounds(iconStart, iconStart, iconEnd, iconEnd);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //Drawing shadow (if not in edit mode)
        if (!isInEditMode()) canvas.drawOval(shadowCircleRect, shadowPaint);

        //Drawing main button circle
        canvas.drawOval(btnCircleRect, btnPaint);

        //Drawing icon on top if available
        if (icon != null) icon.draw(canvas);
    }

    private int calculateViewPadding() {
        return (int) (btnSize * 0.2);
    }
}
