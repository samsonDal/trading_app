package com.trading.app.subscriber;

import com.trading.app.api.SubscriptionCache;
import com.trading.app.api.SubscriptionConfig;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SubscriptionCacheImpl implements SubscriptionCache {

  private final Map<String, SubscriptionConfig> cache = new HashMap<>();

  @Override
  public void put(String symbol, SubscriptionConfig subscriptionConfig) {
    cache.put(symbol, subscriptionConfig);
  }

  @Override
  public SubscriptionConfig get(String symbol) {
    return cache.get(symbol);
  }

  @Override
  public Collection<SubscriptionConfig> getAll() {
    return cache.values();
  }
}
