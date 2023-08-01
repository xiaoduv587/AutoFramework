
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mte.wdd.main;

import com.mte.wdd.core.MteSenseDynamicEngine;
import com.mte.wdd.util.MteSenseFileIO;
import com.mte.wdd.util.MteSenseInstanceUtil;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import java.io.File;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

public class MteSenseLoader implements MteSenseLoaderProperty {
    private int modifyTimeCount = 0;
    private long lastModifyTime = 0L;
    private int delayTime = 3;
    private Class clazz;

    public MteSenseLoader() {
    }

    public MteSenseLoader(int delayTime) {
        this.setDelayTime(delayTime);
    }


    public void senseLoader(MteSenseLoaderOptions options) {
        this.senseLoader(options.getLoaderOption("mtesensewdd.fullFilePath"), options.getLoaderOption("mtesensewdd.fullClassName"), options.getLoaderOption("mtesensewdd.methodName"));
    }

    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }

    private boolean isModify(String fullFilePath) {
        try {
            File actionFile = new File(fullFilePath);
            if (this.modifyTimeCount != 0) {
                long current = actionFile.getAbsoluteFile().lastModified();
                if (this.lastModifyTime != current && actionFile.getAbsoluteFile().canWrite()) {
                    ++this.modifyTimeCount;
                    this.lastModifyTime = current;
                    return true;
                } else {
                    return false;
                }
            } else {
                this.lastModifyTime = actionFile.getAbsoluteFile().lastModified();
                ++this.modifyTimeCount;
                return true;
            }
        } catch (Exception var5) {
            var5.printStackTrace();
            return false;
        }
    }

    public void senseLoader(String fullFilePath, String fullClassName, String methodName) {
        while (true) {
            do {
                MteSenseInstanceUtil.sleep(this.delayTime);
            } while (!this.isModify(fullFilePath));

            this.loadActionMethod(fullFilePath, fullClassName, methodName);
        }
    }

    private void loadActionMethod( String fullFilePath, String fullClassName, String methodName) {
        System.out.println("<---------------- Webdriver Dynamic Debug Start ---------------->\n");
        System.out.println(fullFilePath + " last modified time is " + this.lastModifyTime + "\n");

        try {
            MteSenseDynamicEngine engine = MteSenseDynamicEngine.getInstance();
            String source = MteSenseFileIO.read(fullFilePath);

            this.clazz = engine.javaCodeToObject(fullClassName, source).newInstance().getClass();
            this.clazz.getMethod(methodName).invoke(this.clazz.newInstance());



            System.out.println(methodName + " has been executed at " + MteSenseInstanceUtil.getCurrentTime() + "\n");
        } catch (Exception var8) {
            var8.printStackTrace();
        }

        System.out.println("<---------------- Webdriver Dynamic Debug End   ---------------->\n");
    }
}
