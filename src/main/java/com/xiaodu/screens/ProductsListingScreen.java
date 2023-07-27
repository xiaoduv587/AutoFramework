//package com.xiaodu.screens;
//
//import com.codeborne.selenide.As;
//import com.codeborne.selenide.Condition;
//import com.codeborne.selenide.appium.AppiumSelectors;
//import com.codeborne.selenide.appium.SelenideAppiumElement;
//import com.xiaodu.commands.ScrollToElement;
//import com.xiaodu.conditions.CustomCondition;
//import io.appium.java_client.pagefactory.AndroidFindBy;
//import io.appium.java_client.pagefactory.iOSXCUITFindBy;
//import org.openqa.selenium.By;
//
//import static com.codeborne.selenide.Selenide.$;
//import static com.codeborne.selenide.appium.AppiumClickOptions.tap;
//import static com.codeborne.selenide.appium.ScreenObject.screen;
//import static com.xiaodu.locator.LocatorIdentifier.getLocator;
//
//public class ProductsListingScreen {
//
//    @As("bike light product")
//    @AndroidFindBy(xpath = "//android.widget.TextView[@text='$9.99']/preceding-sibling::android.view.ViewGroup/android.widget.ImageView")
//    @iOSXCUITFindBy(accessibility = "Sauce Labs Bike Light")
//    private SelenideAppiumElement bikeLightProduct;
//
//    @As("one sie product")
//    @AndroidFindBy(xpath = "//android.widget.TextView[@text='$7.99']/preceding-sibling::android.view.ViewGroup/android.widget.ImageView")
//    @iOSXCUITFindBy(accessibility = "Sauce Labs Onesie")
//    private SelenideAppiumElement oneSieProduct;
//
//    @As("Footer text")
//    private static final By FOOTER_ANDROID = AppiumSelectors.withText("All Rights Reserved");
//
//    @As("Footer text")
//    private static final By FOOTER_IOS = AppiumSelectors.withName("All Rights Reserved");
//
//    public ProductDetailsScreen selectBikeLightProduct() {
//        bikeLightProduct.shouldBe(Condition.visible).click(tap()); //native event tap
//        return screen(ProductDetailsScreen.class);
//    }
//
//    public ProductDetailsScreen selectOneSieProduct() {
//        oneSieProduct.scrollTo().shouldBe(Condition.visible).click();
//        return screen(ProductDetailsScreen.class);
//    }
//
//    public void checkWhetherFooterIsPresent() {
//        $(getLocator(FOOTER_ANDROID, FOOTER_IOS))
//                .execute(new ScrollToElement())
//                .shouldHave(CustomCondition.attributeMatching("text", "label", "Sauce Labs"));
//    }
//}