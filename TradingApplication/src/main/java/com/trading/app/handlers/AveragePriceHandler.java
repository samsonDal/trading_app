package com.trading.app.handlers;

import com.trading.app.api.TradeHandler;
import com.trading.app.model.Trade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AveragePriceHandler implements TradeHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(AveragePriceHandler.class);
  private double totalPriceSoFar;
  private int totalTradesSoFar;
  private double avg;

  @Override
  public void handle(Trade trade) {
    totalPriceSoFar += trade.getPrice();
    totalTradesSoFar++;

    avg = totalPriceSoFar / totalTradesSoFar;
  }

  @Override
  public type getType() {
    return type.AVG_PRICE;
  }

  @Override
  public void printResult() {
    LOGGER.info("average price is {}", avg);
  }

  double getAveragePrice() {
    return avg;
  }
}
