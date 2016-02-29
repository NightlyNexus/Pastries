package com.nightlynexus.pastries.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.nightlynexus.pastries.R;
import com.nightlynexus.pastries.controller.LayoutInflaterCloningContextWrapper;
import com.nightlynexus.pastries.controller.ViewFactory;
import com.nightlynexus.pastries.data.Pastry;

import static com.nightlynexus.pastries.view.DetailView.DetailViewFactory.DetailViewFactoryContextWrapper.getPastry;
import static com.nightlynexus.pastries.view.ToolbarAndContentView.ToolbarAndContentViewContext.getToolbarTitleController;

public final class DetailView extends LinearLayout {
  public static final class DetailViewFactory implements ViewFactory, Parcelable {
    static final class DetailViewFactoryContextWrapper extends LayoutInflaterCloningContextWrapper {
      private static final String PASTRY = "com.nightlynexus.pastries.PASTRY";

      @SuppressWarnings({ "ResourceType", "WrongConstant" }) // Explicitly doing a custom service.
      static Pastry getPastry(Context context) {
        return (Pastry) context.getSystemService(PASTRY);
      }

      private final Pastry pastry;

      public DetailViewFactoryContextWrapper(Context base, Pastry pastry) {
        super(base);
        this.pastry = pastry;
      }

      @Override public Object getSystemService(String name) {
        if (PASTRY.equals(name)) {
          return pastry;
        }
        return super.getSystemService(name);
      }
    }

    private final Pastry pastry;

    public DetailViewFactory(Pastry pastry) {
      this.pastry = pastry;
    }

    protected DetailViewFactory(Parcel in) {
      pastry = in.readParcelable(Pastry.class.getClassLoader());
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
      dest.writeParcelable(pastry, flags);
    }

    @Override public View createView(Context context, ViewGroup parent) {
      context = new DetailViewFactoryContextWrapper(context, pastry);
      return LayoutInflater.from(context).inflate(R.layout.detail, parent, false);
    }

    @Override public int describeContents() {
      return 0;
    }

    public static final Creator<DetailViewFactory> CREATOR = new Creator<DetailViewFactory>() {
      @Override public DetailViewFactory createFromParcel(Parcel in) {
        return new DetailViewFactory(in);
      }

      @Override public DetailViewFactory[] newArray(int size) {
        return new DetailViewFactory[size];
      }
    };
  }

  private final ToolbarTitleController toolbarTitleController;
  private final Pastry pastry;

  public DetailView(Context context) {
    super(context);
    toolbarTitleController = getToolbarTitleController(context);
    pastry = getPastry(context);
    LayoutInflater.from(context).inflate(R.layout.children_detail, this, true);
    ImageView icon = (ImageView) getChildAt(0);
    TextView name = (TextView) getChildAt(1);
    icon.setImageResource(pastry.drawable);
    name.setText(getResources().getResourceEntryName(pastry.drawable));
  }

  public DetailView(Context context, AttributeSet attrs) {
    super(context, attrs);
    toolbarTitleController = getToolbarTitleController(context);
    pastry = getPastry(context);
    LayoutInflater.from(context).inflate(R.layout.children_detail, this, true);
    ImageView icon = (ImageView) getChildAt(0);
    TextView name = (TextView) getChildAt(1);
    icon.setImageResource(pastry.drawable);
    name.setText(getResources().getResourceEntryName(pastry.drawable));
  }

  public DetailView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    toolbarTitleController = getToolbarTitleController(context);
    pastry = getPastry(context);
    LayoutInflater.from(context).inflate(R.layout.children_detail, this, true);
    ImageView icon = (ImageView) getChildAt(0);
    TextView name = (TextView) getChildAt(1);
    icon.setImageResource(pastry.drawable);
    name.setText(getResources().getResourceEntryName(pastry.drawable));
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public DetailView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    toolbarTitleController = getToolbarTitleController(context);
    pastry = getPastry(context);
    LayoutInflater.from(context).inflate(R.layout.children_detail, this, true);
    ImageView icon = (ImageView) getChildAt(0);
    TextView name = (TextView) getChildAt(1);
    icon.setImageResource(pastry.drawable);
    name.setText(getResources().getResourceEntryName(pastry.drawable));
  }

  @Override protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    toolbarTitleController.setTitle(getResources().getResourceEntryName(pastry.drawable));
  }
}
