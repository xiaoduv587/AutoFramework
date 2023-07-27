package com.xiaodu.steps;


import com.xiaodu.listener.ScreenShoter;
import io.qameta.allure.Step;


public class Steps {

    @Step("Screenshot")
    public void screenShot(String name) {
        ScreenShoter.makeScreenshot(name);
    }

}

