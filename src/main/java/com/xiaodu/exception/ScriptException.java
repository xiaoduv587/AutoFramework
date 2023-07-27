//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xiaodu.exception;


public class ScriptException extends BaseException {
    private static final long serialVersionUID = 1L;

    public ScriptException() {
    }

    public ScriptException(String message) {
        super(message);
    }

    public ScriptException(String string, Exception e) {
        super(string, e);
    }

//    public ScriptException(CustomDriver driver, String string, Exception e) {
//        super(string, e);
//        this.driver = driver;
//    }

    public ScriptException(Throwable cause) {
        super(cause);
    }
}
