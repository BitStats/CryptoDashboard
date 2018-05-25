package mx.itam.Backend;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

class Utils {
    /**
     * Regresa la representacion con el numero de decimales que queramos
     *
     * @param currency double
     * @param decimals numero de digitos decimales
     * @return
     */
    public static String currencyFormat(double currency, int decimals) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatter.setMinimumFractionDigits(decimals); //Especifica 10 como la cantidad mínima de dígitos fraccionarios
        return formatter.format(currency);
    }

    /**
     * @param time Un momento en fecha UNIX
     * @return La fecha UNIX convertida a formato dd/MM/yyyy
     */
    public static String getDateFromEpoch(long time) {
        return new SimpleDateFormat("dd/MM/yyyy").format(new Date(time));
    }
    /**
     * @param interval
     * @param limit
     * @return El tiempo transcurrido, en milisegundos, desde el inicio del intervalo hasta el momento actual en formato para la API
     */
    public static long calculateTimeLapseInMilis(Interval interval, int limit) {
        return System.currentTimeMillis() - interval.getTimeInMilis() * limit;
    }
}
