package com.xiaodu;

import com.alibaba.fastjson.JSONObject;
import com.xiaodu.exception.ScriptException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.openqa.selenium.PageLoadStrategy;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author xiaodu
 */
@Log4j2
public class FrameWorkConfig {

    public ConfigKey<URL> APPIUM_HUB_URL;
    public ConfigKey<String> STF_MODULE_URL;
    /**
     * use gradle/maven deault:gradle
     */
    public ConfigKey<String> RUN_TYPE;

    public ConfigKey<Integer> THREAD_COUNT;
    public ConfigKey<String> PROJECT_NAME;

    public ConfigKey<Boolean> BF_IS_ENABLE;
    public ConfigKey<Boolean> HUB_IS_ENABLE;

    /**
     * Selenoid 开关
     */
    public ConfigKey<Boolean> SELENOID_IS_ENABLE;
    public ConfigKey<String> DRIVER_TYPE;

    /**
     * report portal
     */
    public ConfigKey<Boolean> RP_IS_ENABLE;
    public ConfigKey<String> RP_PROJ_NAME;
    public ConfigKey<String> RP_ENDPOINT;
    public ConfigKey<String> RP_UUID;

    /**
     * 运行结束是否关闭driver
     */
    public ConfigKey<Boolean> TEST_END_CLOSE_BROWSER;

    /**
     * FTP
     */
    public ConfigKey<Boolean> SAVE_PASSRATE_TO_FTP;
    public ConfigKey<Boolean> SAVE_SCORECARD_SHEET_TO_FTP;
    public ConfigKey<Boolean> SAVE_REPORT_TO_FTP;
    public ConfigKey<String> FTP_HOST;
    public ConfigKey<Integer> FTP_PORT;
    public ConfigKey<String> FTP_USERNAME;
    public ConfigKey<String> FTP_PWD;
    public ConfigKey<String> TESTDATA_FILE_NAME;
    public ConfigKey<String> TEST_EXEC_ENV;
    public ConfigKey<Boolean> USE_LOCAL_TESTDATA;

    public ConfigKey<String> UPLOAD_REPORT_REMOTPATH;
    public ConfigKey<String> UPLOAD_REPORT_DIRECTORY_NAME;
    public ConfigKey<String> UPDATE_PASSRATE_REMOTPATH;
    public ConfigKey<String> UPDATE_PASSRATE_FILENAME;


    /**
     * jenkins
     */
    public ConfigKey<String> JENKINS_JOB_NAME;


    /**
     * Selenium
     */
    public ConfigKey<PageLoadStrategy> PAGE_LOAD_STRATEGY;    //页面加载策略
    public ConfigKey<String> SELENIUM_HUB_URL;
    public ConfigKey<Boolean> CHROME_ENABLE_HEADLESS;
    public ConfigKey<String> CHROME_DRIVER_VERSION_FOR_WINDOWS;
    public ConfigKey<String> FILTERING_DOMAIN_NAME;
    public ConfigKey<String> BROSWER_LANGUAGE;  //语言
    public ConfigKey<String> BORWER_SOURCE;  //远程 or 本地

    public ConfigKey<String> BROSWER_WINDOW_SIZE;
    public ConfigKey<Integer> BROSWER_WINDOW_WIDE;
    public ConfigKey<Integer> BROSWER_WINDOW_HIGH;


    public ConfigKey<String> SCREEN_SHOTS_TYPE;
    public ConfigKey<String> SCREENSHOTS_WIDE;
    public ConfigKey<String> SCREENSHOTS_HIGH;

    public ConfigKey<Float> IMG_COMPRESS_PROPORTION;


    public ConfigKey<Boolean> DEVELOPER_MODEL;//开发者模式,大幅提高测试速度。不截图，等待时间缩短。

    /**
     * selenoid
     */
    public ConfigKey<Boolean> SELENOID_ENABLE_VNC;//实时观看
    public ConfigKey<Boolean> SELENOID_ENABLE_VIDEO;//录屏
    public ConfigKey<Boolean> USE_SELENOID_HUB;//使用selenode_ggr


    /**
     * Selenide相关
     */
    public ConfigKey<Integer> BROWSER_EXPLICIT_TIMEOUT;
    public ConfigKey<Integer> BROWSER_PAGELOAD_TIMEOUT;
    public ConfigKey<Integer> BROWSER_ELEMENT_DISAPPEAR_TIMEOUT;
    public ConfigKey<Integer> BROWSER_SCRIPT_TIMEOUT;



