package com.hradecek.alarms.mappings;

import com.hradecek.alarms.snmp.Oid;

import java.util.Map;

/**
 * Represents alarm mappings.
 * <p>
 * Mapping always maps OID value to {@link AlarmMapping alarm}. Single file can consists of more than one such mapping.
 */
public interface AlarmMappings {

    /**
     * Get all mappings.
     *
     * @return OID to alarm mappings
     */
    Map<Oid, AlarmMapping> getMappings();

    /**
     * Merge with all {@code alarmMappings}.
     *
     * @param alarmMappings alarm mappings to be add
     */
    void addAll(AlarmMappings alarmMappings);
}
