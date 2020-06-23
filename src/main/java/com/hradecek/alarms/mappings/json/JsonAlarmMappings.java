package com.hradecek.alarms.mappings.json;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.hradecek.alarms.mappings.AlarmMapping;
import com.hradecek.alarms.mappings.AlarmMappings;
import com.hradecek.alarms.snmp.Oid;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents single JSON mappings.
 * <p>
 * JSON mapping consists of single JSON object with OIDs as keys and mapped alarms as values.
 */
public class JsonAlarmMappings implements AlarmMappings {

    private Map<Oid, AlarmMapping> mappings = new HashMap<>();

    @Override
    public Map<Oid, AlarmMapping> getMappings() {
        return mappings;
    }

    @Override
    public void addAll(AlarmMappings alarmMappings) {
        mappings.putAll(alarmMappings.getMappings());
    }

    @JsonAnySetter
    public void setMapping(String oid, AlarmMapping alarmMapping) {
        mappings.put(new Oid(oid), alarmMapping);
    }
}
