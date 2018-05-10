package mx.itam;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

public class DataHistorica {
    private long openTime;
    private double openPrice;
    private double maxPrice;
    private double minPrice;
    private double closePrice;

    public DataHistorica(long openTime, double openPrice, double maxPrice, double minPrice, double closePrice) {
        this.openTime = openTime;
        this.openPrice = openPrice;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.closePrice = closePrice;
    }

    public static String getDateFromEpoch(long time){
        Timestamp stamp = new Timestamp(time);
        Date date = new Date(stamp.getTime());
       return date.toString();
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Open Time: ").append(getDateFromEpoch(openTime)).append("\tOpen Price: ").append(openPrice);
        sb.append("\tMax Price: ").append(maxPrice).append("\tMin Price: ").append(minPrice);
        sb.append("\tClose Price: ").append(closePrice);
        return sb.toString();
    }
}
