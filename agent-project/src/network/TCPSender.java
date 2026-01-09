package network;

import model.Alert;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;


public class TCPSender implements Runnable{
    private Socket socket;
    private Alert alert;

    public TCPSender(Socket socket, Alert alert) throws IOException {
        this.alert = alert;
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Alert getAlert() {
        return alert;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
    }

    public void run(){

        try {
            Gson gson = new Gson();
            String jsonAlert = gson.toJson(this.alert);
//            System.out.println(jsonAlert);

            OutputStream out = this.socket.getOutputStream();
            out.write((jsonAlert + "\n").getBytes(StandardCharsets.UTF_8));
            out.flush();
//            System.out.println("Alert sent...");

        } catch (IOException exception) {
            System.out.println("Failed to get output stream");
        }


    }


}
