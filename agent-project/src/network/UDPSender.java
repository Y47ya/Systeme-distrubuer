package network;

import model.Alert;
import model.Metric;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.BlockingDeque;

public class UDPSender implements Runnable{

    private final BlockingDeque<Metric> metrics;
    private DatagramSocket datagramSocket;
    private InetAddress address;
    private Socket tcpSocket;

    public DatagramSocket getDatagramSocket() {
        return datagramSocket;
    }

    public void setDatagramSocket(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public Socket getTcpSocket() {
        return tcpSocket;
    }

    public void setTcpSocket(Socket tcpSocket) {
        this.tcpSocket = tcpSocket;
    }

    public UDPSender(Socket tcpSocket, DatagramSocket datagramSocket, BlockingDeque<Metric> metrics) throws Exception {
        this.metrics = metrics;
        this.address = InetAddress.getByName("localhost");
        this.datagramSocket = datagramSocket;
        this.tcpSocket = tcpSocket;
    }

    public double getThreshold(String type) {
        if (Objects.equals(type, "Disk alert"))
            return Alert.getDiskThreshold();
        else if (Objects.equals(type, "Ram alert"))
            return Alert.getRamThreshold();
        else
            return Alert.getCpuThreshold();
    }

    public void checkAlerts(Metric metric){
        String agentId = metric.getAgentId();
        double cpuUsage = metric.getCpuUsage();
        double totalCpu = metric.getTotalCpu();
        double ramUsage = metric.getRamUsage();
        double ramSize = metric.getRamSize();
        double diskUsage = metric.getDiskUsage();
        double diskSize = metric.getDiskSize();
        String timestamp = metric.getTimestamp();

        Map<String, Double> errors = new HashMap<>();

//        System.out.println("Checking alert...");

        if ((diskUsage / diskSize) >= Alert.getDiskThreshold())
            errors.put("Disk alert", diskUsage);
        if ((ramUsage / ramSize) >= Alert.getRamThreshold())
            errors.put("Ram alert", ramUsage);
        if (cpuUsage >= Alert.getCpuThreshold())
            errors.put("CPU alert", cpuUsage);

        errors.forEach(
                (type, val) -> {
                    Alert alert = new Alert(
                            agentId,
                            type,
                            val,
                            this.getThreshold(type),
                            timestamp

                    );

//                    System.out.println(alert.toString());

                    try{
//                        System.out.println("Alert is about to be sent...");
                        TCPSender tcpSender = new TCPSender(this.tcpSocket, alert);
                        Thread tcpSenderThread = new Thread(tcpSender);
                        tcpSenderThread.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );



    }

    public void run() {

        while (true) {
            try {

                Metric metric = this.metrics.take();
//                System.out.println(metric);

                try {

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);

                    this.checkAlerts(metric);

                    oos.writeObject(metric);
                    oos.flush();

                    byte[] data = baos.toByteArray();

                    int port = 12345;
                    DatagramPacket packet = new DatagramPacket(
                            data,
                            data.length,
                            this.address,
                            port
                    );

                    try {

                        synchronized (this.datagramSocket){
                            datagramSocket.send(packet);
//                            System.out.println("Packet sent...");
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                } catch (IOException exception) {
                    exception.printStackTrace();
                }







            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Packet Failed");
            }

        }
    }


}
