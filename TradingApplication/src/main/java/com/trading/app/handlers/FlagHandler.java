package com.trading.app.handlers;

import com.trading.app.api.FeedObject;
import com.trading.app.api.TradeHandler;
import com.trading.app.model.Trade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlagHandler implements TradeHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(FlagHandler.class);
  private int numberOfTradesWithFlag;

  @Override
  public void handle(Trade trade) {
    if (trade.getFlag().equals(FeedObject.Flag.X)) numberOfTradesWithFlag++;
  }

  @Override
  public type getType() {
    return type.FLAG;
  }

  @Override
  public void printResult() {
    LOGGER.info(" number of trades with flag {} is {}", FeedObject.Flag.X, numberOfTradesWithFlag);
  }

  int getNumberOfTradesWithFlag() {
    return numberOfTradesWithFlag;
  }
}
