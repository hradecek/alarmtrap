package com.hradecek.alarms.mappings.json;

import com.hradecek.alarms.mappings.AlarmMappings;
import com.hradecek.alarms.mappings.AlarmMappingsReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Reads directory which contains one or more alarm mapping files in JSON format.
 *
 * @see JsonMappingFileReader
 */
public class JsonMappingDirectoryReader implements AlarmMappingsReader<Path> {

    private static final String SUFFIX_JSON = ".json";

    private final JsonMappingFileReader mappingFileReader = new JsonMappingFileReader();
    private final boolean skipInvalid;

    /**
     * Constructor.
     */
    public JsonMappingDirectoryReader() {
        this(false);
    }

    /**
     * Constructor.
     * <p>
     * If {@code skipInvalid} is set to true invalid JSON files will be skipped and reading will continue
     * with next files. Otherwise reading will end with exception.
     *
     * @param skipInvalid if true invalid JSON files will be skipped
     */
    public JsonMappingDirectoryReader(boolean skipInvalid) {
        this.skipInvalid = skipInvalid;
    }

    @Override
    public AlarmMappings readAlarmMappings(Path mappingsPath) throws IOException {
        final var allMappings = new JsonAlarmMappings();
        for (var jsonFile : getJsonFiles(mappingsPath)) {
            try {
                allMappings.addAll(mappingFileReader.readAlarmMappings(jsonFile));
            } catch (IOException exception) {
                if (!skipInvalid) {
                    throw exception;
                }
            }
        }
        return allMappings;
    }

    private static List<Path> getJsonFiles(final Path directoryPath) throws IOException {
        try (final var files = Files.list(directoryPath)) {
            return files.filter(path -> path.toString().endsWith(SUFFIX_JSON)).collect(Collectors.toList());
        }
    }
}
