package com.hradecek.alarms.mappings;

import java.io.IOException;

/**
 * Reads alarm mappings.
 */
@FunctionalInterface
public interface AlarmMappingsReader<T> {

    /**
     * Reads alarm mapping from provided {@code mappingsPath}.
     *
     * @param mappingsPath path to where the alarm mappings are stored
     * @return read alarm mappings
     * @throws IOException if alarm mappings cannot be read
     */
    AlarmMappings readAlarmMappings(T mappingsPath) throws IOException;
}
