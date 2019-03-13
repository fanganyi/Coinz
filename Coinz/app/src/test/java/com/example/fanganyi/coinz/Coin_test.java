package com.example.fanganyi.coinz;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Coin_test {
    //coin will not be null
    private static final Coin coin1 = new Coin(-3.187067851289144, 55.94583163791626,
            "700d-b6f2-da88-a5dc-6002-cabe",9.54859807530815, "PENY","9");
    private static final Coin coin2 = new Coin(-3.1868301923640945, 55.943010536823124,
            "c72a-eaaf-ba19-170e-7b03-974b", 0.5901685593209194, "DOLR", "0");
    private static final Coin coin3 = new Coin(-3.1920692692309576, 55.94345615342773,
            "3be4-6ec8-728f-01c0-1c76-3156", 4.504042669740328, "SHIL", "4");
    private static final Coin coin4 = new Coin(-3.1864827000440195, 55.9429233986976,
            "e97c-cf81-353b-1780-f6dc-1913", 3.1596871075784163, "QUID", "3");


    @Test
    public void testGetValue() {
        assertEquals(9.54859807530815, coin1.getValue(),0);
        assertEquals(0.5901685593209194, coin2.getValue(),0);
        assertEquals(4.504042669740328, coin3.getValue(),0);
        assertEquals(3.1596871075784163, coin4.getValue(),0);
    }

    @Test
    public void testGetCurrency() {
        assertEquals("PENY", coin1.getCurrancy());
        assertEquals("DOLR", coin2.getCurrancy());
        assertEquals("SHIL", coin3.getCurrancy());
        assertEquals("QUID", coin4.getCurrancy());
    }

    @Test
    public void testGetlat() {
        assertEquals(-3.187067851289144, coin1.getLat(),0);
        assertEquals(-3.1868301923640945, coin2.getLat(),0);
        assertEquals(-3.1920692692309576, coin3.getLat(),0);
        assertEquals(-3.1864827000440195, coin4.getLat(),0);
    }

    @Test
    public void testGetLng() {
        assertEquals(55.94583163791626, coin1.getLng(),0);
        assertEquals(55.943010536823124, coin2.getLng(),0);
        assertEquals(55.94345615342773, coin3.getLng(),0);
        assertEquals(55.9429233986976, coin4.getLng(),0);
    }

    @Test
    public void testGetCoinID() {
        assertEquals("700d-b6f2-da88-a5dc-6002-cabe", coin1.getCoinID());
        assertEquals("c72a-eaaf-ba19-170e-7b03-974b", coin2.getCoinID());
        assertEquals("3be4-6ec8-728f-01c0-1c76-3156", coin3.getCoinID());
        assertEquals("e97c-cf81-353b-1780-f6dc-1913", coin4.getCoinID());
    }

    @Test
    public void testGetSymbol() {
        assertEquals("9", coin1.getSymbol());
        assertEquals("0", coin2.getSymbol());
        assertEquals("4", coin3.getSymbol());
        assertEquals("3", coin4.getSymbol());
    }

    @Test
    public void testisBankin() {
        assertEquals(false, coin1.isIsbankin());
        assertEquals(false, coin2.isIsbankin());
        assertEquals(false, coin3.isIsbankin());
        assertEquals(false, coin4.isIsbankin());

        coin1.bankin();
        coin2.bankin();
        coin3.bankin();
        coin4.bankin();
        assertEquals(true, coin1.isIsbankin());
        assertEquals(true, coin2.isIsbankin());
        assertEquals(true, coin3.isIsbankin());
        assertEquals(true, coin4.isIsbankin());
    }


    @Test
    public void testisCllected() {
        assertEquals(false, coin1.isCollected());
        assertEquals(false, coin2.isCollected());
        assertEquals(false, coin3.isCollected());
        assertEquals(false, coin4.isCollected());

        coin1.collected();
        coin2.collected();
        coin3.collected();
        coin4.collected();
        assertEquals(true, coin1.isCollected());
        assertEquals(true, coin2.isCollected());
        assertEquals(true, coin3.isCollected());
        assertEquals(true, coin4.isCollected());
    }


}