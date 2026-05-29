import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        // Hauptfenster
        JFrame frame = new JFrame("Regristieren/Anmelden");
        frame.setSize(250, 220);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
// test commit und push mit bug fix
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(50, 50, 120, 30);
        panel.add(loginButton);

        JButton regristrierenButton = new JButton("Registrieren");
        regristrierenButton.setBounds(50, 100, 120, 30);
        panel.add(regristrierenButton);

        frame.setContentPane(panel);
        frame.setVisible(true);

        // ActionListener Login
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login = new Login();
                login.setVisible(true);
            }
        });

        // ActionListener Registrieren
        regristrierenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Regristieren regristieren = new Regristieren();
                regristieren.setVisible(true);
            }
        });
    }
}