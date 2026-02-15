package org.errorfreetext.dto.yandex;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SpellerRequest {
    private String text;
    private String lang;
    private int options;
}