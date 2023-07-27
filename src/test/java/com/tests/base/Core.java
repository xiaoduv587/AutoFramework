package com.tests.base;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.appium.SelenideAppium;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

@Log4j2()
//@Listeners({TestListener.class})
public class Core {


//    protected InterFaceTest interFaceTest = InterFaceTest.newInstance();

    @BeforeClass
    protected void openApp() {
        SelenideAppium.launchApp();
    }

    @AfterClass(alwaysRun = true)
    protected void closeApp() {
        Selenide.closeWebDriver();
    }
}
