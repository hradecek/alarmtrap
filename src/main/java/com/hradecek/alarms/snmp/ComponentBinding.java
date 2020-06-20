package com.hradecek.alarms.snmp;

/**
 * Represents component SNMP variable binding.
 * <p>
 * It MUST always consists of {@link Oid oid} and string value.
 */
public class ComponentBinding {

    private final Oid oid;
    private final String value;

    /**
     * Constructor.
     *
     * @param oid component's OID
     * @param value component's string value
     */
    public ComponentBinding(Oid oid, String value) {
        this.oid = oid;
        this.value = value;
    }

    /**
     * Get component's OID.
     *
     * @return component's OID
     */
    public Oid getOid() {
        return oid;
    }

    /**
     * Get component's string value.
     *
     * @return component's string value
     */
    public String getValue() {
        return value;
    }
}
