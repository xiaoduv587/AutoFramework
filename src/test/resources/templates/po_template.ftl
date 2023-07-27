package com.lenovo.dsa.page;

import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import static com.codeborne.selenide.Condition.visible;

public class TestPage {

<#list elements as element>
    /**
    * ${element.note}
    */
    @AndroidFindBy(id = "${element.id}")
    private SelenideElement ${element.name};

    <#if element.isClickElement()>
        public void click${element.name?cap_first}() {
        ${element.name}.as("${element.note}").shouldBe(visible).click();
        }
    </#if>

    <#if element.isTextElement()>
        public String get${element.name?cap_first}Text() {
        return ${element.name}.as("${element.note}").shouldBe(visible).getText();
        }
    </#if>

    <#if element.isInputFieldElement()>
        public void enter${element.name?cap_first}(String text) {
        ${element.name}.as("${element.note}").shouldBe(visible).setValue(text);
        }
    </#if>

</#list>

}
