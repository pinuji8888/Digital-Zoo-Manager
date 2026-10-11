import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/** Exercises the actual Swing form on its event thread, without external libraries. */
public final class ZooGuiTests {
    public static void main(String[] args) throws Exception {
        if (GraphicsEnvironment.isHeadless()) {
            System.out.println("SKIP: GUI checks require a desktop display.");
            return;
        }
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        SwingUtilities.invokeAndWait(() -> {
            ZooFrame frame = new ZooFrame();
            try {
                JTextField name = (JTextField) find(frame, "name");
                JTextField age = (JTextField) find(frame, "age");
                JTextField fur = (JTextField) find(frame, "furColor");
                JComboBox<?> type = (JComboBox<?>) find(frame, "type");
                JCheckBox fly = (JCheckBox) find(frame, "canFly");
                JButton add = (JButton) find(frame, "add");
                JTextArea log = (JTextArea) find(frame, "log");
                JLabel count = (JLabel) find(frame, "count");
                JLabel status = (JLabel) find(frame, "status");
                check(!log.isEditable(), "Log must be read-only");
                String initialLog = log.getText();
                add.doClick();
                check(status.getText().contains("Name is required"), "Blank name rejected");
                name.setText("Leo");
                fur.setText("Golden");
                for (String invalidAge : new String[] {"", "abc", "1.5", "-1", "2147483648"}) {
                    age.setText(invalidAge);
                    add.doClick();
                    check(initialLog.equals(log.getText()), "Invalid age must not add to log");
                    check("0 animals".equals(count.getText()), "Invalid age must not change count");
                }
                age.setText("5");
                fur.setText(" ");
                add.doClick();
                check(status.getText().contains("Fur color is required"), "Blank fur color rejected");
                check(initialLog.equals(log.getText()), "Invalid fur color must not add to log");
                fur.setText("Golden");
                add.doClick();
                check(log.getText().contains("Name: Leo, Age: 5, Fur Color: Golden"), "Mammal details logged");
                check(log.getText().contains("The mammal makes a sound."), "Mammal sound logged");
                check("1 animal".equals(count.getText()), "Mammal counted");
                check(name.getText().isEmpty() && age.getText().isEmpty(), "Form resets on success");

                type.setSelectedItem("Generic Bird");
                check(!fur.getParent().isVisible() && fly.getParent().isVisible(), "Bird-specific controls shown");
                name.setText("Rio");
                age.setText("2");
                fly.setSelected(true);
                add.doClick();
                check(log.getText().contains("Name: Rio, Age: 2, Can Fly: Yes"), "Flying bird logged");
                check(log.getText().contains("The bird chirps."), "Bird sound logged");
                name.setText("Pingu");
                age.setText("0");
                add.doClick();
                check(log.getText().contains("Name: Pingu, Age: 0, Can Fly: No"), "Non-flying newborn logged");
                check("3 animals".equals(count.getText()), "Mixed animal collection counted");
                type.setSelectedItem("Generic Mammal");
                check(fur.getParent().isVisible() && !fly.getParent().isVisible(), "Mammal controls restored");

                frame.validate();
                Container content = frame.getContentPane();
                BufferedImage preview = new BufferedImage(content.getWidth(), content.getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = preview.createGraphics();
                content.printAll(graphics);
                graphics.dispose();
                try {
                    ImageIO.write(preview, "png", new File("build/gui-preview.png"));
                } catch (java.io.IOException exception) {
                    throw new RuntimeException(exception);
                }
                System.out.println("PASS: GUI add flows, validation, type switching, log, and count.");
            } finally {
                frame.dispose();
            }
        });
    }

    private static Component find(Container parent, String name) {
        for (Component component : parent.getComponents()) {
            if (name.equals(component.getName())) {
                return component;
            }
            if (component instanceof Container) {
                Component match = find((Container) component, name);
                if (match != null) {
                    return match;
                }
            }
        }
        return null;
    }

    private static void check(boolean condition, String description) {
        if (!condition) {
            throw new AssertionError(description);
        }
    }
}
