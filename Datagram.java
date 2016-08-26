import java.io.IOException;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import javax.jmdns.ServiceEvent;

import org.jfree.data.xy.XYSeries;

public class Datagram {

    static class SampleListener implements ServiceListener {
        @Override
        public void serviceAdded(ServiceEvent event) {
            System.out.println("Service added   : " + event.getName() + "." + event.getType());
        }

        @Override
        public void serviceRemoved(ServiceEvent event) {
            System.out.println("Service removed : " + event.getName() + "." + event.getType());
        }

        @Override
        public void serviceResolved(ServiceEvent event) {
            System.out.println("Service resolved: " + event.getInfo());
            serviceUrls = event.getInfo().getURLs();
        }
    }
    static String []serviceUrls;
    static String mServiceName = "JmDNS Server";
    static String SERVICE_TYPE = "_IAmTheBirdman._udp.local";
    static int serverUptime = 600; // packets
    static boolean client = false;
    static public void main(String[] args) throws UnknownHostException, SocketException, IOException {
        int xlast = 0;
        int ylast = 0;
        int zlast = 0;
        int thetalast = 0;
        int x, y, z, theta;
        XYSeries dataseries = new XYSeries("S1");

        byte[] buf = new byte[64];

        final int DEFAULT_MULTICAST_PORT = 5555;
        final String multicastGroup = "225.4.5.6";
        final String adapterName = "eth5";


        /*
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        for (NetworkInterface netint : Collections.list(nets))
            displayInterfaceInformation(netint);
        */

        MulticastSocket mSocket = new MulticastSocket(DEFAULT_MULTICAST_PORT);
        mSocket.setReuseAddress(true);
        //mSocket.setSoTimeout(5000);
        NetworkInterface nic = NetworkInterface.getByName(adapterName);
        mSocket.joinGroup(InetAddress.getByName(multicastGroup));

        //DatagramSocket socket = new DatagramSocket();
        DatagramPacket packet = new DatagramPacket(buf, buf.length);

        /* new */
        //InetAddress group = InetAddress.getByName("224.0.1.1");
        //MulticastSocket socket = new MulticastSocket();
        //socket.joinGroup(group);
        /* new */

        //int port = socket.getLocalPort();
        InetAddress addr = InetAddress.getLocalHost();
        //addr = multicastGroup;
        String hostname = InetAddress.getByName(addr.getHostName()).toString();

        JmDNS jmdns = JmDNS.create();
        ServiceInfo info = ServiceInfo.create(SERVICE_TYPE, mServiceName, DEFAULT_MULTICAST_PORT, "App service");
        jmdns.unregisterAllServices();
        jmdns.registerService(info);

        System.out.println("REGISTERED as " + info);

        XYLineChart plot = new XYLineChart("Run speed", dataseries); // listens for events
        plot.setVisible(true);

        int count = 0;

        if(client) {
            jmdns.unregisterAllServices();
            jmdns.addServiceListener("_IAmPhone._udp.local.", new SampleListener());
        }
        while (!mSocket.isClosed() && count < serverUptime) {
            mSocket.receive(packet);
            count++;
            String data = new String(packet.getData()); // packet.getData >> byte array

            /* *
            *
            * ===PACKET FORMAT===
            *
            * String
            * "(int)x:(int)y:(int)z:(int)theta"
            *
            * int format: "%+.9d"
            *
            * */

            String[] dataParsed = data.split(":");
            try {
                // good packet
                x = Integer.parseInt(dataParsed[0]);
                y = Integer.parseInt(dataParsed[1]);
                z = Integer.parseInt(dataParsed[2]);
                theta = Integer.parseInt(dataParsed[3].trim());
            } catch (java.lang.NumberFormatException e) {
                //corrupted packet;
                x = xlast;
                y = ylast;
                z = zlast;
                theta = thetalast;
            }
            xlast = x;
            ylast = y;
            zlast = z;
            thetalast = theta;

            String line = (
                    "\n          x:" + x +
                            "\n          y:" + y +
                            "\n          z:" + z +
                            "\norientation:" + theta
            );
            System.out.println(line);
            // add 2 values to XYSeries to graph
            dataseries.add(count, x); // sends event to listeners
        }


        jmdns.unregisterAllServices();

    }
    static void displayInterfaceInformation(NetworkInterface netint) throws SocketException {
        System.out.printf("Display name: %s\n", netint.getDisplayName());
        System.out.printf("Name: %s\n", netint.getName());
        Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
            System.out.printf("InetAddress: %s\n", inetAddress);
        }
        System.out.printf("\n");
    }
}