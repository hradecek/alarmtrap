# alarmtrap
This tool provides simple CLI application (built using [picocli](https://picocli.info/))
that can send proprietary SNMP traps based on provided so called *mapping files*.

## Build CLI (native images included)
`alarmtrap` is built using maven and [GraalVM](https://www.graalvm.org/).

As native image is built in `install` maven step be sure maven is using GraalVM instead of JRE.

Run:
```shell
$ mvn clean install
```

After successful build, runnable native image `alarmtrap` and *fat-jar* `alarmtrap.jar` are present in `target/` folder.

## Run CLI
For help run:
```shell
$ alarmtrap --help
```

### Parameters
**`<alarmName>`**

Name of the alarm. Must be present in provided mappings.

**`<address>`**

Host address, where resulting SNMP trap is sent.

Must be in form of "`<IP_ADDRESS>:<PORT>`" or "`<IP_ADDRESS>`". If is omitted, the `162` is used by default.

### Options
**`-m`** or **`--mappings`**

Path to  mapping file or directory containing mapping files in JSON format.

Default path is working directory.

**`-c`** or **`--component`**

Value to be used for random component generated based on regex found in mappings.

**`-s`** or **`--severity`**

Specify alarm's severity. Must be one of:

 - `CLEARED` (default),
 - `WARNING`,
 - `MINOR`,
 - `MAJOR`,
 - `CRITICAL`.

Default is `CLEARED`.

Severity is case insensitive. E.g. severity `MINOR` can be specified as `Minor`, `minor`, `MInOr`, ... .

**`-t`** or **`--snmptrap`**

If specified, then alarm is not really send to provided address, but "copy & paste"-ready string
[snmptrap](http://www.net-snmp.org/tutorial/tutorial-5/commands/snmptrap.html) is printed out instead.

Example:
```
$ alarmtrap --mapings "/path/to/mappings" --component "hello" --severity Minor SomeAlarm 127.0.0.1 -t

snmptrap -v2c -c public 127.0.0.1:162 '' 1.3.6.1.4.1.X.X.X.X 1.3.6.1.4.1.X.Y.Y.Y s hello
```

----

TODOs:

 - separate [com.hradecek.alarms.cli](./src/main/java/com/hradecek/alarms/cli) to its own maven sub-module
 - implements `<TAB>` completion
 - multiple alarms with same name

