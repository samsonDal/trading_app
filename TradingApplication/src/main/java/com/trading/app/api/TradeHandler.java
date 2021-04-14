package com.trading.app.api;

import com.trading.app.model.Trade;

public interface TradeHandler {

  enum type {
    LARGEST_TRADE {},
    AVG_PRICE {},
    FLAG {}
  }

  void handle(Trade trade);

  type getType();

  void printResult();
}
