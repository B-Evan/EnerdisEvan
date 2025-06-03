package fr.btsciel;

import jssc.SerialPortException;

public class MainRPI {
    public static void main() throws InterruptedException, SerialPortException {
        Rn2483 app = new Rn2483("/dev/ttyAMA0");
        app.listerLesPorts().forEach((port) -> {
            System.out.println(port);
        });
        app.initialisation();
        //app.Lecture_DevEui();
        //app.Lecture_Appeui();
        app.OTAA( "70B3D57050002025","BBE58786DC222A04AFBB867792B1B85C");
       int nbs=0;
        Thread.sleep(5000);
       Data.DataSQL();
        while(nbs<10){
            //app.ecrirePayLoad("34342E323134");
            app.ecrirePayLoad(TtnSender.getLastTensionAsHexStringFormat());
            Thread.sleep(60*1000);
            nbs++;
        }
        app.serialPort.closePort();

    }
}