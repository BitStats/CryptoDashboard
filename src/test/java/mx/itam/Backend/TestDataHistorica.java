package mx.itam.Backend;

import java.util.logging.Level;
import java.util.logging.Logger;
import mx.itam.Backend.DataHistorica;
import mx.itam.Backend.Interval;
import org.junit.Assert;
import org.junit.Test;

public class TestDataHistorica {

    @Test
    public void testBeautyDateOne() {
    	int min = 1;
        int hour = 0;
        int day = 0;
        Interval intervalo = new Interval("1m", min, hour, day);
        DataHistorica dh = new DataHistorica(0, 0.0, 0.0, 0.0, 0.0, intervalo);
        assert("18:00:00".equals(dh.beautyDate()));
    }

    @Test
    public void testBeautyDateTwo() {
    	int min = 1;
        int hour = 0;
        int day = 0;
        Interval intervalo = new Interval("1m", min, hour, day);
        DataHistorica dh = new DataHistorica(829996396000L, 0.0, 0.0, 0.0, 0.0, intervalo);
        assert("05:33:16".equals(dh.beautyDate()));
    }

    @Test
    public void testBeautyDateThree() {
    	int min = 1;
        int hour = 0;
        int day = 0;
        Interval intervalo = new Interval("1m", min, hour, day);
        DataHistorica dh = new DataHistorica(1902950416000L, 0.0, 0.0, 0.0, 0.0, intervalo);
        assert("16:20:16".equals(dh.beautyDate()));
    }
}