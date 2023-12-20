package com.pluralsight.healthcare.cli;

import com.pluralsight.healthcare.cli.service.ExternalPatientsManifest;
import com.pluralsight.healthcare.cli.service.PatientRetrievalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        ExternalPatientsManifest patientsToStore = patientRetrievalService.getPatientsFor(facilityId);
        LOG.info("Retrieved the following {} patients {}", patientsToStore.results().size(), patientsToStore);
    }
}
