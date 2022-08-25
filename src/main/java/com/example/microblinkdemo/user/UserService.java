package com.example.microblinkdemo.user;

import com.example.microblinkdemo.exception.NotFoundException;
import com.example.microblinkdemo.user.domain.User;
import com.example.microblinkdemo.user.domain.AbstractUserRequestCreate;
import com.example.microblinkdemo.user.domain.AbstractUserRequestUpdate;
import com.example.microblinkdemo.util.ResponseConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException(ResponseConstants.ERROR_USER_NOT_FOUND);
        });
    }

    public User save(AbstractUserRequestCreate request) {
        return userRepository.save(mapCreateRequestToEntity(request));
    }

    public void update(AbstractUserRequestUpdate request) {
        final boolean doesUserExists = userRepository.existsById(request.getId());
        if (!doesUserExists)
            throw new NotFoundException(ResponseConstants.ERROR_USER_NOT_FOUND);

        userRepository.save(mapUpdateRequestToEntity(request));
    }


    private User mapCreateRequestToEntity(AbstractUserRequestCreate request) {
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .dateOfBirth(request.getDateOfBirth())
                .build();
    }

    private User mapUpdateRequestToEntity(AbstractUserRequestUpdate request) {
        return User.builder()
                .id(request.getId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .dateOfBirth(request.getDateOfBirth())
                .build();
    }
}
