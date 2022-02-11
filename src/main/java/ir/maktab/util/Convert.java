package ir.maktab.util;

import ir.maktab.data.enums.OrderState;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Convert {

    public static Date toDate(String strDate) {
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

    public static String toHexString(String string) {
        byte[] ba = string.getBytes(StandardCharsets.UTF_8);
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < ba.length; i++)
            str.append(String.format("%x", ba[i]));
        return str.toString();
    }

    public static String fromHexString(String hex) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < hex.length(); i+=2) {
            str.append((char) Integer.parseInt(hex.substring(i, i + 2), 16));
        }
        return str.toString();
    }
}
