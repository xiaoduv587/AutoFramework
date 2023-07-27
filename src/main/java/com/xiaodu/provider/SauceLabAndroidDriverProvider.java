package com.xiaodu.provider;

import com.codeborne.selenide.WebDriverProvider;
import com.xiaodu.FrameWorkConfig;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

import javax.annotation.Nonnull;
import java.io.File;

@Log4j2
public class SauceLabAndroidDriverProvider implements WebDriverProvider {


    private static AppiumDriverLocalService service;


    @SneakyThrows
    @Nonnull
    @Override
    public WebDriver createDriver(@Nonnull Capabilities capabilities) {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2);
        options.setPlatformName("android");
        options.setDeviceName("Android");
        //options.setUiautomator2ServerInstallTimeout(Duration.ofSeconds(2)); //Seems to be not working
        options.setCapability("uiautomator2ServerInstallTimeout", 60000);
        options.setCapability(MobileCapabilityType.NO_RESET, true);
        options.setCapability(MobileCapabilityType.FULL_RESET, false);
        options.setCapability("autowebview", true);
        options.setCapability(AndroidMobileCapabilityType.RECREATE_CHROME_DRIVER_SESSIONS, true);
        options.setCapability("appPackage", "com/xiaodu");
        options.setCapability("appActivity", ".activity.SplashActivity");
        File chromedriver = new File("/Users/xiaodu/apps/99_chromedriver");
        options.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE, chromedriver.getAbsolutePath());


        return  new AndroidDriver(FrameWorkConfig.instance().APPIUM_HUB_URL.getValue(), options);
    }
}