package com.dynamic;

import com.codeborne.selenide.Configuration;
import com.mte.wdd.main.MteSenseLoader;
import com.mte.wdd.main.MteSenseLoaderOptions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

//使用前需先将Before_Dynamic_Debug 放在本地testng.xml中
public class Before_Dynamic_Debug_local {


    public static void main(String[] args) {
        MteSenseLoader loader = new MteSenseLoader(5);
        MteSenseLoaderOptions options = new MteSenseLoaderOptions();

        options.setLoaderOption("mtesensewdd.fullFilePath", "src/test/java/com/dynamic/DynamicDebug.java");
        options.setLoaderOption("mtesensewdd.fullClassName", "com.dynamic.DynamicDebug");
        options.setLoaderOption("mtesensewdd.methodName", "runDynamicAction");

        loader.senseLoader(options);
    }

}