    private static String PREFIX_KEY = "";
    private static FrameWorkConfig instance;


    private FrameWorkConfig() {
        this.RUN_TYPE = (new ConfigKey(PREFIX_KEY + "RUN_TYPE", String.class, "gradle"))
                .setDescription("编译方式，默认是gradle");
        this.THREAD_COUNT = new ConfigKey(PREFIX_KEY + "THREAD_COUNT", Integer.class, 5);
        this.PROJECT_NAME = new ConfigKey(PREFIX_KEY + "PROJECT_NAME", String.class, "",
                new Matcher[]{Matchers.describedAs("it can't be empty.", Matchers.not(Matchers.emptyOrNullString()), new Object[ 0 ])})
                .setDescription("项目名称（不能为空）");
        this.BF_IS_ENABLE = new ConfigKey(PREFIX_KEY + "BF_IS_ENABLE", Boolean.class, false);
        this.HUB_IS_ENABLE = new ConfigKey(PREFIX_KEY + "HUB_IS_ENABLE", Boolean.class, false);
        this.SELENOID_IS_ENABLE = new ConfigKey(PREFIX_KEY + "SELENOID_IS_ENABLE", Boolean.class, false);
        this.DRIVER_TYPE = new ConfigKey(PREFIX_KEY + "DRIVER_TYPE", String.class, "chrome");

        this.RP_IS_ENABLE = new ConfigKey(PREFIX_KEY + "RP_IS_ENABLE", Boolean.class, false);
        this.RP_PROJ_NAME = new ConfigKey(PREFIX_KEY + "RP_PROJ_NAME", String.class, "");
        this.RP_ENDPOINT = new ConfigKey(PREFIX_KEY + "RP_ENDPOINT", String.class, "");
        this.RP_UUID = new ConfigKey(PREFIX_KEY + "RP_UUID", String.class, "");

        this.TEST_END_CLOSE_BROWSER = new ConfigKey(PREFIX_KEY + "TEST_END_CLOSE_BROWSER", Boolean.class, true);

        this.SAVE_PASSRATE_TO_FTP = new ConfigKey(PREFIX_KEY + "SAVE_PASSRATE_TO_FTP", Boolean.class, true);
        this.SAVE_SCORECARD_SHEET_TO_FTP = new ConfigKey(PREFIX_KEY + "SAVE_SCORECARD_SHEET_TO_FTP", Boolean.class, true);
        this.SAVE_REPORT_TO_FTP = new ConfigKey(PREFIX_KEY + "SAVE_REPORT_TO_FTP", Boolean.class, true);

        this.FTP_HOST = new ConfigKey(PREFIX_KEY + "FTP_HOST", String.class, "");
        this.FTP_PORT = new ConfigKey(PREFIX_KEY + "FTP_PORT", Integer.class, 0);
        this.FTP_USERNAME = new ConfigKey(PREFIX_KEY + "FTP_USERNAME", String.class, "");
        this.FTP_PWD = new ConfigKey(PREFIX_KEY + "FTP_PWD", String.class, "");
        this.TESTDATA_FILE_NAME = new ConfigKey(PREFIX_KEY + "TESTDATA_FILE_NAME", String.class, "TestData.xlsx");
        this.TEST_EXEC_ENV = new ConfigKey(PREFIX_KEY + "TEST_EXEC_ENV", String.class, "UAT");
        this.USE_LOCAL_TESTDATA = new ConfigKey(PREFIX_KEY + "USE_LOCAL_TESTDATA", Boolean.class, false);
        /**
         * appium相关
         */
        this.STF_MODULE_URL = new ConfigKey(PREFIX_KEY + "REMOTE_URL", String.class, "").setDescription("远程STF地址，默认空");
        try {
            this.APPIUM_HUB_URL = new ConfigKey(PREFIX_KEY + "APPIUM_HUB_URL", URL.class, new URL("http://0.0.0.0:4723/wd/hub")).setDescription("appium地址，默认使用本地");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        /**
         * jenkins
         */
        this.JENKINS_JOB_NAME = new ConfigKey(PREFIX_KEY + "JENKINS_JOB_NAME", String.class, "");

        this.UPLOAD_REPORT_REMOTPATH = new ConfigKey(PREFIX_KEY + "UPLOAD_REPORT_REMOTPATH", String.class, "SAVE_REPORT_TO_FTP/");
        this.UPLOAD_REPORT_DIRECTORY_NAME = new ConfigKey(PREFIX_KEY + "UPLOAD_REPORT_DIRECTORY_NAME", String.class, "runShellRetry-output");
        this.UPDATE_PASSRATE_REMOTPATH = new ConfigKey(PREFIX_KEY + "UPDATE_PASSRATE_REMOTPATH", String.class, "SAVE_PASSRATE_TO_FTP/");
        this.UPDATE_PASSRATE_FILENAME = new ConfigKey(PREFIX_KEY + "UPDATE_PASSRATE_FILENAME", String.class, "pass_rate.xlsx");


        this.DEVELOPER_MODEL = new ConfigKey(PREFIX_KEY + "DEVELOPER_MODEL", Boolean.class, false);

        /**
         * selenoid
         */
        this.SELENOID_ENABLE_VNC = new ConfigKey(PREFIX_KEY + "SELENOID_ENABLE_VNC", Boolean.class, true);
        this.SELENOID_ENABLE_VIDEO = new ConfigKey(PREFIX_KEY + "SELENOID_ENABLE_VIDEO", Boolean.class, true);
        this.USE_SELENOID_HUB = new ConfigKey(PREFIX_KEY + "USE_SELENOID_HUB", Boolean.class, false).setDescription("是否使用ggr(selenoid_hub),如果用到的话，录屏格式会有些许差别");


        /**
         * Selenium
         */
        this.PAGE_LOAD_STRATEGY = new ConfigKey(PREFIX_KEY + "PAGE_LOAD_STRATEGY", PageLoadStrategy.class, PageLoadStrategy.NONE);
        this.SELENIUM_HUB_URL = new ConfigKey(PREFIX_KEY + "SELENIUM_HUB_URL", String.class, "");
        this.CHROME_ENABLE_HEADLESS = new ConfigKey(PREFIX_KEY + "CHROME_ENABLE_HEADLESS", Boolean.class, false);
        this.CHROME_DRIVER_VERSION_FOR_WINDOWS = new ConfigKey(PREFIX_KEY + "CHROME_DRIVER_VERSION_FOR_WINDOWS", String.class, "88");
        this.FILTERING_DOMAIN_NAME = new ConfigKey(PREFIX_KEY + "FILTERING_DOMAIN_NAME", String.class, "");
        this.BROSWER_LANGUAGE = new ConfigKey(PREFIX_KEY + "BROSWER_LANGUAGE", String.class, "en-US");
        this.BORWER_SOURCE = new ConfigKey(PREFIX_KEY + "BORWER_SOURCE", String.class, "Local");


        this.BROSWER_WINDOW_SIZE = new ConfigKey(PREFIX_KEY + "BROSWER_WINDOW_SIZE", String.class, "MAX").setDescription("MAX/CUSTOM");
        this.BROSWER_WINDOW_WIDE = new ConfigKey(PREFIX_KEY + "BROSWER_WINDOW_WIDE", Integer.class, 1920);
        this.BROSWER_WINDOW_HIGH = new ConfigKey(PREFIX_KEY + "BROSWER_WINDOW_HIGH", Integer.class, 1080);

        this.BROWSER_EXPLICIT_TIMEOUT = new ConfigKey(PREFIX_KEY + "BROWSER_EXPLICIT_TIMEOUT", Integer.class, 50);
        this.BROWSER_PAGELOAD_TIMEOUT = new ConfigKey(PREFIX_KEY + "BROWSER_PAGELOAD_TIMEOUT", Integer.class, 150);
        this.BROWSER_SCRIPT_TIMEOUT = new ConfigKey(PREFIX_KEY + "BROWSER_SCRIPT_TIMEOUT", Integer.class, 100);
        this.BROWSER_ELEMENT_DISAPPEAR_TIMEOUT = new ConfigKey(PREFIX_KEY + "BROWSER_ELEMENT_DISAPPEAR_TIMEOUT", Integer.class, 30);

        this.SCREEN_SHOTS_TYPE = new ConfigKey(PREFIX_KEY + "SCREEN_SHOTS_TYPE", String.class, "jpg").setDescription("jpg/png");
        this.SCREENSHOTS_WIDE = new ConfigKey(PREFIX_KEY + "SCREENSHOTS_WIDE", String.class, "1920px");
        this.SCREENSHOTS_HIGH = new ConfigKey(PREFIX_KEY + "SCREENSHOTS_HIGH", String.class, "");
        this.IMG_COMPRESS_PROPORTION = new ConfigKey(PREFIX_KEY + "IMG_COMPRESS_PROPORTION", Float.class, 1.0f);


    }

    public static FrameWorkConfig instance() {
        if (instance == null) {
            instance = new FrameWorkConfig();
        }
        return instance;
    }


    public class ConfigKey<T> {
        String name;
        String description;
        Class<T> type;
        T value;
        T defaultValue;
        boolean mergeDefaultValue = false;
        private Matcher<T>[] matchers;

        public ConfigKey(Class<T> type) {
            this.type = type;
            this.updateVaule();
        }

        public ConfigKey(String name, Class<T> type) {
            this.name = name.toLowerCase().replace(".", "_");
            this.type = type;
            this.updateVaule();
        }

        public ConfigKey(String name, Class<T> type, T value) {
            this.name = name.toLowerCase().replace(".", "_");
            this.type = type;
            this.value = value;
            this.defaultValue = value;
            this.updateVaule();
        }

        @SafeVarargs
        public ConfigKey(String name, Class<T> type, T value, Matcher<T>... matchers) {
            this.name = name.toLowerCase().replace(".", "_");
            this.type = type;
            this.value = value;
            this.defaultValue = value;
            this.matchers = matchers;
            this.updateVaule();
        }

        @SafeVarargs
        public ConfigKey(String name, Class<T> type, T value, boolean mergeDefaultValue, Matcher<T>... matchers) {
            this.name = name.toLowerCase().replace(".", "_");
            this.type = type;
            this.value = value;
            this.defaultValue = value;
            this.mergeDefaultValue = mergeDefaultValue;
            this.matchers = matchers;
            this.updateVaule();
        }

        void updateVaule() {
            T currentValue = FrameWorkConfig.this.resolver(this.getName(), this.value, this.getType());
            if (this.mergeDefaultValue) {
                if (this.defaultValue instanceof JSONObject) {
                    currentValue = (T) ((JSONObject) this.defaultValue).fluentPutAll(((JSONObject) currentValue).getInnerMap());
                } else {
                    log.warn(this.getName() + " 不能合并默认参数。");
                }
            }

            this.value = currentValue;
        }

        public String getName() {
            return this.name;
        }

        public Class<T> getType() {
            return this.type;
        }

        public T getValue() {
            return this.value;
        }

        public void reset() {
            this.value = this.defaultValue;
        }

        public void setValue(Object value) {
            if (this.mergeDefaultValue) {
                if (this.defaultValue instanceof JSONObject) {
                    if (value instanceof JSONObject) {
                        this.value = (T) ((JSONObject) this.value).fluentPutAll(((JSONObject) value).getInnerMap());
                    } else {
                        if (!(value instanceof String)) {
                            throw new ScriptException("参数[" + this.name + "]值必须是json格式。");
                        }

                        JSONObject json = JSONObject.parseObject(String.valueOf(value));
                        this.value = (T) ((JSONObject) this.value).fluentPutAll(json.getInnerMap());
                    }
                } else {
                    log.warn(this.getName() + " 不能合并默认参数。");
                }
            } else if (!this.value.equals(value)) {
                log.info("Parameters override([new value] >>> key[old value]): [" + value + "] >>> " + this.name + "[" + this.value + "]");
                this.value = (T) value;
            }

        }

        public void check() {
            if (this.matchers != null) {
                Matcher[] var1 = this.matchers;
                int var2 = var1.length;

                for (int var3 = 0; var3 < var2; ++var3) {
                    Matcher<T> matcher = var1[ var3 ];
                    assertThat("Config Key[" + this.name + "] value[" + this.value + "] Invalid format.", this.getValue(), matcher);
                }

            }
        }

        public String getDescription() {
            return this.description;
        }

        public ConfigKey<T> setDescription(String description) {
            this.description = description;
            return this;
        }
    }

    private <T> T resolve(ConfigKey<T> configKey) {
        if (configKey == null) {
            throw new ScriptException("parameter can't be null.");
        } else {
            String name = configKey.getName();
            T tValue = configKey.getValue();
            Class<T> type = configKey.getType();
            return this.resolver(name, tValue, type);
        }
    }

    private <T> T resolver(String name, T tValue, Class<T> type) {
        String strValue = "";
        Iterator var5 = System.getenv().entrySet().iterator();

        Map.Entry item;
        while (var5.hasNext()) {
            item = (Map.Entry) var5.next();
            if (((String) item.getKey()).equalsIgnoreCase(name.replace(".", "_"))) {
                strValue = (String) item.getValue();
                break;
            }
        }

        if (StringUtils.isEmpty(strValue)) {
            var5 = System.getProperties().entrySet().iterator();

            while (var5.hasNext()) {
                item = (Map.Entry) var5.next();
                if (StringUtils.equalsIgnoreCase(String.valueOf(item.getKey()), name)) {
                    strValue = String.valueOf(item.getValue());
                    break;
                }
            }
        }


        return StringUtils.isEmpty(strValue) ? tValue : this.parse(type, strValue);
    }

    private <T> T parse(Class<T> type, String strValue) {
        T output = null;
        if (type.equals(String.class)) {
            output = (T) strValue;
        } else if (type.equals(Integer.class)) {
            output = (T) Integer.valueOf(strValue);
        } else if (type.equals(Boolean.class)) {
            output = (T) Boolean.valueOf(strValue);
        } else if (type.equals(Double.class)) {
            output = (T) Double.valueOf(strValue);
        } else if (type.equals(Float.class)) {
            output = (T) Float.valueOf(strValue);
        } else if (type.equals(URL.class)) {
            try {
                output = (T) new URL(strValue);
            } catch (Exception var5) {
                throw new RuntimeException(var5);
            }
        } else {
            if (!type.equals(JSONObject.class)) {
                throw new RuntimeException("Type " + type.getTypeName() + " cannot be parsed");
            }

            output = (T) JSONObject.parseObject(strValue);
        }

        return output;
    }

    public void put(Map<String, ?> map) {
        Map<String, ConfigKey<Object>> configKeys = this.asMap();
        Map<String, Object> undefinedKey = new HashMap();
        Iterator var4 = map.entrySet().iterator();

        while (var4.hasNext()) {
            Map.Entry<String, ?> item = (Map.Entry) var4.next();
            String key = ((String) item.getKey()).toUpperCase();
            if (configKeys.containsKey(key)) {
                ConfigKey<Object> inst = configKeys.get(((String) item.getKey()).toUpperCase());
                inst.setValue(this.parse(inst.getType(), String.valueOf(item.getValue())));
            } else {
                log.info("key[" + (String) item.getKey() + "] is undefined.");
                undefinedKey.put((String) item.getKey(), item.getValue());
            }
        }

        if (undefinedKey.size() > 0) {
            log.warn("these key&value is undefined: " + MapUtils.toProperties(undefinedKey).toString());
            log.trace(this.toString());
        }

    }

    public void put(String key, Object value) {
        try {
            ConfigKey<Object> inst = (ConfigKey) this.asMap().get(key);
            inst.setValue(this.parse(inst.getType(), String.valueOf(value)));
        } catch (Exception var4) {
            log.warn("key[" + key + "]=value[" + value + "] invalid, because:" + var4.getMessage());
        }

    }

    private Map<String, ConfigKey<Object>> asMap() {
        Map<String, ConfigKey<Object>> map = new HashMap<String, ConfigKey<Object>>() {
            public ConfigKey<Object> get(Object key) {
                if (!this.containsKey(key)) {
                    String error = "key[" + key + "] is undefine.";
                    throw new RuntimeException(error);
                } else {
                    return super.get(key);
                }
            }
        };
        Field[] var2 = FrameWorkConfig.class.getDeclaredFields();
        int var3 = var2.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            Field field = var2[ var4 ];
            if (field.getType() == ConfigKey.class) {
                try {
                    ConfigKey<Object> configKey = (ConfigKey) field.get(instance);
                    map.put(field.getName(), configKey);
                } catch (IllegalArgumentException var7) {
                    var7.printStackTrace();
                } catch (IllegalAccessException var8) {
                    var8.printStackTrace();
                }
            }
        }

        return map;
    }

    public void check() {
        Map<String, ConfigKey<Object>> configKeys = this.asMap();
        Iterator var2 = configKeys.entrySet().iterator();

        while (var2.hasNext()) {
            Map.Entry<String, ConfigKey<Object>> item = (Map.Entry) var2.next();
            item.getValue().check();
        }

    }


}
