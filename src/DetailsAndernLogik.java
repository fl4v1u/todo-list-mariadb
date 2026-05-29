import javax.swing.*;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DetailsAndernLogik {
    private String email;
    private String password;
    private String neueEmail;
    private Connection conn1;
Logindaten logindaten=new Logindaten();

    public int HashPassword(){
        int HashLoschenPasswort=password.hashCode();
        return HashLoschenPasswort;

    }

    public boolean Vergleich(){
        boolean TrueOrFalse;
        boolean TrueOrFalse1;

        if(email.equals(logindaten.getDBuserDaten())){
            TrueOrFalse=true;
        }else{
            TrueOrFalse=false;
        }
        if(HashPassword()== logindaten.HashPasswordAnmeldenMethode()){
            TrueOrFalse1=true;
        }else{
            TrueOrFalse1=false;
        }

        if (TrueOrFalse1 && TrueOrFalse){
            System.out.println("Konto löschen");
            return true;
        }else{
            System.out.println("Konto wird nicht gelöscht");
            return false;
        }
    }

    public void EmailAndernMethode(String alteEmail, String passwort, String neueEmail) {

        logindaten.DBverbindung();
        Connection conn = logindaten.getConnection();
        try {

            String sql = "UPDATE user SET email = ? WHERE email = ? AND password_hash = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, neueEmail);
            pstmt.setString(2, alteEmail);
            pstmt.setInt(3, passwort.hashCode());

            int rows = pstmt.executeUpdate();

            if(rows > 0){
                System.out.println("Email erfolgreich geändert");
            } else {
                System.out.println("Email oder Passwort falsch");
            }

        } catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void PasswordAndernMethode(String Email, String altesPasswort, String neuesPassword) {

        logindaten.DBverbindung();
        Connection conn = logindaten.getConnection();

        try {

            String sql = "UPDATE user SET password_hash = ? WHERE email = ? AND password_hash = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, neuesPassword.hashCode());      // neues Passwort speichern
            pstmt.setString(2, Email);                      // Email prüfen
            pstmt.setInt(3, altesPasswort.hashCode());      // altes Passwort prüfen

            int rows = pstmt.executeUpdate();

            if(rows > 0){
                System.out.println("Password erfolgreich geändert");
            } else {
                System.out.println("Email oder Passwort falsch");
            }

        } catch(SQLException e){
            e.printStackTrace();
        }
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getNeueEmail() {
        return neueEmail;
    }

    public void setNeueEmail(String neueEmail) {
        this.neueEmail = neueEmail;
    }
}
