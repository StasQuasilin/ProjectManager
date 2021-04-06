package entity.calendar;

import org.json.simple.JSONArray;

public class WeekDays {
    private boolean[] days;

    public WeekDays(boolean[] booleans) {
        days = booleans;
    }

    public WeekDays(int size) {
        days = new boolean[size];
    }

    public boolean[] getDays() {
        return days;
    }

    public JSONArray array(){
        JSONArray array = new JSONArray();
        for(boolean b : days){
            array.add(b);
        }
        return array;
    }

    public void initDays(int val){
        int d = 64;
        for (int i = 7; i > 0; i--){
            if(days[i - 1] = val >= d){
                val = val % d;
            }
            d = (d >> 1);
        }
    }
    public int getDaysValue(){
        int val = 0;
        int d = 1;
        for (boolean day : days) {
            if (day) val += d;
            d = d << 1;
        }
        return val;
    }
}
