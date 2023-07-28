package com.xiaodu;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.describedAs;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.xiaodu.exception.ScriptException;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matcher;

import com.alibaba.fastjson.JSONObject;

/**
 * 单实例配置文件类，管理所有配置参数<br>
 * 支持以下方式进行参数覆盖（优先级1 2 3）：<br>
 * 1.设置环境变量(注意下划线。键名不区分大小写，但是尽量用大写) <br>
 * COMMONLIB_CHROME_DRIVER_VERSION=2.42 <br>
 * 2.设置jvm运行时变量<br>
 * (1).或者通过jvm参数传入-Dcommonlib.chrome.driver.version=2.42<br>
 * (2).运行时通过System对象进行修改System.setProperty("commonlib.chrome.driver.version","2.42");
 * <br>
 * 3.配置文件commonlib.properties<br>
 * commonlib.chrome.driver.version=2.42
 *
 * @author zhangyh49
 */
public class FrameWorkConfig {

    private static final Logger log = LogManager.getLogger();

    String projectName;

    /**
     * 可以给环境变量添加一个前缀，防止出现重名的环境变量
     */
    private static String PREFIX_KEY = "";

    /**
     * 配置文件
     */
    private static String DEFAULT_PROPERTY = "config.properties";

    /**
     * 当前实例（不可更改）
     */
    private static FrameWorkConfig instance;

    private ConfigKey<String> PROPERTIES = new ConfigKey<String>(DEFAULT_PROPERTY, String.class, DEFAULT_PROPERTY)
            .setDescription("本地配置参数文件名称");

    /**
     * 项目名称（不能为空）
     */
    public ConfigKey<String> PROJECT_NAME = new ConfigKey<String>(PREFIX_KEY + "PROJECT_NAME", String.class, "",
            describedAs("it can't be empty.", not(emptyOrNullString()))).setDescription("项目名称（不能为空）");

    public ConfigKey<String> PROJECT_GROUP = new ConfigKey<String>(PREFIX_KEY + "PROJECT_GROUP", String.class, "")
            .setDescription("项目分组");

    public ConfigKey<String> PROJECT_COUNTRY = new ConfigKey<String>(PREFIX_KEY + "PROJECT_COUNTRY", String.class, "")
            .setDescription("国家");

    /**
     * 本次测试超时时间，默认10080/分钟(7x24小时)
     */
    public ConfigKey<Long> JOB_TIMEOUT = new ConfigKey<Long>(PREFIX_KEY + "JOB_TIMEOUT", Long.class, (long) 10080)
            .setDescription("本次测试超时时间，默认10080/分钟(7x24小时)");

    public ConfigKey<Long> WAIT_DEVICE_RELEASE = new ConfigKey<Long>(PREFIX_KEY + "WAIT_DEVICE_RELEASE", Long.class,
            (long) 60).setDescription("等待测试设备状态释放默认：60秒");

    /**
     * 日志级别（不能为空、默认info）
     */
    public ConfigKey<String> LOGLEVEL = new ConfigKey<String>(PREFIX_KEY + "LOGLEVEL", String.class, "info",
            describedAs("it can't be empty.", not(emptyOrNullString()))).setDescription("日志级别,不能为空,默认info");

    public ConfigKey<String> PROJECT_VERSION = new ConfigKey<String>(PREFIX_KEY + "PROJECT_VERSION", String.class,
            "debug").setDescription("版本");

    public ConfigKey<String> TESTNG_XML = new ConfigKey<String>(PREFIX_KEY + "TESTNG_XML", String.class, "")
            .setDescription("testng.xml");

    /**
     * 指定chrome driver版本(默认为空，将自动下载与本机安装的chrome相匹配的driver)
     */
    public ConfigKey<String> CHROME_DRVIER_VERSION = new ConfigKey<String>(PREFIX_KEY + "CHROME_DRVIER_VERSION",
            String.class, "").setDescription("指定chrome driver版本(默认为空，将自动下载与本机安装的chrome相匹配的driver)");

    /**
     * 指定firefox driver版本(默认为空，将自动下载与本机安装的chrome相匹配的driver)
     */
    public ConfigKey<String> FIREFOX_DRVIER_VERSION = new ConfigKey<String>(PREFIX_KEY + "FIREFOX_DRVIER_VERSION",
            String.class, "").setDescription("指定firefox driver版本(默认为空，将自动下载与本机安装的chrome相匹配的driver)");

