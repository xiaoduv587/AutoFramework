//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mte.wdd.main;

import java.util.HashMap;

public class MteSenseLoaderOptions {
    private HashMap<String, String> options = new HashMap();

    public MteSenseLoaderOptions() {
    }

    public MteSenseLoaderOptions(HashMap<String, String> options) {
        this.options = options;
    }

    public void setOptionsMap(HashMap<String, String> options) {
        this.options = options;
    }

    public void setLoaderOption(String key, String value) {
        this.options.put(key, value);
    }

    public String getLoaderOption(String key) {
        try {
            String value = (String)this.options.get(key);
            return value;
        } catch (Exception var4) {
            return null;
        }
    }
}
