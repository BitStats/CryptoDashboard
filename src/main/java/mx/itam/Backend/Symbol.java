package mx.itam.Backend;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase para definir los símbolos para tipos de cambio
 * @author BitStats
 */
public class Symbol implements Comparable {
    private final static Logger logger =
            Logger.getLogger(Symbol.class.getName());
    private String symbol;
    private String baseAsset;
    private String quoteAsset;

    /**
     * Método constructor
     * @param symbol
     * @param baseAsset
     * @param quoteAsset 
     */
    public Symbol(String symbol, String baseAsset, String quoteAsset) {
        logger.log(Level.INFO,"Leyendo Simbolo:\t"+symbol);
        this.symbol = symbol;
        this.baseAsset = baseAsset;
        this.quoteAsset = quoteAsset;
    }

    /**
     * 
     * @return Las monedas a comparar
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * 
     * @return La moneda base
     */
    public String getBaseAsset() {
        return baseAsset;
    }

    /**
     * 
     * @return La moneda contra la cual se compara
     */
    public String getQuoteAsset() {
        return quoteAsset;
    }

    /**
     * Establece symbol como el valor de entrada
     * @param symbol Monedas a comparar
     */
    public Symbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * 
     * @return Las monedas a comparar en formato XXX\YYY
     */
    @Override
    public String toString() {
        return baseAsset+"\\"+quoteAsset;
    }

    /**
     * 
     * @param o Objeto a comparar con symbol
     * @return Si el objeto de entrada y symbol son iguales
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Symbol)) return false;
        Symbol symbol1 = (Symbol) o;
        return Objects.equals(symbol, symbol1.symbol);
    }

    /**
     * 
     * @return El hash de symbol
     */
    @Override
    public int hashCode() {
        return Objects.hash(symbol);
    }
    
    /**
     * 
     * @param obj Objeto a comparar con symbol
     * @return Resultado de comparar symbol con el objeto
     */
    public int compareTo(Object obj){
        if (!(obj instanceof Symbol)) {
            return -1;
        }
        Symbol symbol1 = (Symbol) obj;
        return this.symbol.compareTo(symbol1.getSymbol());
    }
}
