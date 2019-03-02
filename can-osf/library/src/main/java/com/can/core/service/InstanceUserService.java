package com.can.core.service;

import com.can.core.security.TokenDTO;

public interface InstanceUserService {

    TokenDTO login(String userName, String password);

    void createInstanceUser(String userName, String password);
}
