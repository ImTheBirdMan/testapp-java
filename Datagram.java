import java.io.*;
import java.net.*;
import java.util.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.*;
import java.net.SocketException;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import javax.swing.JFrame;
import javax.swing.JPanel; 
import javax.swing.JComboBox; 
import javax.swing.JButton; 
import javax.swing.JLabel; 
import javax.swing.JList; 
import java.awt.BorderLayout; 
import java.awt.event.ActionListener; 
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.*;
import javax.swing.SwingUtilities;
import java.util.Random;

public class Datagram extends XYLineChart{
   public static String mServiceName = "JmDNS Server";
   public static final String SERVICE_TYPE = "_IAmTheBirdman._udp.local";
   public static int xlast = 0;
   public static int x = xlast;
   public static int ylast = 0;
   public static int y = ylast;
   public static int zlast = 0;
   public static int z = zlast;
   public static int thetalast = 0;
   public static int theta = thetalast;
   public static int count = 0; 
   
   
   public static int getXlast(){
      return x; 
   }
   
   public static int getYlast(){
      return ylast;
   }
   public static int getCount(){
      return count; 
   }
   public static void main(String[] args) throws UnknownHostException, SocketException, IOException  {
        
        
      
    
      
      
      
      DatagramSocket socket = new DatagramSocket();
      SocketAddress addr = socket.getLocalSocketAddress();
      System.out.println(addr);
   
      byte[] buf = new byte[64];
      DatagramPacket packet = new DatagramPacket(buf, buf.length);
      int mPort = socket.getLocalPort();
      System.out.println(mPort);
      JmDNS jmdns = JmDNS.create();
      ServiceInfo info = ServiceInfo.create(SERVICE_TYPE, mServiceName, mPort, "App service");
   
      jmdns.registerService(info);
      System.out.println("REGISTERED");
      System.out.println("Address:" + InetAddress.getLocalHost().getHostAddress());
   
      System.out.println("connected?");
      System.out.println(socket.isConnected());
   
      System.out.println("Socket is closed?");
      System.out.println(socket.isClosed());
   
      System.out.println("Socket is bound?");
      System.out.println(socket.isBound());
      XYLineChart plot = new XYLineChart();  
        //while(true){
        
        
      //Random rand = new Random();
     //int randomNum = rand.nextInt((1000 - 0) + 1) + 0; 
        
      while (!socket.isClosed()) {
         socket.receive(packet);
         String data = new String(packet.getData()); // returns buf?
      
             /* *
              * PACKET FORMAT
              * String
              * "int(x),int(y),int(z),int(theta)"
              *
              * */
            //System.out.println("Raw data: " + data);
      
         String[] dataParsed = data.split(":");
         try {
                // good packet
            x = Integer.parseInt(dataParsed[0]);
            xlast = x;
            y = Integer.parseInt(dataParsed[1]);
            ylast = y;
            z = Integer.parseInt(dataParsed[2]);
            zlast = z;
            theta = Integer.parseInt(dataParsed[3].trim());
            thetalast = theta;
                // good packet
         } 
         catch (java.lang.NumberFormatException e) {
                //corrupted packet;
            x = xlast;
            y = ylast;
            z = zlast;
            theta = thetalast;
         }
      
         String line = ("\n          x:" + x +
                            "\n          y:" + y +
                            "\n          z:" + z +
                            "\norientation:" + theta);
         System.out.println(line);
         //plot.createDataset();
         //int randomNum = (rand.nextInt((1000 - 0) + 1) + 0);
         //x = randomNum; 
         plot.addData(); 
         
         count++; 
      }
      //}
      //plot.initPlot(); 
      System.out.print("Enter any character to stop server: ");
       //Read the char
      char ch = (char) System.in.read();
      jmdns.unregisterAllServices();
      jmdns.unregisterAllServices();
       
      SwingUtilities.invokeLater(
            new Runnable() {
               public void run() {
                  plot.setVisible(true);
                  //plot.getAccessibleContext();  
               }
            });
   
   }
}