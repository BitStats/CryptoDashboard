package mx.itam.Backend;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class DataServices {
    private final static String ENDPOINT = "https://api.binance.com/";
    private ArrayList<Symbol> symbols;
    private ArrayList<Interval> intervals;
    private ArrayList<DataHistorica> historicData;
    private ArrayList<ActualPrice> actualPrices;
    private Symbol selectedSymbol;
    private Interval selectedInterval;
    private DataHistorica actualData;
    private int limit;

    public DataServices(){
        symbols = new ArrayList<>();
        actualPrices = new ArrayList<>();
        try{
            fetchSymbols();
            fetchIntervals();
            Collections.sort(symbols);
            int index = symbols.indexOf(new Symbol("BTCUSDT"));
            selectedSymbol = symbols.get(index);
            selectedInterval = intervals.get(10);
            limit = 15;
            fetchHistoricData(selectedSymbol,selectedInterval,limit);
            fetchActualData(selectedSymbol);
            fetchActualPrices();
        }catch(java.io.IOException ex){
            //Error de conexion en API: Loggear este error
            System.out.println(ex.getMessage());
        }
    }
    //Funciones de Obtener datos
    private void fetchSymbols() throws java.io.IOException{
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(ENDPOINT+"api/v1/exchangeInfo")
                .get()
                .build();
        Response response = client.newCall(request).execute();
        if(response.isSuccessful()) {
            JSONObject jsonResponse = new JSONObject(response.body().string());
            JSONArray symbolArray=jsonResponse.getJSONArray("symbols");
            for(int i=0; i< symbolArray.length();i++){
                JSONObject symbolObj = symbolArray.getJSONObject(i);
                symbols.add(new Symbol(symbolObj.getString("symbol"),symbolObj.getString("baseAsset"),symbolObj.getString("quoteAsset")));
            }
            System.out.println("Cargo bien: fetchSymbols");
        }else{
            //Error de que no conecto con la API
            System.out.println("Error getSymbols");
        }

    }
    private void fetchIntervals(){
        intervals = new ArrayList<>();
        intervals.add(new Interval("1m",1,0,0));
        intervals.add(new Interval("3m",3,0,0));
        intervals.add(new Interval("5m",5,0,0));
        intervals.add(new Interval("15m",15,0,0));
        intervals.add(new Interval("30m",30,0,0));
        intervals.add(new Interval("1h",0,1,0));
        intervals.add(new Interval("4h",0,4,0));
        intervals.add(new Interval("12h",0,12,0));
        intervals.add(new Interval("1d",0,0,1));
        intervals.add(new Interval("3d",0,0,3));
        intervals.add(new Interval("1w",0,0,7));
        intervals.add(new Interval("1M",0,0,30));
        System.out.println("Cargo los intervalos");
    }
    private void fetchHistoricData(Symbol symbol, Interval interval, int limit) throws java.io.IOException{
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(ENDPOINT+"api/v1/klines?symbol="+symbol.getSymbol()+"&interval="+interval.getTimeCode()+"&startTime="+calculateTimeLapseInMilis(interval,limit)+"&limit="+limit)
                .get()
                .build();
        //System.out.println(ENDPOINT+"api/v1/klines?symbol="+symbol.getSymbol()+"&interval="+interval.getTimeCode()+"&startTime="+ calculateTimeLapseInMilis(interval,limit)+"&limit="+limit);
        Response response = client.newCall(request).execute();
        if(response.isSuccessful()){
            historicData = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(response.body().string());
            for(int i=0;i<jsonArray.length();i++){
                JSONArray dataJson = jsonArray.getJSONArray(i);
                historicData.add(new DataHistorica(dataJson.getLong(0), dataJson.getDouble(1),dataJson.getDouble(2),dataJson.getDouble(3),dataJson.getDouble(4),interval));
            }
            System.out.println("Cargo fetchHistoricData");
        }else{
            //Error de conexion con la API
            System.out.println("Error fetchHistoricData");
        }
    }
    private void fetchActualData(Symbol symbol) throws java.io.IOException{
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(ENDPOINT+"api/v3/ticker/price?symbol="+symbol.getSymbol())
                .get()
                .build();
        Response response = client.newCall(request).execute();
        if(response.isSuccessful()){
                JSONObject jsonObject = new JSONObject(response.body().string());
                double price =jsonObject.getDouble("price");
                actualData = new DataHistorica(System.currentTimeMillis(),price,price,price,price,new Interval(1000));
            System.out.println("Cargo fetchActualData");
        }else{
            //Error de conexion con la api
        }
    }
    private void fetchActualPrices() throws java.io.IOException{
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(ENDPOINT+"api/v3/ticker/price")
                    .get()
                    .build();
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()){
                JSONArray jsonArray = new JSONArray(response.body().string());
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    double price = jsonObject.getDouble("price");
                    Symbol symbol = new Symbol(jsonObject.getString("symbol"),"","");
                    int indexSymbol = symbols.indexOf(symbol);
                    if(symbol.equals(selectedSymbol)){
                        actualData = new DataHistorica(0,price,price,price,price,new Interval(1000));
                    }
                    if(indexSymbol<0){
                        System.out.println("No existe el simbolo, se omite");
                    }else{
                        actualPrices.add(new ActualPrice(symbols.get(indexSymbol),price));
                    }
                }
            }else{
                //Error en el API
                System.out.println("Error fetchActualPrice");
            }
        System.out.println("cargo fetchActualPrices");
    }
    //Generadores de data
    public Double[] generateChartData(){
        if(historicData == null || historicData.isEmpty()){
            return null;
            //Error no existe data
        }else{
            Double[] data_double = new Double[historicData.size()];
            for(int i = 0; i< data_double.length;i++){
                data_double[i] = historicData.get(i).getClosePrice();
            }
            return data_double;
        }
    }
    public String[] generateChartAxis(){
        if(historicData == null || historicData.isEmpty()){
            return null;
            //Error no existe data
        }else{
            String[] data_string = new String[historicData.size()];
            for(int i = 0; i< data_string.length;i++){
                data_string[i] = historicData.get(i).beautyDate();
            }
            return data_string;
        }

    }
    public void regenerateHistoricData() throws java.io.IOException{
        fetchHistoricData(selectedSymbol,selectedInterval,limit);
        fetchActualData(selectedSymbol);
    }
    public void regenerateActualPrices() throws java.io.IOException{
        fetchActualPrices();
    }
    // Getters
    public ArrayList<DataHistorica> getHistoricData() {
        return historicData;
    }
    public ArrayList<Symbol> getSymbols() {
        return symbols;
    }
    public ArrayList<Interval> getIntervals() {
        return intervals;
    }
    public Symbol getSelectedSymbol() {
        return selectedSymbol;
    }
    public Interval getSelectedInterval() {
        return selectedInterval;
    }
    public DataHistorica getActualData() throws java.io.IOException {
        fetchActualData(selectedSymbol);
        return actualData;
    }
    public ArrayList<ActualPrice> getActualPrices() {
        return actualPrices;
    }

    //Aux Functions
    private long calculateTimeLapseInMilis(Interval interval, int limit){
        return System.currentTimeMillis() - interval.getTimeInMilis()*limit;
    }
    //Setters
    public void setSelectedSymbol(Symbol selectedSymbol) {
        this.selectedSymbol = selectedSymbol;
    }
    public void setSelectedInterval(Interval selectedInterval) {
        this.selectedInterval = selectedInterval;
    }
    public void setLimit(int limit) {
        this.limit = limit;
    }
}

