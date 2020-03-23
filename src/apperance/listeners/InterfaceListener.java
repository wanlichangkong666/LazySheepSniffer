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
		// TODO �Զ����ɵķ������

//		Display display = Display.getDefault();
	
		final Shell deviceShell = new Shell(SWT.SHELL_TRIM);
		
//		deviceShell.setLayout(new GridLayout());
		deviceShell.setLayout(new FillLayout(SWT.HORIZONTAL));
		//��ʾ����������List
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
				// TODO �Զ����ɵķ������
				
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				// TODO �Զ����ɵķ������
				
			}
			
			//˫��ѡ��ץ����������������Ŵ���Interface��chooseInterfaceId
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
