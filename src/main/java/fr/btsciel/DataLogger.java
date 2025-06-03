package fr.btsciel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DataLogger {

    private static final String URL = "jdbc:sqlite:/home/install/Desktop/Enerdis/Desktop/rpi.sqlite";

    public static void insertMesure(float tension, float frequence, float puissance, float intensite) {
        String sql = "INSERT INTO volt(tension, frequence, puissance, intensite, date) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setFloat(1, tension);
            pstmt.setFloat(2, frequence);
            pstmt.setFloat(3, puissance);
            pstmt.setFloat(4, intensite);
            pstmt.setString(5, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            pstmt.executeUpdate();
            System.out.println("Donnees inserees avec succes.");

        } catch (SQLException e) {
            System.out.println("Erreur lors de l insertion : " + e.getMessage());
        }
    }
}