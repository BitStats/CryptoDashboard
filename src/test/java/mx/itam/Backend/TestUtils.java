package mx.itam.Backend;

import java.util.logging.Level;
import java.util.logging.Logger;
import mx.itam.Backend.Interval;
import mx.itam.Backend.Utils;
import org.junit.Assert;
import org.junit.Test;


import static org.junit.Assert.*;

public class TestUtils {
    private final static Logger logger =
            Logger.getLogger(TestUtils.class.getName());
    @Test
    public void testCurrencyFormatOne() {
        double decimalNumber = 3.1415926535;
        int decimals = 4;
        //logger.log(Level.INFO, Utils.currencyFormat(decimalNumber, decimals));
        assert(Utils.currencyFormat(decimalNumber, decimals).equals("$3.1416"));
    }

    @Test
    public void testCurrencyFormatTwo() {
        double decimalNumber = 123456;
        int decimals = 5;
        //logger.log(Level.INFO, Utils.currencyFormat(decimalNumber, decimals));
        assert(Utils.currencyFormat(decimalNumber, decimals).equals("$123,456.00000"));
    }

    @Test
    public void testCurrencyFormatThree() {
        double decimalNumber = 0.35;
        int decimals = 2;
        //logger.log(Level.INFO, Utils.currencyFormat(decimalNumber, decimals));
        assert(Utils.currencyFormat(decimalNumber, decimals).equals("$0.35"));
    }

    @Test
    public void getDateFromEpochOne() {
        long timeInMillis = 0;
        //logger.log(Level.INFO, Utils.getDateFromEpoch(timeInMillis));
        assert("31/12/1969".equals(Utils.getDateFromEpoch(timeInMillis)));
    }

    @Test
    public void getDateFromEpochTwo() {
        long timeInMillis = 1527395165000L;
        //logger.log(Level.INFO, Utils.getDateFromEpoch(timeInMillis));
        assert("26/05/2018".equals(Utils.getDateFromEpoch(timeInMillis)));
    }

        @Test
    public void getDateFromEpochThree() {
        long timeInMillis = 829996196000L;
        assert("20/04/1996".equals(Utils.getDateFromEpoch(timeInMillis)));
    }


}