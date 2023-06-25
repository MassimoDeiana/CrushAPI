package com.crush.controller;

import com.crush.dtos.GetUserByPhoneNumberResponse;
import com.crush.dtos.UserDto;
import com.crush.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(value = "/phone/{phoneNumber}", produces = "application/json")
    public ResponseEntity<GetUserByPhoneNumberResponse> getUserByPhoneNumber(@PathVariable String phoneNumber) {

            GetUserByPhoneNumberResponse response = userService.getUserByPhoneNumber(phoneNumber);

            return ResponseEntity.ok(response);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<UserDto>> getUsers() {

            List<UserDto> users = userService.getUsers();

            return ResponseEntity.ok(users);
    }


}
