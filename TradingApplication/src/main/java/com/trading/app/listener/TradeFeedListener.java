package com.trading.app.listener;

import com.trading.app.api.*;
import com.trading.app.model.Trade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * TradeFeedListener listens for trades in its queue and process them It currently has a capacity of
 * 8192 of trades in can handle at a current time but that should be configurable Upon receipt of a
 * trades it does a lookup to the subscription cache o get the subscription config of the symbol and
 * call the handler which will then perform operation on the trades.
 */
public class TradeFeedListener implements FeedListener<Trade>, Startable, Stoppable {

  private static final int CAPACITY = 8192;
  private static final Logger LOGGER = LoggerFactory.getLogger(TradeFeedListener.class);

  private final SubscriptionCache subscriptionCache;
  private final BlockingQueue<Trade> queue;
  private final Feed<Trade> feed;
  private ExecutorService executorService =
      Executors.newFixedThreadPool(1, new TradeThreadFactory("TradeListener"));
  private volatile boolean isTerminated = false;

  public TradeFeedListener(SubscriptionCache subscriptionCache, Feed<Trade> feed) {
    this.subscriptionCache = subscriptionCache;
    this.feed = feed;
    queue = new LinkedBlockingQueue<>(8192);
  }

  @Override
  public void onTrade(Trade trade) {
    queue.add(trade);
  }

  @Override
  public void start() {
    feed.subscribe(this);
    executorService.execute(
        () -> {
          Collection<Trade> entries = new ArrayList<>(CAPACITY);
          while (!isTerminated) {
            try {
              Trade trade = queue.take();
              processTrade(trade, entries);
            } catch (InterruptedException e) {
              // call interrupt to set the interrupt status to notify thread has terminated
              Thread.currentThread().interrupt();
              LOGGER.error("Thread interrupted while processing trades from queue ");
            }
          }
        });
  }

  void processTrade(Trade trade, Collection<Trade> entries) {
    entries.add(trade);
    queue.drainTo(entries, CAPACITY - 1); // pull down all entries we can
    LOGGER.info("processing trades {}", entries);
    entries.forEach(
        t -> {
          String symbol = t.getSymbol();
          SubscriptionConfig config = subscriptionCache.get(symbol);
          config.getHandlers().forEach(handler -> handler.handle(t));
        });
    entries.clear();
  }

  int getQueueSize() {
    return queue.size();
  }

  @Override
  public void stop() {
    feed.unsubscribe(this);
    isTerminated = true;
    executorService.shutdownNow();
  }
}
