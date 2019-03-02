package com.can.controller;

import com.can.controller.model.UserModel;
import com.can.controller.request.PaginationRequest;
import com.can.controller.request.RequestGetUsersSearchInput;
import com.can.controller.request.RequestSaveUser;
import com.can.controller.response.BaseResponse;
import com.can.controller.response.ResponseSaveUser;
import com.can.controller.response.ResponseSearchUser;
import com.can.core.dto.GetUsersInputDTO;
import com.can.core.dto.UserDTO;
import com.can.core.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;

@RestController
@Transactional
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "contact/add", method = RequestMethod.POST)
    public ResponseSaveUser createUser(@RequestBody RequestSaveUser request) {
        return saveUser(null, request);
    }


    @RequestMapping(value = "contact/{uid}", method = RequestMethod.POST)
    public ResponseSaveUser updateUser(@PathVariable(value = "uid") @NotNull Long uid, @RequestBody RequestSaveUser request) {
        return saveUser(uid, request);
    }

    private ResponseSaveUser saveUser(Long uid, RequestSaveUser request) {
        UserDTO user = new UserDTO();
        BeanUtils.copyProperties(request, user);
        user.setId(uid);
        Long id = userService.saveUser(user);

        ResponseSaveUser response = new ResponseSaveUser();
        response.setId(id);
        return response;
    }


    @RequestMapping(value = "contact", method = RequestMethod.GET)
    public ResponseSearchUser getUsers(PaginationRequest request) {
        GetUsersInputDTO input = new GetUsersInputDTO();
        input.setPaginationRequest(request);

        return new ResponseSearchUser(
                userService.getUsers(input)
                        .stream()
                        .map(UserController::covertToModel)
                        .collect(Collectors.toList()));
    }

    @RequestMapping(value = "contact/search", method = RequestMethod.GET)
    public ResponseSearchUser getUsers(RequestGetUsersSearchInput searchInput) {
        GetUsersInputDTO input = new GetUsersInputDTO();
        input.setPaginationRequest(searchInput.getRequest());
        input.setFirstName(searchInput.getFirstName());
        input.setLastName(searchInput.getLastName());
        input.setCity(searchInput.getCity());

        return new ResponseSearchUser(
                userService.getUsers(input)
                        .stream()
                        .map(UserController::covertToModel)
                        .collect(Collectors.toList()));
    }


    @RequestMapping(value = "contact/{uid}", method = RequestMethod.GET)
    public UserModel getUser(@PathVariable(value = "uid") @NotNull Long uid) {
        UserDTO user = userService.getUser(uid);
        return covertToModel(user);
    }


    @RequestMapping(value = "contact/{uid}", method = RequestMethod.DELETE)
    public BaseResponse deleteUser(@PathVariable(value = "uid") @NotNull Long uid) {
        userService.deleteUser(uid);
        return new BaseResponse();
    }

    private static UserModel covertToModel(UserDTO user) {
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(user, userModel);
        return userModel;
    }


}
