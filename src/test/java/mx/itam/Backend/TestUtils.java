package mx.itam.Backend;

import java.util.logging.Level;
import java.util.logging.Logger;
import mx.itam.Backend.Interval;
import mx.itam.Backend.Utils;
import org.junit.Assert;
import org.junit.Test;


import static org.junit.Assert.*;

public class TestUtils {
    @Test
    public void testCurrencyFormatOne() {
        double decimalNumber = 3.1415926535;
        int decimals = 4;
        assertEquals("$3.1416",Utils.currencyFormat(decimalNumber, decimals));
    }

    @Test
    public void testCurrencyFormatTwo() {
        double decimalNumber = 123456;
        int decimals = 5;
        assertEquals("$123,456.00000",Utils.currencyFormat(decimalNumber, decimals));
    }

    @Test
    public void testCurrencyFormatThree() {
        double decimalNumber = 0.35;
        int decimals = 2;
        assertEquals("$0.35",Utils.currencyFormat(decimalNumber, decimals));
    }

    @Test
    public void getDateFromEpochOne() {
        long timeInMillis = 0;
        assertEquals("01/01/1970",Utils.getDateFromEpoch(timeInMillis));
    }

    @Test
    public void getDateFromEpochTwo() {
        long timeInMillis = 1527395165000L;
        assertEquals("27/05/2018",Utils.getDateFromEpoch(timeInMillis));
    }

        @Test
    public void getDateFromEpochThree() {
        long timeInMillis = 829996196000L;
        assertEquals("20/04/1996",Utils.getDateFromEpoch(timeInMillis));
    }


}