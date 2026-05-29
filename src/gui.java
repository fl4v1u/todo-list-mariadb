import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

// Klasse für einen Todo-Eintrag
class TodoItem {
    private String text;
    private boolean selected;

    public TodoItem(String text) {
        this.text = text;
        this.selected = false;
    }

    public String getText() { return text; }
    public boolean isSelected() { return selected; }
    public void setSelected(boolean selected) { this.selected = selected; }

    @Override
    public String toString() { return text; }
}

// Renderer für JList, damit Checkbox angezeigt wird
class TodoItemRenderer extends JPanel implements ListCellRenderer<TodoItem> {
    private JCheckBox checkBox = new JCheckBox();
    private JLabel label = new JLabel();

    public TodoItemRenderer() {
        setLayout(new BorderLayout());
        add(checkBox, BorderLayout.WEST);
        add(label, BorderLayout.CENTER);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends TodoItem> list,
                                                  TodoItem value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        checkBox.setSelected(value.isSelected());
        label.setText(value.getText());
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        return this;
    }
}

public class gui extends JFrame {
    private JTextField textField;
    private JButton addButton;
    private JButton deleteButton;
    private DefaultListModel<TodoItem> model;
    private JList<TodoItem> todoList;
    private Connection conn;
    private JPanel panel1;
    private JPanel jpanel2;


    // ID des aktuell eingeloggten Nutzers
    private int currentUserId;

    public int getCurrentUserId() {
        return currentUserId;
    }

    public gui(int userId) {
        super("TODO LISTE");
        this.currentUserId = userId;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        // Verbindung zur Datenbank
        Logindaten logindaten = new Logindaten();
        logindaten.DBverbindung();
        conn = logindaten.getConnection();

        panel1 = new JPanel(new BorderLayout());

        // Oben: Textfeld + Hinzufügen-Button + Einstellungen-Button
        JPanel topPanel = new JPanel();
        textField = new JTextField(15); // kleiner, damit Platz für beide Buttons
        addButton = new JButton("Hinzufügen");
        JButton settingsButton = new JButton("Einstellungen"); // Neuer Button

        topPanel.add(textField);
        topPanel.add(addButton);
        topPanel.add(settingsButton);

        panel1.add(topPanel, BorderLayout.NORTH);

        // Mitte: JList mit ScrollPane
        model = new DefaultListModel<>();
        todoList = new JList<>(model);
        todoList.setCellRenderer(new TodoItemRenderer());
        todoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        todoList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = todoList.locationToIndex(e.getPoint());
                if (index >= 0) {
                    TodoItem item = model.get(index);
                    item.setSelected(!item.isSelected());
                    todoList.repaint();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(todoList);
        panel1.add(scrollPane, BorderLayout.CENTER);

        // Unten: Löschen-Button
        deleteButton = new JButton("Löschen");
        panel1.add(deleteButton, BorderLayout.SOUTH);

        setContentPane(panel1);
        setVisible(true);

        // Aktionen
        loadTodosFromDB();
        addButton.addActionListener(e -> addTodo());
        deleteButton.addActionListener(e -> deleteSelectedTodos());

        // ActionListener für Einstellungen-Button
        settingsButton.addActionListener(e -> openSettings());
    }

    private void loadTodosFromDB() {
        try {
            String sql = "SELECT * FROM todos WHERE uid = ? ORDER BY tid";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, currentUserId);
            ResultSet rs = pstmt.executeQuery();
            model.clear();
            while (rs.next()) {
                String titel = rs.getString("titel");
                model.addElement(new TodoItem(titel));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addTodo() {
        String text = textField.getText();
        if (!text.isEmpty()) {
            try {
                String sql = "INSERT INTO todos (uid, titel) VALUES (?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, currentUserId);
                pstmt.setString(2, text);
                pstmt.executeUpdate();

                model.addElement(new TodoItem(text));
                textField.setText("");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteSelectedTodos() {
        try {
            for (int i = model.getSize() - 1; i >= 0; i--) {
                TodoItem item = model.get(i);
                if (item.isSelected()) {
                    String sql = "DELETE FROM todos WHERE uid = ? AND titel = ?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, currentUserId);
                    pstmt.setString(2, item.getText());
                    pstmt.executeUpdate();
                    model.remove(i);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void openSettings() {
        // Einfaches Einstellungsfenster
        Einstellungen einstellungen = new Einstellungen();
        einstellungen.setVisible(true);

    }

    public static void main(String[] args) {
        Logindaten logindaten = new Logindaten();
        SwingUtilities.invokeLater(() -> new gui(logindaten.getUserID()));
    }
}