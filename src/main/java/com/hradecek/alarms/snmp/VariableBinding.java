package com.hradecek.alarms.snmp;

/**
 * Represents SNMP string variable binding.
 */
public class VariableBinding {

    private final Oid oid;
    private final String value;

    public VariableBinding(final Oid oid, final String value) {
        this.oid = oid;
        this.value = value;
    }

    /**
     * Get variable binding's OID.
     *
     * @return variable binding's OID
     */
    public Oid getOid() {
        return oid;
    }

    /**
     * Get variable binding's value.
     *
     * @return variable binding's value
     */
    public String getValue() {
        return value;
    }
}
