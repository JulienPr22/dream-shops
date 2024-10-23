package com.julienprr.dreamshops.service.user;

import com.julienprr.dreamshops.dto.UserDto;
import com.julienprr.dreamshops.exceptions.AlreadyExistsException;
import com.julienprr.dreamshops.exceptions.ResourceNotFoundException;
import com.julienprr.dreamshops.model.User;
import com.julienprr.dreamshops.repository.UserRepository;
import com.julienprr.dreamshops.request.UserCreateRequest;
import com.julienprr.dreamshops.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public User createUser(UserCreateRequest request) {
        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setFirstname(req.getFirstname());
                    user.setLastname(req.getLastname());
                    user.setEmail(req.getEmail());
                    user.setPassword(req.getPassword());
                    return userRepository.save(user);
                }).orElseThrow(() -> new AlreadyExistsException("User with email " + request.getEmail() + " already exists"));
    }

    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {
        return userRepository.findById(userId).map(existingUser -> {
            existingUser.setFirstname(request.getFirstname());
            existingUser.setLastname(request.getLastname());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete, () -> {
            throw new ResourceNotFoundException("User not found");
        });
    }

    @Override
    public UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
