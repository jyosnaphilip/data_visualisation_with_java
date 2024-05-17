package com.example;
import com.example.charting.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import org.jfree.chart.ChartFactory;  
import org.jfree.chart.ChartPanel;  
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;  

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

@SuppressWarnings("unused")
public class Data{
    //find the column count for the output array of a groupby method
    //if you want an array conaining all indexes where a particular value occurs,
    // and assuming each value occurs equal no of times.
    //from x, find the indexes where each value of unique array occurs
    static int findColumnCount(ArrayList<String> x,ArrayList<String> unique){
        int column_count=0;
        for(int i=0;i<x.size();i++){
            if(x.get(i).equals(unique.get(0))){
                column_count+=1;
            }
        }
        return column_count;
    }

    static ArrayList<String> convertSetToList(HashSet<String> x){
        ArrayList<String> result=new ArrayList<>(x);
        return result;
    }

    //get all unique values from an arraylist of strings
    static ArrayList<String> getUnique(ArrayList<String> x){
        //get all the unique years 
        HashSet<String> x_unique=new HashSet<String>(x);
        //converting back to arrayList
        
        return convertSetToList(x_unique);
    }

    //to be used when you need to add particular indices of an array.
    //add values occuring at those indexes of target, which occur in array indexes
    static Float sumArray(Float[] float_y,Integer[] indexes){
        Float sum=Float.parseFloat("0");
        for(int i:indexes){
            sum+=float_y[i];
        }
        return sum;
    }

    //find common elements between two int arrays, useful for grouping by multiple columns
    static Integer[] findCommon(Integer[] x,Integer[] y){
        HashSet<Integer> x_set=new HashSet<>();
        HashSet<Integer> y_set=new HashSet<>();
        for(Integer i:x){
            x_set.add(i);
        }
        for(Integer i:y){
            y_set.add(i);
        }
        x_set.retainAll(y_set); //keeps all values that are in y_set, in x_set
        
        Integer[] x_list=x_set.toArray(new Integer[x_set.size()]);
        return x_list;
    }
    static Integer[] findCommon(Integer[] x,Integer[] y,Integer[] z){
        HashSet<Integer> x_set=new HashSet<>();
        HashSet<Integer> y_set=new HashSet<>();
        HashSet<Integer> z_set=new HashSet<>();
        for(Integer i:x){
            x_set.add(i);
        }
        for(Integer i:y){
            y_set.add(i);
        }
        for(Integer i:z){
            z_set.add(i);
        }

        x_set.retainAll(y_set); //keeps all values that are in y_set, in x_set
        x_set.retainAll(z_set); //keeps all values that are in y_set, in x_set
        
        Integer[] x_list=x_set.toArray(new Integer[x_set.size()]);
        return x_list;
    }


    static Integer[] convertArrListIntToIntArr(ArrayList<Integer> x){
        Object[] obj_arr=x.toArray(); 
        Integer[] returning=Arrays.copyOf(obj_arr,obj_arr.length,Integer[].class);
        return returning;
    }

    static Float[] convertArrListFloatToFloatArr(ArrayList<Float> x){
        Object[] obj_arr=x.toArray(); 
        Float[] returning=Arrays.copyOf(obj_arr,obj_arr.length,Float[].class);
        return returning;
    }
    //return an integre array of all positions of required element in an array list
    //wherever the value occurs in x, that index is there in the returned list
    static Integer[] findAllInStringArrayList(ArrayList<String> x,String value){
        ArrayList<Integer> arr=new ArrayList<>();
        for(int j=0;j<x.size();j++){
            if(x.get(j).equals(value)){
                arr.add(j);
            }
        }
        //cant convert arraylist to integer array directly!
        
        return convertArrListIntToIntArr(arr);
    }

    
    //groups y based on x by aggregating with 'aggregate'
    //output wil be like a dictionary, with the strings as keys and aggregated terms as values
    static TreeMap<String,Float> groupby(ArrayList<String> x,String aggregate,ArrayList<Float> y){
        TreeMap<String,Float> treemap=new TreeMap<>();
        ArrayList<String> unique_list=getUnique(x);
        int column_count=findColumnCount(x, unique_list);
        for(String i:unique_list){
                //create array to hold all index values of needed values
            Integer[] array=new Integer[column_count];
            array=findAllInStringArrayList(x, i);
            Float[] float_y=convertArrListFloatToFloatArr(y);
            if(aggregate.equals("sum")){
                float sum=sumArray(float_y,array);
                treemap.put(i,sum);
            }
            }
            
        return treemap;
    }

    static Integer[] convertArrListStringToIntegers(ArrayList<String> x){
        Integer[] result=new Integer[x.size()];
        int count=0;
        for(String i:x){
            result[count]=Integer.parseInt(i);
            count+=1;
        }
        return result;
    }

