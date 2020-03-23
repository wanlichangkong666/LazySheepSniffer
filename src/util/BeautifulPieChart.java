package util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;

public class BeautifulPieChart {

	public static JFreeChart CreatePieChart(DefaultPieDataset dataset, String title,boolean is3D)
	{
		JFreeChart chart = null;
		if (is3D) {
			chart = ChartFactory.createPieChart3D(title, dataset, true,true,true);
		}
		else {
			chart = ChartFactory.createPieChart(title,dataset,true,true,true);
		} 
        chart.setTitle(new TextTitle(title, new Font("����",Font.PLAIN, 16)));  
        // ����ͼ��������==Ϊ�˷�ֹ�������룺������������  
        chart.getLegend().setItemFont(new Font("����", Font.PLAIN, 13));  
        // ��ȡ��ͼ��Plot����(ʵ��ͼ��)  
        PiePlot plot = (PiePlot) chart.getPlot();  
        plot.setOutlinePaint(Color.BLACK);
        plot.setShadowPaint(Color.WHITE);
       
        // ͼ�α߿���ɫ 
        plot.setBaseSectionOutlinePaint(Color.WHITE);  
        // ͼ�α߿��ϸ 
        plot.setBaseSectionOutlineStroke(new BasicStroke(0.0f));  
        // ���ñ�״ͼ�Ļ��Ʒ��򣬿��԰�˳ʱ�뷽����ƣ�Ҳ���԰���ʱ�뷽�����  
        plot.setDirection(Rotation.ANTICLOCKWISE);  
        // ���û��ƽǶ�(ͼ����ת�Ƕ�)  
        plot.setStartAngle(70);   
        // ���ñ���ɫ͸����  
        plot.setBackgroundAlpha(0.0F);   
        plot.setForegroundAlpha(0.8F);   
        plot.setLabelFont(new Font("����", Font.PLAIN, 12));
        plot.setLabelBackgroundPaint(null);//��ǩ������ɫ
        plot.setLabelOutlinePaint(null);//��ǩ�߿���ɫ
        plot.setLabelShadowPaint(null);//��ǩ��Ӱ��ɫ 
//        if(is3D)  
//            plot.setExplodePercent(dataset.getKey(2), 0.1D);   
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(  
                "{0}:{1}\r\n({2})", NumberFormat.getNumberInstance(),  
                new DecimalFormat("0.00%")));   
        plot.setCircular(true);   
        plot.setNoDataMessage("�Ҳ�����������...");   
        plot.setToolTipGenerator(new StandardPieToolTipGenerator());   
        return chart;  
	}


}
