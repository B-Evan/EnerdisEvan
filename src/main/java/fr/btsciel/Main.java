package fr.btsciel;

import jssc.SerialPortException;

public class Main {
    public static void main(String[] args) {


        Thread threadRPI = new Thread(new Runnable() {
            public void run() {
                try {
                    MainRPI.main();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (SerialPortException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread threadModBus = new Thread(new Runnable() {
            public void run() {
                try {
                    MainModBus.main();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (SerialPortException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        threadRPI.start();
        threadModBus.start();
    }
}