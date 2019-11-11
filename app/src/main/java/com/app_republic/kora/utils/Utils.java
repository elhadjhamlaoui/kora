package com.app_republic.kora.utils;

import android.content.Context;
import android.widget.TextView;

import com.app_republic.kora.R;
import com.app_republic.kora.model.Match;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public static long getMillisFromMatchDate(String match) {
        Calendar calendar = Calendar.getInstance();
        String[] date = match.split(" ");
        int year = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]) - 1;
        int day = Integer.parseInt(date[2]);
        int hour = Integer.parseInt(date[3]);
        int minute = Integer.parseInt(date[4]);

        calendar.set(year,month,day,hour,minute);

        return calendar.getTimeInMillis();
    }

    public static String getReadableDate(Calendar cal) {
        Date date=cal. getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate=dateFormat. format(date);

        return formattedDate;
    }

    public static String getReadableDay(Calendar cal, Context context) {
        String[] days = context.getResources().getStringArray(R.array.days);
        String day = days[cal.get(Calendar.DAY_OF_WEEK) - 1];
        return day;
    }

    public static String getFullTime(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm aa");
        String dateString = formatter.format(new Date(time));

        return dateString;

    }

    public static String getRemainingTime(long durationInMillis) {

        long second = (durationInMillis / 1000) % 60;
        long minute = (durationInMillis / (1000 * 60)) % 60;
        long hour = (durationInMillis / (1000 * 60 * 60)) % 24;

        String time = String.format("%02d:%02d:%02d", hour, minute, second);


        return time;
    }

    public static long getMillisFromServerDate(String date) {
        long timeInMilliseconds = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date mDate = sdf.parse(date);
            timeInMilliseconds = mDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        return timeInMilliseconds;


    }


}
