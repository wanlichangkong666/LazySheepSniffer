package logic;

import java.awt.Font;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.experimental.chart.swt.ChartComposite;

import util.BeautifulPieChart;

public class StatTrans {
	private static DefaultPieDataset createDataset(double tcp,double udp) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("TCP", tcp);
        dataset.setValue("UDP", udp);
        return dataset;        
    }
    
    /**
     * Creates a chart.
     * 
     * @param dataset  the dataset.
     * 
     * @return A chart.
     */
    
    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void stat(double tcp,double udp) 
    {
    	DefaultPieDataset dataset = createDataset(tcp,udp);
        JFreeChart chart = BeautifulPieChart.CreatePieChart(dataset, "传输层流量统计饼图", true);
        Shell shell = new Shell();
        shell.setSize(600, 400);
        shell.setLayout(new FillLayout());
        shell.setText("传输层流量统计");
        final ChartComposite frame = new ChartComposite(shell, SWT.NONE, chart, true);
        //frame.setDisplayToolTips(false);
        frame.pack();
        shell.open();

    }
}
