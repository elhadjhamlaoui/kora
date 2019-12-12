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

    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat formatterYear = new SimpleDateFormat("MM/dd/yyyy");

    public static String getCommentDate(Long timeAtMiliseconds) {
        timeAtMiliseconds *= 1000L; //Check if this is unnecessary for your use

        if (timeAtMiliseconds == 0) {
            return "";
        }

        //API.log("Day Ago "+dayago);
        String result = "now";
        String dataSot = formatter.format(new Date());
        Calendar calendar = Calendar.getInstance();

        long dayagolong = timeAtMiliseconds;
        calendar.setTimeInMillis(dayagolong);
        String agoformater = formatter.format(calendar.getTime());

        Date CurrentDate = null;
        Date CreateDate = null;

        try {
            CurrentDate = formatter.parse(dataSot);
            CreateDate = formatter.parse(agoformater);

            long different = Math.abs(CurrentDate.getTime() - CreateDate.getTime());

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            long elapsedDays = different / daysInMilli;
            different = different % daysInMilli;

            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;

            if (elapsedDays == 0) {
                if (elapsedHours == 0) {
                    if (elapsedMinutes == 0) {
                        if (elapsedSeconds < 0) {
                            return "0" + " s";
                        } else {
                            if (elapsedSeconds > 0 && elapsedSeconds < 59) {
                                return "now";
                            }
                        }
                    } else {
                        return String.valueOf(elapsedMinutes) + "m ago";
                    }
                } else {
                    return String.valueOf(elapsedHours) + "h ago";
                }

            } else {
                if (elapsedDays <= 29) {
                    return String.valueOf(elapsedDays) + "d ago";
                }
                if (elapsedDays > 29 && elapsedDays <= 58) {
                    return "1Mth ago";
                }
                if (elapsedDays > 58 && elapsedDays <= 87) {
                    return "2Mth ago";
                }
                if (elapsedDays > 87 && elapsedDays <= 116) {
                    return "3Mth ago";
                }
                if (elapsedDays > 116 && elapsedDays <= 145) {
                    return "4Mth ago";
                }
                if (elapsedDays > 145 && elapsedDays <= 174) {
                    return "5Mth ago";
                }
                if (elapsedDays > 174 && elapsedDays <= 203) {
                    return "6Mth ago";
                }
                if (elapsedDays > 203 && elapsedDays <= 232) {
                    return "7Mth ago";
                }
                if (elapsedDays > 232 && elapsedDays <= 261) {
                    return "8Mth ago";
                }
                if (elapsedDays > 261 && elapsedDays <= 290) {
                    return "9Mth ago";
                }
                if (elapsedDays > 290 && elapsedDays <= 319) {
                    return "10Mth ago";
                }
                if (elapsedDays > 319 && elapsedDays <= 348) {
                    return "11Mth ago";
                }
                if (elapsedDays > 348 && elapsedDays <= 360) {
                    return "12Mth ago";
                }

                if (elapsedDays > 360 && elapsedDays <= 720) {
                    return "1 year ago";
                }

                if (elapsedDays > 720) {
                    Calendar calendarYear = Calendar.getInstance();
                    calendarYear.setTimeInMillis(dayagolong);
                    return formatterYear.format(calendarYear.getTime()) + "";
                }

            }

        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return result;
    }



}
