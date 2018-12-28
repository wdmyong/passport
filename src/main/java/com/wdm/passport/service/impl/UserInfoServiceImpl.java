package com.wdm.passport.service.impl;

import java.util.Map;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.wdm.passport.model.User;
import com.wdm.passport.service.UserInfoService;

/**
 * @author wdmyong
 */
@Service
@Lazy
public class UserInfoServiceImpl implements UserInfoService {

    private static final Map<Long, User> userMap = Maps.newConcurrentMap();

    @Override
    public void storeUser(User user) {
        userMap.put(user.getId(), user);
    }

    @Override
    public User getUser(long id) {
        return userMap.get(id);
    }
}
