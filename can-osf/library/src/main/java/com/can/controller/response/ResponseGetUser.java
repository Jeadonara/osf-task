package com.can.controller.response;

import com.can.controller.model.UserModel;

public class ResponseGetUser {

    private UserModel user;

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
