package mx.itam;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Collections;

public class DataServices {
    private final static String ENDPOINT = "https://api.binance.com/";
    private ArrayList<Symbol> symbol_List;
    private ArrayList<Interval> intervals;
    private ArrayList<DataHistorica> data_interval;
    public DataServices(){
        symbol_List = new ArrayList<>();
        try{
            getSymbols();
            getInterval();
            Collections.sort(symbol_List);
            int index = symbol_List.indexOf(new Symbol("BTCUSDT"));
            getDataInterval(symbol_List.get(index),intervals.get(10),10);
        }catch(java.io.IOException ex){
            //Error de conexion en API: Loggear este error
            System.out.println(ex.getMessage());
        }
    }
    private void getSymbols() throws java.io.IOException{
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
                symbol_List.add(new Symbol(symbolObj.getString("symbol"),symbolObj.getString("baseAsset"),symbolObj.getString("quoteAsset")));
            }
        }else{
            //Error de que no conecto con la API
            System.out.println("Error getSymbols");
        }
    }
    private void getInterval(){
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
    }
    private long getEpochInMilis(Interval interval, int limit){
        return System.currentTimeMillis() - interval.getTimeInMilis()*limit;
    }
    private void getDataInterval(Symbol symbol, Interval interval, int limit)throws java.io.IOException{
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(ENDPOINT+"api/v1/klines?symbol="+symbol.getSymbol()+"&interval="+interval.getTimeCode()+"&startTime="+getEpochInMilis(interval,limit)+"&limit="+limit)
                .get()
                .build();
        System.out.println(ENDPOINT+"api/v1/klines?symbol="+symbol.getSymbol()+"&interval="+interval.getTimeCode()+"&startTime="+getEpochInMilis(interval,limit)+"&limit="+limit);
        Response response = client.newCall(request).execute();
        if(response.isSuccessful()){
            data_interval = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(response.body().string());
            for(int i=0;i<jsonArray.length();i++){
                JSONArray dataJson = jsonArray.getJSONArray(i);
                data_interval.add(new DataHistorica(dataJson.getLong(0), dataJson.getDouble(1),dataJson.getDouble(2),dataJson.getDouble(3),dataJson.getDouble(4)));
            }
        }else{
            //Error de conexion con la API
            System.out.println("Error getDataInterval");
        }
    }

    public ArrayList<DataHistorica> getData_interval() {
        return data_interval;
    }
}
