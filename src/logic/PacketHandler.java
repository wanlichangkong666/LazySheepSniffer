package logic;
import java.util.Date;

import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.protocol.lan.Ethernet;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Http;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Udp;

public class PacketHandler<T> implements PcapPacketHandler<T> {

    @Override
    public void nextPacket(PcapPacket packet, T user) {

        Http http = new Http();
        if (!packet.hasHeader(http)) {
            return;
        }
        // System.out.printf("Received packet at %s caplen=%-4d len=%-4d %s\n",
        // new Date(packet.getCaptureHeader().timestampInMillis()), packet
        // .getCaptureHeader().caplen(), // Length
        // // actually
        // // captured
        // packet.getCaptureHeader().wirelen(), // Original
        // // length
        // user // User supplied object
        // );
        String contend = packet.toString();
        
            System.out.println(contend);
        
        // }
        // System.out.println( http.getPacket().toString());

        // System.out.println(contend);

        // String hexdump=packet.toHexdump(packet.size(), false, true,
        // false);

        // byte[] data = FormatUtils.toByteArray(hexdump);

        Ethernet eth = new Ethernet(); // Preallocate our ethernet
                                        // header
        Ip4 ip = new Ip4(); // Preallocat IP version 4 header

        Tcp tcp = new Tcp();

        Udp udp = new Udp();

        // Http http=new Http();
        // if (packet.hasHeader(eth)) {
        // System.out.printf("ethernet.type=%X\n", eth.type());
        // }
        //
        // if (packet.hasHeader(ip)) {
        // System.out.printf("ip.version=%d\n", ip.version());
        // }

    }
}