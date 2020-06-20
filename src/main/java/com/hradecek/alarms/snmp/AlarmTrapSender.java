package com.hradecek.alarms.snmp;

import java.io.IOException;

/**
 * Provides interface for sending alarms as SNMP traps.
 */
public interface AlarmTrapSender {

    /**
     * Sends provided {@code alarmTrap} to host specified by {@code address} and {@code port} using
     * {@code communityString}.
     *
     * @param alarmTrap alarm's SNMP trap definition
     * @param address host's address where alarm SNMP trap is sent
     * @param port host's port
     * @param communityString used SNMP community string
     * @throws IOException thrown if SNMP trap cannot be sent
     */
    void send(AlarmTrap alarmTrap, String address, int port, String communityString) throws IOException;
}
