package logic;

import java.util.ArrayList;
import java.util.List;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;

public class Interface {
	public static int chooseInterfaceId = 0;

	public static List<PcapIf> alldevs = new ArrayList<PcapIf>();
	public List<PcapIf> getInterfaces() {
		 // Will be filled with
		// NICs
		StringBuilder errbuf = new StringBuilder(); // For any error msgs

		/***************************************************************************
		 * First get a list of devices on this system
		 **************************************************************************/
		int r = Pcap.findAllDevs(alldevs, errbuf);
		if (r == Pcap.NOT_OK || alldevs.isEmpty()) {
			System.err.printf("Can't read list of devices, error is %s",
					errbuf.toString());
			return null;
		}
		return alldevs;
		

	}

}
