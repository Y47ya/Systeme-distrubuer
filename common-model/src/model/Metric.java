package model;

import java.io.Serializable;

public class Metric implements Serializable {

    private String agentId;
    private double cpuUsage;
    private double totalCpu;
    private double ramUsage;
    private double ramSize;
    private double diskUsage;
    private double diskSize;
    private String timestamp;

    public Metric(String agentId, double cpuUsage, double ramUsage, double diskUsage, String timestamp, double diskSize, double totalCpu, double ramSize) {
        this.agentId = agentId;
        this.cpuUsage = cpuUsage;
        this.ramUsage = ramUsage;
        this.diskUsage = diskUsage;
        this.timestamp = timestamp;
        this.diskSize = diskSize;
        this.totalCpu = totalCpu;
        this.ramSize = ramSize;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public double getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(double cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public double getRamUsage() {
        return ramUsage;
    }

    public void setRamUsage(double ramUsage) {
        this.ramUsage = ramUsage;
    }

    public double getDiskUsage() {
        return diskUsage;
    }

    public void setDiskUsage(double diskUsage) {
        this.diskUsage = diskUsage;
    }

    public String  getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public double getDiskSize() {
        return diskSize;
    }

    public void setDiskSize(double diskSize) {
        this.diskSize = diskSize;
    }

    public double getTotalCpu() {
        return totalCpu;
    }

    public void setTotalCpu(double totalCpu) {
        this.totalCpu = totalCpu;
    }

    public double getRamSize() {
        return ramSize;
    }

    public void setRamSize(double ramSize) {
        this.ramSize = ramSize;
    }

    @Override
    public String toString() {
        return "Metric{" +
                "agentId='" + agentId + '\'' +
                ", cpuUsage=" + cpuUsage +
                ", totalCpu=" + totalCpu +
                ", ramUsage=" + ramUsage +
                ", ramSize=" + ramSize +
                ", diskUsage=" + diskUsage +
                ", diskSize=" + diskSize +
                ", timestamp=" + timestamp +
                '}';
    }
}
