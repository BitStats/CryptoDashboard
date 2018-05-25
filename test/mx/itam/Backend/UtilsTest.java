package mx.itam.Backend;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class UtilsTest {

    @Test
    public void currencyFormat() {
        double decimalNumber = 0.0;
        int decimals = 7;
        assertEquals("0.00000000",Utils.currencyFormat(decimalNumber,decimals));
    }

    @Test
    public void getDateFromEpoch() {
        long timeInMilis = 0;
        assertEquals("Fecha",Utils.getDateFromEpoch(timeInMilis));
    }
}