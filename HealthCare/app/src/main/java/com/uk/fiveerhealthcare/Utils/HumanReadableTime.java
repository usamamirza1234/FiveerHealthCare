package com.uk.fiveerhealthcare.Utils;

import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class HumanReadableTime {
    public final static long NOW = 0;
    public final static long ONE_SECOND = DateUtils.SECOND_IN_MILLIS;
    public final static long ONE_MINUTE = DateUtils.MINUTE_IN_MILLIS;
    public final static long ONE_HOUR = DateUtils.HOUR_IN_MILLIS;
    public final static long ONE_DAY = DateUtils.DAY_IN_MILLIS;

    private HumanReadableTime() {
    }

    //Get e.g: Yesterday, Today, Tomorrow OR date in given format
    public static String getDayOnly(Date _DateTime, String _DefaultFormat) {

        StringBuffer res = new StringBuffer();

        if (DateUtils.isToday(_DateTime.getTime()))
            res.append("Today");
        else if (DateUtils.isToday(_DateTime.getTime() - ONE_DAY))
            res.append("Tomorrow");
        else if (DateUtils.isToday(_DateTime.getTime() + ONE_DAY))
            res.append("Yesterday");
        else
            res.append(getFormattedDate(_DateTime, _DefaultFormat));

        return res != null ? res.toString() : "";

    }

    //Get only single unit estimated time  e.g: 1 year ago | 2 weeks ago | 5 months ago.
    public static String getEstimatedTimeElapsed(Date _DateTime) {


        String strJustNow,
                strSecsSingle, strSecsMultiple,
                strMinSingle, strMinMultiple,
                strHourSingle, strHourMultiple,
                strDaySingle, strDayMultiple,
                strDayYesterday, strDayTomorrow,
                strWeekSingle, strWeekMultiple,
                strMonthSingle, strMonthMultiple,
                strYearSingle, strYearMultiple,
                titlePast, titleFuture;

        if (!com.uk.fiveerhealthcare.AppConfig.getInstance().isEnglishMode) {
            //Arabic mode
            strJustNow = "الآن";

            strSecsSingle = "ثانيا";
            strSecsMultiple = "ثواني";

            strMinSingle = "دقيقة";
            strMinMultiple = "الدقائق";

            strHourSingle = "ساعة";
            strHourMultiple = "ساعات";

            strDaySingle = "يوم";
            strDayMultiple = "أيام";
            strDayYesterday = "في الامس";
            strDayTomorrow = "غدا";

            strWeekSingle = "أسبوع";
            strWeekMultiple = "أسابيع";

            strMonthSingle = "شهر";
            strMonthMultiple = "الشهور";

            strYearSingle = "سنة";
            strYearMultiple = "سنوات";

            titlePast = " منذ";
            titleFuture = " متبق";
        } else {
            //Default mode
            strJustNow = "Just now";

            strSecsSingle = "sec";
            strSecsMultiple = "secs";

            strMinSingle = "min";
            strMinMultiple = "mins";

            strHourSingle = "hr";
            strHourMultiple = "hrs";

            strDaySingle = "day";
            strDayMultiple = "days";
            strDayYesterday = "Yesterday";
            strDayTomorrow = "Tomorrow";

            strWeekSingle = "week";
            strWeekMultiple = "weeks";

            strMonthSingle = "month";
            strMonthMultiple = "months";

            strYearSingle = "year";
            strYearMultiple = "years";

            titlePast = " ago";
            titleFuture = " remaining";
        }


        long secsPrevious = _DateTime.getTime() / 1000;
        long secsNow = System.currentTimeMillis() / 1000;

        // Calculate difference in milliseconds
        long diff = secsNow - secsPrevious;
        boolean isPastTime = diff > 0;

        diff = Math.abs(diff);

        // Calculate difference in seconds
        long diffSeconds = diff;

        // Calculate difference in minutes
        long diffMinutes = diff / (60);

        // Calculate difference in hours
        long diffHours = diff / (60 * 60);

        // Calculate difference in days
        long diffDays = diff / (60 * 60 * 24);

        // Calculate difference in weeks
        long diffWeeks = diff / (60 * 60 * 24 * 7);

        // Calculate difference in months
        long diffMonths = diff / (60 * 60 * 24 * 30);

        // Calculate difference in years
        long diffYears = diff / (60 * 60 * 24 * 365);


        String timeElapsed = "";

        String tempTitle = isPastTime ? titlePast : titleFuture;

        if (diffYears > 0) {
            timeElapsed = com.uk.fiveerhealthcare.AppConfig.getInstance().numberToCurrLang(diffYears + "") +
                    " " + (diffYears > 1 ? strYearMultiple : strYearSingle) + tempTitle;
        } else if (diffMonths > 0) {
            timeElapsed = com.uk.fiveerhealthcare.AppConfig.getInstance().numberToCurrLang(diffMonths + "") +
                    " " + (diffMonths > 1 ? strMonthMultiple : strMonthSingle) + tempTitle;
        } else if (diffWeeks > 0) {
            timeElapsed = com.uk.fiveerhealthcare.AppConfig.getInstance().numberToCurrLang(diffWeeks + "") +
                    " " + (diffWeeks > 1 ? strWeekMultiple : strWeekSingle) + tempTitle;
        } else if (diffDays > 0) {
            if (diffDays == 1) {
                timeElapsed = isPastTime ? strDayYesterday : strDayTomorrow;
            } else {
                timeElapsed = com.uk.fiveerhealthcare.AppConfig.getInstance().numberToCurrLang(diffDays + "") +
                        " " + strDayMultiple + tempTitle;
            }
        } else if (diffHours > 0) {
            timeElapsed = com.uk.fiveerhealthcare.AppConfig.getInstance().numberToCurrLang(diffHours + "") +
                    " " + (diffHours > 1 ? strHourMultiple : strHourSingle) + tempTitle;
        } else if (diffMinutes > 0) {
            timeElapsed = com.uk.fiveerhealthcare.AppConfig.getInstance().numberToCurrLang(diffMinutes + "") +
                    " " + (diffMinutes > 1 ? strMinMultiple : strMinSingle) + tempTitle;
        } else if (diffSeconds > 10) {
            timeElapsed = com.uk.fiveerhealthcare.AppConfig.getInstance().numberToCurrLang(diffSeconds + "") +
                    " " + (diffSeconds > 1 ? strSecsMultiple : strSecsSingle) + tempTitle;
        } else {//diff few Seconds
            timeElapsed = strJustNow;
        }

        return timeElapsed;

    }

    //Get e.g: 3 days, 10 hours, 1 minute and 4 seconds ago OR date in given format
    public static String getTimeDuration(Date _DateTime, String _DefaultFormat, boolean isSingleUnit) {

        boolean oneUnitPicked = false;

        //TimeZone Offset
        Calendar rightNow = Calendar.getInstance();
        long offset = 0;//rightNow.get(Calendar.ZONE_OFFSET) +rightNow.get(Calendar.DST_OFFSET);

        long currTimeStamp = System.currentTimeMillis() - offset;

        long duration = currTimeStamp - _DateTime.getTime();

        StringBuffer res = new StringBuffer();
        long temp = 0;
        if ((duration) >= NOW && duration < (ONE_DAY * 6)) {
            temp = duration / ONE_DAY;
            if (temp > 0) {
                duration -= temp * ONE_DAY;
                res.append(temp).append(" day").append(temp > 1 ? "s" : "");
                res.append((duration >= ONE_MINUTE && !isSingleUnit) ? ", " : "");
                oneUnitPicked = isSingleUnit ? true : false;
            }

            temp = duration / ONE_HOUR;
            if (temp > 0 && !oneUnitPicked) {
                duration -= temp * ONE_HOUR;
                res.append(temp).append(" hour").append(temp > 1 ? "s" : "");
                res.append((duration >= ONE_MINUTE && !isSingleUnit) ? ", " : "");
                oneUnitPicked = isSingleUnit ? true : false;
            }

            temp = duration / ONE_MINUTE;
            if (temp > 0 && !oneUnitPicked) {
                duration -= temp * ONE_MINUTE;
                res.append(temp).append(" minute").append(temp > 1 ? "s" : "");
                res.append((duration >= ONE_SECOND && !isSingleUnit) ? " and " : "");
                oneUnitPicked = isSingleUnit ? true : false;
            }

            temp = duration / ONE_SECOND;
            if (temp > 0 && !oneUnitPicked) {
                res.append(temp).append(" second").append(temp > 1 ? "s" : "");
            } else if (temp == 0 && !oneUnitPicked) {
                res.append("a moment");
            }
            res.append(" ago");
            return res.toString();
        } else {
            //Ahead of time
            res.append(getFormattedDate(_DateTime, _DefaultFormat));
            return res.toString();
        }
    }

    private static String getFormattedDate(Date _DateTime, String _DefaultFormat) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(_DefaultFormat);
            return (formatter.format(_DateTime));
        } catch (Exception e) {
            return "";
        }
    }

    public static Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * converts time (in milliseconds) to human-readable format
     * "<w> days, <x> hours, <y> minutes and (z) seconds"
     */
    public static String millisToLongDHMS(Date _DateTime, String _DefualtFormat) {

        long currTimeStamp = System.currentTimeMillis();

        long duration = _DateTime.getTime() - currTimeStamp;

        StringBuffer res = new StringBuffer();
        long temp = 0;
        if (duration >= NOW) {
            temp = duration / ONE_DAY;
            if (temp > 0) {
                duration -= temp * ONE_DAY;
                res.append(temp).append(" day").append(temp > 1 ? "s" : "")
                        .append(duration >= ONE_MINUTE ? ", " : "");
            }

            temp = duration / ONE_HOUR;
            if (temp > 0) {
                duration -= temp * ONE_HOUR;
                res.append(temp).append(" hour").append(temp > 1 ? "s" : "")
                        .append(duration >= ONE_MINUTE ? ", " : "");
            }

            temp = duration / ONE_MINUTE;
            if (temp > 0) {
                duration -= temp * ONE_MINUTE;
                res.append(temp).append(" minute").append(temp > 1 ? "s" : "");
            }

            if (!res.toString().equals("") && duration >= ONE_SECOND) {
                res.append(" and ");
            }

            temp = duration / ONE_SECOND;
            if (temp > 0) {
                res.append(temp).append(" second").append(temp > 1 ? "s" : "");
            }
            return res.toString();
        } else if (duration < ONE_SECOND) {
            return _DefualtFormat;
        } else {
            return "a moment ago";//"0 second";
        }
    }
}
