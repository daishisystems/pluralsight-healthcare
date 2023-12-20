package com.pluralsight.healthcare.cli.service;

public record Result(
        String gender,
        Name name,
        Location location,
        String email,
        Login login,
        Dob dob,
        Registered registered,
        String phone,
        String cell,
        Id id,
        Picture picture,
        String nat
) {
}
