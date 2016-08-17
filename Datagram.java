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


public class Datagram {
   public static String mServiceName = "JmDNS Server";
   public static final String SERVICE_TYPE = "_IAmTheBirdman._udp.local";
   
   public static void main(String[] args) throws UnknownHostException, SocketException, IOException {
       DatagramSocket socket = new DatagramSocket();
       SocketAddress addr = socket.getLocalSocketAddress();
       System.out.println(addr);

       //socket.bind(addr);
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

      //while(true){
         while (!socket.isClosed()) {
             System.out.println("1");
             socket.receive(packet);
             System.out.println("2");
             String data = new String(packet.getData()); // returns buf
             System.out.println("Raw data: " + data);
             String[] dataParsed = data.split(",");

             int x = Integer.parseInt(dataParsed[0]);
             int y = Integer.parseInt(dataParsed[1]);
             int z = Integer.parseInt(dataParsed[2]);

             String line = ("x:" + x + " y: " + y + " z:" + z);
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