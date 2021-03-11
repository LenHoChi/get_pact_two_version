package com.example.test1;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.sun.jna.Platform;

import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PacketListener;
import org.pcap4j.core.PcapDumper;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.PcapStat;
import org.pcap4j.core.BpfProgram.BpfCompileMode;
import org.pcap4j.core.PcapNetworkInterface.PromiscuousMode;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.IpV6Packet;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;
import org.pcap4j.util.NifSelector;

public class App {

    static PcapNetworkInterface getNetworkDevice() {
        PcapNetworkInterface device = null;
        try {
            device = new NifSelector().selectNetworkInterface();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return device;
    }

    public static void main(String[] args) throws PcapNativeException, NotOpenException {
        // The code we had before
        PcapNetworkInterface device = getNetworkDevice();
        System.out.println("You chose: " + device);

        // New code below here
        if (device == null) {
            System.out.println("No device chosen.");
            System.exit(1);
        }

        // Open the device and get a handle
        int snapshotLength = 65536; // in bytes
        int readTimeout = 10; // in milliseconds
        final PcapHandle handle;
        handle = device.openLive(snapshotLength, PromiscuousMode.PROMISCUOUS, readTimeout);
        PcapDumper dumper = handle.dumpOpen("out.pcap");

        // Set a filter to only listen for tcp packets on port 80 (HTTP)
        String filter = "tcp port 443";
        handle.setFilter(filter, BpfCompileMode.OPTIMIZE);

        // Create a listener that defines what to do with the received packets
        PacketListener listener = new PacketListener() {
            @Override
            public void gotPacket(Packet packet) {
                // Print packet information to screen


//                String ipDest = String.valueOf(packet.get(IpV4Packet.class).getHeader().getDstAddr());
                String ipDest = String.valueOf(packet.get(IpV4Packet.class).getHeader().getDstAddr());
                //String ipDest= String.valueOf(packet.get(IpV4Packet.class).getHeader());
//                System.out.println(ipDest);
                ipDest = ipDest.replaceAll("/", "");
                //System.out.println();
                InetAddress addr = null;
                try {
                    addr = InetAddress.getByName(ipDest);
                    //addr = Inet4Address.getByName(ipDest);
                    //addr = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                //System.out.println("---->"+addr);
                String host = addr.getHostName();
//                if(host.contains("facebook.com")) {
//                    System.out.println("------------------------------------------------------------------------------------");
//                    System.out.println(handle.getTimestamp());
//                    System.out.println(host);
//                    System.out.println("------------------------------------------------------------------------------------");
//
//
////                    String time = handle.getTimestamp().toString();
////                    String result =time+"||"+host;
////                    System.out.println(result);
//                }
//                // Dump packets to file
////                try {
////                    dumper.dump(packet, handle.getTimestamp());
////                } catch (NotOpenException e) {
////                    e.printStackTrace();
////                }
                System.out.println("------------------------------------------------------------------------------------");
                System.out.println(handle.getTimestamp());
                System.out.println(host);
                System.out.println("------------------------------------------------------------------------------------");
            }

        };

        // Tell the handle to loop using the listener we created
        try {
            int maxPackets = -1;
            handle.loop(maxPackets, listener);
            //PcapStat stats = handle.getStats();
            //System.out.println("Packets received: " + stats.getNumPacketsReceived());
            //System.out.println("Packets dropped: " + stats.getNumPacketsDropped());
            //System.out.println("Packets dropped by interface: " + stats.getNumPacketsDroppedByIf());
            // Supported by WinPcap only
            if (Platform.isWindows()) {
                //System.out.println("Packets captured: " +stats.getNumPacketsCaptured());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dumper.close();
        handle.close();

        // Print out handle statistics



        // Cleanup when complete

    }
}