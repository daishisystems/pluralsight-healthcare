package com.pluralsight.healthcare.domain;

import java.util.Optional;

public record Patient(String id, String firstName, String surname, String gender, String phone, String nat,
                      String email, Optional<String> notes) {

    public Patient {
        filled(id);
        filled(firstName);
        filled(surname);
        filled(gender);
        filled(phone);
        filled(nat);
        filled(email);
        notes.ifPresent(Patient::filled);
    }

    private static void filled(String s) {
        if (s == null || s.isBlank()) {
            throw new IllegalArgumentException("No value present!");
        }
    }
}
