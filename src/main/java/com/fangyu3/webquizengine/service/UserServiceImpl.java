package com.fangyu3.webquizengine.service;

import com.fangyu3.webquizengine.domain.User;
import com.fangyu3.webquizengine.mappers.UserMapper;
import com.fangyu3.webquizengine.repositories.UserRepository;
import com.fangyu3.webquizengine.web.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto findUserByEmail(String userEmail) {
        return userMapper.userToUserDto(userRepository.findByEmail(userEmail).orElse(null));
    }

    @Override
    public UserDto saveUser(UserDto userDto) {

        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        return userMapper.userToUserDto(userRepository.save(userMapper.userDtoToUser(userDto)));
    }


}
