package com.can.core.service;

import com.can.core.exception.CustomError;
import com.can.core.security.TokenDTO;
import com.can.core.security.TokenProvider;
import com.can.core.utils.ValidationUtil;
import com.can.data.dao.InstanceUserCredentialsDAO;
import com.can.data.entity.InstanceUserCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultInstanceUserService implements InstanceUserService {

    @Autowired
    private InstanceUserCredentialsDAO instanceUserCredentialsDAO;

    @Autowired
    private TokenProvider jwtTokenProvider;

    /**
     * @param userName
     * @param password
     * @return Token Value and Expiration Time
     */
    public TokenDTO login(String userName, String password){
            InstanceUserCredentials instanceUser = instanceUserCredentialsDAO.findByUserNameAndPassword(userName, password);
            ValidationUtil.validateNotNull(instanceUser, CustomError.GENERIC_ERROR, "unauthorized");
            return jwtTokenProvider.generateToken(instanceUser.getUserName());
    }

    @Override
    public void createInstanceUser(String userName, String password) {
        ValidationUtil.validateHasText(userName, CustomError.INVALID_PARAMETER, "userName");
        ValidationUtil.validateHasText(password, CustomError.INVALID_PARAMETER, "password");

        InstanceUserCredentials instanceUserCredentials = new InstanceUserCredentials();
        instanceUserCredentials.setUserName(userName);
        instanceUserCredentials.setPassword(password);
        instanceUserCredentialsDAO.save(instanceUserCredentials);
    }
}
