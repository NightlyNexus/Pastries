package com.nightlynexus.pastries.data;

public interface Fetcher {
  interface Fetch {
    interface Callback {
      void onPastriesFetched(Pastry[] pastries);
    }

    void fetch(Callback callback);

    void cancel();
  }

  Fetch newFetch();
}
