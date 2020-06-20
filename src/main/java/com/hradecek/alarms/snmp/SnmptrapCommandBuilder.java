package com.hradecek.alarms.snmp;

import java.util.LinkedList;
import java.util.List;

/**
 * Creates snmptrap command for copy & paste.
 */
public class SnmptrapCommandBuilder {

    public static final String SNMPTRAP_COMMAND = "snmptrap";

    private int port;
    private boolean isPortSpecified = false;
    private Oid trapOid;
    private String address;
    private String community = "public";
    private String sysUptime = "''";
    private SnmpVersion version = SnmpVersion.V2C;
    private List<VariableBinding> variableBindings = new LinkedList<>();

    /**
     * Sets '-v' flag, representing SNMP version.
     *
     * @param version SNMP version
     * @return SnmptrapCommandBuilder
     */
    public SnmptrapCommandBuilder version(final SnmpVersion version) {
        this.version = version;
        return this;
    }

    /**
     * Sets '-c' flag, representing community string.
     *
     * @param community SNMP community string
     * @return SnmptrapCommandBuilder
     */
    public SnmptrapCommandBuilder community(final String community) {
        this.community = community;
        return this;
    }

    /**
     * Sets trapOid parameter.
     *
     * @param trapOid SNMP trapOid value
     * @return SnmptrapCommandBuilder
     */
    public SnmptrapCommandBuilder trapOid(final Oid trapOid) {
        this.trapOid = trapOid;
        return this;
    }

    /**
     * Sets address.
     *
     * @param address target's address
     * @return SnmptrapCommandBuilder
     */
    public SnmptrapCommandBuilder address(final String address) {
        this.address = address;
        return this;
    }

    /**
     * Sets port.
     *
     * @param port target's port
     * @return SnmptrapCommandBuilder
     */
    public SnmptrapCommandBuilder port(int port) {
        this.port = port;
        this.isPortSpecified = true;
        return this;
    }

    /**
     * Add variable binding with string value.
     *
     * @param oid variable binding's OID
     * @param value variable binding's value
     * @return SnmptrapCommandBuilder
     */
    public SnmptrapCommandBuilder addVariableBinding(final Oid oid, final String value) {
        this.variableBindings.add(new VariableBinding(oid, value));
        return this;
    }

    /**
     * Create snmptrap command.
     *
     * @return snmptrap command
     */
    public String create() {
        final var arguments = new LinkedList<String>();
        arguments.add(SNMPTRAP_COMMAND);
        arguments.add(versionFlag());
        arguments.add(communityFlag());
        arguments.add(createTarget());
        arguments.add(sysUptime);
        arguments.add(trapOid.oid());
        variableBindings.stream().map(SnmptrapCommandBuilder::createStringVariableBinding).forEach(arguments::add);

        return String.join(" ", arguments);
    }

    private String versionFlag() {
        return String.format("-v%s", version.getRepresentation());
    }

    private String communityFlag() {
        return String.format("-c %s", community);
    }

    private static String createStringVariableBinding(final VariableBinding variableBinding) {
        return String.format("%s s %s", variableBinding.getOid().oid(), variableBinding.getValue());
    }

    private String createTarget() {
        return isPortSpecified
                ? String.format("%s:%d", address, port)
                : address;
    }
}
