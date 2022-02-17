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
        }
        return null;
    }

    public static OrderState toOrderState(String state) {
        if (state == null)
            return OrderState.NO_STATE;
        else {
            if (state.equalsIgnoreCase(OrderState.WAITING_FOR_SPECIALIST_SUGGESTION.toString())) {
                return OrderState.WAITING_FOR_SPECIALIST_SUGGESTION;
            } else if (state.equalsIgnoreCase(OrderState.WAITING_FOR_SPECIALIST_SELECTION.toString())) {
                return OrderState.WAITING_FOR_SPECIALIST_SELECTION;
            } else if (state.equalsIgnoreCase(OrderState.WAITING_FOR_SPECIALIST_TO_COME.toString())) {
                return OrderState.WAITING_FOR_SPECIALIST_TO_COME;
            } else if (state.equalsIgnoreCase(OrderState.DONE.toString())) {
                return OrderState.DONE;
            } else if (state.equalsIgnoreCase(OrderState.PAID.toString())) {
                return OrderState.PAID;
            } else if (state.equalsIgnoreCase(OrderState.STARTED.toString())) {
                return OrderState.STARTED;
            } else return OrderState.NO_STATE;
        }
    }

    public static String toHexString(String string) {
        byte[] ba = string.getBytes(StandardCharsets.UTF_8);
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < ba.length; i++)
            str.append(String.format("%x", ba[i]));
        return str.toString();
    }

    public static String fromHexString(String hex) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < hex.length(); i += 2) {
            str.append((char) Integer.parseInt(hex.substring(i, i + 2), 16));
        }
        return str.toString();
    }
}
