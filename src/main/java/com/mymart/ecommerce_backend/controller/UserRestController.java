package com.mymart.ecommerce_backend.controller;

import com.mymart.ecommerce_backend.dto.ApiResponse;
import com.mymart.ecommerce_backend.dto.ResetPwdDto;
import com.mymart.ecommerce_backend.dto.UserDto;
import com.mymart.ecommerce_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
    @RequestMapping("/api/user")
    public class UserRestController {

        @Autowired
        private UserService userService;

        @PostMapping("/register")
        public ResponseEntity<ApiResponse<UserDto>> createUser(@RequestBody UserDto userDto) {
            UserDto savedUser = userService.saveUser(userDto);
            ApiResponse<UserDto> response = new ApiResponse<>();
            response.setStatusCode(HttpStatus.CREATED.value());
            response.setMessage("User created successfully");
            response.setData(savedUser);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        @PostMapping("/login")
        public ResponseEntity<ApiResponse<UserDto>> loginUser(@RequestBody UserDto userDto) {
            ApiResponse<UserDto> response = new ApiResponse<>();
            UserDto user = userService.login(userDto.getEmail(), userDto.getPwd());
            if (user != null) {
                response.setStatusCode(HttpStatus.OK.value());
                response.setMessage("Valid login");
                response.setData(user);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setStatusCode(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Check Your Email or Pwd and Try Again");
                response.setData(null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        }

        @PostMapping("/reset-password")
        public ResponseEntity<ApiResponse<UserDto>> resetPassword(@RequestBody ResetPwdDto resetPwdDto) {
            UserDto userDto = userService.resetPwd(resetPwdDto);
            ApiResponse<UserDto> response = new ApiResponse<>();

            if (userDto == null) {
                response.setStatusCode(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Check Your Email or Pwd and Try Again");
                response.setData(null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Password reset successfully");
            response.setData(userDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @GetMapping("/byEmail/{email}")
        public ResponseEntity<ApiResponse<UserDto>> getUserByEmail(@PathVariable String email) {
            UserDto userDto = userService.getUserByEmail(email);
            ApiResponse<UserDto> response = new ApiResponse<>();

            if (userDto == null) {
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage("User not found with email: " + email);
                response.setData(null);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("User retrieved successfully");
            response.setData(userDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
