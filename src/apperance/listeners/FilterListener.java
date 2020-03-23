package apperance.listeners;

import javax.swing.JLabel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import apperance.APPMain;

public class FilterListener implements Listener {

	/**
	 * @wbp.parser.entryPoint
	 */
	
	@Override
	public void handleEvent(Event event) {
		final Shell filterShell = new Shell();
		filterShell.setLayout(new GridLayout());
		filterShell.setSize(300,400);
		final Button IPCheck  =new Button(filterShell, SWT.CHECK);
		IPCheck.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
		IPCheck.setText("IP");
		IPCheck.setSelection(APPMain.wantIP);
		final Button TCPCheck  =new Button(filterShell, SWT.CHECK);
		TCPCheck.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
		TCPCheck.setText("TCP");
		TCPCheck.setSelection(APPMain.wantTCP);
		final Button ARPCheck  =new Button(filterShell, SWT.CHECK);
		ARPCheck.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
		ARPCheck.setText("ARP");
		ARPCheck.setSelection(APPMain.wantARP);
		final Button UDPCheck  =new Button(filterShell, SWT.CHECK);
		UDPCheck.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
		UDPCheck.setText("UDP");
		UDPCheck.setSelection(APPMain.wantUDP);
		final Button ICMPCheck  =new Button(filterShell, SWT.CHECK);
		ICMPCheck.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
		ICMPCheck.setText("ICMP");
		ICMPCheck.setSelection(APPMain.wantICMP);
		final Button HTTPCheck  =new Button(filterShell, SWT.CHECK);
		HTTPCheck.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
		HTTPCheck.setText("HTTP");
		HTTPCheck.setSelection(APPMain.wantHTTP);
		Composite composite = new Composite(filterShell , SWT.BORDER);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL , true,false));
		//设置gridlayout的num为2
		composite.setLayout(new GridLayout(2 , false));
		Button okButton = new Button(composite, SWT.PUSH);
		okButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL , true,true));
		okButton.setText("确定");
		okButton.addListener(SWT.MouseUp, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				APPMain.wantIP = IPCheck.getSelection();
				APPMain.wantICMP = ICMPCheck.getSelection();
				APPMain.wantHTTP = HTTPCheck.getSelection();
				APPMain.wantARP = ARPCheck.getSelection();
				APPMain.wantUDP = UDPCheck.getSelection();
				APPMain.wantTCP = TCPCheck.getSelection();
				//System.out.println(""+APPMain.wantICMP+APPMain.wantARP+APPMain.wantHTTP+APPMain.wantIP+APPMain.wantTCP+APPMain.wantUDP);
				filterShell.dispose();
				
			}
		});
		
		Button cancelButton = new Button(composite, SWT.PUSH);
		cancelButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL , true,true));
		cancelButton.setText("取消");
		cancelButton.addListener(SWT.MouseUp, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				filterShell.dispose();
				
			}
		});
		filterShell.open();
		
	}

}
