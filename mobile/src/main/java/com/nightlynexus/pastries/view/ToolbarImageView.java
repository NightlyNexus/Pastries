package com.nightlynexus.pastries.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

public final class ToolbarImageView extends ImageView {
  public ToolbarImageView(Context context) {
    super(context);
  }

  public ToolbarImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ToolbarImageView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public ToolbarImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    int size = getMeasuredHeight();
    setMeasuredDimension(size, size);
  }
}

