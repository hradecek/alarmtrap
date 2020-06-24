package com.hradecek.alarms.mappings;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Represents alarm's severity used in alarm mappings.
 */
public enum Severity {

    CLEARED("Cleared"),
    INDETERMINATE("Indeterminate"),
    WARNING("Warning"),
    MINOR("Minor"),
    MAJOR("Major"),
    CRITICAL("Critical");

    private final String representation;

    Severity(String representation) {
        this.representation = representation;
    }

    @JsonValue
    public String getRepresentation() {
        return representation;
    }
}
