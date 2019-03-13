package com.example.fanganyi.coinz;


import org.junit.Test;

import static org.junit.Assert.*;

public class Rates_test {

    @Test
    public void testRates(){
        Rates.setDOLR(9.0);
        Rates.setPENY(13.0);
        Rates.setQUID(11.0);
        Rates.setSHIL(7.0);

        assertEquals(7.0, Rates.getSHIL(),0);
        assertEquals(9.0, Rates.getDOLR(),0);
        assertEquals(11.0, Rates.getQUID(), 0);
        assertEquals(13.0, Rates.getPENY(), 0);


    }
}
