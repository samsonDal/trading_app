package com.trading.app.handlers;

import com.trading.app.api.TradeHandler;
import com.trading.app.model.Trade;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AveragePriceHandlerTest {

    @Test
    public void testType() {
        AveragePriceHandler handler = new AveragePriceHandler();
        assertEquals(handler.getType(), TradeHandler.type.AVG_PRICE);
    }

    @Test
    public void testHandleTrade() {
        AveragePriceHandler handler = new AveragePriceHandler();
        Trade trade = mock(Trade.class);
        when(trade.getPrice()).thenReturn(4D);
        handler.handle(trade);
        verify(trade).getPrice();
        assertEquals(4D, handler.getAveragePrice(), 1e-6);
        when(trade.getPrice()).thenReturn(10D);
        handler.handle(trade);
        assertEquals(7D, handler.getAveragePrice(), 1e-6);


    }
}
