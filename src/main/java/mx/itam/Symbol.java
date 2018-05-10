package mx.itam;

import java.util.Objects;

public class Symbol implements Comparable {
    private String symbol;
    private String baseAsset;
    private String quoteAsset;

    public Symbol(String symbol, String baseAsset, String quoteAsset) {
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
