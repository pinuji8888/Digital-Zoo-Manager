import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/** Application entry point. Swing components are created on the event dispatch thread. */
public final class Main {
    private Main() { }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ignored) {
                // The default Swing look and feel is a safe fallback.
            }
            new ZooFrame().setVisible(true);
        });
    }
}
