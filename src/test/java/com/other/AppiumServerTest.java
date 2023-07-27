package com.other;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import java.net.URL;

public class AppiumServerTest {

    public static void main(String[] args) {
        startAppiumServer();
    }

    private static AppiumDriverLocalService service;

    public static void startAppiumServer() {

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
        final URL url = service.getUrl();
        System.out.println("url = " + url);

        service.start();


    }

}
