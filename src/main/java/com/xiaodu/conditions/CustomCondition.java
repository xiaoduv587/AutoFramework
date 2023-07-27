package com.xiaodu.conditions;

import com.codeborne.selenide.CheckResult;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Driver;
import com.codeborne.selenide.appium.AppiumDriverRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.Nonnull;

public final class CustomCondition {

    private CustomCondition() {
    }

    public static Condition attributeMatching(String androidAttribute, String iosAttribute, String expectedValue) {
        boolean androidDriver = AppiumDriverRunner.isAndroidDriver();
        String attributeInContext = androidDriver ? androidAttribute : iosAttribute;

        return new Condition("element should have " + attributeInContext + " with value " + expectedValue) {
            @Nonnull
            @Override
            public CheckResult check(Driver driver, WebElement element) {
                String actualValue = element.getAttribute(attributeInContext);
                return new CheckResult(actualValue.contains(expectedValue), actualValue);
            }
        };
    }

    public static Condition attributeMatching1(By androidby, String iosAttribute, String expectedValue) {
        boolean androidDriver = AppiumDriverRunner.isAndroidDriver();
//        String attributeInContext = androidDriver ? androidAttribute : iosAttribute;

        return new Condition("element should have " + androidby + " with value " + expectedValue) {
            @Nonnull
            @Override
            public CheckResult check(Driver driver, WebElement element) {
                return super.check(driver, element);
            }

            //            @Nonnull
//            @Override
//            public CheckResult check(Driver driver, WebElement element) {
////        String actualValue = element.getAttribute(attributeInContext);
//                if (element != null) {
//                    return new CheckResult(true, "actualValue");
//                }
//                return new CheckResult(false, "actualValue");
//
//            }
        };
    }
}