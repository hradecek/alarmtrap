package com.hradecek.alarms.cli;

import com.hradecek.alarms.mappings.Severity;

/**
 * Thrown when alarm definition was be found.
 */
public class AlarmNotFoundException extends Exception {

    /**
     * Create alarm not found exception for specified and {@code alarmName} and {@code severity}.
     *
     * @param alarmName clearing alarm name
     * @param severity alarm's severity
     */
    public AlarmNotFoundException(final String alarmName, final Severity severity) {
        super(String.format("Alarm '%s' with severity '%s' was not found in mapping files.",
                            alarmName,
                            severity.getRepresentation()));
    }
}
