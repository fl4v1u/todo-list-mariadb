import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Login extends JFrame {
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton loginButton;
    private JPanel panel1;

    Logindaten logindaten = new Logindaten();// Deine Klasse mit DB und Vergleich()

    public Login() {
        panel1 = new JPanel();
        panel1.setLayout(null);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(20, 20, 80, 25);
        panel1.add(emailLabel);

        textField1 = new JTextField();
        textField1.setBounds(100, 20, 200, 25);
        panel1.add(textField1);

        JLabel passLabel = new JLabel("Passwort:");
        passLabel.setBounds(20, 60, 80, 25);
        panel1.add(passLabel);

        passwordField1 = new JPasswordField();
        passwordField1.setBounds(100, 60, 200, 25);
        panel1.add(passwordField1);

        loginButton = new JButton("Login");
        loginButton.setBounds(100, 100, 100, 30);
        panel1.add(loginButton);

        setContentPane(panel1);
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // DB-Verbindung öffnen
        logindaten.DBverbindung();

        // Login-Button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String emailInput = textField1.getText();
                String passwordInput = new String(passwordField1.getPassword());

                // Werte in Logindaten setzen
                logindaten.setEmailanmelden(emailInput);
                logindaten.setPasswordanmelden(passwordInput);

                // Vergleich() Methode benutzen
                logindaten.UserDaten(); // Datenbank laden
                logindaten.Vergleich(); // Login prüfen

            }
        });
    }
}