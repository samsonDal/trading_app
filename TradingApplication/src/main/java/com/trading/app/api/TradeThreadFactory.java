package com.trading.app.api;

import java.text.MessageFormat;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class TradeThreadFactory implements ThreadFactory {
  private static final AtomicInteger poolNumber = new AtomicInteger(1);
  private String namePrefix;

  public TradeThreadFactory(String name) {
    namePrefix = MessageFormat.format("pool-{0}-{1}", poolNumber.getAndIncrement(), name);
  }

  @Override
  public Thread newThread(Runnable r) {
    Thread t = new Thread(r, namePrefix);
    // just to make sure this thread dies once the JVM exits
    t.setDaemon(true);
    return t;
  }
}