    //group y based on x1 and x2 y aggregating on 'aggregate'
    //output will be like a dictionary, where key is an array of strings
    //eg. [2011,wind_energy] is key and value will be sum
    static TreeMap<String[],Float> groupby(ArrayList<String> x1,ArrayList<String> x2,String aggregate,ArrayList<Float> y){
        TreeMap<String,Integer[]> treemap_x1=new TreeMap<>();
        TreeMap<String,Integer[]> treemap_x2=new TreeMap<>();

        ArrayList<String> unique_list_x1=getUnique(x1);
        ArrayList<String> unique_list_x2=getUnique(x2);

        int column_count_x1=findColumnCount(x1, unique_list_x1);
        int column_count_x2=findColumnCount(x2, unique_list_x2);

        for(String i:unique_list_x1){
                //create array to hold all index values of needed values
            Integer[] array_x1=findAllInStringArrayList(x1, i);
            treemap_x1.put(i,array_x1);
                //keys should be unique so it will overwrite
            
            }
        for(String i:unique_list_x2){
                //create array to hold all index values of needed values
            Integer[] array_x2=new Integer[column_count_x2];
            array_x2=findAllInStringArrayList(x2, i);
            treemap_x2.put(i,array_x2);
                
        }
        TreeMap<String[],Float> treemap=new TreeMap<>(Arrays::compare); 
        //uses the array method compare to sort the keys
        for(String j:unique_list_x1){
            Integer[] j_arr=treemap_x1.get(j);
            for(String k:unique_list_x2){
                Integer[] k_arr=treemap_x2.get(k);
                Integer[] common=findCommon(j_arr,k_arr);
                Float sum=sumArray(convertArrListFloatToFloatArr(y), common);
                String[] key={j,k};
                treemap.put(key,sum);
            }
        }    
        return treemap;
    }
    static TreeMap<String[],Float> groupbyMultiple(ArrayList<String> group1,ArrayList<String> group2,ArrayList<String> group3,String aggregate,ArrayList<Float> y,String group3_series){
        TreeMap<String,Integer[]> treemap_group1=new TreeMap<>();
        TreeMap<String,Integer[]> treemap_group2=new TreeMap<>();
        TreeMap<String,Integer[]> treemap_group3=new TreeMap<>();
        ArrayList<String> unique_list_group1=getUnique(group1);
        ArrayList<String> unique_list_group2=getUnique(group2);
        ArrayList<String> unique_list_group3=getUnique(group3);

        int column_count_group1=findColumnCount(group1, unique_list_group1);
        int column_count_group2=findColumnCount(group2, unique_list_group2);
        int column_count_group3=findColumnCount(group3, unique_list_group3);
        String[] subset=new String[]{group1.get(0),group1.get(group1.size()-1)};
        for(String i:subset){
            
                //create array to hold all index values of needed values
            Integer[] array_group1=findAllInStringArrayList(group1, i);
            treemap_group1.put(i,array_group1);
                //keys should be unique or else it will overwrite
            
            }
        for(String i:unique_list_group2){
                //create array to hold all index values of needed values
            Integer[] array_group2=new Integer[column_count_group2];
            array_group2=findAllInStringArrayList(group2, i);
            treemap_group2.put(i,array_group2);
                
        }
        String[] series=new String[]{group3_series};

        for(String i:series){
            
            //create array to hold all index values of needed values
            Integer[] array_group3=new Integer[column_count_group3];
            array_group3=findAllInStringArrayList(group3, i);
            treemap_group3.put(i,array_group3);
            
        }
        TreeMap<String[],Float> treemap=new TreeMap<>(Arrays::compare); 
        //uses the array method compare to sort the keys
        for(String j:unique_list_group2){
            Integer[] j_arr=treemap_group2.get(j);
           
            //this list will have the capacities, we order this list find the top10 and get it from the treemap created for a new treemap
            int count=0;
            for(String k:subset){
               
                Integer[] k_arr=treemap_group1.get(k);
                for(String m: series){
                    Integer[] m_arr=treemap_group3.get(m);
                    Integer[] common=findCommon(j_arr,k_arr,m_arr);
                    if(common.length==0){
                        continue;
                    }
                    else{
                        Float sum=sumArray(convertArrListFloatToFloatArr(y), common);
                        if(sum<=Float.parseFloat("10")){
                            continue;
                        }
                        else{
                            String[] key={j,k,m};
                            treemap.put(key,sum);

                            count+=1;
                        }
                        
                    }

                }

                Set<Map.Entry<String[], Float> > entries= treemap.entrySet();
                Iterator<Map.Entry<String[], Float> > iterator = entries.iterator();
  
            }
        }    
        return treemap;
    }
        

