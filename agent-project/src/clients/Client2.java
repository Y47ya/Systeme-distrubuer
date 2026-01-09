package clients;

import model.Agent;
import model.Alert;
import model.Metric;
import service.SystemMetricCollector;

import java.net.ConnectException;
import java.util.List;

public class Client2 {

    public static void main(String[] args) throws Exception {

        try {
            String name = "client2";

            Agent agent = new Agent(
                    name,
                    "x.x.x.3",
                    "online",
                    123
            );

            System.out.println("Client " + name + " is connected");

            SystemMetricCollector agentController = new SystemMetricCollector(agent, "localhost", 1212);

            Thread thread = new Thread(agentController);
            thread.start();

            List<Alert> alerts = agentController.getAlertsByAgent("2e037da2-1609-40bd-b24a-f2f7615ef991");
            List<Metric> metrics = agentController.getLastNMetrics("2e037da2-1609-40bd-b24a-f2f7615ef991", 3, "desc");

            for (Alert alert : alerts) {
                System.out.println(alert.toString());
            }
            System.out.println("---------------------");
            for (Metric metric : metrics) {
                System.out.println(metric.toString());
            }

        } catch (ConnectException e) {
            System.out.println("Failed to connect to...");
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }

        Thread.currentThread().join();
    }

}
