package service;



import com.sun.management.OperatingSystemMXBean;
import interfaces.AlertService;
import interfaces.MetricService;
import model.Agent;
import model.Alert;
import model.Metric;
import network.UDPSender;


import java.io.File;
import java.lang.management.ManagementFactory;
import java.net.ConnectException;
import java.net.DatagramSocket;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class SystemMetricCollector implements Runnable{

    private final Agent agent;
    private String address;
    private int port;
    private final BlockingDeque<Metric> metrics;
    private UDPSender udpSender;
    private Socket socket;
    private DatagramSocket datagramSocket;
    private MetricService metricService;
    private AlertService alertService;

    public SystemMetricCollector(Agent agent, String address, int port) throws Exception {
        this.metrics = new LinkedBlockingDeque<>();
        this.agent = agent;

        this.address = address;
        this.port = port;

        this.socket = new Socket(this.address, this.port);
        this.datagramSocket = new DatagramSocket();

        this.udpSender = new UDPSender(this.socket, this.datagramSocket, this.metrics);
        new Thread(udpSender).start();

        this.activateRMIService();
    }

    private void activateRMIService() {
        try {

            this.metricService = (MetricService) Naming.lookup("rmi://localhost:1111/metricService");
            this.alertService = (AlertService) Naming.lookup("rmi://localhost:1111/alertService");

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Metric> getMetricsByAgent(String agentId){
        try {

            return this.metricService.getMetricsByAgent(agentId);

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Metric> getLastNMetrics(String agentId, int n, String orderWay) {
        try {

            return this.metricService.getLastNMetrics(agentId, n, orderWay);

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Alert> getAlertsByAgent(String agentId){
        try {

            return this.alertService.getAlertsByAgent(agentId);

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Alert> getLastNAlerts(String agentId, int n, String orderWay) {
        try {

            return this.alertService.getLastNAlerts(agentId, n, orderWay);

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public Metric getMetrics()
    {
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        File disk = new File("C:/");

        double cpuUsage = osBean.getProcessCpuLoad();
        double ramUsage = osBean.getFreeMemorySize();
        double diskSize = disk.getTotalSpace();
        double diskUsage = diskSize - disk.getUsableSpace();
        double ramSize = osBean.getTotalMemorySize();
        double totalCpu = osBean.getCpuLoad();
        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSSSS"));

        return new Metric(
                this.agent.getId(),
                cpuUsage,
                ramUsage,
                diskUsage,
                timestamp,
                diskSize,
                totalCpu,
                ramSize
        );

    }

    public void run() {
        while (true)
        {
            Metric metric = getMetrics();

            try {
                this.metrics.put(metric);
//                System.out.println(metric.toString());

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }
    }

    public Agent getAgent() {
        return agent;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public UDPSender getUdpSender() {
        return udpSender;
    }

    public void setUdpSender(UDPSender udpSender) {
        this.udpSender = udpSender;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public DatagramSocket getDatagramSocket() {
        return datagramSocket;
    }

    public void setDatagramSocket(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }



}




















