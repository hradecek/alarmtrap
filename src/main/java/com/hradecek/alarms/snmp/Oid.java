package com.hradecek.alarms.snmp;

/**
 * Represents SNMP OID.
 */
public class Oid {

    private final String oid;

    /**
     * Constructor.
     *
     * @param oid string OID
     */
    public Oid(String oid) {
        this.oid = oid;
    }

    /**
     * Get OID as string.
     *
     * @return string OID value
     */
    public String oid() {
        return this.oid;
    }
}
