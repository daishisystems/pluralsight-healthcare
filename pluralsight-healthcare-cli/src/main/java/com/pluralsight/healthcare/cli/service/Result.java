package com.pluralsight.healthcare.cli.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
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
