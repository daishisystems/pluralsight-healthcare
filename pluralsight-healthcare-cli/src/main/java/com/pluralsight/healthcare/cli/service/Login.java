package com.pluralsight.healthcare.cli.service;

public record Login(
        String uuid,
        String username,
        String password,
        String salt,
        String md5,
        String sha1,
        String sha256
) {
}
