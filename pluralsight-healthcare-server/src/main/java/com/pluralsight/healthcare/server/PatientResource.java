package com.pluralsight.healthcare.server;

import com.pluralsight.healthcare.domain.Patient;
import com.pluralsight.healthcare.repository.PatientRepository;
import com.pluralsight.healthcare.repository.RepositoryException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.text.StringEscapeUtils;
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
        // Sanitize the notes to escape special characters
        String sanitizedNotes = StringEscapeUtils.escapeJson(notes);

        // Assuming validation passes, proceed with adding sanitized notes
        patientRepository.addNotes(id, sanitizedNotes);

        // Return a 200 OK response
        return Response.ok().build();
    }
}
