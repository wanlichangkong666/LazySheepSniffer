package apperance;



import java.util.ArrayList;
import java.util.List;

import jdk.internal.org.objectweb.asm.util.CheckAnnotationAdapter;
import logic.CapturePacks;
import logic.FileSaveAndOpen;
import logic.Interface;
import logic.StatNet;
import logic.StatTrans;

import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

import apperance.listeners.AboutListener;
import apperance.listeners.FilterListener;
import apperance.listeners.InterfaceListener;

import org.eclipse.swt.custom.ViewForm;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.swt.widgets.Label;

import com.sun.org.apache.bcel.internal.generic.NEW;

import sun.print.resources.serviceui;

public class APPMain {
	
	public static boolean wantIP = true;
	public static boolean wantHTTP = true;
	public static boolean wantICMP = true;
	public static boolean wantARP = true;
	public static boolean wantTCP = true;
	public static boolean wantUDP = true;
	private static  CapturePacks capturePacks = null;
	

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
//		createContents();
		Display display = Display.getDefault();
//		final APPMain shell = new APPMain(display);
		final Shell shell = new Shell();
		//shell.setSize(630, 460);
		shell.setText("\u61D2\u7F8A\u7F8A\u55C5\u63A2\u5668");
		shell.setLayout(new GridLayout());
		//shell.setLayout(new FillLayout(1));
		Composite tool = new Composite(shell, SWT.NONE);
		//tool.setSize(0,20);
		tool.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false));

		// ��������������
		final ToolBar toolBar = new ToolBar(tool, SWT.FLAT);
		//toolBar.setSize(0, 50);
		// �ļ��˵�
		final ToolItem fileItem = new ToolItem(toolBar, SWT.PUSH);
		fileItem.setText("�ļ�");
		fileItem.setToolTipText("�ļ�");
		final Menu fileMenu = new Menu(shell, SWT.POP_UP);
		//�ļ��򿪼����¼���table����
		MenuItem openItem = new MenuItem(fileMenu, SWT.PUSH);
		openItem.setText("��");
	
		//�ļ���������¼���table����
		MenuItem saveItem = new MenuItem(fileMenu, SWT.PUSH);
		saveItem.setText("����");
		
		fileItem.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				// if(event.detail == SWT.ARROW){
				// ��ð�����ť���ڵ�����λ�úʹ�С
				Rectangle rec = fileItem.getBounds();
				// ������˵����ֵ���ʼλ��
				Point point = new Point(rec.x, rec.y + rec.height);
				// ���õ�ת��Ϊ����Ļ��Եĵ�
				point = toolBar.toDisplay(point);
				// ���ò˵���λ��
				fileMenu.setLocation(point.x, point.y);
				fileMenu.setVisible(true);
			}
			// }
		});
		TableViewer tv = new TableViewer(shell, SWT.MULTI| SWT.H_SCROLL 
		          | SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL);
				
				final Table table = tv.getTable();
				GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
				//GridData gd_table = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_table.widthHint = 607;

				gd_table.heightHint = 234;
				table.setLayoutData(gd_table);
				//table.setBounds(toolBar.getBounds());
				table.setHeaderVisible(true); // ��ͷ��ʾ
				table.setLinesVisible(true); // �������ʾ

				TableLayout tLayout = new TableLayout();
				table.setLayout(tLayout);
		Composite packetComposite = new Composite(shell , SWT.BORDER);
		packetComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL , true,false));
		packetComposite.setLayout(new GridLayout(2 , false));
		//����ϸ������ʾ
		final Text packContentText = new Text(packetComposite, SWT.MULTI| SWT.H_SCROLL 
		          | SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL);
		GridData gd_packContentText = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		
		packContentText.setLayoutData(gd_packContentText);
		//GridData gd_packContentText = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_packContentText.heightHint = 119;
		gd_packContentText.widthHint = 600;
		packContentText.setLayoutData(gd_packContentText);
		//packContentText.setVisible(false);
		//ͳ����Ϣ
		TableViewer statTv = new TableViewer(packetComposite, 
				SWT.BORDER | SWT.FULL_SELECTION);
				
				final Table statTable = statTv.getTable();
				GridData statGrid = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
				statGrid.widthHint = 300;
				statGrid.heightHint = 30;
				statTable.setLayoutData(statGrid);
				statTable.setHeaderVisible(true); // ��ͷ��ʾ
				//statTable.setLinesVisible(true); // �������ʾ
				TableLayout statLayout = new TableLayout();
				statTable.setLayout(statLayout);
				//GridData gd_table = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				statLayout.addColumnData(new ColumnWeightData(8)); 
				new TableColumn(statTable, SWT.NONE).setText("total");
				statLayout.addColumnData(new ColumnWeightData(8));
				new TableColumn(statTable, SWT.NONE).setText("ip");
				statLayout.addColumnData(new ColumnWeightData(8));
				new TableColumn(statTable, SWT.NONE).setText("icmp");
				statLayout.addColumnData(new ColumnWeightData(8));
				new TableColumn(statTable, SWT.NONE).setText("http");
				statLayout.addColumnData(new ColumnWeightData(8));
				new TableColumn(statTable, SWT.NONE).setText("udp");
				statLayout.addColumnData(new ColumnWeightData(8));
				new TableColumn(statTable, SWT.NONE).setText("tcp");
				statLayout.addColumnData(new ColumnWeightData(8));
				new TableColumn(statTable, SWT.NONE).setText("arp");
				//TableItem item1 = new TableItem(statTable, SWT.PUSH);
				//item1.setText(new String[]{"1","2","2","2","2","2","2"});
				
				//�ļ��򿪲���
				openItem.addListener(SWT.Selection, new Listener() {
					
					@Override
					public void handleEvent(Event event) {
						TableItem tableItems[] = table.getItems();//�õ����е�tableItem
			            for(int i = 0; i<tableItems.length; i++)
			            {
			                tableItems[i].dispose();//�ͷ�
			            }
			            TableItem statTableItems[] = statTable.getItems();//�õ����е�tableItem
			            for(int i = 0; i<statTableItems.length; i++)
			            {
			                statTableItems[i].dispose();//�ͷ�
			            }
			            CapturePacks.reset();
			            CapturePacks.contents.clear();
			            packContentText.setText("");
						FileSaveAndOpen.open(table,statTable,packContentText);
						
					}
				});
				//�ļ��������
				saveItem.addListener(SWT.Selection, new Listener() {
					
					@Override
					public void handleEvent(Event event) {
						List<String> packsList = new ArrayList<String>();
						 TableItem [] items = table.getItems();
						 
						for ( int i=0;i<items.length;i++)
						{
							 StringBuilder packString = new StringBuilder();
							 for (int j=0;j<table.getColumnCount();j++)
							 {
								 System.out.println(items[j].getText(j));
								 packString.append(items[i].getText(j)+",");
							 }
							 packString.append("\n");
							 packString.append(CapturePacks.contents.get(i));
							 packsList.add(packString.toString());
						}
						FileSaveAndOpen.save(packsList);
						
					}
				});
		// ����˵�
		final ToolItem captureItem = new ToolItem(toolBar, SWT.PUSH);
		captureItem.setText("����");
		captureItem.setToolTipText("����");
		final Menu captureMenu = new Menu(shell, SWT.POP_UP);
		MenuItem chooseItem = new MenuItem(captureMenu, SWT.PUSH);
		chooseItem.setText("ѡ������");
		chooseItem.addListener(SWT.Selection, new InterfaceListener());
		// new MenuItem(captureMenu, SWT.SEPARATOR);
		MenuItem startItem = new MenuItem(captureMenu, SWT.PUSH);
		startItem.setText("��ʼ����");
		startItem.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				TableItem tableItems[] = table.getItems();//�õ����е�tableItem
	            for(int i = 0; i<tableItems.length; i++)
	            {
	                tableItems[i].dispose();//�ͷ�
	            }
	            TableItem statTableItems[] = statTable.getItems();//�õ����е�tableItem
	            for(int i = 0; i<statTableItems.length; i++)
	            {
	                statTableItems[i].dispose();//�ͷ�
	            }
	            CapturePacks.reset();
	            CapturePacks.contents.clear();
	            packContentText.setText("");
				capturePacks = new CapturePacks();
				capturePacks.startCap(Interface.chooseInterfaceId, table,packContentText);

			}
		});
		MenuItem stopItem = new MenuItem(captureMenu, SWT.PUSH);
		stopItem.setText("ֹͣ����");
		stopItem.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				
				capturePacks.stopCap(statTable);
			}
		});
		MenuItem clearItem = new MenuItem(captureMenu, SWT.PUSH);
		clearItem.setText("�������");
		clearItem.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				  TableItem tableItems[] = table.getItems();//�õ����е�tableItem
		            for(int i = 0; i<tableItems.length; i++)
		            {
		                tableItems[i].dispose();//�ͷ�
		            }
		            TableItem statTableItems[] = statTable.getItems();//�õ����е�tableItem
		            for(int i = 0; i<statTableItems.length; i++)
		            {
		                statTableItems[i].dispose();//�ͷ�
		            }
		            packContentText.setText("");
		            CapturePacks.reset();
		            CapturePacks.contents.clear();
				
			}
		});
		
		captureItem.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				// if(event.detail == SWT.ARROW){
				// ��ð�����ť���ڵ�����λ�úʹ�С
				Rectangle rec = captureItem.getBounds();
				// ������˵����ֵ���ʼλ��
				Point point = new Point(rec.x, rec.y + rec.height);
				// ���õ�ת��Ϊ����Ļ��Եĵ�
				point = toolBar.toDisplay(point);
				// ���ò˵���λ��
				captureMenu.setLocation(point.x, point.y);
				captureMenu.setVisible(true);
			}
			// }
		});

		// �������˵�
		final ToolItem filterItem = new ToolItem(toolBar, SWT.NONE);
		filterItem.setText("������");
		filterItem.setToolTipText("������");
		filterItem.addListener(SWT.Selection,new FilterListener());
		//ͳ�Ʋ˵�
		final ToolItem statItem = new ToolItem(toolBar, SWT.NONE);
		statItem.setText("ͳ��ͼ");
		statItem.setToolTipText("ͳ��ͼ");
		final Menu statMenu = new Menu(shell, SWT.POP_UP);
		MenuItem statNetItem = new MenuItem(statMenu, SWT.PUSH);
		statNetItem.setText("���������ͳ��");
		statNetItem.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				try {
					int netTotal = CapturePacks.ipCount+CapturePacks.icmpCount+CapturePacks.arpCount;
					if (netTotal==0) {
						MessageBox warning = new MessageBox(shell,SWT.OK);
						warning.setMessage("���Ƚ��в���");
						warning.open();
						return;
					}
					double ip = CapturePacks.ipCount*1.0/netTotal;
					double icmp = CapturePacks.icmpCount*1.0/netTotal;
					double arp = CapturePacks.arpCount*1.0/netTotal;
					StatNet.stat(ip, icmp, arp);
//					System.out.println(ip);
//					System.out.println(icmp);
//					System.out.println(arp);
				} catch (Exception e) {
					return;
				}
			}
		});
		MenuItem statTransItem = new MenuItem(statMenu, SWT.PUSH);
		statTransItem.setText("���������ͳ��");
		statTransItem.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				
				try {
					int transTotal = CapturePacks.tcpCount+CapturePacks.udpCount;
					if (transTotal==0) {
						MessageBox warning = new MessageBox(shell,SWT.OK);
						warning.setMessage("���Ƚ��в���");
						warning.open();
						return;
					}
					//System.out.println(transTotal);
					double tcp = CapturePacks.tcpCount*1.0/transTotal;
					//System.out.println(tcp);
					double udp = CapturePacks.udpCount*1.0/transTotal;
					StatTrans.stat(tcp,udp);
//					System.out.println(tcp);
//					System.out.println(udp);
				} catch (Exception e) {
					return;
				}
			}
		});
		statItem.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				// if(event.detail == SWT.ARROW){
				// ��ð�����ť���ڵ�����λ�úʹ�С
				Rectangle rec = statItem.getBounds();
				// ������˵����ֵ���ʼλ��
				Point point = new Point(rec.x, rec.y + rec.height);
				// ���õ�ת��Ϊ����Ļ��Եĵ�
				point = toolBar.toDisplay(point);
				// ���ò˵���λ��
				statMenu.setLocation(point.x, point.y);
				statMenu.setVisible(true);
			}
			// }
		});
		//���ڲ˵�
		final ToolItem aboutItem = new ToolItem(toolBar, SWT.NONE);
		aboutItem.setText("����");
		aboutItem.setToolTipText("����");
		aboutItem.addListener(SWT.Selection, new AboutListener());
		
		toolBar.pack();
		// Text content = new Text(shell, SWT.MULTI);
		// content.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		// ���ݰ�ժҪ��
		// Text id = new Text(shell,0) ;
		// id.setText("���");
		//
		// Text srcIP = new Text(shell,0) ;
		// srcIP.setText("ԴIP��ַ");
		// Text dstIP = new Text(shell,0) ;
		// dstIP.setText("Ŀ��IP��ַ");
		//
		// Text srcMac = new Text(shell,0);
		// srcMac.setText("ԴMAC��ַ");
		//
		// Text dstMac = new Text(shell,0);
		// dstMac.setText("Ŀ��MAC��ַ");
		//
		// Text length = new Text(shell,0);
		// length.setText("����");
		//
		// Text prot = new Text(shell,0);
		// prot.setText("Э��");
		//
		// Text time = new Text(shell,0);
		// time.setText("ʱ��");
