package com.nightlynexus.pastries.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;

public final class Pastry implements Parcelable {
  @DrawableRes public final int drawable;

  public Pastry(@DrawableRes int drawable) {
    this.drawable = drawable;
  }

  Pastry(Parcel in) {
    drawable = in.readInt();
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(drawable);
  }

  @Override public int describeContents() {
    return 0;
  }

  public static final Creator<Pastry> CREATOR = new Creator<Pastry>() {
    @Override public Pastry createFromParcel(Parcel in) {
      return new Pastry(in);
    }

    @Override public Pastry[] newArray(int size) {
      return new Pastry[size];
    }
  };
}
