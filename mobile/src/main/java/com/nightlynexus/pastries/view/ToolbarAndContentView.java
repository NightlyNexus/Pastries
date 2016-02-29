package com.nightlynexus.pastries.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.nightlynexus.pastries.R;
import com.nightlynexus.pastries.controller.Controller;
import com.nightlynexus.pastries.controller.LayoutInflaterCloningContextWrapper;
import com.nightlynexus.pastries.controller.Stacker;
import com.nightlynexus.pastries.controller.ViewFactory;
import com.nightlynexus.pastries.data.Fetcher;
import com.nightlynexus.pastries.data.Pastry;
import java.util.ArrayList;

public final class ToolbarAndContentView extends LinearLayout {
  public static final class ToolbarAndContentViewFactory implements ViewFactory, Parcelable {
    public ToolbarAndContentViewFactory() {
    }

    protected ToolbarAndContentViewFactory(Parcel in) {
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
    }

    @Override public View createView(Context context, ViewGroup parent) {
      return LayoutInflater.from(context).inflate(R.layout.toolbar_and_content, parent, false);
    }

    @Override public int describeContents() {
      return 0;
    }

    public static final Creator<ToolbarAndContentViewFactory> CREATOR =
        new Creator<ToolbarAndContentViewFactory>() {
          @Override public ToolbarAndContentViewFactory createFromParcel(Parcel in) {
            return new ToolbarAndContentViewFactory(in);
          }

          @Override public ToolbarAndContentViewFactory[] newArray(int size) {
            return new ToolbarAndContentViewFactory[size];
          }
        };
  }

  private final ToolbarLayoutController toolbarLayoutController;
  private final Context viewContext;
  private Stacker stacker;

  public ToolbarAndContentView(Context context) {
    super(context);
    ToolbarLayout toolbarLayout =
        (ToolbarLayout) LayoutInflater.from(context).inflate(R.layout.toolbar_layout, this, false);
    addView(toolbarLayout);
    stacker = null;
    toolbarLayoutController = new ToolbarLayoutController(toolbarLayout,
        ContextCompat.getDrawable(context, R.drawable.ic_arrow_back_white_24dp),
        getResources().getText(R.string.navigation_up_content_description),
        new ToolbarLayout.OnNavigationClickListener() {
          @Override public void onNavigationClick() {
            stacker.pop();
            setNavigation();
          }
        });
    viewContext = new ToolbarAndContentViewContext(context, toolbarLayoutController,
        new Fetcher.Fetch.Callback() {
          @Override public void onPastriesFetched(Pastry[] pastries) {
            stacker.replace(Controller.from(new ListView.ListViewFactory(pastries)));
            setNavigation();
          }
        }, new ListView.OnPastryClickListener() {
      @Override public void onPastryClick(Pastry pastry) {
        stacker.push(Controller.from(new DetailView.DetailViewFactory(pastry)));
        setNavigation();
      }
    });
    toolbarLayout.addAction(ContextCompat.getDrawable(getContext(), R.mipmap.ic_launcher),
        getResources().getText(R.string.menu_item), new ToolbarLayout.OnActionClickListener() {
          @Override public void onActionClick() {
          }
        });
  }

  public ToolbarAndContentView(Context context, AttributeSet attrs) {
    super(context, attrs);
    ToolbarLayout toolbarLayout =
        (ToolbarLayout) LayoutInflater.from(context).inflate(R.layout.toolbar_layout, this, false);
    addView(toolbarLayout);
    stacker = null;
    toolbarLayoutController = new ToolbarLayoutController(toolbarLayout,
        ContextCompat.getDrawable(context, R.drawable.ic_arrow_back_white_24dp),
        getResources().getText(R.string.navigation_up_content_description),
        new ToolbarLayout.OnNavigationClickListener() {
          @Override public void onNavigationClick() {
            stacker.pop();
            setNavigation();
          }
        });
    viewContext = new ToolbarAndContentViewContext(context, toolbarLayoutController,
        new Fetcher.Fetch.Callback() {
          @Override public void onPastriesFetched(Pastry[] pastries) {
            stacker.replace(Controller.from(new ListView.ListViewFactory(pastries)));
            setNavigation();
          }
        }, new ListView.OnPastryClickListener() {
      @Override public void onPastryClick(Pastry pastry) {
        stacker.push(Controller.from(new DetailView.DetailViewFactory(pastry)));
        setNavigation();
      }
    });
    toolbarLayout.addAction(ContextCompat.getDrawable(getContext(), R.mipmap.ic_launcher),
        getResources().getText(R.string.menu_item), new ToolbarLayout.OnActionClickListener() {
          @Override public void onActionClick() {
          }
        });
  }

