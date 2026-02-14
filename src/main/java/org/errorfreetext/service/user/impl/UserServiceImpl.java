package org.errorfreetext.service.user.impl;

import lombok.RequiredArgsConstructor;
import org.errorfreetext.entity.user.User;
import org.errorfreetext.security.service.AuthService;
import org.errorfreetext.service.user.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthService authService;

    @Override
    public User getCurrent() {
        return authService.getCurrentUser();
    }
}
