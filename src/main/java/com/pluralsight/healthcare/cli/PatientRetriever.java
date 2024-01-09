package com.pluralsight.healthcare.cli;

import com.pluralsight.healthcare.cli.service.PatientRetrievalService;
import com.pluralsight.healthcare.cli.service.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

public class PatientRetriever {
    private static final Logger LOG = LoggerFactory.getLogger(PatientRetriever.class);

    public static void main(String... args) {
        LOG.info("PatientRetriever starting");
        if (args.length == 0) {
            LOG.warn("Please provide a facility name as first argument.");
            return;
        }

        try {
            retrievePatients(args[0]);
        } catch (Exception e) {
            LOG.error("Unexpected error", e);
        }
    }

    private static void retrievePatients(String facilityId) {
        LOG.info("Retrieving patients for facility '{}'", facilityId);
        PatientRetrievalService patientRetrievalService = new PatientRetrievalService();

        List<Result> patientsToStore = patientRetrievalService.getPatientsFor(facilityId)
                .results()
                .stream()
                .filter(result -> Objects.equals(result.gender(), "female"))
                .toList();
        LOG.info("Retrieved the following {} patients {}", patientsToStore.size(), patientsToStore);
    }
}
