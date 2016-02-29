package com.nightlynexus.pastries;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import com.nightlynexus.pastries.data.Fetcher;
import com.nightlynexus.pastries.data.Pastry;

public final class PastriesApplication extends Application {
  private static final String PASTRIES_FETCHER = "com.nightlynexus.pastries.PASTRIES_FETCHER";

  @SuppressWarnings({ "ResourceType", "WrongConstant" }) // Explicitly doing a custom service.
  public static Fetcher getPastriesFetcher(Context context) {
    return (Fetcher) context.getSystemService(PASTRIES_FETCHER);
  }

  private Fetcher fetcher;

  @Override public Object getSystemService(String name) {
    if (PASTRIES_FETCHER.equals(name)) {
      return fetcher;
    }
    return super.getSystemService(name);
  }

  @Override public void onCreate() {
    super.onCreate();
    fetcher = new SampleFetcher(new Handler(), 3000L);
  }

  private static final class SampleFetcher implements Fetcher {
    private final Handler poster;
    private final long delayMillis;

    SampleFetcher(Handler poster, long delayMillis) {
      this.poster = poster;
      this.delayMillis = delayMillis;
    }

    @NonNull @Override public Fetch newFetch() {
      return new SampleFetch(poster, delayMillis);
    }

    private static final class SampleFetch implements Fetch {
      private final Handler poster;
      private final long delayMillis;
      private Runnable fetch;

      SampleFetch(Handler poster, long delayMillis) {
        this.poster = poster;
        this.delayMillis = delayMillis;
        fetch = null;
      }

      @Override public void fetch(Callback callback) {
        fetch = new FetchedRunnable(callback);
        poster.postDelayed(fetch, delayMillis);
      }

      @Override public void cancel() {
        if (fetch != null) {
          poster.removeCallbacks(fetch);
        }
      }

      private final class FetchedRunnable implements Runnable {
        private final Callback callback;

        FetchedRunnable(Callback callback) {
          this.callback = callback;
        }

        @Override public void run() {
          Pastry[] pastries = {
              new Pastry(R.drawable.bagel), new Pastry(R.drawable.cookies),
              new Pastry(R.drawable.linzer_torte), new Pastry(R.drawable.liege),
              new Pastry(R.drawable.moorkoppen), new Pastry(R.drawable.petit_four),
              new Pastry(R.drawable.tompouce)
          };
          callback.onPastriesFetched(pastries);
          fetch = null;
        }
      }
    }
  }
}
