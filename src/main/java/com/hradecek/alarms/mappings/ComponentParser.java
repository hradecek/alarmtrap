package com.hradecek.alarms.mappings;

/**
 * Provide parsing operation for component defined in alarm mappings.
 * <p>
 * Component is usually defined Java-style regex with extra proprietary characters used for business logic.
 */
class ComponentParser {

    private static final String MAPPING_TAGS = "%%";

    /**
     * Parse component regex without proprietary symbols.
     *
     * @param componentMappings raw component as defined in alarm mappings
     * @return parse component regex
     */
    public String parseRegex(final String componentMappings) {
        return stripParentheses(stripMappingTags(componentMappings));
    }

    private static String stripMappingTags(final String component) {
        return isSurrounded(component, MAPPING_TAGS)
                ? component.replace(MAPPING_TAGS, "")
                : component;
    }

    private static String stripParentheses(final String component) {
        return component.startsWith("(") && component.endsWith(")")
                ? component.substring(1, component.length() - 1)
                : component;
    }

    private static boolean isSurrounded(final String string, final String symbols) {
        return string.startsWith(symbols) && string.endsWith(symbols);
    }
}