//		Composite packetsDisplay = new Composite(shell, SWT.NONE);
//		//packetsDisplay.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false));
//		GridData gridData = new GridData();
//		gridData.grabExcessHorizontalSpace = true;
//		gridData.horizontalAlignment = GridData.FILL;
//		gridData.grabExcessVerticalSpace = true;
//		gridData.verticalAlignment = GridData.FILL;
//		packetsDisplay.setLayoutData(gridData);

		tLayout.addColumnData(new ColumnWeightData(15)); 
		new TableColumn(table, SWT.NONE).setText("���");
		tLayout.addColumnData(new ColumnWeightData(15));
		new TableColumn(table, SWT.NONE).setText("ʱ��");
		tLayout.addColumnData(new ColumnWeightData(15));
		new TableColumn(table, SWT.NONE).setText("Э��");
		tLayout.addColumnData(new ColumnWeightData(15));
		new TableColumn(table, SWT.NONE).setText("����");
		tLayout.addColumnData(new ColumnWeightData(25));
		new TableColumn(table, SWT.NONE).setText("ԴIP��ַ");
		tLayout.addColumnData(new ColumnWeightData(25));
		new TableColumn(table, SWT.NONE).setText("Ŀ��IP��ַ");
		tLayout.addColumnData(new ColumnWeightData(25));
		new TableColumn(table, SWT.NONE).setText("ԴMAC��ַ");
		tLayout.addColumnData(new ColumnWeightData(25));
		new TableColumn(table, SWT.NONE).setText("Ŀ��MAC��ַ");
		table.addListener(SWT.MouseDoubleClick, new Listener() {

			@Override
			public void handleEvent(Event event) {
				final int row = table.getSelectionIndex();
				
						packContentText.setText(CapturePacks.contents.get(row));
						packContentText.setVisible(true);
					
			

			}
		});
		shell.open();


		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

	}


}
