import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class EmailAndern extends JFrame {
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton loginButton;
    private JPanel panel1;
    private JTextField textField2;

    Logindaten logindaten = new Logindaten();// Deine Klasse mit DB und Vergleich()
DetailsAndernLogik detailsAndernLogik=new DetailsAndernLogik();
    public EmailAndern() {
        panel1 = new JPanel();
        panel1.setLayout(null);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(20, 20, 80, 25);
        panel1.add(emailLabel);

        textField1 = new JTextField();
        textField1.setBounds(100, 20, 200, 25);
        panel1.add(textField1);

        textField2=new JTextField();
        textField2.setBounds(100,100,200,25);
        panel1.add(textField2);

        JLabel passLabel = new JLabel("Passwort:");
        passLabel.setBounds(20, 60, 80, 25);
        panel1.add(passLabel);

        JLabel neueMailLabel= new JLabel("Neue Email Adresse:");
        neueMailLabel.setBounds(20,100,80,25);
        panel1.add(neueMailLabel);

        passwordField1 = new JPasswordField();
        passwordField1.setBounds(100, 60, 200, 25);
        panel1.add(passwordField1);

        loginButton = new JButton("Ändern");
        loginButton.setBounds(100, 130, 100, 30);
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
                String emailInputNeueMail=textField2.getText();

                // Werte in Logindaten setzen
                detailsAndernLogik.setEmail(emailInput);
                detailsAndernLogik.setPassword(passwordInput);
                detailsAndernLogik.EmailAndernMethode(emailInput,passwordInput,emailInputNeueMail);

            }
        });
    }
}