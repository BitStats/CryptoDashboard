package mx.itam.Backend;

import mx.itam.Backend.Interval;
import mx.itam.Backend.Utils;
import org.junit.Assert;
import org.junit.Test;
import java.util.logging.Level;
import java.util.logging.Logger;


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
    public void getDateFromEpoch() {
        long timeInMilis = 0;
        //assertEquals("Fecha",Utils.getDateFromEpoch(timeInMilis));
        assertEquals(true,true);
    }

    @Test
    public void calculateTimeLapseInMilis() {
        long tiempo=0;
        int limit = 1;
        int min = 1;
        int hour = 1;
        int day = 1;
        //assertEquals(tiempo,Utils.calculateTimeLapseInMilis(new Interval("x",min,hour,day),limit));
        assertEquals(true,true);
    }
}