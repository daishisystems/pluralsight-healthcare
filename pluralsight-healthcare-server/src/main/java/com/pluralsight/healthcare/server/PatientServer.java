package com.pluralsight.healthcare.server;

import com.pluralsight.healthcare.repository.PatientRepository;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

public class PatientServer {
    private static final Logger LOG = LoggerFactory.getLogger(PatientServer.class);
    private static final String BASE_URI = "http://localhost:8080";

    public static void main(String... args) {
        LOG.info("Starting HTTP server");
        PatientRepository patientRepository = PatientRepository.openPatientRepository("./patients.db");
        ResourceConfig resourceConfig = new ResourceConfig().register(new PatientResource(patientRepository));

        GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), resourceConfig);
    }
}
