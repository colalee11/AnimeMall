package com.dlsd.property.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class BannerImageView extends ImageView {


    float width, height;

    public BannerImageView(Context context) {
        this(context, null);
    }

    public BannerImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (width > 24 && height > 24) {
            Path path = new Path();
            path.moveTo(24, 0);
            path.lineTo(width - 24, 0);
            path.quadTo(width, 0, width, 24);
            path.lineTo(width, height - 24);
            path.quadTo(width, height, width - 24, height);
            path.lineTo(24, height);
            path.quadTo(0, height, 0, height - 24);
            path.lineTo(0, 24);
            path.quadTo(0, 0, 24, 0);
            canvas.clipPath(path);
        }

        super.onDraw(canvas);
    }
}