    public ConfigKey<Boolean> USE_LOCAL_XML = new ConfigKey<Boolean>(PREFIX_KEY + "USE_LOCAL_XML", Boolean.class, true)
            .setDescription("加载方式，默认值为True");


    public ConfigKey<String> SELENIUM_HUB_URL = new ConfigKey<String>(PREFIX_KEY + "SELENIUM_HUB_URL", String.class, ""
//			"http://10.62.142.12:4444/wd/hub"
    ).setDescription("远程Selenium Hub地址");

    public ConfigKey<String> APPIUM_HUB_URL = new ConfigKey<String>(PREFIX_KEY + "APPIUM_HUB_URL", String.class, ""
//			"http://10.62.142.12:4444/wd/hub"
    ).setDescription("远程Appium Hub地址");

    /**
     * 控制测试结果是否要上传到sdet
     *
     * @deprecated SDET功能已弃用2022/9/21
     */
    public ConfigKey<Boolean> UPLOAD_RESULT_TO_SDET_DASHBOARD = new ConfigKey<Boolean>(
            PREFIX_KEY + "UPLOAD_RESULT_TO_SDET_DASHBOARD", Boolean.class, false)
            .setDescription("控制测试结果是否上传到SDET平台，默认值false（配合参数USE_LOCAL_XML=true使用）");

	/**
	 * 控制初始化浏览器实例时是否使用无头模式
	 */
	public ConfigKey<Boolean> HEADLESS = new ConfigKey<Boolean>(PREFIX_KEY + "HEADLESS", Boolean.class, false)
			.setDescription("控制所有通过框架启动的webdriver以无头模式运行，默认值false");

    /**
     * 使用WebDriverManager下载drvier时的超时时间，默认：5秒
     */
    public ConfigKey<Integer> WDM_TIMEOUT = new ConfigKey<Integer>(PREFIX_KEY + "WDM_TIMEOUT", Integer.class, 5)
            .setDescription("使用WebDriverManager下载drvier时的超时时间，默认：5秒");

    /**
     * 自动配置webdriver时是否使用国内镜像地址下载webdriver
     * <p>
     * 设置为true适用国内网络，一般不做修改，脚本会根据本机国家语言自动确定是否适用国内镜像
     */
    public ConfigKey<Boolean> WDM_USE_MIRROR = new ConfigKey<Boolean>(PREFIX_KEY + "WDM_USE_MIRROR", Boolean.class,
            false).setDescription("自动配置webdriver时是否使用国内镜像地址下载webdriver，设置为true适用国内网络，一般不做修改，脚本会根据本机国家语言自动确定是否适用国内镜像");

    /**
     * 可自定义配置大小[1080*762] [max] []
     */
    public ConfigKey<String> BROSWER_WINDOW_SIZE = new ConfigKey<String>(PREFIX_KEY + "BROSWER_WINDOW_SIZE",
            String.class, "{'full':false,'wide':1440,'high':900}");

    public ConfigKey<Integer> BROWSER_PAGELOAD_TIMEOUT = new ConfigKey<Integer>(PREFIX_KEY + "BROWSER_PAGELOAD_TIMEOUT",
            Integer.class, 150);

    public ConfigKey<Integer> BROWSER_SCRIPT_TIMEOUT = new ConfigKey<Integer>(PREFIX_KEY + "BROWSER_SCRIPT_TIMEOUT",
            Integer.class, 100);

    public ConfigKey<Boolean> BROWSE_HEADLESS = new ConfigKey<Boolean>(PREFIX_KEY + "BROWSE_HEADLESS", Boolean.class,
            false).setDescription("控制所有通过框架启动的webdriver以无头模式运行，默认值false");;

    public ConfigKey<Integer> FIND_ELEMENT_IMPLICITLY_TIMEOUT = new ConfigKey<Integer>(
            PREFIX_KEY + "FIND_ELEMENT_IMPLICITLY_TIMEOUT", Integer.class, 20).setDescription("查找元素隐式超时");

