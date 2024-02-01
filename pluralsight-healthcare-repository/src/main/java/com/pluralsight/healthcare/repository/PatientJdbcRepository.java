package com.pluralsight.healthcare.repository;

import com.pluralsight.healthcare.domain.Patient;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class PatientJdbcRepository implements PatientRepository {

    private static final String H2_DATABASE_URL =
            "jdbc:h2:file:%s;AUTO_SERVER=TRUE;INIT=RUNSCRIPT FROM 'db_init.sql'";

    private static final String INSERT_PATIENT = """
            MERGE INTO Patients (ID, FIRSTNAME, SURNAME, GENDER, PHONE, NAT, EMAIL)
            VALUES(?, ?, ?, ?, ?, ?, ?)
            """;

    private static final String ADD_NOTES = """
            UPDATE Patients SET NOTES = ?
            WHERE ID = ?
            """;

    private final DataSource dataSource;

    public PatientJdbcRepository(String databaseFile) {
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL(H2_DATABASE_URL.formatted(databaseFile));
        this.dataSource = jdbcDataSource;
    }

    @Override
    public void savePatient(Patient patient) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PATIENT);
            preparedStatement.setString(1, patient.id());
            preparedStatement.setString(2, patient.firstName());
            preparedStatement.setString(3, patient.surname());
            preparedStatement.setString(4, patient.gender());
            preparedStatement.setString(5, patient.phone());
            preparedStatement.setString(6, patient.nat());
            preparedStatement.setString(7, patient.email());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RepositoryException("Failed to save " + patient, e);
        }
    }

    @Override
    public void addNotes(String id, String notes) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_NOTES);
            preparedStatement.setString(1, notes);
            preparedStatement.setString(2, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RepositoryException("Failed to add notes to " + id, e);
        }
    }

    @Override
    public List<Patient> getAllPatients() {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM PATIENTS");

            List<Patient> patients = new ArrayList<>();
            while (resultSet.next()) {
                Patient patient = new Patient(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        Optional.ofNullable(resultSet.getString(8)));
                patients.add(patient);
            }
            return Collections.unmodifiableList(patients);
        } catch (SQLException e) {
            throw new RepositoryException("Failed to retrieve patients", e);
        }
    }
}
