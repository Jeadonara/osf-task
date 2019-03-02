package com.can.controller;

import com.can.controller.request.RequestLogin;
import com.can.controller.response.BaseResponse;
import com.can.controller.response.ResponseLogin;
import com.can.core.security.TokenDTO;
import com.can.core.service.InstanceUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@Transactional
public class InstanceUserController {

    @Autowired
    private InstanceUserService instanceUserService;

    @RequestMapping(value = "contact/signup", method = RequestMethod.POST)
    public BaseResponse signup(@RequestBody RequestLogin request) {
        instanceUserService.createInstanceUser(request.getUserName(),request.getPassword());
        return new BaseResponse();
    }

    @RequestMapping(value = "contact/login", method = RequestMethod.POST)
    public ResponseLogin login(@RequestBody RequestLogin request) {
        return userLogin(request);
    }

    @RequestMapping(value = "contact/login/refresh", method = RequestMethod.POST)
    public ResponseLogin loginRefresh(@RequestBody RequestLogin request) {
        return userLogin(request);
    }

    private ResponseLogin userLogin (RequestLogin request){
        TokenDTO token = instanceUserService.login(request.getUserName(), request.getPassword());

        ResponseLogin response = new ResponseLogin();
        response.setToken(token.getValue());
        response.setExpiresAt(token.getExpiresAt().toString());

        return response;
    }


}
