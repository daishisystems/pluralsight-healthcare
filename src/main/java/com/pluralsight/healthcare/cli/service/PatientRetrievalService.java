package com.pluralsight.healthcare.cli.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PatientRetrievalService {
    private static final String PATIENTS_URI = "https://randomuser.me/api/?results=6";
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

    public String getPatientsFor(String facilityId) {
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(PATIENTS_URI))
                .GET()
                .build();
        try {
            HttpResponse<String> httpResponse = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            return httpResponse.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Could not call patients API", e);
        }
    }
}
