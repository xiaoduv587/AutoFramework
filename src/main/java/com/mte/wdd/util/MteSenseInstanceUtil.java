//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mte.wdd.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MteSenseInstanceUtil {
    private static SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final boolean isWindows = System.getProperty("os.name").toLowerCase().indexOf("windows") != -1;
    private static final boolean isMac = System.getProperty("os.name").toLowerCase().indexOf("mac") != -1;
    private static final boolean isLinux = System.getProperty("os.name").toLowerCase().indexOf("linux") != -1;
    private static final boolean isWindows7 = System.getProperty("os.name").toLowerCase().indexOf("windows 7") != -1;
    private static Double javaVersion = null;

    public MteSenseInstanceUtil() {
    }

    public static boolean isNull(String value) {
        return value == null || value.trim().length() == 0;
    }

    public static void sleep(int second) {
        try {
            Thread.sleep((long)(second * 1000));
        } catch (Exception var2) {
        }

    }

    public static String getCurrentTime() {
        return dateformat.format(new Date());
    }

    public static String getProjectPath() {
        return System.getProperty("user.dir");
    }

    public static double getJavaVersion() {
        if (javaVersion == null) {
            try {
                String ver = System.getProperties().getProperty("java.version");
                String version = "";
                boolean firstPoint = true;

                for(int i = 0; i < ver.length(); ++i) {
                    if (ver.charAt(i) == '.') {
                        if (firstPoint) {
                            version = version + ver.charAt(i);
                        }

                        firstPoint = false;
                    } else if (Character.isDigit(ver.charAt(i))) {
                        version = version + ver.charAt(i);
                    }
                }

                javaVersion = new Double(version);
            } catch (Exception var4) {
                javaVersion = new Double(1.3D);
            }
        }

        return javaVersion;
    }

    public static boolean isWindows() {
        return isWindows;
    }

    public static boolean isWindows7() {
        return isWindows7;
    }

    public static boolean isMac() {
        return isMac;
    }

    public static boolean isLinux() {
        return isLinux;
    }
}
