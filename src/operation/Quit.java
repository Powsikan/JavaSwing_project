package operation;

import javax.swing.*;

public class Quit {
    public Quit() {
    }

    public void onQuit() {
        JOptionPane.showConfirmDialog(null, "Do you want to Exit", "Confirmation", JOptionPane.YES_NO_OPTION);
        System.exit(0);
    }
}
