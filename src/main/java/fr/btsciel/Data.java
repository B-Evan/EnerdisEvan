package fr.btsciel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Data {

    public static void DataSQL() {
        String url = "jdbc:sqlite:/home/install/Desktop/out/artifacts/Enerdis/rpi.sqlite";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM volt")) {

            while (rs.next()) {
                String resultat = rs.getInt("Id") + "," + rs.getString("data") + "," + rs.getFloat("date");
                System.out.println(resultat);
            }
        } catch (SQLException e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
        }
    }
}
