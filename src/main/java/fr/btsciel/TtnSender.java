package fr.btsciel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class TtnSender {

    private static final String URL = "jdbc:sqlite:/home/install/Desktop/Enerdis/Desktop/rpi.sqlite";

    public static String getLastTensionAsHexStringFormat() {
        String hex = "";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT intensite FROM volt ORDER BY Id DESC LIMIT 1")) {

            if (rs.next()) {
                float tension = rs.getFloat("intensite");

                String tensionStr = String.format("%.2f", tension).replace(",", ".");

                StringBuilder sb = new StringBuilder();
                for (char c : tensionStr.toCharArray()) {
                    sb.append(String.format("%02X", (int) c));
                }

                hex = sb.toString();
                System.out.println("Message TTN (ASCII hex) : " + hex);
            }

        } catch (SQLException e) {
            System.out.println("Erreur base de données : " + e.getMessage());
        }

        return hex;
    }
}