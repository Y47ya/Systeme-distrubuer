package model;

import java.io.Serializable;

public class Alert implements Serializable {

    private String agentId;
    private String type;
    private double value;
    private double threshold;
    private String timestamp;
    private static double cpuThreshold = 0.9;
    private static double ramThreshold = 0.85;
    private static double diskThreshold = 0.9;

    public Alert(String agentId, String type, double value, double threshold, String timestamp) {
        this.agentId = agentId;
        this.type = type;
        this.value = value;
        this.threshold = threshold;
        this.timestamp = timestamp;
    }

    public static double getCpuThreshold() {
        return cpuThreshold;
    }

    public static void setCpuThreshold(double cpuThreshold) {
        Alert.cpuThreshold = cpuThreshold;
    }

    public static double getRamThreshold() {
        return ramThreshold;
    }

    public static void setRamThreshold(double ramThreshold) {
        Alert.ramThreshold = ramThreshold;
    }

    public static double getDiskThreshold() {
        return diskThreshold;
    }

    public static void setDiskThreshold(double diskThreshold) {
        Alert.diskThreshold = diskThreshold;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "agentId='" + agentId + '\'' +
                ", type='" + type + '\'' +
                ", value=" + value +
                ", threshold=" + threshold +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}

