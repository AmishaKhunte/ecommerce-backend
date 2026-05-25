package com.mymart.ecommerce_backend.service.Imp;

import com.mymart.ecommerce_backend.constants.AppConstants;
import com.mymart.ecommerce_backend.dto.ResetPwdDto;
import com.mymart.ecommerce_backend.dto.UserDto;
import com.mymart.ecommerce_backend.entities.RoleEntity;
import com.mymart.ecommerce_backend.entities.UserEntity;
import com.mymart.ecommerce_backend.mapper.UserMapper;
import com.mymart.ecommerce_backend.repos.RoleRepo;
import com.mymart.ecommerce_backend.repos.UserRepo;
import com.mymart.ecommerce_backend.security.JwtUtil;
import com.mymart.ecommerce_backend.service.EmailService;
import com.mymart.ecommerce_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtUtil jwtUnit;

    @Override
    public UserDto saveUser(UserDto userDto) {

        String randomPwd = userDto.getPwd();

        if (randomPwd == null || randomPwd.isBlank()) {
            randomPwd = generateRandomPwd(5);
        }

        userDto.setPwd(randomPwd);

        userDto.setPwdUpdated(AppConstants.NO);

        UserEntity userEntity = UserMapper.dtoToEntity(userDto);

        Set<RoleEntity> roleEntitySet = new HashSet<>();

        if(userDto.getRoleName() != null){

            RoleEntity roleEntity =
                    roleRepo.findByNameIgnoreCase(
                            normalizeRoleName(userDto.getRoleName())
                    );

            if(roleEntity != null){

                roleEntitySet.add(roleEntity);

            } else {

                throw new RuntimeException(
                        "Role Not Found"
                );
            }
        }

        userEntity.setRoles(roleEntitySet);

        UserEntity savedUser =
                userRepo.save(userEntity);

        return UserMapper.entityToDto(savedUser);
    }

    @Override
    public UserDto login(String email, String pwd){
        UserEntity userEntity = userRepo.findByEmailAndPwd(email,pwd);

        if(userEntity != null){
            String token = jwtUnit.generateToken(email);
            UserDto userDto = UserMapper.entityToDto(userEntity);

            userDto.setToken(token);

            RoleEntity role = userEntity.getRoles().iterator().next();

            userDto.setRoleName(role.getName());
            return userDto;
        }

        return null;
    }

    @Override
    public UserDto resetPwd(
            ResetPwdDto resetPwdDto
    ) {

        UserEntity userEntity =
                userRepo.findByEmail(
                        resetPwdDto.getEmail()
                );

        if(userEntity != null){

            userEntity.setPwd(resetPwdDto.getNewPwd());

            userEntity.setPwdUpdated(AppConstants.YES);
            UserEntity updatedUser = userRepo.save(userEntity);
            return UserMapper.entityToDto(updatedUser);
        }

        return null;
    }

    @Override
    public UserDto getUserByEmail(
            String email
    ) {

        UserEntity userEntity = userRepo.findByEmail(email);

        if(userEntity != null){

            return UserMapper.entityToDto(userEntity);
        }

        return null;
    }

    private String generateRandomPwd(int length) {

        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";

        Random random = new Random();

        StringBuilder sb = new StringBuilder();

        for(int i=0; i<length; i++){

            int index = random.nextInt(chars.length());

            sb.append(chars.charAt(index));
        }

        return sb.toString();
    }

    private String normalizeRoleName(String roleName) {
        String normalizedRole = roleName.trim();

        if (normalizedRole.toUpperCase().startsWith("ROLE_")) {
            normalizedRole = normalizedRole.substring(5);
        }

        return normalizedRole;
    }
        }





