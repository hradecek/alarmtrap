package com.hradecek.alarms.cli;

import picocli.CommandLine;

/**
 * CLI application main entry point.
 * <p>
 * Bootstraps whole application.
 */
public class MainCliApplication {

    /**
     * Main CLI method.
     *
     * @param args arguments passed from command line
     */
    public static void main(String[] args) {
        int exitCode =
                new CommandLine(new AlarmTrapCommand())
                        .setCaseInsensitiveEnumValuesAllowed(true)
                        .setExecutionExceptionHandler((e, commandLine, parseResult) -> {
                            e.printStackTrace();
                            return 1;
                        })
                        .execute(args);
        System.exit(exitCode);
    }
}
