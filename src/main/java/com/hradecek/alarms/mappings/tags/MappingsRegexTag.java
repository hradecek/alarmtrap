package com.hradecek.alarms.mappings.tags;

/**
 * Represents regex replaceable tag.
 */
public class MappingsRegexTag implements MappingsTag<String> {

    private final String regex;

    /**
     * Constructor.
     *
     * @param regex string representation of regex
     */
    public MappingsRegexTag(final String regex) {
        this.regex = regex;
    }

    @Override
    public String getValue() {
        return regex;
    }
}
