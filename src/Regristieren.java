import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Regristieren extends JFrame {
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton loginButton;
    private JPanel panel1;

    Logindaten logindaten = new Logindaten();

    public Regristieren() {

       logindaten.DBverbindung();


        setContentPane(panel1);                 // FIX
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String anmeldedaten = textField1.getText();
                String password = new String(passwordField1.getPassword());

                // Email prüfen
                if (!anmeldedaten.contains("@")) {
                    JOptionPane.showMessageDialog(Regristieren.this, "Bitte eine gültige Email eingeben!");
                    return;
                }

                // DB-Verbindung öffnen
                logindaten.DBverbindung(); // conn1 wird jetzt korrekt gesetzt

                // Werte setzen
                logindaten.setEmailregistrieren(anmeldedaten);
                logindaten.setPasswordregristrieren(password);

                // In DB speichern
                logindaten.UserRegristrieren();
                JOptionPane.showMessageDialog(Regristieren.this, "Registrierung erfolgreich!");
            }
        });
    }
}