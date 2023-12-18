package com.pluralsight.healthcare.cli;

public class PatientRetriever {

    public static void main(String... args) {
        System.out.println("PatientRetriever started!");
        if (args.length == 0) {
            System.out.println("Please provide a facility name as first argument.");
            return;
        }

        try {
            retrievePatients(args[0]);
        } catch (Exception e) {
            System.out.println("Unexpected error");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void retrievePatients(String facilityId) {
        System.out.println("Retrieving patients for facility " + facilityId);
    }
}
