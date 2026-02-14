package org.errorfreetext.dto.security;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class JwtDtoOut {
    private String accessToken;

    private String refreshToken;

    private Long id;
}
