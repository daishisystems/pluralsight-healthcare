package com.pluralsight.healthcare.server;

import com.pluralsight.healthcare.domain.Patient;
import com.pluralsight.healthcare.repository.PatientRepository;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;

@Path("/patients")
public class PatientResource {
    private static final Logger LOG = LoggerFactory.getLogger(PatientResource.class);

    private final PatientRepository patientRepository;

    public PatientResource(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Patient> getPatients() {
        return patientRepository
                .getAllPatients()
                .stream()
                .sorted(Comparator.comparing(Patient::id))
                .toList();
    }
}
