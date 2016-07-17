package com.tanona.bill.positiveplasma;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Bill on 7/17/2016.
 *
 * Create a shareable class to get set the current date and time
 *
 */
public class DateGetter {

    public static String strDate;
    public static String strTime;

    public static void getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mformat = new SimpleDateFormat(" yyyy-MM-dd ");
        SimpleDateFormat tformat = new SimpleDateFormat("HH:mm");
        String strDate = mformat.format(calendar.getTime());
        String strTime = tformat.format(calendar.getTime());
    }

}
