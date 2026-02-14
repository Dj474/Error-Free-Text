package org.errorfreetext.controller.security;

import jakarta.validation.Valid;
import org.errorfreetext.dto.security.AuthenticationDtoIn;
import org.errorfreetext.dto.security.JwtDtoOut;
import org.errorfreetext.dto.security.JwtRefreshDtoIn;
import org.errorfreetext.dto.security.JwtRefreshDtoOut;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("api/v1/auth")
public interface AuthController {

    @PostMapping("/register")
    JwtDtoOut register(@Valid @RequestBody AuthenticationDtoIn userDtoIn);

    @PostMapping("/signin")
    JwtDtoOut login(@Valid @RequestBody AuthenticationDtoIn userDtoIn);

    @PostMapping("/refresh")
    JwtRefreshDtoOut refreshToken(@Valid @RequestBody JwtRefreshDtoIn refreshDtoIn);

}
