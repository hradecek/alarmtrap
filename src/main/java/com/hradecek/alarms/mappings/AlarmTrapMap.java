package com.hradecek.alarms.mappings;

import com.hradecek.alarms.mappings.tags.MappingsTag;
import com.hradecek.alarms.mappings.tags.TagsIterator;
import com.hradecek.alarms.snmp.AlarmTrap;
import com.hradecek.alarms.snmp.Oid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Maps alarm definitions found in provided mappings to their particular SNMP trap definitions.
 */
public class AlarmTrapMap {

    private final ComponentParser componentParser = new ComponentParser();

    private static class AlarmRecord {

        private final String name;
        private final Severity severity;

        public AlarmRecord(String name, Severity severity) {
            this.name = name;
            this.severity = severity;
        }

        public String name() {
            return this.name;
        }

        public Severity severity() {
            return this.severity;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }

            if (other == null || getClass() != other.getClass()) {
                return false;
            }

            final var that = (AlarmRecord) other;

            return Objects.equals(name, that.name) && severity == that.severity;
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, severity);
        }
    }

    private final Map<AlarmRecord, AlarmTrap> alarmToOid = new HashMap<>();

    /**
     * Constructor.
     * <p>
     * Constructs alarm trap map.
     *
     * @param mappings all alarm mappings
     */
    public AlarmTrapMap(AlarmMappings mappings) {
        for (var mappingEntry : mappings.getMappings().entrySet()) {
            var oid = mappingEntry.getKey();
            var alarmMapping = mappingEntry.getValue();
            alarmToOid.put(alarmMappingToRecord(alarmMapping), alarmMappingToAlarmTrap(oid, alarmMapping));
        }
    }

    private AlarmRecord alarmMappingToRecord(final AlarmMapping alarmMapping) {
        return new AlarmRecord(alarmMapping.getName(), alarmMapping.getSeverity());
    }

    private AlarmTrap alarmMappingToAlarmTrap(final Oid oid, final AlarmMapping alarmMapping) {
        if (alarmMapping.getComponent() != null) {
            return new AlarmTrap(oid, componentParser.parseReplaceables(alarmMapping.getComponent()));
        }
        return new AlarmTrap(oid);
    }

    private static class ComponentParser {

        public List<MappingsTag<?>> parseReplaceables(final String component) {
            final List<MappingsTag<?>> tags = new ArrayList<>();
            final var tagsIterator = new TagsIterator(component);
            while (tagsIterator.hasNext()) {
                tags.add(tagsIterator.next());
            }
            return tags;
        }
    }

    /**
     * Get alarm SNMP trap definition for provided {@code alarmName} and {@code severity}.
     *
     * @param alarmName alarm's name
     * @param severity alarm's severity
     * @return Alarm's SNMP Trap definition if found, {@link Optional#empty()} otherwise.
     */
    public Optional<AlarmTrap> getAlarmTrap(final String alarmName, final Severity severity) {
        return Optional.ofNullable(alarmToOid.get(new AlarmRecord(alarmName, severity)));
    }
}
