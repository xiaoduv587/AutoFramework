package com.xiaodu.utils;


import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作辅助类
 */
public class StringHelper {

    /**
     * Reverses the string based on whitespace
     *
     * @param str before str
     * @return after reverse
     * @author zhibindu1
     */
    public static String reverseSpace(String str) {
        if (str == null) {
            return "";
        }
        String[] strlist = str.trim().split(" +");//split的参数是正则表达式

        StringBuffer sb = new StringBuffer();
        for (int i = strlist.length - 1; i >= 0; i--) {
            sb.append(strlist[i] + " ");
        }

        return sb.toString().trim();
    }

    /**
     * filter the string of null
     *
     * @param object
     * @return String
     */
    public static String FilterNull(Object object) {
        return object != null && !"null".equals(object.toString()) ? object.toString().trim() : "";
    }

    /**
     * determine whether the object is empty or null
     * @param object   the object to b edetermined, can be a string or instance of a class
     * @return boolean
     */
    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        }
        if ("".equals(FilterNull(object.toString()))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * determine whether the object is not empty nor null
     * @param object
     * @return boolean
     */
    public static boolean isNotEmpty(Object object) {
        if (object == null) {
            return false;
        }
        if ("".equals(FilterNull(object.toString().trim()))) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * determine the string is number type
     *
     * @param object
     * @return boolean
     */
    public static boolean isNum(Object object) {
        try {
            new BigDecimal(object.toString());
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * determine the string is long type
     *
     * @param object
     * @return boolean
     */
    public static boolean isLong(Object object) {
        try {
            new Long(object.toString());
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 字符串转换成long类型
     *
     * @param object
     * @return Long
     */
    public static Long toLong(Object object) {
        if (isLong(object)) {
            return new Long(object.toString());
        } else {
            return 0L;
        }
    }

    /**
     * 字符串转换成int类型
     *
     * @param object
     * @return int
     */
    public static int toInt(Object object) {
        if (isNum(object)) {
            return Integer.parseInt(object.toString());
        } else {
            return 0;
        }
    }


    /**
     * 将异常转成字符串
     *
     * @param exception
     * @return string
     */
    public static String getexcExceptionInfo(Exception exception) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            exception.printStackTrace(new PrintStream(baos));
        } finally {
            try {
                baos.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return baos.toString();
    }

    /**
     * @param count input value 1-9
     * @return return 1-9 digit number
     * @author lianlichen1
     */
    public static String getRandomNum(int count) {
        StringBuilder sb = new StringBuilder();
        String str = "0123456789";
        Random r = new Random();
        for (int i = 0; i < count; i++) {
            int num = r.nextInt(str.length());
            sb.append(str.charAt(num));
            str = str.replace((str.charAt(num) + ""), "");
        }
        return sb.toString();
    }

    /**
     * 判断字符串是否为数字类型
     *
     * @param str
     * @return boolean
     */
    public static boolean isNumeric(String str) {

        boolean result = false;
        result = Pattern.matches("^[0-9]{1,}\\.[0-9]{1,}$", str);
        if (result == false) {
            result = Pattern.matches("^[0-9]{1,}$", str);
        }
        return result;
    }


    /**
     * 从字符串中得到数字
     *
     * @param str
     * @return 数字
     */
    public static String getNumericStringsFromFilterResult(String str) {

        if (isNotEmpty(str)) {
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(str);
            while (m.find()) {
                return m.group();
            }
        }
        return "";
    }

    /**
     * 生成 request body
     *
     * @param RequestBody RequestBody
     * @return String
     */
    public static String jsonToRequestBody(Map<String, Object> RequestBody) {

        JSONObject jsonObject;
        String requestBody = null;
        try {
            jsonObject = new JSONObject();
            for (Map.Entry<String, Object> entry : RequestBody.entrySet()) {
                jsonObject.put(entry.getKey(), entry.getValue());
                requestBody = jsonObject.toString();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestBody;
    }

    /**
     * 通过正则表到式过滤字符串
     *
     * @param regex
     * @param str
     * @return 过滤后的字符串
     */
    public static String regex(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;
        char[] array = str.toCharArray();
        for (int i = 0; i < array.length; i++) {
            matcher = pattern.matcher(String.valueOf(array[i]));
            if (!matcher.matches()) {
                str = str.replace(String.valueOf(array[i]), "");
            }
        }

        return str;
    }


}