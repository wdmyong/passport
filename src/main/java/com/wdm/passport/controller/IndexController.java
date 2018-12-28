package com.wdm.passport.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wdmyong
 */
@RestController
@RequestMapping("/")
public class IndexController {

    @RequestMapping({"/", "/index"})
    String index() {
        return "index";
    }

    @RequestMapping("/cookie")
    void cookie(
            HttpServletRequest request,
            HttpServletResponse response) {
        Cookie cookie = new Cookie("test", "test");
        cookie.setDomain("wdm.com");
        response.addCookie(cookie);
    }
}
