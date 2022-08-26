package com.example.microblinkdemo.user;

import com.example.microblinkdemo.exception.NotFoundException;
import com.example.microblinkdemo.user.domain.User;
import com.example.microblinkdemo.user.domain.UserDomain;
import com.example.microblinkdemo.user.domain.UserRequestCreate;
import com.example.microblinkdemo.user.domain.UserRequestUpdate;
import com.example.microblinkdemo.util.ResponseConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException(ResponseConstants.ERROR_USER_NOT_FOUND);
        });
    }

    public UserDomain findMostOverdue() {
        UserDomain userDomain = null;
        final User mostOverdueUser = userRepository.findMostOverdue();
        if (mostOverdueUser != null)  {
            userDomain = mapUserDomain(mostOverdueUser);
        }
        return userDomain;
    }

    public User save(UserRequestCreate request) {
        return userRepository.save(mapCreateRequestToEntity(request));
    }

    public void update(UserRequestUpdate request) {
        throwExceptionIfNotExists(request.getId());
        userRepository.save(mapUpdateRequestToEntity(request));
    }

    public void throwExceptionIfNotExists(Integer userId) {
        final boolean userExists = userRepository.existsById(userId);
        if (!userExists)
            throw new NotFoundException(ResponseConstants.ERROR_USER_NOT_FOUND);
    }

    private User mapCreateRequestToEntity(UserRequestCreate request) {
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .dateOfBirth(request.getDateOfBirth())
                .serialNumber(UUID.randomUUID().toString())
                .build();
    }

    private User mapUpdateRequestToEntity(UserRequestUpdate request) {
        return User.builder()
                .id(request.getId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .dateOfBirth(request.getDateOfBirth())
                .build();
    }

    private UserDomain mapUserDomain(User user) {
        return UserDomain.builder()
                .id(user.getId())
                .serialNumber(user.getSerialNumber())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .build();
    }
}
