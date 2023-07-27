package com.other;

import com.xiaodu.api.BaseRequestSpecification;
import com.xiaodu.api.assertwrapper.ResponseAssert;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 将charles中的curl转成RestAssure代码
 */
public class CurlToCharlesRestAssuredConverter {


    public static void main(String[] args) {
        String curlCommand = "" ;
        String restAssuredCode = convertCurlToRestAssured("aaa", curlCommand);
        System.out.println(restAssuredCode);
    }

    /**
     * @param mothedName  设置的方法名，方法
     * @param curlCommand curl
     * @return
     */
    public static String convertCurlToRestAssured(String mothedName, String curlCommand) {
        String[] commandParts = curlCommand.split("\\s+");

        StringBuilder restAssuredCode = new StringBuilder();
//        restAssuredCode.append("RestAssured.given()\n");



        restAssuredCode.append("BaseRequestSpecification\n" +
                "                .getUserTokenRequest(header)");

        restAssuredCode.append("\n\n //请将上述代码挪到相应Api类中 \n\n");

        restAssuredCode.append("public void " + mothedName + "(String token) {\n");
        restAssuredCode.append("  Map<String, String> header = new HashMap<>();\n" +
                "        header.put(\"Authorization\", token);");




        Map<String, String> headers = new HashMap<>();
        String url = "";
        String requestBody = "";

        for (int i = 1; i < commandParts.length; i++) {
            String part = commandParts[ i ];
            if (part.equals("-H")) {
                String headerKey = commandParts[ ++i ];
                String headerValue = commandParts[ ++i ].replace("\"", "");
//                headers.put(headerKey, headerValue);
            } else if (part.equals("--data-binary")) {
                requestBody = commandParts[ ++i ].replace("\"", "");
            } else if (part.equals("--compressed")) {
                // Ignore "--compressed" flag
                continue;
            } else {
                url = part.replace("\"", "");
            }
        }

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            restAssuredCode.append(String.format(".header(\"%s\", \"%s\")\n", entry.getKey(), entry.getValue()));
        }

        if (!requestBody.isEmpty()) {
            restAssuredCode.append(String.format(".body(\"%s\")\n", requestBody));
        }

        //去掉baseUri
        String prefix = "https://www.baidu.com";
        if (url.startsWith(prefix)) {
            url = url.substring(prefix.length());
        }

        if (curlCommand.contains("--data-binary")) {

            restAssuredCode.append(String.format(".post(\"%s\");", url));
        } else {
            restAssuredCode.append(String.format(".get(\"%s\");", url));
        }

        restAssuredCode.append("\n");
        restAssuredCode.append("Response response = DemoApi.demo(header);");
        restAssuredCode.append("response.print();");


        restAssuredCode.append(" ResponseAssert.assertThat(response)\n" +
                ".statusCodeIs(200)\n" +
                ".hasKeyWithValue(\"success\", \"true\")\n" +
                ".hasKeyWithValue(\"code\", \"0\")\n" +
                ".assertAll();");
        restAssuredCode.append("\n}");


//        Response doSingInResponse = SignInApi.doSignIn(header);

//        doSingInResponse.print();
//        ResponseAssert.assertThat(doSingInResponse)
//                .statusCodeIs(200)
//                .hasKeyWithValue("success", "true")
//                .hasKeyWithValue("code", "0")
//                .assertAll();


        return restAssuredCode.toString();
    }


}
