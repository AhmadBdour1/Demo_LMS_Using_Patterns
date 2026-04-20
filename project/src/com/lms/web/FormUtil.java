package com.lms.web;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility for parsing x-www-form-urlencoded request bodies.
 */
public final class FormUtil {
    private FormUtil() {
    }

    /**
     * Parses HTTP body bytes into key/value pairs.
     */
    public static Map<String, String> parseBody(InputStream bodyStream) throws IOException {
        String body = new String(bodyStream.readAllBytes(), StandardCharsets.UTF_8);
        Map<String, String> values = new HashMap<>();
        // Empty request body -> no form fields.
        if (body.isBlank()) {
            return values;
        }

        String[] pairs = body.split("&");
        for (String pair : pairs) {
            // "key=value" with a split limit keeps '=' inside field values.
            String[] keyValue = pair.split("=", 2);
            String key = decode(keyValue[0]);
            String value = keyValue.length > 1 ? decode(keyValue[1]) : "";
            values.put(key, value);
        }
        return values;
    }

    /**
     * URL-decodes form values using UTF-8.
     */
    private static String decode(String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }
}
