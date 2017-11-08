package com.felix.bottomnavygation;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by user on 07/11/2017.
 */

public class BadgeIndicator extends RelativeLayout {

    private TextView textView;

    private int backgroundColor;
    private int textColor;

    public BadgeIndicator(Context context, int backgroundColor, int textColor) {
        super(context);
        init(backgroundColor, textColor);
    }

    public BadgeIndicator(Context context, @Nullable AttributeSet attrs, int backgroundColor, int textColor) {
        super(context, attrs);
        init(backgroundColor, textColor);
    }

    private void init(int backgroundColor, int textColor) {
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;

        addComponent();
    }

    private void addComponent() {
        setGravity(Gravity.CENTER);
        setBackground(generateCircleDrawable(backgroundColor));

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(5,0,0,0);
        setLayoutParams(params);

        LayoutParams paramsText = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsText.setMargins(10, 5, 10, 5);

        this.textView = new TextView(getContext());
        this.textView.setLayoutParams(paramsText);
        this.textView.setTextColor(ContextCompat.getColor(getContext(), textColor));
        this.textView.setTextSize(8);
        this.textView.setGravity(Gravity.CENTER);

        show(false);
        addView(this.textView);
    }

    private Drawable generateCircleDrawable(int color) {
        ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
        drawable.getPaint().setColor(ContextCompat.getColor(getContext(), backgroundColor));
        return drawable;
    }

    // **** INTERACTIONS **** \\

    public void show(boolean isShow) {
        if (isShow) {
            setVisibility(VISIBLE);
        } else {
            setVisibility(GONE);
        }
    }

    public void updateCount(int value) {
        if (this.textView != null) {
            if (value <= 0) {
                show(false);
            } else {
                if (value > 100) {
                    value = 99;
                }
                this.textView.setText(value + "");
                show(true);
            }
        }
    }
}
