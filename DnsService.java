import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import java.io.IOException;
import java.net.*;
import java.util.Collections;
import java.util.Enumeration;

public class DnsService {

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

    static String DEFAULT_SERVICE_NAME = "JmDNS Server";
    static String SERVICE_TYPE = "_IAmTheBirdman._udp.local";
    static int serverUptime = 600; // packets
    static boolean client = false;
    static public void main(String[] args) throws UnknownHostException, SocketException, IOException {

        final int DEFAULT_PORT = 5555;
        String hostname = DEFAULT_SERVICE_NAME;
        try
        {
            InetAddress my_addr;
            my_addr = InetAddress.getLocalHost();
            hostname = my_addr.getHostName();
        }
        catch (UnknownHostException ex)
        {
            System.out.println("Hostname can not be resolved");
            hostname = DEFAULT_SERVICE_NAME;
        }
        JmDNS jmdns = JmDNS.create();
        ServiceInfo info = ServiceInfo.create(SERVICE_TYPE, hostname, DEFAULT_PORT, "App service");
        jmdns.unregisterAllServices();
        jmdns.registerService(info);

        System.out.println("REGISTERED as " + info);

        if(client) {
            jmdns.unregisterAllServices();
            jmdns.addServiceListener("_IAmPhone._udp.local.", new SampleListener());
        }

        /* CALL C++ PROGRAM */

        System.in.read();

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