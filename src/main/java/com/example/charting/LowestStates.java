package com.example.charting;

import java.awt.Color;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class LowestStates extends JFrame{
    public LowestStates(String Title,TreeMap<String[],Float> treemap,String series,String xlabel,String ylabel) {  
    super(Title);  
        // Create Dataset  
    CategoryDataset dataset = createDataset(treemap,series);  
          
        //Create chart  
    JFreeChart chart=ChartFactory.createBarChart(Title, //Chart Title  
            xlabel, // Category axis  
            ylabel, // Value axis  
            dataset,  
            PlotOrientation.HORIZONTAL,  
            true,true,false  
           );  
           CategoryPlot plot =chart.getCategoryPlot(); 

            CategoryAxis axis = plot.getDomainAxis();
            plot.setRangeGridlinePaint(Color.WHITE);
            axis.setLowerMargin(0.1);
            axis.setUpperMargin(0.1);
            axis.setCategoryMargin(0.2);
            
            BarRenderer renderer = (BarRenderer) plot.getRenderer();
            renderer.setItemMargin(0);  
      
        ChartPanel panel=new ChartPanel(chart);  
        setContentPane(panel);  
      }  
      
      private CategoryDataset createDataset(TreeMap<String[],Float> treemap,String series) {  
        DefaultCategoryDataset dataset = new DefaultCategoryDataset(); 
        Set<Map.Entry<String[], Float> > entries= treemap.entrySet();
        Iterator<Map.Entry<String[], Float> > iterator = entries.iterator();
        // key0=year
        // key1=state
        // key2=power  
        
        while(iterator.hasNext()){
            Map.Entry<String[], Float> entry=iterator.next();
            if(entry.getKey()[2].equals(series)){
              if(entry.getKey()[1].equals("")){
                dataset.addValue(entry.getValue(),"2006",entry.getKey()[0]);
              }
              else if(entry.getKey()[1].equals("2021")){
                dataset.addValue(entry.getValue(),"2021",entry.getKey()[0]);
              }
              else{
                ;
              }
            }  
          }
        return dataset; 
    
        }
    }
