package mx.itam.Backend;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que gestiona la generación de los datos necesarios para el funcionamiento del programa.
 * Se encarga de obtener las monedas con que se puede trabajar así como sus datos actuales e históricos durante un intervalo dado.
 *
 * @author BitStats
 */
public class DataServices {
    //Se instancian las variables globales, las cuales serviran
    //para manejar los datos y las conexiones con las bases de 	//datos así como el objeto logger.
    private final static String ENDPOINT = "https://api.binance.com/";
    private final static Logger logger =
            Logger.getLogger(DataServices.class.getName());
    private final ArrayList<Symbol> symbols;
    private ArrayList<Interval> intervals;
    private ArrayList<DataHistorica> historicData;
    private final ArrayList<ActualPrice> actualPrices;
    private Symbol selectedSymbol;
    private Interval selectedInterval;
    private DataHistorica actualData;
    private int selectedLimit;
    private ArrayList<Integer> limitList;

    /**
     * Método constructor de la clase
     */
    public DataServices() {

        logger.log(Level.INFO, "Recargando la información");
        symbols = new ArrayList<>();
        actualPrices = new ArrayList<>();
        //Aquí se envuelven las consultas en un try catch para poder manejar las excepciones.
        try {
            fetchSymbols();
            fetchIntervals();
            fetchLimits();
            Collections.sort(symbols);
            //Se preparan los parámetros para las consultas.
            int index = symbols.indexOf(new Symbol("BTCUSDT"));
            selectedSymbol = symbols.get(index);
            selectedInterval = intervals.get(10);
            selectedLimit = limitList.get(0);
            fetchHistoricData(selectedSymbol, selectedInterval, selectedLimit);
            fetchActualData(selectedSymbol);
            fetchActualPrices();
            logger.log(Level.INFO, "Se cargo la información");
        } catch (java.io.IOException ex) {
            logger.log(Level.SEVERE, "Ocurrio un error critico en la conexion con la API", ex);

        }
    }

    //Funciones de Obtener datos

    /**
     * Genera y guarda símbolos que representan a las monedas seleccionadas
     *
     * @throws java.io.IOException
     */
    private void fetchSymbols() throws java.io.IOException {
        //Se declara un cliente http.
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(ENDPOINT + "api/v1/exchangeInfo")
                .get()
                .build();
        Response response = client.newCall(request).execute();
        //Se verifica que la consulta haya sido correcta en formato 		//Json.
        if (response.isSuccessful()) {
            JSONObject jsonResponse = new JSONObject(response.body().string());
            JSONArray symbolArray = jsonResponse.getJSONArray("symbols");
            //Se cargan cada uno de los símbolos consultados.
            for (int i = 0; i < symbolArray.length(); i++) {
                JSONObject symbolObj = symbolArray.getJSONObject(i);
                symbols.add(new Symbol(symbolObj.getString("symbol"), symbolObj.getString("baseAsset"), symbolObj.getString("quoteAsset")));
            }
            logger.log(Level.INFO, "Se cargaron los simbolos correctamente");
        } else {
            //Error de que no conecto con la API
            logger.log(Level.WARNING, "Error al conectar la API para obtener los simbolos");
        }

    }

    /**
     * Genera un ArrayList de intervalos
     */
    private void fetchIntervals() {
        //Se cargan los valores predeterminados de intervalos.
        intervals = new ArrayList<>();
        intervals.add(new Interval("1m", 1, 0, 0));
        intervals.add(new Interval("3m", 3, 0, 0));
        intervals.add(new Interval("5m", 5, 0, 0));
        intervals.add(new Interval("15m", 15, 0, 0));
        intervals.add(new Interval("30m", 30, 0, 0));
        intervals.add(new Interval("1h", 0, 1, 0));
        intervals.add(new Interval("4h", 0, 4, 0));
        intervals.add(new Interval("12h", 0, 12, 0));
        intervals.add(new Interval("1d", 0, 0, 1));
        intervals.add(new Interval("3d", 0, 0, 3));
        intervals.add(new Interval("1w", 0, 0, 7));
        intervals.add(new Interval("1M", 0, 0, 30));
        logger.log(Level.INFO, "Se cargaron los intervalos");
    }

