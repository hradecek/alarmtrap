package com.hradecek.alarms.snmp;

import com.hradecek.alarms.mappings.tags.MappingsOidTag;
import com.hradecek.alarms.mappings.tags.MappingsRegexTag;
import com.hradecek.alarms.mappings.tags.MappingsTag;

import com.mifmif.common.regex.Generex;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Represents alarm trap to be sent via SNMP.
 * <p>
 * Alarm MUST always have OID<br>,
 * Alarm MAY have associated component.
 */
public class AlarmTrap {

    private final static RandomOidGenerator RND_OID = new RandomOidGenerator();

    private final Oid oid;
    private final Map<Oid, String> componentValues = new LinkedHashMap<>();

    /**
     * Construtor.
     *
     * @param oid alarm SNMP trap OID
     */
    public AlarmTrap(Oid oid) {
        this.oid = oid;
    }

    /**
     * Constructor.
     * <p>
     * Creates random component OID based on {@code componentRegexes}.
     *
     * @param oid alarm SNMP trap OID
     * @param componentTags all replaceable tag present in component
     */
    public AlarmTrap(Oid oid, List<MappingsTag<?>> componentTags) {
        this.oid = oid;
        for (var componentTag : componentTags) {
            if (componentTag instanceof MappingsRegexTag) {
                componentValues.put(RND_OID.randomOid(((MappingsRegexTag) componentTag).getValue()), null);
            } else {
                componentValues.put(((MappingsOidTag) componentTag).getValue(), null);
            }
        }
    }

    /**
     * Generate random OID.
     */
    private static class RandomOidGenerator {

        private static final Random RND = new Random();

        /**
         * Get random OID by provided OID {@code regex}.
         * <p>
         * ".*" is replaced by random number of consecutive "\d", but maximally 3 (might be 0 as well).
         *
         * @param regex OID regex
         * @return random OID
         */
        public Oid randomOid(final String regex) {
            return new Oid(new Generex(replaceDotStarByRandomDigits(regex)).random());
        }

        private static String replaceDotStarByRandomDigits(final String regex) {
            final var digits = "\\.\\d".repeat(RND.nextInt(3));
            return regex.replace(".*", digits);
        }
    }

    /**
     * Get alarm SNMP trap OID.
     *
     * @return alarm SNMP trap OID
     */
    public Oid getOid() {
        return oid;
    }

    /**
     * Sets component values.
     *
     * @param componentValues component values
     */
    public void setComponentValues(String... componentValues) {
        final var iterator = this.componentValues.entrySet().iterator();
        int componentValueIndex = 0;
        while (iterator.hasNext() && componentValueIndex < componentValues.length) {
            iterator.next().setValue(componentValues[componentValueIndex]);
            ++componentValueIndex;
        }
    }

    /**
     * Get component OIDs with their specified values.
     *
     * @return component binding
     */
    public List<ComponentBinding> getComponentBindings() {
        return componentValues.entrySet()
                              .stream()
                              .filter(entry -> entry.getValue() != null)
                              .map(entry -> new ComponentBinding(entry.getKey(), entry.getValue()))
                              .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        final var string = new StringBuilder();
        string.append("TrapOID= ").append(oid);
        for (Map.Entry<Oid, String> component : componentValues.entrySet()) {
            if (component .getValue() != null) {
                string.append(",Component=(").append(component).append(")");
            }
        }

        return string.toString();
    }
}
