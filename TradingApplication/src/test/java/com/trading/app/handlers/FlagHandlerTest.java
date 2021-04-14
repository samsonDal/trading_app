package com.trading.app.handlers;

import com.trading.app.api.FeedObject;
import com.trading.app.api.TradeHandler;
import com.trading.app.model.Trade;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FlagHandlerTest {

    @Test
    public void testType() {
        FlagHandler handler = new FlagHandler();
        assertEquals(TradeHandler.type.FLAG, handler.getType());
    }

    @Test
    public void testHandleTrade() {
        FlagHandler handler = new FlagHandler();
        Trade trade = mock(Trade.class);
        when(trade.getFlag()).thenReturn(FeedObject.Flag.X);
        handler.handle(trade);
        assertEquals(1, handler.getNumberOfTradesWithFlag());
        when(trade.getFlag()).thenReturn(FeedObject.Flag.A);
        handler.handle(trade);
        assertEquals(1, handler.getNumberOfTradesWithFlag());
        when(trade.getFlag()).thenReturn(FeedObject.Flag.X);
        handler.handle(trade);
        assertEquals(2, handler.getNumberOfTradesWithFlag());


    }
}
