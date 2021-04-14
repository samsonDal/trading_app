package com.trading.app.handlers;

import com.trading.app.api.TradeHandler;
import com.trading.app.model.Trade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LargestTradeHandler implements TradeHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(LargestTradeHandler.class);
  long largestTradeSize = Long.MIN_VALUE;

  public void handle(Trade trade) {
    if (largestTradeSize < trade.getSize()) {
      largestTradeSize = trade.getSize();
    }
  }

  @Override
  public type getType() {
    return type.LARGEST_TRADE;
  }

  @Override
  public void printResult() {
    LOGGER.info("Largest trade is {}", largestTradeSize);
  }

  long getLargestTradeSize() {
    return largestTradeSize;
  }
}
