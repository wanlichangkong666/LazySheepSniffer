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

		// 创建工具栏对象
		final ToolBar toolBar = new ToolBar(tool, SWT.FLAT);
		//toolBar.setSize(0, 50);
		// 文件菜单
		final ToolItem fileItem = new ToolItem(toolBar, SWT.PUSH);
		fileItem.setText("文件");
		fileItem.setToolTipText("文件");
		final Menu fileMenu = new Menu(shell, SWT.POP_UP);
		//文件打开监听事件是table下面
		MenuItem openItem = new MenuItem(fileMenu, SWT.PUSH);
		openItem.setText("打开");
	
		//文件保存监听事件在table下面
		MenuItem saveItem = new MenuItem(fileMenu, SWT.PUSH);
		saveItem.setText("保存");
		
		fileItem.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				// if(event.detail == SWT.ARROW){
				// 获得帮助按钮所在的坐标位置和大小
				Rectangle rec = fileItem.getBounds();
				// 计算出菜单出现的起始位置
				Point point = new Point(rec.x, rec.y + rec.height);
				// 将该店转化为与屏幕相对的点
				point = toolBar.toDisplay(point);
				// 设置菜单的位置
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
				table.setHeaderVisible(true); // 表头显示
				table.setLinesVisible(true); // 表格线显示

				TableLayout tLayout = new TableLayout();
				table.setLayout(tLayout);
		Composite packetComposite = new Composite(shell , SWT.BORDER);
		packetComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL , true,false));
		packetComposite.setLayout(new GridLayout(2 , false));
		//包详细数据显示
		final Text packContentText = new Text(packetComposite, SWT.MULTI| SWT.H_SCROLL 
		          | SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL);
		GridData gd_packContentText = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		
		packContentText.setLayoutData(gd_packContentText);
		//GridData gd_packContentText = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_packContentText.heightHint = 119;
		gd_packContentText.widthHint = 600;
		packContentText.setLayoutData(gd_packContentText);
		//packContentText.setVisible(false);
		//统计信息
		TableViewer statTv = new TableViewer(packetComposite, 
				SWT.BORDER | SWT.FULL_SELECTION);
				
				final Table statTable = statTv.getTable();
				GridData statGrid = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
				statGrid.widthHint = 300;
				statGrid.heightHint = 30;
				statTable.setLayoutData(statGrid);
				statTable.setHeaderVisible(true); // 表头显示
				//statTable.setLinesVisible(true); // 表格线显示
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
				
				//文件打开操作
				openItem.addListener(SWT.Selection, new Listener() {
					
					@Override
					public void handleEvent(Event event) {
						TableItem tableItems[] = table.getItems();//得到所有的tableItem
			            for(int i = 0; i<tableItems.length; i++)
			            {
			                tableItems[i].dispose();//释放
			            }
			            TableItem statTableItems[] = statTable.getItems();//得到所有的tableItem
			            for(int i = 0; i<statTableItems.length; i++)
			            {
			                statTableItems[i].dispose();//释放
			            }
			            CapturePacks.reset();
			            CapturePacks.contents.clear();
			            packContentText.setText("");
						FileSaveAndOpen.open(table,statTable,packContentText);
						
					}
				});
				//文件保存操作
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
		// 捕获菜单
		final ToolItem captureItem = new ToolItem(toolBar, SWT.PUSH);
		captureItem.setText("捕获");
		captureItem.setToolTipText("捕获");
		final Menu captureMenu = new Menu(shell, SWT.POP_UP);
		MenuItem chooseItem = new MenuItem(captureMenu, SWT.PUSH);
		chooseItem.setText("选择网卡");
		chooseItem.addListener(SWT.Selection, new InterfaceListener());
		// new MenuItem(captureMenu, SWT.SEPARATOR);
		MenuItem startItem = new MenuItem(captureMenu, SWT.PUSH);
		startItem.setText("开始捕获");
		startItem.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				TableItem tableItems[] = table.getItems();//得到所有的tableItem
	            for(int i = 0; i<tableItems.length; i++)
	            {
	                tableItems[i].dispose();//释放
	            }
	            TableItem statTableItems[] = statTable.getItems();//得到所有的tableItem
	            for(int i = 0; i<statTableItems.length; i++)
	            {
	                statTableItems[i].dispose();//释放
	            }
	            CapturePacks.reset();
	            CapturePacks.contents.clear();
	            packContentText.setText("");
				capturePacks = new CapturePacks();
				capturePacks.startCap(Interface.chooseInterfaceId, table,packContentText);

			}
		});
		MenuItem stopItem = new MenuItem(captureMenu, SWT.PUSH);
		stopItem.setText("停止捕获");
		stopItem.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				
				capturePacks.stopCap(statTable);
			}
		});
		MenuItem clearItem = new MenuItem(captureMenu, SWT.PUSH);
		clearItem.setText("清空数据");
		clearItem.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				  TableItem tableItems[] = table.getItems();//得到所有的tableItem
		            for(int i = 0; i<tableItems.length; i++)
		            {
		                tableItems[i].dispose();//释放
		            }
		            TableItem statTableItems[] = statTable.getItems();//得到所有的tableItem
		            for(int i = 0; i<statTableItems.length; i++)
		            {
		                statTableItems[i].dispose();//释放
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
				// 获得帮助按钮所在的坐标位置和大小
				Rectangle rec = captureItem.getBounds();
				// 计算出菜单出现的起始位置
				Point point = new Point(rec.x, rec.y + rec.height);
				// 将该店转化为与屏幕相对的点
				point = toolBar.toDisplay(point);
				// 设置菜单的位置
				captureMenu.setLocation(point.x, point.y);
				captureMenu.setVisible(true);
			}
			// }
		});

		// 过滤器菜单
		final ToolItem filterItem = new ToolItem(toolBar, SWT.NONE);
		filterItem.setText("过滤器");
		filterItem.setToolTipText("过滤器");
		filterItem.addListener(SWT.Selection,new FilterListener());
		//统计菜单
		final ToolItem statItem = new ToolItem(toolBar, SWT.NONE);
		statItem.setText("统计图");
		statItem.setToolTipText("统计图");
		final Menu statMenu = new Menu(shell, SWT.POP_UP);
		MenuItem statNetItem = new MenuItem(statMenu, SWT.PUSH);
		statNetItem.setText("网络层流量统计");
		statNetItem.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				try {
					int netTotal = CapturePacks.ipCount+CapturePacks.icmpCount+CapturePacks.arpCount;
					if (netTotal==0) {
						MessageBox warning = new MessageBox(shell,SWT.OK);
						warning.setMessage("请先进行捕获");
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
		statTransItem.setText("传输层流量统计");
		statTransItem.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				
				try {
					int transTotal = CapturePacks.tcpCount+CapturePacks.udpCount;
					if (transTotal==0) {
						MessageBox warning = new MessageBox(shell,SWT.OK);
						warning.setMessage("请先进行捕获");
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
				// 获得帮助按钮所在的坐标位置和大小
				Rectangle rec = statItem.getBounds();
				// 计算出菜单出现的起始位置
				Point point = new Point(rec.x, rec.y + rec.height);
				// 将该店转化为与屏幕相对的点
				point = toolBar.toDisplay(point);
				// 设置菜单的位置
				statMenu.setLocation(point.x, point.y);
				statMenu.setVisible(true);
			}
			// }
		});
		//关于菜单
		final ToolItem aboutItem = new ToolItem(toolBar, SWT.NONE);
		aboutItem.setText("关于");
		aboutItem.setToolTipText("关于");
		aboutItem.addListener(SWT.Selection, new AboutListener());
		
		toolBar.pack();
		// Text content = new Text(shell, SWT.MULTI);
		// content.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		// 数据包摘要区
		// Text id = new Text(shell,0) ;
		// id.setText("序号");
		//
		// Text srcIP = new Text(shell,0) ;
		// srcIP.setText("源IP地址");
		// Text dstIP = new Text(shell,0) ;
		// dstIP.setText("目的IP地址");
		//
		// Text srcMac = new Text(shell,0);
		// srcMac.setText("源MAC地址");
		//
		// Text dstMac = new Text(shell,0);
		// dstMac.setText("目的MAC地址");
		//
		// Text length = new Text(shell,0);
		// length.setText("长度");
		//
		// Text prot = new Text(shell,0);
		// prot.setText("协议");
		//
		// Text time = new Text(shell,0);
		// time.setText("时间");
//		Composite packetsDisplay = new Composite(shell, SWT.NONE);
//		//packetsDisplay.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false));
//		GridData gridData = new GridData();
//		gridData.grabExcessHorizontalSpace = true;
//		gridData.horizontalAlignment = GridData.FILL;
//		gridData.grabExcessVerticalSpace = true;
//		gridData.verticalAlignment = GridData.FILL;
//		packetsDisplay.setLayoutData(gridData);

		tLayout.addColumnData(new ColumnWeightData(15)); 
		new TableColumn(table, SWT.NONE).setText("序号");
		tLayout.addColumnData(new ColumnWeightData(15));
		new TableColumn(table, SWT.NONE).setText("时间");
		tLayout.addColumnData(new ColumnWeightData(15));
		new TableColumn(table, SWT.NONE).setText("协议");
		tLayout.addColumnData(new ColumnWeightData(15));
		new TableColumn(table, SWT.NONE).setText("包长");
		tLayout.addColumnData(new ColumnWeightData(25));
		new TableColumn(table, SWT.NONE).setText("源IP地址");
		tLayout.addColumnData(new ColumnWeightData(25));
		new TableColumn(table, SWT.NONE).setText("目的IP地址");
		tLayout.addColumnData(new ColumnWeightData(25));
		new TableColumn(table, SWT.NONE).setText("源MAC地址");
		tLayout.addColumnData(new ColumnWeightData(25));
		new TableColumn(table, SWT.NONE).setText("目的MAC地址");
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
