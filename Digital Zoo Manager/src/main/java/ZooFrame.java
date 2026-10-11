import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/** Main view and event handling for the in-memory zoo. */
public class ZooFrame extends JFrame {
    private static final String MAMMAL = "Generic Mammal";
    private static final String BIRD = "Generic Bird";
    private static final Color INK = new Color(25, 48, 45);
    private static final Color GREEN = new Color(28, 105, 78);
    private static final Color MUTED = new Color(94, 111, 106);

    // One collection stores both subclasses: this demonstrates polymorphism.
    private final ArrayList<Animal> animals = new ArrayList<>();
    private final JTextField nameField = new JTextField(18);
    private final JTextField ageField = new JTextField(18);
    private final JComboBox<String> typeBox = new JComboBox<>(new String[] {MAMMAL, BIRD});
    private final JTextField furColorField = new JTextField(18);
    private final JCheckBox canFlyBox = new JCheckBox("Can fly?");
    private final CardLayout attributeLayout = new CardLayout();
    private final JPanel attributePanel = new JPanel(attributeLayout);
    private final JTextArea logArea = new JTextArea();
    private final JLabel countLabel = new JLabel("0 animals");
    private final JLabel statusLabel = new JLabel("Ready to welcome your first animal.");

    public ZooFrame() {
        super("Digital Zoo Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(885, 700));

        JPanel root = new JPanel(new BorderLayout(24, 24));
        root.setBackground(new Color(242, 246, 243));
        root.setBorder(new EmptyBorder(28, 28, 22, 28));
        root.add(createHeader(), BorderLayout.NORTH);
        root.add(createForm(), BorderLayout.WEST);
        root.add(createLog(), BorderLayout.CENTER);
        JLabel footer = new JLabel("SESSION STORAGE  /  Animals are kept in memory until this window closes.");
        footer.setForeground(MUTED);
        footer.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        root.add(footer, BorderLayout.SOUTH);
        setContentPane(root);

        nameField.setName("name");
        ageField.setName("age");
        typeBox.setName("type");
        furColorField.setName("furColor");
        canFlyBox.setName("canFly");
        logArea.setName("log");
        statusLabel.setName("status");
        countLabel.setName("count");
        typeBox.addActionListener(event -> {
            attributeLayout.show(attributePanel, (String) typeBox.getSelectedItem());
            statusLabel.setText("Enter the animal details below.");
            statusLabel.setForeground(MUTED);
        });
        pack();
        setLocationRelativeTo(null);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout(12, 10));
        header.setOpaque(false);
        JPanel titles = new JPanel(new BorderLayout(0, 7));
        titles.setOpaque(false);
        JLabel title = new JLabel("Digital Zoo Manager");
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 29));
        title.setForeground(INK);
        JLabel subtitle = new JLabel("A little space for every animal. Build your zoo, one arrival at a time.");
        subtitle.setForeground(MUTED);
        titles.add(title, BorderLayout.NORTH);
        titles.add(subtitle, BorderLayout.SOUTH);
        countLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        countLabel.setForeground(GREEN);
        countLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        header.add(titles, BorderLayout.CENTER);
        header.add(countLabel, BorderLayout.EAST);
        return header;
    }

    private JPanel createForm() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(219, 229, 222)),
                new EmptyBorder(22, 22, 22, 22)));
        form.setPreferredSize(new Dimension(315, 500));
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.insets = new Insets(0, 0, 8, 0);
        JLabel heading = new JLabel("Welcome an animal");
        heading.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 19));
        heading.setForeground(INK);
        form.add(heading, c);
        addField(form, c, "Name", nameField);
        addField(form, c, "Age (years)", ageField);
        addField(form, c, "Animal type", typeBox);

        JPanel mammalDetails = new JPanel(new BorderLayout(0, 6));
        mammalDetails.setOpaque(false);
        JLabel furLabel = new JLabel("Fur color");
        furLabel.setLabelFor(furColorField);
        mammalDetails.add(furLabel, BorderLayout.NORTH);
        mammalDetails.add(furColorField, BorderLayout.CENTER);
        JPanel birdDetails = new JPanel(new BorderLayout());
        birdDetails.setOpaque(false);
        canFlyBox.setOpaque(false);
        birdDetails.add(canFlyBox, BorderLayout.CENTER);
        attributePanel.setOpaque(false);
        attributePanel.add(mammalDetails, MAMMAL);
        attributePanel.add(birdDetails, BIRD);
        c.gridy++;
        form.add(attributePanel, c);

        JButton addButton = new JButton("Add Animal");
        addButton.setName("add");
        addButton.setBackground(GREEN);
        addButton.setForeground(Color.WHITE);
        addButton.setOpaque(true);
        addButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        addButton.setMargin(new Insets(12, 18, 12, 18));
        addButton.addActionListener(event -> addAnimal());
        getRootPane().setDefaultButton(addButton);
        c.gridy++;
        c.insets = new Insets(10, 0, 8, 0);
        form.add(addButton, c);
        statusLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
        statusLabel.setForeground(MUTED);
        statusLabel.getAccessibleContext().setAccessibleName("Form status");
        c.gridy++;
        c.weighty = 1;
        form.add(statusLabel, c);
        return form;
    }

    private void addField(JPanel form, GridBagConstraints c, String label, javax.swing.JComponent field) {
        JLabel caption = new JLabel(label);
        caption.setLabelFor(field);
        c.gridy++;
        form.add(caption, c);
        c.gridy++;
        field.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(240, 31));
        form.add(field, c);
    }

    private JPanel createLog() {
        JPanel panel = new JPanel(new BorderLayout(0, 14));
        panel.setOpaque(false);
        JLabel heading = new JLabel("Zoo activity");
        heading.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 19));
        heading.setForeground(INK);
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        logArea.setBackground(new Color(25, 48, 45));
        logArea.setForeground(new Color(231, 242, 233));
        logArea.setCaretColor(Color.WHITE);
        logArea.setMargin(new Insets(20, 20, 20, 20));
        logArea.setText("YOUR ZOO STARTS HERE\n\nAdd a mammal or a bird using the form.\n"
                + "Each arrival's details and sound will appear here.\n\n");
        logArea.getAccessibleContext().setAccessibleName("Animal information and sound log");
        JScrollPane scroll = new JScrollPane(logArea);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setPreferredSize(new Dimension(490, 420));
        panel.add(heading, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private void addAnimal() {
        try {
            String name = Animal.requireText(nameField.getText(), "Name");
            int age;
            try {
                age = Integer.parseInt(ageField.getText().trim());
            } catch (NumberFormatException exception) {
                throw new IllegalArgumentException("Age must be a valid whole number.");
            }

            Animal animal;
            String type;
            if (MAMMAL.equals(typeBox.getSelectedItem())) {
                animal = new Mammal(name, age, furColorField.getText());
                type = "Mammal";
            } else {
                animal = new Bird(name, age, canFlyBox.isSelected());
                type = "Bird";
            }
            animals.add(animal);
            // Runtime dispatch calls the subclass overrides through an Animal reference.
            logArea.append("Added a new " + type + "!\nInfo: " + animal.getInfo()
                    + ".\nSound: " + animal.makeSound() + "\n\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
            countLabel.setText(animals.size() + (animals.size() == 1 ? " animal" : " animals"));
            statusLabel.setForeground(GREEN);
            statusLabel.setText("Animal added. Ready for the next arrival.");
            nameField.setText("");
            ageField.setText("");
            furColorField.setText("");
            canFlyBox.setSelected(false);
            nameField.requestFocusInWindow();
        } catch (IllegalArgumentException exception) {
            statusLabel.setForeground(new Color(165, 49, 49));
            statusLabel.setText(exception.getMessage());
        }
    }
}
