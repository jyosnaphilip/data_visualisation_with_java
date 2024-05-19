package com.example.charting;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.awt.BasicStroke;
import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;  
  
import org.jfree.chart.ChartFactory;  
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
// @SuppressWarnings("unused")
public class LineChart extends JFrame {

    public LineChart(String title,TreeMap<String[],Float> treemap,String x_name,String y_name){
        super(title);
        //create dataset
        CategoryDataset data1=createDataset(treemap);
        //create chart
        JFreeChart chart = ChartFactory.createLineChart(title,  x_name,y_name,data1,PlotOrientation.VERTICAL,true,true,true);  
        //x_name is x axis label,y_name-y_axis label
       LineAndShapeRenderer renderer = (LineAndShapeRenderer) chart.getCategoryPlot().getRenderer();
       CategoryPlot plot=chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);

        plot.setRangeGridlinePaint(Color.WHITE);
        plot.setDomainGridlinesVisible(false);

        renderer.setBaseStroke(new BasicStroke(5));
        renderer.setSeriesPaint(0, Color.ORANGE);
        renderer.setSeriesPaint(1, Color.RED);
        renderer.setSeriesPaint(2, Color.BLUE);
        renderer.setSeriesPaint(3, Color.MAGENTA);
        renderer.setSeriesPaint(4, Color.BLACK);
        

        ChartPanel panel = new ChartPanel(chart);  
        setContentPane(panel);  
        File chartLine = new File( "LineChart.png" ); 
      try {
        ChartUtilities.saveChartAsPNG( chartLine , chart ,620,480 );
      } catch (IOException e) {
        
        e.printStackTrace();
      }
    }

    private CategoryDataset createDataset(TreeMap<String[],Float> treemap){
        //{year,powertype}=capacity
        Set<Map.Entry<String[], Float> > entries= treemap.entrySet();
        Iterator<Map.Entry<String[], Float> > iterator = entries.iterator();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
       
        String series1="Bio-Mass Power";
        String series2="Small Hydro Power";
        String series3="Solar Power";
        String series4="Waste to Energy";
        String series5="Wind Power";
        while(iterator.hasNext()){
            Map.Entry<String[], Float> entry=iterator.next();
            
            switch(entry.getKey()[1]){
                case "Bio-Mass Power":
                    dataset.addValue(entry.getValue(),series1,entry.getKey()[0]);
                    break;
                case "Small Hydro Power":
                    dataset.addValue(entry.getValue(),series2,entry.getKey()[0]);
                    break;
                case "Solar Power":
                    dataset.addValue(entry.getValue(),series3,entry.getKey()[0]);
                    break;
                case "Waste to Energy":
                    dataset.addValue(entry.getValue(),series4,entry.getKey()[0]);
                    break;
                case "Wind Power":
                    dataset.addValue(entry.getValue(),series5,entry.getKey()[0]);
                    break;
                default:
                    break;
            }

        }
        return dataset;

    }
}
