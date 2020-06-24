package com.hradecek.alarms.mappings.tags;

/**
 * Represents replaceable tag.
 *
 * @param <T> type parameter of replaceable tag
 */
public interface MappingsTag<T> {

    /**
     * Return tag value.
     *
     * @return value
     */
    T getValue();
}
