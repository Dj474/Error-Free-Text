package org.errorfreetext.security.service;


import org.errorfreetext.dto.security.AuthenticationDtoIn;
import org.errorfreetext.dto.security.JwtDtoOut;
import org.errorfreetext.dto.security.JwtRefreshDtoIn;
import org.errorfreetext.dto.security.JwtRefreshDtoOut;
import org.errorfreetext.entity.user.User;

public interface AuthService {

    JwtDtoOut register(AuthenticationDtoIn userDtoIn);

    JwtDtoOut login(AuthenticationDtoIn userDtoIn);

    JwtRefreshDtoOut refreshToken(JwtRefreshDtoIn refreshDtoIn);

    User getCurrentUser();
}
