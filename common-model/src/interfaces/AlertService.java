package interfaces;

import model.Alert;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface AlertService extends Remote {

    List<Alert> getAlertsByAgent(String agentId) throws RemoteException;
    List<Alert> getLastNAlerts(String agentId, int n, String orderWay) throws RemoteException;

}
