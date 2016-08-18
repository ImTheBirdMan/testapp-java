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

public class Datagram extends Gui{
   public static String mServiceName = "JmDNS Server";
   public static final String SERVICE_TYPE = "_IAmTheBirdman._udp.local";
   
   public static void main(String[] args) throws UnknownHostException, SocketException, IOException {
        
        
      new Gui(); 
    
      
      
      
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
      int xlast = 0;
      int x = xlast;
   
      int ylast = 0;
      int y = ylast;
   
      int zlast = 0;
      int z = zlast;
   
      int thetalast = 0;
      int theta = thetalast;
   
        //while(true){
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
      }
      //}
      System.out.print("Enter any character to stop server: ");
       // Read the char
      char ch = (char) System.in.read();
      jmdns.unregisterAllServices();
      jmdns.unregisterAllServices();
   }
}