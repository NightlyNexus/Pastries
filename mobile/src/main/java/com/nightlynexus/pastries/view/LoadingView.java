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
import android.widget.ProgressBar;
import com.nightlynexus.pastries.PastriesApplication;
import com.nightlynexus.pastries.R;
import com.nightlynexus.pastries.controller.ViewFactory;
import com.nightlynexus.pastries.data.Fetcher;

import static com.nightlynexus.pastries.view.ToolbarAndContentView.ToolbarAndContentViewContext.getCallback;
import static com.nightlynexus.pastries.view.ToolbarAndContentView.ToolbarAndContentViewContext.getToolbarTitleController;

public final class LoadingView extends ProgressBar {
  public static final class LoadingViewFactory implements ViewFactory, Parcelable {
    public LoadingViewFactory() {
    }

    protected LoadingViewFactory(Parcel in) {
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
    }

    @Override public View createView(Context context, ViewGroup parent) {
      return LayoutInflater.from(context).inflate(R.layout.loading, parent, false);
    }

    @Override public int describeContents() {
      return 0;
    }

    public static final Creator<LoadingViewFactory> CREATOR = new Creator<LoadingViewFactory>() {
      @Override public LoadingViewFactory createFromParcel(Parcel in) {
        return new LoadingViewFactory(in);
      }

      @Override public LoadingViewFactory[] newArray(int size) {
        return new LoadingViewFactory[size];
      }
    };
  }

  private final ToolbarTitleController toolbarTitleController;
  private final Fetcher fetcher;
  private final Fetcher.Fetch.Callback callback;
  private Fetcher.Fetch fetch;

  public LoadingView(Context context) {
    super(context);
    toolbarTitleController = getToolbarTitleController(context);
    fetcher = PastriesApplication.getPastriesFetcher(context);
    callback = getCallback(context);
  }

  public LoadingView(Context context, AttributeSet attrs) {
    super(context, attrs);
    toolbarTitleController = getToolbarTitleController(context);
    fetcher = PastriesApplication.getPastriesFetcher(context);
    callback = getCallback(context);
  }

  public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    toolbarTitleController = getToolbarTitleController(context);
    fetcher = PastriesApplication.getPastriesFetcher(context);
    callback = getCallback(context);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public LoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    toolbarTitleController = getToolbarTitleController(context);
    fetcher = PastriesApplication.getPastriesFetcher(context);
    callback = getCallback(context);
  }

  @Override protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    toolbarTitleController.setTitle(getResources().getText(R.string.title_loading));
    fetch = fetcher.newFetch();
    fetch.fetch(callback);
  }

  @Override protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    fetch.cancel();
  }
}
