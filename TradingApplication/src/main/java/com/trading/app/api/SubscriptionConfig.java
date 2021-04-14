package com.trading.app.api;

import java.util.Collection;

/**
 * Interface defining contract of a subscription config. It should give the symbol or ric and a
 * collection of Handlers that would be used to handle feed for this ric
 */
public interface SubscriptionConfig {

  String getSymbol();

  Collection<TradeHandler> getHandlers();
}
