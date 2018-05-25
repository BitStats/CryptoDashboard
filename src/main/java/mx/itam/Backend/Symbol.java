package mx.itam.Backend;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase para definir los símbolos para tipos de cambio
 *
 * @author BitStats
 */
public class Symbol implements Comparable {
    //Se instancian las variables globales del sistema.
    private final static Logger logger =
            Logger.getLogger(Symbol.class.getName());
    private final String symbol;
    private String baseAsset;
    private String quoteAsset;

    /**
     * Método constructor
     *
     * @param symbol
     * @param baseAsset
     * @param quoteAsset
     */
    public Symbol(String symbol, String baseAsset, String quoteAsset) {
        //Se manda un comentario log y ademàs se instancian las variables globales de la clase.
        logger.log(Level.INFO, "Leyendo Simbolo:\t" + symbol);
        this.symbol = symbol;
        this.baseAsset = baseAsset;
        this.quoteAsset = quoteAsset;
    }
    //Métodos get de la clase.

    /**
     * Establece symbol como el valor de entrada
     *
     * @param symbol Monedas a comparar
     */
    public Symbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * @return Las monedas a comparar
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * @return La moneda base
     */
    public String getBaseAsset() {
        return baseAsset;
    }
    //Método por asi decirlo set que modifica una de las variables globales(symbol).

    /**
     * @return La moneda contra la cual se compara
     */
    public String getQuoteAsset() {
        return quoteAsset;
    }

    /**
     * @return Las monedas a comparar en formato XXX\YYY
     */
    @Override
    public String toString() {
        return baseAsset + "\\" + quoteAsset;
    }
    //Métodos que deben estar definidos en cualquier buena clase de Java, es decir, equal,
    //compareTo y un hash.

    /**
     * @param o Objeto a comparar con symbol
     * @return Si el objeto de entrada y symbol son iguales
     */
    @Override
    public boolean equals(Object o) {
        //Se compara el objeto intanciado con otro.
        if (this == o) return true;
        if (!(o instanceof Symbol)) return false;
        //Se devuelve verdadero en caso de que los objetos sean del mismo simbolo.
        Symbol symbol1 = (Symbol) o;
        return Objects.equals(symbol, symbol1.symbol);
    }

    /**
     * @return El hash de symbol
     */
    @Override
    public int hashCode() {
        return Objects.hash(symbol);
    }

    /**
     * @param obj Objeto a comparar con symbol
     * @return Resultado de comparar symbol con el objeto
     */
    public int compareTo(Object obj) {
        if (!(obj instanceof Symbol)) {
            return -1;
        }
        Symbol symbol1 = (Symbol) obj;
        return this.symbol.compareTo(symbol1.getSymbol());
    }
}
