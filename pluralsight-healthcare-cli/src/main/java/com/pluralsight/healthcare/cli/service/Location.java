package com.pluralsight.healthcare.cli.service;

public record Location(
        Street street,
        String city,
        String state,
        String country,
        String postcode,
        Coordinates coordinates,
        Timezone timezone
) {
}
