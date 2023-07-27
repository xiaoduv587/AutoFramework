//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xiaodu.exception;

import java.util.HashMap;
import java.util.Map;

public class BaseException extends RuntimeException {
//    protected CustomDriver driver;
    Map<String, Object> info = new HashMap();
    private static final long serialVersionUID = 1L;

    public BaseException() {
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String string, Exception e) {
        super(string, e);
    }

//    public BaseException(CustomDriver driver, String string, Exception e) {
//        super(string, e);
//        this.driver = driver;
//    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public Object getInfo(String key) {
        return this.info.get(key);
    }

    public void setInfo(String key, Object value) {
        this.info.put(key, value);
    }

//    public BaseException setDriver(CustomDriver driver) {
//        this.driver = driver;
//        return this;
//    }
//
//    public CustomDriver getDriver() {
//        return this.driver;
//    }
}
