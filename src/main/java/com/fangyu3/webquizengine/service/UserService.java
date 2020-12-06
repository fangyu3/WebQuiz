package com.fangyu3.webquizengine.service;

import com.fangyu3.webquizengine.domain.User;
import com.fangyu3.webquizengine.web.model.UserDto;
import org.springframework.stereotype.Service;

public interface UserService {
    public UserDto findUserByEmail(String userEmail);

    public UserDto saveUser(UserDto userDto);
}