    public ConfigKey<Integer> FIND_ELEMENT_EXPLICIT_TIMEOUT = new ConfigKey<Integer>(
            PREFIX_KEY + "FIND_ELEMENT_EXPLICIT_TIMEOUT", Integer.class, 20).setDescription("查找元素显式超时");

    /**
     * TestNG 参数
     * <p>
     * 指定testng并发数量
     */
    public ConfigKey<Integer> TESTNG_THREAD_COUNT = new ConfigKey<Integer>(PREFIX_KEY + "TESTNG_THREAD_COUNT",
            Integer.class, 5).setDescription("指定testng并发数量");

    /**
     * TestNG 参数
     * <p>
     * 指定testng报告目录
     */
    public ConfigKey<String> TESTNG_REPORTS_ROOT_DIR = new ConfigKey<String>(PREFIX_KEY + "TESTNG_REPORTS_ROOT_DIR",
            String.class, "test-output").setDescription("指定testng报告目录");

//	/**
//	 * 传入项目中的参数
//	 */
//	public ConfigKey<String> PROJECT_PARAMETER = new ConfigKey<String>(PREFIX_KEY + "PROJECT_PARAMETER", String.class,
//			"");

    public ConfigKey<Boolean> AUTO_CLOSE_DRIVER = new ConfigKey<Boolean>(PREFIX_KEY + "AUTO_CLOSE_DRIVER",
            Boolean.class, true).setDescription("当脚本结束时关闭所有打开的浏览器。默认值:true");

    /**
     * 在报告中显示截屏的宽
     */
    public ConfigKey<String> SCREENSHOTS_WIDE = new ConfigKey<String>(PREFIX_KEY + "SCREENSHOTS_WIDE", String.class,
            "960px").setDescription("报告中截图的宽");

    /**
     * 在报告中显示截屏的高
     */
    public ConfigKey<String> SCREENSHOTS_HIGH = new ConfigKey<String>(PREFIX_KEY + "SCREENSHOTS_HIGH", String.class, "")
            .setDescription("报告中截图的高");

    /**
     * 手机截图在报告中显示宽度
     */
    public ConfigKey<String> MOBILE_SCREENSHORTS_WIDE = new ConfigKey<String>(PREFIX_KEY + "MOBILE_SCREENSHORTS_WIDE",
            String.class, "200px");

    /**
     * 手机截图在报告中显示高度
     */
    public ConfigKey<String> MOBILE_SCREENSHORTS_HIGH = new ConfigKey<String>(PREFIX_KEY + "MOBILE_SCREENSHORTS_HIGH",
            String.class, "");

    /**
     * 截屏图像压缩倍率
     */
    public ConfigKey<Integer> IMG_COMPRESS_PROPORTION = new ConfigKey<Integer>(PREFIX_KEY + "IMG_COMPRESS_PROPORTION",
            Integer.class, 2);

    public ConfigKey<String> JENKINS_JOB_NAME = new ConfigKey<String>(PREFIX_KEY + "JENKINS_JOB_NAME", String.class,
            "");

    public ConfigKey<String> JENKINS_BUILD_NUMBER = new ConfigKey<String>(PREFIX_KEY + "JENKINS_BUILD_NUMBER",
            String.class, "");

    public ConfigKey<String> JENKINS_BUILD_URL = new ConfigKey<String>(PREFIX_KEY + "JENKINS_BUILD_URL", String.class,
            "");

    public ConfigKey<String> TESTDATA_FILE_NAME = new ConfigKey<String>(PREFIX_KEY + "TESTDATA_FILE_NAME", String.class,
            "TestData.xlsx");

//	public ConfigKey<String> FTP_USERNAME = new ConfigKey<String>(PREFIX_KEY + "FTP_USERNAME", String.class, "");

//	public ConfigKey<Integer> FTP_PORT = new ConfigKey<Integer>(PREFIX_KEY + "FTP_PORT", Integer.class, 21);

//	public ConfigKey<String> FTP_PWD = new ConfigKey<String>(PREFIX_KEY + "FTP_PWD", String.class, "");

//	public ConfigKey<String> FTP_HOST = new ConfigKey<String>(PREFIX_KEY + "FTP_HOST", String.class, "");

