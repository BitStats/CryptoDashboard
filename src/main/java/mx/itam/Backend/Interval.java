package mx.itam.Backend;

public class Interval {
    private String timeCode;
    private String fullName;
    private long timeInMilis;

    public long getTimeInMilis() {
        return timeInMilis;
    }

    public Interval(String timeCode, int minutes, int hour, int days) {
        this.timeCode = timeCode;
        this.fullName = beautyInterval(timeCode);
        this.timeInMilis = 1000*60*minutes + 1000*60*60*hour + 1000L*60*60*24*days;
    }
    public Interval(int timeInMilis){
        this.timeInMilis = timeInMilis;
        this.timeCode = "1s";
        this.fullName = "1 segundo";
    }

    @Override
    public String toString() {
        return fullName;
    }

    public String beautyInterval(String st){
        if(st.contains("m")){
            return st.replace("m"," - Minutos");
        }
        if(st.contains("h")){
            return st.replace("h"," - Horas");
        }
        if(st.contains("d")){
            return st.replace("d"," - Dias");
        }
        if(st.contains("M")){
            return st.replace("M"," - Mes");
        }
        if(st.contains("w")){
            return st.replace("w"," - Semana");
        }
        return st;
    }

    public String getTimeCode() {
        return timeCode;
    }
}
