package com.can.controller.response;

import com.can.controller.model.UserModel;

import java.util.List;

public class ResponseSearchUser {

    private List<UserModel> users;

    public ResponseSearchUser(List<UserModel> users) {
        this.users = users;
    }

    public List<UserModel> getUsers() {
        return users;
    }

    public void setUsers(List<UserModel> users) {
        this.users = users;
    }
}
