package com.wdm.passport.app1.controller;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wdmyong
 */
@RestController
@RequestMapping("/app1")
public class App1IndexController {

    private static final Logger logger = LoggerFactory.getLogger(App1IndexController.class);
    // todo，放到配置中
    private static final String PASSPORT_URL = "http://test.wdm.com/login?name=%s&passwd=%s&callback=%s";
    private static final String PASSPORT_VERIFY_URL = "http://test.wdm.com/verify?st=%s";

    @RequestMapping("/index")
    public String index(HttpServletResponse response) {
        try {
            response.sendRedirect(String.format(PASSPORT_URL, 123, 123, "http://test.cel.com/app1/sso"));
            return "success";
        } catch (IOException e) {
            logger.error("error", e);
            return "fail";
        }
    }

    @RequestMapping("/sso")
    public String cas(@RequestParam("st") String st) {
        String url = String.format(PASSPORT_VERIFY_URL, st);
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        final Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            if (response.body() != null) {
                return response.body().string();
            }
        } catch (IOException e) {
            logger.error("error", e);
        }
        return "succ";
    }
}
