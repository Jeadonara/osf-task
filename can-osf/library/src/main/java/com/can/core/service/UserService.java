package com.can.core.service;

import com.can.core.dto.GetUsersInputDTO;
import com.can.core.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO getUser(Long id);

    List<UserDTO> getUsers(GetUsersInputDTO getUsersInput);

    void deleteUser(Long id);

    Long saveUser(UserDTO userInput);


}
