package com.wdm.passport.service.impl;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.wdm.passport.model.User;
import com.wdm.passport.service.ApiService;
import com.wdm.passport.service.UserInfoService;

/**
 * @author wdmyong
 */
@Service
@Lazy
public class ApiServiceImpl implements ApiService {

    private static final Map<String, Long> map = Maps.newConcurrentMap();

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public void storeServiceTicketUser(String serviceTicket, User user) {
        map.put(serviceTicket, user.getId());
        userInfoService.storeUser(user);
    }

    @Override
    public User getUserByServiceTicket(String serviceTicket) {
        return Optional.ofNullable(map.get(serviceTicket))
                .map(userInfoService::getUser)
                .orElse(null);
    }
}