  public ToolbarAndContentView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    ToolbarLayout toolbarLayout =
        (ToolbarLayout) LayoutInflater.from(context).inflate(R.layout.toolbar_layout, this, false);
    addView(toolbarLayout);
    stacker = null;
    toolbarLayoutController = new ToolbarLayoutController(toolbarLayout,
        ContextCompat.getDrawable(context, R.drawable.ic_arrow_back_white_24dp),
        getResources().getText(R.string.navigation_up_content_description),
        new ToolbarLayout.OnNavigationClickListener() {
          @Override public void onNavigationClick() {
            stacker.pop();
            setNavigation();
          }
        });
    viewContext = new ToolbarAndContentViewContext(context, toolbarLayoutController,
        new Fetcher.Fetch.Callback() {
          @Override public void onPastriesFetched(Pastry[] pastries) {
            stacker.replace(Controller.from(new ListView.ListViewFactory(pastries)));
            setNavigation();
          }
        }, new ListView.OnPastryClickListener() {
      @Override public void onPastryClick(Pastry pastry) {
        stacker.push(Controller.from(new DetailView.DetailViewFactory(pastry)));
        setNavigation();
      }
    });
    toolbarLayout.addAction(ContextCompat.getDrawable(getContext(), R.mipmap.ic_launcher),
        getResources().getText(R.string.menu_item), new ToolbarLayout.OnActionClickListener() {
          @Override public void onActionClick() {
          }
        });
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public ToolbarAndContentView(Context context, AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    ToolbarLayout toolbarLayout =
        (ToolbarLayout) LayoutInflater.from(context).inflate(R.layout.toolbar_layout, this, false);
    addView(toolbarLayout);
    stacker = null;
    toolbarLayoutController = new ToolbarLayoutController(toolbarLayout,
        ContextCompat.getDrawable(context, R.drawable.ic_arrow_back_white_24dp),
        getResources().getText(R.string.navigation_up_content_description),
        new ToolbarLayout.OnNavigationClickListener() {
          @Override public void onNavigationClick() {
            stacker.pop();
            setNavigation();
          }
        });
    viewContext = new ToolbarAndContentViewContext(context, toolbarLayoutController,
        new Fetcher.Fetch.Callback() {
          @Override public void onPastriesFetched(Pastry[] pastries) {
            stacker.replace(Controller.from(new ListView.ListViewFactory(pastries)));
            setNavigation();
          }
        }, new ListView.OnPastryClickListener() {
      @Override public void onPastryClick(Pastry pastry) {
        stacker.push(Controller.from(new DetailView.DetailViewFactory(pastry)));
        setNavigation();
      }
    });
    toolbarLayout.addAction(ContextCompat.getDrawable(getContext(), R.mipmap.ic_launcher),
        getResources().getText(R.string.menu_item), new ToolbarLayout.OnActionClickListener() {
          @Override public void onActionClick() {
          }
        });
  }

  public boolean goBack() {
    boolean popped = stacker.pop();
    setNavigation();
    return popped;
  }

  public void dismissToolbarHint() {
    toolbarLayoutController.toolbarLayout.dismissHint();
  }

  @Override protected void onRestoreInstanceState(Parcelable state) {
    SavedState savedState = (SavedState) state;
    stacker = new Stacker(viewContext, this, 1, savedState.stack);
    stacker.push(Controller.from(savedState.currentState));
    setNavigation();
    super.onRestoreInstanceState(savedState.getSuperState());
  }

