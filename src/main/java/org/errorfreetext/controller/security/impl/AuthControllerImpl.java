package org.errorfreetext.controller.security.impl;

import lombok.AllArgsConstructor;
import org.errorfreetext.controller.security.AuthController;
import org.errorfreetext.dto.security.AuthenticationDtoIn;
import org.errorfreetext.dto.security.JwtDtoOut;
import org.errorfreetext.dto.security.JwtRefreshDtoIn;
import org.errorfreetext.dto.security.JwtRefreshDtoOut;
import org.errorfreetext.security.service.AuthService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    public JwtDtoOut register(AuthenticationDtoIn userDtoIn) {
        return authService.register(userDtoIn);
    }

    @Override
    public JwtDtoOut login(AuthenticationDtoIn userDtoIn) {
        return authService.login(userDtoIn);
    }

    @Override
    public JwtRefreshDtoOut refreshToken(JwtRefreshDtoIn refreshDtoIn) {
        return authService.refreshToken(refreshDtoIn);
    }

}
