package com.tests.web;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.*;

public class DrapDemo {

    @Test
    public void drap(){
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        open("http://the-internet.herokuapp.com/drag_and_drop");


        WebElement colA = $("div#column-a");
        WebElement colB = $("div#column-b");

        actions().dragAndDrop(colA, colB).perform();




// alternative code same issue:
// dragAndDrop.clickAndHold(colA).moveToElement(colB).release().perform();
    }

    public static String getWebCSSSelector(WebElement element) {
        final String JS_BUILD_CSS_SELECTOR =
                "for(var e=arguments[0],n=[],i=function(e,n){if(!e||!n)return 0;f" +
                        "or(var i=0,a=e.length;a>i;i++)if(-1==n.indexOf(e[i]))return 0;re" +
                        "turn 1};e&&1==e.nodeType&&'HTML'!=e.nodeName;e=e.parentNode){if(" +
                        "e.id){n.unshift('#'+e.id);break}for(var a=1,r=1,o=e.localName,l=" +
                        "e.className&&e.className.trim().split(/[\\s,]+/g),t=e.previousSi" +
                        "bling;t;t=t.previousSibling)10!=t.nodeType&&t.nodeName==e.nodeNa" +
                        "me&&(i(l,t.className)&&(l=null),r=0,++a);for(var t=e.nextSibling" +
                        ";t;t=t.nextSibling)t.nodeName==e.nodeName&&(i(l,t.className)&&(l" +
                        "=null),r=0);n.unshift(r?o:o+(l?'.'+l.join('.'):':nth-child('+a+'" +
                        ")'))}return n.join(' > ');";

        return (String) executeJavaScript(JS_BUILD_CSS_SELECTOR, element);
    }

}
