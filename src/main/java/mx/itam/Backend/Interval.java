package mx.itam.Backend;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author BitStats
 */
public class Interval {
    private final static Logger logger =
            Logger.getLogger(Interval.class.getName());
    //Se instancian las variables globales de la clase entre ellas 		//el objeto logger para manejar los logs.
    private final String timeCode;
    private final String fullName;
    private final long timeInMilis;

    /**
     * Método constructor
     *
     * @param timeCode código en el cual se mide el intervalo
     * @param minutes  cantidad de minutos
     * @param hour     cantidad de horas
     * @param days     cantidad de días
     */
    public Interval(String timeCode, int minutes, int hour, int days) {
        this.timeCode = timeCode;
        this.fullName = beautyInterval(timeCode);
        //Se convierten los valores de tiempo en milisegundos.
        this.timeInMilis = 1000 * 60 * minutes + 1000 * 60 * 60 * hour + 1000L * 60 * 60 * 24 * days;
        logger.log(Level.INFO, "Creando intervalo:\t" + fullName);
    }

    /**
     * Método constructor
     *
     * @param timeInMilis
     */
    public Interval(int timeInMilis) {
        this.timeInMilis = timeInMilis;
        this.timeCode = "1s";
        this.fullName = "1 segundo";
    }

    /**
     * @return La duración del intervalo en milisegundos
     */
    public long getTimeInMilis() {
        return timeInMilis;
    }

    /**
     * @return El período de tiempo en que se define el intervalo
     */
    @Override
    public String toString() {
        return fullName;
    }

    /**
     * @param st Intervalo a formatear
     * @return El período de tiempo en que se mide el intervalo
     */
    private String beautyInterval(String st) {
        //Se formatea la string recibida para su mejor comprensión.
        if (st.contains("m")) {
            return st.replace("m", " - Minutos");
        }
        if (st.contains("h")) {
            return st.replace("h", " - Horas");
        }
        if (st.contains("d")) {
            return st.replace("d", " - Dias");
        }
        if (st.contains("M")) {
            return st.replace("M", " - Mes");
        }
        if (st.contains("w")) {
            return st.replace("w", " - Semana");
        }
        return st;
    }

    /**
     * @return El código del tiempo
     */
    public String getTimeCode() {
        return timeCode;
    }
}