  @Override protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    if (stacker == null) {
      stacker = new Stacker(viewContext, this, 1, new ArrayList<Controller.SavedState>(1));
      stacker.push(Controller.from(new LoadingView.LoadingViewFactory()));
      setNavigation();
    }
  }

  @Override protected Parcelable onSaveInstanceState() {
    return new SavedState(super.onSaveInstanceState(), stacker.getStack(),
        stacker.getCurrentState());
  }

  private void setNavigation() {
    toolbarLayoutController.setUpEnabled(!stacker.isStackEmpty());
  }

  private static final class SavedState extends BaseSavedState implements Parcelable {
    final ArrayList<Controller.SavedState> stack;
    final Controller.SavedState currentState;

    SavedState(Parcelable superState, ArrayList<Controller.SavedState> stack,
        Controller.SavedState currentState) {
      super(superState);
      this.stack = stack;
      this.currentState = currentState;
    }

    SavedState(Parcel source) {
      super(source);
      stack = source.createTypedArrayList(Controller.SavedState.CREATOR);
      currentState = source.readParcelable(Controller.SavedState.class.getClassLoader());
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
      super.writeToParcel(dest, flags);
      dest.writeTypedList(stack);
      dest.writeParcelable(currentState, flags);
    }

    public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
      @Override public SavedState createFromParcel(Parcel in) {
        return new SavedState(in);
      }

      @Override public SavedState[] newArray(int size) {
        return new SavedState[size];
      }
    };
  }

  private static final class ToolbarLayoutController implements ToolbarTitleController {
    final ToolbarLayout toolbarLayout;
    private final Drawable navigationIcon;
    private final CharSequence contentDescription;
    private final ToolbarLayout.OnNavigationClickListener onNavigationClickListener;

    ToolbarLayoutController(ToolbarLayout toolbarLayout, Drawable navigationIcon,
        CharSequence contentDescription,
        ToolbarLayout.OnNavigationClickListener onNavigationClickListener) {
      this.toolbarLayout = toolbarLayout;
      this.navigationIcon = navigationIcon;
      this.contentDescription = contentDescription;
      this.onNavigationClickListener = onNavigationClickListener;
    }

    @Override public void setTitle(CharSequence title) {
      toolbarLayout.setTitle(title);
    }

    void setUpEnabled(boolean enabled) {
      if (enabled) {
        toolbarLayout.showNavigation(navigationIcon, contentDescription, onNavigationClickListener);
      } else {
        toolbarLayout.hideNavigation();
      }
    }
  }

  public static final class ToolbarAndContentViewContext
      extends LayoutInflaterCloningContextWrapper {
    private static final String TOOLBAR_TITLE_CONTROLLER =
        "com.nightlynexus.pastries.TOOLBAR_TITLE_CONTROLLER";
    private static final String CALLBACK = "com.nightlynexus.pastries.CALLBACK";
    private static final String ON_PASTRY_CLICK_LISTENER =
        "com.nightlynexus.pastries.ON_PASTRY_CLICK_LISTENER";

    @SuppressWarnings({ "ResourceType", "WrongConstant" }) // Explicitly doing a custom service.
    public static ToolbarTitleController getToolbarTitleController(Context context) {
      return (ToolbarTitleController) context.getSystemService(TOOLBAR_TITLE_CONTROLLER);
    }

    @SuppressWarnings({ "ResourceType", "WrongConstant" }) // Explicitly doing a custom service.
    public static Fetcher.Fetch.Callback getCallback(Context context) {
      return (Fetcher.Fetch.Callback) context.getSystemService(CALLBACK);
    }

    @SuppressWarnings({ "ResourceType", "WrongConstant" }) // Explicitly doing a custom service.
    public static ListView.OnPastryClickListener getOnPastryClickListener(Context context) {
      return (ListView.OnPastryClickListener) context.getSystemService(ON_PASTRY_CLICK_LISTENER);
    }

    private final ToolbarTitleController toolbarTitleController;
    private final Fetcher.Fetch.Callback callback;
    private final ListView.OnPastryClickListener onPastryClickListener;

    public ToolbarAndContentViewContext(Context base, ToolbarTitleController toolbarTitleController,
        Fetcher.Fetch.Callback callback, ListView.OnPastryClickListener onPastryClickListener) {
      super(base);
      this.toolbarTitleController = toolbarTitleController;
      this.callback = callback;
      this.onPastryClickListener = onPastryClickListener;
    }

    @Override public Object getSystemService(@NonNull String name) {
      if (TOOLBAR_TITLE_CONTROLLER.equals(name)) {
        return toolbarTitleController;
      } else if (CALLBACK.equals(name)) {
        return callback;
      } else if (ON_PASTRY_CLICK_LISTENER.equals(name)) {
        return onPastryClickListener;
      }
      Object service = super.getSystemService(name);
      if (service != null) {
        return service;
      }
      return super.getSystemService(name);
    }
  }
}
