package com.trading.app.subscriber;

import com.trading.app.api.SubscriptionConfig;
import com.trading.app.api.TradeHandler;

import java.util.Collection;

public class SubscriptionConfigImpl implements SubscriptionConfig {

  private final String symbol;
  private final Collection<TradeHandler> handlers;

  public SubscriptionConfigImpl(String symbol, Collection<TradeHandler> handlers) {
    this.symbol = symbol;
    this.handlers = handlers;
  }

  @Override
  public String getSymbol() {
    return symbol;
  }

  @Override
  public Collection<TradeHandler> getHandlers() {
    return handlers;
  }
}
