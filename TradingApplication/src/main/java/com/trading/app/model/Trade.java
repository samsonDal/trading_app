package com.trading.app.model;

import com.trading.app.api.FeedObject;

public class Trade implements FeedObject {

  private long timestamp = Long.MIN_VALUE;
  private double price = Double.NaN;
  private String symbol;
  private long size;
  private Flag flag;

  public Trade() {}

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    this.size = size;
  }

  public Flag getFlag() {
    return flag;
  }

  public void setFlag(Flag flag) {
    this.flag = flag;
  }

  @Override
  public String toString() {
    return "[" + symbol + ", " + timestamp + ", " + size + ", " + price + ", " + flag + "]";
  }
}
