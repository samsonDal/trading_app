package com.trading.app.api;

import java.util.Collection;

/** Interface for storing the subscriptionConfig of a ric or symbol */
public interface SubscriptionCache {

  void put(String symbol, SubscriptionConfig subscriptionConfig);

  SubscriptionConfig get(String symbol);

  Collection<SubscriptionConfig> getAll();
}
