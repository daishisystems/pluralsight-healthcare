package com.pluralsight.healthcare.domain;

public record Patient(String id, String firstName, String surname, String gender, String phone, String nat, String email) {

    public Patient {
        filled(id);
        filled(firstName);
        filled(surname);
        filled(gender);
        filled(phone);
        filled (nat);
        filled(email);
    }

    private static void filled(String s) {
        if (s == null || s.isBlank()) {
            throw new IllegalArgumentException("No value present!");
        }
    }
}
