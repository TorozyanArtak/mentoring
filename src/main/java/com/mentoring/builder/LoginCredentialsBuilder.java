package com.mentoring.builder;

import com.mentoring.dto.LoginCredentials;
import com.mentoring.utils.PropertiesReader;

public class LoginCredentialsBuilder {
    private String username;
    private String password;

    public LoginCredentialsBuilder() {
    }

    public LoginCredentialsBuilder withValidCredentials() {
        PropertiesReader props = PropertiesReader.getInstance();
        this.username = props.getProperty("valid.username");
        this.password = props.getProperty("valid.password");
        return this;
    }

    public LoginCredentialsBuilder withInvalidCredentials() {
        PropertiesReader props = PropertiesReader.getInstance();
        this.username = props.getProperty("invalid.username");
        this.password = props.getProperty("invalid.password");
        return this;
    }

    public LoginCredentials build() {
        if (username == null || password == null) {
            throw new IllegalStateException("Username and password must be set");
        }
        return new LoginCredentials(username, password);
    }
}

