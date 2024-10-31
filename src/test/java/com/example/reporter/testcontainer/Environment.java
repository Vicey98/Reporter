package com.example.reporter.testcontainer;

import lombok.Data;

@Data
public abstract class Environment {
    public static final String ID = "com.example.reporter.testcontainer.Environment";
    private String dbUrl;
    private String dbUsername;
    private String dbPassword;
}
