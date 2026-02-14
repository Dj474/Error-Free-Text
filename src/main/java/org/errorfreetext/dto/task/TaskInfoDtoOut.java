package org.errorfreetext.dto.task;


import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskInfoDtoOut {

    private UUID id;

    private String status;

    private String correctedText;

    private String errorMessage;
}