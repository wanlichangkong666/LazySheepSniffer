package apperance.listeners;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class AboutListener implements Listener {

	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public void handleEvent(Event event) {
		Shell aboutShell = new Shell();
		aboutShell.setLayout(new GridLayout());
		aboutShell.setSize(400,300);
		Label aboutLabel = new Label(aboutShell, 0);
		aboutLabel.setLayoutData(new GridData(SWT.CENTER,SWT.CENTER,true,true));
		aboutLabel.setText("◊˜’ﬂ£∫ÕıœË”Ó\n” œ‰£∫511649091@qq.com");
		aboutShell.open();
	}

}
