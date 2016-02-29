package com.nightlynexus.pastries;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import com.nightlynexus.pastries.controller.Controller;
import com.nightlynexus.pastries.view.ToolbarAndContentView;

public final class PastriesActivity extends Activity {
  private static final String KEY_TOOLBAR_AND_CONTENT_VIEW_CONTROLLER_STATE =
      "KEY_TOOLBAR_AND_CONTENT_VIEW_CONTROLLER_STATE";

  private Controller toolbarAndContentViewController;

  @Override public Object getSystemService(@NonNull String name) {
    Object service = super.getSystemService(name);
    if (service != null) {
      return service;
    }
    return getApplication().getSystemService(name);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
    if (savedInstanceState == null) {
      toolbarAndContentViewController =
          Controller.from(new ToolbarAndContentView.ToolbarAndContentViewFactory());
    } else {
      Controller.SavedState savedState =
          savedInstanceState.getParcelable(KEY_TOOLBAR_AND_CONTENT_VIEW_CONTROLLER_STATE);
      toolbarAndContentViewController = Controller.from(savedState);
    }
    setContentView(toolbarAndContentViewController.createView(this, root));
  }

  @Override protected void onStop() {
    super.onStop();
    ((ToolbarAndContentView) toolbarAndContentViewController.getView()).dismissToolbarHint();
  }

  @Override public void onBackPressed() {
    if (!((ToolbarAndContentView) toolbarAndContentViewController.getView()).goBack()) {
      super.onBackPressed();
    }
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    outState.putParcelable(KEY_TOOLBAR_AND_CONTENT_VIEW_CONTROLLER_STATE,
        toolbarAndContentViewController.save());
  }

  @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
  }
}
