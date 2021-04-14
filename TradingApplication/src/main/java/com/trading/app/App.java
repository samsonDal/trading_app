package com.trading.app;

import com.trading.app.api.Feed;
import com.trading.app.api.FeedObject;
import com.trading.app.api.SubscriptionCache;
import com.trading.app.api.TradeHandler;
import com.trading.app.feed.TradeFeed;
import com.trading.app.listener.TradeFeedListener;
import com.trading.app.model.Trade;
import com.trading.app.subscriber.SubscriptionCacheImpl;
import com.trading.app.subscriber.SubscriptionConfigImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class App {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  public static void main(String[] args) throws URISyntaxException, IOException {

    SubscriptionCache subscriptionCache = readSubscriptionConfig("subscriptionConfig");
    Feed<Trade> feed = new TradeFeed();
    TradeFeedListener feedListener = new TradeFeedListener(subscriptionCache, feed);
    feedListener.start();
    List<Trade> trades = readTrades();
    trades.forEach(feed::tick);
    // get the result for the rics we subscribed to
    subscriptionCache
        .getAll()
        .forEach(
            subscriptionConfig -> {
              LOGGER.info("Displaying result for {}", subscriptionConfig.getSymbol());
              subscriptionConfig.getHandlers().forEach(TradeHandler::printResult);
            });
  }

  private static URL getResource(String resourceName) {
    URL resource = App.class.getClassLoader().getResource(resourceName);
    if (resource == null) {
      throw new IllegalArgumentException("File not found");
    }

    return resource;
  }

  private static SubscriptionCache readSubscriptionConfig(String subscriptionConfig)
      throws URISyntaxException, IOException {
    SubscriptionCache subscriptionCache = new SubscriptionCacheImpl();
    URL resource = getResource(subscriptionConfig);
    File file = Paths.get(resource.toURI()).toFile();
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      reader
          .lines()
          .forEach(
              line -> {
                String[] split = line.split(",");
                String ric = split[0];
                String[] handlers = split[1].split(":");
                Collection<TradeHandler> handlerCollection = new ArrayList<>();
                for (String v : handlers) {
                  String classQualifiedName = "com.trading.app.handlers." + v;
                  try {
                    Class<?> clazz = Class.forName(classQualifiedName);
                    TradeHandler handler = (TradeHandler) clazz.newInstance();
                    handlerCollection.add(handler);
                  } catch (ClassNotFoundException e) {
                    throw new IllegalArgumentException(
                        "No Handler found for " + classQualifiedName);
                  } catch (IllegalAccessException | InstantiationException e) {
                    throw new IllegalStateException(
                        "Error creating class for " + classQualifiedName);
                  }
                }
                subscriptionCache.put(ric, new SubscriptionConfigImpl(ric, handlerCollection));
              });
    }

    return subscriptionCache;
  }

  private static Trade createTrade(String line) {
    String[] split = line.split(",");
    long timestamp = Long.parseLong(split[0]);
    String symbol = split[1];
    double price = Double.parseDouble(split[2]);
    long size = Long.parseLong(split[3]);
    FeedObject.Flag flag = FeedObject.Flag.valueOf(split[4]);
    Trade t = new Trade();
    t.setPrice(price);
    t.setSize(size);
    t.setSymbol(symbol);
    t.setTimestamp(timestamp);
    t.setFlag(flag);

    return t;
  }

  private static List<Trade> readTrades() throws URISyntaxException, IOException {
    URL resource = getResource("trades");
    File file = Paths.get(resource.toURI()).toFile();
    List<Trade> trades = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      reader
          .lines()
          .forEach(
              line -> {
                trades.add(createTrade(line));
              });
    }

    return trades;
  }
}
