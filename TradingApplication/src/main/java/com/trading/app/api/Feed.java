package com.trading.app.api;

/**
 * Interface defining contract for subscribe/unsubscribe to a Feed implementation should provide
 * behaviour for situations where a tick for a FeedObject is received.
 *
 * <p>It is advisable to have this process on a separate Thread
 *
 * @param <T>
 */
public interface Feed<T extends FeedObject> {

  void subscribe(FeedListener<T> listener);

  void unsubscribe(FeedListener<T> listener);

  void tick(T obj);
}
