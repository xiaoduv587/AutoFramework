package com.tests.base;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.testng.TextReport;
import com.xiaodu.FrameWorkConfig;
import com.xiaodu.utils.DateHelper;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selectors.byLinkText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@Listeners(value = {io.qameta.allure.testng.AllureTestNg.class, TextReport.class})
public class TestWebSetup {


    @BeforeClass
    protected void openWeb() {
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
//        Configuration.holdBrowserOpen=true;
//        Configuration.remote="http://10.122.184.148:4444/wd/hub";


        Map<String, Object> options = new HashMap<>();
        options.put("enableVNC", FrameWorkConfig.instance().SELENOID_ENABLE_VNC.getValue());
        options.put("enableVideo", !FrameWorkConfig.instance().SELENOID_ENABLE_VIDEO.getValue());
        options.put("enableLog", true);
        options.put("name", "selenide_selenoid_test");
//        options.put("videoName", selenoidVideoNameOfTestCase);

        Configuration.browserCapabilities.setCapability("selenoid:options", options);

        // 在此处编写登录测试代码，使用传入的用户名和密码
//        open("https://www.baidu.com/");
        $(".notLogin > .text:nth-child(1)").shouldBe(Condition.visible).click();
        $(".phoneUserInput > input").click();
        $(".phoneUserInput > input").val("15545499283");
        $("#securityLoginState").click();
        $("#phoneSign1").click();
        $(byLinkText("使用密码登录")).click();
        $("#passwordInput .passwordView").click();
        $("#passwordInput .passwordView").val("Ds123456");
        $(".loginFrome4 .buttonColor").click();
        DateHelper.waitSeconds(3);
    }

}