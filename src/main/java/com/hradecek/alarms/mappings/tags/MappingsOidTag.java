package com.hradecek.alarms.mappings.tags;

import com.hradecek.alarms.snmp.Oid;

/**
 * Represents OID replaceable tag.
 */
public class MappingsOidTag implements MappingsTag<Oid> {

    private final Oid oid;

    /**
     * Constructor.
     *
     * @param oid replaceable OID
     */
    public MappingsOidTag(Oid oid) {
        this.oid = oid;
    }

    @Override
    public Oid getValue() {
        return oid;
    }
}
