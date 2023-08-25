package com.jnu.jnucross.adapter;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.HttpRequest;
public class Verifier {
    public static void VerifyChain(String sender, String receiver) {
        /*String jsonBody = "{\"key1\": \"value1\", \"key2\": \"value2\"}";

        // 发起POST请求
        HttpResponse response = HttpRequest.post("http://example.com/api")
                .header("Content-Type", "application/json") // 设置请求头
                .body(jsonBody) // 设置请求体
                .execute();

        // 获取响应状态码
        int status = response.getStatus();
        System.out.println("HTTP Status Code: " + status);

        // 获取响应内容
        String responseBody = response.body();
        System.out.println("Response Body: " + responseBody);
    }*/
        String getUrl = "https://example.com/api/resource";
        HttpResponse getResponse = HttpUtil.createGet(getUrl).execute();
        String getResult = getResponse.body();
        System.out.println("GET Response: " + getResult);
    }
}
