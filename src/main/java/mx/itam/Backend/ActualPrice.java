package mx.itam.Backend;

import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que maneja el precio de una moneda dada en el presente
 * @author BitStats
 */
public class ActualPrice {
    private Symbol symbol;
    private double price;
    private final static Logger logger =
            Logger.getLogger(ActualPrice.class.getName());
    
    /**
     * Método constructor de la clase
     * @param symbol Define la moneda con que se está trabajando
     * @param price Precio actual de la moneda dada
     */
    public ActualPrice(Symbol symbol, double price) {

        this.symbol = symbol;
        this.price = price;
        logger.log(Level.INFO,"Guardando precio actual");
    }

    /**
     * 
     * @return El símbolo de la moneda que se está considerando
     */
    public Symbol getSymbol() {
        return symbol;
    }

    /**
     * 
     * @return El precio de la moneda que se está considerando
     */
    public double getPrice() {
        return price;
    }
    
    /**
     * 
     * @return El precio de la moneda en el formato de moneda $xxxx.xx
     */
    public String currencyFormat(){
        return Utils.currencyFormat(price,10);
    }
}
