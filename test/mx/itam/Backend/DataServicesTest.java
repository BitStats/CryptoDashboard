package mx.itam.Backend;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class DataServicesTest {

    @Test
    public void regenerateHistoricData() {
        //Prueba de estres en API
        DataServices x = new DataServices();
        long start = System.currentTimeMillis();
        int segundos = 60;
        int i = 0;
        while(System.currentTimeMillis()-start > 1000*segundos){
            try {
                x.regenerateHistoricData();
                i++;
            } catch (IOException e) {
                assertTrue("No paso la prueba de estres se alcanzó:"+i,i<0);
            }
        }
        assertTrue("No pasó la prueba de estres se alcanzó: "+i+" llamadas en "+segundos+"s", i>100);
    }
}