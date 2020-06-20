package com.hradecek.alarms.snmp;

import com.mifmif.common.regex.Generex;

import java.util.Optional;

/**
 * Represents alarm trap to be sent via SNMP.
 * <p>
 * Alarm MUST always have OID<br>,
 * Alarm MAY have associated component.
 */
public class AlarmTrap {

    private final Oid oid;

    private String componentRegex;
    private String componentValue;

    /**
     * Construtor.
     *
     * @param oid alarm SNMP trap OID
     */
    public AlarmTrap(Oid oid) {
        this.oid = oid;
    }

    /**
     * Constructor.
     *
     * @param oid alarm SNMP trap OID
     * @param componentRegex component regex
     */
    public AlarmTrap(Oid oid, String componentRegex) {
        this.oid = oid;
        this.componentRegex = componentRegex;
    }

    /**
     * Get alarm SNMP trap OID.
     *
     * @return alarm SNMP trap OID
     */
    public Oid getOid() {
        return oid;
    }

    /**
     * Sets component value.
     *
     * @param componentValue component value
     */
    public void setComponentValue(String componentValue) {
        this.componentValue = componentValue;
    }

    /**
     * Get random component OID based on specified regex and its specified component value.
     * <p>
     * Returns {@link Optional#empty()} if neither component regex nor component value were not specified.
     *
     * @return component binding
     */
    public Optional<ComponentBinding> getComponentBinding() {
        return componentRegex != null && componentValue != null
                ? Optional.of(new ComponentBinding(new Oid(new Generex(componentRegex).random()), componentValue))
                : Optional.empty();
    }
}
