import operation.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Mainwindow extends JFrame implements ActionListener {
    JMenuBar menuBar;
    JMenu mnupers, mnuexit;
    JMenuItem add, edit, delete, view, quit;


    public Mainwindow() {
        super("Student Information System");
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screensize);
        setResizable(false);

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        mnupers = new JMenu("Personal");
        menuBar.add(mnupers);

        add = new JMenuItem("Add");
        mnupers.add(add);
        add.addActionListener(this);

        edit = new JMenuItem("Edit");
        mnupers.add(edit);
        edit.addActionListener(this);

        delete = new JMenuItem("Delete");
        mnupers.add(delete);
        delete.addActionListener(this);

        view = new JMenuItem("View");
        mnupers.add(view);
        view.addActionListener(this);

        mnuexit = new JMenu("Exit");
        menuBar.add(mnuexit);

        quit = new JMenuItem("Quit");
        mnuexit.add(quit);
        quit.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String src = e.getActionCommand();
        if (src.equalsIgnoreCase("Add")) {
            Personal_Add p_add = new Personal_Add();
            p_add.setVisible(true);
        } else if (src.equalsIgnoreCase("Edit")) {
            Personal_Edit p_edit = new Personal_Edit();
            p_edit.setVisible(true);
        } else if (src.equalsIgnoreCase("Delete")) {
            Personal_Delete p_delete = new Personal_Delete();
            p_delete.setVisible(true);
        } else if (src.equalsIgnoreCase("View")) {
            Personal_View p_view = new Personal_View();
            p_view.setVisible(true);
        } else if (src.equalsIgnoreCase("Quit")) {
            Quit quit = new Quit();
            quit.onQuit();

        }
    }
}
