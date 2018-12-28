package com.wdm.passport.service;

import com.wdm.passport.model.User;

/**
 * @author wdmyong
 */
public interface UserInfoService {

    void storeUser(User user);

    User getUser(long id);
}
