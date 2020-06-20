package com.hradecek.alarms.snmp;

/**
 * Represents SNMP version.
 */
public enum SnmpVersion {
    V1("1"), V2C("2c"), V3("3");

    private final String representation;

    SnmpVersion(final String representation) {
        this.representation = representation;
    }

    /**
     * Get string representation.
     *
     * @return string representation
     */
    public String getRepresentation() {
        return representation;
    }
}
