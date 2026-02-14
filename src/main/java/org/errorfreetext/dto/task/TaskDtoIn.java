package org.errorfreetext.dto.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record TaskDtoIn(

        @NotBlank(message = "Text cannot be blank")
        @Size(min = 3, message = "Text must contain at least 3 characters")
        String text,

        @NotBlank(message = "Language cannot be blank")
        @Pattern(regexp = "^(RU|EN)$", message = "Language must be either RU or EN")
        String language
) {}