package com.trading.app.api;

/** Listener that reacts to a trade event */
public interface FeedListener<T extends FeedObject> {

  void onTrade(T trade);
}
