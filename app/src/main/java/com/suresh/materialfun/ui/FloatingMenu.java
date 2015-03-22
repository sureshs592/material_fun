package com.suresh.materialfun.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.suresh.materialfun.R;

public class FloatingMenu extends ViewGroup {

    private FloatingButton mainBtn;

    private enum State { OPEN, CLOSED }

    private State menuState = State.CLOSED;

    public FloatingMenu(Context context, AttributeSet attrs) throws Exception {
        super(context, attrs);

        TypedArray arr = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.FloatingButton, 0, 0);

        try {
            int mainBtnId = arr.getInteger(R.styleable.FloatingMenu_fm_main_action_button, 0);
            if (mainBtnId != 0) {
                mainBtn = (FloatingButton) findViewById(mainBtnId);
            } else {
                throw new Exception("Could not find the main floating action button!");
            }
        } finally {
            arr.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
