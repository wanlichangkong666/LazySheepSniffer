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
        chart.setTitle(new TextTitle(title, new Font("黑体",Font.PLAIN, 16)));  
        // 设置图例的字体==为了防止中文乱码：必须设置字体  
        chart.getLegend().setItemFont(new Font("黑体", Font.PLAIN, 13));  
        // 获取饼图的Plot对象(实际图表)  
        PiePlot plot = (PiePlot) chart.getPlot();  
        plot.setOutlinePaint(Color.BLACK);
        plot.setShadowPaint(Color.WHITE);
       
        // 图形边框颜色 
        plot.setBaseSectionOutlinePaint(Color.WHITE);  
        // 图形边框粗细 
        plot.setBaseSectionOutlineStroke(new BasicStroke(0.0f));  
        // 设置饼状图的绘制方向，可以按顺时针方向绘制，也可以按逆时针方向绘制  
        plot.setDirection(Rotation.ANTICLOCKWISE);  
        // 设置绘制角度(图形旋转角度)  
        plot.setStartAngle(70);   
        // 设置背景色透明度  
        plot.setBackgroundAlpha(0.0F);   
        plot.setForegroundAlpha(0.8F);   
        plot.setLabelFont(new Font("宋体", Font.PLAIN, 12));
        plot.setLabelBackgroundPaint(null);//标签背景颜色
        plot.setLabelOutlinePaint(null);//标签边框颜色
        plot.setLabelShadowPaint(null);//标签阴影颜色 
//        if(is3D)  
//            plot.setExplodePercent(dataset.getKey(2), 0.1D);   
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(  
                "{0}:{1}\r\n({2})", NumberFormat.getNumberInstance(),  
                new DecimalFormat("0.00%")));   
        plot.setCircular(true);   
        plot.setNoDataMessage("找不到可用数据...");   
        plot.setToolTipGenerator(new StandardPieToolTipGenerator());   
        return chart;  
	}


}
