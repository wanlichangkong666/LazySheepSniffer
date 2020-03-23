package apperance.listeners;

import java.awt.event.MouseAdapter;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.SimpleWildcardTester;
import org.jnetpcap.PcapIf;

import logic.Interface;

public class InterfaceListener implements Listener {
	java.util.List<PcapIf> interfaces = new Interface().getInterfaces();
	
	@Override
	public void handleEvent(Event event) {
		// TODO 自动生成的方法存根

//		Display display = Display.getDefault();
	
		final Shell deviceShell = new Shell(SWT.SHELL_TRIM);
		
//		deviceShell.setLayout(new GridLayout());
		deviceShell.setLayout(new FillLayout(SWT.HORIZONTAL));
		//显示所有网卡的List
		final List interfacesList = new List(deviceShell,SWT.BORDER);
		interfacesList.setBounds(deviceShell.getBounds());
		interfacesList.setSize(630,441);
		deviceShell.setSize(interfacesList.getSize());
		for ( PcapIf card : interfaces) {
			interfacesList.add(card.getDescription()+" : "+card.getAddresses().toString().split("]")[0].split("\\[")[3]);
		}
		deviceShell.open();
		interfacesList.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent arg0) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				// TODO 自动生成的方法存根
				
			}
			
			//双击选择抓包网卡，将网卡序号传给Interface的chooseInterfaceId
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				int deviceId = interfacesList.getSelectionIndex();
				Interface.chooseInterfaceId = deviceId;
				System.out.println(deviceId);
				deviceShell.close();
				
			}

			
		});
		
		
	}

}
