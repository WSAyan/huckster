package com.wsayan.huckster.core.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by wahid.sadique on 2/2/2018.
 */

public class CommonOperations {
    public static String listToCommaSeparatedString(List<String> stringList) {
        StringBuilder stringBuilder = new StringBuilder();
        int index = 0;
        for (String string : stringList) {
            stringBuilder.append(string);
            index++;
            if (index != stringList.size()) {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }

    public static String formattedDate(String dateString) {
        String formattedDateSting = "";
        SimpleDateFormat format = new SimpleDateFormat(GlobalConstants.DATE_FORMAT);
        try {
            Date date = format.parse(dateString);
            formattedDateSting = date.toString();
        } catch (ParseException e) {
        }
        return formattedDateSting;
    }
}
