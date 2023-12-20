package com.pluralsight.healthcare.cli.service;

import java.util.List;

public record ExternalPatientsManifest(List<Result> results, Info info) {
}

