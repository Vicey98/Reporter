package com.example.reporter.config;

import lombok.Data;

@Data
public abstract class Environment {
    public static final String ID = "com.example.reporter.config.Environment";
    private String dbUrl;
    private String dbUsername;
    private String dbPassword;
}
