import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Details extends JFrame {

    private JButton löschenButton;
    private JButton detailsAndernButton;
    private JPanel panel;

    public Details() {
        // Panel erstellen
        panel = new JPanel();
        panel.setLayout(null); // Null-Layout wie im Login-Fenster

        // "Löschen"-Button
        löschenButton = new JButton("Email Ändern");
        löschenButton.setBounds(50, 50, 120, 30); // x, y, width, height
        panel.add(löschenButton);

        // "Details ändern"-Button
        detailsAndernButton = new JButton("Password ändern");
        detailsAndernButton.setBounds(50, 100, 120, 30);
        panel.add(detailsAndernButton);

        // Panel setzen
        setContentPane(panel);
        setSize(200, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Fenster zentrieren
        setVisible(true);

        // ActionListener für Buttons
        löschenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Email gedrückt");
                // Hier Logik zum Löschen einfügen
                EmailAndern emailAndern=new EmailAndern();
                emailAndern.setVisible(true);

            }
        });

        detailsAndernButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Password ändern gedrückt");
                // Hier Logik zum Ändern der Details einfügen
                PasswordAndern passwordAndern=new PasswordAndern();
                passwordAndern.setVisible(true);

            }
        });
    }

    // Test-Main
    public static void main(String[] args) {
        new Details();
    }
}