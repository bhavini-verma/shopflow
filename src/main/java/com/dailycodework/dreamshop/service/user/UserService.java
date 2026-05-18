package com.dailycodework.dreamshop.service.user;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.dailycodework.dreamshop.exceptions.ResourceAlreadyExistsException;
import com.dailycodework.dreamshop.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshop.model.User;
import com.dailycodework.dreamshop.request.CreateUserRequest;
import com.dailycodework.dreamshop.request.UpdateUserRequest;
import com.dailycodework.dreamshop.repository.UserRepository;
import com.dailycodework.dreamshop.dto.UserDto;

import lombok.RequiredArgsConstructor;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements iUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(req -> !userRepository.existsByEmail(req.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setEmail(req.getEmail());
                    user.setFirstName(req.getFirstName());
                    user.setLastName(req.getLastName());
                    user.setPassword(passwordEncoder.encode(req.getPassword()));
                    return userRepository.save(user);
                })
                .orElseThrow(
                        () -> new ResourceAlreadyExistsException("Oops! " + request.getEmail() + " already exists"));
    }

    @Override
    public User updateUser(Long id, UpdateUserRequest request) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.findById(id).ifPresentOrElse(
                userRepository::delete,
                () -> {
                    throw new ResourceNotFoundException("User not found!");
                });
    }

    public UserDto convertUserToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }

}
