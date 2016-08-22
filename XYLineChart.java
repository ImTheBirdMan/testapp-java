/**
 * This program demonstrates how to draw XY line chart with XYDataset
 * using JFreechart library.
 * @author www.codejava.net
 *
 */
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;


public class XYLineChart extends JFrame {
   XYSeriesCollection dataset = new XYSeriesCollection();
   boolean autoSort = false;
   boolean allowDuplicateXValues = false;
   XYSeries series = new XYSeries("Object 1", autoSort, allowDuplicateXValues);


   public XYLineChart() {
      super("Packet Data");
      JPanel chartPanel = createChartPanel();
      add(chartPanel, BorderLayout.CENTER);
      setSize(640, 400);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLocationRelativeTo(null);
   }

   public JPanel createChartPanel() {
      String chartTitle = "Movement Chart";
      String xAxisLabel = "X";
      String yAxisLabel = "Packet Count";
      createDataset(); 
      XYDataset datasetPlot = dataset;
   
      JFreeChart chart = ChartFactory.createXYLineChart(chartTitle,
            xAxisLabel, yAxisLabel, datasetPlot);
   
      return new ChartPanel(chart);
   }
   
   
   
   public void addData(){
      series.add(Datagram.getXlast(), Datagram.getCount());
   }
   public void createDataset() {
      // creates an XY dataset...
      // returns the dataset
      
      //XYSeriesCollection dataset = new XYSeriesCollection();
      //boolean autoSort = false;
      //XYSeries series = new XYSeries("Object 1", autoSort);
      //List<Point> graphPoints = new ArrayList<>();
      //int x1 = (int) Datagram.getXlast();
      //int y1 = (int) Datagram.getCount();//currently the y on graph is plotting packet number
      //graphPoints.add(new Point(x1, y1));
      System.out.print("Dataset Created.\n"); 
      //series.add(Datagram.getXlast(), Datagram.getCount());
      dataset.addSeries(series);   
       
   }

/*
   public static void main(String[] args) {
      SwingUtilities.invokeLater(
         new Runnable() {
            @Override
            public void run() {
               new XYLineChart().setVisible(true);
            }
         });
   }
   */
}