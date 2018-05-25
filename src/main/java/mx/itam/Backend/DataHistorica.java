package mx.itam.Backend;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase para manejar los precios de una moneda durante un intervalo
 * @author BitStats
 */
public class DataHistorica {
    private long openTime;
    private double openPrice;
    private double maxPrice;
    private double minPrice;
    private double closePrice;
    private Interval interval;
    private final static Logger logger =
            Logger.getLogger(DataHistorica.class.getName());
    
    /**
     * Método constructor de la clase
     * @param openTime Primer momento en el intervalo
     * @param openPrice Precio que se tuvo en el openTime
     * @param maxPrice Precio máximo que tuvo la moneda en el intervalo
     * @param minPrice Precio mínimo que tuvo la moneda en el intervalo
     * @param closePrice Precio que se tuvo en el final del intervalo
     * @param interval Intervalo que se está midiendo
     */
    public DataHistorica(long openTime, double openPrice, double maxPrice, double minPrice, double closePrice, Interval interval) {
        this.openTime = openTime;
        this.openPrice = openPrice;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.closePrice = closePrice;
        this.interval = interval;
        logger.log(Level.INFO,"Guardando data de la fecha: "+beautyDate());

    }

    
    /**
     * Método para regresar una fecha dada en un formato consistente
     * @return La fecha de los datos historicos en formato
     */
    public String beautyDate(){
       if(interval.getTimeCode().contains("m")){
           return new SimpleDateFormat("HH:mm:ss").format(new Date(openTime));
       }
       if(interval.getTimeCode().contains("h")){
           return new SimpleDateFormat("dd-HH:mm").format(new Date(openTime));
       }
       if(interval.getTimeCode().contains("d") || interval.getTimeCode().contains("w")){
           return new SimpleDateFormat("dd-MMM").format(new Date(openTime));
       }
       if(interval.getTimeCode().contains("M")){
           return new SimpleDateFormat("dd/MMM/yyyy").format(new Date(openTime));
       }
       return "";
    }
    
    /**
     * 
     * @return Momento en que comienza el intervalo
     */
    public long getOpenTime() {
        return openTime;
    }
    
    /**
     * 
     * @return Fecha formateada
     */   
    public String getDate(){
        return Utils.getDateFromEpoch(openTime);
    }
    
    /**
     *
     * @return Precio al comienzo del intervalo
     */
    public double getOpenPrice() {
        return openPrice;
    }
    
    /**
     *
     * @return Precio máximo durante el intervalo
     */
    public double getMaxPrice() {
        return maxPrice;
    }
    
    /**
     * 
     * @return Precio mínimo durante el intervalo
     */
    public double getMinPrice() {
        return minPrice;
    }
    
    /**
     * 
     * @return Precio al cierre del intervalo
     */
    public double getClosePrice() {
        return closePrice;
    }
    
    /**
     *
     * @return Precio al principio del intervalo en formato de moneda
     */
    public String getOpenPriceBeauty(){
        return Utils.currencyFormat(openPrice,10);
    }
    
    /**
     * 
     * @return Precio al cierre del intervalo en formato de moneda
     */
    public String getClosePriceBeauty(){
        return Utils.currencyFormat(closePrice,10);
    }
    
    /**
     * 
     * @return Precio máximo durante el intervalo en formato de moneda
     */
    public String getMaxPriceBeauty(){
        return Utils.currencyFormat(maxPrice,10);
    }
    
    /**
     * 
     * @return Precio mínimo durante el intervalo en formato de moneda
     */
    public String getMinPriceBeauty(){
        return Utils.currencyFormat(minPrice,10);
    }
    
    /**
     * 
     * @return String conteniendo los datos históricos de la moneda en el intervalo
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Open Time: ").append(Utils.getDateFromEpoch(openTime)).append("\tOpen Price: ").append(openPrice);
        sb.append("\tMax Price: ").append(maxPrice).append("\tMin Price: ").append(minPrice);
        sb.append("\tClose Price: ").append(closePrice);
        return sb.toString();
    }
}
