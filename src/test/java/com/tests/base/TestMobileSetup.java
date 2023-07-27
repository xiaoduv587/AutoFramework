package com.tests.base;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.appium.SelenideAppium;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.codeborne.selenide.testng.ScreenShooter;
import com.codeborne.selenide.testng.TextReport;
import com.xiaodu.FrameWorkConfig;
import com.xiaodu.listener.AllureOnFailListener;
import com.xiaodu.steps.Steps;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.*;

import java.net.URL;

@Log4j2
@Listeners(value = {TextReport.class, ScreenShooter.class, AllureOnFailListener.class})
public class TestMobileSetup {

    private static AppiumDriverLocalService service;
    protected Steps steps;

    @BeforeSuite(alwaysRun = true)
    protected void openApp() {


        AppiumServiceBuilder appiumServiceBuilder = new AppiumServiceBuilder();
        appiumServiceBuilder.usingAnyFreePort()
                .withArgument(() -> "--base-path", "/wd/hub")
                .withArgument(() -> "--log-timestamp");
        service = AppiumDriverLocalService.buildService(appiumServiceBuilder);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (!service.isRunning()) {
                service.stop();
            }
        }));
        URL url = service.getUrl();
        service.start();

        FrameWorkConfig.instance().APPIUM_HUB_URL.setValue(url);


    }

    @BeforeClass(alwaysRun = true)
    protected void setUp() {
        SelenideAppium.launchApp();
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
        steps = new Steps();

    }

    @AfterClass(alwaysRun = true)
    protected void setDown() {
        Selenide.closeWebDriver();
        steps = null;
    }


    @AfterSuite(alwaysRun = true)
    protected void closeApp() {

    }

}