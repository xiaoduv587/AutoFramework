package com.xiaodu.page;


import static com.codeborne.selenide.Selenide.$x;

public class CommonElements {


    /**
     * toast
     */
//    @AndroidFindBy(className = "android.widget.Toast")
//    private SelenideElement toast;

    public String  getToast(){
        return $x("//*[@class='android.widget.Toast']").as("toast").getText();
    }



}