    public static void timeseriesChart(ArrayList<String> x_val,ArrayList<Float> y_val,ArrayList<String> classes){
        
        TreeMap<String[],Float> data_groupedBy_year_class=groupby(x_val,classes, "sum",y_val);
        SwingUtilities.invokeLater(() -> {  
            LineChart lineChart=new LineChart("Trend of Grid Connected Renewable Energy", data_groupedBy_year_class, "Year", "Capacity");
            lineChart.setAlwaysOnTop(true);  
            lineChart.pack();  
            lineChart.setSize(600, 400);  
            lineChart.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  
            lineChart.setVisible(true);  
        });  
         
    }
   
    public static void BarChartforTop10(ArrayList<String> group1,ArrayList<String> group2,ArrayList<String> group3,ArrayList<Float> y_val,String series,String xlabel,String ylabel){
        TreeMap<String[],Float> data_groupedBy_year_state=groupbyMultiple(group1,group2,group3, "sum",y_val,series);
        SwingUtilities.invokeLater(()->{
            BarChart barChart=new BarChart("State-Wise Comparison of Grid Connected Energy Capacity- "+series,data_groupedBy_year_state,series,xlabel,ylabel);  
            barChart.setSize(800,800);  
            barChart.setLocationRelativeTo(null);  
            barChart.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  
            barChart.setVisible(true); 
            
        });  
    }

    public static void PieCharts(ArrayList<String> group1,ArrayList<String> group2,ArrayList<Float> y_val,String series1,String series2){
        TreeMap<String[],Float> data_groupedBy_type=groupby(group1,group2,"sum",y_val);
        SwingUtilities.invokeLater(() -> {  
            PieChart pieChart = new PieChart("Composition of Total grid connected power generation",series1,series2,data_groupedBy_type);  
            pieChart.setSize(400,600);  
            pieChart.setLocationRelativeTo(null);  
            // pieChart.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  
            pieChart.setVisible(true);      
          });  
    }
    public static void LowestStates(ArrayList<String> group1,ArrayList<Float> y_val,ArrayList<String> group2,String x_label,String y_label){
        TreeMap<String[],Float> data_groupedBy_year_state=groupby(group2,group1, "sum",y_val);
        SwingUtilities.invokeLater(()->{
        BarChart barChart=new BarChart("States/UTs with Lowest Total Grid Connected Renewable energy capacity ",data_groupedBy_year_state,x_label,y_label);  
        barChart.setSize(800,800);  
        barChart.setLocationRelativeTo(null);  
        barChart.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  
        barChart.setVisible(true); 
            
        });  
    }
   
    public static void main(String args[]){
        ArrayList<String> state=new ArrayList<>();
        ArrayList<String> year=new ArrayList<>();
        ArrayList<String> powerType=new ArrayList<>();
        ArrayList<Float> growthRate=new ArrayList<>();
        ArrayList<Float> capacity=new ArrayList<>();
        int lineCount=-1;
        try{
            File dataset=new File("C:\\Users\\jyosn\\OneDrive\\Desktop\\christ uni\\trimester 3\\java\\java_cac\\src\\main\\java\\com\\example\\cleaned_data.csv");
            Scanner fileReader=new Scanner(dataset);

            while(fileReader.hasNextLine()){
                if(lineCount==-1){
                    lineCount+=1;
                    String line=fileReader.nextLine();
                    continue;
                }
                else{
                    String line=fileReader.nextLine();
                    String[] lineArr=line.split(",");
                    state.add(lineArr[1].strip());
                    year.add(lineArr[2].strip());
                    powerType.add(lineArr[3].strip());
                    growthRate.add(Float.parseFloat(lineArr[4].strip()));
                    capacity.add(Float.parseFloat(lineArr[5].strip()));
                    lineCount+=1;
                }
                
            }
            fileReader.close();

        }
        catch(FileNotFoundException e){
            System.out.println("no file found!");
        } 
        // Timeseries chart showing the increasing capacity of different types of power
// For each year, add all instances of a particular power.
        timeseriesChart(year,capacity,powerType);
        BarChartforTop10(year,state,powerType,capacity,"Bio-Mass Power","State","Capacity");
        BarChartforTop10(year,state,powerType,capacity,"Solar Power","State","Capacity");
        BarChartforTop10(year,state,powerType,capacity,"Small Hydro Power","State","Capacity");
        BarChartforTop10(year,state,powerType,capacity,"Waste to Energy","State","Capacity");
        BarChartforTop10(year,state,powerType,capacity,"Wind Power","State","Capacity");
        PieCharts(year,powerType,capacity, "2006","2021");
        // PieCharts(year,powerType,capacity,"2021");
        LowestStates(year,capacity,state,"State","Capacity");

           
    }
}
