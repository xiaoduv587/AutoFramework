package com.tests.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.tests.base.TestWebSetup;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Listeners;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


@Listeners({io.qameta.allure.testng.AllureTestNg.class})
public class AllureSelenideDemo extends TestWebSetup {


    @org.testng.annotations.Test(priority = 1, description = "Test Case 1: Check Title")
    @Description("Verify that the page title is correct")
    public void testTitle() {
        Allure.step("Step 1: Opening Baidu.com");
        open("https://www.baidu.com");
        String title = Selenide.title();
        Assert.assertEquals(title, "百度一下，你就知道");
    }

    @org.testng.annotations.Test(priority = 2, description = "Test Case 2: Search")
    @Description("Verify that search function is working correctly")
    public void testSearch() {
        Allure.step("Step 2: Entering search query");
         $(By.id("kw")).should(Condition.visible).setValue("Allure Selenide Demo").pressEnter();
        Allure.step("Step 3: Verifying results");
        SelenideElement resultsPane = $(By.id("results"));
        SelenideElement resultLink = resultsPane.$("a[href*='qameta/allure']");
        resultLink.click();
        String title = Selenide.title();
        Assert.assertEquals(title, "GitHub - qameta/allure: Allure framework for runShellRetry result reporting");
    }
}