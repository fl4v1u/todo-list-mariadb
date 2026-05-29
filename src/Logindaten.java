import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Logindaten {
    private String emailregistrieren;
    private String passwordregristrieren;
    private String emailanmelden;
    private String passwordanmelden;
    private int userID;
    private Connection conn1;
    private String DBuserDaten;
    private String DBuserPassword;
    static private String password ="1234";
    static private String user ="root";

    public void DBverbindung(){
        try {
             conn1 = DriverManager.getConnection("jdbc:mariadb://localhost:3307/todo", user, password);
            System.out.println("Verbunden");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int HashPasswordRegristrierenMethode(){
        int HashPasswordRegristrieren=getPasswordregristrieren().hashCode();
        return HashPasswordRegristrieren;
    }

    public int HashPasswordAnmeldenMethode(){
         int HashPasswordAnmeleden=getPasswordanmelden().hashCode();
        return HashPasswordAnmeleden;
    }

    //datenversenden an der db
    public void UserRegristrieren() {
        String sql = "INSERT INTO user (email, password_hash) VALUES (?, ?)";
        try {

            PreparedStatement pstmt = conn1.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, emailregistrieren);
            pstmt.setInt(2, HashPasswordRegristrierenMethode()); // Klartext-Passwort
            int rows = pstmt.executeUpdate(); // Daten an DB senden
            if (rows > 0) {
                ResultSet keys = pstmt.getGeneratedKeys();
                if (keys.next()) {
                    int userId = keys.getInt(1);
                    System.out.println("Nutzer registriert! ID: " + userId);
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Diese Email ist bereits registriert!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Daten aus USER entnehmen
    public void UserDaten() {
        String sql = "SELECT user_id, email, password_hash FROM user";
        try {
            PreparedStatement pstmt = conn1.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) { // für jede Zeile in der Tabelle
                int userId = rs.getInt("user_id");
                setUserID(userId);
                String emailUser = rs.getString("email");
                String passwordUser = rs.getString("password_hash");
                setDBuserDaten(emailUser);
                setDBuserPassword(passwordUser);
                System.out.println("ID: " + userId + ", Email: " + emailUser + ", Passwort: " + passwordUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Vergleich() {
        try {
            String sql = "SELECT user_id, password_hash FROM user WHERE email = ?";
            PreparedStatement pstmt = conn1.prepareStatement(sql);
            pstmt.setString(1, getEmailanmelden());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int dbPassword = rs.getInt("password_hash");
                if (dbPassword==HashPasswordAnmeldenMethode()) {
                    System.out.println("Login Erfolgreich");
                    int userId = rs.getInt("user_id");
                    gui gui = new gui(userId);
                    gui.setVisible(true);
                } else {
                    System.out.println("Falsches Passwort!");
                }
            } else {
                System.out.println("Nutzer nicht gefunden!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getDBuserDaten() {
        return DBuserDaten;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setDBuserDaten(String DBuserDaten) {
        this.DBuserDaten = DBuserDaten;
    }

    public void setDBuserPassword(String DBuserPassword) {
        this.DBuserPassword = DBuserPassword;
    }

    public void setEmailregistrieren(String emailregistrieren) {
        this.emailregistrieren = emailregistrieren;
    }

    public String getPasswordregristrieren() {
        return passwordregristrieren;
    }

    public void setPasswordregristrieren(String passwordregristrieren) {
        this.passwordregristrieren = passwordregristrieren;
    }

    public String getEmailanmelden() {
        return emailanmelden;
    }

    public void setEmailanmelden(String emailanmelden) {
        this.emailanmelden = emailanmelden;
    }

    public String getPasswordanmelden() {
        return passwordanmelden;
    }

    public void setPasswordanmelden(String passwordanmelden) {
        this.passwordanmelden = passwordanmelden;
    }
    public Connection getConnection() {
        return conn1;
    }
}
