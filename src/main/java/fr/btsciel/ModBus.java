package fr.btsciel;

import fr.btsciel.LiaisonSerie.LiaisonSerie;
import ModBusRtu.CRC16;
import jssc.SerialPortEvent;
import jssc.SerialPortException;


import java.util.Arrays;

public class ModBus extends LiaisonSerie {
    CRC16 crc16 = new CRC16();
    Byte numeroEsclave;
    byte[] tramWithCRC16;

    public ModBus(Byte numeroEsclave) {
        this.numeroEsclave = numeroEsclave;
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void ModeBus() {
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void fermerLiasonSerie() {
        super.fermerPort();
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public byte[] intDeuxBytes(int number) {
        byte[] deuxBytes = new byte[2];
        deuxBytes[0] = (byte) ((number & 0xFF00) >> 8);
        deuxBytes[1] = (byte) (number & 0xFF);
        return deuxBytes;
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void connecterEsclave(String port, int vitesse, int donnees, int parite, int stop) throws SerialPortException {
        super.initCom(port);
        super.configurerParametres(vitesse, donnees, parite, stop);
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private Float valeurIntensite = null;  // null = pas encore reçue
    private final Object lockIntensite = new Object();

    @Override
    public void serialEvent(SerialPortEvent event) {
        try {
            Thread.sleep(500); // laisser le temps à la trame d'arriver
            byte[] trame = super.lireTrame(super.detecteSiReception());
            if (trame != null) {
                System.out.println("Reponse recue : " + formatReponseHexa(trame));
                float valeur = decoderReponse(trame);
                System.out.println("Valeur decodee : " + (valeur / 10));

                // Stocker dans valeurTension et notifier
                synchronized (lockIntensite) {
                    valeurIntensite = valeur / 10;
                    lockIntensite.notifyAll(); // Réveiller lectureFrequence
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    public float lectureCoils() throws InterruptedException {
        System.out.println("Veuillez entrer la trame RTU (en hexadécimal, sans espaces) :");
        String inputTrame = null; // In.readString();

        assert inputTrame != null;
        byte[] tramWithCRC16 = new byte[inputTrame.length() / 2];
        for (int i = 0; i < tramWithCRC16.length; i++) {
            tramWithCRC16[i] = (byte) Integer.parseInt(inputTrame.substring(2 * i, 2 * i + 2), 16);
        }
        super.ecrire(tramWithCRC16);
        Thread.sleep(1000);

        if (detecteSiReception() > 0) {
            byte[] reponse = super.lireTrame(detecteSiReception());
            return decoderReponse(reponse);
        } else {
            return 0f;
        }
    }



    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    private String formatReponseHexa(byte[] reponse) {
        StringBuilder sb = new StringBuilder();
        for (byte b : reponse) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString().trim();
    }






    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




    private float decoderReponse(byte[] reponse) {
        if (reponse.length < 5) {
            System.out.println("Trame recue invalide.");
            return 0f;
        }

        if (reponse[1] == 0x03) {
            byte[] donnees = Arrays.copyOfRange(reponse, 3, reponse.length - 2);

            int valeurRegistre = (donnees[0] << 8) | (donnees[1] & 0xFF);

            return (float) valeurRegistre;
        } else {
            System.out.println("Code fonction incorrect.");
            return 0f;
        }
    }


    private byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }


    //////////////////////////// LECTURE VALEUR ////////////////////////////



    private float lectureValeur(String inputTrame) throws InterruptedException {

        assert inputTrame != null;
        byte[] tramWithCRC16 = new byte[inputTrame.length() / 2];
        for (int i = 0; i < tramWithCRC16.length; i++) {
            tramWithCRC16[i] = (byte) Integer.parseInt(inputTrame.substring(2 * i, 2 * i + 2), 16);
        }
        super.ecrire(tramWithCRC16);
        Thread.sleep(3000);

        if (detecteSiReception() > 0) {
            byte[] reponse = super.lireTrame(detecteSiReception());
            return decoderReponse(reponse);
        } else {
        return 0f;
    }
}




                        //////////////////////////// FREQUENCE VOLT ////////////////////////////



    public float lectureFrequence() throws InterruptedException {
        System.out.println("Ecriture de la trame pour recevoir la FREQUENCE");
       return lectureValeur("010300000001840A");
   }



                        //////////////////////////// TENSION HZ ////////////////////////////



    public float lectureTension() throws InterruptedException {
        System.out.println("Ecriture de la trame pour recevoir la TENSION");
        return lectureValeur("0103000F0001B409");
    }


                        //////////////////////////// PUISSANCE KW ////////////////////////////



    public float lecturePuissance() throws InterruptedException {
        System.out.println("Ecriture de la trame pour recevoir la PUISSANCE");
        return lectureValeur("01030010000185CF");
    }



                 //////////////////////////// INTENSITE AMPERE ////////////////////////////



//    public float lectureIntensite() throws InterruptedException{
//        System.out.println("Ecriture de la trame pour recevoir l'INTENSITE");
//        return lectureValeur("01030002000125CA");
//    }

    public float lectureIntensite() throws InterruptedException {
        synchronized (lockIntensite) {
            valeurIntensite = null; // Réinitialiser

            System.out.println("Ecriture de la trame pour recevoir l'INTENSITE");
            super.ecrire(hexStringToByteArray("01030002000125CA"));

            // Attendre que serialEvent() remplisse valeurTension
            lockIntensite.wait(2000); // attendre max 2 secondes

            if (valeurIntensite != null) {
                return valeurIntensite;
            } else {
                System.out.println("Timeout : aucune réponse reçue.");
                return 0f;
            }
        }
    }

}
