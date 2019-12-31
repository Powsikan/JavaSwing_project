package operation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.*;

public class Personal_Add extends JFrame implements KeyListener {
    JLabel l1, l2, l3, l4, l5;
    JTextField t1, t2, t3, t4, t5;

    Connection con;
    PreparedStatement ps;
    Statement st;
    ResultSet rs;

    String stno, stname, staddr, sttelno, stemail;

    public Personal_Add() {
        super("Personal Data Add Frame");
        setSize(320, 420);
        setResizable(false);
        setLayout(null);

        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension windowsize = getSize();

        this.setBounds((screensize.width - windowsize.width) / 2, (screensize.height - windowsize.height) / 2, windowsize.width, windowsize.height);

        l1 = new JLabel("Number");
        add(l1);
        l1.setBounds(30, 100, 100, 25);

        l2 = new JLabel("Name");
        add(l2);
        l2.setBounds(30, 150, 100, 25);

        l3 = new JLabel("Address");
        add(l3);
        l3.setBounds(30, 200, 100, 25);

        l4 = new JLabel("TeleNo");
        add(l4);
        l4.setBounds(30, 250, 100, 25);

        l5 = new JLabel("Email");
        add(l5);
        l5.setBounds(30, 300, 100, 25);


        t1 = new JTextField();
        add(t1);
        t1.setBounds(190, 100, 100, 25);
        t1.addKeyListener(this);

        t2 = new JTextField();
        add(t2);
        t2.setBounds(190, 150, 100, 25);
        t2.setEditable(false);
        t2.addKeyListener(this);

        t3 = new JTextField();
        add(t3);
        t3.setBounds(190, 200, 100, 25);
        t3.setEditable(false);
        t3.addKeyListener(this);

        t4 = new JTextField();
        add(t4);
        t4.setBounds(190, 250, 100, 25);
        t4.setEditable(false);
        t4.addKeyListener(this);

        t5 = new JTextField();
        add(t5);
        t5.setBounds(190, 300, 100, 25);
        t5.setEditable(false);
        t5.addKeyListener(this);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "root", "");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyChar();
        if ((e.getSource() == t1) && (code == 10)) {
            onNumVal();
        } else if ((e.getSource() == t2) && (code == 10)) {
            onNameVal();
        } else if ((e.getSource() == t3) && (code == 10)) {
            onAddrVal();
        } else if ((e.getSource() == t4) && (code == 10)) {
            onTelVal();
        } else if ((e.getSource() == t5) && (code == 10)) {
            onEmailVal();
        }

    }

    private void onRecordAdd() {
        int confirm = JOptionPane.showConfirmDialog(null, "Do you want to add this record", "Addition message", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (confirm == JOptionPane.YES_NO_OPTION) {
            try {
                ps = con.prepareStatement("insert into personal_data values(?,?,?,?,?)");
                ps.setString(1, stno);
                ps.setString(2, stname);
                ps.setString(3, staddr);
                ps.setString(4, sttelno);
                ps.setString(5, stemail);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "record added");

                onUserConfirm();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            onUserConfirm();

        }
    }


    private void onUserConfirm() {
        int confirm = JOptionPane.showConfirmDialog(null, "Do you want to continue", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (confirm == JOptionPane.YES_NO_OPTION) {
            onClearAndStart();
        } else {
            System.exit(0);
        }
    }

    private void onClearAndStart() {
        t1.setText("");
        t2.setText("");
        t3.setText("");
        t4.setText("");
        t5.setText("");
        t1.setEditable(true);
        t1.requestFocus();
        t2.setEditable(false);
        t3.setEditable(false);
        t4.setEditable(false);
        t5.setEditable(false);
    }

    private void onEmailVal() {
        stemail = t5.getText();
        if (stemail.equals(""))
            JOptionPane.showMessageDialog(null, "Email is not entered ", "Error", JOptionPane.ERROR_MESSAGE);
        else if (stemail.indexOf('@') < 0 || stemail.indexOf('.') < 0) {
            JOptionPane.showMessageDialog(null, "Email format is invalid", "Error", JOptionPane.ERROR_MESSAGE);
            t5.setText("");
            t5.requestFocus();
        } else {
            int x, y;
            y = 0;
            for (x = 0; x < stemail.length(); x++) {
                if (stemail.charAt(x) == '@')
                    y++;
            }
            if (y > 1) {
                JOptionPane.showMessageDialog(null, "Email format is invalid", "Error", JOptionPane.ERROR_MESSAGE);
                t5.setText("");
                t5.requestFocus();
            } else {
                onRecordAdd();
            }
        }
    }

    private void onTelVal() {
        sttelno = t4.getText();
        int tno;
        if (sttelno.equals(""))
            JOptionPane.showMessageDialog(null, "TelNo is not entered ", "Error", JOptionPane.ERROR_MESSAGE);
        else {
            try {
                sttelno = sttelno.trim();
                tno = Integer.parseInt(sttelno);
                if (sttelno.length() != 10) {
                    JOptionPane.showMessageDialog(null, "TelNo is should be 10 digits ", "Error", JOptionPane.ERROR_MESSAGE);
                    t4.setText("");
                    t4.requestFocus();
                } else {
                    if (sttelno.charAt(0) != '0') {
                        JOptionPane.showMessageDialog(null, "TelNo is should be start with 0 ", "Error", JOptionPane.ERROR_MESSAGE);
                        t4.setText("");
                        t4.requestFocus();
                    } else {
                        t5.setEditable(true);
                        t5.requestFocus();
                        t4.setEditable(false);
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Only numbers allowed ", "Error", JOptionPane.ERROR_MESSAGE);
                t4.setText("");
            }
        }
    }

    private void onAddrVal() {
        staddr = t3.getText();
        if (stname.equals(""))
            JOptionPane.showMessageDialog(null, "Address is not entered ", "Error", JOptionPane.ERROR_MESSAGE);
        else {
            t4.setEditable(true);
            t4.requestFocus();
            t3.setEditable(false);
        }

    }

    private void onNameVal() {
        stname = t2.getText();
        if (stname.equals(""))
            JOptionPane.showMessageDialog(null, "Name is not entered ", "Error", JOptionPane.ERROR_MESSAGE);
        else {
            t3.setEditable(true);
            t3.requestFocus();
            t2.setEditable(false);
        }


    }

    private void onNumVal() {
        stno = t1.getText();
        if (stno.equals("")) {
            JOptionPane.showConfirmDialog(null, "Number is Not Entered");
        } else {
            stno = stno.trim();
            if (stno.length() != 5) {
                JOptionPane.showConfirmDialog(null, "Number Length should be 5");
                t1.setText("");
            } else {
                try {
                    st = con.createStatement();
                    rs = st.executeQuery("select * from personal_data where Number='" + stno + "'");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if (rs.next()) {
                        JOptionPane.showConfirmDialog(null, "Number Already Exist");
                        t1.setText("");
                        t1.requestFocus();
                    } else {
                        t2.setEditable(true);
                        t2.requestFocus();
                        t1.setEditable(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
