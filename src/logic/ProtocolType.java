package logic;

import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.protocol.network.*;
import org.jnetpcap.protocol.tcpip.*;

public class ProtocolType {
	public String getProType(PcapPacket item) {
		String protocol = null;
		// 判断协议的类型
		if (item.hasHeader(new Arp())) {
			protocol = "ARP";
		}
		if (item.hasHeader(new Ip4())) {
			protocol = "IPv4";
		} else if (item.hasHeader(new Ip6())) {
			protocol = "IPv6";
		}
		if (item.hasHeader(new Udp())) {
			protocol = "UDP";
		}
		if (item.hasHeader(new Tcp())) {
			protocol = "TCP";
		}
		if (item.hasHeader(new Icmp())) {
			protocol = "ICMP";
		}

		if (item.hasHeader(new Http())) {
			protocol = "HTTP";
		}
		return protocol;
	}
}
