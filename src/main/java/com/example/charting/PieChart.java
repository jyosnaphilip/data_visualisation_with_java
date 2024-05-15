package com.example.charting;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;  
import org.jfree.chart.ChartPanel;  
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.ChartUtilities;  

import org.jfree.chart.labels.PieSectionLabelGenerator;  
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;  
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;  
import org.jfree.data.general.PieDataset;  

public class PieChart extends JFrame{
 
  
  public PieChart(String title,String series1,String series2, TreeMap<String[],Float> treemap) {  
    super(title);  
    JPanel panel=new JPanel(new GridLayout(1,1));
    // frame.setLayout( new FlowLayout() );
    // Create dataset  
    PieDataset dataset1 = createDataset(treemap,series1);
    PieDataset dataset2 = createDataset(treemap,series2);  
   
  
    // Create chart  
    JFreeChart chart1 = ChartFactory.createPieChart(  
        title+series1,  
        dataset1,  
        true,   
        true,  
        false);  
        PiePlot plot1 = (PiePlot) chart1.getPlot();
        plot1.setSectionPaint(1,Color.green.darker());
        plot1.setSectionPaint(2,Color.MAGENTA.darker());
        plot1.setSectionPaint(3,Color.blue.darker());
        plot1.setSectionPaint(4,Color.BLACK.darker());
        plot1.setSectionPaint(4,Color.RED.darker());
        plot1.setShadowPaint(null);
        

        // plot.setSectionPaint(new Color(120, 0, 120));
        // or do this, if you are using an older version of JFreeChart:
        //plot.setSectionPaint(1, Color.black);
        //plot.setSectionPaint(3, new Color(120, 0, 120));
    //Format Label  
    PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(); 
          
    ((PiePlot) chart1.getPlot()).setLabelGenerator(labelGenerator);  
      File pieChart = new File( "PieChart.png" ); 
      try {
        ChartUtilities.saveChartAsPNG( pieChart , chart1 ,620,480 );
      } catch (IOException e) {
        
        e.printStackTrace();
      }
      panel.add(new ChartPanel(chart1),BorderLayout.CENTER);
      
      JFreeChart chart2 = ChartFactory.createPieChart(  
        title+series2,  
        dataset2,  
        true,   
        true,  
        false);  
        PiePlot plot2 = (PiePlot) chart2.getPlot();
        plot2.setSectionPaint(1,Color.green.darker());
        plot2.setSectionPaint(2,Color.MAGENTA.darker());
        plot2.setSectionPaint(3,Color.blue.darker());
        plot2.setSectionPaint(4,Color.BLACK.darker());
        plot2.setSectionPaint(4,Color.RED.darker());
        plot2.setShadowPaint(null);
        PieSectionLabelGenerator labelGenerator2 = new StandardPieSectionLabelGenerator(); 
          
    ((PiePlot) chart2.getPlot()).setLabelGenerator(labelGenerator2);  
      File pieChart2 = new File( "PieChart2.png" ); 
      try {
        ChartUtilities.saveChartAsPNG( pieChart2 , chart2 ,620,480 );
      } catch (IOException e) {
        
        e.printStackTrace();
      }
      panel.add(new ChartPanel(chart2));
      add(panel,BorderLayout.CENTER);
      
      pack();

      setVisible(true);
    // Create Panel  
     
  }  
  
  private PieDataset createDataset(TreeMap<String[],Float> treemap,String series) {  
  
    DefaultPieDataset dataset=new DefaultPieDataset();  
     
      Set<Map.Entry<String[], Float> > entries= treemap.entrySet();
      Iterator<Map.Entry<String[], Float> > iterator = entries.iterator();
        // key0=year
        // key1=state
        // key2=power  
        
        while(iterator.hasNext()){
            Map.Entry<String[], Float> entry=iterator.next();
            if(entry.getKey()[0].equals(series)){
              
                dataset.setValue(entry.getKey()[1],entry.getValue());
            }
              
              else{
                ;
              }
            }  
            return dataset; 
          }
        
     
  }  
  
   
  