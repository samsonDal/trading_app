package com.trading.app.feed;

import com.trading.app.listener.TradeFeedListener;
import com.trading.app.model.Trade;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TradeFeedTest {

    @Test
    public void testSubscription() {
        TradeFeedListener feedListener = mock(TradeFeedListener.class);
        TradeFeed tradeFeed = new TradeFeed();
        tradeFeed.subscribe(feedListener);
        assertEquals(1, tradeFeed.getListeners().size());


    }

    @Test
    public void testUnsubscription() {
        TradeFeedListener feedListener = mock(TradeFeedListener.class);
        TradeFeed tradeFeed = new TradeFeed();
        tradeFeed.subscribe(feedListener);
        assertEquals(1, tradeFeed.getListeners().size());
        tradeFeed.unsubscribe(feedListener);
        assertEquals(0, tradeFeed.getListeners().size());

    }

    @Test
    public void testTickValidTrade() {
        TradeFeedListener feedListener = mock(TradeFeedListener.class);
        TradeFeed tradeFeed = new TradeFeed();
        tradeFeed.subscribe(feedListener);
        Trade trade = mock(Trade.class);
        tradeFeed.tick(trade);
        verify(feedListener).onTrade(trade);
    }

    @Test
    public void testTickNullTrade() {
        TradeFeedListener feedListener = mock(TradeFeedListener.class);
        TradeFeed tradeFeed = new TradeFeed();
        tradeFeed.subscribe(feedListener);
        tradeFeed.tick(null);
        verify(feedListener, times(0)).onTrade(any(Trade.class));
    }
}
