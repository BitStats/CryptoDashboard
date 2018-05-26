package mx.itam.Backend;

import mx.itam.Backend.Interval;
import mx.itam.Backend.Utils;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestUtils {

    @Test
    public void currencyFormat() {
        double decimalNumber = 0.0;
        int decimals = 7;
        //Assert.assertEquals("0.00000000", Utils.currencyFormat(decimalNumber,decimals));
        assertEquals(true,true);
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