package com.wdm.passport.service;

import com.wdm.passport.model.User;

/**
 * @author wdmyong
 */
public interface ApiService {

    void storeServiceTicketUser(String serviceTicket, User user);

    User getUserByServiceTicket(String serviceTicket);
}
