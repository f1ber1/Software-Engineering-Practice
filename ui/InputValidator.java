package com.example.pos.ui;

import java.math.BigDecimal;

public final class InputValidator {
    private InputValidator() {}

    public static int requirePositiveInt(String raw, String fieldName) {
        try {
            int v = Integer.parseInt(raw.trim());
            if (v <= 0) throw new IllegalArgumentException(fieldName + " must be > 0");
            return v;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid " + fieldName + ": " + raw);
        }
    }

    public static BigDecimal requireNonNegativeDecimal(String raw, String fieldName) {
        try {
            BigDecimal v = new BigDecimal(raw.trim());
            if (v.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException(fieldName + " must be >= 0");
            return v;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid " + fieldName + ": " + raw);
        }
    }

    public static String requireNonBlank(String raw, String fieldName) {
        if (raw == null || raw.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return raw.trim();
    }
}
