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
        // Check for newline characters in notes
        if (notes.contains("\\n") || notes.contains("\\r")) {
            // Return a 400 Bad Request response if newline characters are found
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Notes cannot contain newline characters.")
                    .build();
        }

        // Proceed with adding notes if there are no newline characters
        patientRepository.addNotes(id, notes);

        // Return a 200 OK response indicating success
        return Response.ok().build();
    }
}
