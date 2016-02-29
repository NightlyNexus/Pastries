package com.nightlynexus.pastries.view;

import android.content.Context;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.nightlynexus.pastries.R;
import com.nightlynexus.pastries.controller.LayoutInflaterCloningContextWrapper;
import com.nightlynexus.pastries.controller.ViewFactory;
import com.nightlynexus.pastries.data.Pastry;

import static com.nightlynexus.pastries.view.ListView.ListViewFactory.ListViewFactoryContextWrapper.getPastries;
import static com.nightlynexus.pastries.view.ToolbarAndContentView.ToolbarAndContentViewContext.getOnPastryClickListener;
import static com.nightlynexus.pastries.view.ToolbarAndContentView.ToolbarAndContentViewContext.getToolbarTitleController;

public final class ListView extends RecyclerView {
  public static final class ListViewFactory implements ViewFactory, Parcelable {
    static final class ListViewFactoryContextWrapper extends LayoutInflaterCloningContextWrapper {
      private static final String PASTRIES = "com.nightlynexus.pastries.PASTRIES";

      @SuppressWarnings({ "ResourceType", "WrongConstant" }) // Explicitly doing a custom service.
      static Pastry[] getPastries(Context context) {
        return (Pastry[]) context.getSystemService(PASTRIES);
      }

      private final Pastry[] pastries;

      public ListViewFactoryContextWrapper(Context base, Pastry[] pastries) {
        super(base);
        this.pastries = pastries;
      }

      @Override public Object getSystemService(String name) {
        if (PASTRIES.equals(name)) {
          return pastries;
        }
        return super.getSystemService(name);
      }
    }

    private final Pastry[] pastries;

    public ListViewFactory(Pastry[] pastries) {
      this.pastries = pastries;
    }

    protected ListViewFactory(Parcel in) {
      pastries = in.createTypedArray(Pastry.CREATOR);
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
      dest.writeTypedArray(pastries, flags);
    }

    @Override public View createView(Context context, ViewGroup parent) {
      context = new ListViewFactoryContextWrapper(context, pastries);
      return LayoutInflater.from(context).inflate(R.layout.list, parent, false);
    }

    @Override public int describeContents() {
      return 0;
    }

    public static final Creator<ListViewFactory> CREATOR = new Creator<ListViewFactory>() {
      @Override public ListViewFactory createFromParcel(Parcel in) {
        return new ListViewFactory(in);
      }

      @Override public ListViewFactory[] newArray(int size) {
        return new ListViewFactory[size];
      }
    };
  }

  public interface OnPastryClickListener {
    void onPastryClick(Pastry pastry);
  }

  private final ToolbarTitleController toolbarTitleController;
  private final Pastry[] pastries;
  private final OnPastryClickListener listener;

  public ListView(Context context) {
    super(context);
    toolbarTitleController = getToolbarTitleController(context);
    pastries = getPastries(context);
    listener = getOnPastryClickListener(context);
    addItemDecoration(new VerticalItemDecoration(
        getResources().getDimensionPixelSize(R.dimen.pastry_item_padding)));
    setLayoutManager(new LinearLayoutManager(context));
    setHasFixedSize(true);
    setAdapter(new Adapter(context, pastries, listener));
  }

  public ListView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    toolbarTitleController = getToolbarTitleController(context);
    pastries = getPastries(context);
    listener = getOnPastryClickListener(context);
    addItemDecoration(new VerticalItemDecoration(
        getResources().getDimensionPixelSize(R.dimen.pastry_item_padding)));
    setLayoutManager(new LinearLayoutManager(context));
    setHasFixedSize(true);
    setAdapter(new Adapter(context, pastries, listener));
  }

  public ListView(Context context, @Nullable AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    toolbarTitleController = getToolbarTitleController(context);
    pastries = getPastries(context);
    listener = getOnPastryClickListener(context);
    addItemDecoration(new VerticalItemDecoration(
        getResources().getDimensionPixelSize(R.dimen.pastry_item_padding)));
    setLayoutManager(new LinearLayoutManager(context));
    setHasFixedSize(true);
    setAdapter(new Adapter(context, pastries, listener));
  }

  @Override protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    toolbarTitleController.setTitle(getResources().getText(R.string.title_list));
  }

  private static final class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private final Context context;
    private final Pastry[] pastries;
    private final OnPastryClickListener listener;

    Adapter(Context context, Pastry[] pastries, OnPastryClickListener listener) {
      this.context = context;
      this.pastries = pastries;
      this.listener = listener;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      LayoutInflater inflater = LayoutInflater.from(context);
      ImageView pastry = (ImageView) inflater.inflate(R.layout.list_item, parent, false);
      return new ViewHolder(pastry);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
      final Pastry pastry = pastries[position];
      holder.picture.setImageResource(pastry.drawable);
      holder.picture.setContentDescription(
          context.getResources().getResourceEntryName(pastry.drawable));
      holder.picture.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          listener.onPastryClick(pastry);
        }
      });
    }

    @Override public int getItemCount() {
      return pastries.length;
    }

    static final class ViewHolder extends RecyclerView.ViewHolder {
      final ImageView picture;

      ViewHolder(ImageView picture) {
        super(picture);
        this.picture = picture;
      }
    }
  }

  private static final class VerticalItemDecoration extends RecyclerView.ItemDecoration {
    private final int padding;

    VerticalItemDecoration(int padding) {
      this.padding = padding;
    }

    @Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
        RecyclerView.State state) {
      int position = parent.getChildLayoutPosition(view);
      if (position == 0) {
        return;
      }
      outRect.top = padding;
    }
  }
}
