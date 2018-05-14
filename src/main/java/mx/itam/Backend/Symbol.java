package mx.itam.Backend;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Symbol implements Comparable {
    private final static Logger logger =
            Logger.getLogger(Symbol.class.getName());
    private String symbol;
    private String baseAsset;
    private String quoteAsset;

    public Symbol(String symbol, String baseAsset, String quoteAsset) {
        logger.log(Level.INFO,"Leyendo Simbolo:\t"+symbol);
        this.symbol = symbol;
        this.baseAsset = baseAsset;
        this.quoteAsset = quoteAsset;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getBaseAsset() {
        return baseAsset;
    }

    public String getQuoteAsset() {
        return quoteAsset;
    }

    public Symbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return baseAsset+"\\"+quoteAsset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Symbol)) return false;
        Symbol symbol1 = (Symbol) o;
        return Objects.equals(symbol, symbol1.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol);
    }
    public int compareTo(Object obj){
        if (!(obj instanceof Symbol)) {
            return -1;
        }
        Symbol symbol1 = (Symbol) obj;
        return this.symbol.compareTo(symbol1.getSymbol());
    }
}
