package org.errorfreetext.service.speller.impl;

import lombok.RequiredArgsConstructor;
import org.errorfreetext.dto.yandex.SpellerError;
import org.errorfreetext.service.speller.CorrectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CorrectionServiceImpl implements CorrectionService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String SPELLER_URL = "https://speller.yandex.net/services/spellservice.json/checkTexts";
    private static final int MAX_LENGTH = 10000;

    private static final int IGNORE_DIGITS = 2;
    private static final int IGNORE_URLS = 4;

    public String correctText(String text, String lang) {
        int options = calculateOptions(text);

        List<String> chunks = splitText(text, MAX_LENGTH);
        StringBuilder correctedResult = new StringBuilder();

        for (String chunk : chunks) {
            String correctedChunk = callSpellerApi(chunk, lang, options);
            correctedResult.append(correctedChunk);
        }

        return correctedResult.toString();
    }

    private int calculateOptions(String text) {
        int options = 0;
        if (text.matches(".*\\d.*")) {
            options |= IGNORE_DIGITS;
        }
        if (text.matches(".*https?://.*") || text.matches(".*http?://.*") || text.contains("www.")) {
            options |= IGNORE_URLS;
        }
        return options;
    }

    private List<String> splitText(String text, int size) {
        List<String> parts = new ArrayList<>();
        int start = 0;

        while (start < text.length()) {
            if (start + size >= text.length()) {
                parts.add(text.substring(start));
                break;
            }

            int end = start + size;
            int lastSpace = text.lastIndexOf(' ', end);

            if (lastSpace > start) {
                end = lastSpace;
            }

            parts.add(text.substring(start, end));

            start = end;
            while (start < text.length() && text.charAt(start) == ' ') {
                start++;
            }
        }
        return parts;
    }

    private String callSpellerApi(String chunk, String lang, int options) {
        String url = String.format("%s?text=%s&lang=%s&options=%d", SPELLER_URL, chunk, lang, options);
        ResponseEntity<SpellerError[][]> response = restTemplate.getForEntity(url, SpellerError[][].class);

        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Yandex API error: " + response.getStatusCode());
        }

        return applyCorrections(chunk, response.getBody()[0]);
    }

    private String applyCorrections(String original, SpellerError[] errors) {
        if (errors == null || errors.length == 0) return original;

        StringBuilder sb = new StringBuilder(original);
        for (int i = errors.length - 1; i >= 0; i--) {
            SpellerError error = errors[i];
            if (!error.getS().isEmpty()) {
                sb.replace(error.getPos(), error.getPos() + error.getLen(), error.getS().get(0));
            }
        }
        return sb.toString();
    }
}