package com.trading.app.subscriber;

import com.trading.app.api.SubscriptionConfig;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class SubscriptionConfigTest {

    @Test
    public void testSubscriptionCache() {

        SubscriptionCacheImpl subscriptionCache = new SubscriptionCacheImpl();
        String ric = "ric";
        SubscriptionConfig subscriptionConfig = mock(SubscriptionConfig.class);
        subscriptionCache.put(ric, subscriptionConfig);
        assertEquals(1, subscriptionCache.getAll().size());
        assertEquals(subscriptionConfig, subscriptionCache.get("ric"));
    }
}
