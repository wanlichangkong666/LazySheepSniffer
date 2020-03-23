package logic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.lan.Ethernet;
import org.jnetpcap.protocol.network.*;
import org.jnetpcap.protocol.tcpip.*;
import org.omg.CORBA.PUBLIC_MEMBER;

import apperance.APPMain;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import com.sun.istack.internal.FinalArrayList;

public class CapturePacks {
	public static int totalCount = 0;
	public static int ipCount = 0;
	public static int httpCount = 0;
	public static int icmpCount= 0;
	public static int arpCount = 0;
	public static int tcpCount = 0;
	public static int udpCount = 0;
	String packId;
	String time;
	String protocol;
	String packLen;
	String srcIP;
	String dstIP;
	String srcMAC;
	String dstMAC;
	private Thread startThread = null;
	public static int count = 0;
	public static PcapIf choosenDevice;
	StringBuilder errbuf = new StringBuilder();
	public static java.util.List<String> contents = new ArrayList<String>();
	public static java.util.List<PcapPacket> packsList = new ArrayList<PcapPacket>();

	public void startCap(final int id, final Table table,final Text content) {

		// final List packetsList = new List(packetsShell, SWT.V_SCROLL);
		// packetsList.setBounds(packetsShell.getBounds());
		// packetsShell.setLayout(new FillLayout(SWT.HORIZONTAL));
		// // ��ʾ����������List
		//
		// packetsShell.open();

		choosenDevice = Interface.alldevs.get(id);
		int snaplen = 64 * 1024;// ��󳤶�
		int flags = Pcap.MODE_PROMISCUOUS;// ����ģʽ
		int timeout = 3 * 1000;// ��ʱʱ��
		// ���豸
		final Pcap pcap = Pcap.openLive(choosenDevice.getName(), snaplen,
				flags, timeout, errbuf);
		if (pcap == null) {
			System.out.println("Error while opening device for capture."
					+ errbuf);
		}
		// ����������̽�Ĵ������
		final PcapPacketHandler<String> pcapPacketHandler = new PcapPacketHandler<String>() {

			@Override
			public void nextPacket(PcapPacket pcapPacket, String s) {

				// if (count % 10 == 0) {
				//
				// {
				// for (int i = count - 10; i < count; i++)
				// // packetsList.add(packsList.get(i).toString());
				// // ���ÿһ��ץ�����ݵ�APPMain�е�TableViewer�в�ˢ��
				// {
				// PcapPacket item = packsList.get(i);
				Ethernet eth = new Ethernet();
				Ip4 ip4 = new Ip4();
				Ip6 ip6 = new Ip6();
				Http http = new Http();
				Icmp icmp = new Icmp();
				Arp arp = new Arp();
				Tcp tcp = new Tcp();
				Udp udp = new Udp();
				pcapPacket.hasHeader(ip4);
				pcapPacket.hasHeader(eth);
					
					if (APPMain.wantIP
							&& (pcapPacket.hasHeader(ip4) || pcapPacket
									.hasHeader(ip6)) || APPMain.wantHTTP
							&& pcapPacket.hasHeader(http) || APPMain.wantICMP
							&& pcapPacket.hasHeader(icmp) || APPMain.wantARP
							&& pcapPacket.hasHeader(arp) || APPMain.wantTCP
							&& pcapPacket.hasHeader(tcp) || APPMain.wantUDP
							&& pcapPacket.hasHeader(udp)) {
						// ��ȡ��ǰ����Ŀ...
						packId = "" + count;
						count++;
						totalCount++;

						// ��ȡ���ݰ�Դ��ַ
						
						try {
							srcIP = FormatUtils.ip(ip4.source());
						} catch (Exception e) {
							
							srcIP = "---.---.---.---";
						}

						// ��ȡ���ݰ�Ŀ�ĵ�ַ
						try {
							dstIP = FormatUtils.ip(ip4.destination());
						} catch (Exception e) {
							
							dstIP = "---.---.---.---";
						}
						srcMAC = FormatUtils.mac(eth.source());
						dstMAC = FormatUtils.mac(eth.destination());
						// �ж�Э�������
						if (pcapPacket.hasHeader(arp)) {
							protocol = "ARP";
							arpCount++;
							
						}
						if (pcapPacket.hasHeader(ip4)) {
							protocol = "IPv4";
							ipCount++;
						} else if (pcapPacket.hasHeader(ip6)) {
							protocol = "IPv6";
							ipCount++;
						}
						if (pcapPacket.hasHeader(udp)) {
							protocol = "UDP";
							udpCount++;
						}
						if (pcapPacket.hasHeader(tcp)) {
							protocol = "TCP";
							tcpCount++;
						}
						if (pcapPacket.hasHeader(icmp)) {
							protocol = "ICMP";
							icmpCount++;
						}

						if (pcapPacket.hasHeader(http)) {
							protocol = "HTTP";
							httpCount++;
						}
						//System.out.println(pcapPacket.getPacketWirelen());
						packLen=String.valueOf(pcapPacket.getPacketWirelen());
						// �����ݰ���ʱ��ת��Ϊ������Ϥ����ʽ
						time = new String(
								new SimpleDateFormat("HH:mm:ss")
										.format(pcapPacket.getCaptureHeader()
												.timestampInMillis()));
						
						contents.add(pcapPacket.toHexdump());
						// System.out.println(pcapPacket.toHexdump());
						Display.getDefault().syncExec(new Runnable() {
							public void run() {
								TableItem packItem = new TableItem(table,
										SWT.NONE);

								packItem.setText(new String[] { packId, time,
										protocol,packLen, srcIP, dstIP, srcMAC, dstMAC });
							}
						});
					}
				}
			

			// }
			// }
			// }
		};
		startThread = new Thread(new Runnable() {

			@Override
			public void run() {
				pcap.loop(-1, pcapPacketHandler, "Jnetpcap rocks");

				// packetsList.update();
			}

		});
		startThread.start();
	}

	public void stopCap(final Table statTable) {
		if (startThread != null) {
			startThread.stop();
			startThread = null;
		}
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					 TableItem tableItems[] = statTable.getItems();//�õ����е�tableItem
			            for(int i = 0; i<tableItems.length; i++)
			            {
			                tableItems[i].dispose();//�ͷ�
			            }
					TableItem statItem = new TableItem(statTable, SWT.PUSH);
					statItem.setText(new String[]{String.valueOf(totalCount),
							String.valueOf(ipCount),
							String.valueOf(icmpCount),
							String.valueOf(httpCount),
							String.valueOf(udpCount),
							String.valueOf(tcpCount),
							String.valueOf(arpCount)});
				}
			});
			//System.out.println(tcpCount);
		
	}
	public static void reset() {
		totalCount = 0;
		ipCount = 0;
		httpCount = 0;
		icmpCount= 0;
		arpCount = 0;
		tcpCount = 0;
		udpCount = 0;
		count = 0;
		
	}
}
