package com.hradecek.alarms.mappings;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Reads alarm mappings.
 */
public interface AlarmMappingsReader {

    /**
     * Reads alarm mapping from provided {@code mappingsPath}.
     *
     * @param mappingsPath path to where the alarm mappings are stored
     * @return read alarm mappings
     * @throws IOException if alarm mappings cannot be read
     */
    AlarmMappings readAlarmMappings(Path mappingsPath) throws IOException;
}
