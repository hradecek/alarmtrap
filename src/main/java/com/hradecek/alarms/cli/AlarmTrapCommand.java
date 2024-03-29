package com.hradecek.alarms.cli;

import com.hradecek.alarms.mappings.AlarmMappings;
import com.hradecek.alarms.mappings.AlarmTrapMap;
import com.hradecek.alarms.mappings.Severity;
import com.hradecek.alarms.mappings.json.JsonMappingReaderFactory;
import com.hradecek.alarms.mappings.json.JsonMappingResourcesReader;
import com.hradecek.alarms.snmp.AlarmTrap;
import com.hradecek.alarms.snmp.DefaultAlarmTrapSender;
import com.hradecek.alarms.snmp.SnmpVersion;
import com.hradecek.alarms.snmp.SnmptrapCommandBuilder;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

/**
 * Represents main application.
 * <p>
 * Here are defined behavior, options, parameters etc. common for whole CLI application.
 */
@Command(name = "alarmtrap",
         mixinStandardHelpOptions = true,
         usageHelpAutoWidth = true,
         headerHeading = "Usage:%n%n",
         header = "Raises or clears alarm using SNMP traps.",
         synopsisHeading = "%n",
         parameterListHeading = "%nParameters:%n",
         optionListHeading = "%nOptions:%n",
         version = "alarmtrap - 0.0.1")
public class AlarmTrapCommand implements Callable<Integer> {

    public static final int DEFAULT_PORT = 162;
    public static final String DEFAULT_COMMUNITY = "public";
    private static final String PREDEFINED_MAPPINGS = "classpath*:/mappings/*.json";

    protected final DefaultAlarmTrapSender alarmTrapSender = new DefaultAlarmTrapSender();

    @Option(names = { "-m", "--mappings" },
            description = "Path to alarm mappings (either file or directory),%nDefault: working directory ('${DEFAULT-VALUE}').")
    protected Path mappingsPath = Paths.get(".");

    @Option(names = { "-c", "--components"}, description = "Values for OIDs in component.")
    protected List<String> components;

    @Option(names = { "-s", "--severity" }, description = "${COMPLETION-CANDIDATES}%nDefault: ${DEFAULT-VALUE}.")
    protected Severity severity = Severity.CLEARED;

    @Option(names = { "-t", "--snmptrap" } , description = "Creates snmptrap command for copy&paste instead of sending real SNMP trap.")
    protected boolean isSnmptrapSimulation = false;

    @Parameters(index = "0", description = "Alarm name")
    protected String alarmName;

    @Parameters(index = "1", description = "Host address - <IP_ADDRESS>:<PORT>%n<PORT> is 162 if not specified.")
    protected String address;

    @Override
    public Integer call() throws Exception {
        final var mappingsReader = JsonMappingReaderFactory.create(mappingsPath);
        final var alarmMappings = mappingsReader.readAlarmMappings(mappingsPath);
        readPredefined().ifPresent(alarmMappings::addAll);
        final var alarmTrapMap = new AlarmTrapMap(alarmMappings);
        final var alarmTrap = alarmTrapMap.getAlarmTrap(alarmName, severity)
                                          .orElseThrow(() -> new AlarmNotFoundException(alarmName, severity));
        if (null != components) {
            alarmTrap.setComponentValues(components.toArray(String[]::new));
        }

        if (isSnmptrapSimulation) {
            System.out.println(createSnmpTrapCommand(alarmTrap));
        } else {
            alarmTrapSender.send(alarmTrap, parseAddress(), parsePort(), DEFAULT_COMMUNITY);
            System.out.printf("Sent SNMP trap: '%s'\n", alarmTrap);
        }

        return 0;
    }

    private Optional<AlarmMappings> readPredefined() {
        try {
            return Optional.of(new JsonMappingResourcesReader().readAlarmMappings(PREDEFINED_MAPPINGS));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private String createSnmpTrapCommand(final AlarmTrap alarmTrap) {
        final var snmptrapCommandBuilder = new SnmptrapCommandBuilder();
        snmptrapCommandBuilder.version(SnmpVersion.V2C);
        snmptrapCommandBuilder.community(DEFAULT_COMMUNITY);
        snmptrapCommandBuilder.address(parseAddress());
        snmptrapCommandBuilder.port(parsePort());
        snmptrapCommandBuilder.trapOid(alarmTrap.getOid());
        for (final var componentBinding : alarmTrap.getComponentBindings()) {
            snmptrapCommandBuilder.addVariableBinding(componentBinding.getOid(), componentBinding.getValue());
        }

        return snmptrapCommandBuilder.create();
    }

    private String parseAddress() {
        return address.split(":")[0];
    }

    private int parsePort() {
        String[] parsedAddress = address.split(":");
        if (parsedAddress.length >= 2) {
            try {
                return Integer.parseInt(parsedAddress[1]);
            } catch (NumberFormatException ex) {
                System.err.format("Port %s is not valid, using default %d instead.", parsedAddress[1], DEFAULT_PORT);
                return DEFAULT_PORT;
            }
        } else {
            return DEFAULT_PORT;
        }
    }
}
