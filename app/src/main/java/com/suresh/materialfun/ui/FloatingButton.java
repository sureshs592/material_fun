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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.suresh.materialfun.R;

public class FloatingButton extends View {

    private final String TAG = "FloatingButton";

    //Drawing tools
    private Paint btnPaint;
    private Paint shadowPaint;

    //Button attributes
    private int btnColor;
    private int btnSize;
    private final float BTN_SIZE_DP = 56f;
    private RectF btnCircleRect;

    //Icon attributes
    private Drawable btnIcon;
    private int btnIconSize;
    private final float BTN_ICON_SIZE_DP = 24f;

    //Shadow attributes
    private RectF shadowCircleRect;


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
        //Calculating button size
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        btnSize = (int)((BTN_SIZE_DP * displayMetrics.density) + 0.5);
        btnIconSize = (int)((BTN_ICON_SIZE_DP * displayMetrics.density) + 0.5);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        int viewPadding = calculateViewPadding();
        int btnStart = viewPadding;
        int btnEnd = viewPadding + btnSize;
        btnCircleRect = new RectF(btnStart, btnStart, btnEnd, btnEnd);

        int positionOffset = 5, sizeOffset = 2;
        shadowCircleRect = new RectF(btnStart + sizeOffset, btnStart + positionOffset,
                btnEnd - sizeOffset, btnEnd + positionOffset);

        if (btnIcon != null) {
            int iconPadding = (int) (btnSize * 0.3);
            int iconStart = btnStart + iconPadding;
            int iconEnd = btnEnd - iconPadding;
            btnIcon.setBounds(iconStart, iconStart, iconEnd, iconEnd);
        }
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
        //Drawing shadow (if not in edit mode)
        if (!isInEditMode()) canvas.drawOval(shadowCircleRect, shadowPaint);

        //Drawing main button circle
        canvas.drawOval(btnCircleRect, btnPaint);

        //Drawing icon on top if available
        if (btnIcon != null) btnIcon.draw(canvas);
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
