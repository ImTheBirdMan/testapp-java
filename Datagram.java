import java.io.IOException;
import java.net.*;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

import org.jfree.data.xy.XYSeries;

public class Datagram {

    static String mServiceName = "JmDNS Server";
    static String SERVICE_TYPE = "_IAmTheBirdman._udp.local";
    static int serverUptime = 600; // packets

    static public void main(String[] args) throws UnknownHostException, SocketException, IOException {
        int xlast = 0;
        int ylast = 0;
        int zlast = 0;
        int thetalast = 0;
        int x, y, z, theta;
        XYSeries dataseries = new XYSeries("S1");

        byte[] buf = new byte[64];

        DatagramSocket socket = new DatagramSocket();
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        int port = socket.getLocalPort();

        JmDNS jmdns = JmDNS.create();
        ServiceInfo info = ServiceInfo.create(SERVICE_TYPE, mServiceName, port, "App service");

        jmdns.registerService(info);
        System.out.println("REGISTERED");
        System.out.println("Address:" + InetAddress.getLocalHost().getHostAddress() + ":" + port);

        XYLineChart plot = new XYLineChart("Run speed", dataseries); // listens for events
        plot.setVisible(true);

        int count = 0;
        while (!socket.isClosed() && count < serverUptime) {
            socket.receive(packet);
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
            }
            catch (java.lang.NumberFormatException e) {
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
}