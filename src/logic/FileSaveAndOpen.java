package logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapDumper;
import org.jnetpcap.PcapHandler;
import org.jnetpcap.PcapIf;

public class FileSaveAndOpen {
	public static int totalCount=0;
	public static int ipCount = 0;
	public static int icmpCount = 0;
	public static int arpCount = 0;
	public static int tcpCount = 0;
	public static int udpCount = 0;
	public static int httpCount = 0;
	public static void open(Table table, Table statTable, Text packContentText) {
		Shell openShell = new Shell();
		FileDialog dialog = new FileDialog(openShell, SWT.OPEN);
		dialog.setFilterPath("");// 设置默认的路径
		dialog.setText("请选择打开的数据包文件");// 设置对话框的标题
		dialog.setFileName("");// 设置默认的文件名
		dialog.setFilterNames(new String[] { "文本文件 (*.txt)", "所有文件(*.*)" });// 设置扩展名
		dialog.setFilterExtensions(new String[] { "*.txt", "*.*" });// 设置文件扩展名
		String fileName = dialog.open();//
		File inputFile = null;
		if (fileName != null) {
			inputFile = new File(fileName);
		} else {
			return;
		}
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(inputFile));
			int count = 0;
			String line;
			StringBuilder content = new StringBuilder();
			while ((line = br.readLine()) != null) {
				count++;
				
				if (line.equals("")) {
					count = 0;
					totalCount++;
					CapturePacks.contents.add(content.toString());
				}
				if (count == 1) {
					String[] packInfo = line.split(",");
					//System.out.println(packInfo);
					TableItem packItem = new TableItem(table, SWT.NONE);

					packItem.setText(new String[] { packInfo[0], packInfo[1],
							packInfo[2], packInfo[3], packInfo[4], packInfo[5],
							packInfo[6], packInfo[7] });
					System.out.println(packInfo[2]);
					// 设置统计数据
					if (packInfo[2].equals("IPv4") || packInfo[2].equals( "IPv6"))
						ipCount++;
					if (packInfo[2].equals("TCP")) {
						ipCount++;
						tcpCount++;
					}
					if (packInfo[2] .equals("UDP")) {
						ipCount++;
						udpCount++;
					}
					if (packInfo[2].equals("ICMp"))
						icmpCount++;
					if (packInfo[2] .equals("ARP"))
						arpCount++;
					if (packInfo[2].equals("HTTP"))
						httpCount++;
					content.delete(0, content.length());
				} else {
					content.append(line);
					content.append("\n");
				}
			}
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		// 设置统计数据
		TableItem statItem = new TableItem(statTable, SWT.NONE);

		statItem.setText(new String[]{String.valueOf(totalCount),
				String.valueOf(ipCount),
				String.valueOf(icmpCount),
				String.valueOf(httpCount),
				String.valueOf(udpCount),
				String.valueOf(tcpCount),
				String.valueOf(arpCount)});
	}

	public static void save(List<String> packs) {
		Shell saveShell = new Shell();
		FileDialog dialog = new FileDialog(saveShell, SWT.SAVE);
		String fileName = dialog.open(); // 获得保存的文件名
		File outPutFile = null;
		if (fileName != null) {
			outPutFile = new File(fileName);
		} else {
			return;
		}
		if (!outPutFile.exists()) {
			try {
				outPutFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		PrintWriter pw;
		try {
			pw = new PrintWriter(outPutFile);
			for (String pack : packs) {
				pw.println(pack);
			}
			pw.flush();
			pw.close();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		MessageBox warning = new MessageBox(saveShell);
		warning.setMessage("文件保存成功");
		warning.open();

	}

	// public static void main(String[] args) {
	// List<PcapIf> alldevs = new ArrayList<PcapIf>();
	// StringBuilder errbuf = new StringBuilder();
	//
	// /***************************************************************************
	// * 获取设备列表
	// **************************************************************************/
	// int r = Pcap.findAllDevs(alldevs, errbuf);
	// if (r == Pcap.NOT_OK || alldevs.isEmpty()) {
	// System.err.printf("Can't read list of devices, error is %s\n",
	// errbuf.toString());
	// return;
	// }
	// PcapIf device = alldevs.get(0); // We know we have atleast 1 device
	//
	// /***************************************************************************
	// * 打开选中设备
	// **************************************************************************/
	// int snaplen = 64 * 1024; // Capture all packets, no trucation
	// int flags = Pcap.MODE_PROMISCUOUS; // capture all packets
	// int timeout = 10 * 1000; // 10 seconds in millis
	// Pcap pcap = Pcap.openLive(device.getName(), snaplen, flags, timeout,
	// errbuf);
	// if (pcap == null) {
	// System.err.printf("Error while opening device for capture: %s\n",
	// errbuf.toString());
	// return;
	// }
	//
	// /***************************************************************************
	// * 创建一个和pcap关联的pcapdumper
	// ***************************************************************************/
	// String ofile = "D:/tmp-capture-file.cap";
	// final PcapDumper dumper = pcap.dumpOpen(ofile); // output file
	//
	// /***************************************************************************
	// * 创建一个接收数据包的handler并且告诉dumper将数据包写入输出文件中
	// **************************************************************************/
	// PcapHandler<PcapDumper> dumpHandler = new PcapHandler<PcapDumper>() {
	//
	//
	// @Override
	// public void nextPacket(PcapDumper arg0, long seconds, int useconds, int
	// caplen,
	// int len, ByteBuffer buffer) {
	// dumper.dump(seconds, useconds, caplen, len, buffer);
	//
	// }
	// };
	//
	// /***************************************************************************
	// * 告诉loop捕获10个数据包，并传入我们第三步创建的dumper
	// **************************************************************************/
	// pcap.loop(10, dumpHandler, dumper);
	//
	// File file = new File(ofile);
	// System.out.printf("%s file has %d bytes in it!\n", ofile, file.length());
	//
	//
	// /***************************************************************************
	// * 最后dumper和handler都要关闭
	// **************************************************************************/
	// dumper.close(); // 如果dumper不关闭，那么输出文件是没法删除的
	// pcap.close();
	//
	// // if (file.exists()) {
	// // file.delete();
	// // // 删除输出文件，当然你也可以不删除，使用wireshark打开可以看到更多的信息
	// // }
	// }

}
