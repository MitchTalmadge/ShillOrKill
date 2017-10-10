package com.mitchtalmadge.shillorkill.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SpringBootTest
public class TweetDiscoveryServiceTest {

    @Test
    public void TestIsFirstCoinMentioned() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = TweetDiscoveryService.class.getDeclaredMethod("isFirstCoinMentioned", String.class, String.class);
        method.setAccessible(true);

        Assert.assertTrue((Boolean) method.invoke(null, "coindera: Top 3 \n $VEN $USD on hitbtc +119% $VEN $ETH on hitbtc +43% $BTC $AGRS on BittrexExchange +19%", "$VEN"));
        Assert.assertFalse((Boolean) method.invoke(null, "Top 3 \n $GAME $BTC on @hitbtc +18% $BAS $ETH on @hitbtc +18% $VEN $BTC on @hitbtc +17%", "$VEN"));
    }

}