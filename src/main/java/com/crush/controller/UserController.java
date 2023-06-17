package com.crush.controller;

import com.crush.dtos.UserDto;
import com.crush.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/{name}", produces = "application/json")
    public ResponseEntity<UserDto> getUser(@PathVariable String name) {

            UserDto userDto = userService.getUserByUsername(name);

            return ResponseEntity.ok(userDto);

    }


}
