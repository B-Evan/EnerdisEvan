package fr.btsciel;

import jssc.SerialPortEvent;
import jssc.SerialPortException;

import java.nio.charset.StandardCharsets;


public class Application extends LiaisonSerie {

    private String port;


    public Application(String port) {
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

    public void Get_Version() throws InterruptedException {
        super.ecrire("sys get ver\r\n".getBytes(StandardCharsets.UTF_8));
        Thread.sleep(1000);
    }

    public void raz() throws InterruptedException {
        super.ecrire("sys reset\r\n".getBytes(StandardCharsets.UTF_8));
        Thread.sleep(2000);
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
