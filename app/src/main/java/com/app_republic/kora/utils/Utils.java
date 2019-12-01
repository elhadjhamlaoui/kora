package com.app_republic.kora.utils;

import android.content.Context;

import com.app_republic.kora.R;
import com.app_republic.kora.model.Player;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    public static String getReadableFullDate(Calendar cal) {
        Date date=cal. getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
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

    public static Player getPlayerFromDepId(Player player, String dep_id) {
        if (player.getOtherInfo() != null)
        for (Player player1 : player.getOtherInfo())
            if (player1.getDepId().equals(dep_id))
                return player1;

        return player;

    }


}
