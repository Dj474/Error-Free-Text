package org.errorfreetext.security.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.errorfreetext.dto.security.AuthenticationDtoIn;
import org.errorfreetext.dto.security.JwtDtoOut;
import org.errorfreetext.dto.security.JwtRefreshDtoIn;
import org.errorfreetext.dto.security.JwtRefreshDtoOut;
import org.errorfreetext.entity.user.User;
import org.errorfreetext.exception.BadRequestException;
import org.errorfreetext.exception.ForbiddenException;
import org.errorfreetext.exception.NotFoundException;
import org.errorfreetext.exception.UnauthorizedException;
import org.errorfreetext.repository.user.UserRepository;
import org.errorfreetext.security.jwt.JwtTokenProvider;
import org.errorfreetext.security.service.AuthService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    private JwtDtoOut getJwtForUser(User user) {
        JwtDtoOut jwtDtoOut = new JwtDtoOut();
        jwtDtoOut.setAccessToken(jwtTokenProvider.generateAccessTokenFromId(user.getId()));
        jwtDtoOut.setRefreshToken(jwtTokenProvider.generateRefreshTokenFromId(user.getId()));
        jwtDtoOut.setId(user.getId());
        return jwtDtoOut;
    }

    @Override
    @Transactional
    public JwtDtoOut register(AuthenticationDtoIn userDtoIn) {
        if (userRepository.existsByLogin(userDtoIn.getLogin())) {
            throw new BadRequestException("Login exists");
        }

        User user = new User();
        user.setLogin(userDtoIn.getLogin());
        user.setPassword(passwordEncoder.encode(userDtoIn.getPassword()));
        userRepository.save(user);

        return getJwtForUser(user);
    }

    @Override
    @Transactional
    public JwtDtoOut login(AuthenticationDtoIn userDtoIn) {

        User user = userRepository.findByLogin(userDtoIn.getLogin()).orElse(null);
        if (Objects.isNull(user)) {
            throw new NotFoundException("Login not exists");
        }

        if (!passwordEncoder.matches(userDtoIn.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Wrong password");
        }

        return getJwtForUser(user);
    }

    @Override
    public JwtRefreshDtoOut refreshToken(JwtRefreshDtoIn refreshDtoIn) {

        if (!jwtTokenProvider.validateRefreshToken(refreshDtoIn.getRefreshToken())) {
            throw new ForbiddenException("Refresh token expired");
        }

        Long id = jwtTokenProvider.getUserIdFromRefreshToken(refreshDtoIn.getRefreshToken());

        User user = userRepository.findById(id).orElse(null);
        if (Objects.isNull(user)) {
            throw new NotFoundException("User not exists");
        }

        JwtRefreshDtoOut jwtRefreshDtoOut = new JwtRefreshDtoOut();
        jwtRefreshDtoOut.setRefreshToken(refreshDtoIn.getRefreshToken());
        jwtRefreshDtoOut.setAccessToken(jwtTokenProvider.generateAccessTokenFromId(user.getId()));
        return jwtRefreshDtoOut;
    }

    @Override
    @Transactional
    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Long) {
            return userRepository.getReferenceById((Long) principal);
        }
        throw new ForbiddenException("Invalid credentials");
    }
}
