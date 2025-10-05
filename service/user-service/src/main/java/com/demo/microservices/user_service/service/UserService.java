package com.demo.microservices.user_service.service;

import com.demo.microservices.user_service.dto.UserDto;
import com.demo.microservices.user_service.entity.User;
import com.demo.microservices.user_service.mapper.UserMapper;
import com.demo.microservices.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    
    public UserDto createUser(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Email already exists: " + userDto.getEmail());
        }
        
        User user = userMapper.toEntity(userDto);
        if (user.getStatus() == null) {
            user.setStatus(User.UserStatus.ACTIVE);
        }
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }
    
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        return userMapper.toDtoList(userRepository.findAll());
    }
    
    @Transactional(readOnly = true)
    public Optional<UserDto> getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto);
    }
    
    @Transactional(readOnly = true)
    public Optional<UserDto> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toDto);
    }
    
    public UserDto updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        userMapper.updateEntityFromDto(userDto, user);
        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }
    
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}



