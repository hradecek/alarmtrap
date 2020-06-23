package com.hradecek.alarms.mappings.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hradecek.alarms.mappings.AlarmMappings;
import com.hradecek.alarms.mappings.AlarmMappingsReader;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Reads single alarm mapping file in JSON format.
 */
public class JsonMappingFileReader implements AlarmMappingsReader<Path> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public AlarmMappings readAlarmMappings(Path mappingsPath) throws IOException {
        return objectMapper.readValue(mappingsPath.toFile(), JsonAlarmMappings.class);
    }
}
