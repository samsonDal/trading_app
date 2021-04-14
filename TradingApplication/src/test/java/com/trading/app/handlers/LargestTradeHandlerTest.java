package com.trading.app.handlers;

import com.trading.app.api.TradeHandler;
import com.trading.app.model.Trade;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LargestTradeHandlerTest {

    @Test
    public void testLargeTrade() {
        LargestTradeHandler handler = new LargestTradeHandler();
        Trade trade = mock(Trade.class);
        when(trade.getSize()).thenReturn(10L);
        handler.handle(trade);
        assertEquals(10L, handler.getLargestTradeSize());
        when(trade.getSize()).thenReturn(5L);
        handler.handle(trade);
        assertEquals(10L, handler.getLargestTradeSize());
    }

    @Test
    public void testType() {
        LargestTradeHandler handler = new LargestTradeHandler();
        assertEquals(TradeHandler.type.LARGEST_TRADE, handler.getType());
    }
}
