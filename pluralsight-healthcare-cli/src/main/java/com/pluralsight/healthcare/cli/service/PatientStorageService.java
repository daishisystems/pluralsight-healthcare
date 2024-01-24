package com.pluralsight.healthcare.cli.service;

import com.pluralsight.healthcare.domain.Patient;
import com.pluralsight.healthcare.repository.PatientRepository;

import java.util.List;

public class PatientStorageService {
    private final PatientRepository patientRepository;

    public PatientStorageService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public void storePatients(List<Result> results) {
        for (Result result : results) {
            Patient patient = new Patient(result.id().value(), result.name().first(), result.name().last(), result.gender(), result.phone(), result.nat(), result.email());
            patientRepository.savePatient(patient);
        }
    }
}
