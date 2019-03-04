package com.wsayan.huckster.core.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

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

    public static String formattedDateAndTime(String dateString) {
        String formattedTime = "";
        SimpleDateFormat sdf = new SimpleDateFormat(GlobalConstants.DATE_FORMAT_PATTERN_ORIGINAL, Locale.US);
        SimpleDateFormat output = new SimpleDateFormat(GlobalConstants.DATE_FORMAT_PATTERN_DISPLAY, Locale.US);
        try {
            Date d = sdf.parse(dateString);
            formattedTime = output.format(d);
        } catch (ParseException ignored) {

        }
        return formattedTime;
    }

    public static String iconUrlMaker(String url) {
        return WebUtils.BASE_IMAGE_URL + "?url=" + url + "&size=70..120..200";
    }

    public static int getSharedPrefListPosition(String[] array, String key) {
        int position = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(key)) {
                position = i;
            }
        }
        return position;
    }

}
