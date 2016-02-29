package com.nightlynexus.pastries.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.nightlynexus.pastries.R;

public final class ToolbarLayout extends LinearLayout {
  public interface OnNavigationClickListener {
    void onNavigationClick();
  }

  public interface OnActionClickListener {
    void onActionClick();
  }

  private final ImageView navigation;
  private final TextView title;
  private final boolean extendToInsets;
  private WindowInsets lastApplied;
  private Toast hint;

  public ToolbarLayout(Context context) {
    super(context);
    AttributeSet attrs = null;
    int defStyleAttr = 0;
    int defStyleRes = R.style.DefaultToolbarLayout;
    TypedArray a =
        context.obtainStyledAttributes(attrs, R.styleable.toolbar_ToolbarLayout, defStyleAttr,
            defStyleRes);
    extendToInsets = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && a.getBoolean(
        R.styleable.toolbar_ToolbarLayout_toolbar_extendToInsets, false);
    a.recycle();
    if (extendToInsets) {
      lastApplied = null;
      setFitsSystemWindows(true);
      setSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_STABLE | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
    LayoutInflater.from(context).inflate(R.layout.children_toolbar_layout, this, true);
    navigation = (ImageView) getChildAt(0);
    title = (TextView) getChildAt(1);
    hint = null;
  }

  public ToolbarLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
    int defStyleAttr = 0;
    int defStyleRes = R.style.DefaultToolbarLayout;
    TypedArray a =
        context.obtainStyledAttributes(attrs, R.styleable.toolbar_ToolbarLayout, defStyleAttr,
            defStyleRes);
    extendToInsets = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && a.getBoolean(
        R.styleable.toolbar_ToolbarLayout_toolbar_extendToInsets, false);
    a.recycle();
    if (extendToInsets) {
      lastApplied = null;
      setFitsSystemWindows(true);
      setSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_STABLE | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
    LayoutInflater.from(context).inflate(R.layout.children_toolbar_layout, this, true);
    navigation = (ImageView) getChildAt(0);
    title = (TextView) getChildAt(1);
    hint = null;
  }

  public ToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    int defStyleRes = R.style.DefaultToolbarLayout;
    TypedArray a =
        context.obtainStyledAttributes(attrs, R.styleable.toolbar_ToolbarLayout, defStyleAttr,
            defStyleRes);
    extendToInsets = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && a.getBoolean(
        R.styleable.toolbar_ToolbarLayout_toolbar_extendToInsets, false);
    a.recycle();
    if (extendToInsets) {
      lastApplied = null;
      setFitsSystemWindows(true);
      setSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_STABLE | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
    LayoutInflater.from(context).inflate(R.layout.children_toolbar_layout, this, true);
    navigation = (ImageView) getChildAt(0);
    title = (TextView) getChildAt(1);
    hint = null;
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public ToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    TypedArray a =
        context.obtainStyledAttributes(attrs, R.styleable.toolbar_ToolbarLayout, defStyleAttr,
            defStyleRes);
    extendToInsets = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && a.getBoolean(
        R.styleable.toolbar_ToolbarLayout_toolbar_extendToInsets, false);
    a.recycle();
    if (extendToInsets) {
      lastApplied = null;
      setFitsSystemWindows(true);
      setSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_STABLE | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
    LayoutInflater.from(context).inflate(R.layout.children_toolbar_layout, this, true);
    navigation = (ImageView) getChildAt(0);
    title = (TextView) getChildAt(1);
    hint = null;
  }

  public void showNavigation(Drawable navigationIcon, CharSequence contentDescription,
      final OnNavigationClickListener listener) {
    if (navigation.getVisibility() != VISIBLE) {
      navigation.setVisibility(VISIBLE);
    }
    navigation.setImageDrawable(navigationIcon);
    navigation.setContentDescription(contentDescription);
    navigation.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        listener.onNavigationClick();
      }
    });
  }

  public void hideNavigation() {
    if (navigation.getVisibility() != GONE) {
      navigation.setVisibility(GONE);
      navigation.setImageDrawable(null);
      navigation.setContentDescription(null);
      navigation.setOnClickListener(null);
    }
  }

  public void setTitle(CharSequence title) {
    this.title.setText(title);
  }

  public void addAction(Drawable icon, CharSequence title, final OnActionClickListener listener) {
    ImageView action = (ImageView) LayoutInflater.from(getContext())
        .inflate(R.layout.toolbar_menu_item, this, false);
    action.setImageDrawable(icon);
    action.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        listener.onActionClick();
      }
    });
    action.setOnLongClickListener(
        TextUtils.isEmpty(title) ? null : new MenuActionOnLongClickListener(action, title));
    action.setContentDescription(title);
    addView(action);
  }

  // TODO: subscribe to window exit?
  public void dismissHint() {
    if (hint != null) {
      hint.cancel();
      hint = null;
    }
  }

  @TargetApi(Build.VERSION_CODES.KITKAT_WATCH) @Override
  public WindowInsets onApplyWindowInsets(WindowInsets insets) {
    if (extendToInsets) {
      int left, top, right, bottom;
      if (lastApplied == null) {
        left = insets.getSystemWindowInsetLeft();
        top = insets.getSystemWindowInsetTop();
        right = insets.getSystemWindowInsetRight();
        bottom = insets.getSystemWindowInsetBottom();
      } else {
        left = insets.getSystemWindowInsetLeft() - lastApplied.getSystemWindowInsetLeft();
        top = insets.getSystemWindowInsetTop() - lastApplied.getSystemWindowInsetTop();
        right = insets.getSystemWindowInsetRight() - lastApplied.getSystemWindowInsetRight();
        bottom = insets.getSystemWindowInsetBottom() - insets.getSystemWindowInsetBottom();
      }
      ViewGroup.LayoutParams lp = getLayoutParams();
      int widthOriginal = lp.width;
      int heightOriginal = lp.height;
      lp.width = widthOriginal + left + right;
      lp.height = heightOriginal + top + bottom;
      setLayoutParams(lp);
      lastApplied = insets;
    }
    return super.onApplyWindowInsets(insets);
  }

  private final class MenuActionOnLongClickListener implements OnLongClickListener {
    private final View action;
    private final CharSequence title;

    MenuActionOnLongClickListener(View action, CharSequence title) {
      this.action = action;
      this.title = title;
    }

    @Override public boolean onLongClick(View v) {
      if (hint != null) {
        hint.cancel();
      }
      hint = Toast.makeText(action.getContext(), title, Toast.LENGTH_SHORT);
      Rect windowVisibleDisplayFrame = new Rect();
      action.getWindowVisibleDisplayFrame(windowVisibleDisplayFrame);
      int[] locationOnScreen = new int[2];
      action.getLocationOnScreen(locationOnScreen);
      boolean isRtl = isRtl();
      int windowVisibleDisplayFrameEnd =
          isRtl ? windowVisibleDisplayFrame.left : windowVisibleDisplayFrame.right;
      int xOffset = windowVisibleDisplayFrameEnd - locationOnScreen[0] - action.getWidth() / 2;
      if (isRtl) {
        xOffset = -xOffset;
      }
      int yOffset = locationOnScreen[1] - windowVisibleDisplayFrame.top + action.getHeight();
      hint.setGravity(Gravity.END | Gravity.TOP, xOffset, yOffset);
      hint.show();
      return true;
    }

    private boolean isRtl() {
      return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1
          && action.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
    }
  }
}
