package mx.itam.Backend;

import java.text.NumberFormat;

public class ActualPrice {
    private Symbol symbol;
    private double price;
    public ActualPrice(Symbol symbol, double price) {
        this.symbol = symbol;
        this.price = price;
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
