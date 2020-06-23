# alarmtrap
This tool provides simple CLI application (built using [picocli](https://picocli.info/))
that can send proprietary SNMP traps based on provided so called *mapping files*.

## Build CLI

### Fat JAR
In order to build runnable fat-jar run:

```shell
$ mvn clean install
```

After successful build, *fat-jar* `alarmtrap.jar` is present in `target/` folder.
It can be run using `java`, like so:
```shell
$ java -jar alarm.jar --help
```

### Native image
In order to build runnable native-image using [GraalVM](https://www.graalvm.org/)

```shell
$ mvn clean install -Pnative-image
```

As native image is built in `install` maven step be sure maven is using GraalVM instead of JRE.

After successful build, native image `alarmtrap` is present in `target/` folder.
It can be run just like any other ordinary executable program:
```shell
$ ./alarmtrap --help
```

### Predefined mapping files
Mapping files put into [`resources/mappings/`](./src/main/resources/mappings/) folder are pre-loaded in
compile time and available together with files defined by `-m` option.

## Running CLI
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

