package com.hradecek.alarms.mappings.json;

import com.hradecek.alarms.mappings.AlarmMappingsReader;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Creates either directory or file reader based on provided path.
 */
public class JsonMappingReaderFactory {

    /**
     * Creates {@link AlarmMappingsReader}.
     *
     * @param path path to mapping directory or file
     * @return relevant alarm mappings reader
     */
    public static AlarmMappingsReader<Path> create(final Path path) {
        return Files.isDirectory(path) ? new JsonMappingDirectoryReader() : new JsonMappingFileReader();
    }
}
