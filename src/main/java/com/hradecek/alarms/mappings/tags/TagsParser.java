package com.hradecek.alarms.mappings.tags;

import com.hradecek.alarms.snmp.Oid;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Provide parsing operation for replaceable tags used in alarm mappings.
 * <p>
 * Tags can be defined either by:
 * <ul>
 *   <li>OID or
 *   <li>OID Java-style regex
 * </ul>
 */
public class TagsParser {

    private static final Pattern OID_PATTERN = Pattern.compile("%%.*?%%");
    private static final Pattern REGEX_OID_PATTERN = Pattern.compile(".*?%%\\((.*?)\\)%%.*?");

    /**
     * Parse regex without proprietary symbols as strings.
     * <p>
     * Example: %%(1\\.3\\.6\\.*)%% will be parsed as string "1\.3\.6\.*"
     *
     * @param string raw string which may contains special OID regex tags
     * @return all found regexes as string
     */
    public List<String> parseRegexes(final String string) {
        return getAllMatches(string, REGEX_OID_PATTERN);
    }

    /**
     * Parse replaceable OID values.
     * <p>
     * Example: %%1.3.6.1.6.3.1.1.5.3%% will be parsed as string "1.3.6.1.6.3.1.1.5.3"
     *
     * @param string raw string which may contains special OID tags
     * @return all found OIDs
     */
    public List<Oid> parseOids(final String string) {
        return getAllMatches(string, OID_PATTERN).stream().map(Oid::new).collect(Collectors.toList());
    }

    private static List<String> getAllMatches(final String string, final Pattern pattern) {
        final var matcher = pattern.matcher(string);
        final var matches = new ArrayList<String>();

        while (matcher.find()) {
            matches.add(matcher.group(1));
        }

        return matches;
    }
}
