package interfaces;

import model.Metric;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface MetricService extends Remote {

    List<Metric> getMetricsByAgent(String agentId) throws RemoteException;
    List<Metric> getLastNMetrics(String agentId, int n, String orderWay) throws RemoteException;

}
