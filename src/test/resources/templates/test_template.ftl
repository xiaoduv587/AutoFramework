package com.lenovo.automation;

import com.lenovo.dsa.page.PostPage;
import com.tests.base.TestMobileSetup;
import org.testng.annotations.Test;

import static com.codeborne.selenide.appium.ScreenObject.screen;

public class ShopTest extends TestMobileSetup {

<#--    @Test(priority = 1, description = "xxx")-->
<#--    public void testChat() {-->
<#--        PostPage page = screen(PostPage.class);-->

<#--        <#list elements as element>page.click${element.methodName}();-->
<#--            String ${element.fieldName}Text = page.get${element.methodName}Text();-->
<#--            // 添加断言或其他逻辑操作-->
<#--        </#list>-->
<#--    }-->

<#list elements as element>
    <#if element.isClickElement()>
        @Test(priority = 1, description = "${element.id}点击测试")
        void testClick${element.name?cap_first}() {
<#--        ${className} page = screen(${className}.class);-->
        page.click${element.name?cap_first}();
        // 执行后续断言或操作
        }
    </#if>

    <#if element.isTextElement()>
        @Test(priority = 2, description = "${element.id}文本获取测试")
        void testGet${element.name?cap_first}Text() {
<#--        ${className} page = screen(${className}.class);-->
        String text = page.get${element.name?cap_first}Text();
        // 执行后续断言或操作
        }
    </#if>

    <#if element.isInputFieldElement()>
        @Test(priority = 3, description = "${element.id}输入测试")
        void testEnter${element.name?cap_first}() {
<#--        ${className} page = screen(${className}.class);-->
        page.enter${element.name?cap_first}("输入值");
        // 执行后续断言或操作
        }
    </#if>

</#list>


}
