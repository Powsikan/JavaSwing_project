package operation;

import javax.swing.*;
import java.awt.*;

public class Personal_Edit extends JFrame {
    public Personal_Edit() {
        super("Personal Data Edit Frame");
        setSize(300, 360);
        setResizable(false);
        setLayout(null);

        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension windowsize = getSize();

        this.setBounds((screensize.width - windowsize.width) / 2, (screensize.height - windowsize.height) / 2, windowsize.width, windowsize.height);
    }
}
