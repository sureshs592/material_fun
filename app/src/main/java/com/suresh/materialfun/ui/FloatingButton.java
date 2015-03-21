package com.suresh.materialfun.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import com.suresh.materialfun.R;

public class FloatingButton extends View {

    private int btnColor;

    public FloatingButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray arr = context.getTheme().obtainStyledAttributes(attrs, R.styleable.FloatingButton, 0, 0);

        try {
            btnColor = arr.getColor(R.styleable.FloatingButton_buttonColor, Color.BLACK);
        } finally {
            arr.recycle();
        }
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
