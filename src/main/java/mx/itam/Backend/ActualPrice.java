package mx.itam.Backend;

import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActualPrice {
    private Symbol symbol;
    private double price;
    private final static Logger logger =
            Logger.getLogger(ActualPrice.class.getName());
    public ActualPrice(Symbol symbol, double price) {

        this.symbol = symbol;
        this.price = price;
        logger.log(Level.INFO,"Guardando precio actual");
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }
    public String currencyFormat(){
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatter.setMinimumFractionDigits(10);
        return formatter.format(price);
    }
}
