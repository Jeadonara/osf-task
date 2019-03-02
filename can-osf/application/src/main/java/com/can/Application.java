package com.can;


import com.can.data.dao.InstanceUserCredentialsDAO;
import com.can.data.entity.InstanceUserCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication(scanBasePackages = "com.can")
public class Application implements CommandLineRunner {

    @Autowired
    private InstanceUserCredentialsDAO systemUserCredentialsDAO;

    @PostConstruct
    public void init() {
        // Setting Spring Boot SetTimeZone
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }



    @Override
    public void run(String... params) throws Exception {
        InstanceUserCredentials admin = new InstanceUserCredentials();
        admin.setUserName("admin");
        admin.setPassword("admin");
        systemUserCredentialsDAO.save(admin);

    }
}
