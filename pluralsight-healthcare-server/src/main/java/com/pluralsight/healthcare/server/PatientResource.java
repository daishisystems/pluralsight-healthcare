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
        // Define the maximum and minimum acceptable lengths for notes
        int minLength = 10; // Example minimum length
        int maxLength = 500; // Example maximum length

        // Check if the notes are within the defined boundaries
        if (notes.length() < minLength || notes.length() > maxLength) {
            // Return a 400 Bad Request response if validation fails
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("The format of the notes payload is invalid. It must be between "
                            + minLength + " and " + maxLength + " characters.")
                    .build();
        }

        // Proceed with adding notes if validation passes
        patientRepository.addNotes(id, notes);

        // Return a 200 OK response
        return Response.ok().build();
    }
}
