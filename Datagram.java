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
             String data = new String(packet.getData());
             System.out.println(data);
             /*
             String[] dataParsed = data.split(",");
             float timestamp = Float.parseFloat(dataParsed[0]);
             float sensortype = Float.parseFloat(dataParsed[1]);
             float x = Float.parseFloat(dataParsed[2]);
             float y = Float.parseFloat(dataParsed[3]);
             float z = Float.parseFloat(dataParsed[4]);
             String sensorname = new String();
             if (sensortype == 1) {
                sensorname = "GPS";
             }
             else if (sensortype == 2) {
                sensorname = "Magnetometer";
             }
             else if (sensortype == 3) {
                sensorname = "Accelerometer";
             }
             else if (sensortype == 4) {
                sensorname = "Gyroscope";
             }
             else if (true) {
                sensorname = "Unknown";
             }
             Date date = new Date();String line = ("Timestamp " + timestamp + ", local date " + date.toString() + ", sensor " + sensorname + ", x " + x + ", y " + y + ", z " + z);
             System.out.println(line);
             */
         
         }
      //}
       System.out.print("Enter any character to stop server: ");
       // Read the char
       char ch = (char) System.in.read();
       jmdns.unregisterAllServices();
       jmdns.unregisterAllServices();
   }
}