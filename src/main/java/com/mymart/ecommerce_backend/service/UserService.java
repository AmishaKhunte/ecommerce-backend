package com.mymart.ecommerce_backend.service;

import com.mymart.ecommerce_backend.dto.ResetPwdDto;
import com.mymart.ecommerce_backend.dto.ShippingAddressDto;
import com.mymart.ecommerce_backend.dto.UserDto;

import java.util.List;

public interface UserService {

    public UserDto saveUser(UserDto userDto);

    public UserDto login(String email, String pwd);

    public UserDto resetPwd(ResetPwdDto resetPwdDto);

    public UserDto getUserByEmail(String email);

}
