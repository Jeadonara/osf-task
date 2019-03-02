package com.can.core.service;

import com.can.controller.request.PaginationRequest;
import com.can.core.dto.GetUsersInputDTO;
import com.can.core.dto.UserDTO;
import com.can.data.dao.UserDAO;
import com.can.data.entity.User;
import com.can.core.exception.CustomError;
import com.can.core.utils.ValidationUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.can.core.utils.Utils.*;
import static com.can.core.utils.ValidationUtil.*;
import static org.springframework.util.StringUtils.hasText;

@Service
public class DefaultUserService implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDTO getUser(Long id) {
        validateNumber(id, CustomError.INVALID_PARAMETER, "id");
        User user = userDAO.findById(id).orElse(null);
        ValidationUtil.validateNotNull(user, CustomError.RECORD_NOT_FOUND, "user", "id", String.valueOf(id));
        return convertToDTO(user);
    }


    /**
     * @param input
     *  Query parameters for user collection:
     *  page size, page number, firstName, lastName, addressCity
     * @return matched users
     */
    @Override
    public List<UserDTO> getUsers(GetUsersInputDTO input) {
        PaginationRequest pagingRequest = Optional.ofNullable(input.getPaginationRequest()).orElse(new PaginationRequest());

        int pageNumber = nvl(pagingRequest.getOffset(), 1);
        int pageSize = nvl(pagingRequest.getSize(), 10);
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize, new Sort(Sort.Direction.DESC, "id"));

        List<User> users = userDAO.getUsers(pageRequest, input.getFirstName(), input.getLastName(), input.getCity());
        return users.stream().map(DefaultUserService::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long id) {
        validateNumber(id, CustomError.INVALID_PARAMETER, "id");
        userDAO.deleteById(id);
    }

    /**
     * @param userInput
     * User Properties for creating a new user or updating existing user
     * @return
     * User uid that created or updated
     */
    @Override
    public Long saveUser(UserDTO userInput) {
        validateNotNull(userInput, CustomError.INVALID_PARAMETER, "userInput");
        validateHasText(userInput.getFirstName(), CustomError.INVALID_PARAMETER, "firstName");
        validateHasText(userInput.getLastName(), CustomError.INVALID_PARAMETER, "lastName");
        validateNotNull(userInput.getAddressStreet(), CustomError.INVALID_PARAMETER, "addressStreet");
        validateNotNull(userInput.getAddressCity(), CustomError.INVALID_PARAMETER, "addressCity");
        validateNotNull(userInput.getPhoneType(), CustomError.INVALID_PARAMETER, "phoneType");

        String phoneNumber = userInput.getPhoneNumber();
        assertTrue(hasText(phoneNumber) && phoneNumber.length() == 10, CustomError.GENERIC_ERROR, " Phone numbers must be 10 digits long");

        String email = userInput.getEmail();
        ValidationUtil.validateEmailAddress(email);

        final User user = Optional.ofNullable(userInput.getId())
                .map(id -> {
                    User existingUser = userDAO.getOne(id);
                    validateNotNull(existingUser, CustomError.RECORD_NOT_FOUND, "existingUser");
                    return existingUser;
                })
                .orElse(new User());

        BeanUtils.copyProperties(userInput, user,"id");

        User entity = userDAO.save(user);

        return entity.getId();
    }

    private static UserDTO convertToDTO(User input) {
        UserDTO user = new UserDTO();
        BeanUtils.copyProperties(input, user);
        return user;
    }
}
