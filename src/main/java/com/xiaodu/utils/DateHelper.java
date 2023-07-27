package com.xiaodu.utils;


import lombok.extern.log4j.Log4j2;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间操作辅助类
 */
@Log4j2
public class DateHelper {
//    private static final Logger logger = new Logger(MethodHandles.lookup().lookupClass().getName());

    /**
     * the current date
     *
     * @param format 例: yyyyMMdd_HH:mm
     * @return 格式化后时间字符串
     */
    public static String getCurrentDate(String format) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
        return dateFormat.format(date);
    }

    /**
     * for the last day of the next month
     *
     * @param format 例: yyyyMMdd_HH:mm
     * @return 格式化后的最后一天时间字符串
     */
    public static String getNextMonthLastDay(String format) {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat(format, Locale.ENGLISH);
        System.out.println("" + df.format(date));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 2);
        calendar.set(Calendar.DATE, 0);
        return df.format(calendar.getTime());
    }

    /**
     * get past date
     *
     * @param past past day
     * @return past day
     * @author lianlichen1
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        return format.format(today);
    }

    /**
     * 获取时间
     *
     * @param format
     * @return 格式化后的时间字符串
     */
    public static String getTimeStamp(String format) {
        return new SimpleDateFormat(format).format(System.currentTimeMillis());
    }


    /**
     * 等待时长并添加原因
     *
     * @param seconds 等待时间
     */
    public static void waitSeconds(long seconds) {
       waitSeconds(seconds,"");
    }

    /**
     * 等待时长并添加原因
     *
     * @param seconds 等待时间
     * @param reason  等待原因
     */
    public static void waitSeconds(long seconds, String reason) {
        String logStr;
        try {
            if (seconds <= 0) {
                return;
            }
            for (int i = 1; i <= seconds; i++) {
                Thread.sleep(1000);
                if (reason.isEmpty() || reason.equals("")) {
                    logStr = "Think time expect : " + seconds + " seconds actual : " + i + " seconds";
                } else {
                    logStr = "Think time expect : " + seconds + " seconds actual : " + i + " seconds. Reason: " + reason;
                }
                log.info(logStr);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }


}
