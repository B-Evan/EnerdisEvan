package fr.btsciel;

import jssc.SerialPortException;

import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws InterruptedException, SerialPortException {
        Application app = new Application("/dev/ttyAMA0");
        app.listerLesPorts().forEach((port) -> {
            System.out.println(port);
        });
        app.initialisation();
        app.Get_Version();
        Thread.sleep(5000);
        app.serialPort.closePort();

    }
}