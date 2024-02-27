package com.pluralsight.healthcare.server;

import com.pluralsight.healthcare.domain.Patient;
import com.pluralsight.healthcare.repository.PatientRepository;
import com.pluralsight.healthcare.repository.RepositoryException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.stream.Stream;

@Path("/patients")
public class PatientResource {
    private static final Logger LOG = LoggerFactory.getLogger(PatientResource.class);

    private final PatientRepository patientRepository;

    public PatientResource(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Stream<Patient> getPatients() {
        try {
            return patientRepository
                    .getAllPatients()
                    .stream()
                    .sorted(Comparator.comparing(Patient::id));
        } catch (RepositoryException e) {
            LOG.error("Could not retrieve patients from the database.", e);
            throw new NotFoundException();
        }
    }

    @POST
    @Path("/{id}/notes")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response addNotes(@PathParam("id") String id, String notes) {
        // Define a maximum length for the notes
        final int MAX_LENGTH = 18; // Example limit

        // Check if the notes exceed the maximum length
        if (notes.length() > MAX_LENGTH) {
            // Return a 400 Bad Request response if the notes are too long
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Notes exceed the maximum length of " + MAX_LENGTH + " characters.")
                    .build();
        }

        // Proceed with adding notes if validation passes
        patientRepository.addNotes(id, notes);

        // Return a 200 OK response indicating success
        return Response.ok().build();
    }
}