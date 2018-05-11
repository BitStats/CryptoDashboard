package mx.itam.Backend;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class DataHistorica {
    private long openTime;
    private double openPrice;
    private double maxPrice;
    private double minPrice;
    private double closePrice;
    private Interval interval;

    public DataHistorica(long openTime, double openPrice, double maxPrice, double minPrice, double closePrice, Interval interval) {
        this.openTime = openTime;
        this.openPrice = openPrice;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.closePrice = closePrice;
        this.interval = interval;
    }

    public static String getDateFromEpoch(long time){
       return new SimpleDateFormat("dd/MM/yyyy").format(new Date(time));
    }
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
    public long getOpenTime() {
        return openTime;
    }
    public String getDate(){
        return getDateFromEpoch(openTime);
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public String getOpenPriceBeauty(){
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatter.setMinimumFractionDigits(10);
        return formatter.format(openPrice);
    }
    public String getClosePriceBeauty(){
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatter.setMinimumFractionDigits(10);
        return formatter.format(closePrice);
    }
    public String getMaxPriceBeauty(){
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatter.setMinimumFractionDigits(10);
        return formatter.format(maxPrice);
    }
    public String getMinPriceBeauty(){
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatter.setMinimumFractionDigits(10);
        return formatter.format(minPrice);
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Open Time: ").append(getDateFromEpoch(openTime)).append("\tOpen Price: ").append(openPrice);
        sb.append("\tMax Price: ").append(maxPrice).append("\tMin Price: ").append(minPrice);
        sb.append("\tClose Price: ").append(closePrice);
        return sb.toString();
    }
}
