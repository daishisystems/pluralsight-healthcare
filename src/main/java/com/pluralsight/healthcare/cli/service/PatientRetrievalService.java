package com.pluralsight.healthcare.cli.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PatientRetrievalService {
    private static final String PATIENTS_URI = "https://randomuser.me/api/?results=6";
    private static final HttpClient HTTP_CLIENT = HttpClient
            .newBuilder()
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .build();

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static ExternalPatientsManifest getExternalPatients(HttpResponse<String> httpResponse) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(httpResponse.body(), ExternalPatientsManifest.class);
    }

    public ExternalPatientsManifest getPatientsFor(String facilityId) {
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(PATIENTS_URI))
                .GET()
                .build();
        try {
            HttpResponse<String> httpResponse = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() == 200) {
                return getExternalPatients(httpResponse);
            }
            throw new RuntimeException("Patients API call failed with status code " + httpResponse.statusCode());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Could not call patients API", e);
        }
    }
}
