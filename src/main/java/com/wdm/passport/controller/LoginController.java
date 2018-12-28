package com.wdm.passport.controller;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wdm.passport.model.User;
import com.wdm.passport.service.ApiService;
import com.wdm.tool.json.JsonUtils;

/**
 * @author wdmyong
 */
@RestController
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private static final String TGC = "TGC";
    private static final String serviceTicket = "?st=%s";

    @Autowired
    private ApiService apiService;

    @RequestMapping("/login")
    String login(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "passwd", required = false, defaultValue = "") String passwd,
            @RequestParam("callback") String callback) {
        Cookie[] cookies = request.getCookies();
        Cookie cookie = null;
        if (cookies != null) {
            cookie = Arrays.stream(cookies) //
                    .filter(c -> TGC.equals(c.getName())).findAny().orElse(null);
        }
        if (cookie == null) {
            if (name.equals(passwd)) {
                cookie = new Cookie(TGC, "wdmyong");
                cookie.setDomain("wdm.com");
                response.addCookie(cookie);
            } else {
                return "wrong user !!!";
            }
        }
        User user = new User(123L, name, passwd);
        apiService.storeServiceTicketUser(cookie.getValue(), user);
        return redirectCallback(response, callback, cookie.getValue());
    }

    private String redirectCallback(HttpServletResponse response, String callback, String st) {
        try {
            response.sendRedirect(callback + String.format(serviceTicket, st));
            return "success";
        } catch (IOException e) {
            logger.error("error", e);
            return "error";
        }
    }

    @RequestMapping("/verify")
    public String verify(@RequestParam("st") String st) {
        User user = apiService.getUserByServiceTicket(st);
        if (user != null) {
            return JsonUtils.object2JsonStr(user);
        }
        return "verifyFail";
    }

    @RequestMapping("/logout")
    void logout(HttpServletResponse response) {
        Cookie cookie = new Cookie(TGC, "");
        cookie.setDomain("wdm.com");
        response.addCookie(cookie);
    }
}
