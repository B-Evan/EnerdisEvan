package fr.btsciel;

import jssc.SerialPortException;

import static java.lang.Byte.parseByte;


public class MainModBus {
    public static void main() throws InterruptedException, SerialPortException {
        System.out.println("Le numero de l'esclave : ");
        fr.btsciel.ModBus modbus ;
        modbus= new fr.btsciel.ModBus( parseByte("1") );
        System.out.println("Le COM ?");
        modbus.connecterEsclave("/dev/ttyUSB0",9600,8,0,1);
        try {
            while (true) {
                try {
                    float resultatFrequence = modbus.lectureFrequence();
                    float resultatTension = modbus.lectureTension();
                    float resultatPuissance = modbus.lecturePuissance();
                    float resultatIntensite = modbus.lectureIntensite();


                   //System.out.println("Resultat de la Tension : " + resultatTension);
                    //System.out.println("Résultat de la Frequence : " + resultatFrequence);

                    DataLogger.insertMesure(resultatTension, resultatFrequence, resultatPuissance, resultatIntensite);

                    Thread.sleep(30*1000);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }
}