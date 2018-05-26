package mx.itam.Backend;

import mx.itam.Backend.DataServices;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class TestDataServices {

    @Test
    public void regenerateHistoricData() {
        //Prueba de estres en API
        /*
        DataServices x = new DataServices();
        long start = System.currentTimeMillis();
        int segundos = 3;
        int i = 0;
        while(System.currentTimeMillis()-start > 1000*segundos){
            try{
                x.regenerateHistoricData();
            }catch(IOException e) {

            }
            i++;
        }
        assertTrue("Si pasó la prueba de estres se alcanzó: "+i+" llamadas en "+segundos+"s", i>100);
        */
    }
}