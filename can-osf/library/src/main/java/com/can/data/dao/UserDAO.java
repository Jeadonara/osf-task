package com.can.data.dao;

import com.can.data.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDAO extends JpaRepository<User, Long> {

    @Query(value = " SELECT u FROM User u "
            + " WHERE ( ( :firstName IS NULL) OR u.firstName = :firstName ) "
            + " AND ( ( :lastName IS NULL) OR u.lastName = :lastName ) "
            + " AND ( ( :city IS NULL) OR u.addressCity = :city ) ")
    List<User> getUsers(Pageable pageable, @Param("firstName") String firstName, @Param("lastName") String lastName, @Param("city") String city);
}
