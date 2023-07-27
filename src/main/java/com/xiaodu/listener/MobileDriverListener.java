//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xiaodu.listener;

import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.support.events.WebDriverListener;

import java.io.File;

public class MobileDriverListener implements WebDriverListener {
//    private static final Logger log = LogManager.getLogger(MobileDriverListener.class);
    WebDriver driver;
    Object click_style;
    Object sendkeys_style;
    Object clear_style;
    long beforeTo_timestamp;

    public MobileDriverListener(WebDriver driver) {
        this.driver = driver;
    }

    public File getScreenshotAsFile() {
        return (File)((TakesScreenshot)this.driver).getScreenshotAs(OutputType.FILE);
    }

    public JavascriptExecutor getJavascriptExecutor() {
        return (JavascriptExecutor)this.driver;
    }

    @Override
    public void beforeClick(WebElement element) {
        this.click_style = this.getStyle(element);
        this.highlight(element);
//        if (element instanceof CustomElement) {
//            CustomElement ele = (CustomElement)element;
//            String msg = String.format("click the button %s", StringUtils.isEmpty(ele.getName()) ? ele.getBy() : ele.getName());
//            log.info(msg, this.getScreenshotAsFile(), this.driver);
//        } else {
//            String msg = String.format("click the button %s", element);
//            log.info(msg, this.getScreenshotAsFile(), this.driver);
//        }

    }

    @Override
    public void afterClick(WebElement element) {
        this.setStyle(element, this.click_style);
    }


    @Override
    public void beforeSendKeys(WebElement element, CharSequence... keysToSend) {
        if (keysToSend != null) {
            StringBuffer buff = new StringBuffer();

            for(int i = 0; i < keysToSend.length; ++i) {
                buff.append(keysToSend[i]);
            }

            this.sendkeys_style = this.getStyle(element);
            this.highlight(element);
//            if (element instanceof CustomElement) {
//                CustomElement ele = (CustomElement)element;
//                String msg = String.format("enter %s in the input box %s", buff, StringUtils.isEmpty(ele.getName()) ? ele.getBy() : ele.getName());
//                log.info(msg, this.getScreenshotAsFile(), this.driver);
//            } else {
//                String msg = String.format("enter %s in the input box %s", buff);
//                log.info(msg, this.getScreenshotAsFile(), this.driver);
//            }
        }

    }

    @Override
    public void afterSendKeys(WebElement element, CharSequence... keysToSend) {
        this.setStyle(element, this.sendkeys_style);
    }

    @Override
    public void beforeClear(WebElement element) {
        this.clear_style = this.getStyle(element);
        this.highlight(element);
//        if (element instanceof CustomElement) {
//            CustomElement ele = (CustomElement)element;
//            String msg = String.format("clean up the input box %s", StringUtils.isEmpty(ele.getName()) ? ele.getBy() : ele.getName());
//            log.info(msg, this.getScreenshotAsFile(), this.driver);
//        } else {
//            String msg = String.format("clean up the input box %s", element);
//            log.info(msg, this.getScreenshotAsFile(), this.driver);
//        }

    }


    @Override
    public void beforeTo(Navigation navigation, String url) {
//        this.beforeTo_timestamp = System.currentTimeMillis();
//        log.info("Navigating to " + url);
    }

    @Override
    public void afterTo(Navigation navigation, String url) {
        long duration = (System.currentTimeMillis() - this.beforeTo_timestamp) / 1000L;
//        log.info("Loading {} page wastes {} seconds", url, duration, this.getScreenshotAsFile(), this.driver);
    }


    protected Object getStyle(WebElement element) {
        try {
            return this.getJavascriptExecutor().executeScript("element = arguments[0];return element.getAttribute('style');", new Object[]{element});
        } catch (UnsupportedCommandException var3) {
            return null;
        }
    }

    protected void highlight(WebElement element) {
        try {
//            this.getJavascriptExecutor().executeScript("element = arguments[0];original_style = element.getAttribute('style');element.setAttribute('style', original_style + \"; border: 3px solid red;\");", new Object[]{element});
//            this.getJavascriptExecutor().executeScript();", new Object[]{element});
            getJavascriptExecutor().executeScript("element = arguments[0];" + "original_style = element.getAttribute('style');"
                    + "element.setAttribute('style', original_style + \";" + " border: 3px solid red;\");"
                    + "setTimeout(function(){element.setAttribute('style', original_style);}, 2000);", element);
        } catch (UnsupportedCommandException var3) {
        }

    }

    protected void setStyle(WebElement element, Object style) {
        try {
            this.getJavascriptExecutor().executeScript("element = arguments[0];element.setAttribute('style', \"" + style + "\");", new Object[]{element});
        } catch (Exception var4) {
        }

    }
}