    public ConfigKey<String> SCREENSHORTS_FILE_SUFFIX = new ConfigKey<String>(PREFIX_KEY + "SCREENSHORTS_FILE_SUFFIX",
            String.class, "png");

    public ConfigKey<String> STF_PROTOCOL = new ConfigKey<String>(PREFIX_KEY + "STF_PROTOCOL", String.class, "http");

    public ConfigKey<String> STF_HOST = new ConfigKey<String>(PREFIX_KEY + "STF_HOST", String.class, "127.0.0.1");

    public ConfigKey<Integer> STF_PORT = new ConfigKey<Integer>(PREFIX_KEY + "STF_PORT", Integer.class, 80);

    public ConfigKey<String> STF_TOKEN = new ConfigKey<String>(PREFIX_KEY + "STF_TOKEN", String.class,
            "d07979fb31054319985b2ec11fcd67c436682d7b958b4bafa38de91a8b8dcd84");

    /**
     * reportportal配置
     */
//    public ConfigKey<JSONObject> RP_CONFIG = new ConfigKey<JSONObject>(PREFIX_KEY + "RP_CONFIG", JSONObject.class,
//            new JSONObject()
//
//                    .fluentPut(ListenerProperty.BASE_URL.getPropertyName(), "http://report.sdet.com:8080")
//
//                    .fluentPut(ListenerProperty.API_KEY.getPropertyName(), "c0de59f9-29a0-4703-a8a7-")
//
//                    .fluentPut(ListenerProperty.LAUNCH_NAME.getPropertyName(), PROJECT_NAME.getValue())
//
//                    .fluentPut(ListenerProperty.PROJECT_NAME.getPropertyName(), PROJECT_NAME.getValue())
//
//                    .fluentPut(ListenerProperty.ENABLE.getPropertyName(), true)
//
//                    .fluentPut(ListenerProperty.DESCRIPTION.getPropertyName(), "")
//
//                    .fluentPut(ListenerProperty.LAUNCH_ATTRIBUTES.getPropertyName(), "")
//
//                    .fluentPut(ListenerProperty.IS_CONVERT_IMAGE.getPropertyName(), false)
//
//                    .fluentPut(ListenerProperty.MODE.getPropertyName(), "DEFAULT")
//
//                    .fluentPut(ListenerProperty.SKIPPED_AS_ISSUE.getPropertyName(), true)
//
//                    .fluentPut(ListenerProperty.BATCH_SIZE_LOGS.getPropertyName(), 20)
//
//                    .fluentPut(ListenerProperty.KEYSTORE_RESOURCE.getPropertyName(), "<PATH_TO_YOUR_KEYSTORE>")
//
//                    .fluentPut(ListenerProperty.KEYSTORE_PASSWORD.getPropertyName(), "<PASSWORD_OF_YOUR_KEYSTORE>"),
//            true) {
//
//        public JSONObject getValue() {
//            if (StringUtils.isEmpty(super.getValue().getString(ListenerProperty.LAUNCH_NAME.getPropertyName()))) {
//                super.getValue().fluentPut(ListenerProperty.LAUNCH_NAME.getPropertyName(), PROJECT_NAME.getValue());
//            }
//            if (StringUtils.isEmpty(super.getValue().getString(ListenerProperty.PROJECT_NAME.getPropertyName()))) {
//                super.getValue().fluentPut(ListenerProperty.PROJECT_NAME.getPropertyName(), PROJECT_NAME.getValue());
//            }
//            return super.getValue();
//        }
//
//    };

//    public ConfigKey<Boolean> ENABLE_REPORTPORTAL = new ConfigKey<Boolean>(PREFIX_KEY + "ENABLE_REPORTPORTAL",
//            Boolean.class, false) {
//        @Override
//        public void setValue(Object value) {
//            if (Boolean.valueOf(String.valueOf(value))) {
//                RP_CONFIG.getValue().put("rp.enable", true);
//            }
//        }
//    };


    /**
     * TestNG运行时只运行指定的case
     */
    public ConfigKey<String> SPECIFY_TESTCASE = new ConfigKey<String>(PREFIX_KEY + "SPECIFY_TESTCASE", String.class, "")
            .setDescription("TestNG运行时只运行指定的case");

