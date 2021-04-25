package com.trading.app.feed;

import com.trading.app.api.Feed;
import com.trading.app.api.FeedListener;
import com.trading.app.model.Trade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * TradeFeed should run on a separate thread. For real feeds we don't want this thread to be too
 * busy doing business logic but just get incoming trades from upstream and publish to subscribers
 * (i.e. the listeners)
 *
 * <p>all it does is push the trades in the listener's queue.
 */
public class TradeFeed implements Feed<Trade> {

  private static final Logger LOGGER = LoggerFactory.getLogger(Trade.class);
  private final List<FeedListener<Trade>> listeners = new CopyOnWriteArrayList<>();

  public TradeFeed() {}

  @Override
  public synchronized void subscribe(FeedListener<Trade> listener) {
    if (!listeners.contains(listener)) listeners.add(listener);
  }

  @Override
  public synchronized void unsubscribe(FeedListener<Trade> listener) {
    listeners.remove(listener);
  }

  public void tick(Trade trade) {
    if (trade != null) {
      LOGGER.info("Received new trade {}", trade);
      listeners.forEach(listener -> listener.onTrade(trade));
    }
  }

  List<FeedListener<Trade>> getListeners() {
    return listeners;
  }
}
