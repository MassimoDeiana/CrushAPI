package com.crush.controller;

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


//    @Operation(summary = "Get user by name", tags = {"user"}, operationId = "getUser")
//    @GetMapping(value = "/{name}", produces = "application/json")
//    public ResponseEntity<UserResponse> getUser(@PathVariable String name) {
//        UserResponse userDto = userService.getUser(name);
//        return ResponseEntity.ok(userDto);
//    }
//
//    @Operation(summary = "Create user", tags = {"user"}, operationId = "createUser")
//    @PostMapping(produces = "application/json", consumes = "application/json")
//    public ResponseEntity<UserResponse> createUser(@RequestBody UserCreation user) {
//        UserResponse savedUserDto = userService.createUser(user);
//        return ResponseEntity.ok(savedUserDto);
//    }
//
//    @Operation(summary = "Delete user", tags = {"user"}, operationId = "deleteUser")
//    @DeleteMapping(value = "/{id}", name = "deleteUser")
//    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
//        userService.deleteUser(id);
//        return ResponseEntity.ok().build();
//    }

    @GetMapping(value = "/{name}", produces = "application/json")
    public ResponseEntity<String> getUser(@PathVariable String name) {
        return ResponseEntity.ok("Hello " + name);
    }


}