    /**
     * TestNG运行时排除指定的case
     */
    public ConfigKey<String> EXCLUDE_TESTCASE = new ConfigKey<String>(PREFIX_KEY + "EXCLUDE_TESTCASE", String.class, "")
            .setDescription("TestNG运行时排除指定的case");

    public ConfigKey<Locale> LOCALE = new ConfigKey<Locale>(PREFIX_KEY + "Locale", Locale.class, Locale.getDefault());

    public ConfigKey<Boolean> TAKE_SCREENSHOT_ON_ERROR = new ConfigKey<Boolean>(PREFIX_KEY + "TAKE_SCREENSHOT_ON_ERROR",
            Boolean.class, true).setDescription("当测试发生错误时，是否执行webdriver截图，默认True");

    public ConfigKey<Boolean> ENABLE_SPLUNK_ALERT = new ConfigKey<Boolean>(PREFIX_KEY + "ENABLE_SPLUNK_ALERT",
            Boolean.class, false).setDescription("是否启用SPLUNK平台通知功能(此功能需要单独配置),默认值False");

    public ConfigKey<String> EXCEPTION_MSG_RECEIVER = new ConfigKey<String>(PREFIX_KEY + "EXCEPTION_MSG_RECEIVER",
            String.class, "15701207383@163.com").setDescription("异常信息接收者（框架执行异常会发送到指定的邮箱，通常是开发者，多个邮箱用\";\"分号隔开）");

    public ConfigKey<Integer> PUSH_REPORT_TO_CLOUD_RETRY_COUNT = new ConfigKey<Integer>(
            PREFIX_KEY + "PUSH_REPORT_TO_CLOUD_RETRY_COUNT", Integer.class, 2).setDescription("当推送测试结果至邮件里发生异常时重试次数");

    private FrameWorkConfig() {

    }

    static boolean PRINT_CONFIG = true;

    public static FrameWorkConfig instance() {
        if (instance == null) {
            instance = new FrameWorkConfig();
        }
        if (PRINT_CONFIG == false) {
            log.info("\n" + instance.toString());
            PRINT_CONFIG = true;
        }
        return instance;
    }