    /**
     * Genera un ArrayList de limites.
     */
    private void fetchLimits() {
        limitList = new ArrayList<>();
        limitList.add(15);
        limitList.add(20);
        limitList.add(25);
        logger.log(Level.INFO, "Se cargaron los limites");
    }

    /**
     * Genera y guarda los datos históricos
     *
     * @param symbol   La moneda a trabajar
     * @param interval El intervalo cuyos datos históricos se buscan
     * @param limit    Límite aceptable para intentar usar la API
     * @throws java.io.IOException
     */
    private void fetchHistoricData(Symbol symbol, Interval interval, int limit) throws java.io.IOException {
        //Se instancia un cliente http que se encargará de la consulta.
        OkHttpClient client = new OkHttpClient();
        //Se hace la consulta.
        Request request = new Request.Builder()
                .url(ENDPOINT + "api/v1/klines?symbol=" + symbol.getSymbol() + "&interval=" + interval.getTimeCode() + "&startTime=" +Utils.calculateTimeLapseInMilis(interval, limit) + "&limit=" + selectedLimit)
                .get()
                .build();
        Response response = client.newCall(request).execute();
        //SE verifica que la consulta haya sido exitosa y se formatea
        //el objeto JSON con los datos.
        if (response.isSuccessful()) {
            historicData = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(response.body().string());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray dataJson = jsonArray.getJSONArray(i);
                historicData.add(new DataHistorica(dataJson.getLong(0), dataJson.getDouble(1), dataJson.getDouble(2), dataJson.getDouble(3), dataJson.getDouble(4), interval));
            }
            logger.log(Level.INFO, "Se cargó la información historica");
        } else {
            logger.log(Level.SEVERE, "Error al obtener la informacion historica en la llamada al API, error: " + response.code());
        }
    }

    /**
     * Genera y guarda datos actuales para una moneda dada
     *
     * @param symbol La moneda a trabajar
     * @throws java.io.IOException
     */
    private void fetchActualData(Symbol symbol) throws java.io.IOException {
        //Se declara un cliente http encargado de hacer la consulta.
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(ENDPOINT + "api/v3/ticker/price?symbol=" + symbol.getSymbol())
                .get()
                .build();
        //Se ejecuta la consulta.
        Response response = client.newCall(request).execute();
        //Se verifica que la consulta haya sido exitosa y se formatean 		//los datos recibidos. Se mantiene el progreso por medio de
        //logs.
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            double price = jsonObject.getDouble("price");
            actualData = new DataHistorica(System.currentTimeMillis(), price, price, price, price, new Interval(1000));
            logger.log(Level.INFO, "Se cargó la información actual.");
        } else {
            logger.log(Level.SEVERE, "Error al obtener la informacion actual en la llamada de la API, error: " + response.code());
        }
    }

    /**
     * Genera y guarda los datos actuales de las monedas
     *
     * @throws java.io.IOException
     */
    private void fetchActualPrices() throws java.io.IOException {
        //Se instancia un cliente http que hará las consultas.
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(ENDPOINT + "api/v3/ticker/price")
                .get()
                .build();
        //Se hace la consulta.
        Response response = client.newCall(request).execute();
        //Se verifica que los datos hayan sido recibidos correctamente
        // se les formatea y además se mantiene un log.
        if (response.isSuccessful()) {
            JSONArray jsonArray = new JSONArray(response.body().string());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                double price = jsonObject.getDouble("price");
                Symbol symbol = new Symbol(jsonObject.getString("symbol"), "", "");
                int indexSymbol = symbols.indexOf(symbol);
                if (symbol.equals(selectedSymbol)) {
                    actualData = new DataHistorica(0, price, price, price, price, new Interval(1000));
                }
                if (indexSymbol < 0) {
                    logger.log(Level.WARNING, "No sé encontro el simbolo. Al cargar los precios actuales");
                } else {
                    actualPrices.add(new ActualPrice(symbols.get(indexSymbol), price));
                }
            }
            logger.log(Level.INFO, "Se cargaron los precios actuales.");
        } else {
            logger.log(Level.SEVERE, "Error al conectar la llamada de la API, error: " + response.code());
        }

    }
    //Generadores de data

    /**
     * @return Arreglo que contiene los datos históricos
     */
    public Double[] generateChartData() {
        //Se cargan los datos en una tabla para posteriormente
        // graficamente si es que fueron cargados correctamente.
        if (historicData == null || historicData.isEmpty()) {
            logger.log(Level.SEVERE, "No hay información para cargar.");
            return null;

        } else {
            Double[] data_double = new Double[historicData.size()];
            //Mediante un for se recorren los datos y son cargados en su
            //Posición correcta.
            for (int i = 0; i < data_double.length; i++) {
                data_double[i] = historicData.get(i).getClosePrice();
            }
            logger.log(Level.INFO, "Se cargó la información para la grafica.");
            return data_double;
        }
    }

    /**
     * @return Arreglo que contiene las fechas de los datos históricos
     */
    public String[] generateChartAxis() {
        //Se verifica que haya información precargada en las variables
        //globales.
        if (historicData == null || historicData.isEmpty()) {
            logger.log(Level.SEVERE, "No hay información para cargar.");
            return null;

        } else {
            //Se recorren los valores de información de los ejes y se
            //preparan para su posterior despliegue gráfico.
            String[] data_string = new String[historicData.size()];
            for (int i = 0; i < data_string.length; i++) {
                data_string[i] = historicData.get(i).beautyDate();
            }
            logger.log(Level.INFO, "Se cargo la informacion para la grafica.");
            return data_string;
        }

    }

    /**
     * Carga los valores actualizados del método fetchHistoricData(selectedSymbol, selectedInterval, selectedLimit)
     *
     * @throws java.io.IOException
     */
    public void regenerateHistoricData() throws java.io.IOException {
        logger.log(Level.INFO, "Recargando la información historica.");
        fetchHistoricData(selectedSymbol, selectedInterval, selectedLimit);
        fetchActualData(selectedSymbol);

    }

    /**
     * Carga los valores actualizados del método fetchActualPrices()
     *
     * @throws java.io.IOException
     */
    public void regenerateActualPrices() throws java.io.IOException {
        logger.log(Level.INFO, "Recargando la información de precios actuales.");
        fetchActualPrices();
    }
    // A partir de aquí comienzan los Getters, Setters y una función 
    //auxiliar.

    /**
     * @return Arreglo que contiene los datos históricos
     */
    public ArrayList<DataHistorica> getHistoricData() {
        return historicData;
    }

    /**
     * @return Arreglo que contiene los símbolos de las monedas
     */
    public ArrayList<Symbol> getSymbols() {
        return symbols;
    }

    /**
     * @return Arreglo que contiene los intervalos
     */
    public ArrayList<Interval> getIntervals() {
        return intervals;
    }

    /**
     * @return Arreglo que contiene los limites
     */
    public ArrayList<Integer> getLimitList() {
        return limitList;
    }

    /**
     * @return El símbolo seleccionado
     */
    public Symbol getSelectedSymbol() {
        return selectedSymbol;
    }

    /**
     * Establece selectedSymbol como el valor de entrada
     *
     * @param selectedSymbol La moneda seleccionada
     */
    public void setSelectedSymbol(Symbol selectedSymbol) {
        this.selectedSymbol = selectedSymbol;
    }

    /**
     * @return El intervalo seleccionado
     */
    public Interval getSelectedInterval() {
        return selectedInterval;
    }

    /**
     * Establece selectedInterval como el intervalo de entrada
     *
     * @param selectedInterval El intervalo seleccionado
     */
    public void setSelectedInterval(Interval selectedInterval) {
        this.selectedInterval = selectedInterval;
    }

    /**
     * @return Datos actuales de la moneda seleccionada
     * @throws java.io.IOException
     */
    public DataHistorica getActualData() throws java.io.IOException {
        fetchActualData(selectedSymbol);
        return actualData;
    }
    //Aux Functions

    /**
     * @return Arreglo que contiene los datos actuales de las monedas
     */
    public ArrayList<ActualPrice> getActualPrices() {
        return actualPrices;
    }

    //Setters

    /**
     * @return el limite seleccionado
     */
    public int getSelectedLimit() {
        return selectedLimit;
    }

    /**
     * Establece selectedLimit como el valor de entrada
     *
     * @param selectedLimit El límite necesario para trabajar con la API
     */
    public void setSelectedLimit(int selectedLimit) {
        this.selectedLimit = selectedLimit;
    }

}

