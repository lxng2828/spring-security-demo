package com.vtlong.my_spring_boot_project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vtlong.my_spring_boot_project.dto.request.CreateUserRequestDto;
import com.vtlong.my_spring_boot_project.dto.request.UpdateUserRequestDto;
import com.vtlong.my_spring_boot_project.dto.response.UserResponseDto;
import com.vtlong.my_spring_boot_project.mapper.UserMapper;
import com.vtlong.my_spring_boot_project.model.User;
import com.vtlong.my_spring_boot_project.repository.UserRepository;

@Service
public class AdminUserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserMapper userMapper;
    
    public List<UserResponseDto> findAll() {
        List<User> users = userRepository.findAll();
        return userMapper.toResponseDtoList(users);
    }
    
    public Optional<UserResponseDto> findById(String id) {
        Optional<User> userOptional = userRepository.findById(id);
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserResponseDto userResponseDto = userMapper.toResponseDto(user);
            return Optional.of(userResponseDto);
        } else {
            return Optional.empty();
        }
    }
    
    public UserResponseDto create(CreateUserRequestDto createUserRequestDto) {
        User user = userMapper.toEntity(createUserRequestDto);
        User savedUser = userRepository.save(user);
        return userMapper.toResponseDto(savedUser);
    }
    
    public UserResponseDto update(String id, UpdateUserRequestDto updateUserRequestDto) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            userMapper.updateEntityFromRequestDto(updateUserRequestDto, user);
            User updatedUser = userRepository.save(user);
            return userMapper.toResponseDto(updatedUser);
        }
        return null;
    }
    
    public boolean delete(String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
}
