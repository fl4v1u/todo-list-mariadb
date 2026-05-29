import javax.swing.*;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
public class LoschenLogik {
    private String LoschenEmail;
    private String LoschenPassword;
    private Connection conn1;
    Logindaten logindaten = new Logindaten();

    public int HashPasswordLoschen(){
        int HashLoschenPasswort=LoschenPassword.hashCode();
        return HashLoschenPasswort;

    }

    public void userLoeschen() {

        logindaten.DBverbindung();
        conn1 = logindaten.getConnection();

        try {

            String findUser =
                    "SELECT user_id FROM user WHERE email = ? AND password_hash = ?";

            PreparedStatement findStmt =
                    conn1.prepareStatement(findUser);

            findStmt.setString(1, LoschenEmail);
            findStmt.setInt(2, HashPasswordLoschen());

            ResultSet rs = findStmt.executeQuery();

            if (rs.next()) {

                int userId = rs.getInt("user_id");

                String deleteTodos =
                        "DELETE FROM todos WHERE uid = ?";

                PreparedStatement todoStmt =
                        conn1.prepareStatement(deleteTodos);

                todoStmt.setInt(1, userId);
                todoStmt.executeUpdate();

                String deleteUser =
                        "DELETE FROM user WHERE user_id = ?";

                PreparedStatement userStmt =
                        conn1.prepareStatement(deleteUser);

                userStmt.setInt(1, userId);
                userStmt.executeUpdate();

                System.out.println("Nutzer erfolgreich gelöscht");

            } else {

                System.out.println("Email oder Passwort falsch");

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }
    }


    public String getLoschenEmail() {
        return LoschenEmail;
    }

    public void setLoschenEmail(String loschenEmail) {
        LoschenEmail = loschenEmail;
    }

    public String getLoschenPassword() {
        return LoschenPassword;
    }

    public void setLoschenPassword(String loschenPassword) {
        LoschenPassword = loschenPassword;
    }

}
