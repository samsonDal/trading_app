package com.trading.app.listener;

import com.trading.app.api.TradeHandler;
import com.trading.app.api.SubscriptionCache;
import com.trading.app.api.SubscriptionConfig;
import com.trading.app.feed.TradeFeed;
import com.trading.app.handlers.LargestTradeHandler;
import com.trading.app.model.Trade;
import com.trading.app.subscriber.SubscriptionCacheImpl;
import com.trading.app.subscriber.SubscriptionConfigImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TradeFeedListenerTest {

    @Test
    public void testOnTrade() {

        SubscriptionCache subscriptionCache = mock(SubscriptionCacheImpl.class);
        TradeFeed tradeFeed = mock(TradeFeed.class);
        TradeFeedListener feedListener = new TradeFeedListener(subscriptionCache, tradeFeed);
        feedListener.onTrade(mock(Trade.class));

        assertEquals(1, feedListener.getQueueSize());
    }

    @Test
    public void testProcessTrade() {

        SubscriptionCache subscriptionCache = mock(SubscriptionCacheImpl.class);
        SubscriptionConfig subscriptionConfig = mock(SubscriptionConfigImpl.class);
        TradeHandler handler = mock(LargestTradeHandler.class);
        List<TradeHandler> handlers = new ArrayList<>();
        handlers.add(handler);
        TradeFeed tradeFeed = mock(TradeFeed.class);
        TradeFeedListener feedListener = new TradeFeedListener(subscriptionCache, tradeFeed);
        Trade trade = mock(Trade.class);
        String symbol = "XYZ";
        when(trade.getSymbol()).thenReturn(symbol);
        when(subscriptionCache.get(symbol)).thenReturn(subscriptionConfig);
        when(subscriptionConfig.getHandlers()).thenReturn(handlers);
        feedListener.processTrade(trade);
        verify(handler).handle(trade);
    }
}
