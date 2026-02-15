package org.errorfreetext.dto.yandex;

import lombok.Data;

import java.util.List;

@Data
public class SpellerError {

    private int code;

    private int pos;

    private int row;

    private int col;

    private int len;

    private String word;

    private List<String> s;
}
