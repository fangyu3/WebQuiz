package com.fangyu3.webquizengine.web.controller;

import com.fangyu3.webquizengine.service.UserService;
import com.fangyu3.webquizengine.web.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser (@Valid @RequestBody UserDto userDto) {

        if (userService.findUserByEmail(userDto.getEmail()) != null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity(userService.saveUser(userDto),HttpStatus.CREATED);
    }
}
