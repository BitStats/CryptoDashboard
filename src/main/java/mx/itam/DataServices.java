package mx.itam;

import java.util.ArrayList;

public class DataServices {
    private final static String Endpoint = "https://api.binance.com/";
    private ArrayList<String> Symbol_List;

    public DataServices(){
        Symbol_List = new ArrayList<>();
    }

}
