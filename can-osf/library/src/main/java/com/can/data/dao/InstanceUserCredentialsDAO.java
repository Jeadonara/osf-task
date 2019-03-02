package com.can.data.dao;

import com.can.data.entity.InstanceUserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstanceUserCredentialsDAO extends JpaRepository<InstanceUserCredentials,Long> {

    InstanceUserCredentials findByUserName(String userName);

    InstanceUserCredentials findByUserNameAndPassword(String userName,String password);

}
