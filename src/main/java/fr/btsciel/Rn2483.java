package fr.btsciel;

import fr.btsciel.LaisonSerie.LiaisonSerie;
import jssc.SerialPortEvent;
import jssc.SerialPortException;

import java.nio.charset.StandardCharsets;


public class Rn2483 extends LiaisonSerie {

    private String port;


    public Rn2483(String port) {
        this.port = port;
    }

    public void initialisation() throws SerialPortException, InterruptedException {
        super.initCom(port);
        // 57600 bps, 8 bits, no parity, 1 Stop bit,
        super.configurerParametres(57600, 8, 0, 1);
        Thread.sleep(1000);
    }

    public void ecrire(byte[] trame) {
        super.ecrire(trame);
    }

    public void execute_cmd(String cmd) {
        try {
            super.ecrire(cmd.getBytes(StandardCharsets.UTF_8));
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void Lecture_DevEui(){
        System.out.println("DevEUI ->");
        execute_cmd("sys get hweui\r\n");
    }

    public void Lecture_Appeui(){
        System.out.println("AppEUI ->");
        execute_cmd("mac get appeui\r\n");
    }
    public void OTAA(String App_Eui,String App_Key){
        System.out.println("DevEUI ->");
        execute_cmd("sys get hweui\r\n");
        System.out.println("set du AppEui ->");
        execute_cmd("mac set appeui "+App_Eui+"\r\n");
        System.out.println("set du AppKey ->");
        execute_cmd("mac set appkey "+App_Key+"\r\n");
        System.out.println("Sauvegarde des parametrages...");
        execute_cmd("mac save\r\n");
        System.out.println("Connexion en live data...");
        execute_cmd("mac join otaa\r\n");
    }
/////////////////////////////////////////Start Module///////////////////////////////////////////////

    public void Get_Version() throws InterruptedException {
        super.ecrire("sys get ver\r\n".getBytes(StandardCharsets.UTF_8)); // Version du rs2483
        Thread.sleep(1000);
    }

    public void Set_Raz() throws InterruptedException {
        super.ecrire("sys reset\r\n".getBytes(StandardCharsets.UTF_8)); // Reset du rs2483
        Thread.sleep(2000);
    }

////////////////////////////////////// Initialisation Lorawan /////////////////////////////////////


    public void Set_mac_ADR() throws InterruptedException {
        super.ecrire("adr\r\n".getBytes(StandardCharsets.UTF_8));  // Recevoire l'addresse MAC
        Thread.sleep(1000);
    }

    public void Set_mac_appIdentifier() throws InterruptedException {
        super.ecrire("appui\r\n".getBytes(StandardCharsets.UTF_8));  // Definit la clé d'identification
        Thread.sleep(1000);
    }

    public void Set_mac_appKey() throws InterruptedException {
        super.ecrire("appkey\r\n".getBytes(StandardCharsets.UTF_8));  // Definit la clé d'application pour le module RN2483
        Thread.sleep(1000);
    }

    public void Set_mac_classWan() throws InterruptedException {
        super.ecrire("class\r\n".getBytes(StandardCharsets.UTF_8));  // Définit la classe opérationnelle LoRaWAN
        Thread.sleep(1000);
    }


////////////////////////////////////////////// Lire la trame ASCI //////////////////////////////

    //ecrire payLoad (cnf –confirmed, uncnf – unconfirmed)
    public void ecrirePayLoad(String payloadHex) {
        System.out.println("Ecriture du payload...");
        String confirmation = "uncnf";
        String port = "1";
        //si payload doit etre en ascii
        execute_cmd(String.format("mac tx %s %s %s\r\n", confirmation, port, payloadHex));
    }


    public void serialEvent(SerialPortEvent event) {
       byte[] laTrame;
       int longueur = 0;
       longueur = event.getEventValue();
       laTrame = super.lireTrame(longueur);

        System.out.println("""
                 ______________
                |  Reception   |
                |______________|
               
                   | ASCII       -> %s    
                """.formatted(new String(laTrame, StandardCharsets.US_ASCII)));

    }
}