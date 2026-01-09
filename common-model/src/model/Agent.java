package model;

import java.io.Serializable;
import java.util.UUID;

public class Agent implements Serializable {
    private String id;
    private String name;
    private String ipAddress;
    private String status;


    public Agent(String name, String ipAddress, String status, long lastUpdate) {
        this.id = UUID.randomUUID().toString();;
        this.name = name;
        this.ipAddress = ipAddress;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Agent{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