    @SuppressWarnings({ "unchecked", "serial" })
    private Map<String, ConfigKey<Object>> asMap() {
        Map<String, ConfigKey<Object>> map = new HashMap<String, ConfigKey<Object>>() {
            @Override
            public ConfigKey<Object> get(Object key) {
                if (!this.containsKey(key)) {
                    String error = "key[" + key + "] is undefine.";
                    throw new RuntimeException(error);
                }
                return super.get(key);
            }
        };
        for (Field field : FrameWorkConfig.class.getDeclaredFields()) {
            if (field.getType() == ConfigKey.class) {
                try {
                    ConfigKey<Object> configKey = (ConfigKey<Object>) field.get(instance);
                    map.put(field.getName(), configKey);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    /**
     * 覆盖已定义的参数
     *
     * @param map map
     */
    public void put(Map<String, ?> map) {
        Map<String, ConfigKey<Object>> configKeys = this.asMap();
        Map<String, Object> undefinedKey = new HashMap<String, Object>();
        for (Entry<String, ?> item : map.entrySet()) {
            String key = item.getKey().toUpperCase();
            if (configKeys.containsKey(key)) {
                ConfigKey<Object> inst = configKeys.get(item.getKey().toUpperCase());
                inst.setValue(parse(inst.getType(), String.valueOf(item.getValue())));
            } else {
                log.info("key[" + item.getKey() + "] is undefined.");
                undefinedKey.put(item.getKey(), item.getValue());
            }
        }
        if (undefinedKey.size() > 0) {
            log.warn("these key&value is undefined: " + MapUtils.toProperties(undefinedKey).toString());
            log.trace(this.toString());
        }
    }

    /**
     * 覆盖已定义的参数
     *
     * @param key   key
     * @param value value
     */
    public void put(String key, Object value) {
        try {
            ConfigKey<Object> inst = this.asMap().get(key);
            inst.setValue(parse(inst.getType(), String.valueOf(value)));
        } catch (Exception e) {
            log.warn("key[" + key + "]=value[" + value + "] invalid, because:" + e.getMessage());
        }
    }

    private <T> T resolve(ConfigKey<T> configKey) {
        if (configKey == null) {
            throw new ScriptException("parameter can't be null.");
        }
        String name = configKey.getName();
        T tValue = configKey.getValue();
        Class<T> type = configKey.getType();
        return resolver(name, tValue, type);
    }

    private <T> T resolver(String name, T tValue, Class<T> type) {
        String strValue = "";
        for (Entry<String, String> item : System.getenv().entrySet()) {
            if (item.getKey().equalsIgnoreCase(name.replace(".", "_"))) {
                strValue = item.getValue();
                break;
            }
        }
        if (StringUtils.isEmpty(strValue)) {
            for (Entry<Object, Object> item : System.getProperties().entrySet()) {
                if (StringUtils.equalsIgnoreCase(String.valueOf(item.getKey()), name)) {
                    strValue = String.valueOf(item.getValue());
                    break;
                }
            }
        }
        if (StringUtils.isEmpty(strValue)) {
            // 排除PROPERTIES
            if (StringUtils.equalsAnyIgnoreCase(StringUtils.replace(DEFAULT_PROPERTY, ".", "_"), name)) {
                return tValue;
            }
            strValue = getProperty(name);
        }
        if (StringUtils.isEmpty(strValue)) {
            return tValue;
        } else {
            return parse(type, strValue);
        }
    }

    @SuppressWarnings("unchecked")
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
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (type.equals(JSONObject.class)) {
            output = (T) JSONObject.parseObject(strValue);
        } else {
            throw new RuntimeException("Type " + type.getTypeName() + " cannot be parsed");
        }
        return output;
    }

    private String getProperty(String key) {
        String value = null;
        String propertiesValue = getProperties();
        String defaultProperties = DEFAULT_PROPERTY;
        try {
            value = getPropertyFrom(propertiesValue, key);
            if (value == null) {
                value = getPropertyFrom(defaultProperties, key);
            }
        } finally {
            if (value == null) {
                value = "";
            }
        }
        return value;
    }

    boolean ConfigFileExist = true;

    private String getPropertyFrom(String properties, String key) {
        Properties props = new Properties();
        URL resource = FrameWorkConfig.class.getClassLoader().getResource(properties);
        if (resource == null) {
            if (ConfigFileExist) {
                log.warn(properties + " config file missing.");
                ConfigFileExist = false;
            }
            return "";
        }
        try (InputStream inputStream = resource.openStream()) {
//		try (InputStream inputStream = FrameWorkConfig.class.getResourceAsStream(properties)) {
            props.load(inputStream);
        } catch (IOException e) {
            log.trace("Property {} not found in {}", key, properties);
        }
        String value = "";
        for (Entry<Object, Object> item : props.entrySet()) {
            if (StringUtils.equalsIgnoreCase(String.valueOf(item.getKey()), key)) {
                value = String.valueOf(item.getValue());
                break;
            }
        }
        log.trace("get properties {} value {} from file {} ", key, value, properties);
        return value;
    }

    // Getter Setter
    String getProperties() {
        return resolve(PROPERTIES);
    }

    /**
     * print config value
     *
     * @return String
     */
    @Override
    public String toString() {
        StringBuffer buff = new StringBuffer();
        int maxLength = 0;
        int count = 0;
        for (Field field : this.getClass().getDeclaredFields()) {
            if (field.getType() == ConfigKey.class) {
                try {
                    ConfigKey<?> keyvalue = (ConfigKey<?>) field.get(this);
                    if (keyvalue.getName().length() > maxLength) {
                        maxLength = keyvalue.getName().length();
                    }
                    count += 1;
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    log.error("Exception resetting {" + field + "}");
                }
            }
        }

        buff.append(StringUtils.repeat("*", maxLength * 2) + "\n");
        buff.append(StringUtils.center("    Print Config    ", maxLength * 2, "*") + "\n");
        buff.append(StringUtils.repeat("*", maxLength * 2) + "\n");
        for (Field field : this.getClass().getDeclaredFields()) {
            if (field.getType() == ConfigKey.class) {
                try {
                    ConfigKey<?> keyvalue = (ConfigKey<?>) field.get(this);
                    String key = keyvalue.getName().toUpperCase();
                    String description = keyvalue.getDescription();
                    Object value = keyvalue.getValue();
                    String tmp = StringUtils.rightPad(key, maxLength, "  -") + " = " + value + "\n";
                    tmp = tmp + "[" + (StringUtils.isEmpty(description) ? "该参数没有描述信息" : description) + "]\n\n";
                    buff.append(tmp);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    log.error("Exception resetting {" + field + "}");
                }
            }
        }
        buff.append(StringUtils.repeat("-", maxLength * 2) + "\n");
        buff.append("Parameter Count: " + count + "\n");
        buff.append(StringUtils.repeat("*", maxLength * 2) + "\n");
        buff.append(StringUtils.center("    Print Config End.    ", maxLength * 2, "*") + "\n");
        buff.append(StringUtils.repeat("*", maxLength * 2) + "\n");
        return new String(buff);
    }



    /**
     * 检查所有参数是否合法
     */
    public void check() {
        Map<String, ConfigKey<Object>> configKeys = this.asMap();
        for (Entry<String, ConfigKey<Object>> item : configKeys.entrySet()) {
            item.getValue().check();
        }
    }

    /**
     * config bean
     */
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
            // 矫正规则
            this.name = name.toLowerCase().replace(".", "_");
            this.type = type;
            this.updateVaule();
        }

        public ConfigKey(String name, Class<T> type, T value) {
            // 矫正规则
            this.name = name.toLowerCase().replace(".", "_");
            this.type = type;
            this.value = value;
            this.defaultValue = value;
            this.updateVaule();
        }

        @SafeVarargs
        public ConfigKey(String name, Class<T> type, T value, Matcher<T>... matchers) {
            // 矫正规则
            this.name = name.toLowerCase().replace(".", "_");
            this.type = type;
            this.value = value;
            this.defaultValue = value;
            this.matchers = matchers;
            this.updateVaule();
        }

        @SafeVarargs
        public ConfigKey(String name, Class<T> type, T value, boolean mergeDefaultValue, Matcher<T>... matchers) {
            // 矫正规则
            this.name = name.toLowerCase().replace(".", "_");
            this.type = type;
            this.value = value;
            this.defaultValue = value;
            this.mergeDefaultValue = mergeDefaultValue;
            this.matchers = matchers;
            this.updateVaule();
        }

        @SuppressWarnings("unchecked")
        void updateVaule() {
            T currentValue = resolver(this.getName(), value, this.getType());
            if (mergeDefaultValue) {
                if (defaultValue instanceof JSONObject) {
                    currentValue = (T) ((JSONObject) defaultValue)
                            .fluentPutAll(((JSONObject) currentValue).getInnerMap());
                } else {
                    log.warn(this.getName() + " 不能合并默认参数。");
                }
            }
            this.value = currentValue;
        }

        public String getName() {
            return name;
        }

        public Class<T> getType() {
            return type;
        }

        public T getValue() {
            return value;
        }

        public void reset() {
            value = defaultValue;
        }

        @SuppressWarnings("unchecked")
        public void setValue(Object value) {
            if (mergeDefaultValue) {
                if (defaultValue instanceof JSONObject) {
                    if (value instanceof JSONObject) {
                        this.value = (T) ((JSONObject) this.value).fluentPutAll(((JSONObject) value).getInnerMap());
                    } else if (value instanceof String) {
                        JSONObject json = JSONObject.parseObject(String.valueOf(value));
                        this.value = (T) ((JSONObject) this.value).fluentPutAll(json.getInnerMap());
                    } else {
                        throw new ScriptException("参数[" + name + "]值必须是json格式。");
                    }
                } else {
                    log.warn(this.getName() + " 不能合并默认参数。");
                }
            } else {
                if (!this.value.equals(value)) {
                    log.info("Parameters override([new value] >>> key[old value]): [" + value + "] >>> " + name + "["
                            + this.value + "]");
                    this.value = (T) value;
                }
            }
        }

        public void check() {
            if (matchers == null) {
                return;
            }
            for (Matcher<T> matcher : matchers) {
                assertThat("FrameWorkConfig Key[" + name + "] value[" + value + "] Invalid format.", this.getValue(), matcher);
            }
        }

        public String getDescription() {
            return description;
        }

        public ConfigKey<T> setDescription(String description) {
            this.description = description;
            return this;
        }

    }

}
