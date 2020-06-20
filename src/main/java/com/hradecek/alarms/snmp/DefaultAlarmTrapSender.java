package com.hradecek.alarms.snmp;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * Implements {@link AlarmTrapSender} using Snmp4j library.
 */
public class DefaultAlarmTrapSender implements AlarmTrapSender {

    @Override
    public void send(AlarmTrap alarmTrap, String address, int port, String communityString) throws IOException {
        createSnmp().send(createPdu(alarmTrap), createCommunity(address, port, communityString));
    }

    private static Snmp createSnmp() throws IOException {
        final var snmp = new Snmp(new DefaultUdpTransportMapping());
        snmp.listen();

        return snmp;
    }

    private CommunityTarget<Address> createCommunity(final String address,
                                                     int port,
                                                     final String communityString) throws UnknownHostException {
        final var communityTarget = new CommunityTarget<>();
        communityTarget.setAddress(new UdpAddress(InetAddress.getByName(address), port));
        communityTarget.setCommunity(new OctetString(communityString));
        communityTarget.setVersion(SnmpConstants.version2c);
        communityTarget.setTimeout(5_000);

        return communityTarget;

    }

    private PDU createPdu(AlarmTrap alarmTrap) {
        final var pdu = new PDU();

        pdu.add(new VariableBinding(SnmpConstants.sysUpTime, new OctetString(new Date().toString())));
        pdu.add(new VariableBinding(SnmpConstants.snmpTrapOID, new OID(alarmTrap.getOid().oid())));
        pdu.setType(PDU.NOTIFICATION);

        alarmTrap.getComponentBinding()
                 .ifPresent(componentBinding -> pdu.add(componentToVariableBinding(componentBinding)));

        return pdu;
    }

    private static VariableBinding componentToVariableBinding(final ComponentBinding componentBinding) {
        return new VariableBinding(new OID(componentBinding.getOid().oid()),
                                   new OctetString(componentBinding.getValue()));
    }
}
