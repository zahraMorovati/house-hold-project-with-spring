package ir.maktab.util;

import ir.maktab.data.enums.OrderState;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Convert {

    public static Date parsDate(String strDate) {
        if (strDate != null) {
            try {
                return new SimpleDateFormat("yyyy-mm-dd").parse(strDate);
            } catch (ParseException e) {
                return null;
            }
        }return null;
    }

    public static OrderState toOrderState(String state){
        try {
            return OrderState.valueOf(state);
        }catch (Exception e){
            return null;
        }
    }
}
