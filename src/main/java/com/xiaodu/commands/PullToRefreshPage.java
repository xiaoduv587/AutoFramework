package com.xiaodu.commands;

import com.codeborne.selenide.Command;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.appium.AppiumDriverRunner;
import com.codeborne.selenide.impl.WebElementSource;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import javax.annotation.Nullable;

import static com.codeborne.selenide.appium.WebdriverUnwrapper.cast;
import static java.time.Duration.ofMillis;
import static java.util.Collections.singletonList;

public class PullToRefreshPage implements Command<SelenideElement> {

    @Nullable
    @Override
    public SelenideElement execute(
            SelenideElement proxy, WebElementSource locator, @Nullable Object[] objects) {
        AppiumDriver appiumDriver = cast(locator.driver(), AppiumDriver.class).orElseThrow();


        performScroll(appiumDriver);
        return proxy;
    }


    private Dimension getMobileDeviceSize(AppiumDriver appiumDriver) {
        return appiumDriver.manage().window().getSize();
    }

    private void performScroll(AppiumDriver appiumDriver) {
        Dimension size = getMobileDeviceSize(appiumDriver);
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence sequenceToPerformScroll = getSequenceToPerformScroll(finger, size);
        appiumDriver.perform(singletonList(sequenceToPerformScroll));
    }

    private Sequence getSequenceToPerformScroll(PointerInput finger, Dimension size) {
        int endY = (int) (size.getHeight() * 0.8);
        int startY = (int) (size.getHeight() * 0.2);
        return new Sequence(finger, 1)
                .addAction(
                        finger.createPointerMove(
                                ofMillis(0),
                                PointerInput.Origin.viewport(),
                                size.getWidth() / 2,
                                startY))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.MIDDLE.asArg()))
                .addAction(
                        finger.createPointerMove(
                                ofMillis(2000),
                                PointerInput.Origin.viewport(),
                                size.getWidth() / 2,
                                endY))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.MIDDLE.asArg()));
    }
}