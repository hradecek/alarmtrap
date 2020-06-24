package com.hradecek.alarms.mappings.tags;

import com.hradecek.alarms.snmp.Oid;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Iterates through all "replaceable" tags in provided string.
 * <p>
 * Replaceable tags might be in form of pure OID or Regex.
 */
public class TagsIterator implements Iterator<MappingsTag<?>> {

    private static final String OID_GROUP = "oids";
    private static final String OID_PATTERN = String.format("%%(?<%s>1.3.*?)%%", OID_GROUP);

    private static final String REGEX_GROUP = "regex";
    private static final String REGEX_OID_PATTERN = String.format(".*?%%\\((?<%s>.*?)\\)%%.*?", REGEX_GROUP);

    private static final Pattern REPLACEABLE_PATTERN =
            Pattern.compile(String.format("(?:%s)|(?:%s)", OID_PATTERN, REGEX_OID_PATTERN));

    private final Matcher matcher;

    /**
     * Constructor.
     *
     * @param string string containing replaceables tags (%%)
     */
    public TagsIterator(final String string) {
        this.matcher = REPLACEABLE_PATTERN.matcher(string);
    }

    @Override
    public boolean hasNext() {
        return matcher.find();
    }

    @Override
    public MappingsTag<?> next() {
        final var oidMatch = matcher.group(OID_GROUP);

        if (null != oidMatch) {
            return new MappingsOidTag(new Oid(oidMatch));
        }
        return new MappingsRegexTag(matcher.group(REGEX_GROUP));
    }
}
