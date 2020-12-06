package com.fangyu3.webquizengine.mappers;

import com.fangyu3.webquizengine.config.security.UserDetailsImpl;
import com.fangyu3.webquizengine.domain.Quiz;
import com.fangyu3.webquizengine.domain.User;
import com.fangyu3.webquizengine.web.model.QuizDto;
import com.fangyu3.webquizengine.web.model.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.userdetails.UserDetails;

@Mapper
public interface UserMapper {
    public UserDto userToUserDto(User user);

    public User userDtoToUser(UserDto userDto);

    @Mapping(target="username", source="email")
    public UserDetailsImpl userToUserDetails(User user);
}
