import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Einstellungen extends JFrame {

    private JButton löschenButton;
    private JButton detailsAndernButton;
    private JPanel panel;

    public Einstellungen() {
        // Panel erstellen
        panel = new JPanel();
        panel.setLayout(null); // Null-Layout wie im Login-Fenster

        // "Löschen"-Button
        löschenButton = new JButton("Löschen");
        löschenButton.setBounds(50, 50, 120, 30); // x, y, width, height
        panel.add(löschenButton);

        // "Details ändern"-Button
        detailsAndernButton = new JButton("Details ändern");
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
                System.out.println("Löschen gedrückt");
                loschen loschen = new loschen();
                loschen.setVisible(true);
                // Hier Logik zum Löschen einfügen
            }
        });

        detailsAndernButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Details ändern gedrückt");
                Details details = new Details();
                details.setVisible(true);
                // Hier Logik zum Ändern der Details einfügen
            }
        });
    }

    // Test-Main
    public static void main(String[] args) {
        new Einstellungen();
    }
}