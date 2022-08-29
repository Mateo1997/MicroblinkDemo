package com.example.microblinkdemo.user;

import com.example.microblinkdemo.exception.NotFoundException;
import com.example.microblinkdemo.user.domain.User;
import com.example.microblinkdemo.user.domain.UserDomain;
import com.example.microblinkdemo.user.domain.UserRequestCreate;
import com.example.microblinkdemo.user.domain.UserRequestUpdate;
import com.example.microblinkdemo.util.ResponseConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public UserDomain findById(Integer id) {
        return userRepository.findById(id)
                .map(userConverter::entityToDomain)
                .orElseThrow(() -> {
            throw new NotFoundException(ResponseConstants.ERROR_USER_NOT_FOUND);
        });
    }

    public UserDomain findMostOverdue() {
        UserDomain userDomain = null;
        final User mostOverdueUser = userRepository.findMostOverdue();
        if (mostOverdueUser != null)  {
            userDomain = userConverter.entityToDomain(mostOverdueUser);
        }
        return userDomain;
    }

    public UserDomain save(UserRequestCreate request) {
        final User userEntity = userConverter.createRequestToEntity(request);
        final User userDTO = userRepository.save(userEntity);
        return userConverter.entityToDomain(userDTO);
    }

    public void update(UserRequestUpdate request) {
        throwExceptionIfNotExists(request.getId());
        final User userEntity = userConverter.updateRequestToEntity(request);
        userRepository.save(userEntity);
    }

    public void throwExceptionIfNotExists(Integer userId) {
        final boolean userExists = userRepository.existsById(userId);
        if (!userExists)
            throw new NotFoundException(ResponseConstants.ERROR_USER_NOT_FOUND);
    }
}
