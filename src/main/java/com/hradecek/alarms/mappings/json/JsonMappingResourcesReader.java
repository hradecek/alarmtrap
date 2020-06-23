package com.hradecek.alarms.mappings.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hradecek.alarms.mappings.AlarmMappings;
import com.hradecek.alarms.mappings.AlarmMappingsReader;

import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;

/**
 * Reads alarm mappings in JSON format present on class path.
 */
public class JsonMappingResourcesReader implements AlarmMappingsReader<String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public AlarmMappings readAlarmMappings(final String mappingsPath) throws IOException {
        final var alarmMappings = new JsonAlarmMappings();
        final var resources = new PathMatchingResourcePatternResolver(getClass().getClassLoader()).getResources(mappingsPath);

        for (final var mappingResource : resources) {
            alarmMappings.addAll(objectMapper.readValue(mappingResource.getInputStream(), JsonAlarmMappings.class));
        }

        return alarmMappings;
    }
}
